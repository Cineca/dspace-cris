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
package it.cilea.hku.authority.webui.controller.jdyna;

import it.cilea.hku.authority.model.dynamicfield.BoxResearcherGrant;
import it.cilea.hku.authority.model.dynamicfield.EditTabResearcherGrant;
import it.cilea.hku.authority.model.dynamicfield.TabResearcherGrant;
import it.cilea.hku.authority.service.ExtendedTabService;
import it.cilea.hku.authority.util.ResearcherPageUtils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

public class FormGrantTabController
		extends
		AFormTabController<BoxResearcherGrant, TabResearcherGrant> {

	public FormGrantTabController(Class<TabResearcherGrant> clazzT,
			Class<BoxResearcherGrant> clazzB) {
		super(clazzT, clazzB);
	}

	@Override
	protected Map referenceData(HttpServletRequest request) throws Exception {
		String paramId = request.getParameter("id");
		Map<String, Object> map = super.referenceData(request);
		if (paramId != null) {			
			EditTabResearcherGrant editTab = ((ExtendedTabService<BoxResearcherGrant, TabResearcherGrant, EditTabResearcherGrant>) applicationService)
					.getEditTabByDisplayTab(Integer.parseInt(paramId),EditTabResearcherGrant.class);
			if (editTab != null) {
				map.put("edittabid", editTab.getId());
			}
		}
		return map;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		TabResearcherGrant object = (TabResearcherGrant) command;
      
		String deleteImage_s = request.getParameter("deleteIcon");		

        if (deleteImage_s != null)
        {
            Boolean deleteImage = Boolean.parseBoolean(deleteImage_s);
            if (deleteImage)
            {
                ResearcherPageUtils.removeTabIcon(object);
            }
        }

        
		boolean createEditTab = false;
		if (object.getId() == null) {
			createEditTab = true;
		}
		applicationService.saveOrUpdate(TabResearcherGrant.class,
				object);
		
        MultipartFile itemIcon = object.getIconFile();
        
        // if there is a remote url we don't upload the file 
        if (itemIcon != null && !itemIcon.getOriginalFilename().isEmpty())
        {
           ResearcherPageUtils.loadTabIcon(object, object.getId().toString(), object.getIconFile());
           applicationService.saveOrUpdate(TabResearcherGrant.class,
   				object);
        }
		if (createEditTab) {
			String name = ExtendedTabService.PREFIX_SHORTNAME_EDIT_TAB
					+ object.getShortName();
			String title = ExtendedTabService.PREFIX_TITLE_EDIT_TAB
			+ object.getTitle();
			EditTabResearcherGrant e = applicationService
					.getTabByShortName(EditTabResearcherGrant.class,
							name);
			if (e == null) {
				e = new EditTabResearcherGrant();
				e.setDisplayTab(object);
				e.setTitle(title);
				e.setShortName(name);
				applicationService.saveOrUpdate(
						EditTabResearcherGrant.class, e);
			} else {
				if (e.getDisplayTab() == null) {
					e.setDisplayTab(object);
					applicationService.saveOrUpdate(
							EditTabResearcherGrant.class, e);
				}
			}
		}
		return new ModelAndView(getSuccessView());
	}
}
