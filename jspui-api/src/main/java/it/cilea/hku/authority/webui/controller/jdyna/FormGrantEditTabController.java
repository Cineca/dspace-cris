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
import it.cilea.hku.authority.service.ApplicationService;
import it.cilea.hku.authority.util.ResearcherPageUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

public class FormGrantEditTabController
		extends
		AFormTabController<BoxResearcherGrant, EditTabResearcherGrant> {

	public final static String SUBMIT_DECOUPLE = "dehookupit";
	public final static String SUBMIT_HOOKUP = "hookupit";

	public FormGrantEditTabController(
			Class<EditTabResearcherGrant> clazzT,
			Class<BoxResearcherGrant> clazzB) {
		super(clazzT, clazzB);
	}

	@Override
	protected Map referenceData(HttpServletRequest request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		String paramId = request.getParameter("id");
		if (paramId != null) {
			EditTabResearcherGrant object = applicationService.get(
					EditTabResearcherGrant.class,
					Integer.parseInt(paramId));
			if (object != null && object.getDisplayTab() != null) {
				List<BoxResearcherGrant> owneredContainers = new LinkedList<BoxResearcherGrant>();
				for (BoxResearcherGrant box : object.getDisplayTab()
						.getMask()) {				
					owneredContainers.add(box);
				}
				map.put("boxsList", owneredContainers);
			} else {
				map = super.referenceData(request);
			}
		} else {
			map = super.referenceData(request);
		}

		return map;
	}

	@Override
	protected ModelAndView processFormSubmission(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		EditTabResearcherGrant object = (EditTabResearcherGrant) command;
		Map<String, String> maprequest = request.getParameterMap();

		if (maprequest.containsKey(SUBMIT_DECOUPLE)) {
			((ApplicationService) applicationService)
					.decoupleEditTabByDisplayTab(object.getDisplayTab().getId(), EditTabResearcherGrant.class);
		}
		if (maprequest.containsKey(SUBMIT_HOOKUP)) {
			String hookit = request.getParameter("hookedtab");
			TabResearcherGrant tab = applicationService.getTabByShortName(
					TabResearcherGrant.class, hookit);
			if (tab == null) {
				return new ModelAndView(getFormView(), "tab", object);
			}
			((ApplicationService) applicationService).saveOrUpdate(
					EditTabResearcherGrant.class, object);
			((ApplicationService) applicationService)
					.hookUpEditTabToDisplayTab(object.getId(), tab.getId(), EditTabResearcherGrant.class);
		}
		return super.processFormSubmission(request, response, command, errors);
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		EditTabResearcherGrant object = (EditTabResearcherGrant) command;
      
		String deleteImage_s = request.getParameter("deleteIcon");		

        if (deleteImage_s != null)
        {
            Boolean deleteImage = Boolean.parseBoolean(deleteImage_s);
            if (deleteImage)
            {
                ResearcherPageUtils.removeTabIcon(object);
            }
        }

        		
		applicationService.saveOrUpdate(EditTabResearcherGrant.class,
				object);
		
        MultipartFile itemIcon = object.getIconFile();
        
        // if there is a remote url we don't upload the file 
        if (itemIcon != null && !itemIcon.getOriginalFilename().isEmpty())
        {
           ResearcherPageUtils.loadTabIcon(object, object.getId().toString(), object.getIconFile());
           applicationService.saveOrUpdate(EditTabResearcherGrant.class,
   				object);
        }
		return new ModelAndView(getSuccessView());
	}
}
