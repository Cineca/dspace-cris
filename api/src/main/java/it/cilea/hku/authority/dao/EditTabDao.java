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

import it.cilea.hku.authority.model.dynamicfield.AbstractEditTab;
import it.cilea.hku.authority.model.dynamicfield.AbstractTab;
import it.cilea.osd.jdyna.dao.TabDao;
import it.cilea.osd.jdyna.web.Box;
import it.cilea.osd.jdyna.web.Containable;



public interface EditTabDao<H extends Box<Containable>, D extends AbstractTab<H>, T extends AbstractEditTab<H,D>> extends TabDao<H,T> {

	public T uniqueByDisplayTab(int tabId);
	
}
