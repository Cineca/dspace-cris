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


import it.cilea.osd.jdyna.model.AnagraficaObject;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OrderBy;

@Entity
@Table(name="model_rp_jdyna")
@NamedQueries( {
        @NamedQuery(name = "RPAdditionalFieldStorage.findAll", query = "from RPAdditionalFieldStorage order by id"),
        @NamedQuery(name = "RPAdditionalFieldStorage.paginate.id.asc", query = "from RPAdditionalFieldStorage order by id asc"),
        @NamedQuery(name = "RPAdditionalFieldStorage.paginate.id.desc", query = "from RPAdditionalFieldStorage order by id desc"),  
        @NamedQuery(name = "RPAdditionalFieldStorage.paginateByTipologiaProprieta.value.asc", query = "select rpdyn from RPAdditionalFieldStorage rpdyn left outer join rpdyn.anagrafica anagrafica where anagrafica.position = 0 and anagrafica.typo.id = ? order by anagrafica.value.sortValue asc"),
        @NamedQuery(name = "RPAdditionalFieldStorage.paginateByTipologiaProprieta.value.desc", query = "select rpdyn from RPAdditionalFieldStorage rpdyn left outer join rpdyn.anagrafica anagrafica where anagrafica.position = 0 and anagrafica.typo.id = ? order by anagrafica.value.sortValue desc"),
        @NamedQuery(name = "RPAdditionalFieldStorage.paginateEmptyById.value.asc", query = "select rpdyn from RPAdditionalFieldStorage rpdyn where rpdyn NOT IN (select rpdyn from RPAdditionalFieldStorage rpdyn left outer join rpdyn.anagrafica anagrafica where anagrafica.position = 0 and anagrafica.typo.id = ?) order by id asc"),
        @NamedQuery(name = "RPAdditionalFieldStorage.paginateEmptyById.value.desc", query = "select rpdyn from RPAdditionalFieldStorage rpdyn where rpdyn NOT IN (select rpdyn from RPAdditionalFieldStorage rpdyn left outer join rpdyn.anagrafica anagrafica where anagrafica.position = 0 and anagrafica.typo.id = ?) order by id desc"),
        @NamedQuery(name = "RPAdditionalFieldStorage.countNotEmptyByTipologiaProprieta", query = "select count(rpdyn) from RPAdditionalFieldStorage rpdyn left outer join rpdyn.anagrafica anagrafica where anagrafica.position = 0 and anagrafica.typo.id = ? "),
        @NamedQuery(name = "RPAdditionalFieldStorage.countEmptyByTipologiaProprieta", query = "select count(rpdyn) from RPAdditionalFieldStorage rpdyn where rpdyn NOT IN (select rpdyn from RPAdditionalFieldStorage rpdyn left outer join rpdyn.anagrafica anagrafica where anagrafica.position = 0 and anagrafica.typo.id = ?)"),
        @NamedQuery(name = "RPAdditionalFieldStorage.count", query = "select count(*) from RPAdditionalFieldStorage")
})
public class RPAdditionalFieldStorage extends AnagraficaObject<RPProperty, RPPropertiesDefinition> {

	@Id
	@GeneratedValue(generator = "RPDYNAADDITIONAL_SEQ")
	@SequenceGenerator(name = "RPDYNAADDITIONAL_SEQ", sequenceName = "RPDYNAADDITIONAL_SEQ")
	private Integer id;
	
	
	@OneToMany(mappedBy = "parent", fetch=FetchType.EAGER)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })	
	@OrderBy(clause="position asc")
	private List<RPProperty> anagrafica;
	
	public List<RPProperty> getAnagrafica() {
		if(this.anagrafica == null) {
			this.anagrafica = new LinkedList<RPProperty>();
		}
		return anagrafica;
	}

	public Class<RPProperty> getClassProperty() {
		return RPProperty.class;
	}

	public Class<RPPropertiesDefinition> getClassPropertiesDefinition() {
		return RPPropertiesDefinition.class;
	}

	public Integer getId() {
		return id;
	}

	public void setAnagraficaLazy(List<RPProperty> pp) {
		this.anagrafica = pp;		
	}

}
