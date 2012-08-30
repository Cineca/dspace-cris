/**
 * <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
 * <html><head>
 * <title>301 Moved Permanently</title>
 * </head><body>
 * <h1>Moved Permanently</h1>
 * <p>The document has moved <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>.</p>
 * </body></html>
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.log4j.Logger;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Index;

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
@NamedQueries( {
        @NamedQuery(name = "ResearcherPage.findAll", query = "from ResearcherPage order by id"),
        @NamedQuery(name = "ResearcherPage.paginate.id.asc", query = "from ResearcherPage order by id asc"),
        @NamedQuery(name = "ResearcherPage.paginate.id.desc", query = "from ResearcherPage order by id desc"),
        @NamedQuery(name = "ResearcherPage.paginate.academicName.asc", query = "from ResearcherPage order by academicName.value asc"),
        @NamedQuery(name = "ResearcherPage.paginate.academicName.desc", query = "from ResearcherPage order by academicName.value desc"),
        @NamedQuery(name = "ResearcherPage.paginate.chineseName.asc", query = "from ResearcherPage order by chineseName.value asc"),
        @NamedQuery(name = "ResearcherPage.paginate.chineseName.desc", query = "from ResearcherPage order by chineseName.value desc"),
        @NamedQuery(name = "ResearcherPage.paginate.dept.asc", query = "from ResearcherPage order by dept.value asc"),
        @NamedQuery(name = "ResearcherPage.paginate.dept.desc", query = "from ResearcherPage order by dept.value desc"),
        @NamedQuery(name = "ResearcherPage.paginate.status.asc", query = "from ResearcherPage order by status asc"),
        @NamedQuery(name = "ResearcherPage.paginate.status.desc", query = "from ResearcherPage order by status desc"),
        @NamedQuery(name = "ResearcherPage.paginate.staffNo.asc", query = "from ResearcherPage order by staffNo asc"),
        @NamedQuery(name = "ResearcherPage.paginate.staffNo.desc", query = "from ResearcherPage order by staffNo desc"),
        @NamedQuery(name = "ResearcherPage.paginate.fullName.asc", query = "from ResearcherPage order by fullName asc"),
        @NamedQuery(name = "ResearcherPage.paginate.fullName.desc", query = "from ResearcherPage order by fullName desc"),
        @NamedQuery(name = "ResearcherPage.count", query = "select count(*) from ResearcherPage"),
        @NamedQuery(name = "ResearcherPage.findAllResearcherPageByStatus", query = "from ResearcherPage where status = ? order by id"),
        @NamedQuery(name = "ResearcherPage.findAllResearcherByName", query = "select distinct rp from ResearcherPage rp join rp.variants v where rp.academicName.value = :par0 or rp.fullName = :par0 or rp.chineseName.value = :par0 or v.value = :par0"),
        //@NamedQuery(name = "ResearcherPage.findAllResearcherByField", query = "select rp from ResearcherPage rp where :par0 in indices(rp.additionalFields)"),
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
        @NamedQuery(name = "ResearcherPage.findAnagraficaByRPID", query = "select dynamicField.anagrafica from ResearcherPage rp where rp.id = ?")
})

public class ResearcherPage implements UUIDSupport, Identifiable, HasTimeStampInfo, Cloneable, IExportableDynamicObject<RPPropertiesDefinition, RPProperty, RPAdditionalFieldStorage>
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
    @DocumentId
    private Integer id;
    
    @Column(nullable = false, unique = true)
    private String uuid;

    /** True if researcher is active */
    private Boolean status;

    /** HKU internal unique identifier of the ResearcherPage, must be not null */
    @Column(nullable = false, unique = true)
    @Fields(value = { @Field(index = Index.TOKENIZED, name = "default") })
    private String staffNo;

    /** the full name of the researcher, must be not null */
    @Column(nullable = false)
    @Fields(value = { @Field(index = Index.TOKENIZED, name = "default"),
            @Field(index = Index.TOKENIZED, name = "names") })
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
    @AttributeOverrides( {
            @AttributeOverride(name = "value", column = @Column(name = "honorific_value")),
            @AttributeOverride(name = "visibility", column = @Column(name = "honorific_visibility")) })
    @Cascade(value = { CascadeType.ALL })
    /**
     * the honorific 
     */
    private RestrictedField honorific;

    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name = "value", column = @Column(name = "academicName_value")),
            @AttributeOverride(name = "visibility", column = @Column(name = "academicName_visibility")) })
    @Cascade(value = { CascadeType.ALL })
    /**
     * the academic name 
     */
    private RestrictedField academicName;
    
    @Embedded
    @AttributeOverrides( {
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
     * the title
     */
    private List<RestrictedField> title;

    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name = "value", column = @Column(name = "dept_value")),
            @AttributeOverride(name = "visibility", column = @Column(name = "dept_visibility")) })    
    /**
     * the department
     */
    private RestrictedField dept;

    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name = "value", column = @Column(name = "address_value")),
            @AttributeOverride(name = "visibility", column = @Column(name = "address_visibility")) })
    /**
     * the address
     */
    private RestrictedField address;

    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name = "value", column = @Column(name = "officeTel_value")),
            @AttributeOverride(name = "visibility", column = @Column(name = "officeTel_visibility")) })
    /**
     * the office telephone number
     */
    private RestrictedField officeTel;

    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name = "value", column = @Column(name = "email_value")),
            @AttributeOverride(name = "visibility", column = @Column(name = "email_visibility")) })
    /**
     * the email
     */
    private RestrictedField email;

    /**
     * url of a webpage where check for a picture. Only for internal staff not
     * used in public view
     */  
    @Type(type = "text")
    private String urlPict;

    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name = "value", column = @Column(name = "pict_ext")),
            @AttributeOverride(name = "mimeType", column = @Column(name = "pict_type")),
            @AttributeOverride(name = "visibility", column = @Column(name = "pict_visibility")) })
    /**
     * the picture to show on the public page 
     */
    private RestrictedFieldFile pict;

    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name = "value", column = @Column(name = "cv_ext")),
            @AttributeOverride(name = "remoteUrl", column = @Column(name = "cv_url")),            
            @AttributeOverride(name = "mimeType", column = @Column(name = "cv_type")),
            @AttributeOverride(name = "visibility", column = @Column(name = "cv_visibility")) })
    /**
     * the CV to show on the public page 
     */
    private RestrictedFieldLocalOrRemoteFile cv;
    
    @Deprecated
    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name = "value", column = @Column(name = "bio_value")),
            @AttributeOverride(name = "visibility", column = @Column(name = "bio_visibility")) })
    /**
     * the url where find the researcher biography
     * 
     * @Deprecated changed to dynamic field (shortname=myurls)
     */
    private RestrictedField bio;

    @Deprecated    
    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name = "value", column = @Column(name = "personal_value")),
            @AttributeOverride(name = "visibility", column = @Column(name = "personal_visibility")) })
    /**
     * the url to the personal page of the researcher
     * 
     * @Deprecated changed to dynamic field (shortname=myurls)
     */
    private RestrictedField urlPersonal;
    
    @Deprecated    
    @Embedded
    @CollectionOfElements(targetElement = RestrictedField.class, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SELECT)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    /**
     * the area of expertise list
     * @Deprecated changed to dynamic field (shortname=expertise (complex field)) 
     */
    private List<RestrictedField> media;

    @Embedded
    @CollectionOfElements(targetElement = RestrictedField.class, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SELECT)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
   /**
    * the variants form of the name (include also Japanese, Korean, etc.)
    */
   private List<RestrictedField> variants;

    @Embedded
    @CollectionOfElements(targetElement = RestrictedField.class, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SELECT)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    /**
     * the interests list
     */
    private List<RestrictedField> interests;

    @Deprecated
    @Embedded
    @CollectionOfElements(targetElement = RestrictedField.class, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SELECT)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    /**
     * the list of spoken languages in English form
     * @Deprecated changed to dynamic field (shortname=spokenEN (children of 'spoken' complex field))
     */
    private List<RestrictedField> spokenLanguagesEN;

    @Deprecated
    @Embedded
    @CollectionOfElements(targetElement = RestrictedField.class, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SELECT)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })    
    /**
     * the list of spoken languages in Chinese form
     * @Deprecated changed to dynamic field (shortname=spokenZH (children of 'spoken' complex field))
     */
    private List<RestrictedField> spokenLanguagesZH;

    @Deprecated
    @Embedded
    @CollectionOfElements(targetElement = RestrictedField.class, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SELECT)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    /**
     * the list of written languages in English form
     */
    private List<RestrictedField> writtenLanguagesEN;

    @Deprecated
    @Embedded
    @CollectionOfElements(targetElement = RestrictedField.class, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SELECT)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })    
    /**
     * the list of written languages in Chinese form
     */
    private List<RestrictedField> writtenLanguagesZH;

    // fields that come from the bibliometric excel data...
    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name = "value", column = @Column(name = "ridISI_value")),
            @AttributeOverride(name = "visibility", column = @Column(name = "ridISI_visibility")) })    
    /**
     * ISI Researcher ID
     */
    private RestrictedField ridISI;

    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name = "value", column = @Column(name = "ridLinkISI_value")),
            @AttributeOverride(name = "visibility", column = @Column(name = "ridLinkISI_visibility"))
    }) 
    /**
     * Link to ISI RID page
     */
    private RestrictedField ridLinkISI;

    /**
     * ISI Document Count 
     */
    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name = "value", column = @Column(name = "paperCountISI_value")),
            @AttributeOverride(name = "visibility", column = @Column(name = "paperCountISI_visibility")) })     
    private RestrictedField paperCountISI;

    /**
     * ISI Link to Document Count  page
     */
    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name = "value", column = @Column(name = "paperLinkISI_value")),
            @AttributeOverride(name = "visibility", column = @Column(name = "paperLinkISI_visibility")) })
    private RestrictedField paperLinkISI;

    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name = "value", column = @Column(name = "citationCountISI_value")),
            @AttributeOverride(name = "visibility", column = @Column(name = "citationCountISI_visibility")) })
    /**
     * ISI Times Cited 
     */            
    private RestrictedField citationCountISI;

    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name = "value", column = @Column(name = "citationLinkISI_value")),
            @AttributeOverride(name = "visibility", column = @Column(name = "citationLinkISI_visibility")) })
    /**
     * Link to ISI Times Cited page
     */                       
    private RestrictedField citationLinkISI;

    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name = "value", column = @Column(name = "coAuthorsISI_value")),
            @AttributeOverride(name = "visibility", column = @Column(name = "coAuthorsISI_visibility")) })
    /**
     * ISI Co-Authors 
     */                       
    private RestrictedField coAuthorsISI;

    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name = "value", column = @Column(name = "hindexISI_value")),
            @AttributeOverride(name = "visibility", column = @Column(name = "hindexISI_visibility")) })
    /**
     * ISI h-index
     */                      
    private RestrictedField hindexISI;

    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name = "value", column = @Column(name = "authorIdScopus_value")),
            @AttributeOverride(name = "visibility", column = @Column(name = "authorIdScopus_visibility")) })    
    /**
     * Scopus AuthorID 
     */                          
    private RestrictedField authorIdScopus;

    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name = "value", column = @Column(name = "authorIdLinkScopus_value")),
            @AttributeOverride(name = "visibility", column = @Column(name = "authorIdLinkScopus_visibility")) })
    /**
     * Link to Scopus AuthorID page
     */            
    private RestrictedField authorIdLinkScopus;

    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name = "value", column = @Column(name = "paperCountScopus_value")),
            @AttributeOverride(name = "visibility", column = @Column(name = "paperCountScopus_visibility")) })
    /**
     * Scopus Document count 
     */            
    private RestrictedField paperCountScopus;

    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name = "value", column = @Column(name = "paperLinkScopus_value")),
            @AttributeOverride(name = "visibility", column = @Column(name = "paperLinkScopus_visibility")) })
    /**
     * Link to Scopus Document count page
     */                        
    private RestrictedField paperLinkScopus;

    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name = "value", column = @Column(name = "citationCountScopus_value")),
            @AttributeOverride(name = "visibility", column = @Column(name = "citationCountScopus_visibility")) })
    /**
     * Scopus Cited By 
     */                    
    private RestrictedField citationCountScopus;

    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name = "value", column = @Column(name = "citationLinkScopus_value")),
            @AttributeOverride(name = "visibility", column = @Column(name = "citationLinkScopus_visibility")) })
    /**
     * Link to Scopus Cited By 
     */              
    private RestrictedField citationLinkScopus;

    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name = "value", column = @Column(name = "coAuthorsScopus_value")),
            @AttributeOverride(name = "visibility", column = @Column(name = "coAuthorsScopus_visibility")) })
    /**
     * Scopus Co-Authors
     */      
    private RestrictedField coAuthorsScopus;

    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name = "value", column = @Column(name = "coAuthorsLinkScopus_value")),
            @AttributeOverride(name = "visibility", column = @Column(name = "coAuthorsLinkScopus_visibility")) })
    /**
     * Link to Scopus Co-Authors page
     */                  
    private RestrictedField coAuthorsLinkScopus;

    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name = "value", column = @Column(name = "hindexScopus_value")),
            @AttributeOverride(name = "visibility", column = @Column(name = "hindexScopus_visibility")) })
    /**
     * Scopus h-index
     */          
    private RestrictedField hindexScopus;

    // fields outside the "standard" excel file

    /**
     * Map of additional custom data 
     */
	@OneToOne
	@Cascade(value = { CascadeType.ALL })
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
     * @return the fullName
     */
    public String getFullName()
    {
        return fullName;
    }

    /**
     * Setter method.
     * 
     * @param fullName
     *            the full name
     */
    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    /**
     * Getter method.
     * @return the honorific
     */
    public RestrictedField getHonorific()
    {
        if (honorific == null)
        {
            honorific = new RestrictedField();
        }
        return honorific;
    }

    /**
     * Setter method.
     * 
     * @param honorific
     *            the honorific
     */
    public void setHonorific(RestrictedField honorific)
    {
        this.honorific = honorific;
    }

    /**
     * Getter method.
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
     * @return the title's list
     */
    public List<RestrictedField> getTitle()
    {
        if (title == null)
        {
            title = new LinkedList<RestrictedField>();
        }
        return title;
    }

    /**
     * Setter method.
     * 
     * @param title
     *            the title's list
     */    
    public void setTitle(List<RestrictedField> title)
    {
        this.title = title;
    }

    /**
     * Getter method.
     * @return the department
     */
    public RestrictedField getDept()
    {
        if (dept == null)
        {
            dept = new RestrictedField();
        }
        return dept;
    }

    /**
     * Setter method.
     * 
     * @param dept
     *            the department
     */
    public void setDept(RestrictedField dept)
    {
        this.dept = dept;
    }

    /**
     * Getter method.
     * @return the address
     */
    public RestrictedField getAddress()
    {
        if (address == null)
        {
            address = new RestrictedField();
        }
        return address;
    }

    /**
     * Setter method.
     * 
     * @param address
     *            the address
     */
    public void setAddress(RestrictedField address)
    {
        this.address = address;
    }

    /**
     * Getter method.
     * @return the office telephone number
     */
    public RestrictedField getOfficeTel()
    {
        if (officeTel == null)
        {
            officeTel = new RestrictedField();
        }
        return officeTel;
    }

    /**
     * Setter method.
     * 
     * @param officeTel
     *            the office telephone number
     */
    public void setOfficeTel(RestrictedField officeTel)
    {
        this.officeTel = officeTel;
    }

    /**
     * Getter method.
     * @return the email
     */
    public RestrictedField getEmail()
    {
        if (email == null)
        {
            email = new RestrictedField();
        }
        return email;
    }

    /**
     * Setter method.
     * 
     * @param email
     *            the email
     */
    public void setEmail(RestrictedField email)
    {
        this.email = email;
    }

    /**
     * Getter method.
     * @return the url of a webpage where check for a picture
     */
    public String getUrlPict()
    {
        return urlPict;
    }

    /**
     * Setter method.
     * 
     * @param urlPict
     *            the url of a webpage where check for a picture
     */
    public void setUrlPict(String urlPict)
    {
        this.urlPict = urlPict;
    }

    /**
     * Getter method.
     * @return the picture to show on the public page 
     */
    public RestrictedFieldFile getPict()
    {
        if (pict == null)
        {
            pict = new RestrictedFieldFile();
        }
        return pict;
    }

    /**
     * Setter method.
     * 
     * @param pict
     *            the picture to show on the public page 
     */
    public void setPict(RestrictedFieldFile pict)
    {
        this.pict = pict;
    }

    /**
     * Getter method.
     * @return the picture to show on the public page 
     */
    public RestrictedFieldLocalOrRemoteFile getCv()
    {
        if (cv == null)
        {
            cv = new RestrictedFieldLocalOrRemoteFile();
        }
        return cv;
    }

    /**
     * Setter method.
     * 
     * @param pict
     *            the picture to show on the public page 
     */
    public void setCv(RestrictedFieldLocalOrRemoteFile cv)
    {
        this.cv = cv;
    }
    
    /**
     * Getter method.
     * @return the biography
     */
    public RestrictedField getBio()
    {
        if (bio == null)
        {
            bio = new RestrictedField();
        }
        return bio;
    }

    /**
     * Setter method.
     * 
     * @param biography
     *            the biography
     */
    public void setBio(RestrictedField bio)
    {
        this.bio = bio;
    }

    /**
     * Getter method.
     * @return the variants form of the name (include also Japanese, Korean, etc.)
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
     *            the variants form of the name (include also Japanese, Korean, etc.)
     */
    public void setVariants(List<RestrictedField> variants)
    {
        this.variants = variants;
    }


    /**
     * Getter method.
     * @return the interests
     */
    public List<RestrictedField> getInterests()
    {
        if (interests == null)
        {
            interests = new LinkedList<RestrictedField>();
        }
        return interests;
    }

    /**
     * Setter method.
     * 
     * @param interests
     *            interests
     */
    public void setInterests(List<RestrictedField> interests)
    {
        this.interests = interests;
    }

    /**
     * Getter method.
     * @return the ISI Researcher ID
     */
    public RestrictedField getRidISI()
    {
        if (ridISI == null)
        {
            ridISI = new RestrictedField();
        }
        return ridISI;
    }

    /**
     * Setter method.
     * 
     * @param ridISI
     *            the ISI Researcher ID
     */
    public void setRidISI(RestrictedField ridISI)
    {
        this.ridISI = ridISI;
    }

    /**
     * Getter method.
     * @return the link to ISI RID page
     */
    public RestrictedField getRidLinkISI()
    {
        if (ridLinkISI == null)
        {
            ridLinkISI = new RestrictedField();
        }
        return ridLinkISI;
    }

    /**
     * Setter method.
     * 
     * @param ridLinkISI
     *            the link to ISI RID page
     */
    public void setRidLinkISI(RestrictedField ridLinkISI)
    {
        this.ridLinkISI = ridLinkISI;
    }

    /**
     * Getter method.
     * @return the ISI Document Count 
     */
    public RestrictedField getPaperCountISI()
    {
        if (paperCountISI == null)
        {
            paperCountISI = new RestrictedField();
        }
        return paperCountISI;
    }

    /**
     * Setter method.
     * 
     * @param paperCountISI
     *            the ISI Document Count 
     */
    public void setPaperCountISI(RestrictedField paperCountISI)
    {
        this.paperCountISI = paperCountISI;
    }

    /**
     * Getter method.
     * @return the ISI Link to Document Count page
     */
    public RestrictedField getPaperLinkISI()
    {
        if (paperLinkISI == null)
        {
            paperLinkISI = new RestrictedField();
        }
        return paperLinkISI;
    }

    /**
     * Setter method.
     * 
     * @param paperLinkISI
     *            the ISI Link to Document Count page
     */
    public void setPaperLinkISI(RestrictedField paperLinkISI)
    {
        this.paperLinkISI = paperLinkISI;
    }

    /**
     * Getter method.
     * @return the ISI Times Cited 
     */
    public RestrictedField getCitationCountISI()
    {
        if (citationCountISI == null)
        {
            citationCountISI = new RestrictedField();
        }
        return citationCountISI;
    }

    /**
     * Setter method.
     * 
     * @param citationCountISI
     *            the ISI Times Cited 
     */
    public void setCitationCountISI(RestrictedField citationCountISI)
    {
        this.citationCountISI = citationCountISI;
    }

    /**
     * Getter method.
     * @return the link to ISI Times Cited page
     */
    public RestrictedField getCitationLinkISI()
    {
        if (citationLinkISI == null)
        {
            citationLinkISI = new RestrictedField();
        }
        return citationLinkISI;
    }

    /**
     * Setter method.
     * 
     * @param citationLinkISI
     *            the link to ISI Times Cited page
     */
    public void setCitationLinkISI(RestrictedField citationLinkISI)
    {
        this.citationLinkISI = citationLinkISI;
    }

    /**
     * Getter method.
     * @return the ISI Co-Authors 
     */
    public RestrictedField getCoAuthorsISI()
    {
        if (coAuthorsISI == null)
        {
            coAuthorsISI = new RestrictedField();
        }
        return coAuthorsISI;
    }

    /**
     * Setter method.
     * 
     * @param coAuthorsISI
     *            the ISI Co-Authors 
     */
    public void setCoAuthorsISI(RestrictedField coAuthorsISI)
    {
        this.coAuthorsISI = coAuthorsISI;
    }

    /**
     * Getter method.
     * @return the ISI h-index
     */
    public RestrictedField getHindexISI()
    {
        if (hindexISI == null)
        {
            hindexISI = new RestrictedField();
        }
        return hindexISI;
    }

    /**
     * Setter method.
     * 
     * @param hindexISI
     *            the ISI h-index
     */
    public void setHindexISI(RestrictedField hindexISI)
    {
        this.hindexISI = hindexISI;
    }

    /**
     * Getter method.
     * @return the Scopus AuthorID 
     */
    public RestrictedField getAuthorIdScopus()
    {
        if (authorIdScopus == null)
        {
            authorIdScopus = new RestrictedField();
        }
        return authorIdScopus;
    }

    /**
     * Setter method.
     * 
     * @param authorIdScopus
     *            the Scopus AuthorID 
     */
    public void setAuthorIdScopus(RestrictedField authorIdScopus)
    {
        this.authorIdScopus = authorIdScopus;
    }

    /**
     * Getter method.
     * @return the link to Scopus AuthorID page
     */
    public RestrictedField getAuthorIdLinkScopus()
    {
        if (authorIdLinkScopus == null)
        {
            authorIdLinkScopus = new RestrictedField();
        }
        return authorIdLinkScopus;
    }

    /**
     * Setter method.
     * 
     * @param authorIdLinkScopus
     *            the link to Scopus AuthorID page
     */
    public void setAuthorIdLinkScopus(RestrictedField authorIdLinkScopus)
    {
        this.authorIdLinkScopus = authorIdLinkScopus;
    }

    /**
     * Getter method.
     * @return  the Scopus document count
     */
    public RestrictedField getPaperCountScopus()
    {
        if (paperCountScopus == null)
        {
            paperCountScopus = new RestrictedField();
        }
        return paperCountScopus;
    }

    /**
     * Setter method.
     * 
     * @param paperCountScopus
     *            the Scopus document count
     */
    public void setPaperCountScopus(RestrictedField paperCountScopus)
    {
        this.paperCountScopus = paperCountScopus;
    }

    /**
     * Getter method.
     * @return the link to Scopus Document count page
     */
    public RestrictedField getPaperLinkScopus()
    {
        if (paperLinkScopus == null)
        {
            paperLinkScopus = new RestrictedField();
        }
        return paperLinkScopus;
    }

    /**
     * Setter method.
     * 
     * @param paperLinkScopus
     *            the link to Scopus Document count page
     */
    public void setPaperLinkScopus(RestrictedField paperLinkScopus)
    {
        this.paperLinkScopus = paperLinkScopus;
    }
    
    /**
     * Getter method.
     * @return the Scopus Cited By
     */
    public RestrictedField getCitationCountScopus()
    {
        if (citationCountScopus == null)
        {
            citationCountScopus = new RestrictedField();
        }
        return citationCountScopus;
    }

    /**
     * Setter method.
     * 
     * @param citationCountScopus
     *            the Scopus Cited By
     */
    public void setCitationCountScopus(RestrictedField citationCountScopus)
    {
        this.citationCountScopus = citationCountScopus;
    }

    /**
     * Getter method.
     * @return the link to Scopus citation page 
     */
    public RestrictedField getCitationLinkScopus()
    {
        if (citationLinkScopus == null)
        {
            citationLinkScopus = new RestrictedField();
        }
        return citationLinkScopus;
    }

    /**
     * Setter method.
     * 
     * @param citationLinkScopus
     *            the link to Scopus citation page 
     */
    public void setCitationLinkScopus(RestrictedField citationLinkScopus)
    {
        this.citationLinkScopus = citationLinkScopus;
    }

    /**
     * Getter method.
     * @return the Scopus co-Author(s)
     */
    public RestrictedField getCoAuthorsScopus()
    {
        if (coAuthorsScopus == null)
        {
            coAuthorsScopus = new RestrictedField();
        }
        return coAuthorsScopus;
    }

    /**
     * Setter method.
     * 
     * @param coAuthorsScopus
     *            the Scopus co-Author(s)
     */
    public void setCoAuthorsScopus(RestrictedField coAuthorsScopus)
    {
        this.coAuthorsScopus = coAuthorsScopus;
    }

    /**
     * Getter method.
     * @return the the link to Scopus Co-Authors page
     */
    public RestrictedField getCoAuthorsLinkScopus()
    {
        if (coAuthorsLinkScopus == null)
        {
            coAuthorsLinkScopus = new RestrictedField();
        }
        return coAuthorsLinkScopus;
    }

    /**
     * Setter method.
     * 
     * @param coAuthorsLinkScopus
     *            the link to Scopus Co-Authors page
     */
    public void setCoAuthorsLinkScopus(RestrictedField coAuthorsLinkScopus)
    {
        this.coAuthorsLinkScopus = coAuthorsLinkScopus;
    }

    /**
     * Getter method.
     * @return the Scopus h-index
     */
    public RestrictedField getHindexScopus()
    {
        if (hindexScopus == null)
        {
            hindexScopus = new RestrictedField();
        }
        return hindexScopus;
    }

    /**
     * Setter method.
     * 
     * @param hindexScopus
     *            the Scopus h-index
     */
    public void setHindexScopus(RestrictedField hindexScopus)
    {
        this.hindexScopus = hindexScopus;
    }

    /**
     * Getter method.
     * @return the url to the personal page of the researcher
     */
    public RestrictedField getUrlPersonal()
    {
        if (urlPersonal == null)
        {
            urlPersonal = new RestrictedField();
        }
        return urlPersonal;
    }

    /**
     * Setter method.
     * 
     * @param urlPersonal
     *            the url to the personal page of the researcher
     */
    public void setUrlPersonal(RestrictedField urlPersonal)
    {
        this.urlPersonal = urlPersonal;
    }

    /**
     * Getter method.
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
     * @return the area of expertise list 
     */
    public List<RestrictedField> getMedia()
    {
        if (media == null)
        {
            media = new LinkedList<RestrictedField>();
        }
        return media;
    }

    /**
     * Setter method.
     * 
     * @param media
     *            the area of expertise list 
     */
    public void setMedia(List<RestrictedField> media)
    {
        this.media = media;
    }

    /**
     * Getter method.
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
     * @return the list of spoken languages in English form
     */
    public List<RestrictedField> getSpokenLanguagesEN()
    {
        if (spokenLanguagesEN == null)
        {
            spokenLanguagesEN = new LinkedList<RestrictedField>();
        }
        return spokenLanguagesEN;
    }

    /**
     * Setter method.
     * 
     * @param spokenLanguagesEN
     *            the list of spoken languages in English form
     */
    public void setSpokenLanguagesEN(List<RestrictedField> spokenLanguagesEN)
    {
        this.spokenLanguagesEN = spokenLanguagesEN;
    }

    /**
     * Getter method.
     * @return the list of spoken languages in Chinese form
     */
    public List<RestrictedField> getSpokenLanguagesZH()
    {
        if (spokenLanguagesZH == null)
        {
            spokenLanguagesZH = new LinkedList<RestrictedField>();
        }
        return spokenLanguagesZH;
    }

    /**
     * Setter method.
     * 
     * @param spokenLanguagesZH
     *            the list of spoken languages in Chinese form
     */
    public void setSpokenLanguagesZH(List<RestrictedField> spokenLanguagesZH)
    {
        this.spokenLanguagesZH = spokenLanguagesZH;
    }

    /**
     * Getter method.
     * @return the list of written languages in English form
     */
    public List<RestrictedField> getWrittenLanguagesEN()
    {
        if (writtenLanguagesEN == null)
        {
            writtenLanguagesEN = new LinkedList<RestrictedField>();
        }
        return writtenLanguagesEN;
    }

    /**
     * Setter method.
     * 
     * @param writtenLanguagesEN
     *            the list of written languages in English form
     */
    public void setWrittenLanguagesEN(List<RestrictedField> writtenLanguagesEN)
    {
        this.writtenLanguagesEN = writtenLanguagesEN;
    }

    /**
     * Getter method.
     * @return the list of written languages in Chinese form
     */
    public List<RestrictedField> getWrittenLanguagesZH()
    {
        if (writtenLanguagesZH == null)
        {
            writtenLanguagesZH = new LinkedList<RestrictedField>();
        }
        return writtenLanguagesZH;
    }

    /**
     * Setter method.
     * 
     * @param writtenLanguagesZH
     *            the list of written languages in Chinese form
     */
    public void setWrittenLanguagesZH(List<RestrictedField> writtenLanguagesZH)
    {
        this.writtenLanguagesZH = writtenLanguagesZH;
    }

    /**
     * Getter method.
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
    public void setNamesModifiedTimeStamp(SingleTimeStampInfo namesModifiedTimeStamp)
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

	public void setDynamicField(RPAdditionalFieldStorage dynamicField) {
		this.dynamicField = dynamicField;
	}

	public RPAdditionalFieldStorage getDynamicField() {
		if(this.dynamicField == null) {
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
	 * PARTIALLY IMPLEMENTED: works only on dept and any not nested dynamic fields
	 * 
	 * @param dcField
	 *            the field (not null) to retrieve the value
	 * @return a list of 0, 1 or more values
	 */
    public List<String> getMetadata(String dcField)
    {
        List<String> result = new ArrayList();
        if (dcField == null)
        {
            throw new IllegalArgumentException("You must specified a field");
        }
        if (dcField.equals("dept")
                && getDept().getVisibility() == VisibilityConstants.PUBLIC)
        {
            result.add(getDept().getValue());
        }
        else
        {
            List<RPProperty> dyna = getDynamicField().getAnagrafica4view().get(
                    dcField);
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

//	public ResearcherPage clone(ResearcherPage clone) {
//		clone.setAcademicName(this.academicName);
//		clone.setAddress(this.address);
//		clone.setAuthorIdLinkScopus(this.authorIdLinkScopus);
//		return null;
//	}
    
    @Override
    public Object clone() throws CloneNotSupportedException {
    	return super.clone();
    }

	public void setInternalRP(boolean internalRP) {
		this.internalRP = internalRP;
	}

	public boolean isInternalRP() {
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
        return ""+this.getId();
    }

    @Override
    public String getNameIDAttribute()
    {
        return ExportConstants.NAME_ID_ATTRIBUTE;
    }

    @Override
    public String getValueIDAttribute()
    {       
        if(this.getUuid()==null) {
            return "";
        }
        return ""+this.getUuid().toString();
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
        return ""+RP_TYPE_ID;
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

    
     
}
