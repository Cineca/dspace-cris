package it.cilea.hku.authority.webui.controller.jdyna;

import it.cilea.hku.authority.model.OrganizationUnit;
import it.cilea.hku.authority.model.dynamicfield.BoxOrganizationUnit;
import it.cilea.hku.authority.model.dynamicfield.TabOrganizationUnit;
import it.cilea.hku.authority.webui.web.tag.ResearcherTagLibraryFunctions;
import it.cilea.osd.jdyna.web.controller.AjaxOpenTabController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public class OULoaderTabsController extends AjaxOpenTabController<BoxOrganizationUnit, TabOrganizationUnit>
{

    public OULoaderTabsController()
    {
        super(TabOrganizationUnit.class);
    }
    
    @Override
    public boolean isBoxHidden(HttpServletRequest request, Integer objectID,
            BoxOrganizationUnit box)
    {
        return ResearcherTagLibraryFunctions.isBoxHidden(
                getApplicationService().get(OrganizationUnit.class, objectID), box);
    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {        
        return super.loadTabs(request, response);
    }

}
