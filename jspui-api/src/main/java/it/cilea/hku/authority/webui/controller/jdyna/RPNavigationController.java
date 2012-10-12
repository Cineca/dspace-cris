package it.cilea.hku.authority.webui.controller.jdyna;

import it.cilea.hku.authority.model.ResearcherPage;
import it.cilea.hku.authority.model.dynamicfield.BoxRPAdditionalFieldStorage;
import it.cilea.hku.authority.model.dynamicfield.TabRPAdditionalFieldStorage;
import it.cilea.hku.authority.util.ResearcherPageUtils;
import it.cilea.hku.authority.webui.web.tag.ResearcherTagLibraryFunctions;
import it.cilea.osd.jdyna.web.controller.AjaxNavigationController;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dspace.core.ConfigurationManager;
import org.springframework.web.servlet.ModelAndView;

public class RPNavigationController
        extends
        AjaxNavigationController<BoxRPAdditionalFieldStorage, TabRPAdditionalFieldStorage>
{

    private List<String> publistFilters;

    public RPNavigationController()
    {
        super(TabRPAdditionalFieldStorage.class);        
        publistFilters = new ArrayList<String>();
        String menu = getPublicationMenu();
        if (menu != null)
        {
            String[] typesConf = menu.split(",");
            for (String type : typesConf)
            {
                publistFilters.add(type.trim());
            }
        }
    }

    private String getPublicationMenu()
    {
        return ConfigurationManager.getProperty("cris.publicationlist.menu");
    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {

        return super.loadNavigation(request, response);
    }

    @Override
    public int countBoxPublicMetadata(HttpServletRequest request, Integer objectID,
            BoxRPAdditionalFieldStorage box, boolean b)
    {        
        return ResearcherTagLibraryFunctions
        .countBoxPublicMetadata(getApplicationService().get(ResearcherPage.class, objectID), box, b);
    }

    @Override
    public boolean isBoxHidden(HttpServletRequest request, Integer objectID,
            BoxRPAdditionalFieldStorage box)
    {
        return ResearcherTagLibraryFunctions.isBoxHidden(
                getApplicationService().get(ResearcherPage.class, objectID), box);
    }

    
}
