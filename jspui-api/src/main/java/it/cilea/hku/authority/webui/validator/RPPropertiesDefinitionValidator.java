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

import it.cilea.hku.authority.model.dynamicfield.DecoratorRPPropertiesDefinition;
import it.cilea.osd.common.validation.BaseValidator;
import it.cilea.osd.jdyna.controller.DecoratorPropertiesDefinitionController;
import it.cilea.osd.jdyna.service.ValidatorService.ValidationResult;

import java.util.List;

import org.springframework.validation.Errors;

public class RPPropertiesDefinitionValidator extends BaseValidator {
	
   	private List<String> messages;
   	
   	private ExtendedValidatorService validatorService;

   	
   	public boolean supports(Class arg0) {
		return getClazz().isAssignableFrom(arg0);
	}
	
	@Override
	public void validate(Object object, Errors errors) {

		DecoratorRPPropertiesDefinition metadato = (DecoratorRPPropertiesDefinition) object;

		// lo shortname non puo' essere vuoto

		String shortName = metadato.getShortName();

		// validazione shortname...deve essere unico e non nullo e formato solo da caratteri
		// alfabetici da 'a-zA-Z','_' e '-'
		boolean result = (shortName != null) && shortName.matches("^[a-z_\\-A-Z]*$");
				
		if (result && shortName.length()!=0) {

			ValidationResult result2 = null;

			// verifica se e' unica
			// controllo sul db che non ci siano shortname uguali
			result2 = validatorService.checkShortName(
					metadato.getObject().getClass(), metadato.getObject());
			if (!result2.isSuccess())
				errors.rejectValue("shortName", result2.getMessage());

		} else {
			errors.rejectValue("shortName",
					"error.message.validation.shortname.pattern");
		}
	}

	public void setValidatorService(ExtendedValidatorService validatorService) {
		this.validatorService = validatorService;
	}
	

}
