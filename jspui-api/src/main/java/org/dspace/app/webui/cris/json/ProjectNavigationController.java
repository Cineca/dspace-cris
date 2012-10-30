package org.dspace.app.webui.cris.json;

import it.cilea.hku.authority.model.Project;
import it.cilea.hku.authority.model.dynamicfield.BoxProject;
import it.cilea.hku.authority.model.dynamicfield.DecoratorProjectPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.DecoratorProjectTypeNested;
import it.cilea.hku.authority.model.dynamicfield.ProjectNestedObject;
import it.cilea.hku.authority.model.dynamicfield.ProjectNestedPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.ProjectNestedProperty;
import it.cilea.hku.authority.model.dynamicfield.ProjectTypeNestedObject;
import it.cilea.hku.authority.model.dynamicfield.TabProject;
import it.cilea.hku.authority.webui.web.tag.ResearcherTagLibraryFunctions;
import it.cilea.osd.jdyna.model.IContainable;
import it.cilea.osd.jdyna.web.controller.json.AjaxJSONNavigationController;

import java.util.List;

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

            if (cont instanceof DecoratorProjectTypeNested)
            {
                DecoratorProjectTypeNested decorator = (DecoratorProjectTypeNested) cont;
                ProjectTypeNestedObject real = (ProjectTypeNestedObject)decorator.getReal();
                List<ProjectNestedObject> results = getApplicationService()
                        .getNestedObjectsByParentIDAndTypoID(Integer
                                .parseInt(p.getIdentifyingValue()),
                                (real.getId()), ProjectNestedObject.class);
                
                external: for (ProjectNestedObject object : results)
                {
                    for (ProjectNestedPropertiesDefinition rpp : real
                            .getMask())
                    {                   
                        
                        
                            for (ProjectNestedProperty pp : object.getAnagrafica4view().get(rpp.getShortName()))
                            {
                                if (pp.getVisibility() == 1)
                                {
                                    result++;
                                    break external;
                                }
                            } 

                        
                        
                    }
                }

            }

             
            if (cont instanceof DecoratorProjectPropertiesDefinition)
            {
                DecoratorProjectPropertiesDefinition decorator = (DecoratorProjectPropertiesDefinition) cont;
                result += ResearcherTagLibraryFunctions.countDynamicPublicMetadata(
                        p.getDynamicField(), decorator.getShortName(),
                        decorator.getRendering(), decorator.getReal(),
                        true);

            }
             
         
            

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
