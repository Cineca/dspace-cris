/**
 * <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
 * <html><head>
 * <title>301 Moved Permanently</title>
 * </head><body>
 * <h1>Moved Permanently</h1>
 * <p>The document has moved <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>.</p>
 * </body></html>
 */
package it.cilea.hku.authority.dspace;

import java.util.List;

import org.apache.log4j.Logger;
import org.dspace.content.authority.AuthorityVariantsSupport;
import org.dspace.content.authority.ChoiceAuthority;
import org.dspace.content.authority.Choices;
import org.dspace.content.authority.NotificableAuthority;

/**
 * This class is the main point of integration beetween the Researcher Pages and
 * DSpace. It implements the contract of the Authority Control Framework
 * providing full support for the variants facility. It implements also the
 * callback interface to "record" rejection of potential matches.
 * 
 * @author cilea
 */
public class HKUAuthority implements ChoiceAuthority, AuthorityVariantsSupport, NotificableAuthority
{
    /** The logger */
    private static Logger log = Logger.getLogger(HKUAuthority.class);

    /** The name as this ChoiceAuthority MUST be configurated */
    public final static String HKU_AUTHORITY_MODE = "HKUAuthority";

    @Override
    public void reject(int itemID, String authorityKey)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void reject(int[] itemIDs, String authorityKey)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<String> getVariants(String key, String locale)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Choices getMatches(String field, String text, int collection,
            int start, int limit, String locale)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Choices getBestMatch(String field, String text, int collection,
            String locale)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getLabel(String field, String key, String locale)
    {
        // TODO Auto-generated method stub
        return null;
    }
    
   
}
