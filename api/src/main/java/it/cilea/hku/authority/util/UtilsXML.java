/**
 * <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
 * <html><head>
 * <title>301 Moved Permanently</title>
 * </head><body>
 * <h1>Moved Permanently</h1>
 * <p>The document has moved <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>.</p>
 * </body></html>
 */
package it.cilea.hku.authority.util;

import it.cilea.hku.authority.model.IExportableDynamicObject;
import it.cilea.hku.authority.model.IRestrictedField;
import it.cilea.hku.authority.model.Investigator;
import it.cilea.hku.authority.model.ResearcherPage;
import it.cilea.hku.authority.model.RestrictedField;
import it.cilea.hku.authority.model.RestrictedFieldFile;
import it.cilea.hku.authority.model.RestrictedFieldLocalOrRemoteFile;
import it.cilea.hku.authority.model.dynamicfield.DecoratorRPPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.DecoratorRestrictedField;
import it.cilea.hku.authority.service.ApplicationService;
import it.cilea.osd.jdyna.dto.AnagraficaObjectDTO;
import it.cilea.osd.jdyna.dto.ValoreDTO;
import it.cilea.osd.jdyna.model.AWidget;
import it.cilea.osd.jdyna.model.AnagraficaObject;
import it.cilea.osd.jdyna.model.PropertiesDefinition;
import it.cilea.osd.jdyna.model.Property;
import it.cilea.osd.jdyna.utils.ExportUtils;
import it.cilea.osd.jdyna.value.EmbeddedLinkValue;
import it.cilea.osd.jdyna.value.MultiValue;
import it.cilea.osd.jdyna.web.ADecoratorPropertiesDefinition;
import it.cilea.osd.jdyna.web.IContainable;
import it.cilea.osd.jdyna.widget.WidgetCombo;
import it.cilea.osd.jdyna.widget.WidgetDate;
import it.cilea.osd.jdyna.widget.WidgetLink;
import it.cilea.osd.jdyna.widget.WidgetTesto;

import java.beans.PropertyEditor;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.jdom.Document;
import org.jdom.Element;

/**
 * Utility class to create researchers export functionalities.
 * 
 * @author Pascarelli Andrea
 * 
 */
public class UtilsXML
{

    private Type type;

    private Pagination pagination;

    private boolean seeHiddenValue = false;

    public static final String ROOT_RESEARCHERS = "researchers";

    public static final String ELEMENT_RESEARCHER = "researcher";

    public static final String NAMEATTRIBUTE_SRC_LINK = "src";

    public static final String NAMEATTRIBUTE_VISIBILITY = "visibility";

    public static final String NAMEATTRIBUTE_RPID = "rpid";

    public static final String NAMEATTRIBUTE_STAFF_NO = "staffNo";

    public static final String NAMEATTRIBUTE_MIMETYPE = "mime";

    public static final String NAMEATTRIBUTE_REMOTEURL = "remotesrc";

    public static final String GRANT_NAMEATTRIBUTE_RGID = "rgid";

    public static final String GRANT_NAMEATTRIBUTE_RPID = "rpkey";

    public static final String GRANT_NAMEATTRIBUTE_CODE = "code";

    public static final String GRANT_ELEMENT_INVESTIGATOR = "investigator";

    public static final String GRANT_ELEMENT_COINVESTIGATORS = "coInvestigators";

    public static final String GRANT_ELEMENT_COINVESTIGATOR = "coInvestigator";

    public static final String GRANT_TAG_PROJECTS = "projects";

    public static final String GRANT_TAG_PROJECTSCODE = "projectcode";

    public static final String GRANT_TAG_INVESTIGATOR = "investigators";

    public static final String GRANT_TAG_COINVESTIGATOR = "coinvestigators";

    private Writer writer;

    private ApplicationService applicationService;

    private Document xmldoc;

    public Document getXmldoc()
    {
        return xmldoc;
    }

    public void setXmldoc(Document xmldoc)
    {
        this.xmldoc = xmldoc;
    }

    public UtilsXML(Writer writer, ApplicationService applicationService)
    {
        this.writer = writer;
        this.applicationService = applicationService;
    }

    public Writer getWriter()
    {
        return writer;
    }

    public void setWriter(Writer writer)
    {
        this.writer = writer;
    }

