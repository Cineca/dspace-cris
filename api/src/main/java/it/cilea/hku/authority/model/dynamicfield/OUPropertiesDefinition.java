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
@Table(name="cris_ou_propertiesdefinition")
@NamedQueries( {
    @NamedQuery(name = "OUPropertiesDefinition.findAll", query = "from OUPropertiesDefinition order by id"),    
    @NamedQuery(name = "OUPropertiesDefinition.findValoriOnCreation", query = "from OUPropertiesDefinition where onCreation=true"),
    @NamedQuery(name = "OUPropertiesDefinition.findSimpleSearch", query = "from OUPropertiesDefinition where simpleSearch=true"),
    @NamedQuery(name = "OUPropertiesDefinition.findAdvancedSearch", query = "from OUPropertiesDefinition where advancedSearch=true"),
    @NamedQuery(name = "OUPropertiesDefinition.uniqueIdByShortName", query = "select id from OUPropertiesDefinition where shortName = ?"),
    @NamedQuery(name = "OUPropertiesDefinition.uniqueByShortName", query = "from OUPropertiesDefinition where shortName = ?"),
    @NamedQuery(name = "OUPropertiesDefinition.findValoriDaMostrare", query = "from OUPropertiesDefinition where showInList = true")
})
public class OUPropertiesDefinition extends PropertiesDefinition {
	
		
	@Transient
	public Class<OUAdditionalFieldStorage> getAnagraficaHolderClass() {
		return OUAdditionalFieldStorage.class;
	}

	@Transient
	public Class<OUProperty> getPropertyHolderClass() {
		return OUProperty.class;
	}
	
	@Override
	public Class<DecoratorOUPropertiesDefinition> getDecoratorClass() {
		return DecoratorOUPropertiesDefinition.class;
	}

}
