/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package org.dspace.app.cris.model;

import it.cilea.osd.jdyna.model.PropertiesDefinition;
import it.cilea.osd.jdyna.model.Property;

import java.util.HashMap;
import java.util.Map;

public class CrisConstants {
	public static final int CRIS_TYPE_ID_START = 9;
	public static final int RP_TYPE_ID = 9;
	public static final int PROJECT_TYPE_ID = 10;
	public static final int OU_TYPE_ID = 11;
	
	//injected via bean configuration for dynamic jdyna entity
	public static Map<String, Integer> otherTypeMap = new HashMap<String, Integer>();
	public static Map<String, String> authorityPrefixMap = new HashMap<String, String>();
	
	public static <T extends ACrisObject<P, TP>, P extends Property<TP>, TP extends PropertiesDefinition> Integer getEntityType(T crisObject) {
	    return crisObject.getType();
	}
	
	public static <T extends ACrisObject<P, TP>, P extends Property<TP>, TP extends PropertiesDefinition> String getAuthorityPrefix(T crisObject) {
        return crisObject.getAuthorityPrefix();
    }

  
    public void setOtherTypeMap(Map<String, Integer> otherTypeMap)
    {
        CrisConstants.otherTypeMap = otherTypeMap;
    }
    
    public void setAuthorityPrefixMap(Map<String, String> authorityPrefixMap)
    {
        CrisConstants.authorityPrefixMap = authorityPrefixMap;
    }
}
