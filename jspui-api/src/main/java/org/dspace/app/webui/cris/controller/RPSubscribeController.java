/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package org.dspace.app.webui.cris.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dspace.app.cris.model.ResearcherPage;
import org.dspace.app.cris.service.ApplicationService;
import org.dspace.app.cris.service.RPSubscribeService;
import org.dspace.app.cris.util.ResearcherPageUtils;
import org.dspace.app.webui.util.UIUtil;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 * This SpringMVC controller allows a dspace user to subscribe alert notification
 * of updated or newly added item to a RP
 * 
 * @author cilea
 * 
 */
public class RPSubscribeController extends MultiActionController
{

    /**
     * the applicationService for query the RP db, injected by Spring IoC
     */  
    private ApplicationService applicationService;
    
    private RPSubscribeService rpSubService;

    private String viewName;
    
    public void setViewName(String viewName)
    {
        this.viewName = viewName;
    }
    
    public String getViewName()
    {
        return viewName;
    }
    
    public ModelAndView subscribe(HttpServletRequest arg0,
            HttpServletResponse arg1) throws Exception
    {                
        String id_s = arg0.getParameter("id");
        Integer id = null;
        if(id_s!=null && !id_s.isEmpty()) {
            id = Integer.parseInt(id_s);
        }
        else 
        {
            return null;
        }
        rpSubService.subscribe(UIUtil.obtainContext(arg0).getCurrentUser(), id);
        return new ModelAndView(getViewName()+ ResearcherPageUtils.getPersistentIdentifier(id, ResearcherPage.class)+"?subscribe=true");
    }

    public ModelAndView unsubscribe(HttpServletRequest arg0,
            HttpServletResponse arg1) throws Exception
    {                
        String id_s = arg0.getParameter("id");
        Integer id = null;
        if(id_s!=null && !id_s.isEmpty()) {
            id = Integer.parseInt(id_s);
        }
        else 
        {
            return null;
        }
        rpSubService.unsubscribe(UIUtil.obtainContext(arg0).getCurrentUser(), id);
        return new ModelAndView(getViewName()+ ResearcherPageUtils.getPersistentIdentifier(id, ResearcherPage.class)+"?subscribe=false");
    }

    public ApplicationService getApplicationService()
    {
        return applicationService;
    }

    public void setApplicationService(ApplicationService applicationService)
    {
        this.applicationService = applicationService;
    }
    
    public void setRpSubService(RPSubscribeService rpSubService)
    {
        this.rpSubService = rpSubService;
    }
    
    public RPSubscribeService getRpSubService()
    {
        return rpSubService;
    }
}
