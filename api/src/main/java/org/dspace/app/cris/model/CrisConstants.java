/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package org.dspace.app.cris.model;

import it.cilea.osd.jdyna.model.ANestedPropertiesDefinition;
import it.cilea.osd.jdyna.model.ANestedProperty;
import it.cilea.osd.jdyna.model.ATypeNestedObject;
import it.cilea.osd.jdyna.model.PropertiesDefinition;
import it.cilea.osd.jdyna.model.Property;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.dspace.app.cris.model.jdyna.ACrisNestedObject;
import org.dspace.content.Collection;
import org.dspace.content.Community;
import org.dspace.content.DSpaceObject;
import org.dspace.content.Item;
import org.dspace.core.Constants;

public class CrisConstants {
    public static final String CFG_MODULE = "cris";
    public static final String CFG_NETWORK_MODULE = "network";
	public static final int CRIS_TYPE_ID_START = 9;
	public static final int RP_TYPE_ID = 9;
	public static final int PROJECT_TYPE_ID = 10;
	public static final int OU_TYPE_ID = 11;
    public static final int NRP_TYPE_ID = 109;
    public static final int NPROJECT_TYPE_ID = 110;
    public static final int NOU_TYPE_ID = 111;
    public static final Integer CRIS_DYNAMIC_TYPE_ID_START = 1000;
    public static final Integer CRIS_NDYNAMIC_TYPE_ID_START = 10000;
    
	//injected via bean configuration for dynamic jdyna entity
	public static Map<String, Integer> typeMap = new HashMap<String, Integer>();
	public static Map<String, String> authorityPrefixMap = new HashMap<String, String>();
	
	public static <T extends DSpaceObject> Integer getEntityType(T crisObject) {
	    return crisObject.getType();
	}
	
	public static <T extends DSpaceObject> Integer getEntityType(Class<T> clazz) throws InstantiationException, IllegalAccessException {
	    if(Item.class.isAssignableFrom(clazz)) {
            return Constants.ITEM;
        }
        else if(Collection.class.isAssignableFrom(clazz)) {
            return Constants.COLLECTION;
        }
        else if(Community.class.isAssignableFrom(clazz)) {
            return Constants.COMMUNITY;
        }
        return CrisConstants.getEntityType(clazz.newInstance());
    }
	
	public static <T extends ACrisObject<P, TP, NP, NTP, ACNO, ATNO>, P extends Property<TP>, TP extends PropertiesDefinition, NP extends ANestedProperty<NTP>, NTP extends ANestedPropertiesDefinition, ACNO extends ACrisNestedObject<NP, NTP, P, TP>, ATNO extends ATypeNestedObject<NTP>> String getAuthorityPrefix(T crisObject) {
        return crisObject.getAuthorityPrefix();
    }

	public static String getEntityTypeText(Integer type) {
	    	    
        if(type >= CrisConstants.CRIS_TYPE_ID_START) {
            for(Entry<String, Integer> entry : typeMap.entrySet()) {
                if(type.equals(entry.getValue())) {
                    return entry.getKey();
                }
            }                
        }
        else {
            return Constants.typeText[type].toLowerCase();
        }        	    
	    return null;
	}
  
    public void setTypeMap(Map<String, Integer> otherTypeMap)
    {
        CrisConstants.typeMap = otherTypeMap;
    }
    
    public void setAuthorityPrefixMap(Map<String, String> authorityPrefixMap)
    {
        CrisConstants.authorityPrefixMap = authorityPrefixMap;
    }
}
