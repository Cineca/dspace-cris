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

import it.cilea.osd.common.model.IdentifiableObject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

/**
 * This class models a single subscription to receive mail notification of the
 * newly added item to ResearcherPage.
 * 
 * @author cilea
 * 
 */
@Entity
@NamedQueries( {
        // WARNING: the findAll query MUST return RPSubscription ordered by eperson as it is needed by the batch script
        @NamedQuery(name = "RPSubscription.findAll", query = "from RPSubscription order by epersonID"),
        @NamedQuery(name = "RPSubscription.countByRp", query = "select count(*) from RPSubscription where rp = ? order by id"),
        @NamedQuery(name = "RPSubscription.findRPByEpersonID", query = "select rp from RPSubscription where epersonID = ?"),
        @NamedQuery(name = "RPSubscription.deleteByEpersonID", query = "delete from RPSubscription where epersonID = ?"),
        @NamedQuery(name = "RPSubscription.uniqueByEpersonIDandRp", query = "from RPSubscription where epersonID = ? and rp = ?")
})   
public class RPSubscription extends IdentifiableObject {
    /** DB Primary key */
    @Id
    @GeneratedValue(generator = "SUBSCRIPTIONRP_SEQ")
    @SequenceGenerator(name = "SUBSCRIPTIONRP_SEQ", sequenceName = "SUBSCRIPTIONRP_SEQ")
    private Integer id;
	
    /**
	 * the rp to monitor
	 */
    @ManyToOne
	private ResearcherPage rp;
		
	/**
	 * the eperson ID of the subscriber
	 */
	private int epersonID;

	public Integer getId()
    {
        return id;
    }
	
	public void setId(Integer id)
    {
        this.id = id;
    }
	
	public int getEpersonID()
    {
        return epersonID;
    }
	
	public ResearcherPage getRp()
    {
        return rp;
    }
	
	public void setEpersonID(int epersonID)
    {
        this.epersonID = epersonID;
    }
	
	public void setRp(ResearcherPage rp)
    {
        this.rp = rp;
    }
}
