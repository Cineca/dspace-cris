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

import it.cilea.hku.authority.model.ResearcherPage;
import it.cilea.hku.authority.model.dynamicfield.RPPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.RPProperty;
import it.cilea.hku.authority.util.ResearcherPageUtils;
import it.cilea.hku.authority.webui.dto.ResearcherPageDTO;

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
public class FormAdministrationAddResearcherController extends
        AFormResearcherPageController
{

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object command, BindException errors)
            throws Exception
    {
        ResearcherPageDTO researcherDTO = (ResearcherPageDTO) command;
        String staffNo = researcherDTO.getSourceID();
        ResearcherPage researcher = null;
        if (staffNo != null && !staffNo.isEmpty())
        {
            researcher = applicationService
                    .getResearcherPageByStaffNo(researcherDTO.getSourceID());

            if (researcher != null)
            {

                return new ModelAndView(getSuccessView()
                        + "administrator/index.htm?error=true");
            }

        }

        else
        {
            researcher = new ResearcherPage();
            researcher.setSourceID(staffNo);
            researcher.setStatus(false);
            researcher.getDynamicField().setResearcherPage(researcher);
            RPProperty property = researcher.getDynamicField().createProprieta(
                    applicationService.findPropertiesDefinitionByShortName(
                            RPPropertiesDefinition.class, "fullName"));
            property.getValue().setOggetto(
                    "Insert fullname here (" + staffNo + ")");
            property.setVisibility(1);
            applicationService.saveOrUpdate(ResearcherPage.class, researcher);

        }
        return new ModelAndView(getSuccessView()
                + ResearcherPageUtils.getPersistentIdentifier(researcher));

    }
}
