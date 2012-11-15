/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package org.dspace.app.webui.cris.json;

import it.cilea.osd.jdyna.model.IContainable;
import it.cilea.osd.jdyna.web.controller.json.AjaxJSONNavigationController;

import java.util.List;

import org.dspace.app.cris.model.OrganizationUnit;
import org.dspace.app.cris.model.dynamicfield.BoxOrganizationUnit;
import org.dspace.app.cris.model.dynamicfield.DecoratorOUPropertiesDefinition;
import org.dspace.app.cris.model.dynamicfield.DecoratorOUTypeNested;
import org.dspace.app.cris.model.dynamicfield.OUNestedObject;
import org.dspace.app.cris.model.dynamicfield.OUNestedPropertiesDefinition;
import org.dspace.app.cris.model.dynamicfield.OUNestedProperty;
import org.dspace.app.cris.model.dynamicfield.OUTypeNestedObject;
import org.dspace.app.cris.model.dynamicfield.TabOrganizationUnit;
import org.dspace.app.webui.cris.web.tag.ResearcherTagLibraryFunctions;

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


            if (cont instanceof DecoratorOUTypeNested)
            {
                DecoratorOUTypeNested decorator = (DecoratorOUTypeNested) cont;
                OUTypeNestedObject real = (OUTypeNestedObject)decorator.getReal();
                List<OUNestedObject> results = getApplicationService()
                        .getNestedObjectsByParentIDAndTypoID(Integer
                                .parseInt(p.getIdentifyingValue()),
                                (real.getId()), OUNestedObject.class);
                
                external: for (OUNestedObject object : results)
                {
                    for (OUNestedPropertiesDefinition rpp : real
                            .getMask())
                    {                   
                        
                        
                            for (OUNestedProperty pp : object.getAnagrafica4view().get(rpp.getShortName()))
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

             
            if (cont instanceof DecoratorOUPropertiesDefinition)
            {
                DecoratorOUPropertiesDefinition decorator = (DecoratorOUPropertiesDefinition) cont;
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
            BoxOrganizationUnit box)
    {
        return ResearcherTagLibraryFunctions.isBoxHidden(
                getApplicationService().get(OrganizationUnit.class, objectID), box);
    }

    
}
