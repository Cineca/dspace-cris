/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package org.dspace.app.cris.model.dynamicfield;

import it.cilea.osd.jdyna.model.ATypeNestedObject;
import it.cilea.osd.jdyna.model.AWidget;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
/**
*
* @author pascarelli
*
*/
@Entity
@Table(name = "cris_project_nestedobject_typo")
@NamedQueries ({
    @NamedQuery(name="ProjectTypeNestedObject.findAll", query = "from ProjectTypeNestedObject order by id" ),
    @NamedQuery(name="ProjectTypeNestedObject.uniqueByNome", query = "from ProjectTypeNestedObject where shortName = ?" )
    
})
public class ProjectTypeNestedObject extends ATypeNestedObject<ProjectNestedPropertiesDefinition>
{
    @ManyToMany
    @JoinTable(name = "cris_project_nestedobject_typo2mask")
    @Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<ProjectNestedPropertiesDefinition> mask;

    @Override
    public List<ProjectNestedPropertiesDefinition> getMask()
    {
        return mask;
    }

    public void setMask(List<ProjectNestedPropertiesDefinition> mask) {
        this.mask = mask;
    }

    @Override
    public Class getDecoratorClass()
    {
        return DecoratorProjectTypeNested.class;
    }

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
    public AWidget getRendering()
    {        
        return null;
    }

}
