/**
 * <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
 * <html><head>
 * <title>301 Moved Permanently</title>
 * </head><body>
 * <h1>Moved Permanently</h1>
 * <p>The document has moved <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>.</p>
 * </body></html>
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
