package it.cilea.hku.authority.webui.components;

import it.cilea.hku.authority.discovery.CrisSearchService;
import it.cilea.hku.authority.webui.dto.ComponentInfoDTO;
import it.cilea.osd.jdyna.components.IBeanComponent;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dspace.app.webui.util.UIUtil;
import org.dspace.authorize.AuthorizeManager;
import org.dspace.content.DSpaceObject;
import org.dspace.core.Context;
import org.dspace.core.I18nUtil;
import org.dspace.discovery.DiscoverQuery;
import org.dspace.discovery.DiscoverQuery.SORT_ORDER;
import org.dspace.discovery.DiscoverResult;
import org.dspace.discovery.SearchService;
import org.dspace.discovery.SearchServiceException;
import org.dspace.sort.SortOption;
import org.dspace.utils.DSpace;

public abstract class ASolrConfigurerComponent<T extends DSpaceObject>
        implements IRPComponent
{

    /** log4j logger */
    private static Logger log = Logger
            .getLogger(ASolrConfigurerComponent.class);

    DSpace dspace = new DSpace();

    private SearchService searcher;

    private Map<String, IBeanComponent> types = new HashMap<String, IBeanComponent>();

    private String componentIdentifier;

    private int objectType;
    
    private String shortName;
    
    public List<String[]> addActiveTypeInRequest(HttpServletRequest request)
            throws Exception
    {
        String authority = getAuthority(request);
        Context c = UIUtil.obtainContext(request);
        List<String[]> subLinks = new ArrayList<String[]>();
        for (String type : types.keySet())
        {            
            DiscoverResult docs = search(c, type, authority, 0, 0, null, true);
            if (docs.getTotalSearchResults() > 0)
            {
                subLinks.add(new String[] {
                        type,
                        MessageFormat.format(I18nUtil.getMessage(
                                "jsp.layout.dspace.detail.fieldset-legend.component."
                                        + type, c), docs
                                .getTotalSearchResults()),
                        "" + docs.getTotalSearchResults() });
            }
        }
        request.setAttribute("activeTypes"+ getComponentIdentifier(), subLinks);
        return subLinks;
    }

    @Override
    public List<String[]> sublinks(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        List<String[]> subLinks = (List<String[]>) request
                .getAttribute("activeTypes"+getComponentIdentifier());
        if (subLinks == null)
        {
            return addActiveTypeInRequest(request);
        }
        return subLinks;
    }

    @Override
    public void evalute(HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        String authority = getAuthority(request);
        // Get the query from the box name
        String type = getType(request);
        List<String[]> activeTypes = addActiveTypeInRequest(request);

        int start = 0;

        int sortBy = -1;
        String order = "";
        int rpp = -1;
        int etAl = -1;
        String orderfield = "";
        boolean ascending = false; 
        
        Context context = UIUtil.obtainContext(request);
        DiscoverResult docs = null;
        long docsNumFound = 0;
        
        if (types.keySet().contains(type))
        {
            start = UIUtil.getIntParameter(request, "start" + getTypes().get(type).getComponentIdentifier());
            // can't start earlier than 0 in the results!
            if (start < 0)
            {
                start = 0;
            }
            sortBy = getSortBy(request, type);
            order = getOrder(request, type);
            rpp = getRPP(request, type);
            etAl = getEtAl(request, type);
            orderfield = sortBy != -1 ? "bi_sort_" + sortBy +"_sort": null;
            ascending = SortOption.ASCENDING.equalsIgnoreCase(order);

            // Perform the search
            
            docs = search(context, type, authority, start, rpp, orderfield,
                    ascending);
            if (docs != null)
            {
                docsNumFound = docs.getTotalSearchResults();
            }
        }

        if ((docs == null || docsNumFound == 0) && activeTypes.size() > 0)
        {
            type = activeTypes.get(0)[0];
            sortBy = getSortBy(request, type);
            order = getOrder(request, type);
            rpp = getRPP(request, type);
            etAl = getEtAl(request, type);
            orderfield = sortBy != -1 ? "bi_sort_" + sortBy +"_sort": null;
            ascending = SortOption.ASCENDING.equalsIgnoreCase(order);
            docs = search(context, type, authority, start, rpp, orderfield,
                    ascending);
            if (docs != null)
            {
                docsNumFound = docs.getTotalSearchResults();
            }
        }
       

        // Pass in some page qualities
        // total number of pages
        int pageTotal = 0;

        if (docs != null)
        {
            pageTotal = (int) (1 + ((docsNumFound - 1) / rpp));
        }
        // current page being displayed
        int pageCurrent = 1 + (start / rpp);

        // pageLast = min(pageCurrent+9,pageTotal)
        int pageLast = ((pageCurrent + 9) > pageTotal) ? pageTotal
                : (pageCurrent + 9);

        // pageFirst = max(1,pageCurrent-9)
        int pageFirst = ((pageCurrent - 9) > 1) ? (pageCurrent - 9) : 1;

        SortOption sortOption = null;
        if (sortBy > 0)
        {
            sortOption = SortOption.getSortOption(sortBy);
        }

        // Pass the results to the display JSP
           
        
        Map<String, ComponentInfoDTO<T>> componentInfoMap = (Map<String, ComponentInfoDTO<T>>)request.getAttribute("componentinfomap");
        if(componentInfoMap==null || componentInfoMap.isEmpty()) {
            componentInfoMap = new HashMap<String, ComponentInfoDTO<T>>();
        }
        else {
            if(componentInfoMap.containsKey(getShortName())) {
                componentInfoMap.remove(getShortName());
            }
        }
               
        ComponentInfoDTO<T> componentInfo = buildComponentInfo(docs, context, type, start,
                order, rpp, etAl, docsNumFound, pageTotal,
                pageCurrent, pageLast, pageFirst, sortOption);

        componentInfoMap.put(getShortName(), componentInfo);
        request.setAttribute("componentinfomap", componentInfoMap);
        
        if (AuthorizeManager.isAdmin(context))
        {
            // Set a variable to create admin buttons
            request.setAttribute("admin_button", new Boolean(true));
        }
    }

    public ComponentInfoDTO<T> buildComponentInfo(DiscoverResult docs, Context context, String type, int start,
            String order, int rpp, int etAl, long docsNumFound,
            int pageTotal, int pageCurrent, int pageLast,
            int pageFirst, SortOption sortOption) throws Exception
    {
        ComponentInfoDTO<T> componentInfo = new ComponentInfoDTO<T>();
        if(docs!=null) {
            componentInfo.setItems(getObjectFromSolrResult(docs, context));
        }

        componentInfo.setPagetotal(pageTotal);
        componentInfo.setPagecurrent(pageCurrent);
        componentInfo.setPagelast(pageLast);
        componentInfo.setPagefirst(pageFirst);

        componentInfo.setOrder(order);
        componentInfo.setSo(sortOption);
        componentInfo.setStart(start);
        componentInfo.setRpp(rpp);
        componentInfo.setEtAl(etAl);
        componentInfo.setTotal(docsNumFound);
        componentInfo.setType(type);
        return componentInfo;
    }

    public abstract T[] getObjectFromSolrResult(DiscoverResult docs,
            Context context) throws Exception;

    public DiscoverResult search(Context context, String type,
            String authority, int start, int rpp, String orderfield,
            boolean ascending) throws SearchServiceException
    {
        // can't start earlier than 0 in the results!
        if (start < 0)
        {
            start = 0;
        }
        String query = getQuery(type, authority);
        List<String> filters = getFilters(type);

        DiscoverQuery solrQuery = new DiscoverQuery();
        solrQuery.addFilterQueries("NOT(withdrawn:true)",
                "search.resourcetype:" + getObjectType());
        solrQuery.setQuery(query);
        solrQuery.addSearchField("search.resourceid");
        solrQuery.addSearchField("search.resourcetype");
        solrQuery.setStart(start);
        solrQuery.setMaxResults(rpp);
        if (orderfield == null)
        {
            orderfield = "score";
        }
        solrQuery.setSortField(orderfield, ascending ? SORT_ORDER.asc
                : SORT_ORDER.desc);

        if (filters != null)
        {
            for (String filter : filters)
            {
                solrQuery.addFilterQueries(filter);
            }
        }

        return getSearcher().search(context, solrQuery);

    }
    

    public String getType(HttpServletRequest request)
    {
        String type = request.getParameter("open");
        if (type == null)
        {
            type = types.keySet().iterator().next();
        }
        return type;
    }

    public int getEtAl(HttpServletRequest request, String type)
    {
        int etAl = UIUtil.getIntParameter(request, "etAl"+ getComponentIdentifier());
        if (etAl == -1)
        {
            etAl = types.get(type).getEtal();
        }
        return etAl;
    }

    public int getRPP(HttpServletRequest request, String type)
    {
        int rpp = UIUtil.getIntParameter(request, "rpp"+ getComponentIdentifier());
        if (rpp == -1)
        {
            rpp = getTypes().get(type).getRpp();
        }
        return rpp;
    }

    public String getOrder(HttpServletRequest request, String type)
    {
        String order = request.getParameter("order"+ getComponentIdentifier());
        if (order == null)
        {
            order = getTypes().get(type).getOrder();
        }
        return order;
    }

    public int getSortBy(HttpServletRequest request, String type)
    {
        int sortBy = UIUtil.getIntParameter(request, "sort_by"+ getComponentIdentifier());
        if (sortBy == -1)
        {
            sortBy = getTypes().get(type).getSortby();
        }
        return sortBy;
    }

    public List<String> getFilters(String type)
    {
        return getTypes().get(type).getFilters();
    }

    public String getQuery(String type, String authority)
    {
        return MessageFormat.format(getTypes().get(type).getQuery(), authority);
    }

    public Map<String, IBeanComponent> getTypes()
    {
        return types;
    }

    public void setTypes(Map<String, IBeanComponent> types)
    {
        this.types = types;
    }

    public SearchService getSearcher()
    {
        if (searcher == null)
        {
            searcher = dspace.getServiceManager().getServiceByName(
                    SearchService.class.getName(), CrisSearchService.class);
        }
        return searcher;
    }

    public long count(String type, Integer id)
    {
        Context context = null;

        try
        {
            context = new Context();

            String query = getQuery(type, getAuthority(id));
            List<String> filters = getFilters(type);
            DiscoverQuery solrQuery = new DiscoverQuery();
            solrQuery.addFilterQueries("NOT(withdrawn:true)",
                    "search.resourcetype:" + getObjectType());
            solrQuery.setQuery(query);
            solrQuery.addSearchField("search.resourceid");
            solrQuery.addSearchField("search.resourcetype");

            if (filters != null)
            {
                for (String filter : filters)
                {
                    solrQuery.addFilterQueries(filter);
                }
            }
            return getSearcher().search(context, solrQuery)
                    .getTotalSearchResults();
        }

        catch (Exception ex)
        {
            log.error(ex.getMessage(), ex);
        }
        finally
        {
            if (context != null && context.isValid())
            {
                context.abort();
            }
        }
        return -1;
    }
    

    
    

    public String getComponentIdentifier()
    {
        return componentIdentifier;
    }
    
    public int getObjectType()
    {
        return objectType;
    }

    public void setComponentIdentifier(String componentIdentifier)
    {
        this.componentIdentifier = componentIdentifier;
    }

    public void setObjectType(int objectType)
    {
        this.objectType = objectType;
    }

    public abstract String getAuthority(HttpServletRequest request);
    public abstract String getAuthority(Integer id);
    
    @Override
    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }

    @Override
    public String getShortName()
    {      
        return this.shortName;
    }

}
