package org.dspace.app.cris.ws;

import java.io.File;

import org.dspace.app.cris.model.CrisConstants;
import org.dspace.services.ConfigurationService;

public class WSConfigurationService
{
    private ConfigurationService configurationService;
    
    public void setConfigurationService(
            ConfigurationService configurationService)
    {
        this.configurationService = configurationService;
    }
    
    public ConfigurationService getConfigurationService()
    {
        return configurationService;
    }
    
    public String getMainSchema()
    {
        String property = configurationService.getProperty("cris.webservices.xsd.path");
        if (!property.endsWith(File.separator))
        {
            property += File.separator;
        }
        return "file:"+property+"crisrequest.xsd";
    }
    
    public String getLocationUri()
    {
        return configurationService.getProperty("dspace.url")+"/webservices/";
    }
}
