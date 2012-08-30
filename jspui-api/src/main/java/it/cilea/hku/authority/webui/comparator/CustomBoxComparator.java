/**
 * <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
 * <html><head>
 * <title>301 Moved Permanently</title>
 * </head><body>
 * <h1>Moved Permanently</h1>
 * <p>The document has moved <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>.</p>
 * </body></html>
 */
package it.cilea.hku.authority.webui.comparator;

import it.cilea.osd.jdyna.model.PropertiesDefinition;
import it.cilea.osd.jdyna.model.Property;
import it.cilea.osd.jdyna.web.Box;
import it.cilea.osd.jdyna.web.Containable;

import java.util.Comparator;

import org.apache.commons.lang.StringUtils;

public class CustomBoxComparator<P extends Property<TP>, TP extends PropertiesDefinition,C extends Containable<P>, B extends Box<C>> implements Comparator<B> {

	@Override
	public int compare(B o1, B o2) {
		if(o2==null) return -1;				
		return o1.getTitle().trim().compareTo(o2.getTitle().trim());
	}

}
