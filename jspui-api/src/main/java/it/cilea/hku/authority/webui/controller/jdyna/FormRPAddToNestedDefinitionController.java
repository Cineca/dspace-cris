package it.cilea.hku.authority.webui.controller.jdyna;

import it.cilea.hku.authority.model.dynamicfield.BoxResearcherPage;
import it.cilea.hku.authority.model.dynamicfield.DecoratorRPNestedPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.DecoratorRPPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.DecoratorRPTypeNested;
import it.cilea.hku.authority.model.dynamicfield.RPPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.TabResearcherPage;
import it.cilea.osd.jdyna.widget.WidgetDate;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;



public class FormRPAddToNestedDefinitionController extends AFormDecoratorPropertiesDefinitionController<WidgetDate, RPPropertiesDefinition, DecoratorRPPropertiesDefinition, BoxResearcherPage, TabResearcherPage> {

	
	
	
	
	public FormRPAddToNestedDefinitionController(Class<RPPropertiesDefinition> targetModel, Class<WidgetDate> renderingModel) {
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
		
	    DecoratorRPTypeNested nested = null;
		String rendering = request.getParameter("renderingparent");
		if (rendering != null) {
			nested = getApplicationService().get(DecoratorRPTypeNested.class, Integer.parseInt(rendering));			
		}
		DecoratorRPNestedPropertiesDefinition object = (DecoratorRPNestedPropertiesDefinition)super.formBackingObject(request);		
		nested.getReal().getMask().add(object.getReal());
		return object;
		
	}
	
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		String rendering = request.getParameter("renderingparent");
		String boxId = request.getParameter("boxId");
		String tabId = request.getParameter("tabId");
		DecoratorRPNestedPropertiesDefinition object = (DecoratorRPNestedPropertiesDefinition)command;
		getApplicationService().saveOrUpdate(DecoratorRPNestedPropertiesDefinition.class, object);		
		DecoratorRPTypeNested rPd = getApplicationService().get(DecoratorRPTypeNested.class, Integer.parseInt(rendering));
		return new ModelAndView(getSuccessView()+"?pDId="+rPd.getReal().getId()+"&boxId="+boxId+"&tabId="+tabId);
	}
}
