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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
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
@Table(name = "model_researcher_page")
@NamedQueries({
        @NamedQuery(name = "ResearcherPage.findAll", query = "from ResearcherPage order by id"),
        @NamedQuery(name = "ResearcherPage.paginate.id.asc", query = "from ResearcherPage order by id asc"),
        @NamedQuery(name = "ResearcherPage.paginate.id.desc", query = "from ResearcherPage order by id desc"),
        @NamedQuery(name = "ResearcherPage.paginate.academicName.asc", query = "from ResearcherPage order by academicName.value asc"),
        @NamedQuery(name = "ResearcherPage.paginate.academicName.desc", query = "from ResearcherPage order by academicName.value desc"),
        @NamedQuery(name = "ResearcherPage.paginate.chineseName.asc", query = "from ResearcherPage order by chineseName.value asc"),
        @NamedQuery(name = "ResearcherPage.paginate.chineseName.desc", query = "from ResearcherPage order by chineseName.value desc"),        
        @NamedQuery(name = "ResearcherPage.paginate.status.asc", query = "from ResearcherPage order by status asc"),
        @NamedQuery(name = "ResearcherPage.paginate.status.desc", query = "from ResearcherPage order by status desc"),
        @NamedQuery(name = "ResearcherPage.paginate.staffNo.asc", query = "from ResearcherPage order by staffNo asc"),
        @NamedQuery(name = "ResearcherPage.paginate.staffNo.desc", query = "from ResearcherPage order by staffNo desc"),
        @NamedQuery(name = "ResearcherPage.paginate.fullName.asc", query = "from ResearcherPage order by fullName asc"),
        @NamedQuery(name = "ResearcherPage.paginate.fullName.desc", query = "from ResearcherPage order by fullName desc"),
        @NamedQuery(name = "ResearcherPage.count", query = "select count(*) from ResearcherPage"),
        @NamedQuery(name = "ResearcherPage.findAllResearcherPageByStatus", query = "from ResearcherPage where status = ? order by id"),
        @NamedQuery(name = "ResearcherPage.findAllResearcherByName", query = "select distinct rp from ResearcherPage rp join rp.variants v where rp.academicName.value = :par0 or rp.fullName = :par0 or rp.chineseName.value = :par0 or v.value = :par0"),
        // @NamedQuery(name = "ResearcherPage.findAllResearcherByField", query =
        // "select rp from ResearcherPage rp where :par0 in indices(rp.additionalFields)"),
        @NamedQuery(name = "ResearcherPage.countAllResearcherByName", query = "select count(*) from ResearcherPage rp join rp.variants v where (rp.academicName.value = :par0 or rp.fullName = :par0 or rp.chineseName.value = :par0 or v.value = :par0)"),
        @NamedQuery(name = "ResearcherPage.countAllResearcherByNameExceptResearcher", query = "select count(*) from ResearcherPage rp join rp.variants v where (rp.academicName.value = :par0 or rp.fullName = :par0 or rp.chineseName.value = :par0 or v.value = :par0) and rp.id != :par1 "),
        @NamedQuery(name = "ResearcherPage.findAllResearcherByNamesTimestampLastModified", query = "from ResearcherPage where namesModifiedTimeStamp.timestamp >= ?"),
        @NamedQuery(name = "ResearcherPage.uniqueResearcherPageByStaffNo", query = "from ResearcherPage rp where rp.staffNo = ?"),
        @NamedQuery(name = "ResearcherPage.findAllResearcherInDateRange", query = "from ResearcherPage rp where rp.timeStampInfo.timestampCreated.timestamp between :par0 and :par1"),
        @NamedQuery(name = "ResearcherPage.findAllResearcherByCreationDateBefore", query = "from ResearcherPage rp where rp.timeStampInfo.timestampCreated.timestamp <= ?"),
        @NamedQuery(name = "ResearcherPage.findAllResearcherByCreationDateAfter", query = "from ResearcherPage rp where rp.timeStampInfo.timestampCreated.timestamp >= ?"),
        @NamedQuery(name = "ResearcherPage.findAllNextResearcherByIDStart", query = "from ResearcherPage rp where rp.id >= ?"),
        @NamedQuery(name = "ResearcherPage.findAllPrevResearcherByIDEnd", query = "from ResearcherPage rp where rp.id <= ?"),
        @NamedQuery(name = "ResearcherPage.findAllResearcherInIDRange", query = "from ResearcherPage rp where rp.id between :par0 and :par1"),
        @NamedQuery(name = "ResearcherPage.findAllNextResearcherByStaffNoStart", query = "from ResearcherPage rp where rp.staffNo >= ?"),
        @NamedQuery(name = "ResearcherPage.findAllPrevResearcherByStaffNoEnd", query = "from ResearcherPage rp where rp.staffNo <= ?"),
        @NamedQuery(name = "ResearcherPage.findAllResearcherInStaffNoRange", query = "from ResearcherPage rp where rp.staffNo between :par0 and :par1"),
        @NamedQuery(name = "ResearcherPage.findAnagraficaByRPID", query = "select dynamicField.anagrafica from ResearcherPage rp where rp.id = ?") })
