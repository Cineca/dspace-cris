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

import it.cilea.hku.authority.model.UserWS;
import it.cilea.hku.authority.model.dynamicfield.BoxResearcherGrant;
import it.cilea.hku.authority.model.ws.CriteriaWS;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;
import org.dspace.app.webui.util.UIUtil;
import org.dspace.authorize.AuthorizeException;
import org.dspace.authorize.AuthorizeManager;
import org.dspace.core.Context;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

/**
 * This SpringMVC controller is responsible to handle request of export
 * 
 * @author cilea
 * 
 */
public class FormUserWSController extends BaseFormController
{

    @Override
    protected Object formBackingObject(HttpServletRequest request)
            throws Exception
    {
        Context context = UIUtil.obtainContext(request);
        if (!AuthorizeManager.isAdmin(context))
        {
            throw new AuthorizeException(
                    "Only system administrator can access to the functionality");
        }
        
        UserWS userws = (UserWS) super.formBackingObject(request);
        String id = request.getParameter("id");
        if(id!=null && !id.isEmpty()) {
            userws = applicationService.get(UserWS.class, Integer.parseInt(id));
        }
        
        if (userws.getCriteria().isEmpty())
        {
            for (String criteria : CriteriaWS.getCRITERIA())
            {
                CriteriaWS newCriteria = new CriteriaWS();
                newCriteria.setCriteria(criteria);
                newCriteria.setFilter("");
                newCriteria.setEnabled(false);
                userws.getCriteria().add(newCriteria);
            }
        }        
        userws.setCriteria(LazyList.decorate(userws.getCriteria(),
                FactoryUtils.instantiateFactory(CriteriaWS.class)));
        return userws;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object command, BindException errors)
            throws Exception
    {
        UserWS object = (UserWS) command;
        applicationService.saveOrUpdate(UserWS.class, object);
        return new ModelAndView(getSuccessView());
    }

}
