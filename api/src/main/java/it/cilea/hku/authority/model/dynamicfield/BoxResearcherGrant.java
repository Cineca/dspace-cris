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

import it.cilea.osd.jdyna.web.Box;
import it.cilea.osd.jdyna.web.Containable;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "model_jdyna_box_grant")
@NamedQueries({
		@NamedQuery(name = "BoxResearcherGrant.findAll", query = "from BoxResearcherGrant order by priority asc"),
		@NamedQuery(name = "BoxResearcherGrant.findContainableByHolder", query = "from Containable containable where containable in (select m from BoxResearcherGrant box join box.mask m where box.id = ?)"),
		@NamedQuery(name = "BoxResearcherGrant.findHolderByContainable", query = "from BoxResearcherGrant box where :par0 in elements(box.mask)"),
		@NamedQuery(name = "BoxResearcherGrant.uniqueBoxByShortName", query = "from BoxResearcherGrant box where shortName = ?")
})		
public class BoxResearcherGrant extends Box<Containable> {
	
	@ManyToMany
	@JoinTable(name = "model_jdyna_boxgrant2containablegrant")	
	private List<Containable> mask;

	public BoxResearcherGrant() {
		this.visibility = VisibilityTabConstant.ADMIN;
	}
	
	@Override
	public List<Containable> getMask() {
		if(this.mask==null) {
			this.mask = new LinkedList<Containable>();
		}		
		return mask;
	}

	@Override
	public void setMask(List<Containable> mask) {
		if(mask!=null) {
			Collections.sort(mask);
		}
		this.mask = mask;
	}

	
}
