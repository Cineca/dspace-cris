package it.cilea.hku.authority.webui.controller.jdyna;

import it.cilea.hku.authority.model.ResearcherPage;
import it.cilea.hku.authority.model.dynamicfield.BoxRPAdditionalFieldStorage;
import it.cilea.hku.authority.model.dynamicfield.TabRPAdditionalFieldStorage;
import it.cilea.hku.authority.webui.web.tag.ResearcherTagLibraryFunctions;
import it.cilea.osd.jdyna.web.controller.AjaxOpenTabController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public class RPLoaderTabsController extends AjaxOpenTabController<BoxRPAdditionalFieldStorage, TabRPAdditionalFieldStorage>
{

    public RPLoaderTabsController()
    {
        super(TabRPAdditionalFieldStorage.class);
    }
    
    @Override
    public boolean isBoxHidden(HttpServletRequest request, Integer objectID,
            BoxRPAdditionalFieldStorage box)
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
