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
package it.cilea.hku.authority.webui.controller.jdyna;

import it.cilea.hku.authority.model.dynamicfield.BoxProject;
import it.cilea.hku.authority.model.dynamicfield.DecoratorProjectPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.ProjectPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.TabProject;
import it.cilea.osd.jdyna.widget.WidgetLink;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;



public class FormProjectLinkPropertiesDefinitionController extends AFormDecoratorPropertiesDefinitionController<WidgetLink, ProjectPropertiesDefinition, DecoratorProjectPropertiesDefinition, BoxProject, TabProject> {

	
	
	public FormProjectLinkPropertiesDefinitionController(Class<ProjectPropertiesDefinition> targetModel, Class<WidgetLink> renderingModel) {
		super(targetModel, renderingModel);
	}
	
	

	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		String boxId = request.getParameter("boxId");
		String tabId = request.getParameter("tabId");
		DecoratorProjectPropertiesDefinition object = (DecoratorProjectPropertiesDefinition) command;
		getApplicationService().saveOrUpdate(DecoratorProjectPropertiesDefinition.class, object);
		if(boxId!=null && !boxId.isEmpty()) {
			BoxProject box = getApplicationService().get(BoxProject.class, Integer.parseInt(boxId));
			box.getMask().add(object);
			getApplicationService().saveOrUpdate(BoxProject.class, box);	
		}
		return new ModelAndView(getSuccessView()+"?id="+boxId+"&tabId="+tabId);
		
	}
}
