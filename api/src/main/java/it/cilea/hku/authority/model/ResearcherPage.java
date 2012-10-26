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
package it.cilea.hku.authority.model;

import it.cilea.hku.authority.model.dynamicfield.RPAdditionalFieldStorage;
import it.cilea.hku.authority.model.dynamicfield.RPPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.RPProperty;
import it.cilea.hku.authority.model.export.ExportConstants;
import it.cilea.hku.authority.model.listener.RPListener;
import it.cilea.osd.common.core.HasTimeStampInfo;
import it.cilea.osd.common.core.SingleTimeStampInfo;
import it.cilea.osd.common.core.TimeStampInfo;
import it.cilea.osd.common.model.Identifiable;
import it.cilea.osd.jdyna.model.AnagraficaSupport;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.log4j.Logger;
import org.dspace.authorize.AuthorizeException;
import org.dspace.core.Context;
import org.dspace.eperson.EPerson;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * This class models the HKU Researcher Page concept. Almost all the field of
 * this class are {@link RestrictedField} or List of it, because the visibility
 * of its values can be turned on/off. A ResearcherPage maintains also a list of
 * related {@link ItemOutsideHub}
 * 
 * @author cilea
 * 
 */
@Entity
@Table(name = "cris_researcherpage")
@NamedQueries({
        @NamedQuery(name = "ResearcherPage.findAll", query = "from ResearcherPage order by id"),
        @NamedQuery(name = "ResearcherPage.paginate.id.asc", query = "from ResearcherPage order by id asc"),
        @NamedQuery(name = "ResearcherPage.paginate.id.desc", query = "from ResearcherPage order by id desc"),
        @NamedQuery(name = "ResearcherPage.paginate.status.asc", query = "from ResearcherPage order by status asc"),
        @NamedQuery(name = "ResearcherPage.paginate.status.desc", query = "from ResearcherPage order by status desc"),
        @NamedQuery(name = "ResearcherPage.count", query = "select count(*) from ResearcherPage"),
        @NamedQuery(name = "ResearcherPage.findAllResearcherPageByStatus", query = "from ResearcherPage where status = ? order by id"),
        // @NamedQuery(name = "ResearcherPage.findAllResearcherByField", query =
        // "select rp from ResearcherPage rp where :par0 in indices(rp.additionalFields)"),
        @NamedQuery(name = "ResearcherPage.findAllResearcherByName", query = "select distinct rp from ResearcherPage rp join rp.dynamicField.anagrafica vv where ((vv.typo.shortName = 'variants' or vv.typo.shortName = 'preferredName' or vv.typo.shortName = 'fullName' or vv.typo.shortName = 'translatedName') and vv.value = :par0)"),
        @NamedQuery(name = "ResearcherPage.countAllResearcherByName", query = "select count(*) from ResearcherPage rp join rp.dynamicField.anagrafica vv where ((vv.typo.shortName = 'variants' or vv.typo.shortName = 'preferredName' or vv.typo.shortName = 'fullName' or vv.typo.shortName = 'translatedName') and vv.value = :par0)"),
        @NamedQuery(name = "ResearcherPage.countAllResearcherByNameExceptResearcher", query = "select count(*) from ResearcherPage rp join rp.dynamicField.anagrafica vv where ((vv.typo.shortName = 'variants' or vv.typo.shortName = 'preferredName' or vv.typo.shortName = 'fullName' or vv.typo.shortName = 'translatedName') and vv.value = :par0) and rp.id != :par1 "),
        @NamedQuery(name = "ResearcherPage.findAllResearcherByNamesTimestampLastModified", query = "from ResearcherPage where namesModifiedTimeStamp.timestamp >= ?"),
        @NamedQuery(name = "ResearcherPage.uniqueBySourceID", query = "from ResearcherPage rp where rp.sourceID = ?"),
        @NamedQuery(name = "ResearcherPage.findAllResearcherInDateRange", query = "from ResearcherPage rp where rp.timeStampInfo.timestampCreated.timestamp between :par0 and :par1"),
        @NamedQuery(name = "ResearcherPage.findAllResearcherByCreationDateBefore", query = "from ResearcherPage rp where rp.timeStampInfo.timestampCreated.timestamp <= ?"),
        @NamedQuery(name = "ResearcherPage.findAllResearcherByCreationDateAfter", query = "from ResearcherPage rp where rp.timeStampInfo.timestampCreated.timestamp >= ?"),
        @NamedQuery(name = "ResearcherPage.findAllNextResearcherByIDStart", query = "from ResearcherPage rp where rp.id >= ?"),
        @NamedQuery(name = "ResearcherPage.findAllPrevResearcherByIDEnd", query = "from ResearcherPage rp where rp.id <= ?"),
        @NamedQuery(name = "ResearcherPage.findAllResearcherInIDRange", query = "from ResearcherPage rp where rp.id between :par0 and :par1"),
        @NamedQuery(name = "ResearcherPage.findAllNextResearcherBysourceIDStart", query = "from ResearcherPage rp where rp.sourceID >= ?"),
        @NamedQuery(name = "ResearcherPage.findAllPrevResearcherBysourceIDEnd", query = "from ResearcherPage rp where rp.sourceID <= ?"),
        @NamedQuery(name = "ResearcherPage.findAllResearcherInsourceIDRange", query = "from ResearcherPage rp where rp.sourceID between :par0 and :par1"),
        @NamedQuery(name = "ResearcherPage.findAnagraficaByRPID", query = "select dynamicField.anagrafica from ResearcherPage rp where rp.id = ?"),
        @NamedQuery(name = "ResearcherPage.uniqueUUID", query = "from ResearcherPage where uuid = ?")
})
public class ResearcherPage extends ACrisObject
        implements
        HasTimeStampInfo,
        Cloneable,
        IExportableDynamicObject<RPPropertiesDefinition, RPProperty, RPAdditionalFieldStorage>,
        AnagraficaSupport<RPProperty, RPPropertiesDefinition>
{

    
    private Integer epersonID;
    
    @Transient
    public static final int PUBLICATION_LIST_SECTION = -1;

    @Transient
    public static final int DOWNLOAD_CV_SECTION = -2;

    @Transient
    public static final int COLLABORATION_NETWORK_SECTION = -3;

    @Transient
    /**
     * Constant for resource type assigned to the RP
     */
    public static final int RP_TYPE_ID = 9;

    /** log4j logger */
    @Transient
    private static Logger log = Logger.getLogger(ResearcherPage.class);

    /** timestamp info for creation and last modify */
    @Embedded
    private TimeStampInfo timeStampInfo;

    @Embedded
    @AttributeOverride(name = "timestamp", column = @Column(name = "namesTimestampLastModified"))
    private SingleTimeStampInfo namesModifiedTimeStamp;

    /** DB Primary key */
    @Id
    @GeneratedValue(generator = "CRIS_RESEARCHERPAGE_SEQ")
    @SequenceGenerator(name = "CRIS_RESEARCHERPAGE_SEQ", sequenceName = "CRIS_RESEARCHERPAGE_SEQ")
    private Integer id;


    /** True if researcher is active */
    private Boolean status;


    @Transient
    /**
     * The names that the ResearcherPage has when loaded from the db the first
     * time. It is useful for comparison with the current names to see if
     * changes has been made.
     * 
     */
    private String oldNames;

    /**
     * Map of additional custom data
     */
    @Embedded
    private RPAdditionalFieldStorage dynamicField;

    /**
     * Manually rejected potential matches
     */
    @CollectionOfElements(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SELECT)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    private Set<Integer> rejectItems;

    @Transient
    private boolean internalRP = true;

    /**
     * Constructor method, create new ResearcherPage setting status to true.
     */
    public ResearcherPage()
    {
        this.status = true;
        this.dynamicField = new RPAdditionalFieldStorage();
    }

    /**
     * Getter method.
     * 
     * @return the db primary key
     */
    public Integer getId()
    {
        return id;
    }

    /**
     * Setter method.
     * 
     * @param id
     *            the db primary key
     */
    public void setId(Integer id)
    {
        this.id = id;
    }


    /**
     * Wrapper method.
     * 
     * @return the fullName
     */
    public String getFullName()
    {
        String result = "";
        for (RPProperty property : this.getDynamicField().getAnagrafica4view()
                .get("fullName"))
        {
            result += property.getValue().getObject();
            break;
        }
        return result;
    }

    /**
     * Getter method.
     * 
     * @return the academic name
     */
    public RestrictedField getPreferredName()
    {
        RestrictedField result = new RestrictedField();
        for (RPProperty property : this.getDynamicField().getAnagrafica4view()
                .get("preferredName"))
        {
            result.setValue(property.getValue().getObject().toString());
            result.setVisibility(property.getVisibility());
            break;
        }
        return result;
    }

    /**
     * Wrapper method.
     * 
     * @return the chinese name
     */
    public RestrictedField getTranslatedName()
    {
        RestrictedField result = new RestrictedField();
        for (RPProperty property : this.getDynamicField().getAnagrafica4view()
                .get("translatedName"))
        {
            result.setValue(property.getValue().getObject().toString());
            result.setVisibility(property.getVisibility());
            break;
        }
        return result;
    }

    /**
     * Wrapper method.
     * 
     * @return the variants form of the name (include also Japanese, Korean,
     *         etc.)
     */
    public List<RestrictedField> getVariants()
    {
        List<RestrictedField> results = new ArrayList<RestrictedField>();

        for (RPProperty property : this.getDynamicField().getAnagrafica4view()
                .get("variants"))
        {
            RestrictedField result = new RestrictedField();
            result.setValue(property.getValue().getObject().toString());
            result.setVisibility(property.getVisibility());
            results.add(result);
        }
        return results;
    }

    /**
     * Wrapper method
     * 
     * @return
     */
    public RestrictedField getEmail()
    {
        RestrictedField result = new RestrictedField();
        for (RPProperty property : this.getDynamicField().getAnagrafica4view()
                .get("publicEmail"))
        {
            result.setValue(property.getValue().getObject().toString());
            result.setVisibility(property.getVisibility());
            break;
        }
        return result;

    }

    /**
     * Getter method.
     * 
     * @return the timestamp of creation and last modify of this ResearcherPage
     */
    public TimeStampInfo getTimeStampInfo()
    {
        if (timeStampInfo == null)
        {
            timeStampInfo = new TimeStampInfo();
        }
        return timeStampInfo;
    }

    /**
     * Getter method.
     * 
     * @return the status
     */
    public Boolean getStatus()
    {
        return status;
    }

    /**
     * Setter method.
     * 
     * @param id
     *            the status of this ResearcherPage
     */
    public void setStatus(Boolean status)
    {
        this.status = status;
    }

    /**
     * Getter method.
     * 
     * @return the manually rejected potential matches
     */
    public Set<Integer> getRejectItems()
    {
        if (rejectItems == null)
        {
            rejectItems = new HashSet<Integer>();
        }
        return rejectItems;
    }

    /**
     * Setter method.
     * 
     * @param rejectItems
     *            the manually rejected potential matches
     */
    public void setRejectItems(Set<Integer> rejectItems)
    {
        this.rejectItems = rejectItems;
    }

    /**
     * Getter method.
     * 
     * @return the timestamp of last modify to this Researcher Page's names
     */
    public SingleTimeStampInfo getNamesModifiedTimeStamp()
    {
        return namesModifiedTimeStamp;
    }

    /**
     * Getter method.
     * 
     * @see RPListener
     * @param oldNames
     *            a string containing all the names as initially set on the
     *            first time access. Useful for detect changes to a name:
     *            addition, deletion, visibility change
     */
    public String getOldNames()
    {
        return oldNames;
    }

    /**
     * Setter method.
     * 
     * @param namesModifiedTimeStamp
     *            the timestamp of last modified to researcher names
     */
    public void setNamesModifiedTimeStamp(
            SingleTimeStampInfo namesModifiedTimeStamp)
    {
        this.namesModifiedTimeStamp = namesModifiedTimeStamp;
    }

    /**
     * Setter method.
     * 
     * @see RPListener
     * @param oldNames
     *            a string containing all the names as initially set on the
     *            first time access. Useful for detect changes to a name:
     *            addition, deletion, visibility change
     */
    public void setOldNames(String oldNames)
    {
        this.oldNames = oldNames;
    }

    public void setDynamicField(RPAdditionalFieldStorage dynamicField)
    {
        this.dynamicField = dynamicField;
    }

    public RPAdditionalFieldStorage getDynamicField()
    {
        if (this.dynamicField == null)
        {
            this.dynamicField = new RPAdditionalFieldStorage();
        }
        return dynamicField;
    }

    /**
     * Convenience method to get data from ResearcherPage by a string. For any
     * existent field name the method must return the relative value (i.e
     * getMetadata("fullName") is equivalent to getFullName()) but the method
     * always return a list (with 0, 1 or more elements). For dynamic field it
     * returns the value of the dynamic field with the shorter name equals to
     * the argument. Only public values are returned!
     * 
     * 
     * @param dcField
     *            the field (not null) to retrieve the value
     * @return a list of 0, 1 or more values
     */
    public List<String> getMetadata(String field)
    {
        List<String> result = new ArrayList();

        List<RPProperty> dyna = getDynamicField().getAnagrafica4view().get(
                field);
        for (RPProperty prop : dyna)
        {
            if (prop.getVisibility() == VisibilityConstants.PUBLIC)
                result.add(prop.toString());
        }

        return result;
    }

    @Transient
    public List<String> getAllPublicNames()
    {
        List<String> results = new ArrayList<String>();
        results.add(getFullName());
        if (getPreferredName().getValue() != null
                && !getPreferredName().getValue().isEmpty())
        {
            results.add(getPreferredName().getValue());
        }
        if (getTranslatedName().getValue() != null
                && !getTranslatedName().getValue().isEmpty())
        {
            results.add(getTranslatedName().getValue());
        }
        for (RestrictedField rf : getVariants())
        {
            if (rf.getVisibility() == VisibilityConstants.PUBLIC
                    && rf.getValue() != null)
            {
                results.add(rf.getValue());
            }
        }
        return results;
    }

    public List<String> getAllNames()
    {
        List<String> results = new ArrayList<String>();
        results.add(getFullName());
        if (getPreferredName().getValue() != null
                && !getPreferredName().getValue().isEmpty())
        {
            results.add(getPreferredName().getValue());
        }
        if (getTranslatedName().getValue() != null
                && !getTranslatedName().getValue().isEmpty())
        {
            results.add(getTranslatedName().getValue());
        }
        for (RestrictedField rf : getVariants())
        {
            if (rf.getValue() != null)
            {
                results.add(rf.getValue());
            }
        }
        return results;
    }

    
    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }

    public void setInternalRP(boolean internalRP)
    {
        this.internalRP = internalRP;
    }

    public boolean isInternalRP()
    {
        return internalRP;
    }

    
    public String getNamePublicIDAttribute()
    {
        return ExportConstants.NAME_PUBLICID_ATTRIBUTE;
    }

    
    public String getValuePublicIDAttribute()
    {
        return "" + this.getId();
    }

    
    public String getNameIDAttribute()
    {
        return ExportConstants.NAME_ID_ATTRIBUTE;
    }

    
    public String getValueIDAttribute()
    {
        if (this.getUuid() == null)
        {
            return "";
        }
        return "" + this.getUuid().toString();
    }

    
    public String getNameBusinessIDAttribute()
    {
        return ExportConstants.NAME_BUSINESSID_ATTRIBUTE;
    }

    
    public String getValueBusinessIDAttribute()
    {
        return this.getSourceID();
    }

    
    public String getNameTypeIDAttribute()
    {
        return ExportConstants.NAME_TYPE_ATTRIBUTE;
    }

    
    public String getValueTypeIDAttribute()
    {
        return "" + RP_TYPE_ID;
    }

    
    public String getNameSingleRowElement()
    {
        return ExportConstants.ELEMENT_SINGLEROW;
    }

    
    public String getIdentifyingValue()
    {
        return this.dynamicField.getIdentifyingValue();
    }

    
    public String getDisplayValue()
    {
        return this.dynamicField.getDisplayValue();
    }

    
    public List<RPProperty> getAnagrafica()
    {
        return this.dynamicField.getAnagrafica();
    }

    
    public Map<String, List<RPProperty>> getAnagrafica4view()
    {
        return this.dynamicField.getAnagrafica4view();
    }

    
    public void setAnagrafica(List<RPProperty> anagrafica)
    {
        this.dynamicField.setAnagrafica(anagrafica);
    }

    
    public RPProperty createProprieta(RPPropertiesDefinition tipologiaProprieta)
            throws IllegalArgumentException
    {
        return this.dynamicField.createProprieta(tipologiaProprieta);
    }

    
    public RPProperty createProprieta(
            RPPropertiesDefinition tipologiaProprieta, Integer posizione)
            throws IllegalArgumentException
    {
        return this.dynamicField.createProprieta(tipologiaProprieta, posizione);
    }

    
    public boolean removeProprieta(RPProperty proprieta)
    {
        return this.dynamicField.removeProprieta(proprieta);
    }

    
    public List<RPProperty> getProprietaDellaTipologia(
            RPPropertiesDefinition tipologiaProprieta)
    {
        return this.dynamicField.getProprietaDellaTipologia(tipologiaProprieta);
    }

    
    public Class<RPProperty> getClassProperty()
    {
        return this.dynamicField.getClassProperty();
    }

    
    public Class<RPPropertiesDefinition> getClassPropertiesDefinition()
    {
        return this.dynamicField.getClassPropertiesDefinition();
    }

    
    public void inizializza()
    {
        this.dynamicField.inizializza();
    }

    
    public void invalidateAnagraficaCache()
    {
        this.dynamicField.invalidateAnagraficaCache();
    }

    
    public void pulisciAnagrafica()
    {
        this.dynamicField.pulisciAnagrafica();
    }

    public EPerson getDspaceUser()
    {
        Context context = null;
        EPerson eperson = null;

        try
        {
            context = new Context();
            if (epersonID == null)
            {

                eperson = EPerson.findByEmail(context, getEmail().getValue());
            }
            else
            {
                eperson = EPerson.find(context, epersonID);
            }
        }
        catch (SQLException e)
        {
            log.error(e.getMessage());
        }
        catch (AuthorizeException e)
        {
            log.error(e.getMessage());
        }
        finally
        {
            if (context != null && context.isValid())
            {
                context.abort();
            }
        }

        return eperson;
    }

    public Integer getEpersonID()
    {
        return epersonID;
    }

    public void setEpersonID(Integer idEPerson)
    {
        this.epersonID = idEPerson;
    }


    public String getPublicPath()
    {        
        return "/cris/rp/";
    }

}
