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
package it.cilea.hku.authority.webui.web.tag;

import it.cilea.hku.authority.model.OrganizationUnit;
import it.cilea.hku.authority.model.Project;
import it.cilea.hku.authority.model.ResearcherPage;
import it.cilea.hku.authority.model.RestrictedField;
import it.cilea.hku.authority.model.dynamicfield.BoxOrganizationUnit;
import it.cilea.hku.authority.model.dynamicfield.BoxProject;
import it.cilea.hku.authority.model.dynamicfield.BoxResearcherPage;
import it.cilea.hku.authority.model.dynamicfield.DecoratorRPPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.DecoratorRPTypeNested;
import it.cilea.hku.authority.model.dynamicfield.RPAdditionalFieldStorage;
import it.cilea.hku.authority.model.dynamicfield.RPNestedObject;
import it.cilea.hku.authority.model.dynamicfield.RPNestedPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.RPNestedProperty;
import it.cilea.hku.authority.model.dynamicfield.RPPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.RPProperty;
import it.cilea.hku.authority.model.dynamicfield.RPTypeNestedObject;
import it.cilea.hku.authority.service.ApplicationService;
import it.cilea.hku.authority.util.Researcher;
import it.cilea.hku.authority.util.ResearcherPageUtils;
import it.cilea.osd.jdyna.components.IBeanComponent;
import it.cilea.osd.jdyna.components.IComponent;
import it.cilea.osd.jdyna.model.ADecoratorPropertiesDefinition;
import it.cilea.osd.jdyna.model.ADecoratorTypeDefinition;
import it.cilea.osd.jdyna.model.ANestedObject;
import it.cilea.osd.jdyna.model.ANestedPropertiesDefinition;
import it.cilea.osd.jdyna.model.ATypeNestedObject;
import it.cilea.osd.jdyna.model.AWidget;
import it.cilea.osd.jdyna.model.AccessLevelConstants;
import it.cilea.osd.jdyna.model.AnagraficaSupport;
import it.cilea.osd.jdyna.model.Containable;
import it.cilea.osd.jdyna.model.IContainable;
import it.cilea.osd.jdyna.model.PropertiesDefinition;
import it.cilea.osd.jdyna.model.Property;
import it.cilea.osd.jdyna.web.Box;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dspace.core.ConfigurationManager;

public class ResearcherTagLibraryFunctions
{

    private static ApplicationService applicationService;

    /**
     * log4j category
     */
    public static final Log log = LogFactory
            .getLog(ResearcherTagLibraryFunctions.class);

    public static boolean isGroupFieldsHidden(
            RPAdditionalFieldStorage anagraficaObject, String logicGroup)
    {
        boolean result = true;
        String dspaceProperty = "researcherpage.containables.box.logicgrouped."
                + logicGroup;
        log.debug("Get from configuration additional containables object : "
                + dspaceProperty);
        String confContainables = ConfigurationManager
                .getProperty(dspaceProperty);
        if (confContainables != null && !confContainables.isEmpty())
        {
            String[] listConfContainables = confContainables.split(",");
            for (String cont : listConfContainables)
            {
                cont = cont.trim();
                for (RPProperty p : anagraficaObject.getAnagrafica4view().get(
                        cont))
                {
                    boolean resultPiece = checkDynamicVisibility(
                            anagraficaObject, p.getTypo().getShortName(), p
                                    .getTypo().getRendering(), p.getTypo());

                    if (resultPiece == false)
                    {
                        return false;
                    }
                }
            }
        }
        return result;

    }

    public static boolean isGroupFieldsHiddenWithStructural(
            ResearcherPage researcher, String logicGroup)
            throws IllegalArgumentException, IllegalAccessException,
            InvocationTargetException
    {
        boolean result = true;
        String dspaceProperty = "researcherpage.containables.box.logicgrouped."
                + logicGroup + ".structural";
        log.debug("Get from configuration additional containables object : "
                + dspaceProperty);
        String confContainables = ConfigurationManager
                .getProperty(dspaceProperty);

        if (confContainables != null && !confContainables.isEmpty())
        {
            String[] listConfContainables = confContainables.split(",");
            for (String cont : listConfContainables)
            {
                cont = cont.trim();
                Method[] methods = researcher.getClass().getMethods();
                Object field = null;
                Method method = null;
                for (Method m : methods)
                {
                    if (m.getName().toLowerCase()
                            .equals("get" + cont.toLowerCase()))
                    {
                        field = m.invoke(researcher, null);
                        method = m;
                        break;
                    }
                }
                if (method.getReturnType().isAssignableFrom(List.class))
                {
                    for (RestrictedField rr : (List<RestrictedField>) field)
                    {

                        if (rr.getVisibility() == 1)
                        {
                            if (rr.getValue() != null
                                    && !rr.getValue().isEmpty())
                            {
                                return false;
                            }
                        }

                    }
                }
                else if (method.getReturnType().isAssignableFrom(String.class))
                {
                    String rr = (String) field;
                    if (rr != null && !rr.isEmpty())
                    {
                        return false;
                    }
                }
                else
                {
                    RestrictedField rr = (RestrictedField) field;
                    if (rr.getVisibility() == 1)
                    {
                        if (rr.getValue() != null && !rr.getValue().isEmpty())
                        {
                            return false;
                        }
                    }
                }
            }
        }

        result = isGroupFieldsHidden(researcher.getDynamicField(), logicGroup);
        return result;

    }

