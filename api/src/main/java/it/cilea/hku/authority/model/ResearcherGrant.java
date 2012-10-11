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

import it.cilea.hku.authority.model.dynamicfield.GrantPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.GrantProperty;
import it.cilea.hku.authority.model.dynamicfield.ProjectAdditionalFieldStorage;
import it.cilea.hku.authority.model.export.ExportConstants;
import it.cilea.osd.common.core.HasTimeStampInfo;
import it.cilea.osd.common.core.TimeStampInfo;
import it.cilea.osd.common.model.Identifiable;
import it.cilea.osd.jdyna.model.AnagraficaSupport;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;



@Entity
@Table(name = "model_researcher_grant")
@NamedQueries( {
        @NamedQuery(name = "ResearcherGrant.findAll", query = "from ResearcherGrant order by id"),
        @NamedQuery(name = "ResearcherGrant.count", query = "select count(*) from ResearcherGrant"),
        @NamedQuery(name = "ResearcherGrant.paginate.id.asc", query = "from ResearcherGrant order by id asc"),
        @NamedQuery(name = "ResearcherGrant.paginate.id.desc", query = "from ResearcherGrant order by id desc"),
        @NamedQuery(name = "ResearcherGrant.uniqueRGByCode", query = "from ResearcherGrant where rgCode = ? order by id desc")        
})
public class ResearcherGrant implements Identifiable, UUIDSupport, HasTimeStampInfo, Cloneable, IExportableDynamicObject<GrantPropertiesDefinition, GrantProperty, ProjectAdditionalFieldStorage>, AnagraficaSupport<GrantProperty, GrantPropertiesDefinition> {

	@Transient
	/**
	 * Constant for resource type assigned to the Researcher Grants
	 */
	public static final int GRANT_TYPE_ID = 10;
	
	 /** DB Primary key */
    @Id
    @GeneratedValue(generator = "RESEARCHERGRANT_SEQ")
    @SequenceGenerator(name = "RESEARCHERGRANT_SEQ", sequenceName = "RESEARCHERGRANT_SEQ")    
    private Integer id;
    
    @Column(nullable = false, unique = true)
    private String uuid;
    
    /** timestamp info for creation and last modify */
    @Embedded
    private TimeStampInfo timeStampInfo;
    
    @Embedded
    private Investigator investigator;
        
    @CollectionOfElements
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name="model_grant_investigator")    
    private List<Investigator> coInvestigators;
    

    /**
     * Map of additional custom data
     */
    @Embedded
    private ProjectAdditionalFieldStorage dynamicField;
    
    
    /** True if grant is active */
    private Boolean status;
    
    /** Grant code */
    private String rgCode;
		

	public void setInvestigator(Investigator investigator) {
		this.investigator = investigator;
	}

	public Investigator getInvestigator() {
		if(this.investigator==null) {
			this.investigator=new Investigator();
		}
		return investigator;
	}

	public void setCoInvestigators(List<Investigator> coInvestigators) {		
		this.coInvestigators = coInvestigators;
	}

	public List<Investigator> getCoInvestigators() {
		if(this.coInvestigators==null) {
			this.coInvestigators = new LinkedList<Investigator>();
		}
		return coInvestigators;
	}

    /**
     * Getter method.
     * @return the timestamp of creation and last modify of this ResearcherGrant
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
     *            the status of this ResearcherGrant
     */
    public void setStatus(Boolean status)
    {
        this.status = status;
    }

	public void setRgCode(String rgCode) {
		this.rgCode = rgCode;
	}

	public String getRgCode() {
		return rgCode;
	}

	
    @Override
    public Object clone() throws CloneNotSupportedException {
    	return super.clone();
    }
    
    public String getTitle() {
    	String result = "";
    	for(GrantProperty title : this.getDynamicField().getAnagrafica4view().get("projecttitle")) {
    		result += title.getValue().getObject();
    		result += " ";
    	}
    	return result;
    }
    
    public String getYear() {
    	String result = "";
    	for(GrantProperty year : this.getDynamicField().getAnagrafica4view().get("fundingyear")) {
    		result += year.getValue().getObject();
    		result += " ";
    	}
    	return result;
    }
    
    public String getInvestigatorToDisplay() {
    	Investigator inv = getInvestigator();
    	if(inv!=null) {
    		if(inv.getIntInvestigator()!=null) {
    			return inv.getIntInvestigator().getFullName();
    		}
    		else {
    			return inv.getExtInvestigator();
    		}
    	}
    	return "";
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
        return this.getRgCode();
    }

    @Override
    public String getNameTypeIDAttribute()
    {
        return ExportConstants.NAME_TYPE_ATTRIBUTE;
    }

    @Override
    public String getValueTypeIDAttribute()
    {
        return ""+GRANT_TYPE_ID;
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
    public ProjectAdditionalFieldStorage getDynamicField()
    {
        return dynamicField;
    }

    public void setDynamicField(ProjectAdditionalFieldStorage dynamicField)
    {
        this.dynamicField = dynamicField;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
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
    public List<GrantProperty> getAnagrafica()
    {
        return this.dynamicField.getAnagrafica();
    }

    @Override
    public Map<String, List<GrantProperty>> getAnagrafica4view()
    {
        return this.dynamicField.getAnagrafica4view();
    }

    @Override
    public void setAnagrafica(List<GrantProperty> anagrafica)
    {
        this.dynamicField.setAnagrafica(anagrafica);
    }

    @Override
    public GrantProperty createProprieta(
            GrantPropertiesDefinition tipologiaProprieta)
            throws IllegalArgumentException
    {
        return this.dynamicField.createProprieta(tipologiaProprieta);
    }

    @Override
    public GrantProperty createProprieta(
            GrantPropertiesDefinition tipologiaProprieta, Integer posizione)
            throws IllegalArgumentException
    {
        return this.dynamicField.createProprieta(tipologiaProprieta, posizione);
    }

    @Override
    public boolean removeProprieta(GrantProperty proprieta)
    {
        return this.dynamicField.removeProprieta(proprieta);
    }

    @Override
    public List<GrantProperty> getProprietaDellaTipologia(
            GrantPropertiesDefinition tipologiaProprieta)
    {
        return this.dynamicField.getProprietaDellaTipologia(tipologiaProprieta);
    }

    @Override
    public Class<GrantProperty> getClassProperty()
    {
        return this.dynamicField.getClassProperty();
    }

    @Override
    public Class<GrantPropertiesDefinition> getClassPropertiesDefinition()
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

}
