package org.dspace.app.cris.ws;

import java.io.IOException;

import org.dspace.app.cris.model.ws.User;
import org.dspace.discovery.SearchServiceException;
import org.jdom.Element;

public interface IWSService
{
    public Element marshall(String query, String paginationStart,
            String paginationLimit, String[] splitProjection, String type,
            Element root, User userWS, String nameRoot)
            throws SearchServiceException, IOException;
}
