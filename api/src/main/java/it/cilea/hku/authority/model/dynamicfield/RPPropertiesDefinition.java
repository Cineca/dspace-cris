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
package it.cilea.hku.authority.model.dynamicfield;

import it.cilea.osd.jdyna.model.PropertiesDefinition;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="cris_rp_propertiesdefinition")
@NamedQueries( {
    @NamedQuery(name = "RPPropertiesDefinition.findAll", query = "from RPPropertiesDefinition order by id"),    
    @NamedQuery(name = "RPPropertiesDefinition.findValoriOnCreation", query = "from RPPropertiesDefinition where onCreation=true"),
    @NamedQuery(name = "RPPropertiesDefinition.findSimpleSearch", query = "from RPPropertiesDefinition where simpleSearch=true"),
    @NamedQuery(name = "RPPropertiesDefinition.findAdvancedSearch", query = "from RPPropertiesDefinition where advancedSearch=true"),
    @NamedQuery(name = "RPPropertiesDefinition.uniqueIdByShortName", query = "select id from RPPropertiesDefinition where shortName = ?"),
    @NamedQuery(name = "RPPropertiesDefinition.uniqueByShortName", query = "from RPPropertiesDefinition where shortName = ?"),
    @NamedQuery(name = "RPPropertiesDefinition.findValoriDaMostrare", query = "from RPPropertiesDefinition where showInList = true")
})
public class RPPropertiesDefinition extends PropertiesDefinition {
	
		
	@Transient
	public Class<RPAdditionalFieldStorage> getAnagraficaHolderClass() {
		return RPAdditionalFieldStorage.class;
	}

	@Transient
	public Class<RPProperty> getPropertyHolderClass() {
		return RPProperty.class;
	}
	
	@Override
	public Class<DecoratorRPPropertiesDefinition> getDecoratorClass() {
		return DecoratorRPPropertiesDefinition.class;
	}

}
