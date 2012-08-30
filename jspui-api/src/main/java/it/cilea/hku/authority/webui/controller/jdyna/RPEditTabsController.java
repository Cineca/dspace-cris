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

import it.cilea.hku.authority.model.dynamicfield.BoxRPAdditionalFieldStorage;
import it.cilea.hku.authority.model.dynamicfield.EditTabRPAdditionalFieldStorage;

/**
 * Concrete SpringMVC controller is used to list, delete and view detail of tab
 * 
 * @author pascarelli
 * 
 */
public class RPEditTabsController extends ATabsController<BoxRPAdditionalFieldStorage, EditTabRPAdditionalFieldStorage> {

	
	
	public RPEditTabsController(Class<EditTabRPAdditionalFieldStorage> tabsClass) {
		super(tabsClass);
	}

	

	
}
