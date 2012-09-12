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

import it.cilea.hku.authority.model.ResearcherGrant;
import it.cilea.hku.authority.webui.dto.ResearcherGrantDTO;

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
public class FormAdministrationAddGrantController extends
		AFormResearcherPageController {
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		ResearcherGrantDTO grantDTO = (ResearcherGrantDTO) command;
		String code = grantDTO.getCode();
		ResearcherGrant grant = applicationService
				.getResearcherGrantByCode(code);
		if (code == null || code.isEmpty()) {
			return new ModelAndView(getSuccessView()
					+ "grants/administrator/index.htm?error=true");
		} else {
			if (grant != null) {

				return new ModelAndView(getSuccessView()
						+ "grants/administrator/index.htm?error=true");
			} else {
				grant = new ResearcherGrant();
				grant.setRgCode(code);
				grant.setStatus(false);
				
				applicationService.saveOrUpdate(ResearcherGrant.class, grant);
			}
		}
		return new ModelAndView(getSuccessView() + "grants/details.htm?id="+grant.getId());

	}
}
