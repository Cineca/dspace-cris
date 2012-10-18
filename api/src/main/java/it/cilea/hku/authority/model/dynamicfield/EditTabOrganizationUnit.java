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

import it.cilea.osd.jdyna.web.AbstractEditTab;

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
@Table(name = "cris_ou_edittab")
@NamedQueries({
		@NamedQuery(name = "EditTabOrganizationUnit.findAll", query = "from EditTabOrganizationUnit order by priority asc"),
		@NamedQuery(name = "EditTabOrganizationUnit.findPropertyHolderInTab", query = "from BoxOrganizationUnit box where box in (select m from EditTabOrganizationUnit tab join tab.mask m where tab.id = ?) order by priority"),
		@NamedQuery(name = "EditTabOrganizationUnit.findTabsByHolder", query = "from EditTabOrganizationUnit tab where :par0 in elements(tab.mask)"),
		@NamedQuery(name = "EditTabOrganizationUnit.uniqueByDisplayTab", query = "from EditTabOrganizationUnit tab where displayTab.id = ?"),
		@NamedQuery(name = "EditTabOrganizationUnit.uniqueTabByShortName", query = "from EditTabOrganizationUnit tab where shortName = ?"), 
		@NamedQuery(name = "EditTabOrganizationUnit.findByAccessLevel", query = "from EditTabOrganizationUnit tab where visibility = ? order by priority"),
		@NamedQuery(name = "EditTabOrganizationUnit.findByAdmin", query = "from EditTabOrganizationUnit tab where visibility = 1 or visibility = 2 or visibility = 3 order by priority"),
	    @NamedQuery(name = "EditTabOrganizationUnit.findByOwner", query = "from EditTabOrganizationUnit tab where visibility = 0 or visibility = 2 or visibility = 3 order by priority"),
	    @NamedQuery(name = "EditTabOrganizationUnit.findByAnonimous", query = "from EditTabOrganizationUnit tab where visibility = 3 order by priority")
})
public class EditTabOrganizationUnit extends
		AbstractEditTab<BoxProject,TabProject> {

	/** Showed holder in this tab */
	@ManyToMany
	@JoinTable(name = "cris_ou_edittab2box")
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private List<BoxProject> mask;

	@OneToOne
	private TabProject displayTab;

	public EditTabOrganizationUnit() {
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
