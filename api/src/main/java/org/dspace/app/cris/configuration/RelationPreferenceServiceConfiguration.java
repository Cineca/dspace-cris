package org.dspace.app.cris.configuration;

import java.util.List;

public class RelationPreferenceServiceConfiguration
{
    private List<RelationPreferenceConfiguration> list;

    public List<RelationPreferenceConfiguration> getList()
    {
        return list;
    }

    public void setList(List<RelationPreferenceConfiguration> list)
    {
        this.list = list;
    }

    public synchronized RelationPreferenceConfiguration getRelationPreferenceConfiguration(
            String name)
    {
        for (RelationPreferenceConfiguration conf : list)
        {
            if (conf.getRelationConfiguration() != null)
            {
                if (name.equals(conf.getRelationConfiguration()
                        .getRelationName()))
                {
                    return conf;
                }
            }
        }
        return null;
    }
}
