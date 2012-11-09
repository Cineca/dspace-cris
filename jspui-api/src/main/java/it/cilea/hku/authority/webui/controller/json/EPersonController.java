/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package it.cilea.hku.authority.webui.controller.json;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dspace.app.webui.util.UIUtil;
import org.dspace.authorize.AuthorizeManager;
import org.dspace.core.Context;
import org.dspace.eperson.EPerson;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import flexjson.JSONSerializer;

public class EPersonController extends MultiActionController
{

    public ModelAndView epersons(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        Context context = UIUtil.obtainContext(request);
        // authorization is checked by the getMyRP method
        boolean isAdmin = isAdmin(context, request);
        if (isAdmin)
        {            
            returnJSON(response, fillInDTO(EPerson.search(context, request.getParameter("query"))));
        }
        
        return null;
    }
    
    public ModelAndView eperson(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        Context context = UIUtil.obtainContext(request);                    
        returnJSON(response, fillInDTO(new EPerson[]{EPerson.find(context, Integer.parseInt(request.getParameter("id")))}));   
        
        return null;
    }

    private List<EPersonDTO> fillInDTO(EPerson[] findAll)
    {
        List<EPersonDTO> epersons = new ArrayList<EPersonController.EPersonDTO>();
        for(EPerson eperson : findAll) {
            EPersonDTO dto = new EPersonDTO();
            dto.setFirstName(eperson.getFirstName());
            dto.setId(eperson.getID());
            dto.setLastName(eperson.getLastName());
            dto.setNetId(eperson.getNetid());
            dto.setEmail(eperson.getEmail());
            epersons.add(dto);
        }
        return epersons;
    }

    private void returnJSON(HttpServletResponse response,
            List<EPersonDTO> epersons) throws IOException
    {
        
        JSONSerializer serializer = new JSONSerializer();
        serializer.rootName("epersons");
        serializer.exclude("class");
        response.setContentType("application/json");
        serializer.deepSerialize(epersons, response.getWriter());
    }

    private boolean isAdmin(Context context, HttpServletRequest request)
            throws SQLException, ServletException
    {
        EPerson currUser = getCurrentUser(request);
        if (currUser == null)
        {
            throw new ServletException(
                    "Wrong data or configuration: access to the my rp servlet without a valid user: there is no user logged in");
        }

        return AuthorizeManager.isAdmin(context);
    }

    private EPerson getCurrentUser(HttpServletRequest request)
            throws SQLException
    {
        Context context = UIUtil.obtainContext(request);
        EPerson currUser = context.getCurrentUser();
        return currUser;
    }

    class EPersonDTO
    {
        private int id;

        private String lastName;
        
        private String firstName;
        
        private String netId;
        
        private String email;
        
        public int getId()
        {
            return id;
        }

        public void setId(int id)
        {
            this.id = id;
        }

        public String getLastName()
        {
            return lastName;
        }

        public void setLastName(String lastName)
        {
            this.lastName = lastName;
        }

        public String getFirstName()
        {
            return firstName;
        }

        public void setFirstName(String firstName)
        {
            this.firstName = firstName;
        }

        public String getNetId()
        {
            return netId;
        }

        public void setNetId(String netId)
        {
            this.netId = netId;
        }

        public void setEmail(String email)
        {
            this.email = email;
        }

        public String getEmail()
        {
            return email;
        }

        
    }
}
