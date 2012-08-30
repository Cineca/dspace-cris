/**
 * <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
 * <html><head>
 * <title>301 Moved Permanently</title>
 * </head><body>
 * <h1>Moved Permanently</h1>
 * <p>The document has moved <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>.</p>
 * </body></html>
 */
package it.cilea.hku.authority.webui.controller.jdyna;

import it.cilea.hku.authority.model.dynamicfield.BoxResearcherGrant;
import it.cilea.hku.authority.model.dynamicfield.DecoratorGrantPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.GrantPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.TabResearcherGrant;
import it.cilea.hku.authority.model.dynamicfield.widget.WidgetComboResearcherGrant;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

public class FormGrantNestedPropertiesDefinitionController
		extends
		AFormDecoratorPropertiesDefinitionController<WidgetComboResearcherGrant, GrantPropertiesDefinition, DecoratorGrantPropertiesDefinition, BoxResearcherGrant, TabResearcherGrant> {

	private final String TYPO_ADDTEXT = "text";
	private final String TYPO_ADDDATE = "date";
	private final String TYPO_ADDLINK = "link";
	private final String TYPO_ADDFILE = "file";

	private String addTextView;
	private String addDateView;
	private String addLinkView;
	private String addFileView;

	public FormGrantNestedPropertiesDefinitionController(
			Class<GrantPropertiesDefinition> targetModel,
			Class<WidgetComboResearcherGrant> renderingModel) {
		super(targetModel, renderingModel);
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		String boxId = request.getParameter("boxId");
		String tabId = request.getParameter("tabId");
		DecoratorGrantPropertiesDefinition object = (DecoratorGrantPropertiesDefinition) command;
		getApplicationService()
				.saveOrUpdate(DecoratorGrantPropertiesDefinition.class, object);
		
		Map<String, String> maprequest = request.getParameterMap();
		
		if (maprequest.containsKey(TYPO_ADDTEXT)) {
			return new ModelAndView(addTextView.trim() + "?renderingparent="
					+ object.getRendering().getId() + "&boxId="+boxId+"&tabId="+tabId);
		}
		if (maprequest.containsKey(TYPO_ADDDATE)) {
			return new ModelAndView(addDateView.trim() + "?renderingparent="
					+ object.getRendering().getId()+ "&boxId="+boxId+"&tabId="+tabId);
		}
		if (maprequest.containsKey(TYPO_ADDLINK)) {
			return new ModelAndView(addLinkView.trim() + "?renderingparent="
					+ object.getRendering().getId()+ "&boxId="+boxId+"&tabId="+tabId);
		}
		if (maprequest.containsKey(TYPO_ADDFILE)) {
			return new ModelAndView(addFileView.trim() + "?renderingparent="
					+ object.getRendering().getId()+ "&boxId="+boxId+"&tabId="+tabId);
		}
		
		if(boxId!=null && !boxId.isEmpty()) {
			BoxResearcherGrant box = getApplicationService().get(BoxResearcherGrant.class, Integer.parseInt(boxId));
			box.getMask().add(object);
			getApplicationService().saveOrUpdate(BoxResearcherGrant.class, box);	
		}
		return new ModelAndView(getSuccessView()+"?id="+boxId+"&tabId="+tabId);
	}

	public String getAddTextView() {
		return addTextView;
	}

	public void setAddTextView(String addTextView) {
		this.addTextView = addTextView;
	}

	public String getAddDateView() {
		return addDateView;
	}

	public void setAddDateView(String addDateView) {
		this.addDateView = addDateView;
	}

	public String getAddLinkView() {
		return addLinkView;
	}

	public void setAddLinkView(String addLinkView) {
		this.addLinkView = addLinkView;
	}

	public void setAddFileView(String addFileView) {
		this.addFileView = addFileView;
	}

	public String getAddFileView() {
		return addFileView;
	}
}
