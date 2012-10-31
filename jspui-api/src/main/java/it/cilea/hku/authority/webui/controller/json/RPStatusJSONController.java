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
package it.cilea.hku.authority.webui.controller.json;

import flexjson.JSONSerializer;
import it.cilea.hku.authority.model.ResearcherPage;
import it.cilea.hku.authority.service.ApplicationService;
import it.cilea.hku.authority.util.ResearcherPageUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dspace.app.webui.util.UIUtil;
import org.dspace.core.Context;
import org.dspace.eperson.EPerson;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

/**
 * Retrieve data on the researcher profile of the logged user to be used in a
 * "My Researcher Page"
 * 
 * @author cilea
 * 
 */
public class RPStatusJSONController extends
		ParameterizableViewController {
    /**
     * the applicationService for query the RP db, injected by Spring IoC
     */   
    private ApplicationService applicationService;
    
    public void setApplicationService(ApplicationService applicationService)
    {
        this.applicationService = applicationService;
    }
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	    Context context = UIUtil.obtainContext(request);
	    EPerson currUser = context.getCurrentUser();
	    context.abort();
        if (currUser == null)
        {
            throw new ServletException(
                    "Wrong data or configuration: access to the my rp servlet without a valid user: there is no user logged in");
        }
        
        int id = currUser.getID();
        ResearcherPage rp = applicationService.getResearcherPageByEPersonId(id);
        RPStatusInformation info = new RPStatusInformation();
        if (rp != null)
        {
            info.setActive(rp.getStatus()!=null?rp.getStatus():false);
            info.setUrl("/cris/" + rp.getPublicPath()+ "/" +ResearcherPageUtils.getPersistentIdentifier(rp));
        }
        JSONSerializer serializer = new JSONSerializer();
        serializer.rootName("myrp");
        serializer.exclude("class");
        serializer.deepSerialize(info, response.getWriter());
        return null;
	}
	
	class RPStatusInformation {
	    private boolean active;
	    private String url;
        public boolean isActive()
        {
            return active;
        }
        public void setActive(boolean active)
        {
            this.active = active;
        }
        public String getUrl()
        {
            return url;
        }
        public void setUrl(String url)
        {
            this.url = url;
        }
	}
}
