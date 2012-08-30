/**
 * <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
 * <html><head>
 * <title>301 Moved Permanently</title>
 * </head><body>
 * <h1>Moved Permanently</h1>
 * <p>The document has moved <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>.</p>
 * </body></html>
 */
package it.cilea.hku.authority.webui.controller;

import it.cilea.hku.authority.model.ResearcherPage;
import it.cilea.hku.authority.model.RestrictedField;
import it.cilea.hku.authority.util.ResearcherPageUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.WildcardFilter;
import org.apache.commons.lang.StringUtils;
import org.dspace.app.webui.util.UIUtil;
import org.dspace.authorize.AuthorizeException;
import org.dspace.authorize.AuthorizeManager;
import org.dspace.core.ConfigurationManager;
import org.dspace.core.Context;
import org.dspace.core.Utils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * This SpringMVC controller is responsible to handle the edit of all the data
 * of a researcher page except the addition data that are handled by the
 * {@link FormAdditionalDataEditController}.
 * 
 * @author cilea
 * 
 */
public class FormDataEditController extends AFormResearcherPageController
{
    /*
     * constants to define the scope of the edit page
     */
    /**
     * primary contact data edit page
     */
    public final String METHOD_VIEW_MODE_EDITPRIMARY = "primary";

    /**
     * mediaspokesmanship data edit page
     */
    public final String METHOD_VIEW_MODE_EDITMEDIASPOKES = "mediaspokesmanship";

    /**
     * bibliometric data edit page
     */
    public final String METHOD_VIEW_MODE_EDITBIBLIOMETRIC = "bibliometric";

    /**
     * the scope of the edit page, injected by Spring IoC
     */
    private String method;

    @Override
    protected Object formBackingObject(HttpServletRequest request)
            throws Exception
    {

        String id_s = request.getParameter("id");
        Integer id = null;
        if (id_s != null)
        {
            id = Integer.parseInt(id_s);
        }
        ResearcherPage researcher = applicationService.get(
                ResearcherPage.class, id);
        Context context = UIUtil.obtainContext(request);
        if (!context.getCurrentUser().getNetid().equalsIgnoreCase(
                researcher.getStaffNo())
                && !AuthorizeManager.isAdmin(context))
        {
            throw new AuthorizeException(
                    "Only system admin can edit not personal researcher page");
        }
        if (method.equals(METHOD_VIEW_MODE_EDITMEDIASPOKES)
                || method.equals(METHOD_VIEW_MODE_EDITBIBLIOMETRIC))
        {
            if (!AuthorizeManager.isAdmin(context))
            {
                throw new AuthorizeException(
                        "Only system administrator can access to edit mediaspokesmanship/bibliometric data");
            }
            researcher.setMedia(getLazyList(researcher.getMedia()));
            researcher.setSpokenLanguagesEN(getLazyList(researcher
                    .getSpokenLanguagesEN()));
            researcher.setSpokenLanguagesZH(getLazyList(researcher
                    .getSpokenLanguagesZH()));
            researcher.setWrittenLanguagesEN(getLazyList(researcher
                    .getWrittenLanguagesEN()));
            researcher.setWrittenLanguagesZH(getLazyList(researcher
                    .getWrittenLanguagesZH()));
        }

        if (method.equals(METHOD_VIEW_MODE_EDITPRIMARY))
        {
            researcher.setInterests(getLazyList(researcher.getInterests()));
            researcher.setVariants(getLazyList(researcher.getVariants()));
            researcher.setTitle(getLazyList(researcher.getTitle()));
        }
        return researcher;
    }

