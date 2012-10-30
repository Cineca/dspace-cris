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
