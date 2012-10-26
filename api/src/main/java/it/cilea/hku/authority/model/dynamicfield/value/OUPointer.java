package it.cilea.hku.authority.model.dynamicfield.value;

import it.cilea.hku.authority.model.OrganizationUnit;
import it.cilea.osd.jdyna.value.PointerValue;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@DiscriminatorValue(value="oupointer")
public class OUPointer extends PointerValue<OrganizationUnit>
{

    @ManyToOne
    @LazyCollection(LazyCollectionOption.TRUE)
    @JoinColumn(name="ouvalue")
    private OrganizationUnit real;
    
    @Override
    public Class<OrganizationUnit> getTargetClass()
    {
        return OrganizationUnit.class;
    }

    @Override
    public OrganizationUnit getObject()
    {        
        return real;
    }

    @Override
    protected void setReal(OrganizationUnit oggetto)
    {
       this.real = oggetto;
       if(oggetto != null) {
           sortValue = real.getDisplayValue().toLowerCase();
       }
    }

    @Override
    public OrganizationUnit getDefaultValue()
    {
        return null;
    }

    
}
