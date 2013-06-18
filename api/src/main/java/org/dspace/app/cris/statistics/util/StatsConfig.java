package org.dspace.app.cris.statistics.util;

import javax.persistence.Transient;

import org.dspace.core.ConfigurationManager;

public class StatsConfig {
	
    public static final String CFG_MODULE = "solr-statistics";
    
    @Transient
    public static final int DETAILS_SECTION = -1;

    @Transient
    public static final int DOWNLOAD_CV_SECTION = -2;

    @Transient
    public static final int COLLABORATION_NETWORK_SECTION = -3;
    
	private String statisticsCore;
	
	public String getStatisticsCore() {
	    if(statisticsCore==null) {
	        statisticsCore = ConfigurationManager.getProperty(CFG_MODULE, "server");
	    }
		return statisticsCore;
	}

	public void setStatisticsCore(String statisticsCore) {
		this.statisticsCore = statisticsCore;
	}
	
}
