package it.cilea.hku.authority.model.dynamicfield;

import it.cilea.hku.authority.model.Project;
import it.cilea.hku.authority.model.UUIDSupport;
import it.cilea.osd.common.core.HasTimeStampInfo;
import it.cilea.osd.jdyna.model.ANestedObjectWithTypeSupport;
import it.cilea.osd.jdyna.model.ATipologia;
import it.cilea.osd.jdyna.model.AnagraficaSupport;
import it.cilea.osd.jdyna.model.PropertiesDefinition;
import it.cilea.osd.jdyna.model.Property;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OrderBy;

/**
 * @author pascarelli
 *
 */
@Entity
@Table(name = "cris_project_nestedobject")
@NamedQueries( {
        @NamedQuery(name = "ProjectNestedObject.findAll", query = "from ProjectNestedObject order by id"),
        @NamedQuery(name = "ProjectNestedObject.paginate.id.asc", query = "from ProjectNestedObject order by id asc"),
        @NamedQuery(name = "ProjectNestedObject.paginate.id.desc", query = "from ProjectNestedObject order by id desc"),
        @NamedQuery(name = "ProjectNestedObject.findNestedObjectsByParentIDAndTypoID", query = "from ProjectNestedObject where parent.id = ? and typo.id = ?"),
        @NamedQuery(name = "ProjectNestedObject.paginateNestedObjectsByParentIDAndTypoID.asc.asc", query = "from ProjectNestedObject where parent.id = ? and typo.id = ?"),
        @NamedQuery(name = "ProjectNestedObject.countNestedObjectsByParentIDAndTypoID", query = "select count(*) from ProjectNestedObject where parent.id = ? and typo.id = ?"),
        @NamedQuery(name = "ProjectNestedObject.findActiveNestedObjectsByParentIDAndTypoID", query = "from ProjectNestedObject where parent.id = ? and typo.id = ? and status = true"),
        @NamedQuery(name = "ProjectNestedObject.paginateActiveNestedObjectsByParentIDAndTypoID.asc.asc", query = "from ProjectNestedObject where parent.id = ? and typo.id = ? and status = true"),
        @NamedQuery(name = "ProjectNestedObject.countActiveNestedObjectsByParentIDAndTypoID", query = "select count(*) from ProjectNestedObject where parent.id = ? and typo.id = ? and status = true"),
        @NamedQuery(name = "ProjectNestedObject.findNestedObjectsByTypoID", query = "from ProjectNestedObject where typo.id = ?"),
        @NamedQuery(name = "ProjectNestedObject.deleteNestedObjectsByTypoID", query = "delete from ProjectNestedObject where typo.id = ?")
        })
public class ProjectNestedObject extends ANestedObjectWithTypeSupport<ProjectNestedProperty, ProjectNestedPropertiesDefinition> implements UUIDSupport, HasTimeStampInfo 
{
    
    @OneToMany(mappedBy = "parent")
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })    
    @OrderBy(clause="position asc")
    private List<ProjectNestedProperty> anagrafica;

    @ManyToOne
    private ProjectTypeNestedObject typo;

    @ManyToOne
    private Project parent;
    
    @Override
    public List<ProjectNestedProperty> getAnagrafica() {
        if(this.anagrafica == null) {
            this.anagrafica = new LinkedList<ProjectNestedProperty>();
        }
        return anagrafica;
    }
    

    @Override
    public Class<ProjectNestedProperty> getClassProperty()
    {
        return ProjectNestedProperty.class;
    }

    @Override
    public Class<ProjectNestedPropertiesDefinition> getClassPropertiesDefinition()
    {        
        return ProjectNestedPropertiesDefinition.class;
    }

    @Override
    public ProjectTypeNestedObject getTypo()
    {
        return typo;
    }


    public void setParent(Project parent)
    {
        this.parent = parent;
    }


    @Override
    public Project getParent()
    {
        return parent;
    }


    @Override
    public void setTypo(ATipologia<ProjectNestedPropertiesDefinition> typo)
    {
        this.typo = (ProjectTypeNestedObject)typo;
    }


    @Override
    public <PP extends Property<PTP>, PTP extends PropertiesDefinition> void setParent(
            AnagraficaSupport<PP, PTP> parent)
    {
        this.parent = (Project)parent;        
    }


    @Override
    public Class getClassParent()
    {
        return Project.class;
    }
   
}
