package org.dspace.app.webui.cris.dto;

import java.util.List;

public class RelatedObject
{
    private String uuid;

    private String relationPreference;

    private List<String> descriptionColumns;

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public String getRelationPreference()
    {
        return relationPreference;
    }

    public void setRelationPreference(String relationPreference)
    {
        this.relationPreference = relationPreference;
    }

    public List<String> getDescriptionColumns()
    {
        return descriptionColumns;
    }

    public void setDescriptionColumns(List<String> descriptionColumns)
    {
        this.descriptionColumns = descriptionColumns;
    }
}
