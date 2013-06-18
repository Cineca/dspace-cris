/**
 * 
 */
package org.dspace.app.cris.statistics.bean;

public class StatisticDatasBeanRow implements java.io.Serializable
{
    private String label;

    private Object value;

    private double percentage;

    public StatisticDatasBeanRow(String label, Object value)
    {
        super();
        this.label = label;
        this.value = value;
        percentage = new Float(1);
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public Object getValue()
    {
        return value;
    }

    public void setValue(Object value)
    {
        this.value = value;
    }

    public double getPercentage()
    {
        return percentage;
    }

    public void setPercentage(double percentage)
    {
        this.percentage = percentage;
    }

}