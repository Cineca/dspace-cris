package it.cilea.hku.authority.model.dynamicfield.value;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import it.cilea.hku.authority.model.OrganizationUnit;
import it.cilea.hku.authority.model.Project;
import it.cilea.osd.jdyna.value.PointerValue;

@Entity
@DiscriminatorValue(value="projectpointer")
public class ProjectPointer extends PointerValue<Project>
{
    @ManyToOne
    @LazyCollection(LazyCollectionOption.TRUE)
    @JoinColumn(name="projectvalue")
    private Project real;
    
    @Override
    public Class<Project> getTargetClass()
    {
        return Project.class;
    }

    @Override
    public Project getObject()
    {
        return real;
    }

    @Override
    protected void setReal(Project oggetto)
    {
        this.real = oggetto;
        if(oggetto != null) {
            sortValue = real.getDisplayValue().toLowerCase();
        }        
    }

    @Override
    public Project getDefaultValue()
    {       
        return null;
    }

}
