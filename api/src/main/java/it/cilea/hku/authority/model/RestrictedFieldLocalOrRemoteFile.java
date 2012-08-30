/**
 * <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
 * <html><head>
 * <title>301 Moved Permanently</title>
 * </head><body>
 * <h1>Moved Permanently</h1>
 * <p>The document has moved <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>.</p>
 * </body></html>
 */
package it.cilea.hku.authority.model;

import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;

/**
 * This class extend RestrictedFieldFile to manage the case of remote file
 * 
 * @author cilea
 * 
 */
@Embeddable
public class RestrictedFieldLocalOrRemoteFile extends RestrictedFieldFile
{
    @Type(type = "text")
    /**
     * the remote url of the file if not locally uploaded
     */
    private String remoteUrl;

	public String getRemoteUrl() {
		return remoteUrl;
	}

	public void setRemoteUrl(String remoteUrl) {
		this.remoteUrl = remoteUrl;
	}
}
