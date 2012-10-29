package org.dspace.app.webui.cris.json;

import it.cilea.hku.authority.model.OrganizationUnit;
import it.cilea.hku.authority.model.dynamicfield.BoxOrganizationUnit;
import it.cilea.hku.authority.model.dynamicfield.DecoratorOUPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.TabOrganizationUnit;
import it.cilea.hku.authority.webui.web.tag.ResearcherTagLibraryFunctions;
import it.cilea.osd.jdyna.model.IContainable;
import it.cilea.osd.jdyna.web.controller.json.AjaxJSONNavigationController;

public class OUNavigationController
        extends
        AjaxJSONNavigationController<BoxOrganizationUnit, TabOrganizationUnit>
{

    
    public OUNavigationController()
    {
        super(TabOrganizationUnit.class);      
    }


 
    @Override
    public int countBoxPublicMetadata(Integer objectID,
            BoxOrganizationUnit box, boolean b)
    {        
        int result = 0;
        
        OrganizationUnit p = getApplicationService().get(OrganizationUnit.class, objectID);
        for (IContainable cont : box.getMask())
        {

                DecoratorOUPropertiesDefinition decorator = (DecoratorOUPropertiesDefinition) cont;
                result += ResearcherTagLibraryFunctions.countDynamicPublicMetadata(
                        p.getDynamicField(), decorator.getShortName(),
                        decorator.getRendering(), decorator.getReal(),
                        true);


        }

        
        return result;
    }

    @Override
    public boolean isBoxHidden(Integer objectID,
            BoxOrganizationUnit box)
    {
        return ResearcherTagLibraryFunctions.isBoxHidden(
                getApplicationService().get(OrganizationUnit.class, objectID), box);
    }

    
}
