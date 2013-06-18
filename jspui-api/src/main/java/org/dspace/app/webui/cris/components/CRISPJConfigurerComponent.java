/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package org.dspace.app.webui.cris.components;

import org.apache.log4j.Logger;
import org.dspace.app.cris.discovery.CrisSearchService;
import org.dspace.app.cris.integration.statistics.IStatsComponent;
import org.dspace.app.cris.model.CrisConstants;
import org.dspace.app.webui.cris.components.statistics.ASolrStatsConfigurerComponent;
import org.dspace.app.webui.cris.components.statistics.CrisStatDownloadTopObjectComponent;
import org.dspace.app.webui.cris.components.statistics.CrisStatTopObjectComponent;
import org.dspace.app.webui.cris.components.statistics.CrisStatPJDownloadTopObjectComponent;
import org.dspace.content.DSpaceObject;

public class CRISPJConfigurerComponent extends
        BrowseItemConfigurerComponent
{

    /** log4j logger */
    private static Logger log = Logger
            .getLogger(CRISPJConfigurerComponent.class);


    @Override
    public IStatsComponent getStatsDownloadComponent()
    {
        CrisStatDownloadTopObjectComponent component = new CrisStatPJDownloadTopObjectComponent();

        BeanFacetComponent bean = new BeanFacetComponent();
        
        bean.setFacetQuery(ASolrStatsConfigurerComponent.FILE + ":*");
        bean.setFacetField(ASolrStatsConfigurerComponent.FILE);        
        bean.setQuery(getRelationConfiguration().getQuery());
        for(String key : getTypes().keySet()) {
            bean.getSubQueries().put(key, getTypes().get(key).getFacetQuery());
        }
        
        component.setFromField("search.uniqueid");
        component.setBean(bean);
        component.setTargetObjectClass(getTarget());
        component.setRelationObjectClass(getRelationObjectClass());
        component.setApplicationService(getApplicationService());
        try
        {
            component.setRelationObjectType(CrisConstants.getEntityType(getRelationObjectClass()));
        }
        catch (InstantiationException e)
        {
            log.error(e.getMessage(), e);
        }
        catch (IllegalAccessException e)
        {
            log.error(e.getMessage(), e);
        }
        component.setCrisSearchService((CrisSearchService) getSearchService());
        return component;

    }

    @Override
    public IStatsComponent getStatsViewComponent()
    {
        CrisStatTopObjectComponent component = new CrisStatTopObjectComponent();

        BeanComponent bean = new BeanComponent();
        bean.setQuery(getRelationConfiguration().getQuery());
        for(String key : getTypes().keySet()) {
            bean.getSubQueries().put(key, getTypes().get(key).getFacetQuery());
        }
        
        component.setBean(bean);
        component.setTargetObjectClass(getTarget());
        component.setRelationObjectClass(getRelationObjectClass());
        try
        {
            component.setRelationObjectType(CrisConstants.getEntityType(getRelationObjectClass()));
        }
        catch (InstantiationException e)
        {
            log.error(e.getMessage(), e);
        }
        catch (IllegalAccessException e)
        {
            log.error(e.getMessage(), e);
        }
        return component;
    }

    @Override
    public Class<? extends DSpaceObject> getRelationObjectClass()
    {
        return getRelationConfiguration().getRelationClass();
    }

    @Override
    public Integer getRelationObjectType()
    {
        try
        {
            return CrisConstants.getEntityType(getRelationObjectClass());
        }
        catch (InstantiationException e)
        {
            log.error(e.getMessage(), e);
        }
        catch (IllegalAccessException e)
        {
            log.error(e.getMessage(), e);
        }
        return null;
    }

}
