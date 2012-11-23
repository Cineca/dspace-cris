/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package org.dspace.app.cris.model.jdyna;

import it.cilea.osd.common.core.HasTimeStampInfo;
import it.cilea.osd.jdyna.model.ANestedObjectWithTypeSupport;
import it.cilea.osd.jdyna.model.ATipologia;
import it.cilea.osd.jdyna.model.AnagraficaSupport;
import it.cilea.osd.jdyna.model.PropertiesDefinition;
import it.cilea.osd.jdyna.model.Property;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.dspace.app.cris.model.OrganizationUnit;
import org.dspace.app.cris.model.UUIDSupport;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OrderBy;

/**
 * @author pascarelli
 *
 */
@Entity
@Table(name = "cris_ou_nestedobject")
@NamedQueries( {
        @NamedQuery(name = "OUNestedObject.findAll", query = "from OUNestedObject order by id"),
        @NamedQuery(name = "OUNestedObject.paginate.id.asc", query = "from OUNestedObject order by id asc"),
        @NamedQuery(name = "OUNestedObject.paginate.id.desc", query = "from OUNestedObject order by id desc"),
        @NamedQuery(name = "OUNestedObject.findNestedObjectsByParentIDAndTypoID", query = "from OUNestedObject where parent.id = ? and typo.id = ?"),
        @NamedQuery(name = "OUNestedObject.paginateNestedObjectsByParentIDAndTypoID.asc.asc", query = "from OUNestedObject where parent.id = ? and typo.id = ?"),
        @NamedQuery(name = "OUNestedObject.countNestedObjectsByParentIDAndTypoID", query = "select count(*) from OUNestedObject where parent.id = ? and typo.id = ?"),
        @NamedQuery(name = "OUNestedObject.findActiveNestedObjectsByParentIDAndTypoID", query = "from OUNestedObject where parent.id = ? and typo.id = ? and status = true"),
        @NamedQuery(name = "OUNestedObject.paginateActiveNestedObjectsByParentIDAndTypoID.asc.asc", query = "from OUNestedObject where parent.id = ? and typo.id = ? and status = true"),
        @NamedQuery(name = "OUNestedObject.countActiveNestedObjectsByParentIDAndTypoID", query = "select count(*) from OUNestedObject where parent.id = ? and typo.id = ? and status = true"),
        @NamedQuery(name = "OUNestedObject.findNestedObjectsByTypoID", query = "from OUNestedObject where typo.id = ?"),
        @NamedQuery(name = "OUNestedObject.deleteNestedObjectsByTypoID", query = "delete from OUNestedObject where typo.id = ?")
        })
public class OUNestedObject extends ANestedObjectWithTypeSupport<OUNestedProperty, OUNestedPropertiesDefinition, OUProperty, OUPropertiesDefinition> implements UUIDSupport, HasTimeStampInfo 
{
    
    @OneToMany(mappedBy = "parent")
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })    
    @OrderBy(clause="position asc")
    private List<OUNestedProperty> anagrafica;

    @ManyToOne
    private OUTypeNestedObject typo;

    @ManyToOne
    private OrganizationUnit parent;
    
    @Override
    public List<OUNestedProperty> getAnagrafica() {
        if(this.anagrafica == null) {
            this.anagrafica = new LinkedList<OUNestedProperty>();
        }
        return anagrafica;
    }
    

    @Override
    public Class<OUNestedProperty> getClassProperty()
    {
        return OUNestedProperty.class;
    }

    @Override
    public Class<OUNestedPropertiesDefinition> getClassPropertiesDefinition()
    {        
        return OUNestedPropertiesDefinition.class;
    }

    @Override
    public OUTypeNestedObject getTypo()
    {
        return typo;
    }

    

    @Override
    public OrganizationUnit getParent()
    {
        return parent;
    }


    @Override
    public void setTypo(ATipologia<OUNestedPropertiesDefinition> typo)
    {
        this.typo = (OUTypeNestedObject)typo;
    }

    @Override
    public void setParent(
            AnagraficaSupport<? extends Property<OUPropertiesDefinition>, OUPropertiesDefinition> parent)
    {
        this.parent = (OrganizationUnit) parent;
    }
    @Override
    public Class getClassParent()
    {
        return OrganizationUnit.class;
    }
   
}
