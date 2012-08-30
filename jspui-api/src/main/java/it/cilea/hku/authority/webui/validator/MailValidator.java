/**
 * <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
 * <html><head>
 * <title>301 Moved Permanently</title>
 * </head><body>
 * <h1>Moved Permanently</h1>
 * <p>The document has moved <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>.</p>
 * </body></html>
 */
package it.cilea.hku.authority.webui.validator;

import it.cilea.hku.authority.webui.dto.MailDTO;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class MailValidator implements Validator {
	
	private Class clazz;
	
	
	public boolean supports(Class arg0) {
		return clazz.isAssignableFrom(arg0);
	}
	
	public void validate(Object arg0, Errors arg1) {
	    MailDTO dto = (MailDTO)arg0;
	    if(dto.getText()==null || dto.getText().isEmpty()) {
	        arg1.reject("error.textmail.mandatory", "Mail text is mandatory");
	    }		
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}
}
