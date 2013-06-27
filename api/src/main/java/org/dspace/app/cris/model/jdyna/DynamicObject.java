/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package org.dspace.app.cris.model.jdyna;

import it.cilea.osd.common.core.TimeStampInfo;
import it.cilea.osd.jdyna.model.AType;
import it.cilea.osd.jdyna.model.TypeSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.dspace.app.cris.model.ACrisObject;
import org.dspace.app.cris.model.CrisConstants;
import org.dspace.app.cris.model.VisibilityConstants;


@Entity
@Table(name = "cris_do")
@NamedQueries({
        @NamedQuery(name = "DynamicObject.findAll", query = "from DynamicObject order by id"),
        @NamedQuery(name = "DynamicObject.count", query = "select count(*) from DynamicObject"),
        @NamedQuery(name = "DynamicObject.paginate.id.asc", query = "from DynamicObject order by id asc"),
        @NamedQuery(name = "DynamicObject.paginate.id.desc", query = "from DynamicObject order by id desc"),
        @NamedQuery(name = "DynamicObject.paginate.status.asc", query = "from DynamicObject order by status asc"),
        @NamedQuery(name = "DynamicObject.paginate.status.desc", query = "from DynamicObject order by status desc"),
        @NamedQuery(name = "DynamicObject.paginate.sourceID.asc", query = "from DynamicObject order by sourceID asc"),
        @NamedQuery(name = "DynamicObject.paginate.sourceID.desc", query = "from DynamicObject order by sourceID desc"),
        @NamedQuery(name = "DynamicObject.paginate.uuid.asc", query = "from DynamicObject order by uuid asc"),
        @NamedQuery(name = "DynamicObject.paginate.uuid.desc", query = "from DynamicObject order by uuid desc"),
        @NamedQuery(name = "DynamicObject.uniqueBySourceID", query = "from DynamicObject where sourceID = ?"),
        @NamedQuery(name = "DynamicObject.uniqueUUID", query = "from DynamicObject where uuid = ?"),
        @NamedQuery(name = "DynamicObject.uniqueByCrisID", query = "from DynamicObject where crisID = ?")        
  })
public class DynamicObject extends ACrisObject<DynamicProperty, DynamicPropertiesDefinition, DynamicNestedProperty, DynamicNestedPropertiesDefinition, DynamicNestedObject, DynamicNestedObjectType> implements TypeSupport<DynamicProperty, DynamicPropertiesDefinition>
{
    
    /** DB Primary key */
    @Id
    @GeneratedValue(generator = "CRIS_DYNAOBJ_SEQ")
    @SequenceGenerator(name = "CRIS_DYNAOBJ_SEQ", sequenceName = "CRIS_DYNAOBJ_SEQ")
    private Integer id;
    
    /** timestamp info for creation and last modify */
    @Embedded
    private TimeStampInfo timeStampInfo;
    
    /**
     * Map of additional custom data
     */
    @Embedded
    private DynamicAdditionalFieldStorage dynamicField;

    @ManyToOne
    private DynamicObjectType typo; 
    
    public DynamicObject()
    {
        this.dynamicField = new DynamicAdditionalFieldStorage();
    }
    
    public void setDynamicField(DynamicAdditionalFieldStorage dynamicField)
    {
        this.dynamicField = dynamicField;
    }

    public DynamicAdditionalFieldStorage getDynamicField()
    {
        if (this.dynamicField == null)
        {
            this.dynamicField = new DynamicAdditionalFieldStorage();
        }
        return dynamicField;
    }
    
    @Override
    public List<DynamicProperty> getAnagrafica()
    {        
        return dynamicField.getAnagrafica();
    }

    @Override
    public Class<DynamicProperty> getClassProperty()
    {
        return this.dynamicField.getClassProperty();
    }

    @Override
    public Class<DynamicPropertiesDefinition> getClassPropertiesDefinition()
    {
        return this.dynamicField.getClassPropertiesDefinition();
    }

    @Override
    public Integer getId()
    {
        return id;
    }

    @Override
    public DynamicObjectType getTypo()
    {        
        return typo;
    }

    @Override
    public void setTypo(AType<DynamicPropertiesDefinition> typo)
    {
        this.typo = (DynamicObjectType)typo;        
    }

    @Override
    public Map<String, List<DynamicProperty>> getAnagrafica4view()
    {
        return this.dynamicField.getAnagrafica4view();
    }

    @Override
    public void setAnagrafica(List<DynamicProperty> anagrafica)
    {
        this.dynamicField.setAnagrafica(anagrafica);
    }

    @Override
    public DynamicProperty createProprieta(
            DynamicPropertiesDefinition tipologiaProprieta)
            throws IllegalArgumentException
    {
        return this.dynamicField.createProprieta(tipologiaProprieta);
    }

    @Override
    public DynamicProperty createProprieta(
            DynamicPropertiesDefinition tipologiaProprieta, Integer posizione)
            throws IllegalArgumentException
    {
        return this.dynamicField.createProprieta(tipologiaProprieta, posizione);
    }

    @Override
    public boolean removeProprieta(DynamicProperty proprieta)
    {
        return this.dynamicField.removeProprieta(proprieta);
    }

    @Override
    public List<DynamicProperty> getProprietaDellaTipologia(
            DynamicPropertiesDefinition tipologiaProprieta)
    {
        return this.dynamicField.getProprietaDellaTipologia(tipologiaProprieta);
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
    public String getPublicPath()
    {
        return CrisConstants.getAuthorityPrefix(this);
    }

    @Override
    public String getAuthorityPrefix()
    {
        return CrisConstants.getAuthorityPrefix(this);
    }

    @Override
    public TimeStampInfo getTimeStampInfo()
    {
        if (timeStampInfo == null)
        {
            timeStampInfo = new TimeStampInfo();
        }
        return timeStampInfo;
    }

    @Override
    public Class<DynamicNestedObject> getClassNested()
    {
        return DynamicNestedObject.class;
    }

    @Override
    public Class<DynamicNestedObjectType> getClassTypeNested()
    {
        return DynamicNestedObjectType.class;
    }

    @Override
    public int getType()
    {
        return getTypo().getId() + CrisConstants.CRIS_DYNAMIC_TYPE_ID_START;
    }

    @Override
    public String getName()
    {
        for (DynamicProperty title : this.getAnagrafica4view().get("name"))
        {
            return title.toString();
        }
        return null;
    }

    @Override
    public String getTypeText()
    {
        return CrisConstants.getEntityTypeText(getType());
    }

    public void setId(Integer id)
    {
        this.id = id;
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

        List<DynamicProperty> dyna = getDynamicField().getAnagrafica4view().get(
                field);
        for (DynamicProperty prop : dyna)
        {
            if (prop.getVisibility() == VisibilityConstants.PUBLIC)
                result.add(prop.toString());
        }

        return result;
    }
}
