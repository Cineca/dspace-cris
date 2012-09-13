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
package it.cilea.hku.authority.webui.web.tag;

import it.cilea.hku.authority.model.ResearcherGrant;
import it.cilea.hku.authority.model.ResearcherPage;
import it.cilea.hku.authority.model.RestrictedField;
import it.cilea.hku.authority.model.dynamicfield.BoxRPAdditionalFieldStorage;
import it.cilea.hku.authority.model.dynamicfield.BoxResearcherGrant;
import it.cilea.hku.authority.model.dynamicfield.DecoratorGrantPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.DecoratorRPPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.RPAdditionalFieldStorage;
import it.cilea.hku.authority.model.dynamicfield.RPPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.RPProperty;
import it.cilea.hku.authority.service.ApplicationService;
import it.cilea.hku.authority.util.ResearcherPageUtils;
import it.cilea.hku.authority.webui.dto.AllMonthsStatsDTO;
import it.cilea.osd.jdyna.dto.ValoreDTO;
import it.cilea.osd.jdyna.model.AWidget;
import it.cilea.osd.jdyna.model.AccessLevelConstants;
import it.cilea.osd.jdyna.model.AnagraficaSupport;
import it.cilea.osd.jdyna.model.Containable;
import it.cilea.osd.jdyna.model.IContainable;
import it.cilea.osd.jdyna.model.PropertiesDefinition;
import it.cilea.osd.jdyna.model.Property;
import it.cilea.osd.jdyna.value.MultiValue;
import it.cilea.osd.jdyna.web.Box;
import it.cilea.osd.jdyna.widget.WidgetCombo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dspace.core.ConfigurationManager;

public class ResearcherTagLibraryFunctions {

	private static ApplicationService applicationService;

	/**
	 * log4j category
	 */
	public static final Log log = LogFactory
			.getLog(ResearcherTagLibraryFunctions.class);

	public static boolean isGroupFieldsHidden(
			RPAdditionalFieldStorage anagraficaObject, String logicGroup) {
		boolean result = true;
		String dspaceProperty = "researcherpage.containables.box.logicgrouped."
				+ logicGroup;
		log.debug("Get from configuration additional containables object : "
				+ dspaceProperty);
		String confContainables = ConfigurationManager
				.getProperty(dspaceProperty);
		if (confContainables != null && !confContainables.isEmpty()) {
			String[] listConfContainables = confContainables.split(",");
			for (String cont : listConfContainables) {
				cont = cont.trim();
				for (RPProperty p : anagraficaObject.getAnagrafica4view().get(
						cont)) {
					boolean resultPiece = checkDynamicVisibility(
							anagraficaObject, p.getTypo().getShortName(), p
									.getTypo().getRendering(), p.getTypo());

					if (resultPiece == false) {
						return false;
					}
				}
			}
		}
		return result;

	}

	public static boolean isGroupFieldsHiddenWithStructural(
			ResearcherPage researcher, String logicGroup)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		boolean result = true;
		String dspaceProperty = "researcherpage.containables.box.logicgrouped."
				+ logicGroup + ".structural";
		log.debug("Get from configuration additional containables object : "
				+ dspaceProperty);
		String confContainables = ConfigurationManager
				.getProperty(dspaceProperty);

		if (confContainables != null && !confContainables.isEmpty()) {
			String[] listConfContainables = confContainables.split(",");
			for (String cont : listConfContainables) {
				cont = cont.trim();
				Method[] methods = researcher.getClass().getMethods();
				Object field = null;
				Method method = null;
				for (Method m : methods) {
					if (m.getName().toLowerCase()
							.equals("get" + cont.toLowerCase())) {
						field = m.invoke(researcher, null);
						method = m;
						break;
					}
				}
				if (method.getReturnType().isAssignableFrom(List.class)) {
					for (RestrictedField rr : (List<RestrictedField>) field) {

						if (rr.getVisibility() == 1) {
							if (rr.getValue() != null
									&& !rr.getValue().isEmpty()) {
								return false;
							}
						}

					}
				} else if (method.getReturnType()
						.isAssignableFrom(String.class)) {
					String rr = (String) field;
					if (rr != null && !rr.isEmpty()) {
						return false;
					}
				} else {
					RestrictedField rr = (RestrictedField) field;
					if (rr.getVisibility() == 1) {
						if (rr.getValue() != null && !rr.getValue().isEmpty()) {
							return false;
						}
					}
				}
			}
		}

