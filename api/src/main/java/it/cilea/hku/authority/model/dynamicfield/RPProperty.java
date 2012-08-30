/**
 * <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
 * <html><head>
 * <title>301 Moved Permanently</title>
 * </head><body>
 * <h1>Moved Permanently</h1>
 * <p>The document has moved <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>.</p>
 * </body></html>
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
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.search.annotations.ContainedIn;

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
	@ContainedIn
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
