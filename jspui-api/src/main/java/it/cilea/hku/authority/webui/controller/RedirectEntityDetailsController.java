/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package it.cilea.hku.authority.webui.controller;

import it.cilea.hku.authority.model.ACrisObject;
import it.cilea.hku.authority.service.ApplicationService;
import it.cilea.hku.authority.util.ResearcherPageUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

public class RedirectEntityDetailsController<T extends ACrisObject> extends
        ParameterizableViewController
{

    private ApplicationService applicationService;
    private Class<T> modelClass;
    
    /** log4j category */
    private static Logger log = Logger
            .getLogger(RedirectResearcherPageDetailsController.class);

    
    public RedirectEntityDetailsController(Class<T> modelClazz)
    {
        this.modelClass = modelClazz;
    }
    
    @Override
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {        
        String id = request.getParameter("id");
        T entity = null;
        if (id == null || id.isEmpty())
        {

            String modeCode = request.getParameter("code");
            if (modeCode != null && !modeCode.isEmpty())
            {
                entity = ((ApplicationService) applicationService)
                        .get(modelClass, modeCode);
            }
            else
            {
                String path = request.getPathInfo().substring(1); // remove
                                                                  // first /
                String[] splitted = path.split("/");
                request.setAttribute("authority", splitted[1]);
                entity = ((ApplicationService) applicationService)
                        .get(modelClass,ResearcherPageUtils.getRealPersistentIdentifier(splitted[1]));
                 
            }
        }
        else
        {
            try
            {
                entity = applicationService.get(modelClass,
                        Integer.parseInt(id));
            }
            catch (NumberFormatException e)
            {
                log.error(e.getMessage(), e);
            }
        }

        return new ModelAndView("redirect:"+ ResearcherPageUtils.getPersistentIdentifier(entity));     
    }

 


    public ApplicationService getApplicationService()
    {
        return applicationService;
    }

    public void setApplicationService(ApplicationService applicationService)
    {
        this.applicationService = applicationService;
    }

    public Class<T> getModelClass()
    {
        return modelClass;
    }

    public void setModelClass(Class<T> modelClass)
    {
        this.modelClass = modelClass;
    }
    
}
