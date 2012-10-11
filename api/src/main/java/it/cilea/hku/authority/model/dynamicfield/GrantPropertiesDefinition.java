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

import it.cilea.hku.authority.model.ResearcherGrant;
import it.cilea.osd.jdyna.model.PropertiesDefinition;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="model_grant_jdyna_propertiesdefinition")
@NamedQueries( {
    @NamedQuery(name = "GrantPropertiesDefinition.findAll", query = "from GrantPropertiesDefinition order by id"),    
    @NamedQuery(name = "GrantPropertiesDefinition.findValoriOnCreation", query = "from GrantPropertiesDefinition where onCreation=true"),
    @NamedQuery(name = "GrantPropertiesDefinition.findSimpleSearch", query = "from GrantPropertiesDefinition where simpleSearch=true"),
    @NamedQuery(name = "GrantPropertiesDefinition.findAdvancedSearch", query = "from GrantPropertiesDefinition where advancedSearch=true"),
    @NamedQuery(name = "GrantPropertiesDefinition.uniqueIdByShortName", query = "select id from GrantPropertiesDefinition where shortName = ?"),
    @NamedQuery(name = "GrantPropertiesDefinition.uniqueByShortName", query = "from GrantPropertiesDefinition where shortName = ?"),
    @NamedQuery(name = "GrantPropertiesDefinition.findValoriDaMostrare", query = "from GrantPropertiesDefinition where showInList = true")    
    
})
public class GrantPropertiesDefinition extends PropertiesDefinition {
		
	@Transient
	public Class<ResearcherGrant> getAnagraficaHolderClass() {
		return ResearcherGrant.class;
	}

	@Transient
	public Class<GrantProperty> getPropertyHolderClass() {
		return GrantProperty.class;
	}
	
	@Override
	public Class<DecoratorGrantPropertiesDefinition> getDecoratorClass() {
		return DecoratorGrantPropertiesDefinition.class;
	}

}
