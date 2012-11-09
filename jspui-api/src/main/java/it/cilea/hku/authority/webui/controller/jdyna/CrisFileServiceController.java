/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package it.cilea.hku.authority.webui.controller.jdyna;

import org.dspace.core.ConfigurationManager;

import it.cilea.osd.jdyna.web.controller.FileServiceController;

public class CrisFileServiceController extends FileServiceController
{

    private String filePath;
    
    @Override
    protected String getPath()
    {
        return ConfigurationManager.getProperty(getFilePath());
    }

    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    public String getFilePath()
    {
        return filePath;
    }
}