    @Override
    /**
     * Disallow non admin user to edit some fields
     */
    protected ServletRequestDataBinder createBinder(HttpServletRequest request,
            Object command) throws Exception
    {
        Context context = UIUtil.obtainContext(request);
        if (AuthorizeManager.isAdmin(context))
        {
            return super.createBinder(request, command);
        }
        ServletRequestDataBinder binder = super.createBinder(request, command);
        binder.setDisallowedFields(new String[] { "staffNo", "fullName",
                "ridISI.value", "ridLinkISI.value", "paperCountISI.value",
                "paperLinkISI.value", "citationCountISI.value",
                "citationLinkISI.value", "coAuthorsISI.value",
                "hindexISI.value", "authorIdScopus.value",
                "authorIdLinkScopus.value", "paperCountScopus.value",
                "paperLinkScopus.value", "citationCountScopus.value",
                "citationLinkScopus.value", "coAuthorsScopus.value",
                "coAuthorsLinkScopus.value", "hindexScopus.value",
                "spokenLanguagesEN", "spokenLanguagesZH", "writtenLanguagesEN",
                "writtenLanguagesZH", "media" });
        return binder;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object command, BindException errors)
            throws Exception
    {

        ResearcherPage researcher = (ResearcherPage) command;
        String rp = ResearcherPageUtils.getPersistentIdentifier(researcher);

        if (method.equals(METHOD_VIEW_MODE_EDITPRIMARY))
        {
            String deleteImage_s = request.getParameter("deleteImage");

            if (deleteImage_s != null)
            {
                Boolean deleteImage = Boolean.parseBoolean(deleteImage_s);
                if (deleteImage)
                {
                    File image = new File(ConfigurationManager
                            .getProperty("researcherpage.file.path")
                            + File.separatorChar
                            + ResearcherPageUtils.getPersistentIdentifier(researcher)
                            + File.separatorChar + ResearcherPageUtils.getPersistentIdentifier(researcher) + "." + researcher.getPict().getValue());
                    image.delete();   
                    researcher.setPict(null);
                }
            }
            
            String deleteCV_s = request.getParameter("deleteCV");

			if ((deleteCV_s != null && Boolean.parseBoolean(deleteCV_s))
					|| StringUtils
							.isNotEmpty(researcher.getCv().getRemoteUrl()))
            {
                removeCVFiles(researcher);   
            }
            
            List<RestrictedField> list_interest = new LinkedList<RestrictedField>();
            for (RestrictedField rf : researcher.getInterests())
            {
                if (rf == null || rf.getValue() == null
                        || rf.getValue().equals(""))
                {
                    // elimino dalla lista e loggo
                    log.info("Discard value from lazy list of interests");
                }
                else
                {
                    list_interest.add(rf);
                }
            }

            List<RestrictedField> list_variants = new LinkedList<RestrictedField>();
            for (RestrictedField rf : researcher.getVariants())
            {
                if (rf == null || rf.getValue() == null
                        || rf.getValue().equals(""))
                {
                    // elimino dalla lista e loggo
                    log.info("Discard value from lazy list of variants");
                }
                else
                {
                    list_variants.add(rf);
                }
            }

            List<RestrictedField> list_titles = new LinkedList<RestrictedField>();
            for (RestrictedField rf : researcher.getTitle())
            {
                if (rf == null || rf.getValue() == null
                        || rf.getValue().equals(""))
                {
                    // elimino dalla lista e loggo
                    log.info("Discard value from lazy list of title");
                }
                else
                {
                    list_titles.add(rf);
                }
            }

            MultipartFile itemImage = researcher.getPict().getFile();
            if (itemImage != null && !itemImage.getOriginalFilename().isEmpty())
            {
                String pathImage = ConfigurationManager
                        .getProperty("researcherpage.file.path");
                String ext = itemImage.getOriginalFilename().substring(
                        itemImage.getOriginalFilename().lastIndexOf(".") + 1);
                File dir = new File(pathImage + File.separatorChar + rp);
                dir.mkdir();
                File file = new File(dir, rp + "." + ext);
                file.createNewFile();
                FileOutputStream out = new FileOutputStream(file);
                Utils.bufferedCopy(itemImage.getInputStream(), out);
                out.close();
                researcher.getPict().setValue(ext);
                researcher.getPict().setMimeType(itemImage.getContentType());
            }

            MultipartFile itemCV = researcher.getCv().getFile();
            
            // if there is a remote url we don't upload the file 
            if (StringUtils.isEmpty(researcher.getCv().getRemoteUrl()) && 
            		itemCV != null && !itemCV.getOriginalFilename().isEmpty())
            {
                String pathCV = ConfigurationManager
                        .getProperty("researcherpage.file.path");
                String ext = itemCV.getOriginalFilename().substring(
                        itemCV.getOriginalFilename().lastIndexOf(".") + 1);
                File dir = new File(pathCV + File.separatorChar + rp);
                dir.mkdir();
                File file = new File(dir, rp + "-CV." + ext);
                file.createNewFile();
                FileOutputStream out = new FileOutputStream(file);
                Utils.bufferedCopy(itemCV.getInputStream(), out);
                out.close();
                researcher.getCv().setValue(ext);
                researcher.getCv().setMimeType(itemCV.getContentType());
            }
            
            researcher.setInterests(list_interest);
            researcher.setVariants(list_variants);
            researcher.setTitle(list_titles);

        }

        if (method.equals(METHOD_VIEW_MODE_EDITMEDIASPOKES)
                || method.equals(METHOD_VIEW_MODE_EDITBIBLIOMETRIC))
        {
            List<RestrictedField> list_media = new LinkedList<RestrictedField>();
            for (RestrictedField rf : researcher.getMedia())
            {
                if (rf == null || rf.getValue() == null
                        || rf.getValue().equals(""))
                {
                    // elimino dalla lista e loggo
                    log.info("Discard value from lazy list of media");
                }
                else
                {
                    list_media.add(rf);
                }
            }

            List<RestrictedField> list_spokenEN = new LinkedList<RestrictedField>();
            for (RestrictedField rf : researcher.getSpokenLanguagesEN())
            {
                if (rf == null || rf.getValue() == null
                        || rf.getValue().equals(""))
                {
                    // elimino dalla lista e loggo
                    log
                            .info("Discard value from lazy list of spoken languages EN");
                }
                else
                {
                    list_spokenEN.add(rf);
                }
            }

            List<RestrictedField> list_spokenZH = new LinkedList<RestrictedField>();
            for (RestrictedField rf : researcher.getSpokenLanguagesZH())
            {
                if (rf == null || rf.getValue() == null
                        || rf.getValue().equals(""))
                {
                    // elimino dalla lista e loggo
                    log
                            .info("Discard value from lazy list of spoken languages ZH");
                }
                else
                {
                    list_spokenZH.add(rf);
                }
            }

            List<RestrictedField> list_writtenEN = new LinkedList<RestrictedField>();
            for (RestrictedField rf : researcher.getWrittenLanguagesEN())
            {
                if (rf == null || rf.getValue() == null
                        || rf.getValue().equals(""))
                {
                    // elimino dalla lista e loggo
                    log
                            .info("Discard value from lazy list of written languages EN");
                }
                else
                {
                    list_writtenEN.add(rf);
                }
            }

            List<RestrictedField> list_writtenZH = new LinkedList<RestrictedField>();
            for (RestrictedField rf : researcher.getWrittenLanguagesZH())
            {
                if (rf == null || rf.getValue() == null
                        || rf.getValue().equals(""))
                {
                    // elimino dalla lista e loggo
                    log
                            .info("Discard value from lazy list of written languages ZH");
                }
                else
                {
                    list_writtenZH.add(rf);
                }
            }
            researcher.setMedia(list_media);
            researcher.setSpokenLanguagesEN(list_spokenEN);
            researcher.setSpokenLanguagesZH(list_spokenZH);
            researcher.setWrittenLanguagesEN(list_writtenEN);
            researcher.setWrittenLanguagesZH(list_writtenZH);
        }

        applicationService.saveOrUpdate(ResearcherPage.class, researcher);

        return new ModelAndView(getSuccessView() + rp);
    }

private void removeCVFiles(ResearcherPage researcher) {
    researcher.getCv().setValue(null);
    researcher.getCv().setMimeType(null);
    File directory = new File(ConfigurationManager
            .getProperty("researcherpage.file.path")
            + File.separatorChar
            + ResearcherPageUtils.getPersistentIdentifier(researcher));
    
    if (directory.exists())
    {
        Collection<File> files = FileUtils.listFiles(directory, 
                new WildcardFilter("*-CV.*"), 
                null);
                
        for (File cv : files)
        {
            cv.delete();
        }
    }
}

    /**
     * Decorate list for dynamic binding with spring mvc
     * 
     * @param list
     * @return lazy list temporary
     */
    private List getLazyList(List<RestrictedField> list)
    {
        log.debug("Decorate list for dynamic binding with spring mvc");
        List lazyList = LazyList.decorate(list, FactoryUtils
                .instantiateFactory(RestrictedField.class));

        return lazyList;
    }

    public String getMethod()
    {
        return method;
    }

    public void setMethod(String method)
    {
        this.method = method;
    }

}
