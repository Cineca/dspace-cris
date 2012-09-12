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

import it.cilea.hku.authority.model.ResearcherPage;
import it.cilea.hku.authority.model.RestrictedField;
import it.cilea.hku.authority.model.dynamicfield.BoxRPAdditionalFieldStorage;
import it.cilea.hku.authority.model.dynamicfield.DecoratorRPPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.EditTabRPAdditionalFieldStorage;
import it.cilea.hku.authority.model.dynamicfield.RPAdditionalFieldStorage;
import it.cilea.hku.authority.model.dynamicfield.RPPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.RPProperty;
import it.cilea.hku.authority.model.dynamicfield.VisibilityTabConstant;
import it.cilea.hku.authority.util.ResearcherPageUtils;
import it.cilea.hku.authority.webui.dto.RPAnagraficaObjectDTO;
import it.cilea.osd.jdyna.dto.AnagraficaObjectAreaDTO;
import it.cilea.osd.jdyna.model.AnagraficaObject;
import it.cilea.osd.jdyna.util.AnagraficaUtils;
import it.cilea.osd.jdyna.web.IContainable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFilter;
import org.apache.commons.lang.StringUtils;
import org.dspace.app.webui.util.UIUtil;
import org.dspace.authorize.AuthorizeException;
import org.dspace.authorize.AuthorizeManager;
import org.dspace.core.ConfigurationManager;
import org.dspace.core.Context;
import org.dspace.core.Utils;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

