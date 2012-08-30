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

import it.cilea.hku.authority.model.ResearcherGrant;
import it.cilea.hku.authority.model.dynamicfield.BoxResearcherGrant;
import it.cilea.hku.authority.model.dynamicfield.GrantPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.GrantProperty;
import it.cilea.hku.authority.model.dynamicfield.TabResearcherGrant;
import it.cilea.hku.authority.service.ApplicationService;
import it.cilea.hku.authority.service.ExtendedTabService;
import it.cilea.osd.jdyna.model.AnagraficaSupport;
import it.cilea.osd.jdyna.web.IContainable;
import it.cilea.osd.jdyna.web.controller.SimpleDynaController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dspace.app.webui.util.Authenticate;
import org.dspace.app.webui.util.JSPManager;
import org.dspace.app.webui.util.UIUtil;
import org.dspace.authorize.AuthorizeException;
import org.dspace.authorize.AuthorizeManager;
import org.dspace.core.ConfigurationManager;
import org.dspace.core.Context;
import org.dspace.core.LogManager;
import org.springframework.web.servlet.ModelAndView;

/**
 * This SpringMVC controller is used to build the ResearcherPage details page.
 * The DSpace items included in the details are returned by the DSpace Browse
 * System.
 * 
 * @author cilea
 * 
 */
public class ResearcherGrantDetailsController
        extends
        SimpleDynaController<GrantProperty, GrantPropertiesDefinition, BoxResearcherGrant, TabResearcherGrant>
{

    public ResearcherGrantDetailsController(
            Class<ResearcherGrant> anagraficaObjectClass,
            Class<GrantPropertiesDefinition> classTP,
            Class<TabResearcherGrant> classT, Class<BoxResearcherGrant> classH)
            throws InstantiationException, IllegalAccessException
    {
        super(anagraficaObjectClass, classTP, classT, classH);
    }

    /** log4j category */
    private static Logger log = Logger
            .getLogger(ResearcherGrantDetailsController.class);

    @Override
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        Map<String, Object> model = new HashMap<String, Object>();
        ResearcherGrant grant = null;
        String id = request.getParameter("id");
        if (id != null && !id.isEmpty())
        {
            Integer objectId = Integer.parseInt(id);
            if (objectId == -1)
            {
                String projectCode = request.getParameter("code");
                if (projectCode != null && !projectCode.isEmpty())
                {
                    grant = ((ApplicationService) applicationService)
                            .getResearcherGrantByCode(projectCode);
                }
                else
                {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND,
                            "Grant page not found");
                    return null;
                }
            }
            else
            {
                try
                {
                    grant = applicationService.get(ResearcherGrant.class,
                            objectId);
                }
                catch (NumberFormatException e)
                {
                }
            }
        }
        else
        {
            response.sendError(HttpServletResponse.SC_NOT_FOUND,
                    "Grant page not found");
            return null;
        }

        if (grant == null)
        {
            response.sendError(HttpServletResponse.SC_NOT_FOUND,
                    "Grant page not found");
            return null;
        }

        model.put("grant", grant);
        Context context = UIUtil.obtainContext(request);

        if ((grant.getStatus() == null || grant.getStatus().booleanValue() == false)
                && !AuthorizeManager.isAdmin(context))
        {
            if (context.getCurrentUser() != null
                    || Authenticate.startAuthentication(context, request,
                            response))
            {
                // Log the error
                log.info(LogManager
                        .getHeader(context, "authorize_error",
                                "Only system administrator can access to disabled researcher page"));

                JSPManager
                        .showAuthorizeError(
                                request,
                                response,
                                new AuthorizeException(
                                        "Only system administrator can access to disabled researcher page"));
            }
            return null;
        }

        if (AuthorizeManager.isAdmin(context))
        {
            model.put("grant_page_menu", new Boolean(true));
        }

        ModelAndView mvc = super.handleDetails(request);

        List<IContainable> pDInTab = (List<IContainable>) mvc.getModel().get(
                "propertiesDefinitionsInTab");
        Map<String, List<IContainable>> mapBoxToContainables = (Map<String, List<IContainable>>) mvc
                .getModel().get("propertiesDefinitionsInHolder");
        for (String boxShortName : mapBoxToContainables.keySet())
        {
            List<IContainable> temp = new LinkedList<IContainable>();
            ((ExtendedTabService) applicationService)
                    .findOtherContainablesInBoxByConfiguration(boxShortName,
                            temp);
            pDInTab.addAll(temp);
            mapBoxToContainables.get(boxShortName).addAll(temp);

        }

        mvc.getModel().put("propertiesDefinitionsInHolder",
                mapBoxToContainables);

        mvc.getModel().putAll(model);

        String configurationTextToLink = ConfigurationManager
                .getProperty("researchergrant.display.link");
        List<String> textToLink = new ArrayList<String>();
        if (configurationTextToLink != null)
        {
            for (String s : configurationTextToLink.split(","))
            {
                textToLink.add(s.trim());
            }
        }
        mvc.getModel().put("changedToLink", textToLink);
        return mvc;
    }

    @Override
    protected List<TabResearcherGrant> findTabsWithVisibility(
            HttpServletRequest request) throws SQLException
    {

        // check admin authorization
        Boolean isAdmin = null; // anonymous access
        Context context = UIUtil.obtainContext(request);

        if (AuthorizeManager.isAdmin(context))
        {
            isAdmin = true; // admin
        }

        List<TabResearcherGrant> tabs = applicationService.getTabsByVisibility(
                TabResearcherGrant.class, isAdmin);

        return tabs;
    }

}
