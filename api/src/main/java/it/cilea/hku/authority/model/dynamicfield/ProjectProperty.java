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
package it.cilea.hku.authority.model.dynamicfield;

import it.cilea.hku.authority.model.Project;
import it.cilea.osd.jdyna.model.AnagraficaSupport;
import it.cilea.osd.jdyna.model.Property;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Index;

@Entity
@Table(name="cris_project_prop", 
		uniqueConstraints = {@UniqueConstraint(columnNames={"position","typo_id","parent_id"})})
@NamedQueries( {
	@NamedQuery(name = "ProjectProperty.findPropertyByPropertiesDefinition", query = "from ProjectProperty where typo = ? order by position"),
	@NamedQuery(name = "ProjectProperty.findAll", query = "from ProjectProperty order by id"),	
	@NamedQuery(name = "ProjectProperty.findPropertyByParentAndTypo", query = "from ProjectProperty  where (parent.id = ? and typo.id = ?) order by position"),	
	@NamedQuery(name = "ProjectProperty.deleteAllPropertyByPropertiesDefinition", query = "delete from ProjectProperty property where typo = ?)")
})
public class ProjectProperty extends Property<ProjectPropertiesDefinition> {
	
	@ManyToOne(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)	
	private ProjectPropertiesDefinition typo;
	
	
	@ManyToOne
	@Index(name = "cris_project_prop_idx_parent_id")
	private Project parent;
	

	@Override
	public ProjectPropertiesDefinition getTypo() {
		return typo;
	}

	@Override
	public void setTypo(ProjectPropertiesDefinition propertyDefinition) {
		this.typo = propertyDefinition;		
	}

    @Override
    public void setParent(
            AnagraficaSupport<? extends Property<ProjectPropertiesDefinition>, ProjectPropertiesDefinition> parent)
    {
        if(parent!=null) {
            this.parent = ((ProjectAdditionalFieldStorage)parent).getProject();
        }
        else {
            this.parent = null;
        }       
                
    }

    @Override
    public AnagraficaSupport<ProjectProperty, ProjectPropertiesDefinition> getParent()
    {
        return this.parent;

    }

   
}