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
import it.cilea.hku.authority.model.dynamicfield.DecoratorRPPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.RPPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.TabRPAdditionalFieldStorage;
import it.cilea.osd.jdyna.widget.WidgetDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;



public class FormRPDatePropertiesDefinitionController extends AFormDecoratorPropertiesDefinitionController<WidgetDate, RPPropertiesDefinition, DecoratorRPPropertiesDefinition, BoxRPAdditionalFieldStorage, TabRPAdditionalFieldStorage> {

	
	
	public FormRPDatePropertiesDefinitionController(Class<RPPropertiesDefinition> targetModel, Class<WidgetDate> renderingModel) {
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
