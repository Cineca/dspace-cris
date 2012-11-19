/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package org.dspace.app.cris.integration;


import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.dspace.app.cris.model.ResearcherPage;
import org.dspace.app.cris.model.RestrictedField;
import org.dspace.app.cris.service.ApplicationService;
import org.dspace.app.cris.util.ResearcherPageUtils;
import org.dspace.browse.BrowseEngine;
import org.dspace.browse.BrowseIndex;
import org.dspace.browse.BrowseInfo;
import org.dspace.browse.BrowserScope;
import org.dspace.content.DCValue;
import org.dspace.content.Item;
import org.dspace.content.MetadataField;
import org.dspace.content.MetadataSchema;
import org.dspace.content.authority.Choices;
import org.dspace.core.ConfigurationManager;
import org.dspace.core.Context;

/**
 * Utility class for performing Item to ReseacherPage binding
 * 
 * @author cilea
 * 
 */
public class BindItemToRP
{
    /** the logger */
    private static Logger log = Logger.getLogger(BindItemToRP.class);

    /**
     * the name of the browse index where lookup for potential matches.
     * Configured in the dspace.cfg with the property
     * <code>researcherpage.browseindex</code>
     */
    private static final String researcherPotentialMatchLookupBrowserIndex = ConfigurationManager
            .getProperty("researcherpage.browseindex");

