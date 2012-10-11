package it.cilea.hku.authority.webui.controller.jdyna;

import it.cilea.hku.authority.model.ResearcherPage;
import it.cilea.hku.authority.model.dynamicfield.BoxRPAdditionalFieldStorage;
import it.cilea.hku.authority.model.dynamicfield.DecoratorRPTypeNested;
import it.cilea.hku.authority.model.dynamicfield.EditTabRPAdditionalFieldStorage;
import it.cilea.hku.authority.model.dynamicfield.RPAdditionalFieldStorage;
import it.cilea.hku.authority.model.dynamicfield.RPNestedObject;
import it.cilea.hku.authority.model.dynamicfield.RPNestedPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.RPPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.RPTypeNestedObject;
import it.cilea.hku.authority.service.ApplicationService;
import it.cilea.osd.common.controller.BaseFormController;
import it.cilea.osd.jdyna.dto.AnagraficaObjectAreaDTO;
import it.cilea.osd.jdyna.dto.AnagraficaObjectWithTypeDTO;
import it.cilea.osd.jdyna.dto.ValoreDTO;
import it.cilea.osd.jdyna.dto.ValoreDTOPropertyEditor;
import it.cilea.osd.jdyna.editor.FilePropertyEditor;
import it.cilea.osd.jdyna.model.IContainable;
import it.cilea.osd.jdyna.util.AnagraficaUtils;

import java.beans.PropertyEditor;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

public class FormRPNestedObject extends BaseFormController
{

    private ApplicationService applicationService;
    