public class FormRPDynamicMetadataController
		extends
		AFormDynamicRPController<RPProperty, RPPropertiesDefinition, BoxRPAdditionalFieldStorage, EditTabRPAdditionalFieldStorage, AnagraficaObject<RPProperty, RPPropertiesDefinition>> {


	@Override
	protected Map referenceData(HttpServletRequest request, Object command,
			Errors errors) throws Exception {

		//call super method 
		Map<String, Object> map = super.referenceData(request);
		
		//this map contains key-values pairs, key = box shortname and values = collection of metadata
		Map<String, List<IContainable>> mapBoxToContainables = new HashMap<String, List<IContainable>>();

		AnagraficaObjectAreaDTO anagraficaObjectDTO = (AnagraficaObjectAreaDTO) command;

		//check admin authorization
		boolean isAdmin = false;
		Context context = UIUtil.obtainContext(request);
		if (AuthorizeManager.isAdmin(context)) {
			isAdmin = true;
		}

		//collection of edit tabs (all edit tabs created on system associate to visibility)
		List<EditTabRPAdditionalFieldStorage> tabs = getApplicationService()
				.getTabsByVisibility(EditTabRPAdditionalFieldStorage.class,
						isAdmin);
		
		//check if request tab from view is active (check on collection before)  
		EditTabRPAdditionalFieldStorage editT = getApplicationService().get(
				EditTabRPAdditionalFieldStorage.class,
				anagraficaObjectDTO.getTabId());
		if (!tabs.contains(editT)) {
			throw new AuthorizeException(
					"You not have needed authorization level to display this tab");
		}
		
		//collection of boxs
		List<BoxRPAdditionalFieldStorage> propertyHolders = new LinkedList<BoxRPAdditionalFieldStorage>();
		
		//if edit tab got a display tab (edit tab is hookup to display tab) then edit box will be created from display box otherwise get all boxs in edit tab  
		if (editT.getDisplayTab() != null) {
			for (BoxRPAdditionalFieldStorage box : editT.getDisplayTab()
					.getMask()) {				
				propertyHolders.add(box);
			}
		} else {
			propertyHolders = getApplicationService().findPropertyHolderInTab(
					getClazzTab(), anagraficaObjectDTO.getTabId());
		}

		//clean boxs list with accesslevel
		List<BoxRPAdditionalFieldStorage> propertyHoldersCurrentAccessLevel = new LinkedList<BoxRPAdditionalFieldStorage>();
		for(BoxRPAdditionalFieldStorage propertyHolder : propertyHolders) {
			if(isAdmin) {				
				if(!propertyHolder.getVisibility().equals(VisibilityTabConstant.LOW)) {
					propertyHoldersCurrentAccessLevel.add(propertyHolder);
				}
			}
			else {
				if(!propertyHolder.getVisibility().equals(VisibilityTabConstant.ADMIN)) {
					propertyHoldersCurrentAccessLevel.add(propertyHolder);
				}
			}		
		}
		Collections.sort(propertyHoldersCurrentAccessLevel);
		//this piece of code get containables object from boxs and put them on map
		List<IContainable> pDInTab = new LinkedList<IContainable>();
		for (BoxRPAdditionalFieldStorage iph : propertyHoldersCurrentAccessLevel) {
				List<IContainable> temp = getApplicationService()
						.<BoxRPAdditionalFieldStorage, it.cilea.osd.jdyna.web.Tab<BoxRPAdditionalFieldStorage>>findContainableInPropertyHolder(getClazzBox(),
								iph.getId());
				mapBoxToContainables.put(iph.getShortName(), temp);
				pDInTab.addAll(temp);
		}

		map.put("propertiesHolders", propertyHoldersCurrentAccessLevel);
		map.put("propertiesDefinitionsInTab", pDInTab);
		map.put("propertiesDefinitionsInHolder", mapBoxToContainables);
		map.put("tabList", tabs);
		map.put("simpleNameAnagraficaObject", getClazzAnagraficaObject()
				.getSimpleName());
		return map;
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		String paramTabId = request.getParameter("tabId");
		String paramId = request.getParameter("id");

		Integer id = null;
		Boolean isAdmin = false;
		if (paramId != null) {
			id = Integer.parseInt(paramId);
		}
		ResearcherPage researcher = getApplicationService().get(
				ResearcherPage.class, id);
		Context context = UIUtil.obtainContext(request);
		if ((context.getCurrentUser().getNetid()!=null && !context.getCurrentUser().getNetid()
				.equalsIgnoreCase(researcher.getStaffNo()))
				&& !AuthorizeManager.isAdmin(context)) {
			throw new AuthorizeException(
					"Only system admin can edit not personal researcher page");
		}

		if (AuthorizeManager.isAdmin(context)) {
			isAdmin = true;
		}

		List<EditTabRPAdditionalFieldStorage> tabs = getApplicationService()
				.getTabsByVisibility(EditTabRPAdditionalFieldStorage.class,
						isAdmin);

		Integer areaId;
		if (paramTabId == null) {
			if (tabs.isEmpty()) {
				throw new AuthorizeException("No tabs defined!!");
			}
			areaId = tabs.get(0).getId();
		} else {
			areaId = Integer.parseInt(paramTabId);
		}

		RPAdditionalFieldStorage dynamicObject = researcher.getDynamicField();

		EditTabRPAdditionalFieldStorage editT = getApplicationService().get(
				EditTabRPAdditionalFieldStorage.class, areaId);
		List<BoxRPAdditionalFieldStorage> propertyHolders = new LinkedList<BoxRPAdditionalFieldStorage>();
		if (editT.getDisplayTab() != null) {
			for (BoxRPAdditionalFieldStorage box : editT.getDisplayTab()
					.getMask()) {
				propertyHolders.add(box);
			}
		} else {
			propertyHolders = getApplicationService().findPropertyHolderInTab(
					getClazzTab(), areaId);
		}

		List<IContainable> tipProprietaInArea = new LinkedList<IContainable>();

		for (BoxRPAdditionalFieldStorage iph : propertyHolders) {
			if (editT.getDisplayTab() != null) {
				tipProprietaInArea.addAll(getApplicationService()
						.<BoxRPAdditionalFieldStorage, it.cilea.osd.jdyna.web.Tab<BoxRPAdditionalFieldStorage>>findContainableInPropertyHolder(
								BoxRPAdditionalFieldStorage.class,
								iph.getId()));
			} else {
				tipProprietaInArea.addAll(getApplicationService()
						.<BoxRPAdditionalFieldStorage, it.cilea.osd.jdyna.web.Tab<BoxRPAdditionalFieldStorage>>findContainableInPropertyHolder(getClazzBox(),
								iph.getId()));
			}
		}

		RPAnagraficaObjectDTO anagraficaObjectDTO = new RPAnagraficaObjectDTO(
				researcher);
		anagraficaObjectDTO.setTabId(areaId);
		anagraficaObjectDTO.setObjectId(dynamicObject.getId());
		anagraficaObjectDTO.setParentId(researcher.getId());

		List<RPPropertiesDefinition> realTPS = new LinkedList<RPPropertiesDefinition>();
		List<IContainable> structuralField = new LinkedList<IContainable>();
		for (IContainable c : tipProprietaInArea) {
			RPPropertiesDefinition rpPd = getApplicationService()
					.findPropertiesDefinitionByShortName(
							RPPropertiesDefinition.class, c.getShortName());
			if (rpPd != null) {
				realTPS.add(((DecoratorRPPropertiesDefinition) getApplicationService()
						.findContainableByDecorable(
								getClazzTipologiaProprieta().newInstance()
										.getDecoratorClass(), c.getId()))
						.getReal());
			} else {
				structuralField.add(c);
			}
		}
		AnagraficaUtils.fillDTO(anagraficaObjectDTO, dynamicObject, realTPS);
		return anagraficaObjectDTO;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object object, BindException errors)
			throws Exception {
		RPAnagraficaObjectDTO anagraficaObjectDTO = (RPAnagraficaObjectDTO) object;

		String exitPage = "redirect:/rp/tools/editDynamicData.htm?id="
				+ +anagraficaObjectDTO.getParentId();

		
		EditTabRPAdditionalFieldStorage editT = getApplicationService().get(
				EditTabRPAdditionalFieldStorage.class,
				anagraficaObjectDTO.getTabId());
		if (anagraficaObjectDTO.getNewTabId() != null) {
			exitPage += "&tabId=" + anagraficaObjectDTO.getNewTabId();
		} else {
			exitPage = "redirect:/rp/"
				+ ResearcherPageUtils.getPersistentIdentifier(anagraficaObjectDTO.getParentId()) + "/" + editT.getShortName().substring(4) + ".html";
		}

		if (request.getParameter("cancel") != null) {
			return new ModelAndView(exitPage);
		}
		ResearcherPage researcher = getApplicationService().get(
				ResearcherPage.class, anagraficaObjectDTO.getParentId());
		RPAdditionalFieldStorage myObject = researcher.getDynamicField();

		
		List<BoxRPAdditionalFieldStorage> propertyHolders = new LinkedList<BoxRPAdditionalFieldStorage>();
		if (editT.getDisplayTab() != null) {
			for (BoxRPAdditionalFieldStorage box : editT.getDisplayTab()
					.getMask()) {				
				propertyHolders.add(box);
			}
		} else {
			propertyHolders = getApplicationService().findPropertyHolderInTab(
					getClazzTab(), anagraficaObjectDTO.getTabId());
		}

		List<IContainable> tipProprietaInArea = new LinkedList<IContainable>();

		for (BoxRPAdditionalFieldStorage iph : propertyHolders) {
			
				tipProprietaInArea.addAll(getApplicationService()
						.<BoxRPAdditionalFieldStorage, it.cilea.osd.jdyna.web.Tab<BoxRPAdditionalFieldStorage>>findContainableInPropertyHolder(getClazzBox(),
								iph.getId()));
			
		}

		List<RPPropertiesDefinition> realTPS = new LinkedList<RPPropertiesDefinition>();
		List<IContainable> structuralField = new LinkedList<IContainable>();
		for (IContainable c : tipProprietaInArea) {
			RPPropertiesDefinition rpPd = getApplicationService()
					.findPropertiesDefinitionByShortName(
							RPPropertiesDefinition.class, c.getShortName());
			if (rpPd != null) {
				realTPS.add(((DecoratorRPPropertiesDefinition) getApplicationService()
						.findContainableByDecorable(
								getClazzTipologiaProprieta().newInstance()
										.getDecoratorClass(), c.getId()))
						.getReal());
			} else {
				structuralField.add(c);
			}
		}

		AnagraficaUtils.reverseDTO(anagraficaObjectDTO, myObject, realTPS);

		myObject.pulisciAnagrafica();

		String rp = ResearcherPageUtils.getPersistentIdentifier(researcher);

		String deleteImage_s = request.getParameter("deleteImage");		

        if (deleteImage_s != null)
        {
            Boolean deleteImage = Boolean.parseBoolean(deleteImage_s);
            if (deleteImage)
            {
                ResearcherPageUtils.removePicture(researcher);
            }
        }
        
        String deleteCV_s = request.getParameter("deleteCV");

		if ((deleteCV_s != null && Boolean.parseBoolean(deleteCV_s))
				|| StringUtils
						.isNotEmpty(researcher.getCv().getRemoteUrl()))
        {
            ResearcherPageUtils.removeCVFiles(researcher);   
        }
		
		List<RestrictedField> list_interest = new LinkedList<RestrictedField>();
		for (RestrictedField rf : researcher.getInterests()) {
			if (rf == null || rf.getValue() == null || rf.getValue().equals("")) {
				log.info("Discard value from lazy list of interests");
			} else {
				list_interest.add(rf);
			}
		}

		List<RestrictedField> list_variants = new LinkedList<RestrictedField>();
		for (RestrictedField rf : researcher.getVariants()) {
			if (rf == null || rf.getValue() == null || rf.getValue().equals("")) {
				log.info("Discard value from lazy list of variants");
			} else {
				list_variants.add(rf);
			}
		}

		List<RestrictedField> list_titles = new LinkedList<RestrictedField>();
		for (RestrictedField rf : researcher.getTitle()) {
			if (rf == null || rf.getValue() == null || rf.getValue().equals("")) {
				log.info("Discard value from lazy list of title");
			} else {
				list_titles.add(rf);
			}
		}

        MultipartFile itemImage = researcher.getPict().getFile();
        if (itemImage != null && !itemImage.getOriginalFilename().isEmpty())
        {
        	ResearcherPageUtils.loadImg(researcher, rp, itemImage);
        }

        MultipartFile itemCV = researcher.getCv().getFile();
        
        // if there is a remote url we don't upload the file 
        if (StringUtils.isEmpty(researcher.getCv().getRemoteUrl()) && 
        		itemCV != null && !itemCV.getOriginalFilename().isEmpty())
        {
           ResearcherPageUtils.loadCv(researcher, rp, itemCV);
        }

		researcher.setInterests(list_interest);
		researcher.setVariants(list_variants);
		researcher.setTitle(list_titles);

		List<RestrictedField> list_media = new LinkedList<RestrictedField>();
		for (RestrictedField rf : researcher.getMedia()) {
			if (rf == null || rf.getValue() == null || rf.getValue().equals("")) {
				log.info("Discard value from lazy list of media");
			} else {
				list_media.add(rf);
			}
		}

		List<RestrictedField> list_spokenEN = new LinkedList<RestrictedField>();
		for (RestrictedField rf : researcher.getSpokenLanguagesEN()) {
			if (rf == null || rf.getValue() == null || rf.getValue().equals("")) {
				log.info("Discard value from lazy list of spoken languages EN");
			} else {
				list_spokenEN.add(rf);
			}
		}

		List<RestrictedField> list_spokenZH = new LinkedList<RestrictedField>();
		for (RestrictedField rf : researcher.getSpokenLanguagesZH()) {
			if (rf == null || rf.getValue() == null || rf.getValue().equals("")) {
				log.info("Discard value from lazy list of spoken languages ZH");
			} else {
				list_spokenZH.add(rf);
			}
		}

		List<RestrictedField> list_writtenEN = new LinkedList<RestrictedField>();
		for (RestrictedField rf : researcher.getWrittenLanguagesEN()) {
			if (rf == null || rf.getValue() == null || rf.getValue().equals("")) {
				log.info("Discard value from lazy list of written languages EN");
			} else {
				list_writtenEN.add(rf);
			}
		}

		List<RestrictedField> list_writtenZH = new LinkedList<RestrictedField>();
		for (RestrictedField rf : researcher.getWrittenLanguagesZH()) {
			if (rf == null || rf.getValue() == null || rf.getValue().equals("")) {
				log.info("Discard value from lazy list of written languages ZH");
			} else {
				list_writtenZH.add(rf);
			}
		}
		researcher.setMedia(list_media);
		researcher.setSpokenLanguagesEN(list_spokenEN);
		researcher.setSpokenLanguagesZH(list_spokenZH);
		researcher.setWrittenLanguagesEN(list_writtenEN);
		researcher.setWrittenLanguagesZH(list_writtenZH);
		
		
		researcher.setFullName(anagraficaObjectDTO.getFullName());		
		researcher.setStaffNo(anagraficaObjectDTO.getStaffNo());	
		researcher.setUrlPict(anagraficaObjectDTO.getUrlPict());
				

		getApplicationService().saveOrUpdate(ResearcherPage.class,
				researcher);
		EditTabRPAdditionalFieldStorage area = getApplicationService().get(
				getClazzTab(), anagraficaObjectDTO.getTabId());
		final String areaTitle = area.getTitle();
		saveMessage(
				request,
				getText("action.anagrafica.edited", new Object[] { areaTitle },
						request.getLocale()));

		return new ModelAndView(exitPage);
	}


	@Override
	protected void onBindAndValidate(HttpServletRequest request,
			Object command, BindException errors) throws Exception {

		AnagraficaObjectAreaDTO dto = (AnagraficaObjectAreaDTO) command;
		ResearcherPage researcher = getApplicationService().get(
				ResearcherPage.class, dto.getParentId());
		RPAdditionalFieldStorage myObject = researcher.getDynamicField();

		EditTabRPAdditionalFieldStorage editT = getApplicationService().get(
				EditTabRPAdditionalFieldStorage.class, dto.getTabId());
		List<BoxRPAdditionalFieldStorage> propertyHolders = new LinkedList<BoxRPAdditionalFieldStorage>();
		if (editT.getDisplayTab() != null) {
			for (BoxRPAdditionalFieldStorage box : editT.getDisplayTab()
					.getMask()) {
				propertyHolders.add(box);
			}
		} else {
			propertyHolders = getApplicationService().findPropertyHolderInTab(
					getClazzTab(), dto.getTabId());
		}

		List<IContainable> tipProprietaInArea = new LinkedList<IContainable>();

		for (BoxRPAdditionalFieldStorage iph : propertyHolders) {

				tipProprietaInArea.addAll(getApplicationService()
						.<BoxRPAdditionalFieldStorage, it.cilea.osd.jdyna.web.Tab<BoxRPAdditionalFieldStorage>>findContainableInPropertyHolder(getClazzBox(),
								iph.getId()));

		}

		List<RPPropertiesDefinition> realTPS = new LinkedList<RPPropertiesDefinition>();
		List<IContainable> structuralField = new LinkedList<IContainable>();
		for (IContainable c : tipProprietaInArea) {
			RPPropertiesDefinition rpPd = getApplicationService()
					.findPropertiesDefinitionByShortName(
							RPPropertiesDefinition.class, c.getShortName());
			if (rpPd != null) {
				realTPS.add(rpPd);
			} else {
				structuralField.add(c);
			}
		}
		AnagraficaUtils.reverseDTO(dto, myObject, realTPS);
		AnagraficaUtils.fillDTO(dto, myObject, realTPS);
	}


}
