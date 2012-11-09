/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package it.cilea.hku.authority.model.dynamicfield.widget;

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
@Table(name = "cris_project_widgetfile")
public class WidgetFileProject extends WidgetFile
{

    @Override
    public FileValue getInstanceValore()
    {
        return new FileValue();
    }

    @Override
    public PropertyEditor getPropertyEditor(
            IPersistenceDynaService applicationService)
    {
        return new FilePropertyEditor<WidgetFileProject>(this);
    }

    @Override
    public Class<FileValue> getValoreClass()
    {
        return FileValue.class;
    }

    @Override
    public ValidationMessage valida(Object valore)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getBasePath()
    {
        return ConfigurationManager.getProperty("project.file.path");
    }

    @Override
    public String getServletPath()
    {
        return ConfigurationManager
                .getProperty("project.jdynafile.servlet.name");
    }

    @Override
    public String getCustomFolderByAuthority(String intAuth, String extAuth)
    {
        return ResearcherPageUtils.getPersistentIdentifier(Integer
                .parseInt(intAuth)) + "/" + extAuth;
    }
}
