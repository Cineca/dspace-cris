package org.dspace.app.webui.cris.components.statistics;

import it.cilea.osd.jdyna.model.PropertiesDefinition;

import org.dspace.app.cris.model.jdyna.OUNestedPropertiesDefinition;
import org.dspace.app.cris.model.jdyna.OUPropertiesDefinition;

public class CrisStatOUDownloadTopObjectComponent extends
        CrisStatDownloadTopObjectComponent
{

    @Override
    protected PropertiesDefinition innerCall(Integer pkey)
    {

        PropertiesDefinition def = getApplicationService().get(
                OUPropertiesDefinition.class, pkey);
        if (def == null)
        {
            def = getApplicationService().get(OUNestedPropertiesDefinition.class,
                    pkey);
        }

        return def;
    }

}
