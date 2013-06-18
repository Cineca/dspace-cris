package org.dspace.app.webui.cris.components.statistics;

import it.cilea.osd.jdyna.model.PropertiesDefinition;

import org.dspace.app.cris.model.jdyna.RPNestedPropertiesDefinition;
import org.dspace.app.cris.model.jdyna.RPPropertiesDefinition;

public class StatCrisRPDownloadSelectedObjectComponent extends
        StatCrisDownloadSelectedObjectComponent
{

    @Override
    protected PropertiesDefinition innerCall(Integer pkey)
    {

        PropertiesDefinition def = getApplicationService().get(
                RPPropertiesDefinition.class, pkey);
        if (def == null)
        {
            def = getApplicationService().get(RPNestedPropertiesDefinition.class,
                    pkey);
        }

        return def;
    }

}
