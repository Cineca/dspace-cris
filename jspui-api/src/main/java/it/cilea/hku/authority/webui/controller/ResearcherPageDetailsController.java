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

import it.cilea.hku.authority.dspace.HKUAuthority;
import it.cilea.hku.authority.model.ResearcherPage;
import it.cilea.hku.authority.model.dynamicfield.BoxRPAdditionalFieldStorage;
import it.cilea.hku.authority.model.dynamicfield.RPAdditionalFieldStorage;
import it.cilea.hku.authority.model.dynamicfield.RPPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.RPProperty;
import it.cilea.hku.authority.model.dynamicfield.TabRPAdditionalFieldStorage;
import it.cilea.hku.authority.service.ApplicationService;
import it.cilea.hku.authority.service.ExtendedTabService;
import it.cilea.hku.authority.service.RPSubscribeService;
import it.cilea.hku.authority.util.ResearcherPageUtils;
import it.cilea.hku.authority.webui.components.IRPComponent;
import it.cilea.hku.authority.webui.web.tag.ResearcherTagLibraryFunctions;
import it.cilea.osd.jdyna.model.Containable;
import it.cilea.osd.jdyna.model.IContainable;
import it.cilea.osd.jdyna.web.Box;
import it.cilea.osd.jdyna.web.controller.SimpleDynaController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dspace.app.webui.util.Authenticate;
import org.dspace.app.webui.util.JSPManager;
import org.dspace.app.webui.util.UIUtil;
import org.dspace.authorize.AuthorizeException;
import org.dspace.authorize.AuthorizeManager;
import org.dspace.content.authority.AuthorityDAO;
import org.dspace.content.authority.AuthorityDAOFactory;
import org.dspace.core.ConfigurationManager;
import org.dspace.core.Context;
import org.dspace.core.LogManager;
import org.dspace.eperson.EPerson;
import org.springframework.web.servlet.ModelAndView;

/**
 * This SpringMVC controller is used to build the ResearcherPage details page.
 * The DSpace items included in the details are returned by the DSpace Browse
 * System.
 * 
 * @author cilea
 * 
 */
