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
@Table(name="cris_rp_nestedobject_prop", 
        uniqueConstraints = {@UniqueConstraint(columnNames={"position","typo_id","parent_id"})})
@NamedQueries( {
    @NamedQuery(name = "RPNestedProperty.findPropertyByPropertiesDefinition", query = "from RPNestedProperty where typo = ? order by position"),
    @NamedQuery(name = "RPNestedProperty.findAll", query = "from RPNestedProperty order by id"),    
    @NamedQuery(name = "RPNestedProperty.findPropertyByParentAndTypo", query = "from RPNestedProperty  where (parent.id = ? and typo.id = ?) order by position"),   
    @NamedQuery(name = "RPNestedProperty.deleteAllPropertyByPropertiesDefinition", query = "delete from RPNestedProperty property where typo = ?)")
})
public class RPNestedProperty extends ANestedProperty<RPNestedPropertiesDefinition>
{
    @ManyToOne(fetch=FetchType.EAGER)
    @Fetch(FetchMode.SELECT)    
    private RPNestedPropertiesDefinition typo;
    
    
    @ManyToOne
    @Index(name = "cris_rp_nestedobject_prop_parent_id")
    private RPNestedObject parent;


    @Override
    public RPNestedPropertiesDefinition getTypo()
    {
        return this.typo;
    }

    @Override
    public void setTypo(RPNestedPropertiesDefinition propertyDefinition)
    {
        this.typo = propertyDefinition;
    }

    @Override
    public void setParent(
            AnagraficaSupport<? extends Property<RPNestedPropertiesDefinition>, RPNestedPropertiesDefinition> parent)
    {
        this.parent = (RPNestedObject)parent;
    }

    @Override
    public AnagraficaSupport<RPNestedProperty, RPNestedPropertiesDefinition> getParent()
    {
        return parent;
    }

}
