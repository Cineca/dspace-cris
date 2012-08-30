/**
 * <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
 * <html><head>
 * <title>301 Moved Permanently</title>
 * </head><body>
 * <h1>Moved Permanently</h1>
 * <p>The document has moved <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>.</p>
 * </body></html>
 */
package it.cilea.hku.authority.webui.controller.jdyna;

import it.cilea.hku.authority.model.dynamicfield.BoxRPAdditionalFieldStorage;
import it.cilea.hku.authority.model.dynamicfield.EditTabRPAdditionalFieldStorage;
import it.cilea.hku.authority.model.dynamicfield.TabRPAdditionalFieldStorage;
import it.cilea.hku.authority.service.ExtendedTabService;
import it.cilea.hku.authority.util.ResearcherPageUtils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

public class FormRPTabController
		extends
		AFormTabController<BoxRPAdditionalFieldStorage, TabRPAdditionalFieldStorage> {

	public FormRPTabController(Class<TabRPAdditionalFieldStorage> clazzT,
			Class<BoxRPAdditionalFieldStorage> clazzB) {
		super(clazzT, clazzB);
	}

	@Override
	protected Map referenceData(HttpServletRequest request) throws Exception {
		String paramId = request.getParameter("id");
		Map<String, Object> map = super.referenceData(request);
		if (paramId != null) {
			EditTabRPAdditionalFieldStorage editTab = ((ExtendedTabService<BoxRPAdditionalFieldStorage,TabRPAdditionalFieldStorage,EditTabRPAdditionalFieldStorage>) applicationService)
					.getEditTabByDisplayTab(Integer.parseInt(paramId), EditTabRPAdditionalFieldStorage.class);
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
		TabRPAdditionalFieldStorage object = (TabRPAdditionalFieldStorage) command;
      
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
		applicationService.saveOrUpdate(TabRPAdditionalFieldStorage.class,
				object);
		
        MultipartFile itemIcon = object.getIconFile();
        
        // if there is a remote url we don't upload the file 
        if (itemIcon != null && !itemIcon.getOriginalFilename().isEmpty())
        {
           ResearcherPageUtils.loadTabIcon(object, object.getId().toString(), object.getIconFile());
           applicationService.saveOrUpdate(TabRPAdditionalFieldStorage.class,
   				object);
        }
		if (createEditTab) {
			String name = ExtendedTabService.PREFIX_SHORTNAME_EDIT_TAB
					+ object.getShortName();
			String title = ExtendedTabService.PREFIX_TITLE_EDIT_TAB
			+ object.getTitle();
			EditTabRPAdditionalFieldStorage e = applicationService
					.getTabByShortName(EditTabRPAdditionalFieldStorage.class,
							name);
			if (e == null) {
				e = new EditTabRPAdditionalFieldStorage();
				e.setDisplayTab(object);
				e.setTitle(title);
				e.setShortName(name);
				applicationService.saveOrUpdate(
						EditTabRPAdditionalFieldStorage.class, e);
			} else {
				if (e.getDisplayTab() == null) {
					e.setDisplayTab(object);
					applicationService.saveOrUpdate(
							EditTabRPAdditionalFieldStorage.class, e);
				}
			}
		}
		return new ModelAndView(getSuccessView());
	}
}
