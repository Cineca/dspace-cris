package it.cilea.hku.authority.webui.components;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dspace.browse.BrowsableDSpaceObject;
import org.dspace.browse.BrowseDSpaceObject;
import org.dspace.browse.BrowseItem;
import org.dspace.content.DSpaceObject;
import org.dspace.core.Context;
import org.dspace.discovery.DiscoverResult;

public class ProjectListComponent extends ASolrConfigurerComponent<BrowseItem>
{

    /** log4j logger */
    private static Logger log = Logger.getLogger(ProjectListComponent.class);
 
    @Override
    public BrowseItem[] getObjectFromSolrResult(DiscoverResult docs, Context context)
            throws Exception
    {           
        BrowseItem[] result = new BrowseItem[docs.getDspaceObjects().size()];
        int i = 0;
        for (DSpaceObject obj : docs.getDspaceObjects())
        {             
            result[i] = new BrowseDSpaceObject(context, (BrowsableDSpaceObject) obj);
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

}