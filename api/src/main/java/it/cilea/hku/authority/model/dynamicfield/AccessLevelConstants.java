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

public class AccessLevelConstants {
	/**
	 * All grants on metadata value and visibility
	 */
	public final static Integer HIGH_ACCESS = 3;
	/**
	 * Only visibility edit
	 */
	public final static Integer STANDARD_ACCESS = 2;
	
	/**
	 * Hiding to RP
	 */
	public final static Integer ADMIN_ACCESS = 1;
	
	/**
	 * Nothing operation on metadata
	 */
	public final static Integer LOW_ACCESS = 0;
	
	public static List<Integer> getValues() {
		List<Integer> values = new LinkedList<Integer>();
		values.add(HIGH_ACCESS);
		values.add(STANDARD_ACCESS);
		values.add(ADMIN_ACCESS);
		values.add(LOW_ACCESS);
		return values;
	}
}
