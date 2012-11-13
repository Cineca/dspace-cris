/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package it.cilea.hku.authority.webui.controller.jdyna;

import it.cilea.hku.authority.model.Investigator;
import it.cilea.hku.authority.model.Project;
import it.cilea.hku.authority.model.ResearcherPage;
import it.cilea.hku.authority.model.dynamicfield.BoxProject;
import it.cilea.hku.authority.model.dynamicfield.DecoratorProjectPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.EditTabProject;
import it.cilea.hku.authority.model.dynamicfield.EditTabResearcherPage;
import it.cilea.hku.authority.model.dynamicfield.ProjectAdditionalFieldStorage;
import it.cilea.hku.authority.model.dynamicfield.ProjectNestedObject;
import it.cilea.hku.authority.model.dynamicfield.ProjectNestedPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.ProjectNestedProperty;
import it.cilea.hku.authority.model.dynamicfield.ProjectPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.ProjectProperty;
import it.cilea.hku.authority.model.dynamicfield.RPAdditionalFieldStorage;
import it.cilea.hku.authority.model.dynamicfield.RPPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.VisibilityTabConstant;
import it.cilea.hku.authority.service.ApplicationService;
import it.cilea.hku.authority.util.ResearcherPageUtils;
import it.cilea.hku.authority.webui.dto.ProjectAnagraficaObjectDTO;
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

