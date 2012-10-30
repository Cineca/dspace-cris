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

import it.cilea.hku.authority.model.OrganizationUnit;
import it.cilea.hku.authority.model.dynamicfield.BoxOrganizationUnit;
import it.cilea.hku.authority.model.dynamicfield.DecoratorOUPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.EditTabOrganizationUnit;
import it.cilea.hku.authority.model.dynamicfield.OUAdditionalFieldStorage;
import it.cilea.hku.authority.model.dynamicfield.OUNestedObject;
import it.cilea.hku.authority.model.dynamicfield.OUNestedPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.OUNestedProperty;
import it.cilea.hku.authority.model.dynamicfield.OUPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.OUProperty;
import it.cilea.hku.authority.model.dynamicfield.VisibilityTabConstant;
import it.cilea.hku.authority.service.ApplicationService;
import it.cilea.hku.authority.util.ResearcherPageUtils;
import it.cilea.hku.authority.webui.dto.OUAnagraficaObjectDTO;
import it.cilea.osd.jdyna.dto.AnagraficaObjectAreaDTO;
import it.cilea.osd.jdyna.model.AnagraficaObject;
import it.cilea.osd.jdyna.model.IContainable;
import it.cilea.osd.jdyna.util.AnagraficaUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dspace.app.webui.util.UIUtil;
import org.dspace.authorize.AuthorizeException;
import org.dspace.authorize.AuthorizeManager;
import org.dspace.core.Context;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

