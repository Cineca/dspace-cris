/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package org.dspace.app.webui.cris.components;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dspace.app.cris.model.ACrisObject;
import org.dspace.app.cris.util.ResearcherPageUtils;
import org.dspace.content.DSpaceObject;
import org.dspace.content.Item;
import org.dspace.core.Context;
import org.dspace.discovery.DiscoverResult;

public class ItemsListComponent extends ASolrConfigurerComponent<Item>
{

    /** log4j logger */
    private static Logger log = Logger.getLogger(ItemsListComponent.class);

   
 
    @Override
    public Item[] getObjectFromSolrResult(DiscoverResult docs, Context context)
            throws Exception
    {
        Item[] result = new Item[docs.getDspaceObjects().size()];
        int i = 0;
        for (DSpaceObject obj : docs.getDspaceObjects())
        {
            result[i] = (Item) obj;
            i++;
        }
        return result;
    }





  

}
