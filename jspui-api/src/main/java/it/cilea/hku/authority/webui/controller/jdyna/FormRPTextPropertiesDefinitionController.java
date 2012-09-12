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
import it.cilea.hku.authority.model.dynamicfield.DecoratorRPPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.RPPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.TabRPAdditionalFieldStorage;
import it.cilea.osd.jdyna.widget.WidgetTesto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;



public class FormRPTextPropertiesDefinitionController extends AFormDecoratorPropertiesDefinitionController<WidgetTesto, RPPropertiesDefinition, DecoratorRPPropertiesDefinition, BoxRPAdditionalFieldStorage, TabRPAdditionalFieldStorage> {

	
	
	public FormRPTextPropertiesDefinitionController(Class<RPPropertiesDefinition> targetModel, Class<WidgetTesto> renderingModel) {
		super(targetModel, renderingModel);
	}
	
	
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		String boxId = request.getParameter("boxId");
		String tabId = request.getParameter("tabId");
		DecoratorRPPropertiesDefinition object = (DecoratorRPPropertiesDefinition) command;
		getApplicationService().saveOrUpdate(DecoratorRPPropertiesDefinition.class, object);
		return new ModelAndView(getSuccessView()+"?id="+boxId+"&tabId="+tabId);
		
	}
	
}
