/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * https://github.com/CILEA/dspace-cris/wiki/License
 */
package org.dspace.app.cris.model.jdyna.widget;

import it.cilea.osd.jdyna.editor.FilePropertyEditor;
import it.cilea.osd.jdyna.service.IPersistenceDynaService;

import java.beans.PropertyEditor;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.dspace.app.cris.model.CrisConstants;
import org.dspace.app.cris.model.ResearcherPage;
import org.dspace.app.cris.util.ResearcherPageUtils;
import org.dspace.core.ConfigurationManager;

@Entity
@Table(name="cris_rp_wfile")
public class WidgetFileRP extends AWidgetFileCris {



	@Override
	public PropertyEditor getPropertyEditor(
			IPersistenceDynaService applicationService) {
		return new FilePropertyEditor<WidgetFileRP>(this);
	}

	
	@Override
	public String getBasePath() {
		return ConfigurationManager.getProperty(CrisConstants.CFG_MODULE,"researcherpage.file.path");
	}
	
	@Override
	public String getServletPath() {
		return ConfigurationManager.getProperty(CrisConstants.CFG_MODULE,"researcherpage.jdynafile.servlet.name");
	}
	
	@Override
	public String getCustomFolderByAuthority(String intAuth, String extAuth) {
		return ResearcherPageUtils.getPersistentIdentifier(Integer.parseInt(intAuth), ResearcherPage.class) + "/" + extAuth;
	}
}