    public static boolean isBoxHidden(ResearcherPage anagrafica, String boxName)
            throws IllegalArgumentException, IllegalAccessException,
            InvocationTargetException
    {
        BoxResearcherPage box = applicationService.getBoxByShortName(
                BoxResearcherPage.class, boxName);

        return isBoxHidden(anagrafica, box);

    }

    public static boolean isBoxHidden(Project anagrafica, String boxName)
            throws IllegalArgumentException, IllegalAccessException,
            InvocationTargetException
    {
        BoxProject box = applicationService.getBoxByShortName(BoxProject.class,
                boxName);

        return isBoxHiddenInternal(anagrafica, box);

    }

    public static boolean isBoxHidden(Object anagrafica, String boxName)
            throws IllegalArgumentException, IllegalAccessException,
            InvocationTargetException
    {
        if (anagrafica instanceof Project)
        {
            BoxProject box = applicationService.getBoxByShortName(
                    BoxProject.class, boxName);
            return isBoxHidden((Project) anagrafica, box);
        }
        if (anagrafica instanceof OrganizationUnit)
        {
            BoxOrganizationUnit box = applicationService.getBoxByShortName(
                    BoxOrganizationUnit.class, boxName);
            return isBoxHidden((OrganizationUnit) anagrafica, box);
        }
        BoxResearcherPage box = applicationService.getBoxByShortName(
                BoxResearcherPage.class, boxName);

        return isBoxHidden((ResearcherPage) anagrafica, box);

    }

    public static boolean isBoxHidden(ResearcherPage anagrafica,
            BoxResearcherPage box)
    {

        Researcher researcher = new Researcher();

        Map<String, IComponent> rpComponent = researcher.getRPComponents();
        if (rpComponent != null && !rpComponent.isEmpty())
        {
            for (String key : rpComponent.keySet())
            {
                                
                if (box.getShortName().equals(key))
                {
                    IComponent component = rpComponent.get(key);
                    component.setShortName(box.getShortName());
                    Map<String, IBeanComponent> comp = component.getTypes();

                    for (String compp : comp.keySet())
                    {
                        if (component.count(comp.get(compp)
                                .getComponentIdentifier(), anagrafica.getId()) > 0)
                        {
                            return false;
                        }
                    }

                }
            }

        }
        return isBoxHiddenInternal(anagrafica.getDynamicField(), box)
                && isBoxHiddenWithStructural(anagrafica, box);
    }

    public static boolean isBoxHidden(Project anagrafica, BoxProject box)
    {
        return isBoxHiddenInternal(anagrafica, box);
    }

    public static boolean isBoxHidden(OrganizationUnit anagrafica,
            BoxOrganizationUnit box)
    {
        return isBoxHiddenInternal(anagrafica, box);
    }

    @Deprecated
    public static <TP extends PropertiesDefinition, P extends Property<TP>, B extends Box<Containable>> boolean isBoxHiddenWithStructural(
            ResearcherPage anagrafica, B box)
    {
        boolean result = true;

        List<IContainable> containables = new LinkedList<IContainable>();

        applicationService.findOtherContainablesInBoxByConfiguration(
                box.getShortName(), containables,
                RPPropertiesDefinition.class.getName());
        for (IContainable decorator : containables)
        {
            String shortName = decorator.getShortName();
            Method method = null;
            Object field = null;

            try
            {
                method = anagrafica.getClass().getDeclaredMethod(
                        "get" + StringUtils.capitalise(shortName), null);
                field = method.invoke(anagrafica, null);
            }
            catch (IllegalArgumentException e)
            {
                throw new RuntimeException(e.getMessage(), e);
            }
            catch (IllegalAccessException e)
            {
                throw new RuntimeException(e.getMessage(), e);
            }
            catch (InvocationTargetException e)
            {
                throw new RuntimeException(e.getMessage(), e);
            }
            catch (SecurityException e)
            {
                throw new RuntimeException(e.getMessage(), e);
            }
            catch (NoSuchMethodException e)
            {
                throw new RuntimeException(e.getMessage(), e);
            }

            if (method.getReturnType().isAssignableFrom(List.class))
            {

                for (RestrictedField rr : (List<RestrictedField>) field)
                {

                    if (rr.getVisibility() == 1)
                    {
                        return false;
                    }
                }

            }
            else if (method.getReturnType().isAssignableFrom(String.class))
            {
                return false;
            }
            else
            {
                RestrictedField rr = (RestrictedField) field;
                if (rr.getVisibility() == 1)
                {
                    return false;
                }
            }
        }
        return result;
    }

