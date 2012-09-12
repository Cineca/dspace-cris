/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 * 
 * http://www.dspace.org/license/
 * 
 * The document has moved 
 * <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>
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
