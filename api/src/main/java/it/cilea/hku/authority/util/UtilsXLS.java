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
package it.cilea.hku.authority.util;

import it.cilea.hku.authority.model.ResearcherPage;
import it.cilea.hku.authority.model.RestrictedField;
import it.cilea.hku.authority.model.RestrictedFieldFile;
import it.cilea.hku.authority.model.RestrictedFieldLocalOrRemoteFile;
import it.cilea.hku.authority.model.VisibilityConstants;
import it.cilea.hku.authority.model.dynamicfield.DecoratorRPPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.DecoratorRestrictedField;
import it.cilea.hku.authority.model.dynamicfield.RPPropertiesDefinition;
import it.cilea.hku.authority.model.dynamicfield.RPProperty;
import it.cilea.hku.authority.service.ApplicationService;
import it.cilea.osd.jdyna.model.AWidget;
import it.cilea.osd.jdyna.widget.WidgetCombo;

import java.beans.PropertyEditor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.lang.StringUtils;

public class UtilsXLS {

	/**
	 * Characters sequence used to split multiple values in repeatable field
	 * escaped for use in regex expression
	 */
	public static final String ESCAPE_STOPFIELDS_EXCEL = "\\|\\|\\|";

	/**
	 * Characters sequence used to split multiple values in repeatable field
	 */
	public static final String STOPFIELDS_EXCEL = "|||";

	public static int createCell(ApplicationService applicationService, int y,
			int i, DecoratorRPPropertiesDefinition decorator,
			ResearcherPage researcher, WritableSheet sheet) throws IOException,
			RowsExceededException, WriteException {
		return createElement(applicationService, y, i, decorator.getReal(),
				decorator.getRendering(), researcher, sheet);
	}

	private static int createElement(ApplicationService applicationService,
			int y, int i, RPPropertiesDefinition real, AWidget rendering,
			ResearcherPage researcher, WritableSheet sheet) throws IOException,
			RowsExceededException, WriteException {
		if (rendering instanceof WidgetCombo) {
			return createElement(applicationService, y, i, real,
					(WidgetCombo) rendering, researcher, sheet);
		} else {
			return createSimpleElement(applicationService, y, i,
					real.getShortName(), researcher.getDynamicField()
							.getProprietaDellaTipologia(real), sheet);
		}
	}

	private static int createElement(ApplicationService applicationService,
			int y, int i, RPPropertiesDefinition tp,
			WidgetCombo<RPProperty, RPPropertiesDefinition> rendering,
			ResearcherPage rp, WritableSheet sheet) throws RowsExceededException, WriteException {
		
		String field_value = "";
		String field_visibility = "";
		boolean first = true;
		for (RPProperty rr : rp.getDynamicField().getProprietaDellaTipologia(tp)) {

			if (!first) {
				field_value += STOPFIELDS_EXCEL;
			}
			field_value += rendering.toString(rr.getObject());
			if (!first) {
				field_visibility += STOPFIELDS_EXCEL;
			}
			field_visibility += VisibilityConstants.getDescription(rr
					.getVisibility());
			first = false;
		}

		y = y + 1;
		Label label_v = new Label(y, i, field_value);
		sheet.addCell(label_v);
		Label labelCaption = new Label(y, 0, tp.getShortName());
		sheet.addCell(labelCaption);
		y = y + 1;
		Label label_vv = new Label(y, i, field_visibility);
		sheet.addCell(label_vv);
		labelCaption = new Label(y, 0, tp.getShortName()
				+ ImportExportUtils.LABELCAPTION_VISIBILITY_SUFFIX);
		sheet.addCell(labelCaption);

		return y;
	}

	private static int createSimpleElement(
			ApplicationService applicationService, int y, int i,
			String shortName, List<RPProperty> proprietaDellaTipologia,
			WritableSheet sheet) throws RowsExceededException, WriteException {
		String field_value = "";
		String field_visibility = "";
		boolean first = true;
		for (RPProperty rr : proprietaDellaTipologia) {

			PropertyEditor pe = rr.getTypo().getRendering()
					.getPropertyEditor(applicationService);
			pe.setValue(rr.getObject());
			if (!first) {
				field_value += STOPFIELDS_EXCEL;
			}
			field_value += pe.getAsText();
			if (!first) {
				field_visibility += STOPFIELDS_EXCEL;
			}
			field_visibility += VisibilityConstants.getDescription(rr
					.getVisibility());
			first = false;

		}
		y = y + 1;
		Label label_v = new Label(y, i, field_value);
		sheet.addCell(label_v);
		Label labelCaption = new Label(y, 0, shortName);
		sheet.addCell(labelCaption);
		y = y + 1;
		Label label_vv = new Label(y, i, field_visibility);
		sheet.addCell(label_vv);
		labelCaption = new Label(y, 0, shortName
				+ ImportExportUtils.LABELCAPTION_VISIBILITY_SUFFIX);
		sheet.addCell(labelCaption);

		return y;
	}

