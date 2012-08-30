/**
 * <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
 * <html><head>
 * <title>301 Moved Permanently</title>
 * </head><body>
 * <h1>Moved Permanently</h1>
 * <p>The document has moved <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>.</p>
 * </body></html>
 */
package it.cilea.hku.authority.model;

import org.apache.log4j.Logger;

/**
 * This class maintains the allowed values for the visibility flags
 * {@link RestrictedField#getVisibility()}
 * 
 * @author cilea
 * 
 */
public class VisibilityConstants
{
    /** log4j logger */
    private static Logger log = Logger.getLogger(VisibilityConstants.class);

    /**
     * the int constant for hide visibility flag
     */
    public static final int HIDE = 0;

    /**
     * the int constant for public visibility flag
     */
    public static final int PUBLIC = 1;

    /**
     * the canonical string form of public visibility flag
     */
    public static final String DESC_PUBLIC = "PUBLIC";
    
    /**
     * the canonical string form of hide visibility flag
     */
    public static final String DESC_HIDE = "HIDE";

    /**
     * Used to translate a string into a visibility constant
     * 
     * @param visibilityDescription
     *            the input string
     * @return the visibility value described by the input string
     */
    public static int getIDfromDescription(String visibilityDescription)
    {
        if (visibilityDescription.trim().isEmpty()
                || visibilityDescription.equalsIgnoreCase(DESC_PUBLIC))
        {
            return VisibilityConstants.PUBLIC;
        }
        if (visibilityDescription.equalsIgnoreCase(DESC_HIDE))
        {
            return VisibilityConstants.HIDE;
        }
        log.warn("Invalid visibility value '" + visibilityDescription
                + "' - setting default value");
        return VisibilityConstants.PUBLIC;
    }

    /**
     * Used to translate a visibility constant into a string description
     * 
     * @param visibility
     *            the integer value of visibilty
     * @return the visibility description
     */
    public static String getDescription(Integer visibility)
    {
        if(visibility!=null) {
            if(visibility==1) {
                return DESC_PUBLIC;
            }
            return DESC_HIDE;
        }
        log.warn("Invalid visibility value '" + visibility
                + "'");
        return null;
    }
}
