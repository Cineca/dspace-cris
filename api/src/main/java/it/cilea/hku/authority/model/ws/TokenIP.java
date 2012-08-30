/**
 * <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
 * <html><head>
 * <title>301 Moved Permanently</title>
 * </head><body>
 * <h1>Moved Permanently</h1>
 * <p>The document has moved <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>.</p>
 * </body></html>
 */
package it.cilea.hku.authority.model.ws;

import javax.persistence.Embeddable;

@Embeddable
public class TokenIP
{
    private String token;
    
    private String fromIP;

    private String toIP;
    
    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public String getFromIP()
    {
        return fromIP;
    }

    public void setFromIP(String fromIP)
    {
        this.fromIP = fromIP;
    }

    public String getToIP()
    {
        return toIP;
    }

    public void setToIP(String toIP)
    {
        this.toIP = toIP;
    }

}
