/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package it.cilea.hku.authority.model.dynamicfield;

import it.cilea.osd.jdyna.model.ANestedProperty;
import it.cilea.osd.jdyna.model.AnagraficaSupport;
import it.cilea.osd.jdyna.model.Property;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Index;
/**
 * @author pascarelli
 *
 */
@Entity
@Table(name="cris_project_nestedobject_prop", 
        uniqueConstraints = {@UniqueConstraint(columnNames={"position","typo_id","parent_id"})})
@NamedQueries( {
    @NamedQuery(name = "ProjectNestedProperty.findPropertyByPropertiesDefinition", query = "from ProjectNestedProperty where typo = ? order by position"),
    @NamedQuery(name = "ProjectNestedProperty.findAll", query = "from ProjectNestedProperty order by id"),    
    @NamedQuery(name = "ProjectNestedProperty.findPropertyByParentAndTypo", query = "from ProjectNestedProperty  where (parent.id = ? and typo.id = ?) order by position"),   
    @NamedQuery(name = "ProjectNestedProperty.deleteAllPropertyByPropertiesDefinition", query = "delete from ProjectNestedProperty property where typo = ?)")
})
public class ProjectNestedProperty extends ANestedProperty<ProjectNestedPropertiesDefinition>
{
    @ManyToOne(fetch=FetchType.EAGER)
    @Fetch(FetchMode.SELECT)    
    private ProjectNestedPropertiesDefinition typo;
    
    
    @ManyToOne  
    @Index(name = "cris_project_nestedobject_prop_parent_id")
    private ProjectNestedObject parent;


    @Override
    public ProjectNestedPropertiesDefinition getTypo()
    {
        return this.typo;
    }

    @Override
    public void setTypo(ProjectNestedPropertiesDefinition propertyDefinition)
    {
        this.typo = propertyDefinition;
    }

    @Override
    public void setParent(
            AnagraficaSupport<? extends Property<ProjectNestedPropertiesDefinition>, ProjectNestedPropertiesDefinition> parent)
    {
       this.parent = (ProjectNestedObject)parent;
    }

    @Override
    public AnagraficaSupport<? extends Property<ProjectNestedPropertiesDefinition>, ProjectNestedPropertiesDefinition> getParent()
    {
        return parent;
    }


   
}
