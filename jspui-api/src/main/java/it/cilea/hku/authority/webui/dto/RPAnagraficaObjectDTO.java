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

import it.cilea.hku.authority.model.ResearcherPage;
import it.cilea.hku.authority.model.RestrictedField;
import it.cilea.hku.authority.model.RestrictedFieldFile;
import it.cilea.hku.authority.model.listener.RPListener;
import it.cilea.osd.jdyna.dto.AnagraficaObjectAreaDTO;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;
import org.apache.commons.digester.SetRootRule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RPAnagraficaObjectDTO extends AnagraficaObjectAreaDTO {

	/**
	 * The log4j category
	 */
	protected final Log log = LogFactory.getLog(getClass());

	private Integer epersonID;
	
	private Boolean status;

	private String staffNo;

	private String fullName;

	private RestrictedField academicName;

	private RestrictedField chineseName;

	private RestrictedField email;

	private List<RestrictedField> variants;

	public RPAnagraficaObjectDTO(ResearcherPage rp) {
		super();
		setAcademicName(rp.getPreferredName());		
		setChineseName(rp.getTranslatedName());		
		setEmail(rp.getEmail());
		setFullName(rp.getFullName());		
		setStaffNo(rp.getSourceID());
		setStatus(rp.getStatus());		
		setVariants(rp.getVariants());		
	}


	public List<RestrictedField> getVariants() {
		if (this.variants == null) {
			this.variants = new LinkedList<RestrictedField>();
		}
		setVariants(getLazyList(variants));
		return variants;
	}

	/**
	 * Getter method.
	 * 
	 * @return the staffNo
	 */
	public String getStaffNo() {
		return staffNo;
	}

	/**
	 * Setter method.
	 * 
	 * @param staffNo
	 *            the staffNo
	 */
	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	/**
	 * Getter method.
	 * 
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * Setter method.
	 * 
	 * @param fullName
	 *            the full name
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * Getter method.
	 * 
	 * @return the academic name
	 */
	public RestrictedField getAcademicName() {
		if (academicName == null) {
			academicName = new RestrictedField();
		}
		return academicName;
	}

	/**
	 * Setter method.
	 * 
	 * @param academicName
	 *            the academic name
	 */
	public void setAcademicName(RestrictedField academicName) {
		this.academicName = academicName;
	}

	/**
	 * Getter method.
	 * 
	 * @return the chinese name
	 */
	public RestrictedField getChineseName() {
		if (chineseName == null) {
			chineseName = new RestrictedField();
		}
		return chineseName;
	}

	/**
	 * Setter method.
	 * 
	 * @param chineseName
	 *            the chinese name
	 */
	public void setChineseName(RestrictedField chineseName) {
		this.chineseName = chineseName;
	}

	/**
	 * Getter method.
	 * 
	 * @return the email
	 */
	public RestrictedField getEmail() {
		if (email == null) {
			email = new RestrictedField();
		}
		return email;
	}

	/**
	 * Setter method.
	 * 
	 * @param email
	 *            the email
	 */
	public void setEmail(RestrictedField email) {
		this.email = email;
	}

	/**
     * Setter method.
     * 
     * @param variants
     *            the variants form of the name (include also Japanese, Korean,
     *            etc.)
     */
    public void setVariants(List<RestrictedField> variants) {
        this.variants = variants;
    }

    
	/**
	 * Getter method.
	 * 
	 * @return the status
	 */
	public Boolean getStatus() {
		return status;
	}
	
	/**
	 * Setter method.
	 * 
	 * @param id
	 *            the status of this ResearcherPage
	 */
	public void setStatus(Boolean status) {
		this.status = status;
	}


	/**
	 * Decorate list for dynamic binding with spring mvc
	 * 
	 * @param list
	 * @return lazy list temporary
	 */
	private List getLazyList(List<RestrictedField> list) {
		log.debug("Decorate list for dynamic binding with spring mvc");
		List lazyList = LazyList.decorate(list,
				FactoryUtils.instantiateFactory(RestrictedField.class));

		return lazyList;
	}


    public void setEpersonID(Integer epersonID)
    {
        this.epersonID = epersonID;
    }


    public Integer getEpersonID()
    {
        return epersonID;
    }

}
