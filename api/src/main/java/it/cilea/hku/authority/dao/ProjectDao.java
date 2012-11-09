/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package it.cilea.hku.authority.dao;

import it.cilea.hku.authority.model.Project;
import it.cilea.hku.authority.model.ResearcherPage;
import it.cilea.osd.common.dao.PaginableObjectDao;

import java.io.Serializable;

/**
 * This interface define the methods available to retrieve Project
 * 
 * @author cilea
 * 
 */
public interface ProjectDao extends PaginableObjectDao<Project, Serializable>
{

    public Project uniqueBySourceID(String staffNo);
    
}
