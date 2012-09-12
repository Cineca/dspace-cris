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

import org.springframework.web.multipart.MultipartFile;

public class ImportDTO {
	
	private MultipartFile file;
	
	private String modeXSD;
	
	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setModeXSD(String modeXSD) {
		this.modeXSD = modeXSD;
	}

	public String getModeXSD() {
		return modeXSD;
	}

	
}
