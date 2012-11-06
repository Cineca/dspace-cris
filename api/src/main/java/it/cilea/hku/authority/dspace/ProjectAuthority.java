package it.cilea.hku.authority.dspace;

import it.cilea.hku.authority.model.CrisConstants;
import it.cilea.hku.authority.model.OrganizationUnit;

public class ProjectAuthority extends CRISAuthority
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
