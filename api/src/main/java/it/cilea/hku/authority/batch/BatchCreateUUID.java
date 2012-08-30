/**
 * <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
 * <html><head>
 * <title>301 Moved Permanently</title>
 * </head><body>
 * <h1>Moved Permanently</h1>
 * <p>The document has moved <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>.</p>
 * </body></html>
 */
package it.cilea.hku.authority.batch;

import it.cilea.hku.authority.model.ResearcherGrant;
import it.cilea.hku.authority.model.ResearcherPage;
import it.cilea.hku.authority.service.ApplicationService;

import java.sql.SQLException;

import org.apache.commons.cli.ParseException;
import org.dspace.core.Context;
import org.dspace.utils.DSpace;

public class BatchCreateUUID
{

    /**
     * @param args
     */
    public static void main(String[] args) throws ParseException, SQLException
    {

        Context dspaceContext = new Context();
        dspaceContext.setIgnoreAuthorization(true);
        DSpace dspace = new DSpace();
        ApplicationService applicationService = dspace.getServiceManager()
                .getServiceByName("applicationService",
                        ApplicationService.class);

       for (ResearcherPage rp : applicationService
                .getList(ResearcherPage.class))
        {
            applicationService.saveOrUpdate(ResearcherPage.class, rp);
        }

        for (ResearcherGrant grant : applicationService
                .getList(ResearcherGrant.class))
        {
            applicationService.saveOrUpdate(ResearcherGrant.class, grant);
        }
        
    }
    

}
