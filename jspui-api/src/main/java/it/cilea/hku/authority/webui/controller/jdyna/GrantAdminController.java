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
import it.cilea.hku.authority.service.ApplicationService;
import it.cilea.hku.authority.webui.dto.ResearcherGrantDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

/**
 * Concrete SpringMVC controller is used to list admin RG page
 * 
 * @author pascarelli
 * 
 */
public class GrantAdminController
		extends
		ParameterizableViewController {
	protected Log log = LogFactory.getLog(getClass());
	
	protected ApplicationService applicationService;
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ModelAndView mav = super.handleRequest(request, response);
		try {
			mav.getModel().put("onlyOneBox", applicationService.getList(BoxResearcherGrant.class).get(0).getId());		
		}
		catch(Exception e) {
			mav.getModel().put("error", "error.onlyoneboxmode.notsupported");
			log.error("No onlyOneBox mode supported... nothing boxes found"); 
		}
		String errore = request.getParameter("error");
		ResearcherGrantDTO grantDTO = new ResearcherGrantDTO();
		if(errore!=null && Boolean.parseBoolean(errore)==true) {
			//errore			
			mav.getModel().put("error", "jsp.dspace-admin.hku.error.add-grant");
		}		
		mav.getModel().put("dto", grantDTO);
		return mav;
	}

	public ApplicationService getApplicationService() {
		return applicationService;
	}

	public void setApplicationService(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}
	
}
