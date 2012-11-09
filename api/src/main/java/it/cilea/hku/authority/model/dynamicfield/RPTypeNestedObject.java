/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package it.cilea.hku.authority.model.dynamicfield;

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
@Table(name = "cris_rp_nestedobject_typo")
@NamedQueries ({
    @NamedQuery(name="RPTypeNestedObject.findAll", query = "from RPTypeNestedObject order by id" ),
    @NamedQuery(name="RPTypeNestedObject.uniqueByNome", query = "from RPTypeNestedObject where shortName = ?" )              
})
public class RPTypeNestedObject extends ATypeNestedObject<RPNestedPropertiesDefinition>
{
    @ManyToMany
    @JoinTable(name = "cris_rp_jdyna_nestedobject_typo2mask")
    @Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<RPNestedPropertiesDefinition> mask;

    @Override
    public List<RPNestedPropertiesDefinition> getMask()
    {
        return mask;
    }

    public void setMask(List<RPNestedPropertiesDefinition> mask) {
        this.mask = mask;
    }

    @Override
    public Class getDecoratorClass()
    {
        return DecoratorRPTypeNested.class;
    }


    @Override
    public Class getAnagraficaHolderClass()
    {
        return RPNestedObject.class;
    }

    @Override
    public Class getPropertyHolderClass()
    {
        return RPNestedProperty.class;
    }

    @Override
    public AWidget getRendering()
    {        
        return null;
    }


}
