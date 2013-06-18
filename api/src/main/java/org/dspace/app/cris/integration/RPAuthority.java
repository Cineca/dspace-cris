/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package org.dspace.app.cris.integration;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.dspace.app.cris.model.CrisConstants;
import org.dspace.app.cris.model.ResearcherPage;
import org.dspace.app.cris.model.RestrictedField;
import org.dspace.app.cris.model.VisibilityConstants;
import org.dspace.app.cris.service.ApplicationService;
import org.dspace.app.cris.service.RelationPreferenceService;
import org.dspace.app.cris.util.ResearcherPageUtils;
import org.dspace.content.DCPersonName;
import org.dspace.content.DSpaceObject;
import org.dspace.content.authority.AuthorityVariantsSupport;
import org.dspace.content.authority.Choice;
import org.dspace.content.authority.ChoiceAuthority;
import org.dspace.content.authority.Choices;
import org.dspace.content.authority.NotificableAuthority;
import org.dspace.core.Context;
import org.dspace.core.LogManager;
import org.dspace.discovery.DiscoverQuery;
import org.dspace.discovery.DiscoverResult;
import org.dspace.discovery.SearchService;
import org.dspace.services.ConfigurationService;
import org.dspace.utils.DSpace;

/**
 * This class is the main point of integration beetween the Researcher Pages and
 * DSpace. It implements the contract of the Authority Control Framework
 * providing full support for the variants facility. It implements also the
 * callback interface to "record" rejection of potential matches.
 * 
 * @author cilea
 */
