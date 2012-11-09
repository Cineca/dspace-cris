/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package it.cilea.hku.authority.dao;

import it.cilea.hku.authority.model.dynamicfield.BoxOrganizationUnit;
import it.cilea.hku.authority.model.dynamicfield.EditTabOrganizationUnit;
import it.cilea.hku.authority.model.dynamicfield.TabOrganizationUnit;
import it.cilea.osd.jdyna.dao.EditTabDao;



public interface OUEditTabDao extends EditTabDao<BoxOrganizationUnit,TabOrganizationUnit,EditTabOrganizationUnit> {

	
}