    public Document createRoot() throws IOException,
            ParserConfigurationException
    {
        return createRoot(null, null, null);
    }

    public Document createRoot(String rootName, String prefixNamespace,
            String namespace) throws IOException, ParserConfigurationException
    {
        if (rootName == null)
        {
            rootName = ROOT_RESEARCHERS;
        }
        Element root = null;
        if (namespace == null)
        {
            root = new Element(rootName);
        }
        else if(prefixNamespace==null)
        {
            root = new Element(rootName, prefixNamespace, namespace);
        }
        else {
            root = new Element(rootName, namespace);
        }

        if (this.pagination != null)
        {
            root.setAttribute("hit", "" + this.pagination.getHit());
            root.setAttribute("start", "" + this.pagination.getStart());
            root.setAttribute("rows", "" + this.pagination.getRows());
        }
        if (this.type != null)
        {
            root.setAttribute("type", this.getType().getType());
        }
        xmldoc = new Document();
        xmldoc.addContent(root);
        return xmldoc;
    }

    public <I extends IContainable> void writeRP(ResearcherPage rp,
            List<I> metadata, Element root) throws IOException,
            SecurityException, NoSuchFieldException, IllegalArgumentException,
            IllegalAccessException, InvocationTargetException,
            TransformerException
    {
        List<String> attributes = new LinkedList<String>();
        List<String> valuesAttributes = new LinkedList<String>();
        attributes.add(NAMEATTRIBUTE_STAFF_NO);
        valuesAttributes.add(rp.getStaffNo());
        attributes.add(NAMEATTRIBUTE_RPID);
        valuesAttributes.add(ResearcherPageUtils.getPersistentIdentifier(rp));
        Element element = ExportUtils.createCustomPropertyWithCustomAttributes(
                root, ELEMENT_RESEARCHER, attributes, valuesAttributes);

        for (I containable : metadata)
        {
            if (containable instanceof DecoratorRPPropertiesDefinition)
            {
                this.createElement(
                        (DecoratorRPPropertiesDefinition) containable, rp,
                        element);
            }
            if (containable instanceof DecoratorRestrictedField)
            {
                this.createElement((DecoratorRestrictedField) containable, rp,
                        element);
            }
        }

    }

    private <TP extends PropertiesDefinition, P extends Property<TP>, AO extends AnagraficaObject<P, TP>> void createElement(
            ADecoratorPropertiesDefinition<TP> decorator,
            IExportableDynamicObject<TP, P, AO> rp, Element element)
            throws IOException
    {
        createElement(decorator.getReal(), decorator.getRendering(), rp,
                element);
    }

    // private void createElement(DecoratorRPPropertiesDefinition decorator,
    // ResearcherPage rp, Element element) throws IOException
    // {
    // createElement(decorator.getReal(), decorator.getRendering(), rp,
    // element);
    // }

    private <TP extends PropertiesDefinition, P extends Property<TP>, AO extends AnagraficaObject<P, TP>> void createElement(
            DecoratorRestrictedField decorator,
            IExportableDynamicObject<TP, P, AO> researcher, Element element)
            throws IOException, SecurityException, NoSuchFieldException,
            IllegalArgumentException, IllegalAccessException,
            InvocationTargetException
    {
        String shortName = decorator.getShortName();
        Method[] methods = researcher.getClass().getMethods();
        Object field = null;
        Method method = null;
        Method setter = null;
        for (Method m : methods)
        {
            if (m.getName().toLowerCase()
                    .equals("get" + shortName.toLowerCase()))
            {
                field = m.invoke(researcher, null);
                method = m;
                break;
            }
        }
        if (method.getReturnType().isAssignableFrom(List.class))
        {
            Element coinvestigators = null;
            if(decorator.getShortName().equals("coInvestigators")) {
                coinvestigators = new Element("coInvestigators", element.getNamespacePrefix(), element.getNamespaceURI());
                element.addContent(coinvestigators);
            }
            
            for (IRestrictedField rr : (List<IRestrictedField>) field)
            {
                if(decorator.getShortName().equals("coInvestigators")) {
                    List<String> attributes = new LinkedList<String>();
                    List<String> valuesAttributes = new LinkedList<String>(); 
                    Investigator invest = (Investigator)rr;
                    if(invest.getIntInvestigator()!=null) {
                        attributes.add("rpkey");                    
                        valuesAttributes.add(invest.getIntInvestigator().getValuePublicIDAttribute());
                    }
                    ExportUtils.createCoinvestigator(coinvestigators, decorator.getReal(),
                            ((IRestrictedField) rr).getValue(), attributes, valuesAttributes);

                }
                else {
                    createSimpleElement(decorator.getReal(), (IRestrictedField) rr,
                        element);
                }

            }

        }
        else if (method.getReturnType().isAssignableFrom(String.class))
        {
            createSimpleElement(decorator.getReal(), (String) field, element);
        }
        else
        {
            if (RestrictedFieldLocalOrRemoteFile.class.isAssignableFrom(method
                    .getReturnType()))
            {
                createSimpleElement(decorator.getReal(),
                        (RestrictedFieldLocalOrRemoteFile) field, element);
            }
            else if (RestrictedFieldFile.class.isAssignableFrom(method
                    .getReturnType()))
            {
                createSimpleElement(decorator.getReal(),
                        (RestrictedFieldFile) field, element);
            }
            else
            {
                createSimpleElement(decorator.getReal(),
                        (IRestrictedField) field, element);
            }
        }

    }

