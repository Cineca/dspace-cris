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

import it.cilea.osd.jdyna.controller.BoxsController;
import it.cilea.osd.jdyna.web.Containable;
import it.cilea.osd.jdyna.web.IPropertyHolder;
import it.cilea.osd.jdyna.web.Tab;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

/**
 * Abstract SpringMVC controller is used to add common informations.
 * 
 * @author pascarelli
 * 
 */
public abstract class ABoxsController<H extends IPropertyHolder<Containable>, T extends Tab<H>> extends BoxsController<H, T> {

	
	private String specificPartPath;
	
	public ABoxsController(Class<H> boxClass) {
		super(boxClass);
	}
	
	public String getSpecificPartPath() {
		return specificPartPath;
	}

	public void setSpecificPartPath(String specificPartPath) {
		this.specificPartPath = specificPartPath;
	}

	@Override
	protected ModelAndView handleDetails(HttpServletRequest request) {
		
		ModelAndView mav = super.handleDetails(request);
		mav.getModel().put("specificPartPath", getSpecificPartPath());
		return mav;
		
	}
	
	@Override
	protected ModelAndView handleList(HttpServletRequest arg0) throws Exception {
		ModelAndView mav = super.handleList(arg0);
		mav.getModel().put("specificPartPath", getSpecificPartPath());
		return mav;		
	}
	
}
