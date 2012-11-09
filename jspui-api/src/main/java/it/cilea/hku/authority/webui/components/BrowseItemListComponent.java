/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package it.cilea.hku.authority.webui.components;

import it.cilea.hku.authority.webui.dto.ComponentInfoDTO;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dspace.browse.BrowsableDSpaceObject;
import org.dspace.browse.BrowseDSpaceObject;
import org.dspace.browse.BrowseItem;
import org.dspace.content.DSpaceObject;
import org.dspace.core.Context;
import org.dspace.discovery.DiscoverResult;
import org.dspace.sort.SortOption;

public class BrowseItemListComponent extends
        ASolrConfigurerComponent<BrowseItem>
{

    /** log4j logger */
    private static Logger log = Logger.getLogger(BrowseItemListComponent.class);

    @Override
    public BrowseItem[] getObjectFromSolrResult(DiscoverResult docs,
            Context context) throws Exception
    {
        BrowseItem[] result = new BrowseItem[docs.getDspaceObjects().size()];
        int i = 0;
        for (DSpaceObject obj : docs.getDspaceObjects())
        {
            result[i] = new BrowseDSpaceObject(context,
                    (BrowsableDSpaceObject) obj);
            i++;
        }
        return result;
    }

    @Override
    public String getAuthority(HttpServletRequest request)
    {
        return String.valueOf(request.getAttribute("entityID"));

    }

    @Override
    public String getAuthority(Integer id)
    {
        return String.valueOf(id);
    }

    @Override
    public ComponentInfoDTO<BrowseItem> buildComponentInfo(DiscoverResult docs,
            Context context, String type, int start, String order, int rpp,
            int etAl, long docsNumFound, int pageTotal, int pageCurrent,
            int pageLast, int pageFirst, SortOption sortOption)
            throws Exception
    {
        ComponentInfoDTO<BrowseItem> componentInfo = new ComponentInfoDTO<BrowseItem>();
        componentInfo.setItems(getObjectFromSolrResult(docs, context));

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
}
