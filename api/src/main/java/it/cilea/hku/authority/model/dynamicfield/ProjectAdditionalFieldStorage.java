package it.cilea.hku.authority.model.dynamicfield;


import it.cilea.hku.authority.model.ResearcherGrant;
import it.cilea.osd.jdyna.model.AnagraficaObject;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.OrderBy;

@Embeddable
@NamedQueries( {
    @NamedQuery(name = "ProjectAdditionalFieldStorage.findAll", query = "from ProjectAdditionalFieldStorage order by id"),
    @NamedQuery(name = "ProjectAdditionalFieldStorage.paginate.id.asc", query = "from ProjectAdditionalFieldStorage order by id asc"),
    @NamedQuery(name = "ProjectAdditionalFieldStorage.paginate.id.desc", query = "from ProjectAdditionalFieldStorage order by id desc"),  
    @NamedQuery(name = "ProjectAdditionalFieldStorage.paginateByTipologiaProprieta.value.asc", query = "select rpdyn from ProjectAdditionalFieldStorage rpdyn left outer join rpdyn.anagrafica anagrafica where anagrafica.position = 0 and anagrafica.typo.id = ? order by anagrafica.value.sortValue asc"),
    @NamedQuery(name = "ProjectAdditionalFieldStorage.paginateByTipologiaProprieta.value.desc", query = "select rpdyn from ProjectAdditionalFieldStorage rpdyn left outer join rpdyn.anagrafica anagrafica where anagrafica.position = 0 and anagrafica.typo.id = ? order by anagrafica.value.sortValue desc"),
    @NamedQuery(name = "ProjectAdditionalFieldStorage.paginateEmptyById.value.asc", query = "select rpdyn from ProjectAdditionalFieldStorage rpdyn where rpdyn NOT IN (select rpdyn from ProjectAdditionalFieldStorage rpdyn left outer join rpdyn.anagrafica anagrafica where anagrafica.position = 0 and anagrafica.typo.id = ?) order by id asc"),
    @NamedQuery(name = "ProjectAdditionalFieldStorage.paginateEmptyById.value.desc", query = "select rpdyn from ProjectAdditionalFieldStorage rpdyn where rpdyn NOT IN (select rpdyn from ProjectAdditionalFieldStorage rpdyn left outer join rpdyn.anagrafica anagrafica where anagrafica.position = 0 and anagrafica.typo.id = ?) order by id desc"),
    @NamedQuery(name = "ProjectAdditionalFieldStorage.countNotEmptyByTipologiaProprieta", query = "select count(rpdyn) from ProjectAdditionalFieldStorage rpdyn left outer join rpdyn.anagrafica anagrafica where anagrafica.position = 0 and anagrafica.typo.id = ? "),
    @NamedQuery(name = "ProjectAdditionalFieldStorage.countEmptyByTipologiaProprieta", query = "select count(rpdyn) from ProjectAdditionalFieldStorage rpdyn where rpdyn NOT IN (select rpdyn from ProjectAdditionalFieldStorage rpdyn left outer join rpdyn.anagrafica anagrafica where anagrafica.position = 0 and anagrafica.typo.id = ?)"),
    @NamedQuery(name = "ProjectAdditionalFieldStorage.count", query = "select count(*) from ProjectAdditionalFieldStorage")
})
public class ProjectAdditionalFieldStorage extends AnagraficaObject<GrantProperty, GrantPropertiesDefinition> {
    
    @OneToOne
    @JoinColumn(name = "id")    
    private ResearcherGrant researcherGrant;    

    @OneToMany(mappedBy = "parent")
    @LazyCollection(LazyCollectionOption.FALSE)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })    
    @OrderBy(clause="position asc")
    private List<GrantProperty> anagrafica;
    
    public List<GrantProperty> getAnagrafica() {
        if(this.anagrafica == null) {
            this.anagrafica = new LinkedList<GrantProperty>();
        }
        return anagrafica;
    }

    public Class<GrantProperty> getClassProperty() {
        return GrantProperty.class;
    }

    public Class<GrantPropertiesDefinition> getClassPropertiesDefinition() {
        return GrantPropertiesDefinition.class;
    }

    public Integer getId() {
        return researcherGrant.getId();
    }

    public void setAnagraficaLazy(List<GrantProperty> pp) {
        this.anagrafica = pp;       
    }

    public ResearcherGrant getResearcherGrant()
    {
        return researcherGrant;
    }

    public void setResearcherGrant(ResearcherGrant researcherGrant)
    {
        this.researcherGrant = researcherGrant;
    }
 
}