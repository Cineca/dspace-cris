package it.cilea.hku.authority.webui.controller.jdyna;

import it.cilea.hku.authority.model.OrganizationUnit;
import it.cilea.hku.authority.model.dynamicfield.BoxOrganizationUnit;
import it.cilea.hku.authority.model.dynamicfield.DecoratorOUPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.TabOrganizationUnit;
import it.cilea.hku.authority.webui.web.tag.ResearcherTagLibraryFunctions;
import it.cilea.osd.jdyna.model.IContainable;
import it.cilea.osd.jdyna.web.controller.AjaxNavigationController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public class OUNavigationController
        extends
        AjaxNavigationController<BoxOrganizationUnit, TabOrganizationUnit>
{

    
    public OUNavigationController()
    {
        super(TabOrganizationUnit.class);      
    }


    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {

        return super.loadNavigation(request, response);
    }

    @Override
    public int countBoxPublicMetadata(HttpServletRequest request, Integer objectID,
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
    public boolean isBoxHidden(HttpServletRequest request, Integer objectID,
            BoxOrganizationUnit box)
    {
        return ResearcherTagLibraryFunctions.isBoxHidden(
                getApplicationService().get(OrganizationUnit.class, objectID), box);
    }

    
}