    public static <TP extends PropertiesDefinition, P extends Property<TP>, B extends Box<Containable>> boolean isBoxHiddenInternal(
            AnagraficaSupport<P, TP> anagrafica, B box)
    {

        boolean result = true;

        List<IContainable> containables = new LinkedList<IContainable>();

        containables.addAll(box.getMask());

        for (IContainable cont : containables)
        {

            if (cont instanceof ADecoratorTypeDefinition)
            {
                ADecoratorTypeDefinition decorator = (ADecoratorTypeDefinition) cont;
                ATypeNestedObject<ANestedPropertiesDefinition> real = ((ATypeNestedObject<ANestedPropertiesDefinition>) decorator
                        .getReal());
                List<ANestedObject> results = applicationService
                        .getNestedObjectsByParentIDAndTypoID(Integer
                                .parseInt(anagrafica.getIdentifyingValue()),
                                (real.getId()), ANestedObject.class);
                boolean resultPiece = true;
                for (ANestedObject object : results)
                {
                    for (ANestedPropertiesDefinition rpp : real.getMask())
                    {
                        resultPiece = checkDynamicVisibility(object,
                                rpp.getShortName(), rpp.getRendering(), rpp);
                        if (resultPiece == false)
                        {
                            return false;
                        }
                    }
                }

            }

            if (cont instanceof ADecoratorPropertiesDefinition)
            {
                ADecoratorPropertiesDefinition decorator = (ADecoratorPropertiesDefinition) cont;
                boolean resultPiece = checkDynamicVisibility(anagrafica,
                        decorator.getShortName(), decorator.getRendering(),
                        (TP) decorator.getReal());
                if (resultPiece == false)
                {
                    return false;
                }
            }

        }

        return result;
    }

    private static <TP extends PropertiesDefinition, P extends Property<TP>> boolean checkDynamicVisibility(
            AnagraficaSupport<P, TP> anagrafica, String shortname,
            AWidget rendering, TP rpPropertiesDefinition)
    {

        for (P p : anagrafica.getAnagrafica4view().get(shortname))
        {
            if (p.getVisibility() == 1)
            {
                return false;
            }
        }

        return true;
    }

    public static <H extends Box<Containable>> boolean isThereMetadataNoEditable(
            String boxName, Class<H> model) throws IllegalArgumentException,
            IllegalAccessException, InvocationTargetException
    {
        boolean result = false;
        List<IContainable> containables = new LinkedList<IContainable>();
        H box = applicationService.getBoxByShortName(model, boxName);
        containables.addAll(box.getMask());
        applicationService.findOtherContainablesInBoxByConfiguration(boxName,
                containables);

        for (IContainable decorator : containables)
        {

            if (decorator.getAccessLevel().equals(
                    AccessLevelConstants.STANDARD_ACCESS)
                    || decorator.getAccessLevel().equals(
                            AccessLevelConstants.LOW_ACCESS))
            {
                return true;
            }

        }
        return result;

    }

    public static int countBoxPublicMetadata(ResearcherPage anagrafica,
            String boxName, Boolean onlyComplexValue)
            throws IllegalArgumentException, IllegalAccessException,
            InvocationTargetException
    {
        BoxResearcherPage box = applicationService.getBoxByShortName(
                BoxResearcherPage.class, boxName);

        return countBoxPublicMetadata(anagrafica, box, onlyComplexValue);

    }

