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

import it.cilea.hku.authority.model.dynamicfield.OUAdditionalFieldStorage;
import it.cilea.hku.authority.model.dynamicfield.OUPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.OUProperty;
import it.cilea.osd.common.core.HasTimeStampInfo;
import it.cilea.osd.common.core.TimeStampInfo;
import it.cilea.osd.common.model.Identifiable;
import it.cilea.osd.jdyna.model.AnagraficaSupport;

import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;



@Entity
@Table(name = "cris_organizationunit")
@NamedQueries( {
        @NamedQuery(name = "OrganizationUnit.findAll", query = "from OrganizationUnit order by id"),
        @NamedQuery(name = "OrganizationUnit.count", query = "select count(*) from OrganizationUnit"),
        @NamedQuery(name = "OrganizationUnit.paginate.id.asc", query = "from OrganizationUnit order by id asc"),
        @NamedQuery(name = "OrganizationUnit.paginate.id.desc", query = "from OrganizationUnit order by id desc")                
})
public class OrganizationUnit implements Identifiable, UUIDSupport, HasTimeStampInfo, Cloneable, AnagraficaSupport<OUProperty, OUPropertiesDefinition> {

	@Transient
	/**
	 * Constant for resource type assigned to the Researcher Grants
	 */
	public static final int ORGANIZATIONUNIT_TYPE_ID = 11;
	
	 /** DB Primary key */
    @Id
    @GeneratedValue(generator = "CRIS_OU_SEQ")
    @SequenceGenerator(name = "CRIS_OU_SEQ", sequenceName = "CRIS_OU_SEQ")    
    private Integer id;
    
    @Column(nullable = false, unique = true)
    private String uuid;
    
    /** timestamp info for creation and last modify */
    @Embedded
    private TimeStampInfo timeStampInfo;
    /**
     * Map of additional custom data
     */
    @Embedded
    private OUAdditionalFieldStorage dynamicField;
    
    
    /** True if grant is active */
    private Boolean status;
    
    @Column(nullable = true, unique = true)
    private String sourceId;
    
    /**
     * Getter method.
     * @return the timestamp of creation and last modify of this OrganizationUnit
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
     *            the status of this OrganizationUnit
     */
    public void setStatus(Boolean status)
    {
        this.status = status;
    }

	
    @Override
    public Object clone() throws CloneNotSupportedException {
    	return super.clone();
    }
    

    public OUAdditionalFieldStorage getDynamicField()
    {
        return dynamicField;
    }

    public void setDynamicField(OUAdditionalFieldStorage dynamicField)
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
    public List<OUProperty> getAnagrafica()
    {
        return this.dynamicField.getAnagrafica();
    }

    @Override
    public Map<String, List<OUProperty>> getAnagrafica4view()
    {
        return this.dynamicField.getAnagrafica4view();
    }

    @Override
    public void setAnagrafica(List<OUProperty> anagrafica)
    {
        this.dynamicField.setAnagrafica(anagrafica);
    }

    @Override
    public OUProperty createProprieta(
            OUPropertiesDefinition tipologiaProprieta)
            throws IllegalArgumentException
    {
        return this.dynamicField.createProprieta(tipologiaProprieta);
    }

    @Override
    public OUProperty createProprieta(
            OUPropertiesDefinition tipologiaProprieta, Integer posizione)
            throws IllegalArgumentException
    {
        return this.dynamicField.createProprieta(tipologiaProprieta, posizione);
    }

    @Override
    public boolean removeProprieta(OUProperty proprieta)
    {
        return this.dynamicField.removeProprieta(proprieta);
    }

    @Override
    public List<OUProperty> getProprietaDellaTipologia(
            OUPropertiesDefinition tipologiaProprieta)
    {
        return this.dynamicField.getProprietaDellaTipologia(tipologiaProprieta);
    }

    @Override
    public Class<OUProperty> getClassProperty()
    {
        return this.dynamicField.getClassProperty();
    }

    @Override
    public Class<OUPropertiesDefinition> getClassPropertiesDefinition()
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

    @Override
    public String getUuid()
    {        
        return uuid;
    }


    @Override
    public void setUuid(String uuid)
    {
        this.uuid = uuid;        
    }


    public void setSourceId(String rgCode)
    {
        this.sourceId = rgCode;
    }

    public String getSourceId()
    {
        return sourceId;
    }

}
