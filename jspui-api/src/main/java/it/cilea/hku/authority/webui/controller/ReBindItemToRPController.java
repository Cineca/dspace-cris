/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package it.cilea.hku.authority.webui.controller;

import it.cilea.hku.authority.dspace.BindItemToRP;
import it.cilea.hku.authority.model.ResearcherPage;
import it.cilea.hku.authority.service.ApplicationService;
import it.cilea.hku.authority.util.ResearcherPageUtils;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

/**
 * This SpringMVC controller allows the admin to execute the reBindItemToRP
 * script via WebUI on a RP basis
 * 
 * @see BindItemToRP#work(List, ApplicationService)
 * 
 * @author cilea
 * 
 */
public class ReBindItemToRPController extends ParameterizableViewController
{

    /**
     * the applicationService for query the RP db, injected by Spring IoC
     */  
    private ApplicationService applicationService;

    @Override
    public ModelAndView handleRequest(HttpServletRequest arg0,
            HttpServletResponse arg1) throws Exception
    {                
        String id_s = arg0.getParameter("id");
        Integer id = null;
        ResearcherPage researcher = null;
        if(id_s!=null && !id_s.isEmpty()) {
            id = Integer.parseInt(id_s);
            researcher = applicationService.get(ResearcherPage.class, id);
        }
        List<ResearcherPage> r = new LinkedList<ResearcherPage>();
        r.add(researcher);
        BindItemToRP.work(r, applicationService);
        return new ModelAndView(getViewName()+ ResearcherPageUtils.getPersistentIdentifier(researcher));
    }


    public ApplicationService getApplicationService()
    {
        return applicationService;
    }

    public void setApplicationService(ApplicationService applicationService)
    {
        this.applicationService = applicationService;
    }
}
