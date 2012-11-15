/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package org.dspace.app.cris.dao;

import it.cilea.osd.common.dao.PaginableObjectDao;

import java.util.List;

import org.dspace.app.cris.model.RPSubscription;
import org.dspace.app.cris.model.ResearcherPage;

/**
 * This interface define the methods available to retrieve RPSubscription
 * 
 * @author cilea
 * 
 */
public interface RPSubscriptionDao extends PaginableObjectDao<RPSubscription, Integer> {
	public long countByRp(ResearcherPage rp);
	public List<ResearcherPage> findRPByEpersonID(int epersonID);
	public RPSubscription uniqueByEpersonIDandRp(int epersonID, ResearcherPage rp);
    public void deleteByEpersonID(int id);
}
