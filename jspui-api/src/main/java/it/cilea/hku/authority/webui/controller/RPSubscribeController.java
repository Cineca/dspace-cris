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

import it.cilea.hku.authority.service.ApplicationService;
import it.cilea.hku.authority.service.RPSubscribeService;
import it.cilea.hku.authority.util.ResearcherPageUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        return new ModelAndView(getViewName()+ ResearcherPageUtils.getPersistentIdentifier(id)+"?subscribe=true");
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
        return new ModelAndView(getViewName()+ ResearcherPageUtils.getPersistentIdentifier(id)+"?subscribe=false");
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
