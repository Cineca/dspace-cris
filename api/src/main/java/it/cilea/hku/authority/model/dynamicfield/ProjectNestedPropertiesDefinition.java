package it.cilea.hku.authority.model.dynamicfield;

import it.cilea.osd.jdyna.model.ANestedPropertiesDefinition;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
/**
 * @author pascarelli
 *
 */
@Entity
@Table(name="cris_project_nestedobject_propertiesdefinition")
@NamedQueries( {
    @NamedQuery(name = "ProjectNestedPropertiesDefinition.findAll", query = "from ProjectNestedPropertiesDefinition order by id"),    
    @NamedQuery(name = "ProjectNestedPropertiesDefinition.findValoriOnCreation", query = "from ProjectNestedPropertiesDefinition where onCreation=true"),
    @NamedQuery(name = "ProjectNestedPropertiesDefinition.findSimpleSearch", query = "from ProjectNestedPropertiesDefinition where simpleSearch=true"),
    @NamedQuery(name = "ProjectNestedPropertiesDefinition.findAdvancedSearch", query = "from ProjectNestedPropertiesDefinition where advancedSearch=true"),
    @NamedQuery(name = "ProjectNestedPropertiesDefinition.uniqueIdByShortName", query = "select id from ProjectNestedPropertiesDefinition where shortName = ?"),
    @NamedQuery(name = "ProjectNestedPropertiesDefinition.uniqueByShortName", query = "from ProjectNestedPropertiesDefinition where shortName = ?"),
    @NamedQuery(name = "ProjectNestedPropertiesDefinition.findValoriDaMostrare", query = "from ProjectNestedPropertiesDefinition where showInList = true")
})
public class ProjectNestedPropertiesDefinition extends
        ANestedPropertiesDefinition
{

    @Override
    public Class getAnagraficaHolderClass()
    {
        return ProjectNestedObject.class;
    }

    @Override
    public Class getPropertyHolderClass()
    {
        return ProjectNestedProperty.class;
    }

    @Override
    public Class getDecoratorClass()
    {       
        return DecoratorProjectNestedPropertiesDefinition.class;
    }

}