    /**
     * Search potential matches for all the ResearcherPage supplied. The
     * algorithm search for any researcher page and any researcher's name
     * (regardless the visibility attribute) all the items published in DSpace
     * using the Browse System (@link #researcherPotentialMatchLookupBrowserIndex}, if a match is found
     * and there is not an existent authority key for the metadata then the rp
     * identifier of the matching researcher page is used as authority key and a
     * confidence value is attributed as follow:
     * <ul>
     * <li>{@link Choices.CF_UNCERTAIN} if there is only a potential matching
     * researcher page</li>
     * <li>{@link Choices.CF_AMBIGUOUS} if there are more than one potential
     * matching reseacher pages</li>
     * </ul>
     * 
     * @param rps
     *            the list of ResearcherPage
     * @param applicationService
     *            the ApplicationService
     * 
     * @see #researcherPotentialMatchLookupBrowserIndex
     * @see Choices#CF_UNCERTAIN
     * @see Choices#CF_AMBIGUOUS
     * 
     */
    public static void work(List<ResearcherPage> rps,
            ApplicationService applicationService)
    {
        log.debug("Working...building names list");
        List<NameResearcherPage> names = new LinkedList<NameResearcherPage>();
        for (ResearcherPage researcher : rps)
        {
            String id_padded = ResearcherPageUtils
                    .getPersistentIdentifier(researcher);
            NameResearcherPage name = new NameResearcherPage(researcher
                    .getFullName(), id_padded, researcher.getRejectItems());
            names.add(name);
            RestrictedField field = researcher.getPreferredName();
            if (field != null && field.getValue() != null
                    && !field.getValue().isEmpty())
            {
                NameResearcherPage name_1 = new NameResearcherPage(field
                        .getValue(), id_padded, researcher.getRejectItems());
                names.add(name_1);
            }
            field = researcher.getTranslatedName();
            if (field != null && field.getValue() != null
                    && !field.getValue().isEmpty())
            {
                NameResearcherPage name_2 = new NameResearcherPage(field
                        .getValue(), id_padded, researcher.getRejectItems());
                names.add(name_2);
            }
            for (RestrictedField r : researcher.getVariants())
            {
                if (r != null && r.getValue() != null
                        && !r.getValue().isEmpty())
                {
                    NameResearcherPage name_3 = new NameResearcherPage(r
                            .getValue(), id_padded, researcher.getRejectItems());
                    names.add(name_3);
                }
            }

        }
        log.debug("...DONE building names list size "+names.size());
        log.debug("Create DSpace context and use browse indexing");
        Context context = null;
        try
        {
            context = new Context();
            context.setIgnoreAuthorization(true);
            
            //find all metadata with authority support
            MetadataField[] fields = MetadataField.findAll(context);
            List<MetadataField> fieldsWithAuthoritySupport = new LinkedList<MetadataField>();
            for (MetadataField field : fields)
            {
                String schema = (MetadataSchema.find(context, field
                        .getSchemaID())).getName();
                String mdstring = schema
                        + "."
                        + field.getElement()
                        + (field.getQualifier() == null ? "" : "."
                                + field.getQualifier());
                String choicesPlugin = ConfigurationManager
                        .getProperty("choices.plugin." + mdstring);
                if (choicesPlugin != null)
                {
                    choicesPlugin = choicesPlugin.trim();
                }
                if ((RPAuthority.RP_AUTHORITY_NAME.equals(choicesPlugin)))
                {
                    fieldsWithAuthoritySupport.add(field);
                }
            }
            
            BrowseIndex bi = BrowseIndex
                    .getBrowseIndex(researcherPotentialMatchLookupBrowserIndex);
            // now start up a browse engine and get it to do the work for us
            BrowseEngine be = new BrowseEngine(context);
            int count = 1;
            for (NameResearcherPage tempName : names)
            {
                log.info("work on " + tempName.getName() + " with identifier "
                        + tempName.getPersistentIdentifier() + " (" + count
                        + " of " + names.size() + ")");
                // set up a BrowseScope and start loading the values into it
                BrowserScope scope = new BrowserScope(context);
                scope.setBrowseIndex(bi);
                // scope.setOrder(order);
                scope.setFilterValue(tempName.getName());
                // scope.setFilterValueLang(valueLang);
                // scope.setJumpToItem(focus);
                // scope.setJumpToValue(valueFocus);
                // scope.setJumpToValueLang(valueFocusLang);
                // scope.setStartsWith(startsWith);
                // scope.setOffset(offset);
                scope.setResultsPerPage(Integer.MAX_VALUE);
                // scope.setSortBy(sortBy);
                scope.setBrowseLevel(1);
                // scope.setEtAl(etAl);

                BrowseInfo binfo = be.browse(scope);
                log.info("Find " + binfo.getResultCount()
                        + "item(s) in browsing...");
                for (Item item : binfo.getItemResults(context))
                {
                    if (tempName.getRejectItems() != null
                            && tempName.getRejectItems().contains(item.getID()))
                    {
                        log
                                .warn("Item has been reject for this authority - itemID "
                                        + item.getID());
                    }
                    else
                    {
                        boolean modified = false;
                        

                        DCValue[] values = null;
                        for (MetadataField md : fieldsWithAuthoritySupport)
                        {
                            String schema = (MetadataSchema.find(context, md
                                    .getSchemaID())).getName();

                            values = item.getMetadata(schema, md.getElement(),
                                    md.getQualifier(), Item.ANY);
                            item.clearMetadata(schema, md.getElement(), md
                                    .getQualifier(), Item.ANY);
                            for (DCValue value : values)
                            {

                                long matches = 0;

                                if (value.authority == null
                                        && value.value.equals(tempName
                                                .getName()))
                                {
                                    log.debug("finding author...");
                                    matches = applicationService
                                            .countNamesMatchingExceptResearcher(
                                                    tempName.getName(),
                                                    Integer
                                                            .parseInt(tempName
                                                                    .getPersistentIdentifier()
                                                                    .substring(
                                                                            2)));
                                    log.debug("result: "
                                            + matches
                                            + " it will be a "
                                            + (matches == 0 ? "UNCERTAIN"
                                                    : "AMBIGUOS")
                                            + " confidence");
                                    item.addMetadata(value.schema,
                                            value.element, value.qualifier,
                                            value.language, tempName.getName(),
                                            tempName.getPersistentIdentifier(),
                                            matches == 0 ? Choices.CF_UNCERTAIN
                                                    : Choices.CF_AMBIGUOUS);
                                    modified = true;
                                }
                                else
                                {
                                    item.addMetadata(value.schema,
                                            value.element, value.qualifier,
                                            value.language, value.value,
                                            value.authority, value.confidence);
                                }
                                log
                                        .debug("Update item with id "
                                                + item.getID());
                                log.debug("Adding metadata "
                                        + value.schema
                                        + "."
                                        + value.element
                                        + "."
                                        + value.qualifier
                                        + "."
                                        + value.language
                                        + " value:"
                                        + tempName.getName()
                                        + " authority:"
                                        + tempName.getPersistentIdentifier()
                                        + " confidence:"
                                        + (matches == 0 ? Choices.CF_UNCERTAIN
                                                : Choices.CF_AMBIGUOUS));
                            }
                            values = null;
                        }
                        if (modified)
                        {
                            item.update();
                        }
                        context.commit();
                        context.clearCache();
                    }
                }
                count++;
            }

        }
        catch (Exception e)
        {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
        finally
        {
            if (context != null && context.isValid())
            {
                context.abort();
            }
        }

    }
}

/**
 * Support class to build the full list of names to process in the BindItemToRP
 * work method
 * 
 * @author cilea
 * 
 */
class NameResearcherPage
{
    /** the name form to lookup for */
    private String name;

    /** the rp identifier */
    private String persistentIdentifier;

    /** the ids of previous rejected matches */
    private Set<Integer> rejectItems;

    public NameResearcherPage(String name, String id, Set<Integer> rejectItems)
    {
        this.name = name;
        this.persistentIdentifier = id;
        this.rejectItems = rejectItems;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPersistentIdentifier()
    {
        return persistentIdentifier;
    }

    public void setPersistentIdentifier(String persistentIdentifier)
    {
        this.persistentIdentifier = persistentIdentifier;
    }

    public Set<Integer> getRejectItems()
    {
        return rejectItems;
    }

    public void setRejectItems(Set<Integer> rejectItems)
    {
        this.rejectItems = rejectItems;
    }

}
