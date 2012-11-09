/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package it.cilea.hku.authority.discovery;

import it.cilea.hku.authority.model.ACrisObject;
import it.cilea.osd.jdyna.model.PropertiesDefinition;
import it.cilea.osd.jdyna.model.Property;

import org.apache.solr.common.SolrInputDocument;

public interface CrisServiceIndexPlugin {
	public <P extends Property<TP>, TP extends PropertiesDefinition> void additionalIndex(
			ACrisObject<P, TP> crisObject,
			SolrInputDocument sorlDoc);
}
