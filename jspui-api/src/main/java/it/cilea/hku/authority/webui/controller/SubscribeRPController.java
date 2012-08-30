/**
 * <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
 * <html><head>
 * <title>301 Moved Permanently</title>
 * </head><body>
 * <h1>Moved Permanently</h1>
 * <p>The document has moved <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>.</p>
 * </body></html>
 */
package it.cilea.hku.authority.webui.controller;

import it.cilea.hku.authority.model.RPSubscription;
import it.cilea.hku.authority.model.ResearcherPage;
import it.cilea.hku.authority.service.ApplicationService;
import it.cilea.hku.authority.util.ResearcherPageUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dspace.app.webui.util.Authenticate;
import org.dspace.app.webui.util.UIUtil;
import org.dspace.authenticate.AuthenticationManager;
import org.dspace.authorize.AuthorizeManager;
import org.dspace.core.Context;
import org.dspace.eperson.EPerson;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

/**
 * Redirect the user to his "home page" on a role basis
 * @author cilea
 *
 */
public class SubscribeRPController extends
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
        if (currUser == null && !Authenticate
                .startAuthentication(context, request, response))
        {
            return null;
        }

        String rpkey = request.getParameter("rpkey");
        String action = request.getParameter("action");
        ResearcherPage rp = applicationService.getResearcherByAuthorityKey(rpkey);
        RPSubscription rpSub = applicationService.getRPSubscription(currUser.getID(), rp);
        if ("unsubscribe".equalsIgnoreCase(action))
        {
            // was subscribed
            if (rpSub != null)
            {
                applicationService.delete(RPSubscription.class, rpSub.getId());
            }
        }
        else
        {
            // not yet subscribed
            if (rpSub == null)
            {
                rpSub = new RPSubscription();
                rpSub.setEpersonID(currUser.getID());
                rpSub.setRp(rp);
                applicationService.saveOrUpdate(RPSubscription.class, rpSub);
            }
        }
        
        response.sendRedirect(request.getContextPath() + "/rp/"
                + ResearcherPageUtils.getPersistentIdentifier(rp));
        return null;        
	}
}
