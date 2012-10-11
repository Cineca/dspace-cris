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
package it.cilea.hku.authority.service;

import it.cilea.hku.authority.dao.EditTabDao;
import it.cilea.hku.authority.model.ResearcherPage;
import it.cilea.hku.authority.model.dynamicfield.AbstractEditTab;
import it.cilea.hku.authority.model.dynamicfield.AbstractTab;
import it.cilea.hku.authority.model.dynamicfield.DecoratorRestrictedField;
import it.cilea.hku.authority.model.dynamicfield.RPPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.VisibilityTabConstant;
import it.cilea.osd.jdyna.dao.TabDao;
import it.cilea.osd.jdyna.model.ATipologia;
import it.cilea.osd.jdyna.model.AccessLevelConstants;
import it.cilea.osd.jdyna.model.Containable;
import it.cilea.osd.jdyna.model.IContainable;
import it.cilea.osd.jdyna.model.PropertiesDefinition;
import it.cilea.osd.jdyna.model.Property;
import it.cilea.osd.jdyna.web.Box;
import it.cilea.osd.jdyna.web.IPropertyHolder;
import it.cilea.osd.jdyna.web.Tab;
import it.cilea.osd.jdyna.web.TabService;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dspace.core.ConfigurationManager;

public class ExtendedTabService<H extends Box<Containable>, D extends AbstractTab<H>, T extends AbstractEditTab<H, D>>
        extends TabService
{

    public final static String PREFIX_TITLE_EDIT_BOX = "Edit ";

    public final static String PREFIX_SHORTNAME_EDIT_BOX = "edit";

    public final static String PREFIX_TITLE_EDIT_TAB = "Edit ";

    public final static String PREFIX_SHORTNAME_EDIT_TAB = "edit";

    public static Map<String, List<IContainable>> cacheStructuredMetadata = new HashMap<String, List<IContainable>>();

    /**
     * {@inheritDoc}
     */
    @Override
    @Deprecated
    protected void findOtherContainables(List<IContainable> containables)
    {
        findOtherContainables(containables, RPPropertiesDefinition.class.getName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void findOtherContainables(List<IContainable> containables,
            String extraPrefixConfiguration)
    {
        String dspaceProperty = extraPrefixConfiguration + ".containables";
        log.debug("Get from configuration additional containables object : "
                + dspaceProperty);
        String confContainables = ConfigurationManager
                .getProperty(dspaceProperty);
        if (confContainables != null && !confContainables.isEmpty())
        {
            String[] listConfContainables = confContainables.split(",");
            for (String containable : listConfContainables)
            {
                DecoratorRestrictedField drf = new DecoratorRestrictedField();
                drf.setReal(containable.trim());
                extractIsMandatory(containable, drf);
                extractIsRepeatable(containable, drf);
                extractAccessLevel(containable, drf);
                containables.add(drf);
            }
        }
    }

    @Deprecated
    private void extractIsMandatory(String containable,
            DecoratorRestrictedField drf)
    {
        extractIsMandatory(containable, drf, RPPropertiesDefinition.class.getName());
    }

    private void extractIsMandatory(String containable,
            DecoratorRestrictedField drf, String extraPrefixConfiguration)
    {
        String fieldsNotNullable = ConfigurationManager
                .getProperty(extraPrefixConfiguration
                        + ".containables.structural.mandatory");
        boolean notnullable = fieldsNotNullable.contains(containable);
        drf.setMandatory(notnullable);
    }

    @Deprecated
    private void extractAccessLevel(String containable,
            DecoratorRestrictedField drf)
    {
        extractAccessLevel(containable, drf, RPPropertiesDefinition.class.getName());
    }

    private void extractAccessLevel(String containable,
            DecoratorRestrictedField drf, String extraPrefixConfiguration)
    {
        String fieldsAccessLevelHIGH = ConfigurationManager
                .getProperty(extraPrefixConfiguration
                        + ".containables.box.staticfields.visibility.high");
        boolean accessLevel = fieldsAccessLevelHIGH.contains(containable);
        if (accessLevel)
        {
            drf.setAccessLevel(AccessLevelConstants.HIGH_ACCESS);
            return;
        }
        String fieldsAccessLevelSTANDARD = ConfigurationManager
                .getProperty(extraPrefixConfiguration
                        + ".containables.box.staticfields.visibility.standard");
        accessLevel = fieldsAccessLevelSTANDARD.contains(containable);
        if (accessLevel)
        {
            drf.setAccessLevel(AccessLevelConstants.STANDARD_ACCESS);
            return;
        }
        String fieldsAccessLevelADMIN = ConfigurationManager
                .getProperty(extraPrefixConfiguration
                        + ".containables.box.staticfields.visibility.admin");
        accessLevel = fieldsAccessLevelADMIN.contains(containable);
        if (accessLevel)
        {
            drf.setAccessLevel(AccessLevelConstants.ADMIN_ACCESS);
            return;
        }
        String fieldsAccessLevelLOW = ConfigurationManager
                .getProperty(extraPrefixConfiguration
                        + ".containables.box.staticfields.visibility.low");
        accessLevel = fieldsAccessLevelLOW.contains(containable);
        if (accessLevel)
        {
            drf.setAccessLevel(AccessLevelConstants.LOW_ACCESS);
            return;
        }
        drf.setAccessLevel(AccessLevelConstants.ADMIN_ACCESS);
    }

    private void extractIsRepeatable(String containable,
            DecoratorRestrictedField drf)
    {
        Method[] methods = ResearcherPage.class.getMethods();
        Method method = null;

        for (Method m : methods)
        {
            if (m.getName().toLowerCase()
                    .equals("get" + containable.trim().toLowerCase()))
            {
                method = m;
                break;
            }
        }
        if (method != null
                && method.getReturnType().isAssignableFrom(List.class))
        {
            drf.setRepeatable(true);
        }
        else
        {
            drf.setRepeatable(false);
        }
    }

    @Override
    @Deprecated
    public void findOtherContainablesInBoxByConfiguration(String holderName,
            List<IContainable> containables)
    {
        findOtherContainablesInBoxByConfiguration(holderName, containables,
                "researcherpage");
    }

    public void findOtherContainablesInBoxByConfiguration(String holderName,
            List<IContainable> containables, String extraPrefixConfiguration)
    {
        String boxName = StringUtils.deleteWhitespace(holderName).trim()
                .toLowerCase();

        String dspaceProperty = extraPrefixConfiguration + ".containables.box."
                + boxName;

        if (cacheStructuredMetadata.containsKey(dspaceProperty))
        {
            containables.addAll(cacheStructuredMetadata.get(dspaceProperty));
        }
        else
        {            
            log.debug("Get from configuration additional containables object : "
                    + dspaceProperty);
            String confContainables = ConfigurationManager
                    .getProperty(dspaceProperty);
            List<IContainable> tmp = new LinkedList<IContainable>();
            if (confContainables != null && !confContainables.isEmpty())
            {
                String[] listConfContainables = confContainables.split(",");
                for (String containable : listConfContainables)
                {
                    DecoratorRestrictedField drf = new DecoratorRestrictedField();
                    drf.setReal(containable.trim());
                    extractIsMandatory(containable, drf);
                    extractIsRepeatable(containable, drf);
                    extractAccessLevel(containable, drf);
                    tmp.add(drf);
                    
                }
                containables.addAll(tmp);                
            }            
            cacheStructuredMetadata.put(dspaceProperty, tmp);
        }
    }

    /**
     * Decouple edit tab wrapper from display tab id, check persistence object
     * is alive and after unhook it.
     * 
     * @param tabId
     */
    public void decoupleEditTabByDisplayTab(int tabId, Class<T> clazz)
    {

        T editTab = getEditTabByDisplayTab(tabId, clazz);
        if (editTab != null)
        {
            for (H box : editTab.getDisplayTab().getMask())
            {
                editTab.getMask().add(box);
            }
            editTab.setDisplayTab(null);
            saveOrUpdate(clazz, editTab);
        }
    }

    /**
     * Hook edit tab with display tab
     * 
     * @param id
     */
    public void hookUpEditTabToDisplayTab(Integer editTabId,
            Integer displayTabId, Class<T> clazz)
    {
        T editTab = get(clazz, editTabId);
        if (editTab != null)
        {
            D displayTab = get(editTab.getDisplayTabClass(), displayTabId);
            editTab.setDisplayTab(displayTab);
            saveOrUpdate(clazz, editTab);
        }

    }

    /**
     * Get edit tab by display tab id
     * 
     * @param tabId
     * @return
     */
    public T getEditTabByDisplayTab(Integer tabId, Class<T> clazz)
    {
        EditTabDao<H, D, T> dao = (EditTabDao<H, D, T>) getDaoByModel(clazz);
        T editTab = dao.uniqueByDisplayTab(tabId);
        return editTab;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void getOtherContainableOnCreation(List<IContainable> containables)
    {
        // customization code
    }

    /**
     * 
     * Find by access level, @see {@link VisibilityTabConstant}
     * 
     * 
     * @param isAdmin
     *            three mode, null get all visibility (ADMIN and OWNER
     *            visibility), true get all admin access level, false get all
     *            owner rp access level
     * @return
     */
    @Override
    public <H extends IPropertyHolder<Containable>, T extends Tab<H>> List<T> getTabsByVisibility(
            Class<T> model, Boolean isAdmin)
    {

        TabDao<H, T> dao = (TabDao<H, T>) getDaoByModel(model);
        List<T> tabs = new LinkedList<T>();
        if (isAdmin == null)
        {
            tabs.addAll(dao.findByAnonimous());
//            tabs.addAll(dao.findByAccessLevel(VisibilityTabConstant.HIGH));
        }
        else
        {
            if (isAdmin)
            {
                tabs.addAll(dao.findByAdmin());
//                tabs.addAll(dao.findByAccessLevel(VisibilityTabConstant.HIGH));
//                tabs.addAll(dao.findByAccessLevel(VisibilityTabConstant.ADMIN));
//                tabs.addAll(dao
//                        .findByAccessLevel(VisibilityTabConstant.STANDARD));
            }
            else
            {
                tabs.addAll(dao.findByOwner());
//                tabs.addAll(dao.findByAccessLevel(VisibilityTabConstant.HIGH));
//                tabs.addAll(dao
//                        .findByAccessLevel(VisibilityTabConstant.STANDARD));
//                tabs.addAll(dao.findByAccessLevel(VisibilityTabConstant.LOW));
            }
        }

        Collections.sort(tabs);
        return tabs;

    }

}
