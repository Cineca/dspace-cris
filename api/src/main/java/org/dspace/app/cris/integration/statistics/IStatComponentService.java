package org.dspace.app.cris.integration.statistics;

import java.util.Map;

public interface IStatComponentService<T extends IStatsGenericComponent>
{
    Map<String, T> getComponents();

    T getSelectedObjectComponent();
    
    Map getCommonsParams();
    
    public boolean isShowSelectedObject();
    
    public boolean isShowExtraTab();
}
