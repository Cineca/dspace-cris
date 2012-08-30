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
@Table(name="model_jdyna_tab")
@NamedQueries( {
        @NamedQuery(name = "TabRPAdditionalFieldStorage.findAll", query = "from TabRPAdditionalFieldStorage order by priority asc"),
        @NamedQuery(name = "TabRPAdditionalFieldStorage.findPropertyHolderInTab", query = "from BoxRPAdditionalFieldStorage box where box in (select m from TabRPAdditionalFieldStorage tab join tab.mask m where tab.id = ?) order by priority"),
        @NamedQuery(name = "TabRPAdditionalFieldStorage.findTabsByHolder", query = "from TabRPAdditionalFieldStorage tab where :par0 in elements(tab.mask)"),
        @NamedQuery(name = "TabRPAdditionalFieldStorage.uniqueTabByShortName", query = "from TabRPAdditionalFieldStorage tab where shortName = ?"),
		@NamedQuery(name = "TabRPAdditionalFieldStorage.findByAccessLevel", query = "from TabRPAdditionalFieldStorage tab where visibility = ? order by priority")
})
public class TabRPAdditionalFieldStorage extends AbstractTab<BoxRPAdditionalFieldStorage> {

	/** Showed holder in this tab */
	@ManyToMany
	@JoinTable(name = "model_jdyna_tab2box")	
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