	public static int createCell(ApplicationService applicationService, int y,
			int i, DecoratorRestrictedField decorator,
			ResearcherPage researcher, WritableSheet sheet)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, RowsExceededException, WriteException {
		String shortName = decorator.getShortName();
		Method[] methods = researcher.getClass().getMethods();
		Object field = null;
		Method method = null;
		for (Method m : methods) {
			if (m.getName().toLowerCase()
					.equals("get" + shortName.toLowerCase())) {
				field = m.invoke(researcher, null);
				method = m;
				break;
			}
		}

		if (method.getReturnType().isAssignableFrom(List.class)) {
			String field_value = "";
			String field_visibility = "";
			boolean first = true;
			for (RestrictedField rr : (List<RestrictedField>) field) {

				if (!first) {
					field_value += STOPFIELDS_EXCEL;
				}
				field_value += rr.getValue();
				if (!first) {
					field_visibility += STOPFIELDS_EXCEL;
				}
				field_visibility += VisibilityConstants.getDescription(rr
						.getVisibility());
				first = false;

			}
			y = y + 1;
			Label label_v = new Label(y, i, field_value);
			Label labelCaption = new Label(y, 0, decorator.getShortName());
			sheet.addCell(labelCaption);
			y = y + 1;
			Label label_vv = new Label(y, i, field_visibility);
			labelCaption = new Label(y, 0, decorator.getShortName()
					+ ImportExportUtils.LABELCAPTION_VISIBILITY_SUFFIX);
			sheet.addCell(labelCaption);

			sheet.addCell(label_v);
			sheet.addCell(label_vv);

		} else if (method.getReturnType().isAssignableFrom(String.class)) {
			y = y + 1;
			sheet.addCell(new Label(y, i, (String) field));
			Label labelCaption = new Label(y, 0, decorator.getShortName());
			sheet.addCell(labelCaption);
		} else {
			if (RestrictedFieldLocalOrRemoteFile.class.isAssignableFrom(method
					.getReturnType())) {
				RestrictedFieldLocalOrRemoteFile rflor = (RestrictedFieldLocalOrRemoteFile) field;
				y = y + 1;
				if (StringUtils.isNotEmpty(rflor.getRemoteUrl())) {
					sheet.addCell(new Label(y, i, rflor.getRemoteUrl()));
				} else {
					sheet.addCell(new Label(y, i, rflor.getMimeType()
							+ STOPFIELDS_EXCEL + rflor.getValue()));
				}
				Label labelCaption = new Label(y, 0, decorator.getShortName());
				sheet.addCell(labelCaption);
				y = y + 1;
				sheet.addCell(new Label(y, i, VisibilityConstants
						.getDescription(rflor.getVisibility())));
				labelCaption = new Label(y, 0, decorator.getShortName()
						+ ImportExportUtils.LABELCAPTION_VISIBILITY_SUFFIX);
				sheet.addCell(labelCaption);
			} else if (RestrictedFieldFile.class.isAssignableFrom(method
					.getReturnType())) {
				RestrictedFieldFile rflor = (RestrictedFieldFile) field;
				y = y + 1;
				Label labelCaption = new Label(y, 0, decorator.getShortName());
				sheet.addCell(labelCaption);
				if (StringUtils.isNotEmpty(rflor.getValue())) {
					sheet.addCell(new Label(y, i, rflor.getMimeType()
							+ STOPFIELDS_EXCEL + rflor.getValue()));
				}
				y = y + 1;
				labelCaption = new Label(y, 0, decorator.getShortName()
						+ ImportExportUtils.LABELCAPTION_VISIBILITY_SUFFIX);
				sheet.addCell(labelCaption);
				if (StringUtils.isNotEmpty(rflor.getValue())) {
					sheet.addCell(new Label(y, i, VisibilityConstants
							.getDescription(rflor.getVisibility())));
				}
			} else {
				RestrictedField rr = (RestrictedField) field;
				y = y + 1;
				sheet.addCell(new Label(y, i, rr.getValue()));
				Label labelCaption = new Label(y, 0, decorator.getShortName());
				sheet.addCell(labelCaption);
				y = y + 1;
				sheet.addCell(new Label(y, i, VisibilityConstants
						.getDescription(rr.getVisibility())));
				labelCaption = new Label(y, 0, decorator.getShortName()
						+ ImportExportUtils.LABELCAPTION_VISIBILITY_SUFFIX);
				sheet.addCell(labelCaption);
			}
		}
		return y;
	}

}
