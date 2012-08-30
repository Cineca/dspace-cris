/**
 * <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
 * <html><head>
 * <title>301 Moved Permanently</title>
 * </head><body>
 * <h1>Moved Permanently</h1>
 * <p>The document has moved <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>.</p>
 * </body></html>
 */
package it.cilea.hku.authority.model.dynamicfield;

import it.cilea.osd.jdyna.model.PropertiesDefinition;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="model_rp_jdyna_propertiesdefinition")
@NamedQueries( {
    @NamedQuery(name = "RPPropertiesDefinition.findAll", query = "from RPPropertiesDefinition order by id"),
    @NamedQuery(name = "RPPropertiesDefinition.findAllTipologieProprietaFirstLevel", query = "from RPPropertiesDefinition where topLevel = true order by id"),
    @NamedQuery(name = "RPPropertiesDefinition.findValoriOnCreation", query = "from RPPropertiesDefinition where onCreation=true and topLevel = true"),
    @NamedQuery(name = "RPPropertiesDefinition.findSimpleSearch", query = "from RPPropertiesDefinition where simpleSearch=true"),
    @NamedQuery(name = "RPPropertiesDefinition.findAdvancedSearch", query = "from RPPropertiesDefinition where advancedSearch=true"),
    @NamedQuery(name = "RPPropertiesDefinition.uniqueIdByShortName", query = "select id from RPPropertiesDefinition where shortName = ?"),
    @NamedQuery(name = "RPPropertiesDefinition.uniqueByShortName", query = "from RPPropertiesDefinition where shortName = ?"),
    @NamedQuery(name = "RPPropertiesDefinition.findValoriDaMostrare", query = "from RPPropertiesDefinition where showInList = true and topLevel = true"),
    @NamedQuery(name = "RPPropertiesDefinition.findAllWithWidgetCombo", query = "from RPPropertiesDefinition tipologiaProprietaOpera where tipologiaProprietaOpera.rendering in (from WidgetComboRPAdditional)"),
//  @NamedQuery(name = "RPPropertiesDefinition.findAllWithWidgetFormula", query = "from RPPropertiesDefinition tipologiaProprietaOpera where tipologiaProprietaOpera.rendering in (from WidgetFormula)"),
    @NamedQuery(name = "RPPropertiesDefinition.uniqueTipologiaProprietaCombo", query = "select tipologiaProprietaOpera from RPPropertiesDefinition tipologiaProprietaOpera left join tipologiaProprietaOpera.rendering rendering where rendering.id in (select widgetCombo.id from WidgetComboRPAdditional widgetCombo inner join widgetCombo.sottoTipologie m where m = ?)")
})
public class RPPropertiesDefinition extends PropertiesDefinition {
	
		
	/**
	 * Level access of metadata value {@see AccessLevelConstants}
	 */
	private Integer accessLevel;
	
	public RPPropertiesDefinition() {
		this.accessLevel = AccessLevelConstants.ADMIN_ACCESS;
	}
	
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

	public void setAccessLevel(Integer accessLevel) {
		this.accessLevel = accessLevel;
	}

	public Integer getAccessLevel() {
		return accessLevel;
	}

}
