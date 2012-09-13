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
import it.cilea.hku.authority.model.dynamicfield.GrantPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.TabResearcherGrant;
import it.cilea.hku.authority.service.ExtendedTabService;
import it.cilea.osd.jdyna.model.IContainable;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

public class FormGrantBoxController
		extends
		AFormBoxController<GrantPropertiesDefinition, BoxResearcherGrant, TabResearcherGrant> {

	
	
	public FormGrantBoxController(Class<BoxResearcherGrant> clazzH,
			Class<GrantPropertiesDefinition> clazzTP) {
		super(clazzH, clazzTP);
	}

	@Override
	protected Map referenceData(HttpServletRequest request) throws Exception {
		String paramId = request.getParameter("id");
		String tabId = request.getParameter("tabId");
		Map<String, Object> map = super.referenceData(request);
		List<IContainable> containables = new LinkedList<IContainable>();
		if (paramId != null) {
			BoxResearcherGrant box = applicationService.get(BoxResearcherGrant.class, Integer.parseInt(paramId));
			((ExtendedTabService)applicationService).findOtherContainablesInBoxByConfiguration(box.getShortName(), containables);
		}
		map.put("owneredContainablesByConfiguration", containables);
		List<IContainable> containablesList = new LinkedList<IContainable>();
		containablesList = (List<IContainable>)map.get("containablesList");
		containablesList.addAll(containables);
		map.put("allContainablesList", containablesList);
		map.put("tabId", tabId);
		return map;

	}	
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {

		BoxResearcherGrant object = (BoxResearcherGrant) command;
		String tabId = request.getParameter("tabId");
		applicationService.saveOrUpdate(BoxResearcherGrant.class,
				object);
		saveMessage(
				request,
				getText("action.box.edited", new Object[] { object.getShortName() },
						request.getLocale()));
		return new ModelAndView(getSuccessView() + "?id=" + object.getId());
	}

}
