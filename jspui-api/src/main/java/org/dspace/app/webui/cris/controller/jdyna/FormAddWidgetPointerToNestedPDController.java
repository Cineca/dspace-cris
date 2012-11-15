/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package org.dspace.app.webui.cris.controller.jdyna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dspace.app.cris.model.ACrisObject;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import it.cilea.osd.jdyna.controller.FormAddToNestedDefinitionController;
import it.cilea.osd.jdyna.model.ADecoratorNestedPropertiesDefinition;
import it.cilea.osd.jdyna.model.ADecoratorTypeDefinition;
import it.cilea.osd.jdyna.model.ANestedPropertiesDefinition;
import it.cilea.osd.jdyna.model.ATypeNestedObject;
import it.cilea.osd.jdyna.model.Containable;
import it.cilea.osd.jdyna.value.PointerValue;
import it.cilea.osd.jdyna.web.IPropertyHolder;
import it.cilea.osd.jdyna.web.Tab;
import it.cilea.osd.jdyna.widget.WidgetPointer;

public class FormAddWidgetPointerToNestedPDController<TP extends ANestedPropertiesDefinition, DTP extends ADecoratorNestedPropertiesDefinition<TP>, 
    ATN extends ATypeNestedObject<TP>, DTT extends ADecoratorTypeDefinition<ATN, TP>, 
    H extends IPropertyHolder<Containable>, T extends Tab<H>, VPO extends PointerValue<? extends ACrisObject>>
        extends
        FormAddToNestedDefinitionController<WidgetPointer, TP, DTP, ATN, DTT, H, T>
{

    private Class<VPO> pValueClass;
    
    public FormAddWidgetPointerToNestedPDController(Class<TP> targetModel,
            Class<WidgetPointer> renderingModel, Class<H> boxModel,
            Class<DTT> typeModel, Class<VPO> pValueClass)
    {
        super(targetModel, renderingModel, boxModel, typeModel);
        this.pValueClass = pValueClass;
    }

    @Override
    protected DTP formBackingObject(HttpServletRequest request)
            throws Exception
    {
        DTP tip = (DTP) super.formBackingObject(request);
        ((WidgetPointer<VPO>) tip.getObject().getRendering())
                .setTarget(pValueClass.getCanonicalName());
        return tip;
    }
    
    @Override
    protected ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object command, BindException errors)
            throws Exception {
        String rendering = request.getParameter("renderingparent");
        String boxId = request.getParameter("boxId");
        String tabId = request.getParameter("tabId");
        DTP object = (DTP)command;
        ((WidgetPointer)(object.getReal().getRendering())).setTarget(pValueClass.getCanonicalName());
        DTT rPd = getApplicationService().get(getTypeModel(), Integer.parseInt(rendering));
        
        object.getReal().setAccessLevel(rPd.getAccessLevel());
        getApplicationService().saveOrUpdate(object.getDecoratorClass(), object);      
        
        return new ModelAndView(getSuccessView()+"?pDId="+rPd.getReal().getId()+"&boxId="+boxId+"&tabId="+tabId);
    }    
}
