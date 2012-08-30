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

import it.cilea.hku.authority.model.ResearcherGrant;
import it.cilea.osd.common.dao.PaginableObjectDao;

/**
 * This interface define the methods available to retrieve ResearcherGrant
 * 
 * @author cilea
 * 
 */
public interface ResearcherGrantDao extends
        PaginableObjectDao<ResearcherGrant, Integer>
{

    public long count();

	public ResearcherGrant uniqueRGByCode(String projectcode);

}
