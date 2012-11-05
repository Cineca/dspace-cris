/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 * 
 * http://www.dspace.org/license/
 * 
 * The document has moved 
 * <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>
 */
package it.cilea.hku.authority.webui.controller.json;

import java.io.IOException;
import java.sql.SQLException;

import flexjson.JSONSerializer;
import it.cilea.hku.authority.model.ResearcherPage;
import it.cilea.hku.authority.model.RestrictedField;
import it.cilea.hku.authority.model.dynamicfield.RPPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.RPProperty;
import it.cilea.hku.authority.model.dynamicfield.VisibilityTabConstant;
import it.cilea.hku.authority.service.ApplicationService;
import it.cilea.hku.authority.util.ResearcherPageUtils;
import it.cilea.osd.jdyna.model.AccessLevelConstants;
import it.cilea.osd.jdyna.value.TextValue;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dspace.app.webui.util.UIUtil;
import org.dspace.core.Context;
import org.dspace.eperson.EPerson;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 * Retrieve data on the researcher profile of the logged user to be used in a
 * "My Researcher Page"
 * 
 * @author cilea
 * 
 */
public class MyRPJSONController extends MultiActionController
{
    /**
     * the applicationService for query the RP db, injected by Spring IoC
     */
    private ApplicationService applicationService;

    public void setApplicationService(ApplicationService applicationService)
    {
        this.applicationService = applicationService;
    }

    public ModelAndView create(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        // authorization is checked by the getMyRP method
        ResearcherPage rp = getMyResearcherPage(request);
        if (rp == null)
        {
            rp = new ResearcherPage();
            rp.setEpersonID(getCurrentUser(request).getID());
            RPPropertiesDefinition fN = applicationService
                    .findPropertiesDefinitionByShortName(
                            RPPropertiesDefinition.class, "fullName");
            TextValue val = new TextValue();
            val.setOggetto(getCurrentUser(request).getFullName());
            RPProperty prop = rp.createProprieta(fN);
            prop.setValue(val);
            prop.setVisibility(1);
            applicationService.saveOrUpdate(ResearcherPage.class, rp);
        }
        returnStatusJSON(response, rp);
        return null;
    }

    private void returnStatusJSON(HttpServletResponse response,
            ResearcherPage rp) throws IOException
    {
        RPStatusInformation info = new RPStatusInformation();
        if (rp != null)
        {
            info.setActive(rp.getStatus() != null ? rp.getStatus() : false);
            info.setUrl("/cris/" + rp.getPublicPath() + "/"
                    + ResearcherPageUtils.getPersistentIdentifier(rp));
        }
        JSONSerializer serializer = new JSONSerializer();
        serializer.rootName("myrp");
        serializer.exclude("class");
        response.setContentType("application/json");
        serializer.deepSerialize(info, response.getWriter());
    }

    private ResearcherPage getMyResearcherPage(HttpServletRequest request)
            throws SQLException, ServletException
    {
        EPerson currUser = getCurrentUser(request);
        if (currUser == null)
        {
            throw new ServletException(
                    "Wrong data or configuration: access to the my rp servlet without a valid user: there is no user logged in");
        }

        int id = currUser.getID();
        ResearcherPage rp = applicationService.getResearcherPageByEPersonId(id);
        return rp;
    }

    private EPerson getCurrentUser(HttpServletRequest request)
            throws SQLException
    {
        Context context = UIUtil.obtainContext(request);
        EPerson currUser = context.getCurrentUser();
        return currUser;
    }

    public ModelAndView activate(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        ResearcherPage rp = getMyResearcherPage(request);
        if (rp.getStatus() == null || rp.getStatus() == false)
        {
            rp.setStatus(true);
            applicationService.saveOrUpdate(ResearcherPage.class, rp);
        }

        returnStatusJSON(response, rp);
        return null;
    }

    public ModelAndView hide(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        ResearcherPage rp = getMyResearcherPage(request);
        if (rp.getStatus() == null || rp.getStatus() == true)
        {
            rp.setStatus(false);
            applicationService.saveOrUpdate(ResearcherPage.class, rp);
        }

        returnStatusJSON(response, rp);
        return null;
    }

    public ModelAndView remove(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        ResearcherPage rp = getMyResearcherPage(request);
        if (rp != null)
        {
            applicationService.delete(ResearcherPage.class, rp.getId());
        }
        returnStatusJSON(response, null);
        return null;
    }

    public ModelAndView status(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        ResearcherPage rp = getMyResearcherPage(request);
        returnStatusJSON(response, rp);
        return null;
    }

    class RPStatusInformation
    {
        private boolean active;

        private String url;

        public boolean isActive()
        {
            return active;
        }

        public void setActive(boolean active)
        {
            this.active = active;
        }

        public String getUrl()
        {
            return url;
        }

        public void setUrl(String url)
        {
            this.url = url;
        }
    }
}
