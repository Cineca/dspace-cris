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
import org.hibernate.annotations.Index;

@Entity
@Table(name="model_grant_jdyna_prop", 
		uniqueConstraints = {@UniqueConstraint(columnNames={"position","typo_id","parent_id"})})
@NamedQueries( {
	@NamedQuery(name = "GrantProperty.findPropertyByPropertiesDefinition", query = "from GrantProperty where typo = ? order by position"),
	@NamedQuery(name = "GrantProperty.findAll", query = "from GrantProperty order by id"),	
	@NamedQuery(name = "GrantProperty.findPropertyByParentAndTypo", query = "from GrantProperty  where (parent.id = ? and typo.id = ?) order by position"),	
	@NamedQuery(name = "GrantProperty.deleteAllPropertyByPropertiesDefinition", query = "delete from GrantProperty property where typo = ?)")
})
public class GrantProperty extends Property<GrantPropertiesDefinition> {
	
	@ManyToOne(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)	
	private GrantPropertiesDefinition typo;
	
	
	@ManyToOne
	@Index(name = "model_grant_jdyna_prop_idx_parent_id")
	private ResearcherGrant parent;
	

	@Override
	public GrantPropertiesDefinition getTypo() {
		return typo;
	}

	@Override
	public void setTypo(GrantPropertiesDefinition propertyDefinition) {
		this.typo = propertyDefinition;		
	}


	@Override
	public AnagraficaSupport<GrantProperty, GrantPropertiesDefinition> getParent() {
		return parent;
	}

	@Override
	public void setParent(
			AnagraficaSupport<? extends Property<GrantPropertiesDefinition>, GrantPropertiesDefinition> parent) {
		
		this.parent = (ResearcherGrant)parent;
		
	}

}
