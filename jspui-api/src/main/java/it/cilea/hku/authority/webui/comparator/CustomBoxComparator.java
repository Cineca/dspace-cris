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
package it.cilea.hku.authority.webui.comparator;

import it.cilea.osd.jdyna.model.Containable;
import it.cilea.osd.jdyna.model.PropertiesDefinition;
import it.cilea.osd.jdyna.model.Property;
import it.cilea.osd.jdyna.web.Box;

import java.util.Comparator;

import org.apache.commons.lang.StringUtils;

public class CustomBoxComparator<P extends Property<TP>, TP extends PropertiesDefinition,C extends Containable<P>, B extends Box<C>> implements Comparator<B> {

	@Override
	public int compare(B o1, B o2) {
		if(o2==null) return -1;				
		return o1.getTitle().trim().compareTo(o2.getTitle().trim());
	}

}
