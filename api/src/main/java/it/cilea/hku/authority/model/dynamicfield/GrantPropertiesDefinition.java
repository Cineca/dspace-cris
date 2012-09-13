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
import it.cilea.osd.jdyna.model.AccessLevelConstants;
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
    @NamedQuery(name = "GrantPropertiesDefinition.findAllTipologieProprietaFirstLevel", query = "from GrantPropertiesDefinition where topLevel = true order by id"),
    @NamedQuery(name = "GrantPropertiesDefinition.findValoriOnCreation", query = "from GrantPropertiesDefinition where onCreation=true and topLevel = true"),
    @NamedQuery(name = "GrantPropertiesDefinition.findSimpleSearch", query = "from GrantPropertiesDefinition where simpleSearch=true"),
    @NamedQuery(name = "GrantPropertiesDefinition.findAdvancedSearch", query = "from GrantPropertiesDefinition where advancedSearch=true"),
    @NamedQuery(name = "GrantPropertiesDefinition.uniqueIdByShortName", query = "select id from GrantPropertiesDefinition where shortName = ?"),
    @NamedQuery(name = "GrantPropertiesDefinition.uniqueByShortName", query = "from GrantPropertiesDefinition where shortName = ?"),
    @NamedQuery(name = "GrantPropertiesDefinition.findValoriDaMostrare", query = "from GrantPropertiesDefinition where showInList = true and topLevel = true"),
    @NamedQuery(name = "GrantPropertiesDefinition.findAllWithWidgetCombo", query = "from GrantPropertiesDefinition tipologiaProprietaOpera where tipologiaProprietaOpera.rendering in (from WidgetComboRPAdditional)"),
//  @NamedQuery(name = "GrantPropertiesDefinition.findAllWithWidgetFormula", query = "from GrantPropertiesDefinition tipologiaProprietaOpera where tipologiaProprietaOpera.rendering in (from WidgetFormula)"),
    @NamedQuery(name = "GrantPropertiesDefinition.uniqueTipologiaProprietaCombo", query = "select tipologiaProprietaOpera from GrantPropertiesDefinition tipologiaProprietaOpera left join tipologiaProprietaOpera.rendering rendering where rendering.id in (select widgetCombo.id from WidgetComboResearcherGrant widgetCombo inner join widgetCombo.sottoTipologie m where m = ?)")
})
public class GrantPropertiesDefinition extends PropertiesDefinition {
	
		
	/**
	 * Level access of metadata value {@see AccessLevelConstants}
	 */
	private Integer accessLevel;
	
	public GrantPropertiesDefinition() {
		this.accessLevel = AccessLevelConstants.ADMIN_ACCESS;
	}
	
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

	public void setAccessLevel(Integer accessLevel) {
		this.accessLevel = accessLevel;
	}

	public Integer getAccessLevel() {
		return accessLevel;
	}

}
