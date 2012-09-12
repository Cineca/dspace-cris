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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

/**
 * This class models a single subscription to receive mail notification of the
 * newly added item to ResearcherPage.
 * 
 * @author cilea
 * 
 */
@Entity
@NamedQueries( {
        // WARNING: the findAll and findByFreq query MUST return StatSubscription ordered by eperson as it is needed by the batch script
        @NamedQuery(name = "StatSubscription.findAll", query = "from StatSubscription order by epersonID"),
        @NamedQuery(name = "StatSubscription.findByFreq", query = "from StatSubscription where freq = ? order by epersonID"),
        @NamedQuery(name = "StatSubscription.findByRp", query = "from StatSubscription where rp = ? order by id"),
        @NamedQuery(name = "StatSubscription.findByHandle", query = "from StatSubscription where handle = ? order by id"),
        @NamedQuery(name = "StatSubscription.findByEPersonID", query = "from StatSubscription where epersonID = ? order by rp.id, handle"),
        @NamedQuery(name = "StatSubscription.findByEPersonIDandRP", query = "from StatSubscription where epersonID = ? and rp = ? order by freq"),
        @NamedQuery(name = "StatSubscription.findByEPersonIDandHandle", query = "from StatSubscription where epersonID = ? and handle = ? order by freq"),
        @NamedQuery(name = "StatSubscription.deleteByEPersonID", query = "delete from StatSubscription where epersonID = ?")
})   
public class StatSubscription extends IdentifiableObject {
    /** DB Primary key */
    @Id
    @GeneratedValue(generator = "STATSUBSCRIPTION_SEQ")
    @SequenceGenerator(name = "STATSUBSCRIPTION_SEQ", sequenceName = "STATSUBSCRIPTION_SEQ")
    private Integer id;
	
    /**
	 * the rp to monitor
	 */
    @ManyToOne
	private ResearcherPage rp;
	
	
	/**
	 * the handle of the DSpace resource to monitor
	 */
	private String handle;
	
	/**
	 * the eperson ID of the subscriber
	 */
	private int epersonID;

	/**
	 * See constant FREQUENCY_*
	 */
	private int freq;
	
	@Transient
	public static final int FREQUENCY_DAILY = 1;
	@Transient
	public static final int FREQUENCY_WEEKLY = 7;
	@Transient
	public static final int FREQUENCY_MONTHLY = 30;
	
    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public ResearcherPage getRp()
    {
        return rp;
    }

    public void setRp(ResearcherPage rp)
    {
        this.rp = rp;
    }

    public String getHandle()
    {
        return handle;
    }

    public void setHandle(String handle)
    {
        this.handle = handle;
    }

    public int getEpersonID()
    {
        return epersonID;
    }

    public void setEpersonID(int epersonID)
    {
        this.epersonID = epersonID;
    }

    public int getFreq()
    {
        return freq;
    }

    public void setFreq(int freq)
    {
        this.freq = freq;
    }
}
