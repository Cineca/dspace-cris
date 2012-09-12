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
package it.cilea.hku.authority.model.dynamicfield;

import it.cilea.hku.authority.model.IRestrictedField;
import it.cilea.osd.jdyna.web.Containable;
import it.cilea.osd.jdyna.web.IContainable;

import javax.persistence.Transient;

public class DecoratorRestrictedField extends Containable<String> implements
		IRestrictedField {

		
	private String real;

	/**
	 * Level access of metadata value {@see AccessLevelConstants}
	 */
	private Integer accessLevel;
	
	private boolean repeatable;
	
	private boolean mandatory;
	
	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	@Transient
	public String getShortName() {
		return real;
	}

	@Transient
	public boolean isMandatory() {
		return mandatory;
	}

	
	@Override
	public void setReal(String object) {
		this.real = object;
	}

	@Override
	public String getObject() {
		return this.real;
	}
	@Transient
	public void setAccessLevel(Integer accessLevel) {
		this.accessLevel = accessLevel;
	}
	@Transient
	public Integer getAccessLevel() {
		return accessLevel;
	}

	@Transient
	public String getLabel() {
		return real;
	}

	@Override
	public Integer getVisibility() {
		return getAccessLevel();
	}

	@Override
	public String getValue() {
		return getObject();
	}

	@Override
	public void setVisibility(Integer visibility) {
		this.accessLevel = visibility;
	}

	@Override
	public void setValue(String value) {
		this.real = value;
	}

	@Override
	public boolean getRepeatable() {
		return repeatable;
	}
	
	public void setRepeatable(boolean repeatable) {
		this.repeatable = repeatable;
	}

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public int compareTo(IContainable o) {
		return 0;
	}

}
