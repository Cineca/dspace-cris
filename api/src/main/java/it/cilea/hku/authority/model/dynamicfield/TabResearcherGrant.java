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

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="model_jdyna_tab_grant")
@NamedQueries( {
        @NamedQuery(name = "TabResearcherGrant.findAll", query = "from TabResearcherGrant order by priority asc"),
        @NamedQuery(name = "TabResearcherGrant.findPropertyHolderInTab", query = "from BoxResearcherGrant box where box in (select m from TabResearcherGrant tab join tab.mask m where tab.id = ?) order by priority"),
        @NamedQuery(name = "TabResearcherGrant.findTabsByHolder", query = "from TabResearcherGrant tab where :par0 in elements(tab.mask)"),
        @NamedQuery(name = "TabResearcherGrant.uniqueTabByShortName", query = "from TabResearcherGrant tab where shortName = ?"),
		@NamedQuery(name = "TabResearcherGrant.findByAccessLevel", query = "from TabResearcherGrant tab where visibility = ? order by priority")
})
public class TabResearcherGrant extends AbstractTab<BoxResearcherGrant> {

	/** Showed holder in this tab */
	@ManyToMany
	@JoinTable(name = "model_jdyna_tabgrant2boxgrant")	
	private List<BoxResearcherGrant> mask;

	
	public TabResearcherGrant() {
		this.visibility = VisibilityTabConstant.ADMIN;
	}
	
	@Override
	public List<BoxResearcherGrant> getMask() {
		if(this.mask == null) {
			this.mask = new LinkedList<BoxResearcherGrant>();
		}
		return this.mask;
	}

	@Override
	public void setMask(List<BoxResearcherGrant> mask) {
		this.mask = mask;
	}
}
