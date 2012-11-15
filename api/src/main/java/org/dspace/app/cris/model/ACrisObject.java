/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package org.dspace.app.cris.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.cilea.osd.common.model.Identifiable;
import it.cilea.osd.jdyna.model.AnagraficaSupport;
import it.cilea.osd.jdyna.model.PropertiesDefinition;
import it.cilea.osd.jdyna.model.Property;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang.StringUtils;
import org.dspace.app.cris.util.ResearcherPageUtils;
import org.dspace.browse.BrowsableDSpaceObject;
import org.dspace.content.DCValue;
import org.dspace.content.DSpaceObject;
import org.dspace.content.authority.Choices;

@MappedSuperclass
public abstract class ACrisObject<P extends Property<TP>, TP extends PropertiesDefinition>
        extends DSpaceObject implements UUIDSupport, Identifiable,
        AnagraficaSupport<P, TP>, BrowsableDSpaceObject
{
    /** Cris internal unique identifier, must be null */
    @Column(nullable = true, unique = true)
    private String sourceID;

    private Boolean status;

    @Column(nullable = false, unique = true)
    private String uuid;

    public ACrisObject()
    {
        this.status = false;
    }

    public Boolean getStatus()
    {
        return status;
    }

    public void setStatus(Boolean status)
    {
        this.status = status;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public String getUuid()
    {
        return uuid;
    }

    public void setSourceID(String sourceID)
    {
        this.sourceID = sourceID;
    }

    public String getSourceID()
    {
        return sourceID;
    }

    public abstract String getPublicPath();    
    public abstract String getAuthorityPrefix();

    @Override
    public String getHandle()
    {
        return null;
    }

    @Override
    public int getID()
    {
        return getId() != null ? getId().intValue() : -1;
    }

    @Override
    public boolean isArchived()
    {
        return getStatus() != null ? getStatus() : false;
    }

    @Override
    public boolean isWithdrawn()
    {
        return getStatus() != null ? !getStatus() : false;
    }

    @Override
    public DCValue[] getMetadata(String schema, String element,
            String qualifier, String lang)
    {
        if (!schema.equalsIgnoreCase("cris" + this.getPublicPath()))
        {
            return new DCValue[0];
        }

        element = getCompatibleJDynAShortName(this, element);

        List<P> proprieties = this.getAnagrafica4view().get(element);
        List values = new ArrayList();
        String authority = null;
        if (proprieties != null)
        {
            for (P prop : proprieties)
            {
                Object val = prop.getObject();
                if (StringUtils.isNotEmpty(qualifier)
                        && val instanceof ACrisObject)
                {
                    authority = ResearcherPageUtils.getPersistentIdentifier((ACrisObject) val);
                    qualifier = getCompatibleJDynAShortName((ACrisObject) val,
                            qualifier);
                    List pointProps = (List) ((ACrisObject) val)
                            .getAnagrafica4view().get(qualifier);
                    if (pointProps != null && pointProps.size() > 0)
                    {
                        for (Object pprop : pointProps)
                        {
                            values.add(((Property) pprop).getObject());
                        }
                    }
                }
                else if (val instanceof ACrisObject)
                {
                    authority = ResearcherPageUtils.getPersistentIdentifier((ACrisObject) val);
                    values.add(((ACrisObject) val).getName());
                }
                else
                {
                    values.add(val);
                }
            }
        }

        DCValue[] result = new DCValue[values.size()];
        for (int idx = 0; idx < values.size(); idx++)
        {
            result[idx] = new DCValue();
            result[idx].schema = schema;
            result[idx].element = element;
            result[idx].qualifier = qualifier;
            result[idx].authority = authority;
            result[idx].confidence = StringUtils.isNotEmpty(authority) ? Choices.CF_ACCEPTED
                    : Choices.CF_UNSET;
            result[idx].value = values.get(idx).toString();
        }
        return result;
    }

    private String getCompatibleJDynAShortName(ACrisObject aCrisObject,
            String element)
    {
        Set<String> keys = aCrisObject.getAnagrafica4view().keySet();
        if (!keys.contains(element))
        {
            // DSpace is case insensitive, metadata are all lowercase
            for (String key : keys)
            {
                if (key.replaceAll("[\\-_]", "").equalsIgnoreCase(element))
                {
                    return key;
                }
            }
        }
        return element;
    }

    @Override
    public String toString()
    {
        return getName();
    }
}
