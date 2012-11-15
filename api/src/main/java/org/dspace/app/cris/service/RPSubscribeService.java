/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package org.dspace.app.cris.service;


import java.util.List;

import org.dspace.app.cris.model.RPSubscription;
import org.dspace.app.cris.model.ResearcherPage;
import org.dspace.eperson.EPerson;

public class RPSubscribeService
{
    private ApplicationService as;
    
    public RPSubscribeService(ApplicationService as)
    {
        this.as = as;
    }
    
    public void unsubscribe(EPerson e, int rpid)
    {
        ResearcherPage rp = as.get(ResearcherPage.class, rpid);
        if (rp == null)
            return;
        RPSubscription rpsub = as.getRPSubscription(e.getID(),
                rp);
        if (rpsub != null)
        {
            as.delete(RPSubscription.class, rpsub.getId());
        }
    }
    
    public void subscribe(EPerson e, int rpid)
    {
        ResearcherPage rp = as.get(ResearcherPage.class, rpid);
        if (rp == null)
            return;
        RPSubscription rpsub = as.getRPSubscription(e.getID(),
                rp);
        if (rpsub == null)
        {
            rpsub = new RPSubscription();
            rpsub.setEpersonID(e.getID());
            rpsub.setRp(rp);
            as.saveOrUpdate(RPSubscription.class, rpsub);
        }
    }
    
    public boolean isSubscribed(EPerson e, ResearcherPage rp)
    {
        if (e == null)
            return false;
        return as.getRPSubscription(e.getID(), rp) != null;
    }
    
    public void clearAll(EPerson e)
    {
        as.deleteRPSubscriptionByEPersonID(e.getID());
    }
    
    public List<ResearcherPage> getSubscriptions(EPerson e)
    {
        return as.getRPSubscriptionsByEPersonID(e.getID());
    }
}
