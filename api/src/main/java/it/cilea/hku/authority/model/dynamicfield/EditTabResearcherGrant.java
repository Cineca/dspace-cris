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
		@NamedQuery(name = "EditTabResearcherGrant.findAll", query = "from EditTabResearcherGrant order by priority asc"),
		@NamedQuery(name = "EditTabResearcherGrant.findPropertyHolderInTab", query = "from BoxResearcherGrant box where box in (select m from EditTabResearcherGrant tab join tab.mask m where tab.id = ?) order by priority"),
		@NamedQuery(name = "EditTabResearcherGrant.findTabsByHolder", query = "from EditTabResearcherGrant tab where :par0 in elements(tab.mask)"),
		@NamedQuery(name = "EditTabResearcherGrant.uniqueByDisplayTab", query = "from EditTabResearcherGrant tab where displayTab.id = ?"),
		@NamedQuery(name = "EditTabResearcherGrant.uniqueTabByShortName", query = "from EditTabResearcherGrant tab where shortName = ?"), 
		@NamedQuery(name = "EditTabResearcherGrant.findByAccessLevel", query = "from EditTabResearcherGrant tab where visibility = ? order by priority")
})
public class EditTabResearcherGrant extends
		AbstractEditTab<BoxResearcherGrant,TabResearcherGrant> {

	/** Showed holder in this tab */
	@ManyToMany
	@JoinTable(name = "model_jdyna_edittabgrant2boxgrant")
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private List<BoxResearcherGrant> mask;

	@OneToOne
	private TabResearcherGrant displayTab;

	public EditTabResearcherGrant() {
		this.visibility = VisibilityTabConstant.ADMIN;
	}
	
	@Override
	public List<BoxResearcherGrant> getMask() {
		if (this.mask == null) {
			this.mask = new LinkedList<BoxResearcherGrant>();
		}
		return this.mask;
	}

	@Override
	public void setMask(List<BoxResearcherGrant> mask) {
		this.mask = mask;
	}

	public void setDisplayTab(TabResearcherGrant displayTab) {
		this.displayTab = displayTab;
	}

	public TabResearcherGrant getDisplayTab() {
		return displayTab;
	}


	@Override
	public Class<TabResearcherGrant> getDisplayTabClass() {
		return TabResearcherGrant.class;
	}
	
	


}
