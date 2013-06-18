package org.dspace.app.cris.statistics.bean;

import java.util.Collection;

public abstract class StatisticDatasBean implements java.io.Serializable
{
    public abstract String getKey1();

    public abstract void setKey1(String key1);

    public abstract String getKey2();

    public abstract void setKey2(String key2);

    public abstract String getName();

    public abstract void setName(final String name);

    public abstract int getHits();

    public abstract void setHits(int hits);

    public abstract Collection getDataTable();

    public abstract void setDataTable(Collection dataTable);
}