public class FormProjectDynamicMetadataController
        extends
        AFormDynamicRGController<ProjectProperty, ProjectPropertiesDefinition, BoxProject, EditTabProject, AnagraficaObject<ProjectProperty, ProjectPropertiesDefinition>, ProjectNestedObject, ProjectNestedProperty, ProjectNestedPropertiesDefinition>
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
        List<EditTabProject> tabs = getApplicationService()
                .getTabsByVisibility(EditTabProject.class, isAdmin);

        // check if request tab from view is active (check on collection before)
        EditTabProject editT = getApplicationService().get(
                EditTabProject.class, anagraficaObjectDTO.getTabId());
        if (!tabs.contains(editT))
        {
            throw new AuthorizeException(
                    "You not have needed authorization level to display this tab");
        }

        // collection of boxs
        List<BoxProject> propertyHolders = new LinkedList<BoxProject>();

        // if edit tab got a display tab (edit tab is hookup to display tab)
        // then edit box will be created from display box otherwise get all boxs
        // in edit tab
        if (editT.getDisplayTab() != null)
        {
            for (BoxProject box : editT.getDisplayTab().getMask())
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
        List<BoxProject> propertyHoldersCurrentAccessLevel = new LinkedList<BoxProject>();
        for (BoxProject propertyHolder : propertyHolders)
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
        for (BoxProject iph : propertyHoldersCurrentAccessLevel)
        {
            List<IContainable> temp = getApplicationService()
                    .<BoxProject, it.cilea.osd.jdyna.web.Tab<BoxProject>> findContainableInPropertyHolder(
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
        Project grant = getApplicationService().get(Project.class, id);
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
                List<EditTabProject> tabs = getApplicationService()
                        .getTabsByVisibility(EditTabProject.class, isAdmin);
                if (tabs.isEmpty())
                {
                    throw new AuthorizeException("No tabs defined!!");
                }
                areaId = tabs.get(0).getId();
            }
            else
            {
                EditTabProject fuzzyEditTab = (EditTabProject) ((ApplicationService) getApplicationService())
                        .getEditTabByDisplayTab(
                                Integer.parseInt(paramFuzzyTabId),
                                EditTabProject.class);
                areaId = fuzzyEditTab.getId();
            }
        }
        else
        {
            areaId = Integer.parseInt(paramTabId);
        }

        EditTabProject editT = getApplicationService().get(
                EditTabProject.class, areaId);
        List<BoxProject> propertyHolders = new LinkedList<BoxProject>();
        if (editT.getDisplayTab() != null)
        {
            for (BoxProject box : editT.getDisplayTab().getMask())
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

        for (BoxProject iph : propertyHolders)
        {
            if (editT.getDisplayTab() != null)
            {
                tipProprietaInArea
                        .addAll(getApplicationService()
                                .<BoxProject, it.cilea.osd.jdyna.web.Tab<BoxProject>> findContainableInPropertyHolder(
                                        BoxProject.class, iph.getId()));
            }
            else
            {
                tipProprietaInArea
                        .addAll(getApplicationService()
                                .<BoxProject, it.cilea.osd.jdyna.web.Tab<BoxProject>> findContainableInPropertyHolder(
                                        getClazzBox(), iph.getId()));
            }
        }
        ProjectAdditionalFieldStorage dynamicObject = grant.getDynamicField();
        ProjectAnagraficaObjectDTO anagraficaObjectDTO = new ProjectAnagraficaObjectDTO(
                grant);
        anagraficaObjectDTO.setTabId(areaId);
        anagraficaObjectDTO.setObjectId(grant.getId());
        anagraficaObjectDTO.setParentId(grant.getId());
        if (grant.getInvestigator() != null)
        {
            if (grant.getInvestigator().getIntInvestigator() != null)
            {
                anagraficaObjectDTO.setInvestigator(ResearcherPageUtils
                        .getPersistentIdentifier(grant.getInvestigator()
                                .getIntInvestigator().getId()));
            }
            else
            {
                anagraficaObjectDTO.setInvestigator(grant.getInvestigator()
                        .getExtInvestigator());
            }
        }

        for (Investigator inv : grant.getCoInvestigators())
        {
            if (inv.getIntInvestigator() != null)
            {
                anagraficaObjectDTO.getCoInvestigators().add(
                        ResearcherPageUtils.getPersistentIdentifier(inv
                                .getIntInvestigator()));
            }
            else
            {
                anagraficaObjectDTO.getCoInvestigators().add(
                        inv.getExtInvestigator());
            }
        }

        List<ProjectPropertiesDefinition> realTPS = new LinkedList<ProjectPropertiesDefinition>();
        List<IContainable> structuralField = new LinkedList<IContainable>();
        for (IContainable c : tipProprietaInArea)
        {

            ProjectPropertiesDefinition rpPd = getApplicationService()
                    .findPropertiesDefinitionByShortName(
                            ProjectPropertiesDefinition.class, c.getShortName());
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
        ProjectAnagraficaObjectDTO anagraficaObjectDTO = (ProjectAnagraficaObjectDTO) object;

        String exitPage = "redirect:/cris/tools/project/editDynamicData.htm?id="
                + anagraficaObjectDTO.getParentId();

        
        EditTabProject editT = getApplicationService().get(
                EditTabProject.class, anagraficaObjectDTO.getTabId());
        if (anagraficaObjectDTO.getNewTabId() != null)
        {
            exitPage += "&tabId=" + anagraficaObjectDTO.getNewTabId();
        }
        else
        {
            exitPage = "redirect:/cris/project/"
                    + ResearcherPageUtils.getPersistentIdentifier(anagraficaObjectDTO
                                    .getParentId()) + "/"
                    + editT.getShortName().substring(4) + ".html";
        }
        if (request.getParameter("cancel") != null)
        {
            return new ModelAndView(exitPage);
        }
        
        
        Project grant = getApplicationService().get(Project.class,
                anagraficaObjectDTO.getParentId());
        ProjectAdditionalFieldStorage myObject = grant.getDynamicField();
        
        List<BoxProject> propertyHolders = new LinkedList<BoxProject>();
        if (editT.getDisplayTab() != null)
        {
            for (BoxProject box : editT.getDisplayTab().getMask())
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

        for (BoxProject iph : propertyHolders)
        {

            tipProprietaInArea
                    .addAll(getApplicationService()
                            .<BoxProject, it.cilea.osd.jdyna.web.Tab<BoxProject>> findContainableInPropertyHolder(
                                    getClazzBox(), iph.getId()));

        }

        List<ProjectPropertiesDefinition> realTPS = new LinkedList<ProjectPropertiesDefinition>();
        List<IContainable> structuralField = new LinkedList<IContainable>();
        for (IContainable c : tipProprietaInArea)
        {
            ProjectPropertiesDefinition rpPd = getApplicationService()
                    .findPropertiesDefinitionByShortName(
                            ProjectPropertiesDefinition.class, c.getShortName());
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
        grant.setStatus(anagraficaObjectDTO.getStatus());
        
        String investigator = anagraficaObjectDTO.getInvestigator();
        Investigator realInvestigator = new Investigator();
        if (investigator != null && !investigator.isEmpty())
        {
            if (investigator.trim().matches("rp[0-9]{5}"))
            {
                ResearcherPage rp = getApplicationService().get(
                        ResearcherPage.class,
                        ResearcherPageUtils
                                .getRealPersistentIdentifier(investigator
                                        .trim()));
                realInvestigator.setIntInvestigator(rp);
            }
            else
            {
                realInvestigator.setExtInvestigator(investigator.trim());
            }
        }
        grant.setInvestigator(realInvestigator);
        grant.setCoInvestigators(null);

        List<String> coinvestigators = anagraficaObjectDTO.getCoInvestigators();
        for (String co : coinvestigators)
        {
            realInvestigator = new Investigator();
            if (co != null && !co.isEmpty())
            {
                if (co.trim().matches("rp[0-9]{5}"))
                {
                    ResearcherPage rp = getApplicationService().get(
                            ResearcherPage.class,
                            ResearcherPageUtils.getRealPersistentIdentifier(co
                                    .trim()));
                    realInvestigator.setIntInvestigator(rp);
                }
                else
                {
                    realInvestigator.setExtInvestigator(co.trim());
                }
            }
            grant.getCoInvestigators().add(realInvestigator);
        }
        getApplicationService().saveOrUpdate(Project.class, grant);
        EditTabProject area = getApplicationService().get(getClazzTab(),
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
        Project researcher = getApplicationService().get(Project.class,
                dto.getParentId());
        ProjectAdditionalFieldStorage myObject = researcher.getDynamicField();

        EditTabProject editT = getApplicationService().get(
                EditTabProject.class, dto.getTabId());
        List<BoxProject> propertyHolders = new LinkedList<BoxProject>();
        if (editT.getDisplayTab() != null)
        {
            for (BoxProject box : editT.getDisplayTab().getMask())
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

        for (BoxProject iph : propertyHolders)
        {

            tipProprietaInArea
                    .addAll(getApplicationService()
                            .<BoxProject, it.cilea.osd.jdyna.web.Tab<BoxProject>> findContainableInPropertyHolder(
                                    getClazzBox(), iph.getId()));

        }

        List<ProjectPropertiesDefinition> realTPS = new LinkedList<ProjectPropertiesDefinition>();
        List<IContainable> structuralField = new LinkedList<IContainable>();
        for (IContainable c : tipProprietaInArea)
        {
            ProjectPropertiesDefinition rpPd = getApplicationService()
                    .findPropertiesDefinitionByShortName(
                            ProjectPropertiesDefinition.class, c.getShortName());
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
