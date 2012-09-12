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

import it.cilea.hku.authority.model.Investigator;
import it.cilea.hku.authority.model.ResearcherGrant;
import it.cilea.hku.authority.model.dynamicfield.GrantProperty;


/**
 * This class is the DTO used to manage a single ResearcherGrant in the
 * administrative functionality
 * 
 * 
 * @author pascarelli
 * 
 */
public class ResearcherGrantDTO  {
	
	private Integer id;
	private String code;
	private Boolean status;
	private String title;
	private String year;
	private String investigators;
	private ResearcherGrant grant;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	    
  
	public ResearcherGrant getGrant() {
		return grant;
	}
	public void setGrant(ResearcherGrant grant) {
		this.grant = grant;
	}
	
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getInvestigators() {
		return investigators;
	}
	public void setInvestigators(String investigators) {
		this.investigators = investigators;
	}

}
