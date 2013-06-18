package org.dspace.app.webui.cris.components;

import java.util.ArrayList;
import java.util.List;



public class BeanFacetComponent extends BeanComponent
{    
    @Override
    public List<String> getFilters()
    {     
        List<String> result = new ArrayList<String>();
        result.add(getFacetQuery());
        return result;
    }
 
}
