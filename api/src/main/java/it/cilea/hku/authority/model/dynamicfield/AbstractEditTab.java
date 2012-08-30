/**
 * <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
 * <html><head>
 * <title>301 Moved Permanently</title>
 * </head><body>
 * <h1>Moved Permanently</h1>
 * <p>The document has moved <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>.</p>
 * </body></html>
 */
package it.cilea.hku.authority.model.dynamicfield;

import it.cilea.osd.jdyna.web.Box;
import it.cilea.osd.jdyna.web.Containable;
import it.cilea.osd.jdyna.web.Tab;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractEditTab<H extends Box<Containable>, D extends AbstractTab<H>> extends Tab<H> {

	public abstract Tab<H> getDisplayTab();

	public abstract void setDisplayTab(D tab);

	public abstract Class<D> getDisplayTabClass();
		
}
