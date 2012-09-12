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

import it.cilea.hku.authority.model.dynamicfield.BoxRPAdditionalFieldStorage;
import it.cilea.hku.authority.model.dynamicfield.BoxResearcherGrant;
import it.cilea.hku.authority.model.dynamicfield.DecoratorGrantPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.GrantPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.TabRPAdditionalFieldStorage;
import it.cilea.osd.jdyna.widget.WidgetDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;



public class FormGrantDatePropertiesDefinitionController extends AFormDecoratorPropertiesDefinitionController<WidgetDate, GrantPropertiesDefinition, DecoratorGrantPropertiesDefinition, BoxRPAdditionalFieldStorage, TabRPAdditionalFieldStorage> {

	
	
	public FormGrantDatePropertiesDefinitionController(Class<GrantPropertiesDefinition> targetModel, Class<WidgetDate> renderingModel) {
		super(targetModel, renderingModel);
	}
	
	

	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		String boxId = request.getParameter("boxId");
		String tabId = request.getParameter("tabId");
		
		DecoratorGrantPropertiesDefinition object = (DecoratorGrantPropertiesDefinition) command;
		getApplicationService().saveOrUpdate(DecoratorGrantPropertiesDefinition.class, object);
		if(boxId!=null && !boxId.isEmpty()) {
			BoxResearcherGrant box = getApplicationService().get(BoxResearcherGrant.class, Integer.parseInt(boxId));
			box.getMask().add(object);
			getApplicationService().saveOrUpdate(BoxResearcherGrant.class, box);	
		}
		return new ModelAndView(getSuccessView()+"?id="+boxId+"&tabId="+tabId);
		
	}
}
