package org.dspace.app.webui.cris.components.statistics;

import org.dspace.app.cris.util.ResearcherPageUtils;

public class CrisStatBitstreamTopObjectComponent extends StatBitstreamTopObjectComponent
{

    @Override
    protected String getObjectId(String id)
    {      
        return ResearcherPageUtils.getPersistentIdentifier(Integer.parseInt(id), getTargetObjectClass());
    }
    
 
    
}
