package org.dspace.app.cris.dao;

import it.cilea.osd.common.dao.PaginableObjectDao;

import org.dspace.app.cris.model.ws.User;

/**
 * This interface define the methods available to retrieve User for web services
 * 
 * @author cilea
 * 
 */
public interface UserWSDao extends
        PaginableObjectDao<User, Integer>
{

    User uniqueByUsernameAndPassword(String username, String password);

    User uniqueByToken(String token);
   
}
