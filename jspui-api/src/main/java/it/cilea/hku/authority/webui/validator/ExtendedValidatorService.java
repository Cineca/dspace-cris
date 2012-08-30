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

import it.cilea.hku.authority.service.ExtendedTabService;
import it.cilea.osd.jdyna.service.IPersistenceDynaService;
import it.cilea.osd.jdyna.service.ValidatorService;
import it.cilea.osd.jdyna.web.Box;
import it.cilea.osd.jdyna.web.Tab;

public class ExtendedValidatorService extends ValidatorService {

	

	public ExtendedValidatorService(IPersistenceDynaService applicationService) {
		super(applicationService);
	}

	
	/** 
	 * Shortname tab validation
	 *  
	 * @param clazz
	 * @param object
	 * @return
	 */
	public ValidationResult controllaShortName(Class clazz,Tab object) {
		if(object.getId()==null || !(((ExtendedTabService)getApplicationService()).exist(object.getClass(), object.getId()))) {
			if(((ExtendedTabService)getApplicationService()).getTabByShortName(clazz, object.getShortName())!=null)
				return new ValidationResult("error.message.validation.shortname",false,"Error");
		}
		return new ValidationResult();
	}
	
	/** 
	 * Shortname box validation
	 *  
	 * @param clazz
	 * @param object
	 * @return
	 */
	public ValidationResult checkShortName(Class clazz,Box object) {
		if(object.getId()==null || !(((ExtendedTabService)getApplicationService()).exist(object.getClass(), object.getId()))) {
			if(((ExtendedTabService)getApplicationService()).getBoxByShortName(clazz, object.getShortName())!=null)
				return new ValidationResult("error.message.validation.shortname",false,"Error");
		}
		return new ValidationResult();
	}
}
