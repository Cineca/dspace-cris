/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package org.dspace.app.cris.dspace;

import org.dspace.app.cris.model.CrisConstants;
import org.dspace.app.cris.model.OrganizationUnit;

public class OUAuthority extends CRISAuthority
{

    @Override
    protected int getCRISTargetTypeID()
    {
        return CrisConstants.OU_TYPE_ID;
    }

    @Override
    protected Class<OrganizationUnit> getCRISTargetClass()
    {
        return OrganizationUnit.class;
    }

}
