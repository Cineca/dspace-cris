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

import it.cilea.osd.jdyna.model.AWidget;
import it.cilea.osd.jdyna.model.IPropertiesDefinition;
import it.cilea.osd.jdyna.web.ADecoratorPropertiesDefinition;
import it.cilea.osd.jdyna.web.IContainable;

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
	@NamedQuery(name = "DecoratorRPPropertiesDefinition.findAll", query = "from DecoratorRPPropertiesDefinition order by id"),
	@NamedQuery(name = "DecoratorRPPropertiesDefinition.uniqueContainableByDecorable", query = "from DecoratorRPPropertiesDefinition where real.id = ?"),
	@NamedQuery(name = "DecoratorRPPropertiesDefinition.uniqueContainableByShortName", query = "from DecoratorRPPropertiesDefinition where real.shortName = ?")
	
})
@DiscriminatorValue(value="propertiesdefinition")
public class DecoratorRPPropertiesDefinition extends ADecoratorPropertiesDefinition<RPPropertiesDefinition>  {
	
	@OneToOne(optional=true)
	@JoinColumn(name="propertiesdefinition_fk")
	@Cascade(value = {CascadeType.ALL,CascadeType.DELETE_ORPHAN})
	private RPPropertiesDefinition real;
	

	@Override
	public void setReal(RPPropertiesDefinition real) {
		this.real = real;
	}
	
	@Override
	public RPPropertiesDefinition getObject() {
		return real;
	}

	@Transient
	public Class<RPAdditionalFieldStorage> getAnagraficaHolderClass() {
		return real.getAnagraficaHolderClass();
	}

	@Transient
	public Class<RPProperty> getPropertyHolderClass() {
		return real.getPropertyHolderClass();
	}

	public Class<DecoratorRPPropertiesDefinition> getDecoratorClass() {
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
		RPPropertiesDefinition oo = null;
		if(o instanceof DecoratorRPPropertiesDefinition) {
			oo = (RPPropertiesDefinition)o.getObject();
			return this.real.compareTo(oo);
		}
		return 0;
	}
}
