/**
 * <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
 * <html><head>
 * <title>301 Moved Permanently</title>
 * </head><body>
 * <h1>Moved Permanently</h1>
 * <p>The document has moved <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>.</p>
 * </body></html>
 */
package it.cilea.hku.authority.webui.controller.jdyna;

import it.cilea.hku.authority.model.dynamicfield.BoxResearcherGrant;
import it.cilea.hku.authority.model.dynamicfield.TabResearcherGrant;

/**
 * Concrete SpringMVC controller is used to list, delete and view detail of box
 * 
 * @author pascarelli
 * 
 */
public class GrantBoxsController
		extends
		ABoxsController<BoxResearcherGrant, TabResearcherGrant> {

	public GrantBoxsController(Class<BoxResearcherGrant> boxClass) {
		super(boxClass);
	}

}
