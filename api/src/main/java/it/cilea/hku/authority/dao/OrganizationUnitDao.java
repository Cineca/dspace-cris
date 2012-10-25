/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 * 
 * http://www.dspace.org/license/
 * 
 * The document has moved 
 * <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>
 */
package it.cilea.hku.authority.dao;

import it.cilea.hku.authority.model.OrganizationUnit;
import it.cilea.hku.authority.model.Project;
import it.cilea.osd.common.dao.PaginableObjectDao;

import java.io.Serializable;

/**
 * This interface define the methods available to retrieve Project
 * 
 * @author cilea
 * 
 */
public interface OrganizationUnitDao extends PaginableObjectDao<OrganizationUnit, Serializable>
{
 
    public OrganizationUnit uniqueBySourceID(String staffNo);

}
