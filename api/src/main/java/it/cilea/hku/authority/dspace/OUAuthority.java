package it.cilea.hku.authority.dspace;

import it.cilea.hku.authority.model.CrisConstants;
import it.cilea.hku.authority.model.Project;

public class OUAuthority extends CRISAuthority
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
