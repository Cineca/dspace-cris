/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package org.dspace.app.cris.dao;

import java.util.List;

import org.dspace.app.cris.model.ResearchObject;
import org.dspace.app.cris.model.jdyna.DynamicObjectType;

/**
 * This interface define the methods available to retrieve ResearchObject
 * 
 * @author cilea
 * 
 */
public interface DynamicObjectDao extends CrisObjectDao<ResearchObject>        
{
    
    public ResearchObject uniqueBySourceID(String staffNo);

    public long countByType(DynamicObjectType typo);

    public List<ResearchObject> paginateByType(String sort, boolean inverse,
            int page, Integer pagesize, DynamicObjectType typo);
    
}
