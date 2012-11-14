/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package it.cilea.hku.authority.dspace;

import it.cilea.hku.authority.model.CrisConstants;
import it.cilea.hku.authority.model.Project;

public class ProjectAuthority extends CRISAuthority
{

    @Override
    protected int getCRISTargetTypeID()
    {
        return CrisConstants.PROJECT_TYPE_ID;
    }

    @Override
    protected Class<Project> getCRISTargetClass()
    {
        return Project.class;
    }

}
