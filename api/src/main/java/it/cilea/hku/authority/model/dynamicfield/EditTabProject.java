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
@Table(name = "model_jdyna_edittabgrant")
@NamedQueries({
		@NamedQuery(name = "EditTabProject.findAll", query = "from EditTabProject order by priority asc"),
		@NamedQuery(name = "EditTabProject.findPropertyHolderInTab", query = "from BoxProject box where box in (select m from EditTabProject tab join tab.mask m where tab.id = ?) order by priority"),
		@NamedQuery(name = "EditTabProject.findTabsByHolder", query = "from EditTabProject tab where :par0 in elements(tab.mask)"),
		@NamedQuery(name = "EditTabProject.uniqueByDisplayTab", query = "from EditTabProject tab where displayTab.id = ?"),
		@NamedQuery(name = "EditTabProject.uniqueTabByShortName", query = "from EditTabProject tab where shortName = ?"), 
		@NamedQuery(name = "EditTabProject.findByAccessLevel", query = "from EditTabProject tab where visibility = ? order by priority"),
		@NamedQuery(name = "EditTabProject.findByAdmin", query = "from EditTabProject tab where visibility = 1 or visibility = 2 or visibility = 3 order by priority"),
	    @NamedQuery(name = "EditTabProject.findByOwner", query = "from EditTabProject tab where visibility = 0 or visibility = 2 or visibility = 3 order by priority"),
	    @NamedQuery(name = "EditTabProject.findByAnonimous", query = "from EditTabProject tab where visibility = 3 order by priority")
})
public class EditTabProject extends
		AbstractEditTab<BoxProject,TabProject> {

	/** Showed holder in this tab */
	@ManyToMany
	@JoinTable(name = "model_jdyna_edittabgrant2boxgrant")
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private List<BoxProject> mask;

	@OneToOne
	private TabProject displayTab;

	public EditTabProject() {
		this.visibility = VisibilityTabConstant.ADMIN;
	}
	
	@Override
	public List<BoxProject> getMask() {
		if (this.mask == null) {
			this.mask = new LinkedList<BoxProject>();
		}
		return this.mask;
	}

	@Override
	public void setMask(List<BoxProject> mask) {
		this.mask = mask;
	}

	public void setDisplayTab(TabProject displayTab) {
		this.displayTab = displayTab;
	}

	public TabProject getDisplayTab() {
		return displayTab;
	}


	@Override
	public Class<TabProject> getDisplayTabClass() {
		return TabProject.class;
	}
	
	


}