		result = isGroupFieldsHidden(researcher.getDynamicField(), logicGroup);
		return result;

	}

	public static boolean isBoxHidden(ResearcherPage anagrafica, String boxName)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		BoxRPAdditionalFieldStorage box = applicationService.getBoxByShortName(
				BoxRPAdditionalFieldStorage.class, boxName);

		return isBoxHidden(anagrafica, box);

	}

	public static boolean isBoxHidden(ResearcherGrant anagrafica, String boxName)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		BoxResearcherGrant box = applicationService.getBoxByShortName(
				BoxResearcherGrant.class, boxName);

		return isBoxHiddenInternal(anagrafica, box);

	}
	
	
	public static boolean isBoxHidden(ResearcherPage anagrafica,
			BoxRPAdditionalFieldStorage box) {	
		return isBoxHiddenInternal(anagrafica.getDynamicField(), box) && isBoxHiddenWithStructural(anagrafica, box);
	}

	public static boolean isBoxHidden(ResearcherGrant anagrafica,
			BoxResearcherGrant box) {	
		return isBoxHiddenInternal(anagrafica, box);
	}
	
	public static <TP extends PropertiesDefinition, P extends Property<TP>, B extends Box<Containable>> boolean isBoxHiddenWithStructural(ResearcherPage anagrafica,
			B box) {
		
		boolean result = true;
		
		List<IContainable> containables = new LinkedList<IContainable>();

		applicationService.findOtherContainablesInBoxByConfiguration(
				box.getShortName(), containables);
		for (IContainable decorator : containables) {
			String shortName = decorator.getShortName();
			Method[] methods = anagrafica.getClass().getMethods();
			Object field = null;
			Method method = null;
			for (Method m : methods) {
				if (m.getName().toLowerCase()
						.equals("get" + shortName.toLowerCase())) {
					try {
						field = m.invoke(anagrafica, null);
					} catch (IllegalArgumentException e) {
						throw new RuntimeException(e.getMessage(), e);
					} catch (IllegalAccessException e) {
						throw new RuntimeException(e.getMessage(), e);
					} catch (InvocationTargetException e) {
						throw new RuntimeException(e.getMessage(), e);
					}
					method = m;
					break;
				}
			}
			if (method.getReturnType().isAssignableFrom(List.class)) {

				for (RestrictedField rr : (List<RestrictedField>) field) {

					if (rr.getVisibility() == 1) {
						return false;
					}
				}

			} else if (method.getReturnType().isAssignableFrom(String.class)) {
				return false;
			} else {
				RestrictedField rr = (RestrictedField) field;
				if (rr.getVisibility() == 1) {
					return false;
				}
			}
		}
		return result;
	}
	
	public static <TP extends PropertiesDefinition, P extends Property<TP>, B extends Box<Containable>> boolean isBoxHiddenInternal(AnagraficaSupport<P, TP> anagrafica,
			B box) {
		
		boolean result = true;
		
		List<IContainable> containables = new LinkedList<IContainable>();

		containables.addAll(box.getMask());

		for (IContainable cont : containables) {

			if (cont instanceof DecoratorRPPropertiesDefinition) {
				DecoratorRPPropertiesDefinition decorator = (DecoratorRPPropertiesDefinition) cont;
				boolean resultPiece = checkDynamicVisibility(
						anagrafica, decorator.getShortName(),
						decorator.getRendering(), (TP)decorator.getReal());
				if (resultPiece == false) {
					return false;
				}
			}
			

			if (cont instanceof DecoratorGrantPropertiesDefinition) {
				DecoratorGrantPropertiesDefinition decorator = (DecoratorGrantPropertiesDefinition) cont;
				boolean resultPiece = checkDynamicVisibility(
						anagrafica, decorator.getShortName(),
						decorator.getRendering(), (TP)decorator.getReal());
				if (resultPiece == false) {
					return false;
				}
			}

		}
				
		return result;
	}


	private static <TP extends PropertiesDefinition, P extends Property<TP>> boolean checkDynamicVisibility(
			AnagraficaSupport<P, TP> anagrafica, String shortname,
			AWidget rendering, TP rpPropertiesDefinition) {
		if (rendering instanceof WidgetCombo) {
			List<P> proprietaDellaTipologia = anagrafica
					.getProprietaDellaTipologia(rpPropertiesDefinition);
			List<TP> subTypeCombo = ((WidgetCombo) rendering)
					.getSottoTipologie();
			for (P prop : proprietaDellaTipologia) {

				for (TP tp : subTypeCombo) {
					List<ValoreDTO> proprietaDellaSubTipologia = anagrafica
							.getProprietaDellaTipologiaInValoreMulti(
									(MultiValue) prop
											.getValue(), tp);
					for (ValoreDTO p : proprietaDellaSubTipologia) {
						if (p.getVisibility() == true) {
							return false;
						}
					}
				}

			}
		} else {
			for (P p : anagrafica.getAnagrafica4view().get(shortname)) {
				if (p.getVisibility() == 1) {
					return false;
				}
			}
		}
		return true;
	}

	public static <H extends Box<Containable>> boolean isThereMetadataNoEditable(String boxName, Class<H> model)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		boolean result = false;
		List<IContainable> containables = new LinkedList<IContainable>();
		H box = applicationService.getBoxByShortName(
				model, boxName);
		containables.addAll(box.getMask());
		applicationService.findOtherContainablesInBoxByConfiguration(boxName,
				containables);

		for (IContainable decorator : containables) {

			if (decorator.getAccessLevel().equals(
					AccessLevelConstants.STANDARD_ACCESS)
					|| decorator.getAccessLevel().equals(
							AccessLevelConstants.LOW_ACCESS)) {
				return true;
			}

		}
		return result;

	}

	public static List<AllMonthsStatsDTO> getAllMonthsStats(Object object)
			throws ParseException {

		String[][] temp = (String[][]) object;
		List<AllMonthsStatsDTO> result = new LinkedList<AllMonthsStatsDTO>();

		Map<String, List<String>> tempMap = new HashMap<String, List<String>>();
		boolean foundit = false;
		int countIntegrityMonth = 12;
		for (int i = 0; i < temp.length; i++) {

			String tempKey = temp[i][0];
			String key = tempKey.substring(0, 4);
			if (!tempMap.containsKey(key)) {
				tempMap.put(key, new LinkedList<String>());
				if (countIntegrityMonth > 0 && countIntegrityMonth != 12
						&& i > 0) {
					while (countIntegrityMonth != 0) {
						String check = temp[i - 1][0].substring(0, 4);
						List<String> array = tempMap.get(check);
						array.add(0, null);
						countIntegrityMonth--;
					}
					countIntegrityMonth = 12;
				}
			}
			countIntegrityMonth--;
			List<String> array = tempMap.get(key);
			array.add(temp[i][1]);

			if (countIntegrityMonth > 0 && i == temp.length - 1) {
				while (countIntegrityMonth != 0) {
					array.add(null);
					countIntegrityMonth--;
				}
			}
			if (countIntegrityMonth == 0) {
				countIntegrityMonth = 12;
			}
		}

		for (String key : tempMap.keySet()) {
			AllMonthsStatsDTO dto = new AllMonthsStatsDTO();
			dto.setYear(key);
			Integer total = 0;
			List<String> tempToken = tempMap.get(key);
			for (String token : tempToken) {
				if (token != null && !token.isEmpty()) {
					Integer addendum = Integer.parseInt(token);
					total += addendum;
					if (addendum > 0) {
						foundit = true;
					}
				}
			}
			if (foundit == true) {
				dto.setJan(tempToken.get(0));
				dto.setFeb(tempToken.get(1));
				dto.setMar(tempToken.get(2));
				dto.setApr(tempToken.get(3));
				dto.setMay(tempToken.get(4));
				dto.setJun(tempToken.get(5));
				dto.setJul(tempToken.get(6));
				dto.setAug(tempToken.get(7));
				dto.setSep(tempToken.get(8));
				dto.setOct(tempToken.get(9));
				dto.setNov(tempToken.get(10));
				dto.setDec(tempToken.get(11));
				dto.setTotal(total);
				result.add(dto);
			}
		}
		Collections.sort(result);
		return result;
	}

	public static int countBoxPublicMetadata(ResearcherPage anagrafica,
			String boxName, Boolean onlyComplexValue)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		BoxRPAdditionalFieldStorage box = applicationService.getBoxByShortName(
				BoxRPAdditionalFieldStorage.class, boxName);

		return countBoxPublicMetadata(anagrafica, box, onlyComplexValue);

	}

	public static int countBoxPublicMetadata(ResearcherPage anagrafica,
			BoxRPAdditionalFieldStorage box, boolean onlyComplexValue) {
		int result = 0;
		List<IContainable> containables = new LinkedList<IContainable>();

		containables.addAll(box.getMask());

		for (IContainable cont : containables) {

			if (cont instanceof DecoratorRPPropertiesDefinition) {
				DecoratorRPPropertiesDefinition decorator = (DecoratorRPPropertiesDefinition) cont;
				result += countDynamicPublicMetadata(
						anagrafica.getDynamicField(), decorator.getShortName(),
						decorator.getRendering(), decorator.getReal(),
						onlyComplexValue);
			}

		}

		if (!onlyComplexValue) {
			containables = new LinkedList<IContainable>();
			applicationService.findOtherContainablesInBoxByConfiguration(
					box.getShortName(), containables);
			for (IContainable decorator : containables) {
				String shortName = decorator.getShortName();
				Method[] methods = anagrafica.getClass().getMethods();
				Object field = null;
				Method method = null;
				for (Method m : methods) {
					if (m.getName().toLowerCase()
							.equals("get" + shortName.toLowerCase())) {
						try {
							field = m.invoke(anagrafica, null);
						} catch (IllegalArgumentException e) {
							throw new RuntimeException(e.getMessage(), e);
						} catch (IllegalAccessException e) {
							throw new RuntimeException(e.getMessage(), e);
						} catch (InvocationTargetException e) {
							throw new RuntimeException(e.getMessage(), e);
						}
						method = m;
						break;
					}
				}
				if (method.getReturnType().isAssignableFrom(List.class)) {

					for (RestrictedField rr : (List<RestrictedField>) field) {

						if (rr.getVisibility() == 1) {
							result++;
						}
					}

				} else if (method.getReturnType()
						.isAssignableFrom(String.class)) {
					result++;
				} else {
					RestrictedField rr = (RestrictedField) field;
					if (rr.getVisibility() == 1) {
						result++;
					}
				}
			}
		}
		return result;
	}

	private static int countDynamicPublicMetadata(
			RPAdditionalFieldStorage anagrafica, String shortname,
			AWidget rendering, RPPropertiesDefinition rpPropertiesDefinition,
			boolean onlyComplexValue) {
		int result = 0;
		if (rendering instanceof WidgetCombo) {
			List<RPProperty> proprietaDellaTipologia = anagrafica
					.getProprietaDellaTipologia(rpPropertiesDefinition);
			List<RPPropertiesDefinition> subTypeCombo = ((WidgetCombo) rendering)
					.getSottoTipologie();
			for (RPProperty prop : proprietaDellaTipologia) {

				row: for (RPPropertiesDefinition tp : subTypeCombo) {
					List<ValoreDTO> proprietaDellaSubTipologia = anagrafica
							.getProprietaDellaTipologiaInValoreMulti(
									(MultiValue) prop
											.getValue(), tp);
					cell: for (ValoreDTO p : proprietaDellaSubTipologia) {
						if (p.getVisibility() == true) {
							result++;
							break row;
						}
					}
				}

			}
		} else {
			if (!onlyComplexValue) {
				for (RPProperty p : anagrafica.getAnagrafica4view().get(
						shortname)) {
					if (p.getVisibility() == 1) {
						result++;
					}
				}
			}
		}
		return result;
	}

	public void setApplicationService(ApplicationService applicationService) {
		ResearcherTagLibraryFunctions.applicationService = applicationService;
	}

	public static ApplicationService getApplicationService() {
		return applicationService;
	}

	public static String rpkey(Integer id) {
		return ResearcherPageUtils.getPersistentIdentifier(id);
	}

	public static <TP extends PropertiesDefinition, P extends Property<TP>> List<Containable<P>> sortContainableByComparator(
			List<Containable<P>> containables, String comparatorName)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {

		Comparator comparator = (Comparator) Class.forName(comparatorName)
				.newInstance();
		Collections.sort(containables, comparator);
		return containables;

	}

	public static <TP extends PropertiesDefinition, P extends Property<TP>, C extends Containable<P>> List<Box<C>> sortBoxByComparator(
			List<Box<C>> boxs, String comparatorName)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		Comparator comparator = (Comparator) Class.forName(comparatorName)
				.newInstance();
		Collections.sort(boxs, comparator);
		return boxs;

	}

}
