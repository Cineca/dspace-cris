/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package it.cilea.hku.authority.webui.controller;

import it.cilea.hku.authority.model.CrisConstants;
import it.cilea.hku.authority.util.ResearcherPageUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

/**
 * This SpringMVC controller has been added to handle RP details URL also with
 * the form:<br> 
 * [hub-url]/rp/rp/details.html?id=[rpidentifier]
 * <br>
 * doing a simple redirect to the canonical URL: [hub-url]/rp/[rpidentifier] 
 * 
 * @author cilea
 * 
 */
public class RedirectResearcherPageDetailsController extends
        ParameterizableViewController
{

    /** log4j category */
    private static Logger log = Logger
            .getLogger(RedirectResearcherPageDetailsController.class);

    @Override
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {        
        String paramRPId = request.getParameter("id");
        return new ModelAndView("redirect:/cris/rp/"+"rp"+ResearcherPageUtils.getPersistentIdentifier(Integer.parseInt(paramRPId)));     
    }

 
}
