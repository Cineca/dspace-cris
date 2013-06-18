package org.dspace.app.webui.cris.components.statistics;

import it.cilea.osd.jdyna.model.PropertiesDefinition;

import org.dspace.app.cris.model.jdyna.ProjectNestedPropertiesDefinition;
import org.dspace.app.cris.model.jdyna.ProjectPropertiesDefinition;

public class StatCrisPJDownloadSelectedObjectComponent extends
        StatCrisDownloadSelectedObjectComponent
{

    @Override
    protected PropertiesDefinition innerCall(Integer pkey)
    {

        PropertiesDefinition def = getApplicationService().get(
                ProjectPropertiesDefinition.class, pkey);
        if (def == null)
        {
            def = getApplicationService().get(ProjectNestedPropertiesDefinition.class,
                    pkey);
        }

        return def;
    }

}
