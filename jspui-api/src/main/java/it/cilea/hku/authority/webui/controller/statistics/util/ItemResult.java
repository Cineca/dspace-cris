/**
 * <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
 * <html><head>
 * <title>301 Moved Permanently</title>
 * </head><body>
 * <h1>Moved Permanently</h1>
 * <p>The document has moved <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>.</p>
 * </body></html>
 */
package it.cilea.hku.authority.webui.controller.statistics.util;

import java.util.Collection;
import java.util.Date;

public class ItemResult {
	private String continent;
	private String countryCode;
	private String dctype;
	private String dctype_search;
	private String dns;
	private String epersonid;
	private String id;
	private String ip;
	private Float latitude;
	private Float longitude;
	private Collection<Integer> owningColl;
	private Collection<Integer> owningComm;
	private Collection<Integer> owningItem;
	private String rp;
	private String rp_authority;
	private String rp_search;
	private Date time;
	private String type;
	
	public String getContinent() {
		return continent;
	}
	public void setContinent(String continent) {
		this.continent = continent;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getDctype() {
		return dctype;
	}
	public void setDctype(String dctype) {
		this.dctype = dctype;
	}
	public String getDctype_search() {
		return dctype_search;
	}
	public void setDctype_search(String dctypeSearch) {
		dctype_search = dctypeSearch;
	}
	public String getDns() {
		return dns;
	}
	public void setDns(String dns) {
		this.dns = dns;
	}
	public String getEpersonid() {
		return epersonid;
	}
	public void setEpersonid(String epersonid) {
		this.epersonid = epersonid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Float getLatitude() {
		return latitude;
	}
	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}
	public Float getLongitude() {
		return longitude;
	}
	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}
	
	public Collection<Integer> getOwningColl() {
		return owningColl;
	}
	public void setOwningColl(Collection<Integer> owningColl) {
		this.owningColl = owningColl;
	}
	public Collection<Integer> getOwningComm() {
		return owningComm;
	}
	public void setOwningComm(Collection<Integer> owningComm) {
		this.owningComm = owningComm;
	}
	public Collection<Integer> getOwningItem() {
		return owningItem;
	}
	public void setOwningItem(Collection<Integer> owningItem) {
		this.owningItem = owningItem;
	}
	public String getRp() {
		return rp;
	}
	public void setRp(String rp) {
		this.rp = rp;
	}
	public String getRp_authority() {
		return rp_authority;
	}
	public void setRp_authority(String rpAuthority) {
		rp_authority = rpAuthority;
	}
	public String getRp_search() {
		return rp_search;
	}
	public void setRp_search(String rpSearch) {
		rp_search = rpSearch;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
