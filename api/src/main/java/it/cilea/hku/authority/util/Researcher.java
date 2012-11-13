/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package it.cilea.hku.authority.util;

import it.cilea.hku.authority.discovery.CrisSearchService;
import it.cilea.hku.authority.dspace.CrisComponentsService;
import it.cilea.hku.authority.service.ApplicationService;
import it.cilea.osd.jdyna.components.IComponent;

import java.util.Map;

import org.dspace.utils.DSpace;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;

public class Researcher
{
    DSpace dspace = new DSpace();

    public ApplicationService getApplicationService()
    {
        return dspace.getServiceManager().getServiceByName(
                "applicationService", ApplicationService.class);
    }
    
    public CrisSearchService getCrisSearchService() {
        return dspace.getServiceManager().getServiceByName(
                "org.dspace.discovery.SearchService", CrisSearchService.class);
    }
    
    public Map<String, IComponent> getRPComponents() {
        return dspace.getServiceManager().getServiceByName("rpComponentsService", CrisComponentsService.class).getComponents();
    }
    
    public Map<String, IComponent> getProjectComponents() {
        return dspace.getServiceManager().getServiceByName("projectComponentsService", CrisComponentsService.class).getComponents();
    }
    
    public Map<String, IComponent> getOUComponents() {
        return dspace.getServiceManager().getServiceByName("ouComponentsService", CrisComponentsService.class).getComponents();
    }
 
    public SessionFactory getSessionFactory()
    {
        return (SessionFactory) dspace.getServiceManager().getServiceByName("&sessionFactory", AnnotationSessionFactoryBean.class).getObject();
    }
}