    private void createSimpleElement(String real, String field, Element element)
            throws IOException
    {
        if (field != null && !field.isEmpty())
        {
            ExportUtils.createCustomValue(element, real, field);
        }
    }

    private void createSimpleElement(String real,
            IRestrictedField restrictedField, Element element)
            throws IOException
    {
        if (restrictedField.getValue() != null
                && !restrictedField.getValue().isEmpty()
                && (restrictedField.getVisibility() == 1 || (restrictedField
                        .getVisibility() == 0 && isSeeHiddenValue())))
        {
            List<String> attributes = new LinkedList<String>();
            List<String> valuesAttributes = new LinkedList<String>();
            attributes.add(NAMEATTRIBUTE_VISIBILITY);
            valuesAttributes.add(restrictedField.getVisibility().toString());
            if (restrictedField instanceof RestrictedFieldFile)
            {
                attributes.add(NAMEATTRIBUTE_MIMETYPE);
                valuesAttributes.add(((RestrictedFieldFile) restrictedField)
                        .getMimeType());
            }
            if (restrictedField instanceof RestrictedFieldLocalOrRemoteFile)
            {
                attributes.add(NAMEATTRIBUTE_REMOTEURL);
                valuesAttributes
                        .add(((RestrictedFieldLocalOrRemoteFile) restrictedField)
                                .getRemoteUrl());
            }

            ExportUtils.createCustomValueWithCustomAttributes(element, real,
                    restrictedField.getValue(), attributes, valuesAttributes);
        }
    }

    private void createSimpleElement(String real,
            RestrictedFieldFile restrictedField, Element element)
            throws IOException
    {
        if (restrictedField.getValue() != null
                && !restrictedField.getValue().isEmpty()
                && (restrictedField.getVisibility() == 1 || (restrictedField
                        .getVisibility() == 0 && isSeeHiddenValue())))
        {
            List<String> attributes = new LinkedList<String>();
            List<String> valuesAttributes = new LinkedList<String>();
            attributes.add(NAMEATTRIBUTE_VISIBILITY);
            valuesAttributes.add(restrictedField.getVisibility().toString());

            attributes.add(NAMEATTRIBUTE_MIMETYPE);
            valuesAttributes.add(((RestrictedFieldFile) restrictedField)
                    .getMimeType());

            ExportUtils.createCustomValueWithCustomAttributes(element, real,
                    restrictedField.getValue(), attributes, valuesAttributes);
        }
    }

    private void createSimpleElement(String real,
            RestrictedFieldLocalOrRemoteFile restrictedField, Element element)
            throws IOException
    {
        if (restrictedField.getValue() != null
                && !restrictedField.getValue().isEmpty()
                && (restrictedField.getVisibility() == 1 || (restrictedField
                        .getVisibility() == 0 && isSeeHiddenValue())))
        {
            List<String> attributes = new LinkedList<String>();
            List<String> valuesAttributes = new LinkedList<String>();
            attributes.add(NAMEATTRIBUTE_VISIBILITY);
            valuesAttributes.add(restrictedField.getVisibility().toString());

            attributes.add(NAMEATTRIBUTE_MIMETYPE);
            valuesAttributes.add(((RestrictedFieldFile) restrictedField)
                    .getMimeType());

            attributes.add(NAMEATTRIBUTE_REMOTEURL);
            valuesAttributes
                    .add(((RestrictedFieldLocalOrRemoteFile) restrictedField)
                            .getRemoteUrl());

            ExportUtils.createCustomValueWithCustomAttributes(element, real,
                    restrictedField.getValue(), attributes, valuesAttributes);
        }
    }

