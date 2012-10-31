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

import it.cilea.hku.authority.model.dynamicfield.ProjectAdditionalFieldStorage;
import it.cilea.hku.authority.model.dynamicfield.ProjectPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.ProjectProperty;
import it.cilea.hku.authority.model.export.ExportConstants;
import it.cilea.osd.common.core.HasTimeStampInfo;
import it.cilea.osd.common.core.TimeStampInfo;
import it.cilea.osd.common.model.Identifiable;
import it.cilea.osd.jdyna.model.AnagraficaSupport;

import java.util.ArrayList;
import java.util.LinkedList;
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
@Table(name = "cris_project")
@NamedQueries({
        @NamedQuery(name = "Project.findAll", query = "from Project order by id"),
        @NamedQuery(name = "Project.count", query = "select count(*) from Project"),
        @NamedQuery(name = "Project.paginate.id.asc", query = "from Project order by id asc"),
        @NamedQuery(name = "Project.paginate.id.desc", query = "from Project order by id desc"),
        @NamedQuery(name = "Project.uniqueBySourceID", query = "from Project where sourceID = ? order by id desc"),
        @NamedQuery(name = "Project.uniqueUUID", query = "from Project where uuid = ?")
  })
public class Project extends ACrisObject<ProjectProperty, ProjectPropertiesDefinition>
        implements                
        HasTimeStampInfo,
        Cloneable,
        IExportableDynamicObject<ProjectPropertiesDefinition, ProjectProperty, ProjectAdditionalFieldStorage>
{

    @Transient
    /**
     * Constant for resource type assigned to the Researcher Grants
     */
    public static final int GRANT_TYPE_ID = 10;

    /** DB Primary key */
    @Id
    @GeneratedValue(generator = "CRIS_PROJECT_SEQ")
    @SequenceGenerator(name = "CRIS_PROJECT_SEQ", sequenceName = "CRIS_PROJECT_SEQ")
    private Integer id;

    /** timestamp info for creation and last modify */
    @Embedded
    private TimeStampInfo timeStampInfo;

    /**
     * Map of additional custom data
     */
    @Embedded
    private ProjectAdditionalFieldStorage dynamicField;

    public Project()
    {
        this.dynamicField = new ProjectAdditionalFieldStorage();
    }

    public Investigator getInvestigator()
    {
        // TODO return a JDynA pointer
        return new Investigator();
    }

    public List<Investigator> getCoInvestigators()
    {
        // TODO return a JDynA pointer
        return new LinkedList<Investigator>();
    }

    /**
     * Getter method.
     * 
     * @return the timestamp of creation and last modify of this Project
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

    public String getTitle()
    {
        String result = "";
        for (ProjectProperty title : this.getDynamicField()
                .getAnagrafica4view().get("projecttitle"))
        {
            result += title.getValue().getObject();
            result += " ";
        }
        return result;
    }

    public String getYear()
    {
        String result = "";
        for (ProjectProperty year : this.getDynamicField().getAnagrafica4view()
                .get("fundingyear"))
        {
            result += year.getValue().getObject();
            result += " ";
        }
        return result;
    }

    public String getInvestigatorToDisplay()
    {
        Investigator inv = getInvestigator();
        if (inv != null)
        {
            if (inv.getIntInvestigator() != null)
            {
                return inv.getIntInvestigator().getFullName();
            }
            else
            {
                return inv.getExtInvestigator();
            }
        }
        return "";
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
        return "" + GRANT_TYPE_ID;
    }
    
    public String getNameSingleRowElement()
    {
        return ExportConstants.ELEMENT_SINGLEROW;
    }

    
    public ProjectAdditionalFieldStorage getDynamicField()
    {
        if (this.dynamicField == null)
        {
            this.dynamicField = new ProjectAdditionalFieldStorage();
        }
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

    
    public String getIdentifyingValue()
    {
        return this.dynamicField.getIdentifyingValue();
    }

    
    public String getDisplayValue()
    {
        return this.dynamicField.getDisplayValue();
    }

    
    public List<ProjectProperty> getAnagrafica()
    {
        return this.dynamicField.getAnagrafica();
    }

    
    public Map<String, List<ProjectProperty>> getAnagrafica4view()
    {
        return this.dynamicField.getAnagrafica4view();
    }

    
    public void setAnagrafica(List<ProjectProperty> anagrafica)
    {
        this.dynamicField.setAnagrafica(anagrafica);
    }

    
    public ProjectProperty createProprieta(
            ProjectPropertiesDefinition tipologiaProprieta)
            throws IllegalArgumentException
    {
        return this.dynamicField.createProprieta(tipologiaProprieta);
    }

    
    public ProjectProperty createProprieta(
            ProjectPropertiesDefinition tipologiaProprieta, Integer posizione)
            throws IllegalArgumentException
    {
        return this.dynamicField.createProprieta(tipologiaProprieta, posizione);
    }

    
    public boolean removeProprieta(ProjectProperty proprieta)
    {
        return this.dynamicField.removeProprieta(proprieta);
    }

    
    public List<ProjectProperty> getProprietaDellaTipologia(
            ProjectPropertiesDefinition tipologiaProprieta)
    {
        return this.dynamicField.getProprietaDellaTipologia(tipologiaProprieta);
    }

    
    public Class<ProjectProperty> getClassProperty()
    {
        return this.dynamicField.getClassProperty();
    }

    
    public Class<ProjectPropertiesDefinition> getClassPropertiesDefinition()
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

    public void setInvestigator(Investigator inv)
    {
        // TODO Auto-generated method stub

    }

    public void setCoInvestigators(List<Investigator> coinvestigators)
    {
        // TODO Auto-generated method stub

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

        List<ProjectProperty> dyna = getDynamicField().getAnagrafica4view().get(
                field);
        for (ProjectProperty prop : dyna)
        {
            if (prop.getVisibility() == VisibilityConstants.PUBLIC)
                result.add(prop.toString());
        }

        return result;
    }

    public String getPublicPath()
    {        
        return "project";
    }

    @Override
    public String getName() {
    	return getTitle();
    }
    
    @Override
    public int getType() {
    	return CrisConstants.PROJECT_TYPE_ID;
    }
}