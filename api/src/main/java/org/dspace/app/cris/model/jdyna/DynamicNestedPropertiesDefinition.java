/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * https://github.com/CILEA/dspace-cris/wiki/License
 */
package org.dspace.app.cris.model.jdyna;

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
@Table(name="cris_do_no_pdef")
@NamedQueries( {
    @NamedQuery(name = "DynamicNestedPropertiesDefinition.findAll", query = "from DynamicNestedPropertiesDefinition order by id"),    
    @NamedQuery(name = "DynamicNestedPropertiesDefinition.findValoriOnCreation", query = "from DynamicNestedPropertiesDefinition where onCreation=true"),
    @NamedQuery(name = "DynamicNestedPropertiesDefinition.findSimpleSearch", query = "from DynamicNestedPropertiesDefinition where simpleSearch=true"),
    @NamedQuery(name = "DynamicNestedPropertiesDefinition.findAdvancedSearch", query = "from DynamicNestedPropertiesDefinition where advancedSearch=true"),
    @NamedQuery(name = "DynamicNestedPropertiesDefinition.uniqueIdByShortName", query = "select id from DynamicNestedPropertiesDefinition where shortName = ?"),
    @NamedQuery(name = "DynamicNestedPropertiesDefinition.uniqueByShortName", query = "from DynamicNestedPropertiesDefinition where shortName = ?"),
    @NamedQuery(name = "DynamicNestedPropertiesDefinition.findValoriDaMostrare", query = "from DynamicNestedPropertiesDefinition where showInList = true")
})
public class DynamicNestedPropertiesDefinition extends ANestedPropertiesDefinition
{

    
    @Override
    public Class getAnagraficaHolderClass()
    {
        return DynamicNestedObject.class;
    }

    @Override
    public Class getPropertyHolderClass()
    {
        return DynamicNestedProperty.class;
    }

    @Override
    public Class getDecoratorClass()
    {       
        return DecoratorDynamicNestedPropertiesDefinition.class;
    }

}