    private <A extends AWidget, TP extends PropertiesDefinition, P extends Property<TP>, AO extends AnagraficaObject<P, TP>> void createElement(
            TP tp, A rendering, IExportableDynamicObject<TP, P, AO> rp,
            Element element) throws IOException
    {

        if (rendering instanceof WidgetCombo)
        {
            createElement(tp, (WidgetCombo) rendering, rp, element);
        }
        else
        {
            createSimpleElement(tp.getShortName(), rp.getDynamicField()
                    .getProprietaDellaTipologia(tp), element);
        }
    }

    private <TP extends PropertiesDefinition, P extends Property<TP>, AO extends AnagraficaObject<P, TP>> void createElement(
            TP tp, WidgetCombo<P, TP> rendering,
            IExportableDynamicObject<TP, P, AO> rp, Element element)
            throws IOException
    {
        createComplexElement(tp, rendering.getSottoTipologie(), rp, element);
    }

    private <TP extends PropertiesDefinition, P extends Property<TP>> void createSimpleElement(
            String shortName, List<P> proprietaDellaTipologia, Element element)
            throws IOException
    {

        for (P prop : proprietaDellaTipologia)
        {
            PropertyEditor pe = prop.getTypo().getRendering()
                    .getPropertyEditor(applicationService);

            if (prop.getObject() != null
                    && (prop.getVisibility() == 1 || (prop.getVisibility() == 0 && isSeeHiddenValue())))
            {
                pe.setValue(prop.getObject());
                if (prop.getTypo().getRendering() instanceof WidgetTesto)
                {
                    List<String> attributes = new LinkedList<String>();
                    List<String> valuesAttributes = new LinkedList<String>();
                    attributes.add(NAMEATTRIBUTE_VISIBILITY);
                    valuesAttributes.add(prop.getVisibility().toString());
                    ExportUtils.createCustomValueWithCustomAttributes(element,
                            shortName, pe.getAsText(), attributes,
                            valuesAttributes);
                }
                if (prop.getTypo().getRendering() instanceof WidgetDate)
                {
                    List<String> attributes = new LinkedList<String>();
                    List<String> valuesAttributes = new LinkedList<String>();
                    attributes.add(NAMEATTRIBUTE_VISIBILITY);
                    valuesAttributes.add(prop.getVisibility().toString());
                    ExportUtils.createCustomValueWithCustomAttributes(element,
                            shortName, pe.getAsText(), attributes,
                            valuesAttributes);
                }
                if (prop.getTypo().getRendering() instanceof WidgetLink)
                {
                    EmbeddedLinkValue link = (EmbeddedLinkValue) pe.getValue();
                    List<String> attributes = new LinkedList<String>();
                    List<String> valuesAttributes = new LinkedList<String>();
                    attributes.add(NAMEATTRIBUTE_VISIBILITY);
                    valuesAttributes.add(prop.getVisibility().toString());
                    attributes.add(NAMEATTRIBUTE_SRC_LINK);
                    valuesAttributes.add(link.getValueLink());
                    ExportUtils.createCustomValueWithCustomAttributes(element,
                            shortName, link.getDescriptionLink(), attributes,
                            valuesAttributes);

                }

            }
        }

    }

    private <TP extends PropertiesDefinition, P extends Property<TP>, AO extends AnagraficaObject<P, TP>> void createComplexElement(
            TP combo, List<TP> elements,
            IExportableDynamicObject<TP, P, AO> rp, Element element)
            throws IOException
    {

        List<P> proprietaDellaTipologia = rp.getDynamicField()
                .getProprietaDellaTipologia(combo);

        for (P prop : proprietaDellaTipologia)
        {

            internalCreateComboElement(combo.getShortName(), element,
                    ((MultiValue) prop.getValue()).getObject());

        }

    }

