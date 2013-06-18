package org.dspace.app.cris.statistics.bean;

import java.util.ArrayList;
import java.util.Collection;

public class StatisticDatasBeanRowCollection
{
    // private Hashtable<StatisticDatasBeanRow,Float> percentages = new
    // Hashtable<StatisticDatasBeanRow, Float>();
    private Collection<StatisticDatasBeanRow> list = new ArrayList<StatisticDatasBeanRow>();

    public StatisticDatasBeanRowCollection()
    {

    }

    public void add(StatisticDatasBeanRow statisticDatasBeanRow)
    {

        if (!list.contains(statisticDatasBeanRow))
        {
            list.add(statisticDatasBeanRow);
        }
        updatePercentages();
    }

    private void updatePercentages()
    {
        Integer total = 0;
        for (StatisticDatasBeanRow statisticDatasBeanRow : list)
        {
            total += (Integer) statisticDatasBeanRow.getValue();
        }
        for (StatisticDatasBeanRow statisticDatasBeanRow : list)
        {
            statisticDatasBeanRow.setPercentage((Double) statisticDatasBeanRow
                    .getValue() / total);
        }
    }
}
