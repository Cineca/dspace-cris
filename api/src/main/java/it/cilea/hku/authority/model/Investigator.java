/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 * 
 * http://www.dspace.org/license/
 * 
 * The document has moved 
 * <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>
 */
package it.cilea.hku.authority.model;

import javax.persistence.Embeddable;
import javax.persistence.OneToOne;

@Embeddable
public class Investigator implements IRestrictedField {
	
	private String extInvestigator;
	
	@OneToOne
	private ResearcherPage intInvestigator;

	public ResearcherPage getIntInvestigator() {
		return intInvestigator;
	}

	public void setIntInvestigator(ResearcherPage intInvestigator) {
		this.intInvestigator = intInvestigator;
	}

	public void setExtInvestigator(String extInvestigator) {
		this.extInvestigator = extInvestigator;
	}

	public String getExtInvestigator() {
		return extInvestigator;
	}

    @Override
    public Integer getVisibility()
    {        
        return 1;
    }

    @Override
    public String getValue()
    {
        if(getIntInvestigator()!=null) {
            return getIntInvestigator().getFullName();
        }
        return extInvestigator;
    }

    @Override
    public void setVisibility(Integer visibility)
    {
        // TODO Auto-generated method stub        
    }

    @Override
    public void setValue(String value)
    {
        // TODO Auto-generated method stub
        
    }
	
	
}
