package org.dspace.app.cris.statistics.bean;

import java.util.Map;

public class ResultBean
{
    private Object dataBeans;

    private Map parameters;

    public ResultBean()
    {
    }

    public ResultBean(Object dataBeans, Map parameters)
    {
        this.dataBeans = dataBeans;
        this.parameters = parameters;
    }

    public Object getDataBeans()
    {
        return dataBeans;
    }

    public void setDataBean(Object dataBeans)
    {
        this.dataBeans = dataBeans;
    }

    public Map getParameters()
    {
        return parameters;
    }

    public void setParameters(Map parameters)
    {
        this.parameters = parameters;
    }

}
