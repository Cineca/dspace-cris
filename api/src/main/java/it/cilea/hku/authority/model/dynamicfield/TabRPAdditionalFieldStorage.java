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

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CacheModeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="model_jdyna_tab")
@org.hibernate.annotations.NamedQueries( {
        @org.hibernate.annotations.NamedQuery(name = "TabRPAdditionalFieldStorage.findAll", query = "from TabRPAdditionalFieldStorage order by priority asc"),
        @org.hibernate.annotations.NamedQuery(name = "TabRPAdditionalFieldStorage.findPropertyHolderInTab", query = "from BoxRPAdditionalFieldStorage box where box in (select m from TabRPAdditionalFieldStorage tab join tab.mask m where tab.id = ?) order by priority", cacheable=true),
        @org.hibernate.annotations.NamedQuery(name = "TabRPAdditionalFieldStorage.findTabsByHolder", query = "from TabRPAdditionalFieldStorage tab where :par0 in elements(tab.mask)", cacheable=true),
        @org.hibernate.annotations.NamedQuery(name = "TabRPAdditionalFieldStorage.uniqueTabByShortName", query = "from TabRPAdditionalFieldStorage tab where shortName = ?", cacheable=true),
		@org.hibernate.annotations.NamedQuery(name = "TabRPAdditionalFieldStorage.findByAccessLevel", query = "from TabRPAdditionalFieldStorage tab where visibility = ? order by priority", cacheable=true),
		@org.hibernate.annotations.NamedQuery(name = "TabRPAdditionalFieldStorage.findByAdmin", query = "from TabRPAdditionalFieldStorage tab where visibility = 1 or visibility = 2 or visibility = 3 order by priority", cacheable=true),
		@org.hibernate.annotations.NamedQuery(name = "TabRPAdditionalFieldStorage.findByOwner", query = "from TabRPAdditionalFieldStorage tab where visibility = 0 or visibility = 2 or visibility = 3 order by priority", cacheable=true),
		@org.hibernate.annotations.NamedQuery(name = "TabRPAdditionalFieldStorage.findByAnonimous", query = "from TabRPAdditionalFieldStorage tab where visibility = 3 order by priority", cacheable=true)
})
public class TabRPAdditionalFieldStorage extends AbstractTab<BoxRPAdditionalFieldStorage> {

	/** Showed holder in this tab */
	@ManyToMany	
	@JoinTable(name = "model_jdyna_tab2box")
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private List<BoxRPAdditionalFieldStorage> mask;

	
	public TabRPAdditionalFieldStorage() {
		this.visibility = VisibilityTabConstant.ADMIN;
	}
	
	@Override
	public List<BoxRPAdditionalFieldStorage> getMask() {
		if(this.mask == null) {
			this.mask = new LinkedList<BoxRPAdditionalFieldStorage>();
		}
		return this.mask;
	}

	@Override
	public void setMask(List<BoxRPAdditionalFieldStorage> mask) {
		this.mask = mask;
	}
}
