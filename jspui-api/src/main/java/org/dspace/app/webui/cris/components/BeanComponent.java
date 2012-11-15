/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package org.dspace.app.webui.cris.components;

import it.cilea.osd.jdyna.components.IBeanComponent;

import java.util.ArrayList;
import java.util.List;


public class BeanComponent implements IBeanComponent
{
        
    private String componentIdentifier;
    
    private String query;
    
    private String order;
    
    private int rpp = Integer.MAX_VALUE;
    
    private int sortby = -1;
    
    private List<String> filters = new ArrayList<String>();

    private int etal = -1;
    
    public String getComponentIdentifier()
    {
        return componentIdentifier;
    }

    public void setComponentIdentifier(String componentIdentifier)
    {
        this.componentIdentifier = componentIdentifier;
    }

    public String getQuery()
    {
        return query;
    }

    public void setQuery(String query)
    {
        this.query = query;
    }

    public String getOrder()
    {
        return order;
    }

    public void setOrder(String order)
    {
        this.order = order;
    }

    public int getRpp()
    {        
        return rpp;
    }

    public void setRpp(int rpp)
    {
        this.rpp = rpp;
    }

    public int getSortby()
    {
        return sortby;
    }

    public void setSortby(int sortby)
    {
        this.sortby = sortby;
    }

    public List<String> getFilters()
    {
        return filters;
    }

    public void setFilters(List<String> filters)
    {
        this.filters = filters;
    }

    public void setEtal(int etal)
    {
        this.etal = etal;
    }

    public int getEtal()
    {
        return etal;
    }
    
}
