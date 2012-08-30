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

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import it.cilea.osd.jdyna.controller.FormBoxController;
import it.cilea.osd.jdyna.model.PropertiesDefinition;
import it.cilea.osd.jdyna.web.Containable;
import it.cilea.osd.jdyna.web.IPropertyHolder;
import it.cilea.osd.jdyna.web.Tab;

public class AFormBoxController<TP extends PropertiesDefinition, H extends IPropertyHolder<Containable>, T extends Tab<H>>
		extends FormBoxController<TP, H, T> {


	private String specificPartPath;
	
	
	public AFormBoxController(Class<H> clazzH, Class<TP> clazzTP) {
		super(clazzH, clazzTP);
	}
	

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

}
