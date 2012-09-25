package it.cilea.hku.authority.model.dynamicfield;

import it.cilea.hku.authority.model.ResearcherPage;
import it.cilea.osd.jdyna.model.ANestedObjectWithTypeSupport;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OrderBy;
@Entity
@Table(name = "model_rp_jdyna_nestedobject")
@NamedQueries( {
        @NamedQuery(name = "RPNestedObject.findAll", query = "from RPNestedObject order by id"),
        @NamedQuery(name = "RPNestedObject.paginate.id.asc", query = "from RPNestedObject order by id asc"),
        @NamedQuery(name = "RPNestedObject.paginate.id.desc", query = "from RPNestedObject order by id desc")
        })
public class RPNestedObject extends ANestedObjectWithTypeSupport<RPNestedProperty, RPNestedPropertiesDefinition> 
{

    @OneToMany(mappedBy = "parent")
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })    
    @OrderBy(clause="position asc")
    private List<RPNestedProperty> anagrafica;

    @ManyToOne
    private RPTypeNestedObject typo;
    
    @ManyToOne(targetEntity = ResearcherPage.class)
    private RPAdditionalFieldStorage parent;
    
    @Override
    public List<RPNestedProperty> getAnagrafica() {
        if(this.anagrafica == null) {
            this.anagrafica = new LinkedList<RPNestedProperty>();
        }
        return anagrafica;
    }
    

    @Override
    public Class<RPNestedProperty> getClassProperty()
    {
        return RPNestedProperty.class;
    }

    @Override
    public Class<RPNestedPropertiesDefinition> getClassPropertiesDefinition()
    {
        return RPNestedPropertiesDefinition.class;
    }

    @Override
    public RPTypeNestedObject getTipologia()
    {
        return typo;
    }


    public void setParent(RPAdditionalFieldStorage parent)
    {
        this.parent = parent;
    }


    public RPAdditionalFieldStorage getParent()
    {
        return parent;
    }

  

}
