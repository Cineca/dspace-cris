/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package it.cilea.hku.authority.model.dynamicfield.value;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import it.cilea.hku.authority.model.Project;
import it.cilea.hku.authority.model.ResearcherPage;
import it.cilea.osd.jdyna.value.PointerValue;

@Entity
@DiscriminatorValue(value="rppointer")
public class RPPointer extends PointerValue<ResearcherPage>
{

    @ManyToOne
    @LazyCollection(LazyCollectionOption.TRUE)
    @JoinColumn(name="rpvalue")
    private ResearcherPage real;
    
    @Override
    public Class<ResearcherPage> getTargetClass()
    {
        return ResearcherPage.class;
    }

    @Override
    public ResearcherPage getObject()
    {
        return real;
    }

    @Override
    protected void setReal(ResearcherPage oggetto)
    {
        this.real = oggetto;
        if(oggetto != null) {
            sortValue = real.getDisplayValue().toLowerCase();
        }    
    }

    @Override
    public ResearcherPage getDefaultValue()
    {       
        return null;
    }

}
