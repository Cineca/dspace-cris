package it.cilea.hku.authority.webui.controller.jdyna;

import it.cilea.hku.authority.model.Project;
import it.cilea.hku.authority.model.dynamicfield.BoxProject;
import it.cilea.hku.authority.model.dynamicfield.DecoratorProjectPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.TabProject;
import it.cilea.hku.authority.webui.web.tag.ResearcherTagLibraryFunctions;
import it.cilea.osd.jdyna.model.IContainable;
import it.cilea.osd.jdyna.web.controller.AjaxNavigationController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public class ProjectNavigationController
        extends
        AjaxNavigationController<BoxProject, TabProject>
{

    
    public ProjectNavigationController()
    {
        super(TabProject.class);      
    }


    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {

        return super.loadNavigation(request, response);
    }

    @Override
    public int countBoxPublicMetadata(HttpServletRequest request, Integer objectID,
            BoxProject box, boolean b)
    {        
        int result = 0;
        
        Project p = getApplicationService().get(Project.class, objectID);
        for (IContainable cont : box.getMask())
        {

                DecoratorProjectPropertiesDefinition decorator = (DecoratorProjectPropertiesDefinition) cont;
                result += ResearcherTagLibraryFunctions.countDynamicPublicMetadata(
                        p.getDynamicField(), decorator.getShortName(),
                        decorator.getRendering(), decorator.getReal(),
                        true);


        }

        
        return result;
    }

    @Override
    public boolean isBoxHidden(HttpServletRequest request, Integer objectID,
            BoxProject box)
    {
        return ResearcherTagLibraryFunctions.isBoxHidden(
                getApplicationService().get(Project.class, objectID), box);
    }

    
}
