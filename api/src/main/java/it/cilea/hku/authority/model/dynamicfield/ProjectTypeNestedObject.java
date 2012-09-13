package it.cilea.hku.authority.model.dynamicfield;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import it.cilea.osd.jdyna.model.ATypeNestedObject;
/**
*
* @author pascarelli
*
*/
@Entity
@Table(name = "model_project_jdyna_nestedobject_typo")
@NamedQueries ({
    @NamedQuery(name="ProjectTypeNestedObject.findAll", query = "from ProjectTypeNestedObject order by id" ),
    @NamedQuery(name="ProjectTypeNestedObject.uniqueByNome", query = "from ProjectTypeNestedObject where nome = ?" )
})
public class ProjectTypeNestedObject extends ATypeNestedObject<ProjectNestedPropertiesDefinition>
{
    @ManyToMany
    @JoinTable(name = "model_project_jdyna_nestedobject_typo2mask")
    private List<ProjectNestedPropertiesDefinition> mask;

    @Override
    public List<ProjectNestedPropertiesDefinition> getMaschera()
    {
        return mask;
    }

    public void setMaschera(List<ProjectNestedPropertiesDefinition> mask) {
        this.mask = mask;
    }
}
