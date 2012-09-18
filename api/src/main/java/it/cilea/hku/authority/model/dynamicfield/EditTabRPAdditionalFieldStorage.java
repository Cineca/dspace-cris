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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "model_jdyna_edittab")
@NamedQueries({
		@NamedQuery(name = "EditTabRPAdditionalFieldStorage.findAll", query = "from EditTabRPAdditionalFieldStorage order by priority asc"),
		@NamedQuery(name = "EditTabRPAdditionalFieldStorage.findPropertyHolderInTab", query = "from BoxRPAdditionalFieldStorage box where box in (select m from EditTabRPAdditionalFieldStorage tab join tab.mask m where tab.id = ?) order by priority"),
		@NamedQuery(name = "EditTabRPAdditionalFieldStorage.findTabsByHolder", query = "from EditTabRPAdditionalFieldStorage tab where :par0 in elements(tab.mask)"),
		@NamedQuery(name = "EditTabRPAdditionalFieldStorage.uniqueByDisplayTab", query = "from EditTabRPAdditionalFieldStorage tab where displayTab.id = ?"),
		@NamedQuery(name = "EditTabRPAdditionalFieldStorage.uniqueTabByShortName", query = "from EditTabRPAdditionalFieldStorage tab where shortName = ?"), 
		@NamedQuery(name = "EditTabRPAdditionalFieldStorage.findByAccessLevel", query = "from EditTabRPAdditionalFieldStorage tab where visibility = ? order by priority")
})
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EditTabRPAdditionalFieldStorage extends
		AbstractEditTab<BoxRPAdditionalFieldStorage,TabRPAdditionalFieldStorage> {

	/** Showed holder in this tab */
	@ManyToMany
	@JoinTable(name = "model_jdyna_edittab2box")
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private List<BoxRPAdditionalFieldStorage> mask;

	@OneToOne
	private TabRPAdditionalFieldStorage displayTab;

	public EditTabRPAdditionalFieldStorage() {
		this.visibility = VisibilityTabConstant.ADMIN;
	}
	
	@Override
	public List<BoxRPAdditionalFieldStorage> getMask() {
		if (this.mask == null) {
			this.mask = new LinkedList<BoxRPAdditionalFieldStorage>();
		}
		return this.mask;
	}

	@Override
	public void setMask(List<BoxRPAdditionalFieldStorage> mask) {
		this.mask = mask;
	}

	public void setDisplayTab(TabRPAdditionalFieldStorage displayTab) {
		this.displayTab = displayTab;
	}

	public TabRPAdditionalFieldStorage getDisplayTab() {
		return displayTab;
	}


	@Override
	public Class<TabRPAdditionalFieldStorage> getDisplayTabClass() {
		return TabRPAdditionalFieldStorage.class;
	}
	
}