    private void internalCreateComboElement(String combo,

    Element element, AnagraficaObjectDTO prop)
    {
        Element comboElement = ExportUtils.createTagXML(element, combo);
        for (String tp : prop.getAnagraficaProperties().keySet())
        {
            for (ValoreDTO val : prop.getAnagraficaProperties().get(tp))
            {
                createSimpleElement(tp, val, comboElement);
            }
        }
    }

    private void createSimpleElement(String shortName, ValoreDTO val,
            Element element)
    {

        if (val.getObject() instanceof AnagraficaObjectDTO)
        {
            internalCreateComboElement(shortName, element,
                    (AnagraficaObjectDTO) val.getObject());
        }
        else
        {
            if ((val.getVisibility() || (val.getVisibility() == false && isSeeHiddenValue())))
            {
                List<String> attributes = new LinkedList<String>();
                List<String> valuesAttributes = new LinkedList<String>();
                attributes.add(NAMEATTRIBUTE_VISIBILITY);
                valuesAttributes
                        .add("" + (val.getVisibility() == true ? 1 : 0));
                ExportUtils.createCustomValueWithCustomAttributes(element,
                        shortName, val.getObject().toString(), attributes,
                        valuesAttributes);
            }
        }
    }

    public <I extends IContainable, TP extends PropertiesDefinition, P extends Property<TP>, AO extends AnagraficaObject<P, TP>> void write(
            IExportableDynamicObject<TP, P, AO> rp, List<I> metadata,
            Element root) throws IOException, SecurityException,
            NoSuchFieldException, IllegalArgumentException,
            IllegalAccessException, InvocationTargetException,
            TransformerException
    {
        List<String> attributes = new LinkedList<String>();
        List<String> valuesAttributes = new LinkedList<String>();

        attributes.add(rp.getNamePublicIDAttribute());
        valuesAttributes.add(rp.getValuePublicIDAttribute());
        attributes.add(rp.getNameIDAttribute());
        valuesAttributes.add(rp.getValueIDAttribute());
        attributes.add(rp.getNameBusinessIDAttribute());
        valuesAttributes.add(rp.getValueBusinessIDAttribute());
        attributes.add(rp.getNameTypeIDAttribute());
        valuesAttributes.add(rp.getValueTypeIDAttribute());

        Element element = ExportUtils.createCustomPropertyWithCustomAttributes(
                root, rp.getNameSingleRowElement(), attributes,
                valuesAttributes);

        for (I containable : metadata)
        {
            if (containable instanceof ADecoratorPropertiesDefinition)
            {
                this.createElement(
                        (ADecoratorPropertiesDefinition<TP>) containable, rp,
                        element);
            }
            if (containable instanceof DecoratorRestrictedField)
            {
                this.createElement((DecoratorRestrictedField) containable, rp,
                        element);
            }
        }

    }

    public Pagination createPagination(long hit, long start, int rows)
    {
        this.pagination = new Pagination(hit, start, rows);
        return pagination;
    }

    public Type createType(String type)
    {
        this.setType(new Type(type));
        return this.type;
    }

    public void setPagination(Pagination pagination)
    {
        this.pagination = pagination;
    }

    public Pagination getPagination()
    {
        return pagination;
    }

    public void setSeeHiddenValue(boolean seeHiddenValue)
    {
        this.seeHiddenValue = seeHiddenValue;
    }

    public boolean isSeeHiddenValue()
    {
        return seeHiddenValue;
    }

    public void setType(Type type)
    {
        this.type = type;
    }

    public Type getType()
    {
        return type;
    }

    class Pagination
    {

        private long hit;

        private long start;

        private int rows;

        public Pagination(long hit2, long start2, int rows2)
        {
            this.hit = hit2;
            this.start = start2;
            this.rows = rows2;
        }

        public long getHit()
        {
            return hit;
        }

        public void setHit(long hit)
        {
            this.hit = hit;
        }

        public long getStart()
        {
            return start;
        }

        public void setStart(long start)
        {
            this.start = start;
        }

        public int getRows()
        {
            return rows;
        }

        public void setRows(int rows)
        {
            this.rows = rows;
        }

    }

    class Type
    {
        private String type;

        public Type(String t)
        {
            this.type = t;
        }

        public void setType(String type)
        {
            this.type = type;
        }

        public String getType()
        {
            return type;
        }

    }
}
