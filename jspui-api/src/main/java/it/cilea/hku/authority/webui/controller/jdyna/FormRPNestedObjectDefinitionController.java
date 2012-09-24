/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 * 
 * http://www.dspace.org/license/
 * 
 * The document has moved 
 * <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>
 */
package it.cilea.hku.authority.webui.controller.jdyna;

import it.cilea.hku.authority.model.dynamicfield.DecoratorRPTypeNested;
import it.cilea.hku.authority.model.dynamicfield.RPTypeNestedObject;
import it.cilea.hku.authority.service.ApplicationService;
import it.cilea.osd.common.controller.BaseFormController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

public class FormRPNestedObjectDefinitionController extends BaseFormController {

	private ApplicationService applicationService;
	private Class targetClass;
	private String specificPartPath;
	
	
	@Override
	protected Object formBackingObject(HttpServletRequest request)
	        throws Exception
	{
	    DecoratorRPTypeNested decorator = null;
        RPTypeNestedObject object = null;
        String paramId = request.getParameter("pDId");
        if (paramId == null || paramId.isEmpty()) {
            decorator = (DecoratorRPTypeNested)(super.formBackingObject(request));
            object = new RPTypeNestedObject();
            decorator.setReal(object);
        } else {
            Integer id = Integer.parseInt(paramId);            
            decorator = (DecoratorRPTypeNested) applicationService.findContainableByDecorable(
                    getCommandClass(), id);
        }
        return decorator;
	}

	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		String boxId = request.getParameter("boxId");
		String tabId = request.getParameter("tabId");
		DecoratorRPTypeNested object = (DecoratorRPTypeNested) command;
		getApplicationService().saveOrUpdate(DecoratorRPTypeNested.class, object);
		return new ModelAndView(getSuccessView()+"?id="+boxId+"&tabId="+tabId);
		
	}

    public ApplicationService getApplicationService()
    {
        return applicationService;
    }

    public void setApplicationService(ApplicationService applicationService)
    {
        this.applicationService = applicationService;
    }

    public Class getTargetClass()
    {
        return targetClass;
    }

    public void setTargetClass(Class targetClass)
    {
        this.targetClass = targetClass;
    }

    public String getSpecificPartPath()
    {
        return specificPartPath;
    }

    public void setSpecificPartPath(String specificPartPath)
    {
        this.specificPartPath = specificPartPath;
    }
	
}
