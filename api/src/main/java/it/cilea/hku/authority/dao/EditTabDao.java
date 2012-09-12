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

import it.cilea.hku.authority.model.dynamicfield.AbstractEditTab;
import it.cilea.hku.authority.model.dynamicfield.AbstractTab;
import it.cilea.osd.jdyna.dao.TabDao;
import it.cilea.osd.jdyna.web.Box;
import it.cilea.osd.jdyna.web.Containable;



public interface EditTabDao<H extends Box<Containable>, D extends AbstractTab<H>, T extends AbstractEditTab<H,D>> extends TabDao<H,T> {

	public T uniqueByDisplayTab(int tabId);
	
}
