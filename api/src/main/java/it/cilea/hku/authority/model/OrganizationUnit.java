/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package it.cilea.hku.authority.model;

import it.cilea.hku.authority.model.dynamicfield.OUAdditionalFieldStorage;
import it.cilea.hku.authority.model.dynamicfield.OUPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.OUProperty;
import it.cilea.osd.common.core.HasTimeStampInfo;
import it.cilea.osd.common.core.TimeStampInfo;
import it.cilea.osd.jdyna.model.AnagraficaSupport;

import java.util.List;
import java.util.Map;

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
@NamedQueries({
        @NamedQuery(name = "OrganizationUnit.findAll", query = "from OrganizationUnit order by id"),
        @NamedQuery(name = "OrganizationUnit.count", query = "select count(*) from OrganizationUnit"),
        @NamedQuery(name = "OrganizationUnit.paginate.id.asc", query = "from OrganizationUnit order by id asc"),
        @NamedQuery(name = "OrganizationUnit.paginate.id.desc", query = "from OrganizationUnit order by id desc"),
        @NamedQuery(name = "OrganizationUnit.paginate.status.asc", query = "from OrganizationUnit order by status asc"),
        @NamedQuery(name = "OrganizationUnit.paginate.status.desc", query = "from OrganizationUnit order by status desc"),
        @NamedQuery(name = "OrganizationUnit.paginate.sourceID.asc", query = "from OrganizationUnit order by sourceID asc"),
        @NamedQuery(name = "OrganizationUnit.paginate.sourceID.desc", query = "from OrganizationUnit order by sourceID desc"),
        @NamedQuery(name = "OrganizationUnit.paginate.uuid.asc", query = "from OrganizationUnit order by uuid asc"),
        @NamedQuery(name = "OrganizationUnit.paginate.uuid.desc", query = "from OrganizationUnit order by uuid desc"),
        @NamedQuery(name = "OrganizationUnit.uniqueUUID", query = "from OrganizationUnit where uuid = ?"),
        @NamedQuery(name = "OrganizationUnit.uniqueBySourceID", query = "from OrganizationUnit where sourceID = ?")  
})
public class OrganizationUnit extends
		ACrisObject<OUProperty, OUPropertiesDefinition> implements
		HasTimeStampInfo, Cloneable
{

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

    /** timestamp info for creation and last modify */
    @Embedded
    private TimeStampInfo timeStampInfo;

    /**
     * Map of additional custom data
     */
    @Embedded
    private OUAdditionalFieldStorage dynamicField;

    public OrganizationUnit()
    {
        this.dynamicField = new OUAdditionalFieldStorage();
    }

    /**
     * Getter method.
     * 
     * @return the timestamp of creation and last modify of this
     *         OrganizationUnit
     */
    public TimeStampInfo getTimeStampInfo()
    {
        if (timeStampInfo == null)
        {
            timeStampInfo = new TimeStampInfo();
        }
        return timeStampInfo;
    }

    public Object clone() throws CloneNotSupportedException
    {
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

    public String getIdentifyingValue()
    {
        return this.dynamicField.getIdentifyingValue();
    }

    public String getDisplayValue()
    {
        return this.dynamicField.getDisplayValue();
    }

    public List<OUProperty> getAnagrafica()
    {
        return this.dynamicField.getAnagrafica();
    }

    public Map<String, List<OUProperty>> getAnagrafica4view()
    {
        return this.dynamicField.getAnagrafica4view();
    }

    public void setAnagrafica(List<OUProperty> anagrafica)
    {
        this.dynamicField.setAnagrafica(anagrafica);
    }

    public OUProperty createProprieta(OUPropertiesDefinition tipologiaProprieta)
            throws IllegalArgumentException
    {
        return this.dynamicField.createProprieta(tipologiaProprieta);
    }

    public OUProperty createProprieta(
            OUPropertiesDefinition tipologiaProprieta, Integer posizione)
            throws IllegalArgumentException
    {
        return this.dynamicField.createProprieta(tipologiaProprieta, posizione);
    }

    public boolean removeProprieta(OUProperty proprieta)
    {
        return this.dynamicField.removeProprieta(proprieta);
    }

    public List<OUProperty> getProprietaDellaTipologia(
            OUPropertiesDefinition tipologiaProprieta)
    {
        return this.dynamicField.getProprietaDellaTipologia(tipologiaProprieta);
    }

    public Class<OUProperty> getClassProperty()
    {
        return this.dynamicField.getClassProperty();
    }

    public Class<OUPropertiesDefinition> getClassPropertiesDefinition()
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

    public String getPublicPath()
    {
        return "ou";
    }

    public String getName() {
        for (OUProperty title : this.getDynamicField()
                .getAnagrafica4view().get("name"))
        {
            return title.toString();
        }
        return null;
    }
    
    @Override
    public int getType() {
        return CrisConstants.OU_TYPE_ID;
    }
    
   
    @Override
    public String getAuthorityPrefix()
    {
        return "ou";
    }
}
