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
package it.cilea.hku.authority.webui.dto;

import it.cilea.hku.authority.model.OrganizationUnit;
import it.cilea.hku.authority.util.ResearcherPageUtils;


/**
 * This class is the DTO used to manage a single OrganizationUnit in the
 * administrative functionality
 * 
 * 
 * @author pascarelli
 * 
 */
public class OrganizationUnitDTO {
	
	private Integer id;
	private String sourceID;
	private Boolean status;
	private String name;
		
	private OrganizationUnit organizationUnit;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSourceID() {
		return sourceID;
	}
	public void setSourceID(String code) {
		this.sourceID = code;
	}
	
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String title) {
		this.name = title;
	}	
    public void setOrganizationUnit(OrganizationUnit organizationUnit)
    {
        this.organizationUnit = organizationUnit;
    }
    public OrganizationUnit getOrganizationUnit()
    {
        return organizationUnit;
    }

}