public class FormOUDynamicMetadataController
        extends
        AFormDynamicOUController<OUProperty, OUPropertiesDefinition, BoxOrganizationUnit, EditTabOrganizationUnit, AnagraficaObject<OUProperty, OUPropertiesDefinition>, OUNestedObject, OUNestedProperty, OUNestedPropertiesDefinition>
{

    @Override
    protected Map referenceData(HttpServletRequest request, Object command,
            Errors errors) throws Exception
    {

        // call super method
        Map<String, Object> map = super.referenceData(request);

        // this map contains key-values pairs, key = box shortname and values =
        // collection of metadata
        Map<String, List<IContainable>> mapBoxToContainables = new HashMap<String, List<IContainable>>();

        AnagraficaObjectAreaDTO anagraficaObjectDTO = (AnagraficaObjectAreaDTO) command;

        // check admin authorization
        boolean isAdmin = false;
        Context context = UIUtil.obtainContext(request);
        if (AuthorizeManager.isAdmin(context))
        {
            isAdmin = true;
        }

        // collection of edit tabs (all edit tabs created on system associate to
        // visibility)
        List<EditTabOrganizationUnit> tabs = getApplicationService()
                .getTabsByVisibility(EditTabOrganizationUnit.class, isAdmin);

        // check if request tab from view is active (check on collection before)
        EditTabOrganizationUnit editT = getApplicationService().get(
                EditTabOrganizationUnit.class, anagraficaObjectDTO.getTabId());
        if (!tabs.contains(editT))
        {
            throw new AuthorizeException(
                    "You not have needed authorization level to display this tab");
        }

        // collection of boxs
        List<BoxOrganizationUnit> propertyHolders = new LinkedList<BoxOrganizationUnit>();

        // if edit tab got a display tab (edit tab is hookup to display tab)
        // then edit box will be created from display box otherwise get all boxs
        // in edit tab
        if (editT.getDisplayTab() != null)
        {
            for (BoxOrganizationUnit box : editT.getDisplayTab().getMask())
            {
                propertyHolders.add(box);
            }
        }
        else
        {
            propertyHolders = getApplicationService().findPropertyHolderInTab(
                    getClazzTab(), anagraficaObjectDTO.getTabId());
        }

        // clean boxs list with accesslevel
        List<BoxOrganizationUnit> propertyHoldersCurrentAccessLevel = new LinkedList<BoxOrganizationUnit>();
        for (BoxOrganizationUnit propertyHolder : propertyHolders)
        {
            if (isAdmin)
            {
                if (!propertyHolder.getVisibility().equals(
                        VisibilityTabConstant.LOW))
                {
                    propertyHoldersCurrentAccessLevel.add(propertyHolder);
                }
            }
            else
            {
                if (!propertyHolder.getVisibility().equals(
                        VisibilityTabConstant.ADMIN))
                {
                    propertyHoldersCurrentAccessLevel.add(propertyHolder);
                }
            }
        }
        Collections.sort(propertyHoldersCurrentAccessLevel);
        // this piece of code get containables object from boxs and put them on
        // map
        List<IContainable> pDInTab = new LinkedList<IContainable>();
        for (BoxOrganizationUnit iph : propertyHoldersCurrentAccessLevel)
        {
            List<IContainable> temp = getApplicationService()
                    .<BoxOrganizationUnit, it.cilea.osd.jdyna.web.Tab<BoxOrganizationUnit>> findContainableInPropertyHolder(
                            getClazzBox(), iph.getId());
            mapBoxToContainables.put(iph.getShortName(), temp);
            pDInTab.addAll(temp);
        }

        map.put("propertiesHolders", propertyHoldersCurrentAccessLevel);
        map.put("propertiesDefinitionsInTab", pDInTab);
        map.put("propertiesDefinitionsInHolder", mapBoxToContainables);
        map.put("tabList", tabs);
        map.put("simpleNameAnagraficaObject", getClazzAnagraficaObject()
                .getSimpleName());
        map.put("addModeType", "edit");
        return map;
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request)
            throws Exception
    {
        String paramFuzzyTabId = request.getParameter("hooktabId");
        String paramTabId = request.getParameter("tabId");
        String paramId = request.getParameter("id");

        Integer id = null;
        Boolean isAdmin = false;
        if (paramId != null)
        {
            id = Integer.parseInt(paramId);
        }
        OrganizationUnit grant = getApplicationService().get(OrganizationUnit.class, id);
        Context context = UIUtil.obtainContext(request);
        if (!AuthorizeManager.isAdmin(context))
        {
            throw new AuthorizeException("Only system admin can edit");
        }
        else
        {
            isAdmin = true;
        }

        Integer areaId;
        if (paramTabId == null)
        {
            if (paramFuzzyTabId == null)
            {
                List<EditTabOrganizationUnit> tabs = getApplicationService()
                        .getTabsByVisibility(EditTabOrganizationUnit.class, isAdmin);
                if (tabs.isEmpty())
                {
                    throw new AuthorizeException("No tabs defined!!");
                }
                areaId = tabs.get(0).getId();
            }
            else
            {
                EditTabOrganizationUnit fuzzyEditTab = (EditTabOrganizationUnit) ((ApplicationService) getApplicationService())
                        .getEditTabByDisplayTab(
                                Integer.parseInt(paramFuzzyTabId),
                                EditTabOrganizationUnit.class);
                areaId = fuzzyEditTab.getId();
            }
        }
        else
        {
            areaId = Integer.parseInt(paramTabId);
        }

        EditTabOrganizationUnit editT = getApplicationService().get(
                EditTabOrganizationUnit.class, areaId);
        List<BoxOrganizationUnit> propertyHolders = new LinkedList<BoxOrganizationUnit>();
        if (editT.getDisplayTab() != null)
        {
            for (BoxOrganizationUnit box : editT.getDisplayTab().getMask())
            {
                propertyHolders.add(box);
            }
        }
        else
        {
            propertyHolders = getApplicationService().findPropertyHolderInTab(
                    getClazzTab(), areaId);
        }

        List<IContainable> tipProprietaInArea = new LinkedList<IContainable>();

        for (BoxOrganizationUnit iph : propertyHolders)
        {
            if (editT.getDisplayTab() != null)
            {
                tipProprietaInArea
                        .addAll(getApplicationService()
                                .<BoxOrganizationUnit, it.cilea.osd.jdyna.web.Tab<BoxOrganizationUnit>> findContainableInPropertyHolder(
                                        BoxOrganizationUnit.class, iph.getId()));
            }
            else
            {
                tipProprietaInArea
                        .addAll(getApplicationService()
                                .<BoxOrganizationUnit, it.cilea.osd.jdyna.web.Tab<BoxOrganizationUnit>> findContainableInPropertyHolder(
                                        getClazzBox(), iph.getId()));
            }
        }
        OUAdditionalFieldStorage dynamicObject = grant.getDynamicField();
        OUAnagraficaObjectDTO anagraficaObjectDTO = new OUAnagraficaObjectDTO(
                grant);
        anagraficaObjectDTO.setTabId(areaId);
        anagraficaObjectDTO.setObjectId(grant.getId());
        anagraficaObjectDTO.setParentId(grant.getId());
    

        List<OUPropertiesDefinition> realTPS = new LinkedList<OUPropertiesDefinition>();
        List<IContainable> structuralField = new LinkedList<IContainable>();
        for (IContainable c : tipProprietaInArea)
        {

            OUPropertiesDefinition rpPd = getApplicationService()
                    .findPropertiesDefinitionByShortName(
                            OUPropertiesDefinition.class, c.getShortName());
            if (rpPd != null)
            {
                realTPS.add(rpPd);
            }
            else
            {
                structuralField.add(c);
            }
        }
        AnagraficaUtils.fillDTO(anagraficaObjectDTO, dynamicObject, realTPS);
        return anagraficaObjectDTO;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object object, BindException errors)
            throws Exception
    {
        OUAnagraficaObjectDTO anagraficaObjectDTO = (OUAnagraficaObjectDTO) object;

        String exitPage = "redirect:/cris/tools/ou/editDynamicData.htm?id="
                + anagraficaObjectDTO.getParentId();

        
        EditTabOrganizationUnit editT = getApplicationService().get(
                EditTabOrganizationUnit.class, anagraficaObjectDTO.getTabId());
        if (anagraficaObjectDTO.getNewTabId() != null)
        {
            exitPage += "&tabId=" + anagraficaObjectDTO.getNewTabId();
        }
        else
        {
            exitPage = "redirect:/cris/ou/"
                    + ResearcherPageUtils.getPersistentIdentifier(anagraficaObjectDTO
                                    .getParentId()) + "/"
                    + editT.getShortName().substring(4) + ".html";
        }
        if (request.getParameter("cancel") != null)
        {
            return new ModelAndView(exitPage);
        }
        
        
        OrganizationUnit grant = getApplicationService().get(OrganizationUnit.class,
                anagraficaObjectDTO.getParentId());
        OUAdditionalFieldStorage myObject = grant.getDynamicField();
        
        List<BoxOrganizationUnit> propertyHolders = new LinkedList<BoxOrganizationUnit>();
        if (editT.getDisplayTab() != null)
        {
            for (BoxOrganizationUnit box : editT.getDisplayTab().getMask())
            {
                propertyHolders.add(box);
            }
        }
        else
        {
            propertyHolders = getApplicationService().findPropertyHolderInTab(
                    getClazzTab(), anagraficaObjectDTO.getTabId());
        }

        List<IContainable> tipProprietaInArea = new LinkedList<IContainable>();

        for (BoxOrganizationUnit iph : propertyHolders)
        {

            tipProprietaInArea
                    .addAll(getApplicationService()
                            .<BoxOrganizationUnit, it.cilea.osd.jdyna.web.Tab<BoxOrganizationUnit>> findContainableInPropertyHolder(
                                    getClazzBox(), iph.getId()));

        }

        List<OUPropertiesDefinition> realTPS = new LinkedList<OUPropertiesDefinition>();
        List<IContainable> structuralField = new LinkedList<IContainable>();
        for (IContainable c : tipProprietaInArea)
        {
            OUPropertiesDefinition rpPd = getApplicationService()
                    .findPropertiesDefinitionByShortName(
                            OUPropertiesDefinition.class, c.getShortName());
            if (rpPd != null)
            {
                realTPS.add(rpPd);
            }
            else
            {
                structuralField.add(c);
            }
        }

        AnagraficaUtils.reverseDTO(anagraficaObjectDTO, myObject, realTPS);
        
        myObject.pulisciAnagrafica();
        grant.setSourceID(anagraficaObjectDTO.getSourceID());
        
        
        getApplicationService().saveOrUpdate(OrganizationUnit.class, grant);
        EditTabOrganizationUnit area = getApplicationService().get(getClazzTab(),
                anagraficaObjectDTO.getTabId());
        final String areaTitle = area.getTitle();
        saveMessage(
                request,
                getText("action.anagrafica.edited", new Object[] { areaTitle },
                        request.getLocale()));

        return new ModelAndView(exitPage);
    }

    @Override
    protected void onBindAndValidate(HttpServletRequest request,
            Object command, BindException errors) throws Exception
    {

        AnagraficaObjectAreaDTO dto = (AnagraficaObjectAreaDTO) command;
        OrganizationUnit researcher = getApplicationService().get(OrganizationUnit.class,
                dto.getParentId());
        OUAdditionalFieldStorage myObject = researcher.getDynamicField();

        EditTabOrganizationUnit editT = getApplicationService().get(
                EditTabOrganizationUnit.class, dto.getTabId());
        List<BoxOrganizationUnit> propertyHolders = new LinkedList<BoxOrganizationUnit>();
        if (editT.getDisplayTab() != null)
        {
            for (BoxOrganizationUnit box : editT.getDisplayTab().getMask())
            {
                propertyHolders.add(box);
            }
        }
        else
        {
            propertyHolders = getApplicationService().findPropertyHolderInTab(
                    getClazzTab(), dto.getTabId());
        }

        List<IContainable> tipProprietaInArea = new LinkedList<IContainable>();

        for (BoxOrganizationUnit iph : propertyHolders)
        {

            tipProprietaInArea
                    .addAll(getApplicationService()
                            .<BoxOrganizationUnit, it.cilea.osd.jdyna.web.Tab<BoxOrganizationUnit>> findContainableInPropertyHolder(
                                    getClazzBox(), iph.getId()));

        }

        List<OUPropertiesDefinition> realTPS = new LinkedList<OUPropertiesDefinition>();
        List<IContainable> structuralField = new LinkedList<IContainable>();
        for (IContainable c : tipProprietaInArea)
        {
            OUPropertiesDefinition rpPd = getApplicationService()
                    .findPropertiesDefinitionByShortName(
                            OUPropertiesDefinition.class, c.getShortName());
            if (rpPd != null)
            {
                realTPS.add(rpPd);
            }
            else
            {
                structuralField.add(c);
            }
        }
        AnagraficaUtils.reverseDTO(dto, myObject, realTPS);
        AnagraficaUtils.fillDTO(dto, myObject, realTPS);
    }

}