    @Override
    protected Map referenceData(HttpServletRequest request) throws Exception
    {        
        String typeNestedStringID = request.getParameter("typeNestedID");
        Integer typeNestedID = Integer.parseInt(typeNestedStringID);
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("simpleNameAnagraficaObject",
                RPNestedObject.class.getSimpleName());
        RPTypeNestedObject typo = applicationService.get(RPTypeNestedObject.class, typeNestedID);
        model.put("maschera", typo.getMaschera());               
        String parentStringID = request.getParameter("parentID");
        String editmode = request.getParameter("editmode");
                
        model.put("parentID", parentStringID);
        model.put("elementID", typo.getShortName());
        model.put("typeNestedID", typeNestedStringID);
        model.put("editmode", editmode);
        return model;
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request)
            throws Exception
    {
     
        String elementID = request.getParameter("elementID");        
        String parentStringID = request.getParameter("parentID");
        String typeNestedStringID = request.getParameter("typeNestedID");
                
        Integer parentID = Integer.parseInt(parentStringID);
        Integer typeNestedID = Integer.parseInt(typeNestedStringID);        
                
        RPTypeNestedObject typo = applicationService.get(RPTypeNestedObject.class, typeNestedID);
        RPNestedObject nested = new RPNestedObject();
        if(elementID!=null && !elementID.isEmpty()) {
            String nestedID = elementID.substring(elementID.lastIndexOf("_")+1);            
            nested = applicationService.get(RPNestedObject.class, Integer.parseInt(nestedID));            
        }
        nested.inizializza();
        
        
        AnagraficaObjectWithTypeDTO anagraficaObjectDTO = new AnagraficaObjectWithTypeDTO();        
        anagraficaObjectDTO.setTipologiaId(typo.getId());
        anagraficaObjectDTO.setParentId(parentID);
        anagraficaObjectDTO.setObjectId(nested.getId());
        
        
        AnagraficaUtils.fillDTO(anagraficaObjectDTO, nested, typo.getMaschera());
        return anagraficaObjectDTO;

    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object command, BindException errors)
            throws Exception
    {
        AnagraficaObjectWithTypeDTO anagraficaObjectDTO = (AnagraficaObjectWithTypeDTO) command;
        RPTypeNestedObject typo = applicationService.get(RPTypeNestedObject.class, anagraficaObjectDTO.getTipologiaId());
        
        
        RPNestedObject myObject = new RPNestedObject();
        if(anagraficaObjectDTO.getObjectId()!=null) {
            myObject = applicationService.get(RPNestedObject.class, anagraficaObjectDTO.getObjectId());          
        }
                
        AnagraficaUtils.reverseDTO(anagraficaObjectDTO, myObject, typo.getMaschera());
        
        myObject.pulisciAnagrafica();
        myObject.setParent(applicationService.get(ResearcherPage.class, anagraficaObjectDTO.getParentId()));
        myObject.setTypo(typo);
        getApplicationService().saveOrUpdate(RPNestedObject.class, myObject);
       
        Map<String, Object> model = new HashMap<String, Object>(); 
        Integer parentID = anagraficaObjectDTO.getParentId();
        Integer typeNestedID = anagraficaObjectDTO.getTipologiaId();
        
        List<RPNestedObject> results = applicationService.getNestedObjectsByParentIDAndTypoIDLimitAt(parentID, typeNestedID, RPNestedObject.class, 5, 0);             
        
        Long countAll = applicationService.countNestedObjectsByParentIDAndTypoID(parentID, typeNestedID, RPNestedObject.class);        
        model.put("decoratorPropertyDefinition", applicationService.findContainableByDecorable(DecoratorRPTypeNested.class, typeNestedID));
        model.put("results", results);           
        model.put("offset", 5);
        model.put("limit", 5);
        model.put("pageCurrent", 0);   
        model.put("editmode", true);
        model.put("parentID", parentID);
        model.put("totalHit", countAll.intValue());
        model.put("hitPageSize", results.size());
        return new ModelAndView(getSuccessView(), model);// + anagraficaObjectDTO.getParentId() + "#viewnested_" + typo.getShortName());
    }

    
    @Override
    protected ServletRequestDataBinder createBinder(HttpServletRequest request,
            Object command) throws Exception
    {
        ServletRequestDataBinder servletRequestDataBinder = super.createBinder(
                request, command);
        AnagraficaObjectAreaDTO commandDTO = (AnagraficaObjectAreaDTO) command;
        for (String shortName : commandDTO.getAnagraficaProperties().keySet())
        {
            RPNestedPropertiesDefinition tipologiaProprieta = applicationService
                    .findPropertiesDefinitionByShortName(
                            RPNestedPropertiesDefinition.class, shortName);
            PropertyEditor propertyEditor = tipologiaProprieta.getRendering()
                    .getPropertyEditor(applicationService);
            if (propertyEditor instanceof FilePropertyEditor)
            {
                ((FilePropertyEditor) propertyEditor)
                        .setExternalAuthority(String.valueOf(commandDTO
                                .getParentId()));
                ((FilePropertyEditor) propertyEditor)
                        .setInternalAuthority(String.valueOf(tipologiaProprieta
                                .getId()));
            }

            String path = "anagraficaProperties[" + shortName + "]";
            servletRequestDataBinder.registerCustomEditor(ValoreDTO.class,
                    path, new ValoreDTOPropertyEditor(propertyEditor));
            // per le checkbox
            servletRequestDataBinder.registerCustomEditor(Object.class, path
                    + ".object", propertyEditor);
            logger.debug("Registrato Wrapper del property editor: "
                    + propertyEditor + " per il path: " + path);
            logger.debug("Registrato property editor: " + propertyEditor
                    + " per il path: " + path + ".object");

        }
        return servletRequestDataBinder;
    }

    @Override
    protected void onBindAndValidate(HttpServletRequest request,
            Object command, BindException errors) throws Exception
    {

        AnagraficaObjectWithTypeDTO dto = (AnagraficaObjectWithTypeDTO) command;
        RPTypeNestedObject typo = applicationService.get(RPTypeNestedObject.class, dto.getTipologiaId());
        
        
        RPNestedObject myObject = new RPNestedObject();
        if(dto.getObjectId()!=null) {
            myObject = applicationService.get(RPNestedObject.class, dto.getObjectId());          
        }
                
        
        AnagraficaUtils.reverseDTO(dto, myObject, typo.getMaschera());
        AnagraficaUtils.fillDTO(dto, myObject, typo.getMaschera());
    }
    
    public ApplicationService getApplicationService()
    {
        return applicationService;
    }

    public void setApplicationService(ApplicationService applicationService)
    {
        this.applicationService = applicationService;
    }

}
