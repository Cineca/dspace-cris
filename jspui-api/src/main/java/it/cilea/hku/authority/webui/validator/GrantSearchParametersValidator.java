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

import it.cilea.hku.authority.webui.dto.SearchGrantParametersDTO;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class GrantSearchParametersValidator implements Validator {

	private Class clazz;

	public boolean supports(Class arg0) {
		return clazz.isAssignableFrom(arg0);
	}

	public void validate(Object arg0, Errors arg1) {
		SearchGrantParametersDTO dto = (SearchGrantParametersDTO) arg0;

		if (dto.getSearchMode() != null) {
			if (dto.getAdvancedSyntax()) {
				ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "queryString",
						"error.validation.hku.search.query.empty");
			}
		} else if (dto.getCodeSearchMode() != null) {
			ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "codeQuery",
					"error.validation.hku.search.query.empty");
		} else {
			arg1.reject("error.validation.hku.search.unknow-mode");
		}
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}
}
