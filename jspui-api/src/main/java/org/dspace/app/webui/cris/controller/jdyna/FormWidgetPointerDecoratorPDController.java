/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package org.dspace.app.webui.cris.controller.jdyna;

import it.cilea.osd.jdyna.controller.FormDecoratorPropertiesDefinitionController;
import it.cilea.osd.jdyna.model.ADecoratorPropertiesDefinition;
import it.cilea.osd.jdyna.model.Containable;
import it.cilea.osd.jdyna.model.PropertiesDefinition;
import it.cilea.osd.jdyna.value.PointerValue;
import it.cilea.osd.jdyna.web.IPropertyHolder;
import it.cilea.osd.jdyna.web.Tab;
import it.cilea.osd.jdyna.widget.WidgetPointer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dspace.app.cris.model.ACrisObject;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

public class FormWidgetPointerDecoratorPDController<TP extends PropertiesDefinition, DTP extends ADecoratorPropertiesDefinition<TP>, 
    H extends IPropertyHolder<Containable>, T extends Tab<H>, PV extends PointerValue<? extends ACrisObject>>
        extends FormDecoratorPropertiesDefinitionController<WidgetPointer, TP, DTP, H, T>
{

    private Class<PV> pValueClass;
    
    public FormWidgetPointerDecoratorPDController(Class<TP> targetModel,
            Class<WidgetPointer> renderingModel, Class<H> boxModel, Class<PV> crisModel)
    {
        super(targetModel, renderingModel, boxModel);
        this.pValueClass = crisModel;
    }

    @Override
    protected DTP formBackingObject(HttpServletRequest request)
            throws Exception
    {
        DTP tip = (DTP) super.formBackingObject(request);
        ((WidgetPointer<PV>) tip.getObject().getRendering())
                .setTarget(pValueClass.getCanonicalName());
        return tip;
    }
    
    @Override
    protected ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object command, BindException errors)
            throws Exception
    {

        String boxId = request.getParameter("boxId");
        String tabId = request.getParameter("tabId");

        DTP object = (DTP) command;
        getApplicationService().saveOrUpdate(object.getDecoratorClass(), object);

        if (boxId != null && !boxId.isEmpty())
        {
            H box = getApplicationService().get(getBoxModel(),
                    Integer.parseInt(boxId));
            box.getMask().add(object);
            getApplicationService().saveOrUpdate(getBoxModel(), box);
        }
        return new ModelAndView(getSuccessView() + "?id=" + boxId + "&tabId="
                + tabId);
    }

}
