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

import it.cilea.hku.authority.webui.dto.ResearcherPageDTO;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

/**
 * This SpringMVC controller is used to initialize the ResearcherPageDTO in the
 * "Add Researcher Page" functionality
 * 
 * @author cilea
 * 
 */
public class AdministrationController extends ParameterizableViewController {
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		String errore = arg0.getParameter("error");
		ResearcherPageDTO researcherDTO = new ResearcherPageDTO();
		if(errore!=null && Boolean.parseBoolean(errore)==true) {
			//errore			
			model.put("error", "jsp.dspace-admin.hku.error.add-researcher");
		}		
		model.put("dto", researcherDTO);
		return new ModelAndView(getViewName(),model);
	}
}
