/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package it.cilea.hku.authority.util;

import it.cilea.hku.authority.model.ACrisObject;
import it.cilea.hku.authority.model.CrisConstants;
import it.cilea.hku.authority.model.OrganizationUnit;
import it.cilea.hku.authority.model.Project;
import it.cilea.hku.authority.model.ResearcherPage;
import it.cilea.hku.authority.model.VisibilityConstants;
import it.cilea.hku.authority.service.ApplicationService;

import java.text.DecimalFormat;

import javax.persistence.Transient;

import org.apache.log4j.Logger;

/**
 * This class provides some static utility methods to extract information from
 * the rp identifier quering the RPs database.
 * 
 * @author cilea
 * 
 */
public class ResearcherPageUtils
{
    /** log4j logger */
    @Transient
    private static Logger log = Logger.getLogger(ResearcherPageUtils.class);
    
    /**
     * Formatter to build the rp identifier
     */
    private static DecimalFormat persIdentifierFormat = new DecimalFormat(
            "00000");

    /**
     * the applicationService to query the RP database, injected by Spring IoC
     */
    private static ApplicationService applicationService;

    /**
     * Constructor use by Spring IoC
     * 
     * @param applicationService
     *            the applicationService to query the RP database, injected by
     *            Spring IoC
     */
    public ResearcherPageUtils(ApplicationService applicationService)
    {
        ResearcherPageUtils.applicationService = applicationService;
    }

    /**
     * Build the public identifier (authority) of the supplied CRIS object
     * 
     * @param cris
     *            the cris object
     * @return the public identifier of the supplied CRIS object
     */
    public static String getPersistentIdentifier(ACrisObject cris)
    {
        return cris.getAuthorityPrefix(CrisConstants.authorityPrefixMap) + formatIdentifier(cris.getId());
    }

    
    /**
     * Build the cris identifier starting from the db internal primary key
    */
    public static String getPersistentIdentifier(Integer rp)
    {
        return persIdentifierFormat.format(rp);
    }
    
    
    /**
     * Format the cris suffix identifier starting from the db internal primary key
    */
    private static String formatIdentifier(Integer rp)
    {
        return persIdentifierFormat.format(rp);
    }

    /**
     * Extract the db primary key from a cris identifier
     * 
     * @param authorityKey
     *            the cris identifier
     * @return the db primary key
     */
    public static Integer getRealPersistentIdentifier(String authorityKey)
    {
        try
        {
            return Integer.parseInt(authorityKey.substring(2));
        }
        catch (NumberFormatException e)
        {
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * Return a label to use with a specific form of the name of the researcher.
     * 
     * @param alternativeName
     *            the form of the name to use
     * @param rp
     *            the researcher page
     * @return the label to use
     */
    public static String getLabel(String alternativeName, ResearcherPage rp)
    {
        if (alternativeName.equals(rp.getFullName()))
        {
            return rp.getFullName()
                    + (rp.getTranslatedName() != null
                            && rp.getTranslatedName().getVisibility() == VisibilityConstants.PUBLIC ? " "
                            + rp.getTranslatedName().getValue()
                            : "");
        }
        else
        {
            return alternativeName + " See \"" + rp.getFullName() + "\"";
        }
    }

    /**
     * Return a label to use with a specific form of the name of the researcher.
     * 
     * @param alternativeName
     *            the form of the name to use
     * @param rpKey
     *            the rp identifier of the ResearcherPage
     * @return the label to use
     */
    public static String getLabel(String alternativeName, String rpkey)
    {
        if (rpkey != null)
        {
            ResearcherPage rp = applicationService.get(ResearcherPage.class,
                    getRealPersistentIdentifier(rpkey));
            return getLabel(alternativeName, rp);
        }
        return alternativeName;
    }

    /**
     * Check if the supplied form is the fullname of the ResearcherPage
     * 
     * @param alternativeName
     *            the string to check
     * @param rpkey
     *            the rp identifier
     * @return true, if the form is the fullname of the ResearcherPage with the
     *         supplied rp identifier
     */
    public static boolean isFullName(String alternativeName, String rpkey)
    {
        if (alternativeName != null && rpkey != null)
        {
            ResearcherPage rp = applicationService.get(ResearcherPage.class,
                    getRealPersistentIdentifier(rpkey));
            return alternativeName.equals(rp.getFullName());
        }
        return false;
    }

    /**
     * Check if the supplied form is the Chinese name of the ResearcherPage
     * 
     * @param alternativeName
     *            the string to check
     * @param rpkey
     *            the rp identifier
     * @return true, if the form is the Chinese name of the ResearcherPage with
     *         the supplied rp identifier
     */
    public static boolean isChineseName(String alternativeName, String rpkey)
    {
        if (alternativeName != null && rpkey != null)
        {
            ResearcherPage rp = applicationService.get(ResearcherPage.class,
                    getRealPersistentIdentifier(rpkey));
            return alternativeName.equals(rp.getTranslatedName().getValue());
        }
        return false;
    }

    /**
     * Get the fullname of the ResearcherPage
     * 
     * @param rpkey
     *            the rp identifier
     * @return the fullname of the ResearcherPage
     */
    public static String getFullName(String rpkey)
    {
        if (rpkey != null)
        {
            ResearcherPage rp = applicationService.get(ResearcherPage.class,
                    getRealPersistentIdentifier(rpkey));
            return rp.getFullName();
        }
        return null;
    }

    /**
     * Get the staff number of the ResearcherPage
     * 
     * @param rpkey
     *            the rp identifier
     * @return the staff number of the ResearcherPage
     */
    public static String getStaffNumber(String rpkey)
    {
        if (rpkey != null)
        {
            ResearcherPage rp = applicationService.get(ResearcherPage.class,
                    getRealPersistentIdentifier(rpkey));
            return rp != null ? rp.getSourceID() : null;
        }
        return null;
    }

    /**
     * Get the rp identifier of the ResearcherPage with a given staffno
     * 
     * @param staffno
     *            the staffno
     * @return the rp identifier of the ResearcherPage or null
     */
    public static String getRPIdentifierByStaffno(String staffno)
    {
        if (staffno != null)
        {
            ResearcherPage rp = applicationService
                    .getResearcherPageByStaffNo(staffno);
            if (rp != null)
            {
                return getPersistentIdentifier(rp);
            }
        }
        return null;
    }

    /**
     * Get the ChineseName of the ResearcherPage
     * 
     * @param rpkey
     *            the rp identifier
     * @return the Chinese name of the ResearcherPage
     */
    public static String getChineseName(String rpkey)
    {
        if (rpkey != null)
        {
            ResearcherPage rp = applicationService.get(ResearcherPage.class,
                    getRealPersistentIdentifier(rpkey));
            return VisibilityConstants.PUBLIC == rp.getTranslatedName()
                    .getVisibility() ? rp.getTranslatedName().getValue() : "";
        }
        return null;
    }
  

}
