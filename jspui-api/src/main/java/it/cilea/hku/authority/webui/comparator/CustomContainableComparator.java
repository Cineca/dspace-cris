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
import it.cilea.osd.jdyna.web.Containable;

import java.util.Comparator;

public class CustomContainableComparator<P extends Property<TP>, TP extends PropertiesDefinition,C extends Containable<P>> implements Comparator<C> {

	@Override
	public int compare(C o1, C o2) {
		if(o2==null) return -1;
		if(o1.getShortName().equals(o2.getShortName())) {
			if(o1.getLabel()!=null && o2.getLabel()!=null) {
				return o1.getLabel().trim().compareTo(o2.getLabel().trim());
			}
		}
		return o1.getShortName().compareTo(o2.getShortName());
	}
	
}
