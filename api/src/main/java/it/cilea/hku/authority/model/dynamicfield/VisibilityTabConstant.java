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

public class VisibilityTabConstant {
	
	/**
	 * Show to all
	 */
	public final static Integer HIGH = 3;
	
	/**
	 * Tab and box show only to RP owner and admin
	 */
	public final static Integer STANDARD = 2;
	
	/**
	 * Hiding to RP, show only admin
	 */
	public final static Integer ADMIN = 1;	

	/**
	 * Tab and box show only to RP owner
	 */
	public final static Integer LOW = 0;
	
	public static List<Integer> getValues() {
		List<Integer> values = new LinkedList<Integer>();
		values.add(HIGH);
		values.add(STANDARD);
		values.add(ADMIN);
		values.add(LOW);		
		return values;
	}
	
	public static List<Integer> getEditValues() {
		List<Integer> values = new LinkedList<Integer>();
		values.add(STANDARD);
		values.add(ADMIN);
		values.add(LOW);		
		return values;
	}
}

