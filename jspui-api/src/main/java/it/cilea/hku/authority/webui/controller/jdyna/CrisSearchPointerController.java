package it.cilea.hku.authority.webui.controller.jdyna;

import it.cilea.hku.authority.discovery.CrisSearchService;
import it.cilea.hku.authority.model.ACrisObject;
import it.cilea.osd.jdyna.model.PropertiesDefinition;
import it.cilea.osd.jdyna.web.controller.SearchPointerController;
import it.cilea.osd.jdyna.web.tag.DisplayPointerTagLibrary;

import java.util.ArrayList;
import java.util.List;

import org.dspace.content.DSpaceObject;
import org.dspace.core.Context;
import org.dspace.discovery.DiscoverQuery;

public class CrisSearchPointerController extends SearchPointerController<PropertiesDefinition, ACrisObject>
{
            
    private CrisSearchService searchService;
    
    @Override
    protected List<SelectableDTO> getResult(Class<ACrisObject> target, String filtro, 
            String query, String expression) {
        Context context = null;
        List<SelectableDTO> results = new ArrayList<SelectableDTO>();
        try {
            context = new Context();
          
            List<DSpaceObject> objects = getSearchService().search(context, query, null, true, 0, Integer.MAX_VALUE, filtro);            
            for(DSpaceObject obj : objects) {
                ACrisObject real = (ACrisObject)obj;
                String display = (String)DisplayPointerTagLibrary.evaluate(obj, expression);
                SelectableDTO dto = new SelectableDTO(real.getIdentifyingValue(), display);
                results.add(dto);
            }    
            
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        finally {
            if(context!=null && context.isValid()) {
                context.abort();
            }
        }
        
        return results;
    }

    public void setSearchService(CrisSearchService searchService)
    {
        this.searchService = searchService;
    }

    public CrisSearchService getSearchService()
    {
        return searchService;
    }
   
 
}
