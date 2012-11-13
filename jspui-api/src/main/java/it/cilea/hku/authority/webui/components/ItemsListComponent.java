/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package it.cilea.hku.authority.webui.components;

import it.cilea.hku.authority.model.ACrisObject;
import it.cilea.hku.authority.util.ResearcherPageUtils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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



    @Override
    public String getAuthority(HttpServletRequest request)
    {
        String result = (String) request.getAttribute("authority");
        if(StringUtils.isEmpty(result)) {
            Integer entityID = Integer.parseInt(String.valueOf(request.getAttribute("entityID")));
            return getAuthority(entityID);
        }
        return result;
    }



    @Override
    public String getAuthority(Integer id)
    {      
        return ResearcherPageUtils.getPersistentIdentifier(id, getTarget());
    }

  

}
