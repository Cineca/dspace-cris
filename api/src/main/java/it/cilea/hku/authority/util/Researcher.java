/**
 * <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
 * <html><head>
 * <title>301 Moved Permanently</title>
 * </head><body>
 * <h1>Moved Permanently</h1>
 * <p>The document has moved <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>.</p>
 * </body></html>
 */
package it.cilea.hku.authority.util;

import it.cilea.hku.authority.service.ApplicationService;

import org.dspace.utils.DSpace;

public class Researcher
{
    DSpace dspace = new DSpace();

    public ApplicationService getApplicationService()
    {
        return dspace.getServiceManager().getServiceByName(
                "applicationService", ApplicationService.class);
    }
    
}
