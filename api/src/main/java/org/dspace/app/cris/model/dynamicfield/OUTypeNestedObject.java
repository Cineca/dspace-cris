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
@Table(name = "cris_ou_nestedobject_typo")
@NamedQueries ({
    @NamedQuery(name="OUTypeNestedObject.findAll", query = "from OUTypeNestedObject order by id" ),
    @NamedQuery(name="OUTypeNestedObject.uniqueByNome", query = "from OUTypeNestedObject where shortName = ?" )              
})
public class OUTypeNestedObject extends ATypeNestedObject<OUNestedPropertiesDefinition>
{
    @ManyToMany
    @JoinTable(name = "cris_ou_nestedobject_typo2mask")
    @Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<OUNestedPropertiesDefinition> mask;

    @Override
    public List<OUNestedPropertiesDefinition> getMask()
    {
        return mask;
    }

    public void setMask(List<OUNestedPropertiesDefinition> mask) {
        this.mask = mask;
    }

    @Override
    public Class getDecoratorClass()
    {
        return DecoratorOUTypeNested.class;
    }

    @Override
    public Class getAnagraficaHolderClass()
    {
        return OUNestedObject.class;
    }

    @Override
    public Class getPropertyHolderClass()
    {
        return OUNestedProperty.class;
    }

    @Override
    public AWidget getRendering()
    {        
        return null;
    }
}
