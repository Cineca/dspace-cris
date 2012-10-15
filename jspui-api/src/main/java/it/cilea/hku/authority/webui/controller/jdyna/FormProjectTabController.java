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

import it.cilea.hku.authority.model.dynamicfield.BoxProject;
import it.cilea.hku.authority.model.dynamicfield.EditTabProject;
import it.cilea.hku.authority.model.dynamicfield.TabProject;
import it.cilea.hku.authority.service.ExtendedTabService;
import it.cilea.hku.authority.util.ResearcherPageUtils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

public class FormProjectTabController
		extends
		AFormTabController<BoxProject, TabProject> {

	public FormProjectTabController(Class<TabProject> clazzT,
			Class<BoxProject> clazzB) {
		super(clazzT, clazzB);
	}

	@Override
	protected Map referenceData(HttpServletRequest request) throws Exception {
		String paramId = request.getParameter("id");
		Map<String, Object> map = super.referenceData(request);
		if (paramId != null) {			
			EditTabProject editTab = ((ExtendedTabService<BoxProject, TabProject, EditTabProject>) applicationService)
					.getEditTabByDisplayTab(Integer.parseInt(paramId),EditTabProject.class);
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
		TabProject object = (TabProject) command;
      
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
		applicationService.saveOrUpdate(TabProject.class,
				object);
		
        MultipartFile itemIcon = object.getIconFile();
        
        // if there is a remote url we don't upload the file 
        if (itemIcon != null && !itemIcon.getOriginalFilename().isEmpty())
        {
           ResearcherPageUtils.loadTabIcon(object, object.getId().toString(), object.getIconFile());
           applicationService.saveOrUpdate(TabProject.class,
   				object);
        }
		if (createEditTab) {
			String name = ExtendedTabService.PREFIX_SHORTNAME_EDIT_TAB
					+ object.getShortName();
			String title = ExtendedTabService.PREFIX_TITLE_EDIT_TAB
			+ object.getTitle();
			EditTabProject e = applicationService
					.getTabByShortName(EditTabProject.class,
							name);
			if (e == null) {
				e = new EditTabProject();
				e.setDisplayTab(object);
				e.setTitle(title);
				e.setShortName(name);
				applicationService.saveOrUpdate(
						EditTabProject.class, e);
			} else {
				if (e.getDisplayTab() == null) {
					e.setDisplayTab(object);
					applicationService.saveOrUpdate(
							EditTabProject.class, e);
				}
			}
		}
		return new ModelAndView(getSuccessView());
	}
}