    public static int countBoxPublicMetadata(ResearcherPage anagrafica,
            BoxResearcherPage box, boolean onlyComplexValue)
    {
        int result = 0;
        List<IContainable> containables = new LinkedList<IContainable>();

        containables.addAll(box.getMask());

        for (IContainable cont : containables)
        {

            if (cont instanceof DecoratorRPTypeNested)
            {
                DecoratorRPTypeNested decorator = (DecoratorRPTypeNested) cont;
                RPTypeNestedObject real = (RPTypeNestedObject) decorator
                        .getReal();
                List<RPNestedObject> results = applicationService
                        .getNestedObjectsByParentIDAndTypoID(Integer
                                .parseInt(anagrafica.getIdentifyingValue()),
                                (real.getId()), RPNestedObject.class);

                external: for (RPNestedObject object : results)
                {
                    for (RPNestedPropertiesDefinition rpp : real.getMask())
                    {

                        for (RPNestedProperty p : object.getAnagrafica4view()
                                .get(rpp.getShortName()))
                        {
                            if (p.getVisibility() == 1)
                            {
                                result++;
                                break external;
                            }
                        }

                    }
                }

            }

            if (cont instanceof DecoratorRPPropertiesDefinition)
            {
                DecoratorRPPropertiesDefinition decorator = (DecoratorRPPropertiesDefinition) cont;
                result += countDynamicPublicMetadata(
                        anagrafica.getDynamicField(), decorator.getShortName(),
                        decorator.getRendering(), decorator.getReal(),
                        onlyComplexValue);
            }

        }

        if (!onlyComplexValue)
        {
            containables = new LinkedList<IContainable>();
            applicationService.findOtherContainablesInBoxByConfiguration(
                    box.getShortName(), containables);
            for (IContainable decorator : containables)
            {
                String shortName = decorator.getShortName();
                Method[] methods = anagrafica.getClass().getMethods();
                Object field = null;
                Method method = null;
                for (Method m : methods)
                {
                    if (m.getName().toLowerCase()
                            .equals("get" + shortName.toLowerCase()))
                    {
                        try
                        {
                            field = m.invoke(anagrafica, null);
                        }
                        catch (IllegalArgumentException e)
                        {
                            throw new RuntimeException(e.getMessage(), e);
                        }
                        catch (IllegalAccessException e)
                        {
                            throw new RuntimeException(e.getMessage(), e);
                        }
                        catch (InvocationTargetException e)
                        {
                            throw new RuntimeException(e.getMessage(), e);
                        }
                        method = m;
                        break;
                    }
                }
                if (method.getReturnType().isAssignableFrom(List.class))
                {

                    for (RestrictedField rr : (List<RestrictedField>) field)
                    {

                        if (rr.getVisibility() == 1)
                        {
                            result++;
                        }
                    }

                }
                else if (method.getReturnType().isAssignableFrom(String.class))
                {
                    result++;
                }
                else
                {
                    RestrictedField rr = (RestrictedField) field;
                    if (rr.getVisibility() == 1)
                    {
                        result++;
                    }
                }
            }
        }
        return result;
    }

    public static <P extends Property<PD>, PD extends PropertiesDefinition, T extends AnagraficaSupport<P, PD>> int countDynamicPublicMetadata(
            T anagrafica, String shortname, AWidget rendering,
            PD rpPropertiesDefinition, boolean onlyComplexValue)
    {
        int result = 0;
        if (!onlyComplexValue)
        {
            for (P p : anagrafica.getAnagrafica4view().get(shortname))
            {
                if (p.getVisibility() == 1)
                {
                    result++;
                }
            }
        }

        return result;
    }

    public void setApplicationService(ApplicationService applicationService)
    {
        ResearcherTagLibraryFunctions.applicationService = applicationService;
    }

    public static ApplicationService getApplicationService()
    {
        return applicationService;
    }

    public static String rpkey(Integer id)
    {
        return ResearcherPageUtils.getPersistentIdentifier(id);
    }

    public static <TP extends PropertiesDefinition, P extends Property<TP>> List<Containable<P>> sortContainableByComparator(
            List<Containable<P>> containables, String comparatorName)
            throws InstantiationException, IllegalAccessException,
            ClassNotFoundException
    {

        Comparator comparator = (Comparator) Class.forName(comparatorName)
                .newInstance();
        Collections.sort(containables, comparator);
        return containables;

    }

    public static <TP extends PropertiesDefinition, P extends Property<TP>, C extends Containable<P>> List<Box<C>> sortBoxByComparator(
            List<Box<C>> boxs, String comparatorName)
            throws InstantiationException, IllegalAccessException,
            ClassNotFoundException
    {
        Comparator comparator = (Comparator) Class.forName(comparatorName)
                .newInstance();
        Collections.sort(boxs, comparator);
        return boxs;

    }

    public static List<RPNestedObject> getResearcherNestedObject(
            Integer researcherID, Integer typoNestedId)
    {
        return applicationService.getNestedObjectsByParentIDAndTypoID(
                researcherID, typoNestedId, RPNestedObject.class);
    }

    public static List<RPNestedObject> getPaginateResearcherNestedObject(
            Integer researcherID, Integer typoNestedId, Integer limit,
            Integer offset)
    {
        return applicationService
                .getNestedObjectsByParentIDAndTypoIDLimitAt(researcherID,
                        typoNestedId, RPNestedObject.class, limit, offset);
    }

    public static List<RPNestedObject> getResearcherNestedObjectByShortname(
            Integer researcherID, String typoNested)
    {
        return applicationService.getNestedObjectsByParentIDAndShortname(
                researcherID, typoNested, RPNestedObject.class);
    }

}
