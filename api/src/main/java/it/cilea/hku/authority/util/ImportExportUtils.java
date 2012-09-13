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
package it.cilea.hku.authority.util;

import it.cilea.hku.authority.model.IExportableDynamicObject;
import it.cilea.hku.authority.model.Investigator;
import it.cilea.hku.authority.model.ResearcherGrant;
import it.cilea.hku.authority.model.ResearcherPage;
import it.cilea.hku.authority.model.RestrictedField;
import it.cilea.hku.authority.model.RestrictedFieldFile;
import it.cilea.hku.authority.model.RestrictedFieldLocalOrRemoteFile;
import it.cilea.hku.authority.model.VisibilityConstants;
import it.cilea.hku.authority.model.dynamicfield.DecoratorRPPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.DecoratorRestrictedField;
import it.cilea.hku.authority.model.dynamicfield.GrantPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.RPAdditionalFieldStorage;
import it.cilea.hku.authority.model.dynamicfield.RPPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.RPProperty;
import it.cilea.hku.authority.service.ApplicationService;
import it.cilea.osd.common.util.Utils;
import it.cilea.osd.common.utils.XMLUtils;
import it.cilea.osd.jdyna.dto.AnagraficaObjectDTO;
import it.cilea.osd.jdyna.dto.ValoreDTO;
import it.cilea.osd.jdyna.model.AnagraficaObject;
import it.cilea.osd.jdyna.model.IContainable;
import it.cilea.osd.jdyna.model.PropertiesDefinition;
import it.cilea.osd.jdyna.model.Property;
import it.cilea.osd.jdyna.util.AnagraficaUtils;
import it.cilea.osd.jdyna.value.EmbeddedLinkValue;
import it.cilea.osd.jdyna.value.MultiValue;
import it.cilea.osd.jdyna.widget.WidgetCombo;
import it.cilea.osd.jdyna.widget.WidgetDate;
import it.cilea.osd.jdyna.widget.WidgetLink;
import it.cilea.osd.jdyna.widget.WidgetTesto;

import java.beans.PropertyEditor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPathExpressionException;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dspace.authorize.AuthorizeException;
import org.dspace.content.DCPersonName;
import org.dspace.core.ConfigurationManager;
import org.dspace.core.Context;
import org.dspace.eperson.EPerson;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Static class that provides export functionalities from the RPs database to
 * Excel. It defines also some constants useful for the import functionalities.
 * 
 * @author cilea
 * 
 */
public class ImportExportUtils
{

    private static final DateFormat dateFormat = new SimpleDateFormat(
            "dd-MM-yyyy_HH-mm-ss");

    /** log4j logger */
    private static Logger log = Logger.getLogger(ImportExportUtils.class);

    private static final String XPATH_ATTRIBUTE_SRC = "@"
            + UtilsXML.NAMEATTRIBUTE_SRC_LINK;

    private static final String XPATH_ATTRIBUTE_VIS = "@"
            + UtilsXML.NAMEATTRIBUTE_VISIBILITY;

    private static final String XPATH_ATTRIBUTE_RPID = "@"
            + UtilsXML.NAMEATTRIBUTE_RPID;

    private static final String XPATH_ATTRIBUTE_STAFFNO = "@"
            + UtilsXML.NAMEATTRIBUTE_STAFF_NO;

    private static final String XPATH_ELEMENT_ROOT = UtilsXML.ROOT_RESEARCHERS;

    private static final String XPATH_ELEMENT_RESEARCHER = UtilsXML.ELEMENT_RESEARCHER;

    private static final String XPATH_ATTRIBUTE_REMOTESRC = "@"
            + UtilsXML.NAMEATTRIBUTE_REMOTEURL;

    private static final String XPATH_ATTRIBUTE_MIME = "@"
            + UtilsXML.NAMEATTRIBUTE_MIMETYPE;

    public static final String[] XPATH_RULES = {
            "/" + XPATH_ELEMENT_ROOT + "/" + XPATH_ELEMENT_RESEARCHER,
            XPATH_ATTRIBUTE_STAFFNO, XPATH_ATTRIBUTE_VIS, XPATH_ATTRIBUTE_RPID,
            XPATH_ATTRIBUTE_SRC, XPATH_ATTRIBUTE_REMOTESRC,
            XPATH_ATTRIBUTE_MIME };

    /**
     * Defaul visibility, it is used when no visibility attribute and old value
     * founded
     */
    public static final String DEFAULT_VISIBILITY = "1";

    public static final String LABELCAPTION_VISIBILITY_SUFFIX = " visibility";

    public static final String IMAGE_SUBFOLDER = "image";

    public static final String CV_SUBFOLDER = "cv";

    /**
     * Default absolute path where find the contact data excel file to import
     */
    public static final String PATH_DEFAULT_XML = ConfigurationManager
            .getProperty("dspace.dir")
            + File.separatorChar
            + "rp-import/rpdata.xml";

    /**
     * Default absolute path where find the contact data excel file to import
     */
    public static final String GRANT_PATH_DEFAULT_XML = ConfigurationManager
            .getProperty("dspace.dir")
            + File.separatorChar
            + "rg-import/rpdata.xml";

