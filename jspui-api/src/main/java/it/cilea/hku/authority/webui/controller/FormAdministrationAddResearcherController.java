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

import it.cilea.hku.authority.model.ResearcherPage;
import it.cilea.hku.authority.util.ResearcherPageUtils;
import it.cilea.hku.authority.webui.dto.ResearcherPageDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

/**
 * This SpringMVC controller is responsible to handle the creation of a new
 * ResearcherPage. The initialization of the DTO is done by the
 * {@link AdministrationController}
 * 
 * @author cilea
 * 
 */
public class FormAdministrationAddResearcherController extends
		AFormResearcherPageController {
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		ResearcherPageDTO researcherDTO = (ResearcherPageDTO) command;
		String staffNo = researcherDTO.getStaffNo();
		ResearcherPage researcher = applicationService
				.getResearcherPageByStaffNo(researcherDTO.getStaffNo());
		if (staffNo == null || staffNo.isEmpty()) {
			return new ModelAndView(getSuccessView()
					+ "administrator/index.htm?error=true");
		} else {
			if (researcher != null) {

				return new ModelAndView(getSuccessView()
						+ "administrator/index.htm?error=true");
			} else {
				researcher = new ResearcherPage();
				researcher.setStaffNo(staffNo);
				researcher.setStatus(false);
				researcher.setFullName("Insert name here (" + staffNo + ")");
				applicationService.saveOrUpdate(ResearcherPage.class,
						researcher);
			}
		}
		return new ModelAndView(getSuccessView()
				+ ResearcherPageUtils.getPersistentIdentifier(researcher));

	}
}
