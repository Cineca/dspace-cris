/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package org.dspace.app.cris.dao;

import org.dspace.app.cris.model.dynamicfield.BoxResearcherPage;
import org.dspace.app.cris.model.dynamicfield.TabResearcherPage;

import it.cilea.osd.jdyna.dao.TabDao;



public interface RPTabDao extends TabDao<BoxResearcherPage,TabResearcherPage> {

}