    /**
     * Write in the output stream the researcher pages contact data as an excel
     * file. The format of the exported Excel file is suitable for re-import in
     * the system.
     * 
     * @param rps
     *            the researcher pages list to export
     * @param applicationService
     *            the applicationService
     * @param os
     *            the output stream, it will close directly when the method exit
     * @throws IOException
     * @throws WriteException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public static void exportData(List<ResearcherPage> rps,
            ApplicationService applicationService, OutputStream os,
            List<IContainable> metadata) throws IOException, WriteException,
            IllegalArgumentException, IllegalAccessException,
            InvocationTargetException
    {

        WritableWorkbook workbook = Workbook.createWorkbook(os);
        WritableSheet sheet = workbook.createSheet("Sheet", 0);

        // create initial caption (other caption could be write field together)
        int x = 0;
        sheet.addCell(new Label(x++, 0, "staffNo"));
        sheet.addCell(new Label(x++, 0, "rp"));
        sheet.addCell(new Label(x++, 0, "rp url"));

        // row index
        int i = 1;
        for (ResearcherPage rp : rps)
        {
            int y = 0;
            sheet.addCell(new Label(0, i, ""));
            Label label = (Label) sheet.getCell(0, i);
            label.setString(rp.getStaffNo());
            y++;
            sheet.addCell(new Label(1, i, ""));
            label = (Label) sheet.getCell(1, i);
            label.setString(ResearcherPageUtils.getPersistentIdentifier(rp));
            y++;
            sheet.addCell(new Label(2, i, ""));
            label = (Label) sheet.getCell(2, i);
            label.setString(ConfigurationManager.getProperty("dspace.url")
                    + "/rp/" + ResearcherPageUtils.getPersistentIdentifier(rp));

            for (IContainable containable : metadata)
            {
                if (containable instanceof DecoratorRPPropertiesDefinition)
                {
                    y = UtilsXLS.createCell(applicationService, y, i,
                            (DecoratorRPPropertiesDefinition) containable, rp,
                            sheet);
                }
                if (containable instanceof DecoratorRestrictedField)
                {
                    y = UtilsXLS.createCell(applicationService, y, i,
                            (DecoratorRestrictedField) containable, rp, sheet);
                }
            }

            i++;
        }
        // All sheets and cells added. Now write out the workbook
        workbook.write();
        workbook.close();
    }

    /**
     * 
     * Import xml files, matching validation with xsd builded at runtime
     * execution associate to list of dynamic fields and structural fields
     * 
     * @param input
     *            - XML file stream
     * @param dir
     *            - directory from read image/cv and write temporaries xsd and
     *            xml (this xsd validate actual xml)
     * @param applicationService
     *            - service
     * @param appendMode
     *            TODO
     * @throws Exception
     */
    public static void importResearchersXML(InputStream input, File dir,
            ApplicationService applicationService, Context dspaceContext,
            boolean status) throws Exception
    {

        File filexsd = null;
        File filexml = null;
        List<ResearcherPage> toRenameCV = new LinkedList<ResearcherPage>();
        List<ResearcherPage> toRenameIMG = new LinkedList<ResearcherPage>();
        // build filexml
        String nameXML = "xml-" + dateFormat.format(new Date()) + ".xml";
        filexml = new File(dir, nameXML);
        filexml.createNewFile();
        FileOutputStream out = new FileOutputStream(filexml);
        Utils.bufferedCopy(input, out);
        out.close();

        List<IContainable> metadataALL = applicationService
                .findAllContainables(RPPropertiesDefinition.class);

        // create xsd and write up
        String nameXSD = "xsd-" + dateFormat.format(new Date()) + ".xsd";
        filexsd = new File(dir, nameXSD);
        filexsd.createNewFile();
        FileWriter writer = new FileWriter(filexsd);
        filexsd = generateXSD(writer, dir, metadataALL, filexsd, null);

        // create xsd validator
        SchemaFactory factory = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Source schemaSource = new StreamSource(filexsd);

        Schema schema = factory.newSchema(schemaSource);
        // validate source xml to xsd
        Validator validator = schema.newValidator();
        validator.validate(new StreamSource(filexml));

        // parse xml to dom
        DocumentBuilder parser = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder();
        Document document = parser.parse(filexml);

        // get from list of metadata dynamic field vs structural field
        List<RPPropertiesDefinition> realTPS = new LinkedList<RPPropertiesDefinition>();
        List<IContainable> structuralField = new LinkedList<IContainable>();
        for (IContainable c : metadataALL)
        {
            RPPropertiesDefinition rpPd = applicationService
                    .findPropertiesDefinitionByShortName(
                            RPPropertiesDefinition.class, c.getShortName());
            if (rpPd != null)
            {
                realTPS.add(((DecoratorRPPropertiesDefinition) applicationService
                        .findContainableByDecorable(
                                RPPropertiesDefinition.class.newInstance()
                                        .getDecoratorClass(), c.getId()))
                        .getReal());
            }
            else
            {
                structuralField.add(c);
            }
        }

        List<RPPropertiesDefinition> realFillTPS = new LinkedList<RPPropertiesDefinition>();
        for (RPPropertiesDefinition r : realTPS)
        {
            NodeList e = document.getElementsByTagName(r.getShortName());
            if (e != null && e.getLength() > 0)
            {
                realFillTPS.add(r);
            }
        }

        List<IContainable> structuralFillField = new LinkedList<IContainable>();
        for (IContainable r : structuralField)
        {
            NodeList e = document.getElementsByTagName(r.getShortName());
            if (e != null && e.getLength() > 0)
            {
                structuralFillField.add(r);
            }
        }

        // import xml
        // XPath xpath = XPathFactory.newInstance().newXPath();
        // String xpathExpression = XPATH_RULES[0];
        // NodeList researchers = (NodeList) xpath.evaluate(xpathExpression,
        // document, XPathConstants.NODESET);

        List<Element> researchers = XMLUtils.getElementList(
                document.getDocumentElement(), "researcher");
        int rows_discarded = 0;
        int rows_imported = 0;
        log.info("Start import " + new Date());
        // foreach researcher element in xml
        for (int i = 0; i < researchers.size(); i++)
        {
            log.info("Number " + i + " of " + researchers.size());
            ResearcherPage researcher = null;
            try
            {
                Element node = researchers.get(i);

                // check if staffNo and rpid exists as attribute
                // String nodeId = (String) xpath.evaluate(XPATH_RULES[1], node,
                // XPathConstants.STRING);
                // String rpId = (String) xpath.evaluate(XPATH_RULES[3], node,
                // XPathConstants.STRING);
                String nodeId = node
                        .getAttribute(UtilsXML.NAMEATTRIBUTE_STAFF_NO);
                String rpId = node.getAttribute(UtilsXML.NAMEATTRIBUTE_RPID);
                ResearcherPage clone = null;
                // use dto to fill dynamic metadata
                AnagraficaObjectDTO dto = new AnagraficaObjectDTO();
                AnagraficaObjectDTO clonedto = new AnagraficaObjectDTO();
                boolean update = false; // if update a true then set field to
                                        // null
                                        // on case of empty element
                if (nodeId == null || nodeId.isEmpty())
                {
                    log.error("Researcher discarded ( staffNo not founded) [position researcher: "
                            + i + "]");
                    throw new RuntimeException(
                            "Researcher discarded (staffNo not founded whilst rpId is on xml) [position researcher: "
                                    + i + "]");

                }
                else
                {
                    // if there is rpid then try to get researcher by staffNo
                    // and
                    // set to null all structural metadata lists
                    log.info("Researcher staffNo : " + nodeId
                            + " / rp identifier : " + rpId);
                    if (rpId != null && !rpId.isEmpty())
                    {
                        researcher = applicationService
                                .getResearcherPageByStaffNo(nodeId);
                        if (researcher == null)
                        {
                            log.error("Researcher discarded (staffNo not founded whilst rpId is on xml) [position researcher: "
                                    + i + "]");
                            ;
                            throw new RuntimeException(
                                    "Researcher discarded (staffNo not founded whilst rpId is on xml) [position researcher: "
                                            + i + "]");
                        }
                        else
                        {
                            if (!rpId.equals(ResearcherPageUtils
                                    .getPersistentIdentifier(researcher)))
                            {
                                log.error("Researcher discarded (rpId don't match persistent identifier) [position researcher: "
                                        + i + "]");
                                throw new RuntimeException(
                                        "Researcher discarded (staffNo not founded whilst rpId is on xml) [position researcher: "
                                                + i + "]");
                            }
                        }
                        // clone dynamic data and structural on dto

                        clone = (ResearcherPage) researcher.clone();
                        RPAdditionalFieldStorage additionalTemp = new RPAdditionalFieldStorage();
                        clone.setDynamicField(additionalTemp);
                        additionalTemp.duplicaAnagrafica(researcher
                                .getDynamicField());
                        update = true;

                    }
                    else
                    {
                        // here there is perhaps a new researcher
                        researcher = applicationService
                                .getResearcherPageByStaffNo(nodeId);
                        if (researcher == null)
                        {
                            researcher = new ResearcherPage();
                            researcher.setStaffNo(nodeId);
                            // added by Allen: all newly added researchers are
                            // inactive by default
                            // use -active in command line to change default
                            // status to active.
                            researcher.setStatus(status);

                            clone = (ResearcherPage) researcher.clone();
                            RPAdditionalFieldStorage additionalTemp = new RPAdditionalFieldStorage();
                            clone.setDynamicField(additionalTemp);
                            additionalTemp.duplicaAnagrafica(researcher
                                    .getDynamicField());
                        }
                        else
                        {
                            log.error("Researcher discarded (staffNo " + nodeId
                                    + " already exist) [position researcher: "
                                    + i + "]");
                            throw new RuntimeException(
                                    "Researcher discarded (staffNo "
                                            + nodeId
                                            + " already exist) [position researcher: "
                                            + i + "]");
                        }
                    }
                }

                AnagraficaUtils.fillDTO(dto, researcher.getDynamicField(),
                        realFillTPS);

                // one-shot fill and reverse to well-format clonedto and clean
                // empty
                // data
                AnagraficaUtils.fillDTO(clonedto, clone.getDynamicField(),
                        realFillTPS);
                AnagraficaUtils.reverseDTO(clonedto, clone.getDynamicField(),
                        realFillTPS);
                AnagraficaUtils.fillDTO(clonedto, clone.getDynamicField(),
                        realFillTPS);
                importDynAXML(applicationService, realFillTPS, node, dto,
                        clonedto, update);

                for (IContainable containable : structuralFillField)
                {
                    String shortName = containable.getShortName();
                    // xpathExpression = containable.getShortName();
                    if (containable instanceof DecoratorRestrictedField)
                    {
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
                                String nameSetter = m.getName().replaceFirst(
                                        "g", "s");
                                setter = researcher.getClass().getMethod(
                                        nameSetter, method.getReturnType());
                                break;
                            }
                        }
                        if (method.getReturnType().isAssignableFrom(List.class))
                        {

                            // NodeList nodeslist = (NodeList) xpath.evaluate(
                            // xpathExpression, node,
                            // XPathConstants.NODESET);
                            List<Element> nodeslist = XMLUtils.getElementList(
                                    node, shortName);

                            List<RestrictedField> object = (List<RestrictedField>) field;
                            List<RestrictedField> objectclone = new LinkedList<RestrictedField>();
                            objectclone.addAll(object);

                            for (int y = 0; y < nodeslist.size(); y++)
                            {
                                if (update == true && y == 0)
                                {
                                    object.clear();
                                }
                                Element nsublist = nodeslist.get(y);
                                String value = nsublist.getTextContent();

                                // String visibilityString = xpath.evaluate(
                                // XPATH_RULES[2], nsublist);
                                String visibilityString = nsublist
                                        .getAttribute(UtilsXML.NAMEATTRIBUTE_VISIBILITY);

                                if (value != null && !value.isEmpty())
                                {

                                    RestrictedField itemInCollection = new RestrictedField();

                                    Integer visibility = null;
                                    if (visibilityString != null
                                            && !visibilityString.isEmpty())
                                    {
                                        visibility = Integer
                                                .parseInt(visibilityString);
                                    }
                                    else if (update == false)
                                    {
                                        visibility = VisibilityConstants.PUBLIC;

                                    }
                                    else
                                    {
                                        visibility = checkOldVisibility(
                                                applicationService, value,
                                                objectclone, visibility);

                                    }

                                    // RestrictedField old = checkOldValue(
                                    // applicationService, value, object,
                                    // visibility);
                                    // if (old == null) {
                                    itemInCollection.setValue(value);
                                    if (visibility != null)
                                    {
                                        itemInCollection
                                                .setVisibility(visibility);
                                    }
                                    object.add(itemInCollection);
                                    setter.invoke(researcher, object);
                                    // }
                                }
                                // else {
                                // if (update == true
                                // && nodeslist.getLength() == 1) {
                                // setter.invoke(researcher,
                                // (List<RestrictedField>) null);
                                // }
                                // }

                            }

                        }
                        else
                        {
                            // Object control_value = xpath.evaluate(
                            // xpathExpression, node, XPathConstants.NODE);
                            Element control_value = XMLUtils.getSingleElement(
                                    node, shortName);
                            if (control_value != null)
                            {
                                // String value =
                                // xpath.evaluate(xpathExpression,
                                // node);
                                // String visibilityString = xpath.evaluate(
                                // xpathExpression + "/" + XPATH_RULES[2],
                                // node);

                                String value = XMLUtils.getElementValue(node,
                                        shortName);
                                String visibilityString = control_value
                                        .getAttribute(UtilsXML.NAMEATTRIBUTE_VISIBILITY);

                                if (!value.isEmpty())
                                {

                                    if (method.getReturnType().equals(
                                            String.class))
                                    {

                                        setter.invoke(researcher, value);
                                    }
                                    else
                                    {

                                        Integer visibility = null;
                                        if (visibilityString != null
                                                && !visibilityString.isEmpty())
                                        {
                                            visibility = Integer
                                                    .parseInt(visibilityString);
                                        }
                                        else if (!update)
                                        {
                                            visibility = VisibilityConstants.PUBLIC;

                                        }
                                        else
                                        {
                                            visibility = checkOldVisibility(
                                                    applicationService, value,
                                                    (RestrictedField) field,
                                                    visibility);

                                        }
                                        if (RestrictedFieldLocalOrRemoteFile.class
                                                .equals(method.getReturnType()))
                                        {

                                            RestrictedFieldLocalOrRemoteFile object = (RestrictedFieldLocalOrRemoteFile) field;
                                            object.setValue(value);
                                            if (visibility != null)
                                            {
                                                object.setVisibility(visibility);
                                            }
                                            // String remoteUrl =
                                            // xpath.evaluate(
                                            // xpathExpression + "/"
                                            // + XPATH_RULES[5],
                                            // node);
                                            String remoteUrl = control_value
                                                    .getAttribute(UtilsXML.NAMEATTRIBUTE_REMOTEURL);
                                            if (StringUtils
                                                    .isNotEmpty(remoteUrl))
                                            {
                                                if (update)
                                                {
                                                    ResearcherPageUtils
                                                            .removeCVFiles(researcher);
                                                }
                                                object.setRemoteUrl(remoteUrl);
                                            }
                                            else
                                            {
                                                if (update)
                                                {
                                                    String rp = ResearcherPageUtils
                                                            .getPersistentIdentifier(researcher);

                                                    File file = new File(dir
                                                            + "/"
                                                            + CV_SUBFOLDER
                                                            + "/" + rp + "."
                                                            + value);
                                                    if (file.exists())
                                                    {
                                                        // FIXME should be add
                                                        // mime
                                                        // type
                                                        // to signature of
                                                        // method to
                                                        // check correctness
                                                        ResearcherPageUtils
                                                                .loadCv(researcher,
                                                                        rp,
                                                                        file);
                                                    }
                                                }
                                                else
                                                {
                                                    toRenameCV.add(researcher);

                                                }
                                            }

                                            setter.invoke(researcher, object);

                                        }
                                        else if (RestrictedFieldFile.class
                                                .equals(method.getReturnType()))
                                        {

                                            RestrictedFieldFile object = (RestrictedFieldFile) field;
                                            object.setValue(value);
                                            if (visibility != null)
                                            {
                                                object.setVisibility(visibility);
                                            }
                                            if (update)
                                            {
                                                String rp = ResearcherPageUtils
                                                        .getPersistentIdentifier(researcher);

                                                File file = new File(dir + "/"
                                                        + IMAGE_SUBFOLDER + "/"
                                                        + rp + "." + value);
                                                if (file.exists())
                                                {
                                                    // FIXME should be add mime
                                                    // type
                                                    // to signature of method to
                                                    // check correctness
                                                    ResearcherPageUtils
                                                            .loadImg(
                                                                    researcher,
                                                                    rp, file);
                                                }
                                            }
                                            else
                                            {

                                                toRenameIMG.add(researcher);

                                            }
                                            setter.invoke(researcher, object);

                                        }
                                        else if (RestrictedField.class
                                                .equals(method.getReturnType()))
                                        {

                                            RestrictedField object = (RestrictedField) field;
                                            object.setValue(value);
                                            if (visibility != null)
                                            {
                                                object.setVisibility(visibility);
                                            }
                                            setter.invoke(researcher, object);
                                        }
                                    }

                                }
                                else
                                {
                                    if (update)
                                    {

                                        if (RestrictedField.class.equals(method
                                                .getReturnType()))
                                        {
                                            setter.invoke(researcher,
                                                    (RestrictedField) null);
                                        }
                                        else if (RestrictedFieldFile.class
                                                .equals(method.getReturnType()))
                                        {

                                            ResearcherPageUtils
                                                    .removePicture(researcher);
                                            setter.invoke(researcher,
                                                    (RestrictedFieldFile) null);

                                        }
                                        else if (RestrictedFieldLocalOrRemoteFile.class
                                                .equals(method.getReturnType()))
                                        {
                                            ResearcherPageUtils
                                                    .removeCVFiles(researcher);
                                            setter.invoke(
                                                    researcher,
                                                    (RestrictedFieldLocalOrRemoteFile) null);

                                        }

                                    }
                                }
                            }

                        }
                    }

                }

                AnagraficaUtils.reverseDTO(dto, researcher.getDynamicField(),
                        realFillTPS);

                EPerson dspaceUser = EPerson.findByNetid(dspaceContext,
                        researcher.getStaffNo());
                if (dspaceUser == null)
                {
                    // no dspace user we need to create it
                    try
                    {
                        EPerson emailUser = EPerson.findByEmail(dspaceContext,
                                researcher.getEmail().getValue());
                        if (emailUser != null)
                        {
                            throw new RuntimeException(
                                    "XML Row discarded STAFFNO : "
                                            + researcher.getStaffNo()
                                            + " Find an eperson with email/netId '"
                                            + emailUser.getEmail()
                                            + "/"
                                            + emailUser.getNetid()
                                            + "' that not referred to the staffNo '"
                                            + researcher.getStaffNo()
                                            + "' of researcher. Perhaps is it the same person?");
                        }
                        else
                        {
                            dspaceUser = EPerson.create(dspaceContext);
                            DCPersonName personalName = new DCPersonName(
                                    researcher.getFullName());
                            dspaceUser.setNetid(researcher.getStaffNo());
                            dspaceUser.setFirstName(personalName
                                    .getFirstNames());
                            dspaceUser.setLastName(personalName.getLastName());
                            dspaceUser.setEmail(researcher.getEmail()
                                    .getValue());
                            dspaceUser.setLanguage("en");
                            dspaceUser.setCanLogIn(true);
                            dspaceUser.update();
                        }
                    }
                    catch (SQLException e)
                    {
                        throw new RuntimeException(
                                "XML Row discarded STAFFNO : "
                                        + researcher.getStaffNo()
                                        + " Creation failure new eperson or researcher's mail has not been setted");
                    }
                    catch (AuthorizeException e)
                    {
                        throw new RuntimeException(
                                "XML Row discarded STAFFNO : "
                                        + researcher.getStaffNo()
                                        + " Authorize failure");
                    }
                    dspaceContext.commit();
                }

                applicationService.saveOrUpdate(ResearcherPage.class,
                        researcher);

                log.info("Import researcher " + researcher.getStaffNo()
                        + " (staffNo) / " + researcher.getId()
                        + " (id) - SUCCESS");
                rows_imported++;
            }
            catch (RuntimeException e)
            {
                log.error("Import researcher - FAILED " + e.getMessage(), e);
                rows_discarded++;
                toRenameCV.remove(researcher);
                toRenameIMG.remove(researcher);
            }

        }

        log.info("Import researchers - start import image and cv for new added researcher");
        for (ResearcherPage rrr : toRenameCV)
        {
            File file = new File(dir + "/" + CV_SUBFOLDER + "/"
                    + rrr.getStaffNo() + "." + rrr.getCv().getValue());
            if (file.exists())
            {
                log.debug("CV --- File " + file.getAbsolutePath()
                        + " exist - try to load");
                ResearcherPageUtils.loadCv(rrr,
                        ResearcherPageUtils.getPersistentIdentifier(rrr), file);
            }
            else
            {
                log.warn("CV --- File " + file.getAbsolutePath()
                        + " not founded - check if this file really exist?");
            }
            applicationService.saveOrUpdate(ResearcherPage.class, rrr);
        }

        for (ResearcherPage rrr : toRenameIMG)
        {
            File file = new File(dir + "/" + IMAGE_SUBFOLDER + "/"
                    + rrr.getStaffNo() + "." + rrr.getPict().getValue());
            if (file.exists())
            {
                log.info("IMG --- File " + file.getAbsolutePath()
                        + " exist - try to load");
                ResearcherPageUtils.loadImg(rrr,
                        ResearcherPageUtils.getPersistentIdentifier(rrr), file);
            }
            else
            {
                log.info("IMG --- File " + file.getAbsolutePath()
                        + " not founded - check if this file really exist?");
            }
            applicationService.saveOrUpdate(ResearcherPage.class, rrr);
        }
        log.info("Import researchers - end import additional files");

        log.info("Statistics: row ingested " + rows_imported + " on total of "
                + (researchers.size()) + " (" + rows_discarded
                + " row discarded)");
    }

    private static <P extends Property<TP>, TP extends PropertiesDefinition> void importDynAXML(
            ApplicationService applicationService, List<TP> realFillTPS,
            Element node, AnagraficaObjectDTO dto,
            AnagraficaObjectDTO clonedto, boolean update)
            throws XPathExpressionException
    {
        // foreach dynamic field read xml and fill on dto
        for (TP rpPD : realFillTPS)
        {

            // xpathExpression = rpPD.getShortName();
            String shortName = rpPD.getShortName();
            List<ValoreDTO> values = dto.getAnagraficaProperties().get(
                    shortName);
            List<ValoreDTO> oldValues = clonedto.getAnagraficaProperties().get(
                    shortName);
            if (rpPD.getRendering() instanceof WidgetTesto)
            {
                if (rpPD.isRepeatable())
                {

                    // NodeList nodeslist = (NodeList) xpath.evaluate(
                    // xpathExpression, node,
                    // XPathConstants.NODESET);

                    List<Element> nodeslist = XMLUtils.getElementList(node,
                            shortName);

                    for (int y = 0; y < nodeslist.size(); y++)
                    {
                        if (update == true && y == 0)
                        {
                            dto.getAnagraficaProperties().get(shortName)
                                    .clear();
                        }
                        Element nodetext = nodeslist.get(y);
                        String control_value = nodetext.getTextContent();
                        if (control_value != null && !control_value.isEmpty())
                        {
                            workOnText(applicationService, nodetext, rpPD,
                                    values, oldValues);
                        }
                        // else {
                        // if (update == true
                        // && nodeslist.getLength() == 1) {
                        // dto.getAnagraficaProperties()
                        // .get(shortName).clear();
                        // }
                        // }
                    }
                }
                else
                {
                    // Node nodeText = (Node) xpath.evaluate(
                    // xpathExpression, node, XPathConstants.NODE);
                    Element nodeText = XMLUtils.getSingleElement(node,
                            shortName);
                    String control_value = null;
                    try
                    {
                        control_value = nodeText.getTextContent();
                    }
                    catch (NullPointerException exc)
                    {
                        // nothing
                    }
                    if (control_value != null)
                    {
                        if (update == true)
                        {
                            dto.getAnagraficaProperties().get(shortName)
                                    .clear();
                        }
                        workOnText(applicationService, nodeText, rpPD, values,
                                oldValues);
                    }
                }
            }
            if (rpPD.getRendering() instanceof WidgetCombo)
            {
                if (rpPD.isRepeatable())
                {
                    // NodeList nodeslist = (NodeList) xpath.evaluate(
                    // xpathExpression, node,
                    // XPathConstants.NODESET);
                    List<Element> nodeslist = XMLUtils.getElementList(node,
                            shortName);

                    for (int y = 0; y < nodeslist.size(); y++)
                    {
                        if (update == true && y == 0)
                        {
                            dto.getAnagraficaProperties().get(shortName)
                                    .clear();
                        }
                        Element nodecombo = nodeslist.get(y);
                        String control_value = nodecombo.getTextContent();
                        if (control_value != null && !control_value.isEmpty())
                        {
                            workOnCombo(applicationService, nodecombo, rpPD,
                                    values, oldValues);
                        }
                        // else {
                        // if (update == true
                        // && nodeslist.getLength() == 1) {
                        // dto.getAnagraficaProperties()
                        // .get(shortName).clear();
                        // }
                        // }

                    }
                }
                else
                {
                    // Node nodecombo = (Node) xpath.evaluate(
                    // xpathExpression, node, XPathConstants.NODE);
                    Element nodecombo = XMLUtils.getSingleElement(node,
                            shortName);
                    String control_value = null;
                    try
                    {
                        control_value = nodecombo.getTextContent();
                    }
                    catch (NullPointerException exc)
                    {
                        // nothing
                    }
                    if (control_value != null)
                    {
                        if (update == true)
                        {
                            dto.getAnagraficaProperties().get(shortName)
                                    .clear();
                        }
                        workOnCombo(applicationService, nodecombo, rpPD,
                                values, oldValues);

                    }
                }
            }
            if (rpPD.getRendering() instanceof WidgetDate)
            {
                if (rpPD.isRepeatable())
                {
                    // NodeList nodeslist = (NodeList) xpath.evaluate(
                    // xpathExpression, node,
                    // XPathConstants.NODESET);
                    List<Element> nodeslist = XMLUtils.getElementList(node,
                            shortName);

                    for (int y = 0; y < nodeslist.size(); y++)
                    {
                        if (update == true && y == 0)
                        {
                            dto.getAnagraficaProperties().get(shortName)
                                    .clear();
                        }
                        Node nodeDate = nodeslist.get(y);
                        String control_value = nodeDate.getTextContent();
                        if (control_value != null && !control_value.isEmpty())
                        {
                            workOnDate(applicationService, node, rpPD, values,
                                    oldValues, nodeDate);
                        }
                        // else {
                        // if (update == true
                        // && nodeslist.getLength() == 1) {
                        // dto.getAnagraficaProperties()
                        // .get(shortName).clear();
                        // }
                        // }
                    }
                }
                else
                {
                    // Node nodeDate = (Node) xpath.evaluate(
                    // xpathExpression, node, XPathConstants.NODE);
                    Element nodeDate = XMLUtils.getSingleElement(node,
                            shortName);
                    String control_value = null;
                    try
                    {
                        control_value = nodeDate.getTextContent();
                    }
                    catch (NullPointerException exc)
                    {
                        // nothing
                    }
                    if (control_value != null)
                    {
                        if (update == true)
                        {
                            dto.getAnagraficaProperties().get(shortName)
                                    .clear();
                        }
                        workOnDate(applicationService, node, rpPD, values,
                                oldValues, nodeDate);
                    }
                }
            }
            if (rpPD.getRendering() instanceof WidgetLink)
            {

                if (rpPD.isRepeatable())
                {
                    // NodeList nodeslist = (NodeList) xpath.evaluate(
                    // xpathExpression, node,
                    // XPathConstants.NODESET);
                    List<Element> nodeslist = XMLUtils.getElementList(node,
                            shortName);

                    for (int y = 0; y < nodeslist.size(); y++)
                    {
                        if (update == true && y == 0)
                        {
                            dto.getAnagraficaProperties().get(shortName)
                                    .clear();
                        }
                        Element nodeLink = nodeslist.get(y);
                        String control_value = nodeLink.getTextContent();
                        if (control_value != null && !control_value.isEmpty())
                        {
                            workOnLink(applicationService, rpPD, values,
                                    oldValues, nodeLink);
                        }
                        // else {
                        // if (update == true
                        // && nodeslist.getLength() == 1) {
                        // dto.getAnagraficaProperties()
                        // .get(shortName).clear();
                        // }
                        // }
                    }
                }
                else
                {
                    // Node nodeLink = (Node) xpath.evaluate(
                    // xpathExpression, node, XPathConstants.NODE);
                    Element nodeLink = XMLUtils.getSingleElement(node,
                            shortName);
                    String control_value = null;
                    try
                    {
                        control_value = nodeLink.getTextContent();
                    }
                    catch (NullPointerException exc)
                    {
                        // nothing
                    }
                    if (control_value != null)
                    {
                        if (update == true)
                        {
                            dto.getAnagraficaProperties().get(shortName)
                                    .clear();
                        }
                        workOnLink(applicationService, rpPD, values, oldValues,
                                nodeLink);
                    }
                }
            }
        }
    }

    @Deprecated
    public static File generateGrantXSD(Writer writer, File dir,
            List<IContainable> metadata, File filexsd, String[] elementsRoot)
            throws IOException, NoSuchFieldException, SecurityException,
            InstantiationException, IllegalAccessException
    {

        UtilsXSD xsd = new UtilsXSD(writer);
        xsd.createGrantXSD(metadata, elementsRoot, null, null);
        return filexsd;
    }

    public static File newGenerateGrantXSD(Writer writer, File dir,
            List<IContainable> metadata, File filexsd, String[] elementsRoot, String[] attributeMainRow, boolean[] attributeMainRowRequired)
            throws IOException, NoSuchFieldException, SecurityException,
            InstantiationException, IllegalAccessException
    {

        UtilsXSD xsd = new UtilsXSD(writer);
        xsd.createGrantXSD(metadata, elementsRoot, attributeMainRow, attributeMainRowRequired);
        return filexsd;
    }
    
    @Deprecated
    public static File generateXSD(Writer writer, File dir,
            List<IContainable> metadata, File filexsd, String[] elementsRoot)
            throws IOException, NoSuchFieldException, SecurityException,
            InstantiationException, IllegalAccessException
    {

        UtilsXSD xsd = new UtilsXSD(writer);
        xsd.createXSD(metadata, elementsRoot);
        return filexsd;
    }

    public static File generateSimpleTypeWithListOfAllMetadata(Writer writer, 
            List<IContainable> metadata, File filexsd, String namespace, String fullNamespace, String name) throws IOException, SecurityException, NoSuchFieldException {
        UtilsXSD xsd = new UtilsXSD(writer);
        xsd.createSimpleTypeFor(metadata, namespace, fullNamespace, name);
        return filexsd;        
    }
    
    public static File newGenerateXSD(Writer writer, File dir,
            List<IContainable> metadata, File filexsd, String[] elementsRoot, String namespace,  String namespaceValue,  String namespaceTarget, String[] attributeMainRow, boolean[] attributeMainRowRequired)
            throws IOException, NoSuchFieldException, SecurityException,
            InstantiationException, IllegalAccessException
    {

        UtilsXSD xsd = new UtilsXSD(writer);
        xsd.createXSD(metadata, elementsRoot, namespace, namespaceValue, namespaceTarget, attributeMainRow, attributeMainRowRequired);
        return filexsd;
    }    
    
    private static <P extends Property<TP>, TP extends PropertiesDefinition> void workOnText(
            ApplicationService applicationService, Element node, TP rpPD,
            List<ValoreDTO> values, List<ValoreDTO> old)
            throws XPathExpressionException
    {
        if (node != null)
        {

            // String nodetext = node.getTextContent();
            // String vis = xpath.evaluate(XPATH_RULES[2], node);
            String nodetext = node.getTextContent();
            String vis = node.getAttribute(UtilsXML.NAMEATTRIBUTE_VISIBILITY);

            if (nodetext != null && !nodetext.isEmpty())
            {
                ValoreDTO valueDTO = new ValoreDTO(nodetext);
                if (vis == null || vis.isEmpty())
                {
                    // check old value
                    vis = checkOldVisibility(applicationService, rpPD, old,
                            nodetext, vis);
                }

                if (vis != null && !vis.isEmpty())
                {
                    valueDTO.setVisibility((Integer.parseInt(vis) == 1 ? true
                            : false));
                }

                // ValoreDTO oldValue = checkOldValue(applicationService, rpPD,
                // old, nodetext, valueDTO.getVisibility());
                // if(oldValue==null) {
                values.add(valueDTO);
                log.debug("Write text field " + rpPD.getShortName()
                        + " with value: " + nodetext + " visibility: "
                        + valueDTO.getVisibility());
                // }
            }
        }
    }

    private static <P extends Property<TP>, TP extends PropertiesDefinition> void workOnLink(
            ApplicationService applicationService, TP rpPD,
            List<ValoreDTO> values, List<ValoreDTO> old, Element nodeLink)
            throws XPathExpressionException
    {
        if (nodeLink != null)
        {

            // String nodetext = nodeLink.getTextContent();
            String nodetext = nodeLink.getTextContent();

            if (nodetext != null && !nodetext.isEmpty())
            {
                // String vis = xpath.evaluate(XPATH_RULES[2], node);
                String vis = nodeLink
                        .getAttribute(UtilsXML.NAMEATTRIBUTE_VISIBILITY);
                // if (vis != null && vis.isEmpty()) {
                // vis = xpath.evaluate(XPATH_RULES[2], nodeLink);
                // }
                // String src = xpath.evaluate(XPATH_RULES[4], node);
                String src = nodeLink
                        .getAttribute(UtilsXML.NAMEATTRIBUTE_SRC_LINK);
                // if (src != null && src.isEmpty()) {
                // src = xpath.evaluate(XPATH_RULES[4], nodeLink);
                // }

                nodetext += "|||" + src;

                if (vis == null || vis.isEmpty())
                {
                    // check old value
                    vis = checkOldVisibility(applicationService, rpPD, old,
                            nodetext, vis);
                }
                PropertyEditor pe = rpPD.getRendering().getPropertyEditor(
                        applicationService);
                pe.setAsText(nodetext);
                ValoreDTO valueDTO = new ValoreDTO(pe.getValue());
                if (vis != null && !vis.isEmpty())
                {
                    valueDTO.setVisibility((Integer.parseInt(vis) == 1 ? true
                            : false));
                }
                // ValoreDTO oldValue = checkOldValue(applicationService, rpPD,
                // old, nodetext, valueDTO.getVisibility());
                // if(oldValue==null) {
                values.add(valueDTO);
                log.debug("Write link field " + rpPD.getShortName()
                        + " with value" + nodetext + " visibility: "
                        + valueDTO.getVisibility());
                // }
            }
        }
    }

    private static <P extends Property<TP>, TP extends PropertiesDefinition> void workOnDate(
            ApplicationService applicationService, Element node, TP rpPD,
            List<ValoreDTO> values, List<ValoreDTO> old, Node nodeDate)
            throws XPathExpressionException
    {
        if (nodeDate != null)
        {
            // String nodetext = nodeDate.getTextContent();
            String nodetext = nodeDate.getTextContent();

            if (nodetext != null && !nodetext.isEmpty())
            {
                // String vis = xpath.evaluate(XPATH_RULES[2], node);
                String vis = node
                        .getAttribute(UtilsXML.NAMEATTRIBUTE_VISIBILITY);
                // if (vis != null) {
                // if (vis.isEmpty()) {
                // vis = xpath.evaluate(XPATH_RULES[2], nodeDate);
                // }
                // }

                if (vis == null || vis.isEmpty())
                {
                    // check old value
                    vis = checkOldVisibility(applicationService, rpPD, old,
                            nodetext, vis);
                }

                PropertyEditor pe = rpPD.getRendering().getPropertyEditor(
                        applicationService);
                pe.setAsText(nodetext);
                ValoreDTO valueDTO = new ValoreDTO(pe.getValue());
                if (vis != null && !vis.isEmpty())
                {
                    valueDTO.setVisibility((Integer.parseInt(vis) == 1 ? true
                            : false));
                }
                // ValoreDTO oldValue = checkOldValue(applicationService, rpPD,
                // old, nodetext, valueDTO.getVisibility());
                // if(oldValue==null) {
                values.add(valueDTO);
                log.debug("Write date field " + rpPD.getShortName()
                        + " with value: " + nodetext + " visibility: "
                        + valueDTO.getVisibility());
                // }
            }
        }
    }

    /**
     * 
     * Check old visibility on dynamic field
     * 
     * @param applicationService
     * @param rpPD
     * @param old
     * @param nodetext
     * @param vis
     * @return
     */
    private static <P extends Property<TP>, TP extends PropertiesDefinition> String checkOldVisibility(
            ApplicationService applicationService, TP rpPD,
            List<ValoreDTO> old, String nodetext, String vis)
    {
        PropertyEditor pe = rpPD.getRendering().getPropertyEditor(
                applicationService);

        boolean founded = false;
        for (ValoreDTO temp : old)
        {
            pe.setValue(temp.getObject());
            if (pe.getAsText().equals(nodetext))
            {
                vis = temp.getVisibility() ? "1" : "0";
                founded = true;
                break;
            }
        }
        return founded == true ? vis : DEFAULT_VISIBILITY;
    }

    private static Integer checkOldVisibility(
            ApplicationService applicationService, String value,
            List<RestrictedField> object, Integer vis)
    {
        boolean founded = false;
        for (RestrictedField f : object)
        {
            if (f.getValue().equals(value))
            {
                vis = f.getVisibility();
                founded = true;
                break;
            }
        }
        return founded == true ? vis : VisibilityConstants.PUBLIC;
    }

    private static Integer checkOldVisibility(
            ApplicationService applicationService, String value,
            RestrictedField field, Integer visibility)
    {

        return field.getValue().equals(value) ? field.getVisibility()
                : VisibilityConstants.PUBLIC;
    }

    private static <P extends Property<TP>, TP extends PropertiesDefinition> String workOnCombo(
            ApplicationService applicationService, Element nodecombo, TP rpPD,
            List<ValoreDTO> values, List<ValoreDTO> old)
            throws XPathExpressionException
    {

        AnagraficaObjectDTO subOldDTO = new AnagraficaObjectDTO();
        for (ValoreDTO o : old)
        {
            for (RPPropertiesDefinition rpd : ((WidgetCombo<RPProperty, RPPropertiesDefinition>) (rpPD
                    .getRendering())).getSottoTipologie())
            {
                List<ValoreDTO> t = subOldDTO.getAnagraficaProperties().get(
                        rpd.getShortName());
                if (t != null && !t.isEmpty())
                {
                    t.addAll(((AnagraficaObjectDTO) o.getObject())
                            .getAnagraficaProperties().get(rpd.getShortName()));
                    subOldDTO.getAnagraficaProperties().put(rpd.getShortName(),
                            t);
                }
                else
                {
                    subOldDTO.getAnagraficaProperties().put(
                            rpd.getShortName(),
                            ((AnagraficaObjectDTO) o.getObject())
                                    .getAnagraficaProperties().get(
                                            rpd.getShortName()));
                }
            }
        }
        if (nodecombo != null)
        {
            AnagraficaObjectDTO subDTO = new AnagraficaObjectDTO();

            for (RPPropertiesDefinition rpd : ((WidgetCombo<RPProperty, RPPropertiesDefinition>) (rpPD
                    .getRendering())).getSottoTipologie())
            {
                String xpathExpression = rpd.getShortName();
                List<ValoreDTO> valuescombo = subDTO.getAnagraficaProperties()
                        .get(xpathExpression);
                List<ValoreDTO> valuesoldcombo = subOldDTO
                        .getAnagraficaProperties().get(xpathExpression);

                if (valuescombo == null)
                {
                    valuescombo = new LinkedList<ValoreDTO>();
                    subDTO.getAnagraficaProperties().put(xpathExpression,
                            valuescombo);
                }

                // if (rpPD.isRepeatable()) {

                // NodeList nodeslist = (NodeList) xpath.evaluate(
                // xpathExpression, nodecombo, XPathConstants.NODESET);
                List<Element> nodeslist = XMLUtils.getElementList(nodecombo,
                        rpd.getShortName());
                for (int y = 0; y < nodeslist.size(); y++)
                {
                    Element nodelistelement = nodeslist.get(y);
                    if (nodelistelement != null)
                    {
                        if (rpd.getRendering() instanceof WidgetTesto)
                        {
                            // if (rpd.isRepeatable()) {
                            // // NodeList nodeslistelement = nodelistelement
                            // // .getChildNodes();
                            // List<Element> nodeslistelement =
                            // for (int x = 0; x < nodeslistelement
                            // .getLength(); x++) {
                            // Element nodetext = nodeslistelement
                            // .get(x);
                            // workOnText(applicationService, xpath,
                            // xpathExpression, nodetext, rpd,
                            // valuescombo, valuesoldcombo);
                            // }
                            // } else {
                            workOnText(applicationService, nodelistelement,
                                    rpd, valuescombo, valuesoldcombo);
                            // }

                        }
                        if (rpd.getRendering() instanceof WidgetDate)
                        {
                            // if (rpd.isRepeatable()) {
                            // NodeList nodeslistelement = nodelistelement
                            // .getChildNodes();
                            // for (int x = 0; x < nodeslistelement
                            // .getLength(); x++) {
                            // Node nodeDate = nodeslistelement
                            // .item(x);
                            // workOnDate(applicationService, xpath,
                            // xpathExpression,
                            // nodelistelement, rpd,
                            // valuescombo, valuesoldcombo,
                            // nodeDate);
                            // }
                            // } else {

                            workOnDate(applicationService, nodelistelement,
                                    rpd, valuescombo, valuesoldcombo,
                                    nodelistelement);
                            // }
                        }
                        if (rpd.getRendering() instanceof WidgetLink)
                        {

                            // if (rpd.isRepeatable()) {
                            // NodeList nodeslistelement = nodelistelement
                            // .getChildNodes();
                            // for (int x = 0; x < nodeslistelement
                            // .getLength(); x++) {
                            // Node nodeLink = nodeslistelement
                            // .item(x);
                            // workOnLink(applicationService, xpath,
                            // nodelistelement, rpd,
                            // valuescombo, valuesoldcombo,
                            // nodeLink);
                            // }
                            // } else {

                            workOnLink(applicationService, rpd, valuescombo,
                                    valuesoldcombo, nodelistelement);
                            // }
                        }
                    }

                    // else {
                    // List<ValoreDTO> combovalues = subDTO
                    // .getAnagraficaProperties().get(
                    // xpathExpression);
                    // if (combovalues == null || combovalues.isEmpty()) {
                    // combovalues = new LinkedList<ValoreDTO>();
                    // }
                    // subDTO.getAnagraficaProperties().put(
                    // xpathExpression, combovalues);
                    // }

                }
                // } else {
                // Node nsubcombo = (Node) xpath.evaluate(xpathExpression,
                // nodecombo, XPathConstants.NODE);
                //
                // if (nsubcombo != null) {
                //
                // if (rpd.getRendering() instanceof WidgetTesto) {
                // if (rpd.isRepeatable()) {
                // NodeList nodeslistelement = (NodeList) xpath
                // .evaluate(xpathExpression, nsubcombo,
                // XPathConstants.NODESET);
                // for (int x = 0; x < nodeslistelement
                // .getLength(); x++) {
                // Node nodetext = nodeslistelement.item(x);
                // workOnText(applicationService, xpath,
                // xpathExpression, nodetext, rpd,
                // valuescombo, valuesoldcombo);
                // }
                // } else {
                // workOnText(applicationService, xpath,
                // xpathExpression, nsubcombo, rpd,
                // valuescombo, valuesoldcombo);
                // }
                //
                // }
                // if (rpd.getRendering() instanceof WidgetDate) {
                // if (rpd.isRepeatable()) {
                // NodeList nodeslistelement = (NodeList) xpath
                // .evaluate(xpathExpression, nsubcombo,
                // XPathConstants.NODESET);
                // for (int x = 0; x < nodeslistelement
                // .getLength(); x++) {
                // Node nodeDate = nodeslistelement.item(x);
                // workOnDate(applicationService, xpath,
                // xpathExpression, nsubcombo, rpd,
                // valuescombo, valuesoldcombo, nodeDate);
                // }
                // } else {
                // Node nodeDate = (Node) xpath.evaluate(
                // xpathExpression, nsubcombo,
                // XPathConstants.NODE);
                // workOnDate(applicationService, xpath,
                // xpathExpression, nsubcombo, rpd,
                // valuescombo, valuesoldcombo, nodeDate);
                // }
                // }
                // if (rpd.getRendering() instanceof WidgetLink) {
                //
                // if (rpd.isRepeatable()) {
                // NodeList nodeslistelement = (NodeList) xpath
                // .evaluate(xpathExpression, nsubcombo,
                // XPathConstants.NODESET);
                // for (int x = 0; x < nodeslistelement
                // .getLength(); x++) {
                // Node nodeLink = nodeslistelement.item(x);
                // workOnLink(applicationService, xpath,
                // nsubcombo, rpd, valuescombo, valuesoldcombo,
                // nodeLink);
                // }
                // } else {
                // Node nodeLink = (Node) xpath.evaluate(
                // xpathExpression, nsubcombo,
                // XPathConstants.NODE);
                //
                // workOnLink(applicationService, xpath,
                // nsubcombo, rpd, valuescombo, valuesoldcombo,
                // nodeLink);
                // }
                // }
                //
                // }
                // else {
                // List<ValoreDTO> combovalues = subDTO
                // .getAnagraficaProperties().get(xpathExpression);
                // if (combovalues == null || combovalues.isEmpty()) {
                // combovalues = new LinkedList<ValoreDTO>();
                // }
                // subDTO.getAnagraficaProperties().put(xpathExpression,
                // combovalues);
                // }
                // }
            }
            values.add(new ValoreDTO(subDTO));
            log.debug("Write complex field: " + rpPD.getShortName());
        }
        return rpPD.getShortName();
    }

    /**
     * Export xml, it don't close or flush writer, format with
     * {@link XMLOutputter}, use use jdom for it.
     * 
     * @param writer
     * @param applicationService
     * @param metadata
     * @param researchers
     * @throws IOException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws ParserConfigurationException
     * @throws TransformerException
     */
    @Deprecated
    public static void exportXML(Writer writer,
            ApplicationService applicationService, List<IContainable> metadata,
            List<ResearcherPage> researchers) throws IOException,
            SecurityException, IllegalArgumentException, NoSuchFieldException,
            IllegalAccessException, InvocationTargetException,
            ParserConfigurationException, TransformerException
    {

        UtilsXML xml = new UtilsXML(writer, applicationService);
        org.jdom.Document xmldoc = xml.createRoot(null, null, "http://www.cilea.it/researcherpage/schemas");
        if (researchers != null)
        {
            for (ResearcherPage rp : researchers)
            {
                xml.writeRP(rp, metadata, xmldoc.getRootElement());
            }
            // Serialisation through XMLOutputter
            XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
            out.output(xmldoc, writer);
        }
    }

    public static <TP extends PropertiesDefinition, P extends Property<TP>, AO extends AnagraficaObject<P, TP>, I extends IExportableDynamicObject<TP, P, AO>> void newExportXML(Writer writer,
            ApplicationService applicationService, List<IContainable> metadata,
            List<I> objects, String prefixNamespace, String namespace, String rootName) throws IOException,
            SecurityException, IllegalArgumentException, NoSuchFieldException,
            IllegalAccessException, InvocationTargetException,
            ParserConfigurationException, TransformerException
    {

        UtilsXML xml = new UtilsXML(writer, applicationService);
        org.jdom.Document xmldoc = xml.createRoot(rootName, prefixNamespace, namespace);
        if (objects != null)
        {
            for (I rp : objects)
            {
                xml.write(rp, metadata, xmldoc.getRootElement());
            }
            // Serialisation through XMLOutputter
            XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
            out.output(xmldoc, writer);
        }
    }

    /**
     * Import RGs from RPs finded on database
     * 
     * @param applicationService
     * @param dspaceContext
     * @param status
     *            get only active or all rps
     * @param active
     *            set status true to newly rg
     * @param newly
     *            add only new project
     */
    public static void importGrants(ApplicationService applicationService,
            Context dspaceContext, boolean status, boolean active, boolean newly)
            throws Exception
    {

        List<ResearcherPage> rps = new LinkedList<ResearcherPage>();
        if (status)
        {
            rps = applicationService.getAllResearcherPageByStatus(status);
        }
        else
        {
            rps.addAll(applicationService.getList(ResearcherPage.class));
        }

        // extract grants from rps
        int newImported = 0;
        int editImported = 0;
        int discardImported = 0;
        int skipImported = 0;
        log.info("Start import " + new Date() + " mode(status/active/newly): "
                + status + "/" + active + "/" + newly);
        for (ResearcherPage rp : rps)
        {
            List<RPProperty> rpProperties = rp.getDynamicField()
                    .getAnagrafica4view().get(UtilsXML.GRANT_TAG_PROJECTS);

            if (rpProperties != null && !rpProperties.isEmpty())
            {
                for (RPProperty rpp : rpProperties)
                {
                    MultiValue mvrp = (MultiValue) (rpp.getValue());
                    Map<String, ValoreDTO> map = AnagraficaUtils
                            .getFirstSingleValue(
                                    mvrp,
                                    new String[] { UtilsXML.GRANT_TAG_PROJECTSCODE });
                    String projectcode = (String) map.get(
                            UtilsXML.GRANT_TAG_PROJECTSCODE).getObject();

                    ResearcherGrant rg = null;
                    // use dto to fill dynamic metadata
                    AnagraficaObjectDTO dtoRG = new AnagraficaObjectDTO();
                    if (projectcode != null && !projectcode.isEmpty())
                    {
                        rg = applicationService
                                .getResearcherGrantByCode(projectcode.trim());
                    }
                    else
                    {
                        log.error("Grant discarded ( projectCode not founded) [researcher: "
                                + ResearcherPageUtils
                                        .getPersistentIdentifier(rp) + "]");
                        discardImported++;
                        continue;
                    }

                    // skip if only new grants mode and rg is found
                    if (newly && rg != null)
                    {
                        skipImported++;
                        continue;
                    }

                    // create new grants
                    if (rg == null)
                    {
                        log.info("Create new GRANT with code " + projectcode);
                        rg = new ResearcherGrant();
                        rg.setRgCode(projectcode);
                        rg.setStatus(active);
                        newImported++;
                    }
                    else
                    {
                        log.info("Edit GRANT with code " + projectcode);
                        editImported++;
                    }

                    List<RPPropertiesDefinition> subTps = ((WidgetCombo<RPProperty, RPPropertiesDefinition>) (rpp
                            .getTypo().getRendering())).getSottoTipologie();
                    List<GrantPropertiesDefinition> rgTps = applicationService
                            .getListTipologieProprietaFirstLevel(GrantPropertiesDefinition.class);

                    for (String key : mvrp.getObject()
                            .getAnagraficaProperties().keySet())
                    {
                        dtoRG.getAnagraficaProperties().put(
                                key,
                                mvrp.getObject().getAnagraficaProperties()
                                        .get(key));
                    }

                    // get investigators/coninvestigator
                    List<ValoreDTO> investigatorDTO = dtoRG
                            .getAnagraficaProperties().get(
                                    UtilsXML.GRANT_TAG_INVESTIGATOR);
                    for (ValoreDTO vv : investigatorDTO)
                    {
                        Investigator inv = new Investigator();
                        EmbeddedLinkValue link = (EmbeddedLinkValue) vv
                                .getObject();
                        if (link != null)
                        {
                            if (link.getValueLink() != null
                                    && !link.getValueLink().isEmpty())
                            {
                                inv.setIntInvestigator(applicationService
                                        .getResearcherByAuthorityKey(link
                                                .getValueLink().substring(3)));
                            }
                            else
                            {
                                inv.setExtInvestigator(link
                                        .getDescriptionLink());
                            }
                        }
                        rg.setInvestigator(inv);
                    }
                    List<ValoreDTO> coinvestigatorDTO = dtoRG
                            .getAnagraficaProperties().get(
                                    UtilsXML.GRANT_TAG_COINVESTIGATOR);
                    List<Investigator> coinvestigators = new LinkedList<Investigator>();
                    for (ValoreDTO vv : coinvestigatorDTO)
                    {
                        Investigator co = new Investigator();
                        EmbeddedLinkValue link = (EmbeddedLinkValue) vv
                                .getObject();
                        if (link != null)
                        {
                            if (link.getValueLink() != null
                                    && !link.getValueLink().isEmpty())
                            {
                                co.setIntInvestigator(applicationService
                                        .getResearcherByAuthorityKey(link
                                                .getValueLink()));
                            }
                            else
                            {
                                co.setExtInvestigator(link.getDescriptionLink());

                            }
                        }
                        coinvestigators.add(co);
                    }
                    rg.setCoInvestigators(coinvestigators);
                    AnagraficaUtils.reverseDTO(dtoRG, rg, rgTps);
                    applicationService.saveOrUpdate(ResearcherGrant.class, rg);

                }
            }
        }
        log.info("Stats: total grants imported " + (newImported + editImported)
                + " new/edit:" + newImported + "/" + editImported
                + " discarded:" + discardImported + " "
                + (newly ? " skipped:" + skipImported : ""));
        log.info("### END IMPORT " + new Date());

    }

    /**
     * 
     * Import grant from xml file, matching validation with xsd builded at
     * runtime execution associate to list of dynamic fields
     * 
     * @param input
     *            - XML file stream
     * @param dir
     *            - directory to write temporaries xsd and xml (this xsd
     *            validate actual xml)
     * @param applicationService
     *            - service
     * @throws Exception
     */
    public static void importGrantsXML(InputStream input, File dir,
            ApplicationService applicationService, Context dspaceContext,
            boolean status) throws Exception
    {

        File filexsd = null;
        File filexml = null;

        // build filexml
        String nameXML = "xml-" + dateFormat.format(new Date()) + ".xml";
        filexml = new File(dir, nameXML);
        filexml.createNewFile();
        FileOutputStream out = new FileOutputStream(filexml);
        Utils.bufferedCopy(input, out);
        out.close();

        List<IContainable> metadataALL = applicationService
                .findAllContainables(GrantPropertiesDefinition.class);

        // create xsd and write up
        String nameXSD = "xsd-" + dateFormat.format(new Date()) + ".xsd";
        filexsd = new File(dir, nameXSD);
        filexsd.createNewFile();
        FileWriter writer = new FileWriter(filexsd);
        filexsd = generateGrantXSD(writer, dir, metadataALL, filexsd,
                new String[] { "grants", "grant" });

        // create xsd validator
        SchemaFactory factory = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Source schemaSource = new StreamSource(filexsd);

        Schema schema = factory.newSchema(schemaSource);
        // validate source xml to xsd
        Validator validator = schema.newValidator();
        validator.validate(new StreamSource(filexml));

        // parse xml to dom
        DocumentBuilder parser = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder();
        Document document = parser.parse(filexml);

        List<GrantPropertiesDefinition> realFillTPS = applicationService
                .getListTipologieProprietaFirstLevel(GrantPropertiesDefinition.class);

        List<Element> grantxml = XMLUtils.getElementList(
                document.getDocumentElement(), "grant");
        int rows_discarded = 0;
        int rows_imported = 0;
        log.info("Start import " + new Date());
        // foreach researcher element in xml
        for (int i = 0; i < grantxml.size(); i++)
        {
            log.info("Number " + i + " of " + grantxml.size());
            ResearcherGrant grant = null;
            try
            {
                Element node = grantxml.get(i);

                // check if staffNo and rpid exists as attribute
                // String nodeId = (String) xpath.evaluate(XPATH_RULES[1], node,
                // XPathConstants.STRING);
                // String rpId = (String) xpath.evaluate(XPATH_RULES[3], node,
                // XPathConstants.STRING);
                String nodeId = node
                        .getAttribute(UtilsXML.GRANT_NAMEATTRIBUTE_CODE);
                String rgId = node
                        .getAttribute(UtilsXML.GRANT_NAMEATTRIBUTE_RGID);
                ResearcherGrant clone = null;
                // use dto to fill dynamic metadata
                AnagraficaObjectDTO dto = new AnagraficaObjectDTO();
                AnagraficaObjectDTO clonedto = new AnagraficaObjectDTO();
                boolean update = false; // if update a true then set field to
                                        // null
                                        // on case of empty element
                if (nodeId == null || nodeId.isEmpty())
                {
                    log.error("Grant discarded ( code not founded) [position grant: "
                            + i + "]");
                    throw new RuntimeException(
                            "Grant discarded (code not founded whilst rgId is on xml) [position grant: "
                                    + i + "]");

                }
                else
                {
                    // if there is rgid then try to get grant by code
                    // and
                    // set to null all structural metadata lists
                    log.info("Grant staffNo : " + nodeId
                            + " / rg identifier : " + rgId);
                    if (rgId != null && !rgId.isEmpty())
                    {
                        grant = applicationService
                                .getResearcherGrantByCode(nodeId);
                        if (grant == null)
                        {
                            log.error("Grant discarded (code not founded whilst rgId is on xml) [position grant: "
                                    + i + "]");
                            ;
                            throw new RuntimeException(
                                    "Grant discarded (code not founded whilst rgId is on xml) [position grant: "
                                            + i + "]");
                        }
                        else
                        {
                            if (!rgId.equals(grant.getId().toString()))
                            {
                                log.error("Grant discarded (rgId don't match persistent identifier) [position grant: "
                                        + i + "]");
                                throw new RuntimeException(
                                        "Grant discarded (rgId don't match persistent identifier) [position grant: "
                                                + i + "]");
                            }
                        }
                        // clone dynamic data and structural on dto

                        clone = (ResearcherGrant) grant.clone();
                        ResearcherGrant additionalTemp = new ResearcherGrant();
                        clone.setAnagrafica(additionalTemp.getAnagrafica());
                        additionalTemp.duplicaAnagrafica(grant);
                        update = true;

                    }
                    else
                    {
                        // here there is perhaps a new grant
                        grant = applicationService
                                .getResearcherGrantByCode(nodeId);
                        if (grant == null)
                        {
                            grant = new ResearcherGrant();
                            grant.setRgCode(nodeId);
                            // use -active in command line to change default
                            // status to active.
                            grant.setStatus(status);

                            clone = (ResearcherGrant) grant.clone();
                            ResearcherGrant additionalTemp = new ResearcherGrant();
                            clone.setAnagrafica(additionalTemp.getAnagrafica());
                            additionalTemp.duplicaAnagrafica(grant);
                        }
                        else
                        {
                            log.error("Grant discarded (code " + nodeId
                                    + " already exist) [position grant: " + i
                                    + "]");
                            throw new RuntimeException("Grant discarded (code "
                                    + nodeId
                                    + " already exist) [position grant: " + i
                                    + "]");
                        }
                    }
                }

                AnagraficaUtils.fillDTO(dto, grant, realFillTPS);

                // one-shot fill and reverse to well-format clonedto and clean
                // empty
                // data
                AnagraficaUtils.fillDTO(clonedto, clone, realFillTPS);
                AnagraficaUtils.reverseDTO(clonedto, clone, realFillTPS);
                AnagraficaUtils.fillDTO(clonedto, clone, realFillTPS);

                importDynAXML(applicationService, realFillTPS, node, dto,
                        clonedto, update);

                // import investigator and coinvestigators
                Element control_value = XMLUtils.getSingleElement(node,
                        UtilsXML.GRANT_ELEMENT_INVESTIGATOR);

                if (control_value != null)
                {
                    String value = control_value.getTextContent();
                    boolean isRP = false;
                    if (value.isEmpty())
                    {
                        value = control_value
                                .getAttribute(UtilsXML.GRANT_NAMEATTRIBUTE_RPID);
                        isRP = true;
                    }

                    if (!value.isEmpty())
                    {
                        Investigator investigator = new Investigator();
                        if (isRP)
                        {
                            investigator.setIntInvestigator(applicationService
                                    .getResearcherByAuthorityKey(value));
                        }
                        else
                        {
                            investigator.setExtInvestigator(value);
                        }
                        grant.setInvestigator(investigator);
                    }
                    else
                    {
                        if (update)
                        {
                            log.info("Principal investigator can't empty");
                        }
                    }
                }

                List<Element> nodeslist = XMLUtils.getElementList(node,
                        UtilsXML.GRANT_ELEMENT_COINVESTIGATOR);
                List<Investigator> object = (List<Investigator>) grant
                        .getCoInvestigators();

                for (int y = 0; y < nodeslist.size(); y++)
                {
                    if (update == true && y == 0)
                    {
                        object.clear();
                    }
                    Element nsublist = nodeslist.get(y);
                    String value = nsublist.getTextContent();
                    boolean isRP = false;
                    if (value.isEmpty())
                    {
                        value = nsublist
                                .getAttribute(UtilsXML.GRANT_NAMEATTRIBUTE_RPID);
                        isRP = true;
                    }

                    if (value != null && !value.isEmpty())
                    {
                        Investigator investigator = new Investigator();
                        if (isRP)
                        {
                            investigator.setIntInvestigator(applicationService
                                    .getResearcherByAuthorityKey(value));
                        }
                        else
                        {
                            investigator.setExtInvestigator(value);
                        }
                        object.add(investigator);
                    }

                }
                AnagraficaUtils.reverseDTO(dto, grant, realFillTPS);

                applicationService.saveOrUpdate(ResearcherGrant.class, grant);

                log.info("Import grant " + grant.getRgCode() + " (code) / "
                        + grant.getId() + " (id) - SUCCESS");
                rows_imported++;
            }
            catch (RuntimeException e)
            {
                log.error("Import grant - FAILED " + e.getMessage(), e);
                rows_discarded++;
            }

        }

        log.info("Statistics: row ingested " + rows_imported + " on total of "
                + (grantxml.size()) + " (" + rows_discarded + " row discarded)");
    }

}
