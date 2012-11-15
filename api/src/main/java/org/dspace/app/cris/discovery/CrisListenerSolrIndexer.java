/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package org.dspace.app.cris.discovery;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.dspace.app.cris.model.ACrisObject;
import org.dspace.core.ConfigurationManager;
import org.dspace.core.Email;
import org.dspace.core.I18nUtil;
import org.hibernate.event.PostDeleteEvent;
import org.hibernate.event.PostDeleteEventListener;
import org.hibernate.event.PostInsertEvent;
import org.hibernate.event.PostInsertEventListener;
import org.hibernate.event.PostUpdateEvent;
import org.hibernate.event.PostUpdateEventListener;

/**
 * This listener is used to keep the Search Solr core up-to-date with change to Grant fields. 
 * All the Event that are not related to the ResearcherGrant Entity will be ignored. 
 * When a change happen the solr document related to the Grant will be updated.
 * 
 * @see RPSolrIndexer#index(ResearcherGrant, boolean)
 * @author pascarelli
 * 
 */
public class CrisListenerSolrIndexer implements PostUpdateEventListener, 
    PostDeleteEventListener, PostInsertEventListener
{
    private static Logger log = Logger
            .getLogger(CrisListenerSolrIndexer.class);
    
    private CrisSearchService crisSearchService;

    public CrisSearchService getCrisSearchService()
    {
        return crisSearchService;
    }
    
    public void setCrisSearchService(CrisSearchService crisSearchService)
    {
        this.crisSearchService = crisSearchService;
    }
    
    @Override
    public void onPostUpdate(PostUpdateEvent event)
    {
        Object object = event.getEntity();
        if (!(object instanceof ACrisObject))
        {
            // nothing to do
            return;
        }

        ACrisObject cris = (ACrisObject) object;

        try
        {
            crisSearchService.indexCrisObject(cris, false);    
        }
        catch (Exception e)
        {
            log.error("Failed to update CRIS metadata in discovery index for cris-"
                    + cris.getPublicPath() + " uuid:"+cris.getUuid());
            emailException(e);
        }
    }
    
    @Override
    public void onPostInsert(PostInsertEvent event)
    {
        Object object = event.getEntity();
        if (!(object instanceof ACrisObject))
        {
            // nothing to do
            return;
        }

        ACrisObject cris = (ACrisObject) object;

        try
        {
            crisSearchService.indexCrisObject(cris, false);    
        }
        catch (Exception e)
        {
            log.error("Failed to insert CRIS metadata in discovery index for cris-"
                    + cris.getPublicPath() + " uuid:"+cris.getUuid());
            emailException(e);
        }    }
    
    @Override
    public void onPostDelete(PostDeleteEvent event)
    {
        Object object = event.getEntity();
        if (!(object instanceof ACrisObject))
        {
            // nothing to do
            return;
        }

        ACrisObject cris = (ACrisObject) object;

        try
        {
            crisSearchService.unIndexContent(null, cris, false);    
        }
        catch (Exception e)
        {
            log.error("Failed to remove CRIS metadata in discovery index for cris-"
                    + cris.getPublicPath() + " uuid:"+cris.getUuid());
            emailException(e);
        }
   }
    
    private void emailException(Exception exception) {
        // Also email an alert, system admin may need to check for stale lock
        try {
            String recipient = ConfigurationManager
                    .getProperty("alert.recipient");

            if (recipient != null) {
                Email email = ConfigurationManager
                        .getEmail(I18nUtil.getEmailFilename(
                                Locale.getDefault(), "internal_error"));
                email.addRecipient(recipient);
                email.addArgument(ConfigurationManager
                        .getProperty("dspace.url"));
                email.addArgument(new Date());

                String stackTrace;

                if (exception != null) {
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    exception.printStackTrace(pw);
                    pw.flush();
                    stackTrace = sw.toString();
                } else {
                    stackTrace = "No exception";
                }

                email.addArgument(stackTrace);
                email.send();
            }
        } catch (Exception e) {
            // Not much we can do here!
            log.warn("Unable to send email alert", e);
        }
    }
}
