/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 * 
 * http://www.dspace.org/license/
 * 
 * The document has moved 
 * <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>
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
