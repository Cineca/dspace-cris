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

import it.cilea.osd.jdyna.model.AnagraficaSupport;
import it.cilea.osd.jdyna.model.Property;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="model_rp_jdyna_prop", 
        uniqueConstraints = {@UniqueConstraint(columnNames={"position","typo_id","parent_id"})})
@NamedQueries( {
	@NamedQuery(name = "RPProperty.findPropertyByPropertiesDefinition", query = "from RPProperty where typo = ? order by position"),
	@NamedQuery(name = "RPProperty.findAll", query = "from RPProperty order by id"),	
	@NamedQuery(name = "RPProperty.findPropertyByParentAndTypo", query = "from RPProperty  where (parent.id = ? and typo.id = ?) order by position"),	
	@NamedQuery(name = "RPProperty.deleteAllPropertyByPropertiesDefinition", query = "delete from RPProperty property where typo = ?)")
})
public class RPProperty extends Property<RPPropertiesDefinition> {
	
	@ManyToOne(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)	
	private RPPropertiesDefinition typo;
	
	
	@ManyToOne	
	private RPAdditionalFieldStorage parent;


	@Override
	public RPPropertiesDefinition getTypo() {
		return typo;
	}

	@Override
	public void setTypo(RPPropertiesDefinition propertyDefinition) {
		this.typo = propertyDefinition;		
	}


	@Override
	public AnagraficaSupport<RPProperty, RPPropertiesDefinition> getParent() {
		return parent;
	}

	@Override
	public void setParent(
			AnagraficaSupport<? extends Property<RPPropertiesDefinition>, RPPropertiesDefinition> parent) {
		
		this.parent = (RPAdditionalFieldStorage)parent;
		
	}

}
