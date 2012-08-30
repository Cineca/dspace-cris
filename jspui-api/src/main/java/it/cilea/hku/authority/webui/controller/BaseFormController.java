/**
 * <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
 * <html><head>
 * <title>301 Moved Permanently</title>
 * </head><body>
 * <h1>Moved Permanently</h1>
 * <p>The document has moved <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>.</p>
 * </body></html>
 */
package it.cilea.hku.authority.webui.controller;

import it.cilea.hku.authority.service.ApplicationService;

import java.beans.PropertyEditor;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 * This is the base abstract SpringMVC controller for the RPs authority list
 * project. All the other form controller extends this. This abstract controller
 * is responsible to initialize the SpringMVC binder system with the
 * configurated property editors.
 * 
 * @author cilea
 * 
 */
public abstract class BaseFormController extends SimpleFormController {

    /**
     * The log4j category
     */
	protected final Log log = LogFactory.getLog(getClass());

	/**
     * the applicationService for query the RP db, injected by Spring IoC
     */
	protected ApplicationService applicationService;

	/**
	 * the configurated property editors to use, injected by Spring IoC
	 */
	private Map<Class, PropertyEditor> customPropertyEditors;

	
	public void setApplicationService(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}

	@Override
	/**
	 * Register custom property editors injected by Spring IoC
	 */
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		if (customPropertyEditors != null) {
			for (Class propertyClass : customPropertyEditors.keySet()) {
				log.debug("Registrato customEditor "
						+ customPropertyEditors.get(propertyClass).getClass()
						+ " per la tipologia " + propertyClass);
				binder.registerCustomEditor(propertyClass,
						customPropertyEditors.get(propertyClass));
			}
		}

	}

	public void setCustomPropertyEditors(
			Map<Class, PropertyEditor> customPropertyEditors) {
		this.customPropertyEditors = customPropertyEditors;
	}
}
