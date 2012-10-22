package it.cilea.hku.authority.webui.controller.jdyna;

import it.cilea.hku.authority.model.Project;
import it.cilea.hku.authority.model.dynamicfield.BoxProject;
import it.cilea.hku.authority.model.dynamicfield.TabProject;
import it.cilea.hku.authority.webui.web.tag.ResearcherTagLibraryFunctions;
import it.cilea.osd.jdyna.web.controller.AjaxOpenTabController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public class ProjectLoaderTabsController extends AjaxOpenTabController<BoxProject, TabProject>
{

    public ProjectLoaderTabsController()
    {
        super(TabProject.class);
    }
    
    @Override
    public boolean isBoxHidden(HttpServletRequest request, Integer objectID,
            BoxProject box)
    {
        return ResearcherTagLibraryFunctions.isBoxHidden(
                getApplicationService().get(Project.class, objectID), box);
    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {        
        return super.loadTabs(request, response);
    }

}
