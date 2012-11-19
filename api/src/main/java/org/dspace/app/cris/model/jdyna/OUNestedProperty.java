/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package org.dspace.app.cris.model.jdyna;

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
@Table(name="cris_ou_nestedobject_prop", 
        uniqueConstraints = {@UniqueConstraint(columnNames={"position","typo_id","parent_id"})})
@NamedQueries( {
    @NamedQuery(name = "OUNestedProperty.findPropertyByPropertiesDefinition", query = "from OUNestedProperty where typo = ? order by position"),
    @NamedQuery(name = "OUNestedProperty.findAll", query = "from OUNestedProperty order by id"),    
    @NamedQuery(name = "OUNestedProperty.findPropertyByParentAndTypo", query = "from OUNestedProperty  where (parent.id = ? and typo.id = ?) order by position"),   
    @NamedQuery(name = "OUNestedProperty.deleteAllPropertyByPropertiesDefinition", query = "delete from OUNestedProperty property where typo = ?)")
})
public class OUNestedProperty extends ANestedProperty<OUNestedPropertiesDefinition>
{
    @ManyToOne(fetch=FetchType.EAGER)
    @Fetch(FetchMode.SELECT)    
    private OUNestedPropertiesDefinition typo;
    
    
    @ManyToOne  
    @Index(name = "cris_ou_nestedobject_prop_parent_id")
    private OUNestedObject parent;


    @Override
    public OUNestedPropertiesDefinition getTypo()
    {
        return this.typo;
    }

    @Override
    public void setTypo(OUNestedPropertiesDefinition propertyDefinition)
    {
        this.typo = propertyDefinition;
    }

    @Override
    public void setParent(
            AnagraficaSupport<? extends Property<OUNestedPropertiesDefinition>, OUNestedPropertiesDefinition> parent)
    {
       this.parent = (OUNestedObject)parent;
    }

    @Override
    public AnagraficaSupport<? extends Property<OUNestedPropertiesDefinition>, OUNestedPropertiesDefinition> getParent()
    {
        return parent;
    }


   
}
