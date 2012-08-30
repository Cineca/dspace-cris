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
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;

/**
 * This class models a single field of the ResearcherPage.
 * Any field contains a value and a visibility flags. 
 * 
 * @author cilea
 *
 */
@Embeddable
@MappedSuperclass
public class RestrictedField implements IRestrictedField {
	
	@Type(type = "text")
	/**
	 * the actual content of the field
	 */
	private String value;
		
	/**
	 * the visibility flags of the field
	 */
	private Integer visibility;

    /**
     * Constructor. All the field are by default publicly visible
     */
	public RestrictedField()
    {
	    this.visibility = 1;
    }
	
	/**
     * Getter method.
     * 
     * @return the actual content of the field
     */
	public String getValue() {
		return value;
	}

	/**
     * Setter method.
     * 
     * @param value
     *            the actual content of the field
     */
	public void setValue(String value) {
		this.value = value;
	}

	/**
     * Getter method.
     * 
     * @see VisibilityConstants
     * 
     * @return the visibility flags of the field
     */
    public Integer getVisibility()
    {
        if(visibility==null) {
            return 0;
        }
        return visibility;
    }

    /**
     * Setter method. * @see VisibilityConstants
     * 
     * @param visibility
     *            the visibility flags of the field
     */
    public void setVisibility(Integer visibility)
    {	    
		this.visibility = visibility ==null?new Integer(0):visibility;
	}
}
