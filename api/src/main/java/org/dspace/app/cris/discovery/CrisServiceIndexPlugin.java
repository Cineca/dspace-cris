/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package org.dspace.app.cris.discovery;

import it.cilea.osd.jdyna.model.PropertiesDefinition;
import it.cilea.osd.jdyna.model.Property;

import org.apache.solr.common.SolrInputDocument;
import org.dspace.app.cris.model.ACrisObject;

public interface CrisServiceIndexPlugin {
	public <P extends Property<TP>, TP extends PropertiesDefinition> void additionalIndex(
			ACrisObject<P, TP> crisObject,
			SolrInputDocument sorlDoc);
}
