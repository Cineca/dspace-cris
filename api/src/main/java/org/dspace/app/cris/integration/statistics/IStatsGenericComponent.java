package org.dspace.app.cris.integration.statistics;

import org.dspace.content.DSpaceObject;

public interface IStatsGenericComponent<T extends DSpaceObject>
{
    public Class<T> getRelationObjectClass();
    public Integer getRelationObjectType();
    
}
