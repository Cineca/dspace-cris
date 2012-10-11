package it.cilea.hku.authority.webui.controller.jdyna;

import it.cilea.hku.authority.model.dynamicfield.DecoratorRPTypeNested;
import it.cilea.hku.authority.model.dynamicfield.RPNestedObject;
import it.cilea.hku.authority.model.dynamicfield.RPTypeNestedObject;
import it.cilea.hku.authority.service.ApplicationService;
import it.cilea.osd.common.controller.BaseAbstractController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public class RPNestedDetailsController extends BaseAbstractController 
{
    
    private ApplicationService applicationService;

    private Class modelClazz;
    
    public RPNestedDetailsController(Class model)
    {
        this.modelClazz = model;
    }
    
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ModelAndView retValue = null;                
        if ("delete".equals(method))
            retValue = handleDelete(request);
        else if ("list".equals(method))
            retValue = handleList(request);        
        return retValue;
    }


    private ModelAndView handleList(HttpServletRequest request)
    {
        Map<String, Object> model = new HashMap<String, Object>();   
        String parentStringID = request.getParameter("parentID");
        String typeNestedStringID = request.getParameter("typeNestedID");
        String limitString = request.getParameter("limit");        
        String pageString = request.getParameter("pageCurrent");
        String offsetString = request.getParameter("offset");
        String editmode = request.getParameter("editmode");
        Boolean edit = false;
        if(editmode!=null && !editmode.isEmpty()) {
            edit = Boolean.parseBoolean(editmode);            
        }
        
        Integer parentID = Integer.parseInt(parentStringID);
        Integer typeNestedID = Integer.parseInt(typeNestedStringID);        
        Integer limit = Integer.parseInt(limitString);
        Integer page = Integer.parseInt(pageString);
        Integer offset = Integer.parseInt(offsetString);
        
        List<RPNestedObject> results = applicationService.getNestedObjectsByParentIDAndTypoIDLimitAt(parentID, typeNestedID, modelClazz, limit, page*limit);                 
        
        Long countAll = applicationService.countNestedObjectsByParentIDAndTypoID(parentID, typeNestedID, modelClazz);
        
        model.put("decoratorPropertyDefinition", applicationService.findContainableByDecorable(DecoratorRPTypeNested.class, typeNestedID));
        model.put("results", results);           
        model.put("offset", offset + limit);
        model.put("limit", limit);
        model.put("pageCurrent", page);   
        model.put("editmode", edit);
        model.put("parentID", parentID);
        model.put("totalHit", countAll.intValue());
        model.put("hitPageSize", results.size());
        
        return new ModelAndView(detailsView, model);
    }

    private ModelAndView handleDelete(HttpServletRequest request)
    {
        Map<String, Object> model = new HashMap<String, Object>();
        String elementID = request.getParameter("elementID");
        String parentStringID = request.getParameter("parentID");
        String typeNestedStringID = request.getParameter("typeNestedID");        
        String editmode = request.getParameter("editmode");
        Boolean edit = false;
        if(editmode!=null && !editmode.isEmpty()) {
            edit = Boolean.parseBoolean(editmode);            
        }
                
        Integer parentID = Integer.parseInt(parentStringID);
        Integer typeNestedID = Integer.parseInt(typeNestedStringID);        
        Integer limit = 5;
        Integer page = 0;
        Integer offset = 0;
        
        if(elementID!=null && !elementID.isEmpty()) {
            String nestedID = elementID.substring(elementID.lastIndexOf("_")+1);            
            applicationService.delete(RPNestedObject.class, Integer.parseInt(nestedID));            
        }    
        
        
        List<RPNestedObject> results = applicationService.getNestedObjectsByParentIDAndTypoIDLimitAt(parentID, typeNestedID, modelClazz, 5, 0);                 
        
        Long countAll = applicationService.countNestedObjectsByParentIDAndTypoID(parentID, typeNestedID, modelClazz);
        
        model.put("decoratorPropertyDefinition", applicationService.findContainableByDecorable(DecoratorRPTypeNested.class, typeNestedID));
        model.put("results", results);           
        model.put("offset", offset + limit);
        model.put("limit", limit);
        model.put("pageCurrent", page);   
        model.put("editmode", edit);
        model.put("parentID", parentID);
        model.put("totalHit", countAll.intValue());
        model.put("hitPageSize", results.size());
        
        return new ModelAndView(detailsView, model);
    }

    public void setApplicationService(ApplicationService applicationService)
    {
        this.applicationService = applicationService;
    }

    public ApplicationService getApplicationService()
    {
        return applicationService;
    }


}