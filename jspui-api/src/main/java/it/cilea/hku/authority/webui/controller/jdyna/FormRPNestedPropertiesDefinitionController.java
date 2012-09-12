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

import it.cilea.hku.authority.model.dynamicfield.BoxRPAdditionalFieldStorage;
import it.cilea.hku.authority.model.dynamicfield.DecoratorRPPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.RPPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.TabRPAdditionalFieldStorage;
import it.cilea.hku.authority.model.dynamicfield.widget.WidgetComboRPAdditional;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

public class FormRPNestedPropertiesDefinitionController
		extends
		AFormDecoratorPropertiesDefinitionController<WidgetComboRPAdditional, RPPropertiesDefinition, DecoratorRPPropertiesDefinition, BoxRPAdditionalFieldStorage, TabRPAdditionalFieldStorage> {

	private final String TYPO_ADDTEXT = "text";
	private final String TYPO_ADDDATE = "date";
	private final String TYPO_ADDLINK = "link";
	private final String TYPO_ADDFILE = "file";

	private String addTextView;
	private String addDateView;
	private String addLinkView;
	private String addFileView;

	public FormRPNestedPropertiesDefinitionController(
			Class<RPPropertiesDefinition> targetModel,
			Class<WidgetComboRPAdditional> renderingModel) {
		super(targetModel, renderingModel);
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		String boxId = request.getParameter("boxId");
		String tabId = request.getParameter("tabId");
		DecoratorRPPropertiesDefinition object = (DecoratorRPPropertiesDefinition) command;
		getApplicationService()
				.saveOrUpdate(DecoratorRPPropertiesDefinition.class, object);
		
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
