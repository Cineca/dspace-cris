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
import it.cilea.hku.authority.model.dynamicfield.widget.WidgetComboRPAdditional;
import it.cilea.osd.jdyna.widget.WidgetTesto;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;



public class FormRPAddTextToNestedPropertiesDefinitionController extends AFormDecoratorPropertiesDefinitionController<WidgetTesto, RPPropertiesDefinition, DecoratorRPPropertiesDefinition, BoxRPAdditionalFieldStorage, TabRPAdditionalFieldStorage> {

	
	
	
	
	public FormRPAddTextToNestedPropertiesDefinitionController(Class<RPPropertiesDefinition> targetModel, Class<WidgetTesto> renderingModel) {
		super(targetModel, renderingModel);
	}
	
	
	@Override
	protected Map referenceData(HttpServletRequest request) throws Exception {
		
		Map<String, Object> map =  super.referenceData(request);
		map.put("renderingparent", request.getParameter("renderingparent"));
		map.put("boxId", request.getParameter("boxId"));
		map.put("tabId", request.getParameter("tabId"));
		return map;
	}
	
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		
		WidgetComboRPAdditional nested = null;
		String rendering = request.getParameter("renderingparent");
		if (rendering != null) {
			nested = getApplicationService().get(WidgetComboRPAdditional.class, Integer.parseInt(rendering));			
		}
		DecoratorRPPropertiesDefinition object = (DecoratorRPPropertiesDefinition)super.formBackingObject(request);
		object.getReal().setTopLevel(false);
		nested.getSottoTipologie().add(object.getReal());
		return object;
		
	}
	
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		String rendering = request.getParameter("renderingparent");
		String boxId = request.getParameter("boxId");
		String tabId = request.getParameter("tabId");
		DecoratorRPPropertiesDefinition object = (DecoratorRPPropertiesDefinition)command;
		getApplicationService().saveOrUpdate(object.getDecoratorClass(), object);
		RPPropertiesDefinition rPd = getApplicationService().getTipologiaProprietaComboWith(object.getReal(), getTargetModel());
		return new ModelAndView(getSuccessView()+"?pDId="+rPd.getId()+"&boxId="+boxId+"&tabId="+tabId);
	}
}
