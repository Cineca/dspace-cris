/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * https://github.com/CILEA/dspace-cris/wiki/License
 */
package org.dspace.app.cris.util;

import java.util.List;
import java.util.Map;

import org.dspace.app.cris.discovery.CrisSearchService;
import org.dspace.app.cris.integration.CrisComponentsService;
import org.dspace.app.cris.integration.ICRISComponent;
import org.dspace.app.cris.integration.statistics.CrisStatComponentsService;
import org.dspace.app.cris.integration.statistics.StatComponentsService;
import org.dspace.app.cris.service.ApplicationService;
import org.dspace.app.cris.service.CrisSubscribeService;
import org.dspace.app.cris.service.RelationPreferenceService;
import org.dspace.app.cris.statistics.service.StatSubscribeService;
import org.dspace.utils.DSpace;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

public class Researcher
{
    DSpace dspace = new DSpace();

    public ApplicationService getApplicationService()
    {
        return dspace.getServiceManager().getServiceByName(
                "applicationService", ApplicationService.class);
    }
    
    public CrisSubscribeService getCrisSubscribeService()
    {
        return dspace.getServiceManager().getServiceByName("CrisSubscribeService",
                CrisSubscribeService.class);
    }

    public StatSubscribeService getStatSubscribeService()
    {
        return dspace.getServiceManager().getServiceByName("statSubscribeService",
                StatSubscribeService.class);
    }
    
    public CrisSearchService getCrisSearchService() {
        return dspace.getServiceManager().getServiceByName(
                "org.dspace.discovery.SearchService", CrisSearchService.class);
    }
    
    public Map<String, ICRISComponent> getRPComponents() {
        CrisComponentsService compService = dspace.getServiceManager().getServiceByName("rpComponentsService", CrisComponentsService.class);
        if (compService == null)
        {
            return null;
        }
        return compService.getComponents();
    }
    
    public Map<String, ICRISComponent> getProjectComponents() {
        CrisComponentsService compService = dspace.getServiceManager().getServiceByName("projectComponentsService", CrisComponentsService.class);
        if (compService == null)
        {
            return null;
        }
        return compService.getComponents();
    }
    
    public Map<String, ICRISComponent> getOUComponents() {
        CrisComponentsService compService = dspace.getServiceManager().getServiceByName("ouComponentsService", CrisComponentsService.class);
        if (compService == null)
        {
            return null;
        }
        return compService.getComponents();
    }

    public Map<String, ICRISComponent> getDOComponents() {
        CrisComponentsService compService = dspace.getServiceManager().getServiceByName("doComponentsService", CrisComponentsService.class);
        if (compService == null)
        {
            return null;
        }
        return compService.getComponents();
    }
    
    public SessionFactory getSessionFactory()
    {
        return (SessionFactory) dspace.getServiceManager().getServiceByName("&sessionFactory", LocalSessionFactoryBean.class).getObject();
    }
    
    public RelationPreferenceService getRelationPreferenceService() {
        return dspace.getServiceManager().getServiceByName(
                "org.dspace.app.cris.service.RelationPreferenceService", RelationPreferenceService.class);
    }
    
    
    public CrisStatComponentsService getRPStatsComponents() {
        CrisStatComponentsService compService = dspace.getServiceManager().getServiceByName("rpStatsComponent", CrisStatComponentsService.class);
        if (compService == null)
        {
            return null;
        }
        return compService;        
    }
    
    public StatComponentsService getItemStatsComponents() {
        StatComponentsService compService = dspace.getServiceManager().getServiceByName("itemStatsComponent", StatComponentsService.class);
        if (compService == null)
        {
            return null;
        }
        return compService;
    }
    
    public StatComponentsService getCommunityStatsComponents() {
        StatComponentsService compService = dspace.getServiceManager().getServiceByName("communityStatsComponent", StatComponentsService.class);
        if (compService == null)
        {
            return null;
        }
        return compService;
    }
    
    public StatComponentsService getCollectionStatsComponents() {
        StatComponentsService compService = dspace.getServiceManager().getServiceByName("collectionStatsComponent", StatComponentsService.class);
        if (compService == null)
        {
            return null;
        }
        return compService;
    }
    
    public CrisStatComponentsService getPJStatsComponents() {
        CrisStatComponentsService compService = dspace.getServiceManager().getServiceByName("pjStatsComponent", CrisStatComponentsService.class);
        if (compService == null)
        {
            return null;
        }
        return compService;        
    }
    
    public CrisStatComponentsService getOUStatsComponents() {
        CrisStatComponentsService compService = dspace.getServiceManager().getServiceByName("ouStatsComponent", CrisStatComponentsService.class);
        if (compService == null)
        {
            return null;
        }
        return compService;        
    }

    public CrisStatComponentsService getDOStatsComponents() {
        CrisStatComponentsService compService = dspace.getServiceManager().getServiceByName("doStatsComponent", CrisStatComponentsService.class);
        if (compService == null)
        {
            return null;
        }
        return compService;        
    }

    public List<CrisComponentsService> getAllCrisComponents()
    {
        return dspace.getServiceManager().getServicesByType(CrisComponentsService.class);   
    }
}
