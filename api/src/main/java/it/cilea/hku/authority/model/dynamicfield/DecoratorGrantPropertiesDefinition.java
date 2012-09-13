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

import it.cilea.hku.authority.model.ResearcherGrant;
import it.cilea.osd.jdyna.model.ADecoratorPropertiesDefinition;
import it.cilea.osd.jdyna.model.AWidget;
import it.cilea.osd.jdyna.model.IContainable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@NamedQueries( {
	@NamedQuery(name = "DecoratorGrantPropertiesDefinition.findAll", query = "from DecoratorGrantPropertiesDefinition order by id"),
	@NamedQuery(name = "DecoratorGrantPropertiesDefinition.uniqueContainableByDecorable", query = "from DecoratorGrantPropertiesDefinition where real.id = ?"),
	@NamedQuery(name = "DecoratorGrantPropertiesDefinition.uniqueContainableByShortName", query = "from DecoratorGrantPropertiesDefinition where real.shortName = ?")
	
})
@DiscriminatorValue(value="propertiesdefinitiongrant")
public class DecoratorGrantPropertiesDefinition extends ADecoratorPropertiesDefinition<GrantPropertiesDefinition>  {
	
	@OneToOne(optional=true)
	@JoinColumn(name="propertiesdefinitiongrant_fk")
	@Cascade(value = {CascadeType.ALL,CascadeType.DELETE_ORPHAN})
	private GrantPropertiesDefinition real;
	

	@Override
	public void setReal(GrantPropertiesDefinition real) {
		this.real = real;
	}
	
	@Override
	public GrantPropertiesDefinition getObject() {
		return real;
	}

	@Transient
	public Class<ResearcherGrant> getAnagraficaHolderClass() {
		return real.getAnagraficaHolderClass();
	}

	@Transient
	public Class<GrantProperty> getPropertyHolderClass() {
		return real.getPropertyHolderClass();
	}

	public Class<DecoratorGrantPropertiesDefinition> getDecoratorClass() {
		return real.getDecoratorClass();
	}
	
	@Transient
	public AWidget getRendering() {
		return this.real.getRendering();
	}

	@Transient
	public String getShortName() {
		return this.real.getShortName();
	}

	@Transient
	public boolean isMandatory() {
		return this.real.isMandatory();
	}

	@Transient
	public String getLabel() {
		return this.real.getLabel();
	}

	@Transient
	public int getPriority() {
		return this.real.getPriority();
	}

	@Transient
	public Integer getAccessLevel() {
		return this.real.getAccessLevel();
	}

	@Override
	public boolean getRepeatable() {
		return this.real.isRepeatable();
	}

	@Override
	public int compareTo(IContainable o) {
		GrantPropertiesDefinition oo = null;
		if(o instanceof DecoratorGrantPropertiesDefinition) {
			oo = (GrantPropertiesDefinition)o.getObject();
			return this.real.compareTo(oo);
		}
		return 0;
	}
}
