package org.dspace.app.cris.dao;

import it.cilea.osd.common.dao.PaginableObjectDao;

import org.dspace.app.cris.model.ACrisObject;

public interface CrisObjectDao<T extends ACrisObject> extends PaginableObjectDao<T, Integer>
{

    T uniqueByCrisID(String crisID);

    T uniqueByID(Integer rp);

    T uniqueBySourceID(String sourceID);

    
    
}