public class ResearcherPage
        implements
        UUIDSupport,
        Identifiable,
        HasTimeStampInfo,
        Cloneable,
        IExportableDynamicObject<RPPropertiesDefinition, RPProperty, RPAdditionalFieldStorage>,
        AnagraficaSupport<RPProperty, RPPropertiesDefinition>
{

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
    @GeneratedValue(generator = "RESEARCHERPAGE_SEQ")
    @SequenceGenerator(name = "RESEARCHERPAGE_SEQ", sequenceName = "RESEARCHERPAGE_SEQ")
    private Integer id;

    @Column(nullable = false, unique = true)
    private String uuid;

    /** True if researcher is active */
    private Boolean status;

    /** HKU internal unique identifier of the ResearcherPage, must be not null */
    @Column(nullable = false, unique = true)
    private String staffNo;

    /** the full name of the researcher, must be not null */
    @Column(nullable = false)
    private String fullName;

    @Transient
    /**
     * The names that the ResearcherPage has when loaded from the db the first
     * time. It is useful for comparison with the current names to see if
     * changes has been made.
     * 
     */
    private String oldNames;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "academicName_value")),
            @AttributeOverride(name = "visibility", column = @Column(name = "academicName_visibility")) })
    @Cascade(value = { CascadeType.ALL })
    /**
     * the academic name 
     */
    private RestrictedField academicName;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "chineseName_value")),
            @AttributeOverride(name = "visibility", column = @Column(name = "chineseName_visibility")) })
    /**
     * the Chinese name
     */
    private RestrictedField chineseName;

    @Embedded
    @CollectionOfElements(targetElement = RestrictedField.class, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SELECT)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    /**
     * the variants form of the name (include also Japanese, Korean, etc.)
     */
    private List<RestrictedField> variants;

    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name = "value", column = @Column(name = "email_value")),
            @AttributeOverride(name = "visibility", column = @Column(name = "email_visibility")) })
    /**
     * the email
     */
    private RestrictedField email;

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
     * Getter method.
     * 
     * @return the staffNo
     */
    public String getStaffNo()
    {
        return staffNo;
    }

    /**
     * Setter method.
     * 
     * @param staffNo
     *            the staffNo
     */
    public void setStaffNo(String staffNo)
    {
        this.staffNo = staffNo;
    }

    /**
     * Getter method.
     * 
     * @return the fullName
     */
    public String getFullName()
    {
        return fullName;
    }

    /**
     * Getter method.
     * 
     * @return the academic name
     */
    public RestrictedField getAcademicName()
    {
        if (academicName == null)
        {
            academicName = new RestrictedField();
        }
        return academicName;
    }

    /**
     * Setter method.
     * 
     * @param academicName
     *            the academic name
     */
    public void setAcademicName(RestrictedField academicName)
    {
        this.academicName = academicName;
    }

    /**
     * Getter method.
     * 
     * @return the chinese name
     */
    public RestrictedField getChineseName()
    {
        if (chineseName == null)
        {
            chineseName = new RestrictedField();
        }
        return chineseName;
    }

    /**
     * Setter method.
     * 
     * @param chineseName
     *            the chinese name
     */
    public void setChineseName(RestrictedField chineseName)
    {
        this.chineseName = chineseName;
    }

    /**
     * Getter method.
     * 
     * @return the variants form of the name (include also Japanese, Korean,
     *         etc.)
     */
    public List<RestrictedField> getVariants()
    {
        if (variants == null)
        {
            variants = new LinkedList<RestrictedField>();
        }
        return variants;
    }

    /**
     * Setter method.
     * 
     * @param variants
     *            the variants form of the name (include also Japanese, Korean,
     *            etc.)
     */
    public void setVariants(List<RestrictedField> variants)
    {
        this.variants = variants;
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
        if (field == null)
        {
            throw new IllegalArgumentException("You must specified a field");
        }

        if (field.equals("fullName"))
        {
            result.add(getFullName());
        }
        else if (field.equals("chineseName")
                && getChineseName().getVisibility() == VisibilityConstants.PUBLIC)
        {
            result.add(getChineseName().getValue());
        }
        else if (field.equals("academicName")
                && getAcademicName().getVisibility() == VisibilityConstants.PUBLIC)
        {
            result.add(getAcademicName().getValue());
        }
        else if (field.equals("variants"))                
        {
            for(RestrictedField variant : getVariants()) {
                if(variant.getVisibility() == VisibilityConstants.PUBLIC) {
                    result.add(variant.getValue());                    
                }
            }            
        }
        else if (field.equals("email"))
        {
            result.add(getEmail().getValue());
        }
        else
        {
            List<RPProperty> dyna = getDynamicField().getAnagrafica4view().get(
                    field);
            for (RPProperty prop : dyna)
            {
                if (prop.getVisibility() == VisibilityConstants.PUBLIC)
                    result.add(prop.toString());
            }
        }
        return result;
    }

    @Transient
    public List<String> getAllPublicNames()
    {
        List<String> results = new ArrayList<String>();
        results.add(getFullName());
        if (getAcademicName().getValue() != null
                && !getAcademicName().getValue().isEmpty())
        {
            results.add(getAcademicName().getValue());
        }
        if (getChineseName().getValue() != null
                && !getChineseName().getValue().isEmpty())
        {
            results.add(getChineseName().getValue());
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
        if (getAcademicName().getValue() != null
                && !getAcademicName().getValue().isEmpty())
        {
            results.add(getAcademicName().getValue());
        }
        if (getChineseName().getValue() != null
                && !getChineseName().getValue().isEmpty())
        {
            results.add(getChineseName().getValue());
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

    // public ResearcherPage clone(ResearcherPage clone) {
    // clone.setAcademicName(this.academicName);
    // clone.setAddress(this.address);
    // clone.setAuthorIdLinkScopus(this.authorIdLinkScopus);
    // return null;
    // }

    @Override
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

    @Override
    public String getNamePublicIDAttribute()
    {
        return ExportConstants.NAME_PUBLICID_ATTRIBUTE;
    }

    @Override
    public String getValuePublicIDAttribute()
    {
        return "" + this.getId();
    }

    @Override
    public String getNameIDAttribute()
    {
        return ExportConstants.NAME_ID_ATTRIBUTE;
    }

    @Override
    public String getValueIDAttribute()
    {
        if (this.getUuid() == null)
        {
            return "";
        }
        return "" + this.getUuid().toString();
    }

    @Override
    public String getNameBusinessIDAttribute()
    {
        return ExportConstants.NAME_BUSINESSID_ATTRIBUTE;
    }

    @Override
    public String getValueBusinessIDAttribute()
    {
        return this.getStaffNo();
    }

    @Override
    public String getNameTypeIDAttribute()
    {
        return ExportConstants.NAME_TYPE_ATTRIBUTE;
    }

    @Override
    public String getValueTypeIDAttribute()
    {
        return "" + RP_TYPE_ID;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public String getUuid()
    {
        return uuid;
    }

    @Override
    public String getNameSingleRowElement()
    {
        return ExportConstants.ELEMENT_SINGLEROW;
    }

    @Override
    public String getIdentifyingValue()
    {
        return this.dynamicField.getIdentifyingValue();
    }

    @Override
    public String getDisplayValue()
    {
        return this.dynamicField.getDisplayValue();
    }

    @Override
    public List<RPProperty> getAnagrafica()
    {
        return this.dynamicField.getAnagrafica();
    }

    @Override
    public Map<String, List<RPProperty>> getAnagrafica4view()
    {
        return this.dynamicField.getAnagrafica4view();
    }

    @Override
    public void setAnagrafica(List<RPProperty> anagrafica)
    {
        this.dynamicField.setAnagrafica(anagrafica);
    }

    @Override
    public RPProperty createProprieta(RPPropertiesDefinition tipologiaProprieta)
            throws IllegalArgumentException
    {
        return this.dynamicField.createProprieta(tipologiaProprieta);
    }

    @Override
    public RPProperty createProprieta(
            RPPropertiesDefinition tipologiaProprieta, Integer posizione)
            throws IllegalArgumentException
    {
        return this.dynamicField.createProprieta(tipologiaProprieta, posizione);
    }

    @Override
    public boolean removeProprieta(RPProperty proprieta)
    {
        return this.dynamicField.removeProprieta(proprieta);
    }

    @Override
    public List<RPProperty> getProprietaDellaTipologia(
            RPPropertiesDefinition tipologiaProprieta)
    {
        return this.dynamicField.getProprietaDellaTipologia(tipologiaProprieta);
    }

    @Override
    public Class<RPProperty> getClassProperty()
    {
        return this.dynamicField.getClassProperty();
    }

    @Override
    public Class<RPPropertiesDefinition> getClassPropertiesDefinition()
    {
        return this.dynamicField.getClassPropertiesDefinition();
    }

    @Override
    public void inizializza()
    {
        this.dynamicField.inizializza();
    }

    @Override
    public void invalidateAnagraficaCache()
    {
        this.dynamicField.invalidateAnagraficaCache();
    }

    @Override
    public void pulisciAnagrafica()
    {
        this.dynamicField.pulisciAnagrafica();
    }

    public void setEmail(RestrictedField email)
    {
        this.email = email;
    }

    public RestrictedField getEmail()
    {
        return email;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public int getEPersonID()
    {
        // TODO Auto-generated method stub
        return 0;
    }

}
