/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package org.dspace.app.webui.cris.controller;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dspace.app.cris.model.RPSubscription;
import org.dspace.app.cris.model.ResearcherPage;
import org.dspace.app.cris.service.ApplicationService;
import org.dspace.app.cris.util.ResearcherPageUtils;
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
