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

import java.util.LinkedList;
import java.util.List;

import it.cilea.hku.authority.model.Project;
import it.cilea.hku.authority.model.RestrictedField;
import it.cilea.osd.jdyna.dto.AnagraficaObjectAreaDTO;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ProjectAnagraficaObjectDTO extends AnagraficaObjectAreaDTO {

	/**
	 * The log4j category
	 */
	protected final Log log = LogFactory.getLog(getClass());

	private boolean status;

	private String rgCode;
	
	private String investigator;
	
	private List<String> coInvestigators;

	public ProjectAnagraficaObjectDTO(Project grant) {
		super();
		this.setStatus(grant.getStatus());
		this.setRgCode(grant.getRgCode());
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getRgCode() {
		return rgCode;
	}

	public void setRgCode(String rgCode) {
		this.rgCode = rgCode;
	}

	public void setInvestigator(String investigator) {
		this.investigator = investigator;
	}

	public String getInvestigator() {
		return investigator;
	}

	/**
	 * Decorate list for dynamic binding with spring mvc
	 * 
	 * @param list
	 * @return lazy list temporary
	 */
	private List getLazyList(List<String> list) {
		log.debug("Decorate list for dynamic binding with spring mvc");
		List lazyList = LazyList.decorate(list,
				FactoryUtils.instantiateFactory(String.class));

		return lazyList;
	}

	public void setCoInvestigators(List<String> coInvestigators) {
		this.coInvestigators = coInvestigators;
	}

	public List<String> getCoInvestigators() {
		if (this.coInvestigators == null) {
			this.coInvestigators = new LinkedList<String>();			
		}
		setCoInvestigators(getLazyList(coInvestigators));		
		return coInvestigators;
	}
}
