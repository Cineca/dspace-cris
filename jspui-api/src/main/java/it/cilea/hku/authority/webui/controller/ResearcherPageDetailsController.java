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
import it.cilea.hku.authority.model.dynamicfield.EditTabRPAdditionalFieldStorage;
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
import it.cilea.osd.jdyna.model.IContainable;
import it.cilea.osd.jdyna.web.Box;
import it.cilea.osd.jdyna.web.controller.SimpleDynaController;

import java.sql.SQLException;
import java.util.ArrayList;
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
        	
        	
            researcher = ((ApplicationService)applicationService).get(ResearcherPage.class, objectId);
            
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

        String tabName = extractTabName(request);
        if (tabName == null)
        {
            tabName = findTabsWithVisibility(request).get(0).getShortName();
        }

        model.put("researcher", researcher);
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
        ModelAndView mvc = null;
        try
        {
        	
            mvc = super.handleDetails(request);
            
        }
        catch (Exception e)
        {
            // TODO: Hack to redirect page if all the box are disabled after an
            // edit, please fix.
            // JSPManager.showAuthorizeError(request, response, new
            // AuthorizeException(e.getMessage()));
            response.sendRedirect("/rp/"
                    + ResearcherPageUtils.getPersistentIdentifier(researcher));
            return null;
        }

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
            if (components != null)
            {
                IRPComponent comp = components.get(boxShortName);
                if (comp != null)
                {
                    comp.evalute(request, response);
                }
            }
        }

        // retrieve sub-page active links
        List<TabRPAdditionalFieldStorage> tabs = (List<TabRPAdditionalFieldStorage>) mvc
                .getModel().get("tabList");

        Map<String, List<String[]>> navigation = new HashMap<String, List<String[]>>();
        List<String[]> sublinkstoexport = new ArrayList<String[]>();
        for (TabRPAdditionalFieldStorage tab : tabs)
        {
            List<String[]> sublinks = new ArrayList<String[]>();

            for (BoxRPAdditionalFieldStorage box : tab.getMask())
            {
                IRPComponent comp = null;
                if(components!=null) {
                    comp = components.get(box.getShortName());
                }                
                if (comp != null)
                {
                    List<String[]> compSubLinks = comp.sublinks(request,
                            response);
                    sublinks.addAll(compSubLinks);
                    sublinkstoexport.addAll(compSubLinks);
                }
                else
                {
                    if (!box.isUnrelevant()
                            && !ResearcherTagLibraryFunctions.isBoxHidden(
                                    researcher, box))
                    {
                        int countBoxPublicMetadata = ResearcherTagLibraryFunctions
                                .countBoxPublicMetadata(researcher, box, true);
                        sublinks.add(new String[] {
                                box.getShortName(),
                                box.getTitle()
                                        + (countBoxPublicMetadata == 0 ? ""
                                                : " (" + countBoxPublicMetadata
                                                        + ")"),
                                box.getShortName() });
                    }
                }
            }
            
            navigation.put(tab.getShortName(), sublinks);
        }
        model.put("navigation", navigation);
        String openbox = extractAnchorId(request);
        for(Box box : (List<Box>)mvc.getModel().get("propertiesHolders")) {
            if(box.getShortName().equals(openbox)) {
                if(box.isCollapsed()) {
                    box.setCollapsed(false);
                    break;
                }
            }            
        }
        model.put("sublinktoexport", sublinkstoexport);
        model.put("exportscitations",
                ConfigurationManager.getProperty("exportcitation.options"));
        mvc.getModel().put("propertiesDefinitionsInTab", pDInTab);
        mvc.getModel().put("propertiesDefinitionsInHolder",
                mapBoxToContainables);
        EditTabRPAdditionalFieldStorage fuzzyEditTab = applicationService
                .getTabByShortName(EditTabRPAdditionalFieldStorage.class,
                        "edit" + tabName);
        mvc.getModel().put("tabIdForRedirect",
                fuzzyEditTab != null ? fuzzyEditTab.getId() : "");

        mvc.getModel()
                .put("showStatsOnlyAdmin",
                        ConfigurationManager
                                .getBooleanProperty("statistics.item.authorization.admin"));
        mvc.getModel().putAll(model);

        log.debug("end servlet handleRequest");
        return mvc;
    }

    @Override
    protected List<TabRPAdditionalFieldStorage> findTabsWithVisibility(
            HttpServletRequest request)
            throws SQLException
    {
        Integer researcherId = extractResearcherId(request);
        ResearcherPage researcher = null;
        try
        {
            researcher = ((ApplicationService)applicationService).get(ResearcherPage.class, researcherId);
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

        List<TabRPAdditionalFieldStorage> notEmptyTabs = new LinkedList<TabRPAdditionalFieldStorage>();

        for (TabRPAdditionalFieldStorage tab : tabs)
        {
            List<BoxRPAdditionalFieldStorage> boxs = tab.getMask();
            for (BoxRPAdditionalFieldStorage box : boxs)
            {
                if (!box.isUnrelevant()
                        && !ResearcherTagLibraryFunctions.isBoxHidden(
                                researcher, box))
                {
                    notEmptyTabs.add(tab);
                    break;
                }
            }
        }
        return notEmptyTabs;
    }

    @Override
    protected Integer getAnagraficaId(HttpServletRequest request)
    {
        Integer researcherId = extractResearcherId(request);
        ResearcherPage researcher = null;
        try
        {
            researcher = ((ApplicationService)applicationService).get(ResearcherPage.class, researcherId);
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
        if(publistFilters.contains(type)) {
            return "dspaceitems";
        }
        else {
            if(type!=null && !type.isEmpty()) {
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
