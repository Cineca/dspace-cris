/**
 * <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
 * <html><head>
 * <title>301 Moved Permanently</title>
 * </head><body>
 * <h1>Moved Permanently</h1>
 * <p>The document has moved <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>.</p>
 * </body></html>
 */
package it.cilea.hku.authority.webui.controller;

import it.cilea.hku.authority.model.ResearcherPage;
import it.cilea.hku.authority.util.ResearcherPageUtils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dspace.app.webui.util.UIUtil;
import org.dspace.authorize.AuthorizeManager;
import org.dspace.core.Context;
import org.dspace.eperson.EPerson;

/**
 * This abstract controller add common reference data to all the form managing RP
 * for the hide/unhide of menus on a role basis.
 * 
 * @author cilea
 * 
 */
public abstract class AFormResearcherPageController extends BaseFormController {

	/**
     * add common reference data to all the view for the hide/unhide of menus on
     * a role basis.
     */
    protected Map referenceData(HttpServletRequest request) throws Exception
    {
        Map<String, Object> reference = new HashMap<String, Object>();
        
        String id_s = request.getParameter("id");
        Integer id = Integer.parseInt(id_s);
        ResearcherPage researcher = applicationService.get(
                    ResearcherPage.class, id);
        Context context = UIUtil.obtainContext(request);
        EPerson currUser = context.getCurrentUser();
        if (AuthorizeManager.isAdmin(context)
                || (currUser != null && researcher.getStaffNo().equals(
                        currUser.getNetid())))
        {
            reference.put("researcher_page_menu", new Boolean(true));
            reference.put("researcher", researcher);
        }
        reference.put("authority_key", ResearcherPageUtils
                .getPersistentIdentifier(researcher));
        
        return reference;
    }
}
