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

import it.cilea.hku.authority.model.ResearcherPage;
import it.cilea.hku.authority.model.RestrictedField;
import it.cilea.hku.authority.model.dynamicfield.BoxResearcherPage;
import it.cilea.hku.authority.model.dynamicfield.DecoratorRPPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.EditTabResearcherPage;
import it.cilea.hku.authority.model.dynamicfield.RPAdditionalFieldStorage;
import it.cilea.hku.authority.model.dynamicfield.RPNestedObject;
import it.cilea.hku.authority.model.dynamicfield.RPNestedPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.RPNestedProperty;
import it.cilea.hku.authority.model.dynamicfield.RPPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.RPProperty;
import it.cilea.hku.authority.model.dynamicfield.VisibilityTabConstant;
import it.cilea.hku.authority.service.ApplicationService;
import it.cilea.hku.authority.service.ExtendedTabService;
import it.cilea.hku.authority.util.ResearcherPageUtils;
import it.cilea.hku.authority.webui.dto.RPAnagraficaObjectDTO;
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
import org.dspace.eperson.EPerson;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

public class FormRPDynamicMetadataController
        extends
        AFormDynamicRPController<RPProperty, RPPropertiesDefinition, BoxResearcherPage, EditTabResearcherPage, AnagraficaObject<RPProperty, RPPropertiesDefinition>, RPNestedObject, RPNestedProperty, RPNestedPropertiesDefinition>
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
        List<EditTabResearcherPage> tabs = getApplicationService()
                .getTabsByVisibility(EditTabResearcherPage.class,
                        isAdmin);

        // check if request tab from view is active (check on collection before)
        EditTabResearcherPage editT = getApplicationService().get(
                EditTabResearcherPage.class,
                anagraficaObjectDTO.getTabId());
        if (!tabs.contains(editT))
        {
            throw new AuthorizeException(
                    "You not have needed authorization level to display this tab");
        }

        // collection of boxs
        List<BoxResearcherPage> propertyHolders = new LinkedList<BoxResearcherPage>();

        // if edit tab got a display tab (edit tab is hookup to display tab)
        // then edit box will be created from display box otherwise get all boxs
        // in edit tab
        if (editT.getDisplayTab() != null)
        {
            for (BoxResearcherPage box : editT.getDisplayTab()
                    .getMask())
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
        List<BoxResearcherPage> propertyHoldersCurrentAccessLevel = new LinkedList<BoxResearcherPage>();
        for (BoxResearcherPage propertyHolder : propertyHolders)
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
        for (BoxResearcherPage iph : propertyHoldersCurrentAccessLevel)
        {
            List<IContainable> temp = getApplicationService()
                    .<BoxResearcherPage, it.cilea.osd.jdyna.web.Tab<BoxResearcherPage>> findContainableInPropertyHolder(
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
        ResearcherPage researcher = getApplicationService().get(
                ResearcherPage.class, id);
        Context context = UIUtil.obtainContext(request);
        EPerson currentUser = context.getCurrentUser();
        if (currentUser==null || (researcher.getEpersonID()!=null && currentUser.getID()!=researcher.getEpersonID())
                && !AuthorizeManager.isAdmin(context))
        {
            throw new AuthorizeException(
                    "Only system admin can edit not personal researcher page");
        }

        if (AuthorizeManager.isAdmin(context))
        {
            isAdmin = true;
        }

        Integer areaId;
        if (paramTabId == null)
        {
            if (paramFuzzyTabId == null)
            {
                List<EditTabResearcherPage> tabs = getApplicationService()
                        .getTabsByVisibility(
                                EditTabResearcherPage.class, isAdmin);
                if (tabs.isEmpty())
                {
                    throw new AuthorizeException("No tabs defined!!");
                }
                areaId = tabs.get(0).getId();
            }
            else
            {
                EditTabResearcherPage fuzzyEditTab = (EditTabResearcherPage)((ApplicationService)getApplicationService()).getEditTabByDisplayTab(Integer.parseInt(paramFuzzyTabId),EditTabResearcherPage.class);
                areaId = fuzzyEditTab.getId();
            }
        }
        else
        {
            areaId = Integer.parseInt(paramTabId);
        }
        
        EditTabResearcherPage editT = getApplicationService().get(
                EditTabResearcherPage.class, areaId);
        List<BoxResearcherPage> propertyHolders = new LinkedList<BoxResearcherPage>();
        if (editT.getDisplayTab() != null)
        {
            for (BoxResearcherPage box : editT.getDisplayTab()
                    .getMask())
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

        for (BoxResearcherPage iph : propertyHolders)
        {
            if (editT.getDisplayTab() != null)
            {
                tipProprietaInArea
                        .addAll(getApplicationService()
                                .<BoxResearcherPage, it.cilea.osd.jdyna.web.Tab<BoxResearcherPage>> findContainableInPropertyHolder(
                                        BoxResearcherPage.class,
                                        iph.getId()));
            }
            else
            {
                tipProprietaInArea
                        .addAll(getApplicationService()
                                .<BoxResearcherPage, it.cilea.osd.jdyna.web.Tab<BoxResearcherPage>> findContainableInPropertyHolder(
                                        getClazzBox(), iph.getId()));
            }
        }

        RPAdditionalFieldStorage dynamicObject = researcher.getDynamicField();
        RPAnagraficaObjectDTO anagraficaObjectDTO = new RPAnagraficaObjectDTO(
                researcher);
        anagraficaObjectDTO.setTabId(areaId);
        anagraficaObjectDTO.setObjectId(dynamicObject.getId());
        anagraficaObjectDTO.setParentId(researcher.getId());

        List<RPPropertiesDefinition> realTPS = new LinkedList<RPPropertiesDefinition>();
        List<IContainable> structuralField = new LinkedList<IContainable>();
        for (IContainable c : tipProprietaInArea)
        {
            RPPropertiesDefinition rpPd = getApplicationService()
                    .findPropertiesDefinitionByShortName(
                            RPPropertiesDefinition.class, c.getShortName());
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
        RPAnagraficaObjectDTO anagraficaObjectDTO = (RPAnagraficaObjectDTO) object;

        String exitPage = "redirect:/cris/tools/rp/editDynamicData.htm?id="
                + anagraficaObjectDTO.getParentId();

        EditTabResearcherPage editT = getApplicationService().get(
                EditTabResearcherPage.class,
                anagraficaObjectDTO.getTabId());
        if (anagraficaObjectDTO.getNewTabId() != null)
        {
            exitPage += "&tabId=" + anagraficaObjectDTO.getNewTabId();
        }
        else
        {
            exitPage = "redirect:/cris/rp/"
                    + ResearcherPageUtils
                            .getPersistentIdentifier(anagraficaObjectDTO
                                    .getParentId()) + "/"
                    + editT.getShortName().substring(4) + ".html";
        }
        if (request.getParameter("cancel") != null)
        {
            return new ModelAndView(exitPage);
        }

        ResearcherPage researcher = getApplicationService().get(
                ResearcherPage.class, anagraficaObjectDTO.getParentId());
        RPAdditionalFieldStorage myObject = researcher.getDynamicField();

        List<BoxResearcherPage> propertyHolders = new LinkedList<BoxResearcherPage>();
        if (editT.getDisplayTab() != null)
        {
            for (BoxResearcherPage box : editT.getDisplayTab()
                    .getMask())
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

        for (BoxResearcherPage iph : propertyHolders)
        {

            tipProprietaInArea
                    .addAll(getApplicationService()
                            .<BoxResearcherPage, it.cilea.osd.jdyna.web.Tab<BoxResearcherPage>> findContainableInPropertyHolder(
                                    getClazzBox(), iph.getId()));

        }

        List<RPPropertiesDefinition> realTPS = new LinkedList<RPPropertiesDefinition>();
        List<IContainable> structuralField = new LinkedList<IContainable>();
        for (IContainable c : tipProprietaInArea)
        {
            RPPropertiesDefinition rpPd = getApplicationService()
                    .findPropertiesDefinitionByShortName(
                            RPPropertiesDefinition.class, c.getShortName());
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
        researcher.setSourceID(anagraficaObjectDTO.getSourceID());
        researcher.setStatus(anagraficaObjectDTO.getStatus());
        
        getApplicationService().saveOrUpdate(ResearcherPage.class, researcher);
        EditTabResearcherPage area = getApplicationService().get(
                getClazzTab(), anagraficaObjectDTO.getTabId());
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
        ResearcherPage researcher = getApplicationService().get(
                ResearcherPage.class, dto.getParentId());
        RPAdditionalFieldStorage myObject = researcher.getDynamicField();

        EditTabResearcherPage editT = getApplicationService().get(
                EditTabResearcherPage.class, dto.getTabId());
        List<BoxResearcherPage> propertyHolders = new LinkedList<BoxResearcherPage>();
        if (editT.getDisplayTab() != null)
        {
            for (BoxResearcherPage box : editT.getDisplayTab()
                    .getMask())
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

        for (BoxResearcherPage iph : propertyHolders)
        {

            tipProprietaInArea
                    .addAll(getApplicationService()
                            .<BoxResearcherPage, it.cilea.osd.jdyna.web.Tab<BoxResearcherPage>> findContainableInPropertyHolder(
                                    getClazzBox(), iph.getId()));

        }

        List<RPPropertiesDefinition> realTPS = new LinkedList<RPPropertiesDefinition>();
        List<IContainable> structuralField = new LinkedList<IContainable>();
        for (IContainable c : tipProprietaInArea)
        {
            RPPropertiesDefinition rpPd = getApplicationService()
                    .findPropertiesDefinitionByShortName(
                            RPPropertiesDefinition.class, c.getShortName());
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
