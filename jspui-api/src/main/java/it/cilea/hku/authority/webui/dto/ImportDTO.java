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
