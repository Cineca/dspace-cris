/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * https://github.com/CILEA/dspace-cris/wiki/License
 */
package org.dspace.app.webui.cris.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dspace.app.cris.model.jdyna.DynamicObjectType;
import org.dspace.app.cris.service.ApplicationService;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 * This SpringMVC controller is responsible to handle building and editing of the CRIS 2nd level entities
 * 
 * @author pascarelli
 * 
 */
public class FormAdministrationTypeDOController extends SimpleFormController
{
    /**
     * the applicationService for query the RP db, injected by Spring IoC
     */
    private ApplicationService applicationService;

    @Override
    protected Object formBackingObject(HttpServletRequest request)
            throws Exception
    {
        String id_s = request.getParameter("id");
        DynamicObjectType object = null;
        if (id_s != null)
        {
            Integer id = Integer.parseInt(id_s);
            object = applicationService.get(DynamicObjectType.class, id);
        }
        else
        {
            object = new DynamicObjectType();
        }
        return object;
    }

    
    @Override
    protected ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object command, BindException errors)
            throws Exception
    {

        DynamicObjectType object = (DynamicObjectType) command;
        applicationService.saveOrUpdate(DynamicObjectType.class, object);
        return new ModelAndView(getSuccessView());
    }

    public void setApplicationService(ApplicationService applicationService)
    {
        this.applicationService = applicationService;
    }

}
