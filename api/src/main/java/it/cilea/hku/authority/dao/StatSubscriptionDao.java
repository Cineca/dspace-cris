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

import it.cilea.hku.authority.model.ResearcherPage;
import it.cilea.hku.authority.model.StatSubscription;
import it.cilea.osd.common.dao.PaginableObjectDao;

import java.util.List;

/**
 * This interface define the methods available to retrieve StatSubscription
 * 
 * @author cilea
 * 
 */
public interface StatSubscriptionDao extends PaginableObjectDao<StatSubscription, Integer> {
    public List<StatSubscription> findByFreq(int freq);
    public List<StatSubscription> findByRp(ResearcherPage rp);
	public List<StatSubscription> findByHandle(String handle);
	public List<StatSubscription> findByEpersonID(int epersonID);
    public List<StatSubscription> findByEPersonID(int eid);
    public List<StatSubscription> findByEPersonIDandRP(int id, ResearcherPage rp);
    public List<StatSubscription> findByEPersonIDandHandle(int id, String handle);
    public void deleteByEPersonID(int id);
}
