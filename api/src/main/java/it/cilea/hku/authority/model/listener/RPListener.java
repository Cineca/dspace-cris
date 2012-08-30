/**
 * <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
 * <html><head>
 * <title>301 Moved Permanently</title>
 * </head><body>
 * <h1>Moved Permanently</h1>
 * <p>The document has moved <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>.</p>
 * </body></html>
 */
package it.cilea.hku.authority.model.listener;

import it.cilea.hku.authority.model.ResearcherPage;
import it.cilea.hku.authority.model.RestrictedField;
import it.cilea.osd.common.core.SingleTimeStampInfo;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.event.PostLoadEvent;
import org.hibernate.event.PostLoadEventListener;
import org.hibernate.event.PreInsertEvent;
import org.hibernate.event.PreInsertEventListener;
import org.hibernate.event.PreUpdateEvent;
import org.hibernate.event.PreUpdateEventListener;

/**
 * This listner is used to keep track of changes in the RP name fields, all the
 * Event that are not related to the ResearcherPage Entity will be ignored. When
 * a change happen (adding a new form, removing an old one or change the
 * visibility flag) the <code>namesModifiedTimeStamp</code> field in the
 * Researcher Page is updated with the current date/timestamp.
 * 
 * @see ResearcherPage#getNamesModifiedTimeStamp()
 * @author cilea
 * 
 */
public class RPListener implements PreUpdateEventListener, PreInsertEventListener,
        PostLoadEventListener
{
    /**
     * After the loading of the data the listner store "the initial values" of
     * the name fields in a transient field so to compare it with the current
     * values before saving to discover changes
     */
    public void onPostLoad(PostLoadEvent event)
    {
        Object object = event.getEntity();
        if (object instanceof ResearcherPage)
        {
            ResearcherPage rp = (ResearcherPage) object;

            String oldNames = "";

            Set<String> oldNamesSet = new TreeSet<String>();
            if(rp.getFullName()!=null) {
                oldNamesSet.add(rp.getFullName());
            }
            if (rp.getAcademicName().getValue() != null)
            {
                oldNamesSet.add(rp.getAcademicName().getValue()+rp.getAcademicName().getVisibility());
            }
            if (rp.getChineseName().getValue() != null)
            {
                oldNamesSet.add(rp.getChineseName().getValue()+rp.getChineseName().getVisibility());
            }
            for (RestrictedField variant : rp.getVariants())
            {
                if (variant.getValue() != null)
                {
                    oldNamesSet.add(variant.getValue()+variant.getVisibility());
                }
            }

            for (String oldName : oldNamesSet)
            {
                oldNames += oldName;
            }

            rp.setOldNames(oldNames);

        }
    }

    /**
     * Compare the initial values of the name fields with the current values, if
     * a change is discovered the namesModifiedTimeStamp field of the
     * ResearcherPage is updated with the current date/timestamp
     */
    public boolean onPreUpdate(PreUpdateEvent event)
    {
        Object object = event.getEntity();
        if (object instanceof ResearcherPage)
        {
            ResearcherPage rp = (ResearcherPage) object;

            String newNames = "";
            String oldNames = rp.getOldNames();

            Set<String> newNamesSet = new TreeSet<String>();
            if(rp.getFullName()!=null) {
                newNamesSet.add(rp.getFullName());
            }
            if (rp.getAcademicName().getValue() != null) {
                newNamesSet.add(rp.getAcademicName().getValue()+rp.getAcademicName().getVisibility());
            }
            if (rp.getChineseName().getValue() != null)
            {
                newNamesSet.add(rp.getChineseName().getValue()+rp.getChineseName().getVisibility());
            }
            for (RestrictedField variant : rp.getVariants())
            {
                newNamesSet.add(variant.getValue()+rp.getChineseName().getVisibility());
            }

            for (String newName : newNamesSet)
            {
                newNames += newName;
            }
            if (
                 (
                    ((oldNames == null || newNames == null) && 
                            !(oldNames == null && newNames == null)) 
                    || (oldNames != null && newNames != null)
                        && oldNames.hashCode() != newNames.hashCode()
                  )
                )
            {
                int idx = 0;
                boolean found = false;
                for (String propName : event.getPersister().getPropertyNames())
                {
                    if ("namesModifiedTimeStamp".equals(propName))
                    {
                        found = true;
                        break;
                    }
                    idx++;
                }
                if (found)
                {
                    event.getState()[idx] = new SingleTimeStampInfo(new Date());
                }
            }
        }
        return false;
    }

    /**
     * Store in the namesModifiedTimeStamp field of the ResearcherPage the
     * current date/timestamp for every new ResearcherPage.
     */
    public boolean onPreInsert(PreInsertEvent event)
    {
        Object object = event.getEntity();
        if (object instanceof ResearcherPage)
        {
            int idx = 0;
            boolean found = false;
            for (String propName : event.getPersister().getPropertyNames())
            {
                if ("namesModifiedTimeStamp".equals(propName))
                {
                    found = true;
                    break;
                }
                idx++;
            }
            if (found)
            {
                event.getState()[idx] = new SingleTimeStampInfo(new Date());
            }
        }
        return false;
    }
}
