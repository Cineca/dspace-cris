package org.dspace.app.webui.cris.json;

import it.cilea.hku.authority.model.Project;
import it.cilea.hku.authority.model.dynamicfield.BoxProject;
import it.cilea.hku.authority.model.dynamicfield.DecoratorProjectPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.TabProject;
import it.cilea.hku.authority.webui.web.tag.ResearcherTagLibraryFunctions;
import it.cilea.osd.jdyna.model.IContainable;
import it.cilea.osd.jdyna.web.controller.json.AjaxJSONNavigationController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public class ProjectNavigationController
        extends
        AjaxJSONNavigationController<BoxProject, TabProject>
{

    
    public ProjectNavigationController()
    {
        super(TabProject.class);      
    }
       

    @Override
    public int countBoxPublicMetadata(Integer objectID,
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
    public boolean isBoxHidden(Integer objectID,
            BoxProject box)
    {
        return ResearcherTagLibraryFunctions.isBoxHidden(
                getApplicationService().get(Project.class, objectID), box);
    }

    
}
