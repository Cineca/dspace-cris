/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package it.cilea.hku.authority.webui.dto;

import it.cilea.hku.authority.model.ResearcherPage;
import it.cilea.hku.authority.util.ResearcherPageUtils;
import it.cilea.osd.common.core.TimeStampInfo;

import java.util.List;

/**
 * This class is the DTO used to manage a single ResearcherPage in the
 * administrative browsing functionality and the edit of additional fields.
 * 
 * 
 * @author cilea
 * 
 */
public class ResearcherPageDTO  {
	
	private Integer id;
	private String uuid;
	private String sourceID;
	private String fullName;
	private Integer epersonID;	
	private Boolean status;
	private TimeStampInfo timeStampInfo;
	
	private List<String> labels;
	private String persistentIdentifier;
    private ResearcherPage rp;
	
	public ResearcherPageDTO() {	
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSourceID() {
		return sourceID;
	}

	public void setSourceID(String staffNo) {
		this.sourceID = staffNo;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public TimeStampInfo getTimeStampInfo() {
		return timeStampInfo;
	}

	public void setTimeStampInfo(TimeStampInfo timeStampInfo) {
		this.timeStampInfo = timeStampInfo;
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

    public String getPersistentIdentifier()
    {
        if(persistentIdentifier==null || persistentIdentifier.isEmpty()) {
            persistentIdentifier = ResearcherPageUtils.getPersistentIdentifier(getId());
        }
        return persistentIdentifier;
    }

    public void setPersistentIdentifier(String persistentIdentifier)
    {
        this.persistentIdentifier = persistentIdentifier;
    }

    public void setRp(ResearcherPage r)
    {
        this.rp = r;
    }
    
    public ResearcherPage getRp()
    {
        return rp;
    }

    public void setEpersonID(Integer epersonID)
    {
        this.epersonID = epersonID;
    }

    public Integer getEpersonID()
    {
        return epersonID;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public String getUuid()
    {
        return uuid;
    }

  
}
