/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package it.cilea.hku.authority.batch;

import it.cilea.hku.authority.model.Project;
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

        for (Project grant : applicationService
                .getList(Project.class))
        {
            applicationService.saveOrUpdate(Project.class, grant);
        }
        
    }
    

}
