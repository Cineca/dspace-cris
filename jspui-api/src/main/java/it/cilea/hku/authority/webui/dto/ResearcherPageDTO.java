/**
 * <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
 * <html><head>
 * <title>301 Moved Permanently</title>
 * </head><body>
 * <h1>Moved Permanently</h1>
 * <p>The document has moved <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>.</p>
 * </body></html>
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
	private String staffNo;
	private String fullName;
	private String academicName;
	private String chineseName;
	private String dept;
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

	public String getStaffNo() {
		return staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
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

    public String getAcademicName()
    {
        return academicName;
    }

    public void setAcademicName(String academicName)
    {
        this.academicName = academicName;
    }

    public String getChineseName()
    {
        return chineseName;
    }

    public void setChineseName(String chineseName)
    {
        this.chineseName = chineseName;
    }

    public String getDept()
    {
        return dept;
    }

    public void setDept(String dept)
    {
        this.dept = dept;
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
}
