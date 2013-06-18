/**
 * 
 */
package org.dspace.app.cris.statistics.bean;

public class MapPointBean implements java.io.Serializable
{
    private String latitude;

    private String longitude;

    private Integer value;

    private double percentage;

    public MapPointBean(String latitude, String longitude, Integer value)
    {
        super();
        this.latitude = latitude;
        this.longitude = longitude;
        this.value = value;
        percentage = new Float(1);
    }

    public String getLatitude()
    {
        return latitude;
    }

    public void setLatitude(String latitude)
    {
        this.latitude = latitude;
    }

    public String getLongitude()
    {
        return longitude;
    }

    public void setLongitude(String longitude)
    {
        this.longitude = longitude;
    }

    public Integer getValue()
    {
        return value;
    }

    public void setValue(Integer value)
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