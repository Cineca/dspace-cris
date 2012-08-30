/**
 * <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
 * <html><head>
 * <title>301 Moved Permanently</title>
 * </head><body>
 * <h1>Moved Permanently</h1>
 * <p>The document has moved <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>.</p>
 * </body></html>
 */
package it.cilea.hku.authority.service;

import it.cilea.hku.authority.model.RPSubscription;
import it.cilea.hku.authority.model.ResearcherPage;

import java.util.List;

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