public class ResearcherPageDetailsController
        extends
        SimpleDynaController<RPProperty, RPPropertiesDefinition, BoxRPAdditionalFieldStorage, TabRPAdditionalFieldStorage>
{

    public ResearcherPageDetailsController(
            Class<RPAdditionalFieldStorage> anagraficaObjectClass,
            Class<RPPropertiesDefinition> classTP,
            Class<TabRPAdditionalFieldStorage> classT,
            Class<BoxRPAdditionalFieldStorage> classH)
            throws InstantiationException, IllegalAccessException
    {
        super(anagraficaObjectClass, classTP, classT, classH);
        publistFilters = new ArrayList<String>();
        String[] typesConf = ConfigurationManager.getProperty(
                "researcherpage.publicationlist.menu").split(",");
        for (String type : typesConf)
        {
            publistFilters.add(type.trim());
        }
    }

    private Map<String, IRPComponent> components;

    private List<String> publistFilters;

    /** log4j category */
    private static Logger log = Logger
            .getLogger(ResearcherPageDetailsController.class);

    private RPSubscribeService rpSubscribeService;

    public void setRpSubscribeService(RPSubscribeService rpSubscribeService)
    {
        this.rpSubscribeService = rpSubscribeService;
    }

    public void setComponents(Map<String, IRPComponent> components)
    {
        this.components = components;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        log.debug("Start handleRequest");
        Map<String, Object> model = new HashMap<String, Object>();

        Integer objectId = extractResearcherId(request);
        if (objectId == -1)
        {
            response.sendError(HttpServletResponse.SC_NOT_FOUND,
                    "Researcher page not found");
            return null;
        }

        ResearcherPage researcher = null;
        try
        {

            researcher = ((ApplicationService) applicationService).get(
                    ResearcherPage.class, objectId);

        }
        catch (NumberFormatException e)
        {
        }

        if (researcher == null)
        {
            response.sendError(HttpServletResponse.SC_NOT_FOUND,
                    "Researcher page not found");
            return null;
        }

               
        Context context = UIUtil.obtainContext(request);
        EPerson currUser = context.getCurrentUser();

        if ((researcher.getStatus() == null || researcher.getStatus()
                .booleanValue() == false) && !AuthorizeManager.isAdmin(context))
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

        if (AuthorizeManager.isAdmin(context)
                || (currUser != null && researcher.getStaffNo().equals(
                        currUser.getNetid())))
        {
            model.put("researcher_page_menu", new Boolean(true));
            model.put("authority_key",
                    ResearcherPageUtils.getPersistentIdentifier(researcher));

            if (AuthorizeManager.isAdmin(context))
            {
                AuthorityDAO dao = AuthorityDAOFactory.getInstance(context);
                long pendingItems = dao
                        .countIssuedItemsByAuthorityValueInAuthority(
                                HKUAuthority.HKU_AUTHORITY_MODE,
                                ResearcherPageUtils
                                        .getPersistentIdentifier(researcher));
                model.put("pendingItems", new Long(pendingItems));
            }
        }
        if (rpSubscribeService != null)
        {
            boolean subscribed = rpSubscribeService.isSubscribed(currUser,
                    researcher);
            model.put("subscribed", subscribed);
        }
        // this map contains key-values pairs, key = box shortname and values =
        // collection of metadata
        Map<String, List<IContainable>> mapBoxToContainables = new HashMap<String, List<IContainable>>();
        Map<String, Map<String,IContainable>> mapBoxToMapContainables = new HashMap<String, Map<String,IContainable>>();
        List<IContainable> pDInTab = new LinkedList<IContainable>();
        List<BoxRPAdditionalFieldStorage> propertyHolders = new LinkedList<BoxRPAdditionalFieldStorage>();
        List<TabRPAdditionalFieldStorage> tabs = findTabsWithVisibility(request, model, response);
        Integer tabId = getTabId(request);
        try
        {
            
            TabRPAdditionalFieldStorage t = null; 
                
            if (tabId == null)
            {                
                if(tabs!=null && !tabs.isEmpty()) {
                    t = tabs.get(0);
                    tabId = t.getId();
                }
            }
            else {
                t = applicationService.get(tabClass,
                        tabId);                
            }

            if (tabId == null)
            {
                throw new RuntimeException(
                        "No tabs to display contact administrator");
            }

      
            if (!tabs.contains(t))
            {
                throw new RuntimeException(
                        "You not have needed authorization level to display this tab");
            }

            // collection of boxs
            propertyHolders = t.getMask();

            String openbox = extractAnchorId(request);
            // this piece of code get containables object from boxs and put them
            // on map
            for (BoxRPAdditionalFieldStorage box : propertyHolders)
            {

                String boxShortName = box.getShortName();
                List<IContainable> temp = applicationService
                .<BoxRPAdditionalFieldStorage, TabRPAdditionalFieldStorage>findContainableInPropertyHolder(propertyHolderClass,
                        box.getId());       
                Map<String, IContainable> tempMap = new HashMap<String, IContainable>();
                ((ExtendedTabService) applicationService).findOtherContainablesInBoxByConfiguration(
                        box.getShortName(), temp,RPPropertiesDefinition.class.getName());
                if (components != null)
                {
                    IRPComponent comp = components.get(boxShortName);
                    if (comp != null)
                    {
                        comp.evalute(request, response);
                    }
                }

                if (box.getShortName().equals(openbox))
                {
                    if (box.isCollapsed())
                    {
                        box.setCollapsed(false);                        
                    }
                }
                
                for(IContainable tt : temp) {
                    tempMap.put(tt.getShortName(), tt);
                }           

                mapBoxToContainables.put(box.getShortName(), temp);
                mapBoxToMapContainables.put(box.getShortName(), tempMap);
                pDInTab.addAll(temp);
            }
            researcher.getDynamicField().inizializza();

        }
        catch (Exception e)
        {
            // TODO: Hack to redirect page if all the box are disabled after an
            // edit, please fix.
            // JSPManager.showAuthorizeError(request, response, new
            // AuthorizeException(e.getMessage()));
            log.error(e.getMessage(), e);           
            response.sendRedirect("/rp/"
                    + ResearcherPageUtils.getPersistentIdentifier(researcher));
            return null;
        }
        
        Collections.sort(propertyHolders);
        model.put("propertiesHolders", propertyHolders);
        model.put("propertiesDefinitionsInHolder", mapBoxToContainables);
        model.put("mapPropertiesDefinitionsInHolder", mapBoxToMapContainables);
        model.put("tabList", tabs);
        model.put("tabId", tabId);
        model.put("path", modelPath);
        model.put("anagraficaObject", researcher.getDynamicField());
        model.put("addModeType", "display");
        model.put("researcher", researcher);
        model.put("exportscitations",
                ConfigurationManager.getProperty("exportcitation.options"));

        model.put("showStatsOnlyAdmin", ConfigurationManager
                .getBooleanProperty("statistics.item.authorization.admin"));

        return new ModelAndView(detailsView, model);
    }

    @Override
    protected List<TabRPAdditionalFieldStorage> findTabsWithVisibility(
            HttpServletRequest request, Map<String, Object> model,
            HttpServletResponse response) throws Exception
    {
        Integer researcherId = extractResearcherId(request);
        ResearcherPage researcher = null;
        try
        {
            researcher = ((ApplicationService) applicationService).get(
                    ResearcherPage.class, researcherId);
        }
        catch (NumberFormatException e)
        {
            return null;
        }
        // check admin authorization
        Boolean isAdmin = null; // anonymous access
        Context context = UIUtil.obtainContext(request);
        EPerson currUser = context.getCurrentUser();
        if (AuthorizeManager.isAdmin(context))
        {
            isAdmin = true; // admin
        }
        else if ((currUser != null && researcher.getStaffNo().equals(
                currUser.getNetid())))
        {
            isAdmin = false; // owner
        }
        List<TabRPAdditionalFieldStorage> tabs = applicationService
                .getTabsByVisibility(TabRPAdditionalFieldStorage.class, isAdmin);
        return tabs;

       
    }

    @Override
    protected Integer getAnagraficaId(HttpServletRequest request)
    {
        Integer researcherId = extractResearcherId(request);
        ResearcherPage researcher = null;
        try
        {
            researcher = ((ApplicationService) applicationService).get(
                    ResearcherPage.class, researcherId);
        }
        catch (NumberFormatException e)
        {
            return -1;
        }
        return researcher.getDynamicField().getId();
    }

    @Override
    protected Integer getTabId(HttpServletRequest request)
    {
        String tabName = extractTabName(request);
        if (StringUtils.isNotEmpty(tabName))
        {
            TabRPAdditionalFieldStorage tab = applicationService
                    .getTabByShortName(TabRPAdditionalFieldStorage.class,
                            tabName);
            if (tab != null)
                return tab.getId();
        }
        return null;
    }

    private String extractAnchorId(HttpServletRequest request)
    {
        String type = request.getParameter("open");
        if (publistFilters.contains(type))
        {
            return "dspaceitems";
        }
        else
        {
            if (type != null && !type.isEmpty())
            {
                return type;
            }
        }
        return "";
    }

    private Integer extractResearcherId(HttpServletRequest request)
    {
        String path = request.getPathInfo().substring(1); // remove first /
        String[] splitted = path.split("/");
        request.setAttribute("authority", splitted[0]);
        return ResearcherPageUtils.getRealPersistentIdentifier(splitted[0]);
    }

    private String extractTabName(HttpServletRequest request)
    {
        String path = request.getPathInfo().substring(1); // remove first /
        String[] splitted = path.split("/");
        if (splitted.length > 1)
        {
            return splitted[1].replaceAll("\\.html", "");
        }
        else
            return null;
    }

}
