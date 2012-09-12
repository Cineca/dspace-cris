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
package it.cilea.hku.authority.dao;

import it.cilea.osd.common.model.Identifiable;

import java.util.List;

import org.hibernate.CacheMode;
import org.hibernate.Query;

/**
 * This interface define general methods available to query the RPs database
 * 
 * @author cilea
 * 
 */
public class ApplicationDao extends it.cilea.osd.common.dao.impl.ApplicationDao {

	public void clearSession() {
		getSession().clear();		
	}
	public void ignoreCacheMode() {
		getSession().setCacheMode(CacheMode.IGNORE);
	}
	public void flushSession() {
		getSession().flush();
	}
	public void evict(Identifiable identifiable){
		getSession().evict(identifiable);
	}
	
	public <T extends Object> List<T> getCL(String token, String classe) {
		Query query = getSession().getNamedQuery(classe + ".findByDescription");
		query.setParameter(0, "%"+token+"%");
		return query.list();
	}


}
