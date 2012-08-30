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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

/**
 * This SpringMVC controller has been added to handle RP details URL also with
 * the form:<br> 
 * [hub-url]/rp/rp/details.html?persistentIdentifier=[rpidentifier]
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
        String paramRPId = request.getParameter("persistentIdentifier");
        return new ModelAndView("redirect:/rp/"+paramRPId);     
    }

 
}
