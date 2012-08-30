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

import java.util.Map;

import it.cilea.osd.jdyna.controller.FormDecoratorPropertiesDefinitionController;
import it.cilea.osd.jdyna.model.AWidget;
import it.cilea.osd.jdyna.model.PropertiesDefinition;
import it.cilea.osd.jdyna.web.ADecoratorPropertiesDefinition;
import it.cilea.osd.jdyna.web.Containable;
import it.cilea.osd.jdyna.web.IPropertyHolder;
import it.cilea.osd.jdyna.web.Tab;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;



public abstract class AFormDecoratorPropertiesDefinitionController<W extends AWidget, TP extends PropertiesDefinition, DTP extends ADecoratorPropertiesDefinition<TP>, H extends IPropertyHolder<Containable>, T extends Tab<H>> extends FormDecoratorPropertiesDefinitionController<W ,TP, DTP, H, T> {
	
	public AFormDecoratorPropertiesDefinitionController(Class<TP> targetModel,
			Class<W> renderingModel) {
		super(targetModel, renderingModel);		
	}

	private String specificPartPath;	
	
	
	public String getSpecificPartPath() {
		return specificPartPath;
	}

	public void setSpecificPartPath(String specificPartPath) {
		this.specificPartPath = specificPartPath;
	}
	
	@Override
	protected Map referenceData(HttpServletRequest request, Object command,
			Errors errors) throws Exception {
		
		Map model = super.referenceData(request, command, errors);
		model.put("specificPartPath", getSpecificPartPath());
		return model;
		
	}
	
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		ModelAndView mav = super.onSubmit(request, response, command, errors);
		mav.getModel().put("specificPartPath", getSpecificPartPath());
		return mav;		
	}
}
