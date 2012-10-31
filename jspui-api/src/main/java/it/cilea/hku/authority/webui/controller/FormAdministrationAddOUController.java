/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 * 
 * http://www.dspace.org/license/
 * 
 * The document has moved 
 * <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>
 */
package it.cilea.hku.authority.webui.controller;

import it.cilea.hku.authority.model.OrganizationUnit;
import it.cilea.hku.authority.model.dynamicfield.OUPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.OUProperty;
import it.cilea.hku.authority.webui.dto.OrganizationUnitDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
public class FormAdministrationAddOUController extends
        AFormResearcherPageController
{
    @Override
    protected ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object command, BindException errors)
            throws Exception
    {
        OrganizationUnitDTO grantDTO = (OrganizationUnitDTO) command;
        String code = grantDTO.getCode();
        OrganizationUnit grant = null;
        if (code != null && !code.isEmpty())
        {
            grant = applicationService.getOrganizationUnitByCode(code);
            if (grant != null)
            {

                return new ModelAndView(getSuccessView()
                        + "administrator/index.htm?error=true");
            }
        }
        else
        {

            grant = new OrganizationUnit();
            grant.setSourceID(code);
            grant.setStatus(false);
            grant.getDynamicField().setOrganizationUnit(grant);
            OUProperty property = grant.getDynamicField().createProprieta(
                    applicationService.findPropertiesDefinitionByShortName(
                            OUPropertiesDefinition.class, "title"));
            property.getValue().setOggetto(
                    "Insert Organization Unit NAME here (" + code + ")");
            property.setVisibility(1);
            applicationService.saveOrUpdate(OrganizationUnit.class, grant);

        }
        return new ModelAndView(getSuccessView() + "details.htm?id="
                + grant.getId());

    }
}