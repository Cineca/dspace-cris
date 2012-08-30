/**
 * <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
 * <html><head>
 * <title>301 Moved Permanently</title>
 * </head><body>
 * <h1>Moved Permanently</h1>
 * <p>The document has moved <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>.</p>
 * </body></html>
 */
package it.cilea.hku.authority.model.dynamicfield.widget;

import it.cilea.hku.authority.model.ResearcherGrant;
import it.cilea.hku.authority.util.ResearcherPageUtils;
import it.cilea.osd.jdyna.editor.FilePropertyEditor;
import it.cilea.osd.jdyna.service.IPersistenceDynaService;
import it.cilea.osd.jdyna.util.ValidationMessage;
import it.cilea.osd.jdyna.value.FileValue;
import it.cilea.osd.jdyna.widget.WidgetFile;

import java.beans.PropertyEditor;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.dspace.core.ConfigurationManager;

@Entity
@Table(name="model_grant_jdyna_widgetfile")
public class WidgetFileRG extends WidgetFile {

	@Override
	public FileValue getInstanceValore() {
		return new FileValue();
	}

	@Override
	public PropertyEditor getPropertyEditor(
			IPersistenceDynaService applicationService) {
		return new FilePropertyEditor<WidgetFileRG>(this);
	}

	@Override
	public Class<FileValue> getValoreClass() {
		return FileValue.class;
	}

	@Override
	public ValidationMessage valida(Object valore) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getBasePath() {
		return ConfigurationManager.getProperty("researchergrant.file.path");
	}
	
	@Override
	public String getServletPath() {
		return ConfigurationManager
				.getProperty("researchergrant.jdynafile.servlet.name");
	}
	
	@Override
	public String getCustomFolderByAuthority(String intAuth, String extAuth) {
		return intAuth + "/" + extAuth;
	}
}
