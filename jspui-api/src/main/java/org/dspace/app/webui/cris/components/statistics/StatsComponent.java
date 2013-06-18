package org.dspace.app.webui.cris.components.statistics;

import it.cilea.osd.jdyna.components.IBeanSubComponent;

import org.dspace.app.cris.integration.statistics.IStatsComponent;
import org.dspace.content.DSpaceObject;

public abstract class StatsComponent<T extends DSpaceObject> extends ASolrStatsConfigurerComponent implements IStatsComponent
{
    public static final String DOWNLOAD = "download";
    public static final String VIEW = "view";
    
    private IBeanSubComponent bean;    
    private Class<T> targetObjectClass;
    private Class<T> relationObjectClass;
    private Integer relationObjectType;
    
    public void setBean(IBeanSubComponent bean)
    {
        this.bean = bean;
    }

    public IBeanSubComponent getBean()
    {
        return bean;
    }

    public void setTargetObjectClass(Class<T> targetObjectClass)
    {
        this.targetObjectClass = targetObjectClass;
    }

    public Class<T> getTargetObjectClass()
    {
        return targetObjectClass;
    }

    public void setRelationObjectClass(Class<T> relationObjectClass)
    {
        this.relationObjectClass = relationObjectClass;
    }

    public Class<T> getRelationObjectClass()
    {
        return relationObjectClass;
    }

    public Integer getRelationObjectType()
    {
        return relationObjectType;
    }

    public void setRelationObjectType(Integer relationObjectType)
    {
        this.relationObjectType = relationObjectType;
    }
    
    public abstract String getMode();

}
