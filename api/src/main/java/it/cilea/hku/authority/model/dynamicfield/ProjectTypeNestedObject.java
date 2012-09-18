package it.cilea.hku.authority.model.dynamicfield;

import it.cilea.osd.jdyna.model.ATypeNestedObject;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
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
    @Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
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
