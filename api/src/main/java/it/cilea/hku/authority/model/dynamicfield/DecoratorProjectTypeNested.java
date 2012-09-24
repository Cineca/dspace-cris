package it.cilea.hku.authority.model.dynamicfield;

import it.cilea.osd.jdyna.model.ADecoratorTypeDefinition;
import it.cilea.osd.jdyna.model.IContainable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@NamedQueries( {
    @NamedQuery(name = "DecoratorProjectTypeNested.findAll", query = "from DecoratorProjectTypeNested order by id"),
    @NamedQuery(name = "DecoratorProjectTypeNested.uniqueContainableByDecorable", query = "from DecoratorProjectTypeNested where real.id = ?"),
    @NamedQuery(name = "DecoratorProjectTypeNested.uniqueContainableByShortName", query = "from DecoratorProjectTypeNested where real.shortName = ?")
    
})
@DiscriminatorValue(value="typerpnestedobject")
public class DecoratorProjectTypeNested extends
    ADecoratorTypeDefinition<ProjectTypeNestedObject, ProjectNestedPropertiesDefinition>
{

    @OneToOne(optional=true)
    @JoinColumn(name="typeprojectnestedobject_fk")
    @Cascade(value = {CascadeType.ALL,CascadeType.DELETE_ORPHAN})
    private ProjectTypeNestedObject real;

    @Override
    public String getShortName()
    {        
        return this.real.getShortName();
    }

    @Override
    public Integer getAccessLevel()
    {
        return this.real.getAccessLevel();
    }

    @Override
    public String getLabel()
    {
        return this.real.getDescrizione();
    }

    @Override
    public boolean getRepeatable()
    {
        return this.real.isRepeatable();
    }

    @Override
    public int getPriority()
    {
        return this.real.getPriority();
    }

    @Override
    public int compareTo(IContainable o) {
        ProjectTypeNestedObject oo = null;
        if(o instanceof DecoratorProjectTypeNested) {
            oo = (ProjectTypeNestedObject)o.getObject();
            return this.real.compareTo(oo);
        }
        return 0;
    }
    
    @Override
    public void setReal(ProjectTypeNestedObject object)
    {
       this.real = object;        
    }

    @Override
    public ProjectTypeNestedObject getObject()
    {
        return real;
    }

    
}
