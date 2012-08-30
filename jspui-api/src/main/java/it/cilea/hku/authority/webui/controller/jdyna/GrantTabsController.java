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

import it.cilea.hku.authority.model.dynamicfield.BoxResearcherGrant;
import it.cilea.hku.authority.model.dynamicfield.EditTabResearcherGrant;
import it.cilea.hku.authority.model.dynamicfield.TabResearcherGrant;
import it.cilea.hku.authority.service.ApplicationService;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

/**
 * Concrete SpringMVC controller is used to list, delete and view detail of tab
 * 
 * @author pascarelli
 * 
 */
public class GrantTabsController extends ATabsController<BoxResearcherGrant, TabResearcherGrant> {

	
	
	public GrantTabsController(Class<TabResearcherGrant> tabsClass) {
		super(tabsClass);
	}


	@Override
	protected ModelAndView handleDelete(HttpServletRequest request) {
		
		String tabId = request.getParameter("id");
		Integer paramIntegerId = Integer.parseInt(tabId);
		((ApplicationService)getApplicationService()).decoupleEditTabByDisplayTab(paramIntegerId,EditTabResearcherGrant.class);
		return super.handleDelete(request);
		
		
	}

	
}
