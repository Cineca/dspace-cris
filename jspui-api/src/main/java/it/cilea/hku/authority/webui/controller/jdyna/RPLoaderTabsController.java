package it.cilea.hku.authority.webui.controller.jdyna;

import it.cilea.hku.authority.model.ResearcherPage;
import it.cilea.hku.authority.model.dynamicfield.BoxResearcherPage;
import it.cilea.hku.authority.model.dynamicfield.TabResearcherPage;
import it.cilea.hku.authority.webui.web.tag.ResearcherTagLibraryFunctions;
import it.cilea.osd.jdyna.web.controller.AjaxOpenTabController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public class RPLoaderTabsController extends AjaxOpenTabController<BoxResearcherPage, TabResearcherPage>
{

    public RPLoaderTabsController()
    {
        super(TabResearcherPage.class);
    }
    
    @Override
    public boolean isBoxHidden(HttpServletRequest request, Integer objectID,
            BoxResearcherPage box)
    {
        return ResearcherTagLibraryFunctions.isBoxHidden(
                getApplicationService().get(ResearcherPage.class, objectID), box);
    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {        
        return super.loadTabs(request, response);
    }

}
