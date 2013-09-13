/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * https://github.com/CILEA/dspace-cris/wiki/License
 */
package org.dspace.app.webui.cris.controller.admin;

import it.cilea.osd.jdyna.controller.FormWidgetPointerDecoratorPDController;
import it.cilea.osd.jdyna.widget.WidgetPointer;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dspace.app.cris.model.jdyna.BoxDynamicObject;
import org.dspace.app.cris.model.jdyna.DecoratorDynamicPropertiesDefinition;
import org.dspace.app.cris.model.jdyna.DynamicObjectType;
import org.dspace.app.cris.model.jdyna.DynamicPropertiesDefinition;
import org.dspace.app.cris.model.jdyna.TabDynamicObject;
import org.dspace.app.cris.model.jdyna.value.DOPointer;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

public class FormWidgetPointerDODecoratorPDController extends
        FormWidgetPointerDecoratorPDController<DynamicPropertiesDefinition, DecoratorDynamicPropertiesDefinition, BoxDynamicObject, TabDynamicObject, DOPointer>
{

    


    public FormWidgetPointerDODecoratorPDController(
            Class<DynamicPropertiesDefinition> targetModel,
            Class<WidgetPointer> renderingModel,
            Class<BoxDynamicObject> boxModel, Class<DOPointer> crisModel)
    {
        super(targetModel, renderingModel, boxModel, crisModel);
    }

    @Override
    protected Map referenceData(HttpServletRequest request) throws Exception
    {
        
        Map map = super.referenceData(request);
        List<DynamicObjectType> researchobjects = getApplicationService().getList(DynamicObjectType.class);
        map.put("researchobjects", researchobjects);
        return map;
        
    }
    
    @Override
    protected ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object command, BindException errors)
            throws Exception
    {
        DynamicPropertiesDefinition object = (DynamicPropertiesDefinition)command;
        String shortName = object.getShortName();
        
        String boxId = request.getParameter("boxId");
                        
        if(boxId!=null && !boxId.isEmpty()) {
            BoxDynamicObject box = getApplicationService().get(BoxDynamicObject.class, Integer.parseInt(boxId));
            if(!shortName.startsWith(box.getTypeDef().getShortName())) {
                object.setShortName(box.getTypeDef().getShortName() + shortName);   
            }            
        }  
        return super.onSubmit(request, response, command, errors);
    }
}
