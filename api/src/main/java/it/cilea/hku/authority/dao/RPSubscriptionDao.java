/**
 * <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
 * <html><head>
 * <title>301 Moved Permanently</title>
 * </head><body>
 * <h1>Moved Permanently</h1>
 * <p>The document has moved <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>.</p>
 * </body></html>
 */
package it.cilea.hku.authority.dao;

import java.util.List;

import it.cilea.hku.authority.model.RPSubscription;
import it.cilea.hku.authority.model.ResearcherPage;
import it.cilea.osd.common.dao.PaginableObjectDao;

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
