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
