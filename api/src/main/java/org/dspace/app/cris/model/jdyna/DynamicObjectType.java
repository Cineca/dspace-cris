/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package org.dspace.app.cris.model.jdyna;

import it.cilea.osd.jdyna.model.AType;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 
 * 
 * @author pascarelli
 * 
 */
@Entity
@Table(name = "cris_do_tp")
@NamedQueries({
        @NamedQuery(name = "DynamicObjectType.findAll", query = "from DynamicObjectType order by id"),
        @NamedQuery(name = "DynamicObjectType.uniqueByShortName", query = "from DynamicObjectType where shortName = ?"),
        @NamedQuery(name = "DynamicObjectType.findPropertiesDefinitionsByTypo", query = "???")
})
        
public class DynamicObjectType extends AType<DynamicPropertiesDefinition>
{

    /** DB Primary key */
    @Id
    @GeneratedValue(generator = "CRIS_TYPODYNAOBJ_SEQ")
    @SequenceGenerator(name = "CRIS_TYPODYNAOBJ_SEQ", sequenceName = "CRIS_TYPODYNAOBJ_SEQ")    
    private Integer id;
    
    @ManyToMany    
    @JoinTable(name = "cris_do_tp2pdef", joinColumns = { 
            @JoinColumn(name = "cris_do_tp_id") }, 
            inverseJoinColumns = { @JoinColumn(name = "cris_do_pdef_id") })
    @Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<DynamicPropertiesDefinition> mask;

    @Override
    public List<DynamicPropertiesDefinition> getMask()
    {
        return mask;
    }

    public void setMask(List<DynamicPropertiesDefinition> mask) {
        this.mask = mask;
    }

    @Override
    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }
}