public class RPAuthority extends CRISAuthority implements
        AuthorityVariantsSupport, NotificableAuthority
{
    /** The logger */
    private static Logger log = Logger.getLogger(RPAuthority.class);

    /** The name as this ChoiceAuthority MUST be configurated */
    public final static String RP_AUTHORITY_NAME = "RPAuthority";

    /** The RPs database service layer */
    private ApplicationService applicationService;

    /** The RPs search service layer */
    private SearchService searchService;

    private ConfigurationService configurationService;

    private RelationPreferenceService relationPreferenceService;

    /**
     * Make sure that the class is fully initialized before use it
     */
    private void init()
    {
        if (applicationService == null && searchService == null)
        {
            DSpace dspace = new DSpace();
            applicationService = dspace.getServiceManager().getServiceByName(
                    "applicationService", ApplicationService.class);
            searchService = dspace.getServiceManager().getServiceByName(
                    "org.dspace.discovery.SearchService", SearchService.class);

            configurationService = dspace.getServiceManager().getServiceByName(
                    "org.dspace.services.ConfigurationService",
                    ConfigurationService.class);

            relationPreferenceService = dspace
                    .getServiceManager()
                    .getServiceByName(
                            "org.dspace.app.cris.service.RelationPreferenceService",
                            RelationPreferenceService.class);
        }
    }

    /**
     * Empty constructor needed by the DSpace plugin facility.
     */
    public RPAuthority()
    {
        // nothing to do, initialization is provided by the IoC Spring Frameword
    }

    /**
     * Return a list of choices performing a lucene query on the RPs names index
     * appending the wildchar to every word in the query (i.e. if the query
     * string is Chan Tse the search will be perfomed with Chan* Tse*). For any
     * matching RP will be returned choices for every variants form.
     * 
     * {@link ChoiceAuthority#getMatches(String, int, int, int, String)}
     * 
     * @param query
     *            the lookup string
     * @param collection
     *            (not used by this Authority)
     * @param locale
     *            (not used by this Authority)
     * @param start
     *            (not used by this Authority)
     * @param limit
     *            (not used by this Authority)
     * @param locale
     *            (not used by this Authority)
     * 
     * @return a Choices of RPs where a name form match the query string
     */
    @Override
    public Choices getMatches(String field, String query, int collection,
            int start, int limit, String locale)
    {
        try
        {
            init();
            if (query != null && query.length() > 2)
            {
                DCPersonName tmpPersonName = new DCPersonName(
                        query.toLowerCase());

                String luceneQuery = "";
                if (StringUtils.isNotBlank(tmpPersonName.getLastName()))
                {
                    luceneQuery += ClientUtils.escapeQueryChars(tmpPersonName
                            .getLastName().trim())
                            + (StringUtils.isNotBlank(tmpPersonName
                                    .getFirstNames()) ? "" : "*");
                }

                if (StringUtils.isNotBlank(tmpPersonName.getFirstNames()))
                {
                    luceneQuery += (luceneQuery.length() > 0 ? " " : "")
                            + ClientUtils.escapeQueryChars(tmpPersonName
                                    .getFirstNames().trim()) + "*";
                }
                luceneQuery = luceneQuery.replaceAll("\\\\ ", " ");
                DiscoverQuery discoverQuery = new DiscoverQuery();
                discoverQuery.setDSpaceObjectFilter(CrisConstants.RP_TYPE_ID);
                String filter = configurationService.getProperty("cris."
                        + RP_AUTHORITY_NAME
                        + ((field != null && !field.isEmpty()) ? "." + field
                                : "") + ".filter");
                if (filter != null)
                {
                    discoverQuery.addFilterQueries(filter);
                }

                discoverQuery
                        .setQuery("{!lucene q.op=AND df=crisauthoritylookup}("
                                + luceneQuery
                                + ") OR (\""
                                + luceneQuery.substring(0,
                                        luceneQuery.length() - 1) + "\")");
                discoverQuery.setMaxResults(50);
                DiscoverResult result = searchService.search(null,
                        discoverQuery, true);

                List<Choice> choiceList = new ArrayList<Choice>();

                for (DSpaceObject dso : result.getDspaceObjects())
                {
                    ResearcherPage rp = (ResearcherPage) dso;
                    choiceList
                            .add(new Choice(ResearcherPageUtils
                                    .getPersistentIdentifier(rp), rp
                                    .getFullName(), ResearcherPageUtils
                                    .getLabel(rp.getFullName(), rp)));

                    if (rp.getTranslatedName() != null
                            && rp.getTranslatedName().getVisibility() == VisibilityConstants.PUBLIC
                            && rp.getTranslatedName().getValue() != null)
                    {
                        choiceList.add(new Choice(ResearcherPageUtils
                                .getPersistentIdentifier(rp), rp
                                .getTranslatedName().getValue(),
                                ResearcherPageUtils.getLabel(rp
                                        .getTranslatedName().getValue(), rp)));
                    }

                    for (RestrictedField variant : rp.getVariants())
                    {
                        if (variant.getValue() != null
                                && variant.getVisibility() == VisibilityConstants.PUBLIC)
                        {
                            choiceList.add(new Choice(ResearcherPageUtils
                                    .getPersistentIdentifier(rp), variant
                                    .getValue(), ResearcherPageUtils.getLabel(
                                    variant.getValue(), rp)));
                        }
                    }
                }

                Choice[] results = new Choice[choiceList.size()];
                results = choiceList.toArray(results);
                return new Choices(results, 0, results.length,
                        Choices.CF_AMBIGUOUS, false, 0);
            }
            return new Choices(false);
        }
        catch (Exception e)
        {
            log.error("Error quering the RPAuthority - " + e.getMessage(), e);
            return new Choices(true);
        }
    }

    /**
     * Return a list of choices performing an exact query on the RP names (full,
     * Chinese, academinc, variants). For any matching RP will be returned
     * choices for every variants form, the default choice will be which that
     * match with the "query" string. This method is used by unattended
     * submssion only, interactive submission will use the
     * {@link RPAuthority#getMatches(String, String, int, int, int, String)}.
     * The confidence value of the returned Choices will be
     * {@link Choices#CF_UNCERTAIN} if there is only a RP that match with the
     * lookup string or {@link Choices#CF_AMBIGUOUS} if there are more RPs.
     * 
     * {@link ChoiceAuthority#getMatches(String, String, int, int, int, String)}
     * 
     * @param field
     *            (not used by this Authority)
     * @param text
     *            the lookup string
     * @param collection
     *            (not used by this Authority)
     * @param locale
     *            (not used by this Authority)
     * @return a Choices of RPs that have an exact string match between a name
     *         forms and the text lookup string
     */
    @Override
    public Choices getBestMatch(String field, String text, int collection,
            String locale)
    {

        try
        {
            init();
            List<Choice> choiceList = new ArrayList<Choice>();
            int totalResult = 0;
            if (text != null && text.length() > 2)
            {
                DiscoverQuery discoverQuery = new DiscoverQuery();
                discoverQuery.setDSpaceObjectFilter(CrisConstants.RP_TYPE_ID);
                String filter = configurationService.getProperty("cris."
                        + RP_AUTHORITY_NAME + ".filter");
                if (filter != null)
                {
                    discoverQuery.addFilterQueries(filter);
                }

                discoverQuery
                        .setQuery("{!lucene q.op=AND df=crisauthoritylookup}\""
                                + ClientUtils.escapeQueryChars(text.trim())
                                + "\"");
                discoverQuery.setMaxResults(50);
                DiscoverResult result = searchService.search(null,
                        discoverQuery, true);
                totalResult = (int) result.getTotalSearchResults();
                for (DSpaceObject dso : result.getDspaceObjects())
                {
                    ResearcherPage rp = (ResearcherPage) dso;
                    choiceList
                            .add(new Choice(rp.getCrisID(), text,
                                    ResearcherPageUtils.getLabel(
                                            rp.getFullName(), rp)));
                }
            }

            Choice[] results = new Choice[choiceList.size()];
            if (choiceList.size() > 0)
            {
                results = choiceList.toArray(results);

                if (totalResult == 1)
                {
                    return new Choices(results, 0, totalResult,
                            Choices.CF_UNCERTAIN, false, 0);
                }
                else
                {
                    return new Choices(results, 0, totalResult,
                            Choices.CF_AMBIGUOUS, false, 0);
                }
            }
            else
            {
                return new Choices(false);
            }
        }
        catch (Exception e)
        {
            log.error("Error quering the HKUAuthority - " + e.getMessage(), e);
            return new Choices(true);
        }
    }

    /**
     * Return a list of all not null "public" forms of the RP name.
     * 
     * @param key
     *            the researcher identifier (i.e. rp00024)
     * @param locale
     *            (not used by this Authority)
     * 
     * @return a list of all not null "public" forms of the RP name.
     */
    public List<String> getVariants(String key, String locale)
    {
        init();
        Integer id = ResearcherPageUtils.getRealPersistentIdentifier(key,
                ResearcherPage.class);
        if (id == null)
        {
            log.error(LogManager.getHeader(null, "getLabel",
                    "invalid key for hkuauthority key " + key));
            return null;
        }
        ResearcherPage rp = applicationService.get(ResearcherPage.class, id);
        List<String> publicNames = new ArrayList<String>();
        publicNames.add(rp.getFullName());
        if (rp.getTranslatedName() != null
                && rp.getTranslatedName().getVisibility() == VisibilityConstants.PUBLIC)
        {
            String value = rp.getTranslatedName().getValue();
            if (StringUtils.isNotBlank(value))
            {
                publicNames.add(value);
            }
        }

        if (rp.getPreferredName() != null
                && rp.getPreferredName().getVisibility() == VisibilityConstants.PUBLIC)
        {
            String value = rp.getPreferredName().getValue();
            if (StringUtils.isNotBlank(value))
            {
                publicNames.add(value);
            }
        }

        List<RestrictedField> variants = rp.getVariants();
        if (variants != null)
        {
            for (RestrictedField v : variants)
            {
                if (v.getVisibility() == VisibilityConstants.PUBLIC)
                {
                    String value = v.getValue();
                    if (StringUtils.isNotBlank(value))
                    {
                        publicNames.add(value);
                    }
                }
            }
        }

        return publicNames;
    }

    /**
     * Record the reject of a potential match so to avoid the same proposal to
     * happen in future.
     * 
     * @param itemID
     *            the id of the item that has been rejected
     * @param authorityKey
     *            the researcher identifier (i.e. rp00024)
     */
    public void reject(int itemID, String authorityKey)
    {
        init();
        ResearcherPage cris = applicationService
                .getResearcherByAuthorityKey(authorityKey);
        Context context = null;

        try
        {
            context = new Context();
            context.turnOffAuthorisationSystem();
            List<String> list = new ArrayList<String>();
            list.add(String.valueOf(itemID));
            relationPreferenceService.unlink(context, cris, "publications",
                    list);
            context.restoreAuthSystemState();
        }
        catch (SQLException e)
        {
            log.error(e.getMessage(), e);
        }
        finally
        {
            if(context!=null && context.isValid()) {
                context.abort();
            }
        }

    }

    /**
     * Record the reject of a potential match so to avoid the same proposal to
     * happen in future.
     * 
     * @param itemIDs
     *            the id of the items that has been rejected
     * @param authorityKey
     *            the researcher identifier (i.e. rp00024)
     */
    public void reject(int[] itemIDs, String authorityKey)
    {
        init();
        ResearcherPage cris = applicationService
                .getResearcherByAuthorityKey(authorityKey);
        Context context = null;
        try
        {
            context = new Context();
            context.turnOffAuthorisationSystem();
            List<String> list = new ArrayList<String>();
            for(int itemID : itemIDs) {
                list.add(String.valueOf(itemID));    
            }
            
            relationPreferenceService.unlink(context, cris, "publications",
                    list);
            context.restoreAuthSystemState();
        }
        catch (SQLException e)
        {
            log.error(e.getMessage(), e);
        }
        finally
        {
            if(context!=null && context.isValid()) {
                context.abort();
            }
        }
        
    }

    @Override
    protected int getCRISTargetTypeID()
        {
        return CrisConstants.RP_TYPE_ID;
        }

    @Override
    protected Class<ResearcherPage> getCRISTargetClass()
    {
        return ResearcherPage.class;
    }
}
