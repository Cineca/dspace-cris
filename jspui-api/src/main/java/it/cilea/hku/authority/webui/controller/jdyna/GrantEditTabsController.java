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
package it.cilea.hku.authority.webui.controller.jdyna;

import it.cilea.hku.authority.model.dynamicfield.BoxResearcherGrant;
import it.cilea.hku.authority.model.dynamicfield.EditTabResearcherGrant;

/**
 * Concrete SpringMVC controller is used to list, delete and view detail of tab
 * 
 * @author pascarelli
 * 
 */
public class GrantEditTabsController extends ATabsController<BoxResearcherGrant, EditTabResearcherGrant> {

	
	
	public GrantEditTabsController(Class<EditTabResearcherGrant> tabsClass) {
		super(tabsClass);
	}

	

	
}
