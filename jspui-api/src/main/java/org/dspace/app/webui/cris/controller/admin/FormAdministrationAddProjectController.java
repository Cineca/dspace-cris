/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package org.dspace.app.webui.cris.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dspace.app.cris.model.Project;
import org.dspace.app.webui.cris.controller.BaseFormController;
import org.dspace.app.webui.cris.dto.ProjectDTO;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

/**
 * This SpringMVC controller is responsible to handle the creation of a new
 * ResearcherPage. The initialization of the DTO is done by the
 * {@link RPAdminController}
 * 
 * @author cilea
 * 
 */
public class FormAdministrationAddProjectController extends BaseFormController
{
    @Override
    protected ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object command, BindException errors)
            throws Exception
    {
        ProjectDTO grantDTO = (ProjectDTO) command;
        String code = grantDTO.getSourceID();
        Project grant = null;
        if (code != null && !code.isEmpty())
        {
            grant = applicationService.getResearcherGrantByCode(code);
            if (grant != null)
            {

                return new ModelAndView("redirect:/cris/ou/"
                        + "administrator/index.htm?error=true");
            }
        }
        else
        {

            grant = new Project();
            grant.setSourceID(code);
            grant.setStatus(false);
            grant.getDynamicField().setProject(grant);
            applicationService.saveOrUpdate(Project.class, grant);

        }
        return new ModelAndView(getSuccessView() + grant.getId());

    }
}
