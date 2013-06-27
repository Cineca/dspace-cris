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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dspace.app.cris.model.jdyna.DynamicObjectType;
import org.dspace.app.cris.service.ApplicationService;
import org.dspace.app.webui.cris.dto.OrganizationUnitDTO;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

/**
 * Concrete SpringMVC controller is used to list admin OU page
 * 
 * @author pascarelli
 * 
 */
public class DOAdminController extends ParameterizableViewController
{
    protected Log log = LogFactory.getLog(getClass());

    protected ApplicationService applicationService;

    @Override
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {

        ModelAndView mav = super.handleRequest(request, response);     
       
        mav.getModel().put("researchobjects", applicationService.getList(DynamicObjectType.class));
        return mav;
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
