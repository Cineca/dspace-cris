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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.cilea.hku.authority.model.ResearcherPage;
import it.cilea.hku.authority.model.UserWS;
import it.cilea.hku.authority.service.ApplicationService;
import it.cilea.hku.authority.util.ResearcherPageUtils;
import it.cilea.osd.common.controller.BaseAbstractController;
import it.cilea.osd.jdyna.web.IContainable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dspace.app.webui.util.UIUtil;
import org.dspace.authorize.AuthorizeManager;
import org.dspace.core.Context;
import org.dspace.eperson.EPerson;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

/**
 * Controller to manage show details, list and delete for a web services User
 * 
 * @author cilea
 * 
 */
public class UsersWSController extends BaseAbstractController
{
    /**
     * the applicationService for query the RP db, injected by Spring IoC
     */
    private ApplicationService applicationService;

    public void setApplicationService(ApplicationService applicationService)
    {
        this.applicationService = applicationService;
    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ModelAndView retValue = null;
        if ("details".equals(method))
            retValue = handleDetails(request);
        else if ("delete".equals(method))
            retValue = handleDelete(request);
        else if ("list".equals(method))
            retValue = handleList(request);
        return retValue;
    }

        
    protected ModelAndView handleDetails(HttpServletRequest request) {
        
        Map<String, Object> model = new HashMap<String, Object>();
        String id = request.getParameter("id");        
        UserWS user = applicationService.get(UserWS.class, id);       
                        
        model.put("user", user);                
        return new ModelAndView(detailsView, model);

    }
    

    protected ModelAndView handleDelete(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<String, Object>();
        String id = request.getParameter("id");
        
        try {
            applicationService.delete(UserWS.class, Integer.parseInt(id));    
            saveMessage(request, getText("action.ws.deleted"));
        }
        catch (Exception e) {
            saveMessage(request, getText("action.ws.deleted.noSuccess"));          
        }
        return new ModelAndView(listView, model);
    }


    protected ModelAndView handleList(HttpServletRequest arg0) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();               
        model.put("listUsers", applicationService.getList(UserWS.class));
        return new ModelAndView(listView,model);
    }
}
