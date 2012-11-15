/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package it.cilea.hku.authority.webui.components;

import org.apache.log4j.Logger;
import org.dspace.browse.BrowsableDSpaceObject;
import org.dspace.browse.BrowseDSpaceObject;
import org.dspace.browse.BrowseItem;
import org.dspace.content.DSpaceObject;
import org.dspace.core.Context;
import org.dspace.discovery.DiscoverResult;

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

  
}
