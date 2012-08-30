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

import it.cilea.hku.authority.model.UUIDSupport;

import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.def.DefaultSaveOrUpdateEventListener;

public class UUIDListener extends DefaultSaveOrUpdateEventListener
{

    private void generateUUID(Object object)
    {
        UUIDSupport uuidOwner = (UUIDSupport) object;
        if (uuidOwner.getUuid() == null || uuidOwner.getUuid().isEmpty())
        {
            uuidOwner.setUuid(UUID.randomUUID().toString().trim());
        }
    }

    @Override
    public void onSaveOrUpdate(SaveOrUpdateEvent event)
            throws HibernateException
    {
        Object object = event.getObject();
        if (object instanceof UUIDSupport)
        {
            generateUUID(object);
        }
        super.onSaveOrUpdate(event);
    }

}
