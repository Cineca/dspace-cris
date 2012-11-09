/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package org.dspace.app.webui.cris.json;

import it.cilea.hku.authority.model.ResearcherPage;
import it.cilea.hku.authority.model.dynamicfield.BoxResearcherPage;
import it.cilea.hku.authority.model.dynamicfield.TabResearcherPage;
import it.cilea.hku.authority.webui.web.tag.ResearcherTagLibraryFunctions;
import it.cilea.osd.jdyna.web.controller.json.AjaxJSONNavigationController;


public class RPNavigationController
        extends
        AjaxJSONNavigationController<BoxResearcherPage, TabResearcherPage>
{

    public RPNavigationController()
    {
        super(TabResearcherPage.class);       
      
    }
      

    @Override
    public int countBoxPublicMetadata(Integer objectID,
            BoxResearcherPage box, boolean b)
    {        
        return ResearcherTagLibraryFunctions
        .countBoxPublicMetadata(getApplicationService().get(ResearcherPage.class, objectID), box, b);
    }

    @Override
    public boolean isBoxHidden(Integer objectID,
            BoxResearcherPage box)
    {
        return ResearcherTagLibraryFunctions.isBoxHidden(
                getApplicationService().get(ResearcherPage.class, objectID), box);
    }

    
}
