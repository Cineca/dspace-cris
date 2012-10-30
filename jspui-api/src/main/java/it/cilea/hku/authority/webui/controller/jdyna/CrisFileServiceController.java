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
