/**
 * <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
 * <html><head>
 * <title>301 Moved Permanently</title>
 * </head><body>
 * <h1>Moved Permanently</h1>
 * <p>The document has moved <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>.</p>
 * </body></html>
 */
package it.cilea.hku.authority.webui.dto;

import it.cilea.hku.authority.model.ResearcherPage;
import it.cilea.hku.authority.model.RestrictedField;
import it.cilea.hku.authority.model.RestrictedFieldFile;
import it.cilea.hku.authority.model.listener.RPListener;
import it.cilea.osd.jdyna.dto.AnagraficaObjectAreaDTO;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;
import org.apache.commons.digester.SetRootRule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RPAnagraficaObjectDTO extends AnagraficaObjectAreaDTO {

	/**
	 * The log4j category
	 */
	protected final Log log = LogFactory.getLog(getClass());

	private Boolean status;

	private String staffNo;

	private String fullName;

	private RestrictedField honorific;

	private RestrictedField academicName;

	private RestrictedField chineseName;

	private List<RestrictedField> title;

	private RestrictedField dept;

	private RestrictedField address;

	private RestrictedField officeTel;

	private RestrictedField email;

	private String urlPict;

	private RestrictedFieldFile pict;

	private RestrictedFieldFile cv;

	private RestrictedField bio;

	private RestrictedField urlPersonal;

	private List<RestrictedField> media;

	private List<RestrictedField> variants;

	private List<RestrictedField> interests;

	private List<RestrictedField> spokenLanguagesEN;

	private List<RestrictedField> spokenLanguagesZH;

	private List<RestrictedField> writtenLanguagesEN;

	private List<RestrictedField> writtenLanguagesZH;

	// fields that come from the bibliometric excel data...
	private RestrictedField ridISI;

	private RestrictedField ridLinkISI;

	private RestrictedField paperCountISI;

	private RestrictedField paperLinkISI;

	private RestrictedField citationCountISI;

	private RestrictedField citationLinkISI;

	private RestrictedField coAuthorsISI;

	private RestrictedField hindexISI;

	private RestrictedField authorIdScopus;

	private RestrictedField authorIdLinkScopus;

	private RestrictedField paperCountScopus;

	private RestrictedField paperLinkScopus;

	private RestrictedField citationCountScopus;

	private RestrictedField citationLinkScopus;

	private RestrictedField coAuthorsScopus;

	private RestrictedField coAuthorsLinkScopus;

	private RestrictedField hindexScopus;

	public RPAnagraficaObjectDTO(ResearcherPage rp) {
		super();
		setAcademicName(rp.getAcademicName());
		setAddress(rp.getAddress());
		setAuthorIdLinkScopus(rp.getAuthorIdLinkScopus());
		setAuthorIdScopus(rp.getAuthorIdScopus());
		setBio(rp.getBio());
		setChineseName(rp.getChineseName());
		setCitationCountISI(rp.getCitationCountISI());
		setCitationCountScopus(rp.getCitationCountScopus());
		setCitationLinkISI(rp.getCitationLinkISI());
		setCitationLinkScopus(rp.getCitationLinkScopus());
		setCoAuthorsISI(rp.getCoAuthorsISI());
		setCoAuthorsLinkScopus(rp.getCoAuthorsLinkScopus());
		setCoAuthorsScopus(rp.getCoAuthorsScopus());
		setCv(rp.getCv());
		setDept(rp.getDept());
		setEmail(rp.getEmail());
		setFullName(rp.getFullName());
		setHindexISI(rp.getHindexISI());
		setHindexScopus(rp.getHindexScopus());
		setHonorific(rp.getHonorific());
		setInterests(rp.getInterests());
		setMedia(rp.getMedia());
		setOfficeTel(rp.getOfficeTel());
		setPaperCountISI(rp.getPaperCountISI());
		setPaperCountScopus(rp.getPaperCountScopus());
		setPaperLinkISI(rp.getPaperLinkISI());
		setPaperLinkScopus(rp.getPaperLinkScopus());
		setPict(rp.getPict());
		setRidISI(rp.getRidISI());
		setRidLinkISI(rp.getRidLinkISI());
		setSpokenLanguagesEN(rp.getSpokenLanguagesEN());
		setSpokenLanguagesZH(rp.getSpokenLanguagesZH());
		setStaffNo(rp.getStaffNo());
		setStatus(rp.getStatus());
		setTitle(rp.getTitle());
		setUrlPersonal(rp.getUrlPersonal());
		setUrlPict(rp.getUrlPict());
		setVariants(rp.getVariants());
		setWrittenLanguagesEN(rp.getWrittenLanguagesEN());
		setWrittenLanguagesZH(rp.getWrittenLanguagesZH());
	}

	public List<RestrictedField> getTitle() {
		if (this.title == null) {
			this.title = new LinkedList<RestrictedField>();
		}
		setTitle(getLazyList(title));
		return title;
	}

	public List<RestrictedField> getMedia() {
		if (this.media == null) {
			this.media = new LinkedList<RestrictedField>();
		}
		setMedia(getLazyList(media));
		return media;
	}

	public List<RestrictedField> getVariants() {
		if (this.variants == null) {
			this.variants = new LinkedList<RestrictedField>();
		}
		setVariants(getLazyList(variants));
		return variants;
	}

	public List<RestrictedField> getInterests() {
		if (this.interests == null) {
			this.interests = new LinkedList<RestrictedField>();
		}
		setInterests(getLazyList(interests));
		return interests;
	}

	public List<RestrictedField> getSpokenLanguagesEN() {
		if (this.spokenLanguagesEN == null) {
			this.spokenLanguagesEN = new LinkedList<RestrictedField>();
		}
		setSpokenLanguagesEN(getLazyList(spokenLanguagesEN));
		return spokenLanguagesEN;
	}

	public List<RestrictedField> getSpokenLanguagesZH() {
		if (this.spokenLanguagesZH == null) {
			this.spokenLanguagesZH = new LinkedList<RestrictedField>();
		}
		setSpokenLanguagesZH(getLazyList(spokenLanguagesZH));
		return spokenLanguagesZH;
	}

	public List<RestrictedField> getWrittenLanguagesEN() {
		if (this.writtenLanguagesEN == null) {
			this.writtenLanguagesEN = new LinkedList<RestrictedField>();
		}
		setWrittenLanguagesEN(getLazyList(writtenLanguagesEN));
		return writtenLanguagesEN;
	}

	public List<RestrictedField> getWrittenLanguagesZH() {
		if (this.writtenLanguagesZH == null) {
			this.writtenLanguagesZH = new LinkedList<RestrictedField>();
		}
		setWrittenLanguagesZH(getLazyList(writtenLanguagesZH));
		return writtenLanguagesZH;
	}

	/**
	 * Getter method.
	 * 
	 * @return the staffNo
	 */
	public String getStaffNo() {
		return staffNo;
	}

	/**
	 * Setter method.
	 * 
	 * @param staffNo
	 *            the staffNo
	 */
	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	/**
	 * Getter method.
	 * 
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * Setter method.
	 * 
	 * @param fullName
	 *            the full name
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * Getter method.
	 * 
	 * @return the honorific
	 */
	public RestrictedField getHonorific() {
		if (honorific == null) {
			honorific = new RestrictedField();
		}
		return honorific;
	}

	/**
	 * Setter method.
	 * 
	 * @param honorific
	 *            the honorific
	 */
	public void setHonorific(RestrictedField honorific) {
		this.honorific = honorific;
	}

	/**
	 * Getter method.
	 * 
	 * @return the academic name
	 */
	public RestrictedField getAcademicName() {
		if (academicName == null) {
			academicName = new RestrictedField();
		}
		return academicName;
	}

	/**
	 * Setter method.
	 * 
	 * @param academicName
	 *            the academic name
	 */
	public void setAcademicName(RestrictedField academicName) {
		this.academicName = academicName;
	}

	/**
	 * Getter method.
	 * 
	 * @return the chinese name
	 */
	public RestrictedField getChineseName() {
		if (chineseName == null) {
			chineseName = new RestrictedField();
		}
		return chineseName;
	}

	/**
	 * Setter method.
	 * 
	 * @param chineseName
	 *            the chinese name
	 */
	public void setChineseName(RestrictedField chineseName) {
		this.chineseName = chineseName;
	}

	/**
	 * Setter method.
	 * 
	 * @param title
	 *            the title's list
	 */
	public void setTitle(List<RestrictedField> title) {
		this.title = title;
	}

	/**
	 * Getter method.
	 * 
	 * @return the department
	 */
	public RestrictedField getDept() {
		if (dept == null) {
			dept = new RestrictedField();
		}
		return dept;
	}

	/**
	 * Setter method.
	 * 
	 * @param dept
	 *            the department
	 */
	public void setDept(RestrictedField dept) {
		this.dept = dept;
	}

	/**
	 * Getter method.
	 * 
	 * @return the address
	 */
	public RestrictedField getAddress() {
		if (address == null) {
			address = new RestrictedField();
		}
		return address;
	}

	/**
	 * Setter method.
	 * 
	 * @param address
	 *            the address
	 */
	public void setAddress(RestrictedField address) {
		this.address = address;
	}

	/**
	 * Getter method.
	 * 
	 * @return the office telephone number
	 */
	public RestrictedField getOfficeTel() {
		if (officeTel == null) {
			officeTel = new RestrictedField();
		}
		return officeTel;
	}

	/**
	 * Setter method.
	 * 
	 * @param officeTel
	 *            the office telephone number
	 */
	public void setOfficeTel(RestrictedField officeTel) {
		this.officeTel = officeTel;
	}

	/**
	 * Getter method.
	 * 
	 * @return the email
	 */
	public RestrictedField getEmail() {
		if (email == null) {
			email = new RestrictedField();
		}
		return email;
	}

	/**
	 * Setter method.
	 * 
	 * @param email
	 *            the email
	 */
	public void setEmail(RestrictedField email) {
		this.email = email;
	}

	/**
	 * Getter method.
	 * 
	 * @return the url of a webpage where check for a picture
	 */
	public String getUrlPict() {
		return urlPict;
	}

	/**
	 * Setter method.
	 * 
	 * @param urlPict
	 *            the url of a webpage where check for a picture
	 */
	public void setUrlPict(String urlPict) {
		this.urlPict = urlPict;
	}

	/**
	 * Getter method.
	 * 
	 * @return the picture to show on the public page
	 */
	public RestrictedFieldFile getPict() {
		if (pict == null) {
			pict = new RestrictedFieldFile();
		}
		return pict;
	}

	/**
	 * Setter method.
	 * 
	 * @param pict
	 *            the picture to show on the public page
	 */
	public void setPict(RestrictedFieldFile pict) {
		this.pict = pict;
	}

	/**
	 * Getter method.
	 * 
	 * @return the picture to show on the public page
	 */
	public RestrictedFieldFile getCv() {
		if (cv == null) {
			cv = new RestrictedFieldFile();
		}
		return cv;
	}

	/**
	 * Setter method.
	 * 
	 * @param pict
	 *            the picture to show on the public page
	 */
	public void setCv(RestrictedFieldFile cv) {
		this.cv = cv;
	}

	/**
	 * Getter method.
	 * 
	 * @return the biography
	 */
	public RestrictedField getBio() {
		if (bio == null) {
			bio = new RestrictedField();
		}
		return bio;
	}

	/**
	 * Setter method.
	 * 
	 * @param biography
	 *            the biography
	 */
	public void setBio(RestrictedField bio) {
		this.bio = bio;
	}

	/**
	 * Setter method.
	 * 
	 * @param variants
	 *            the variants form of the name (include also Japanese, Korean,
	 *            etc.)
	 */
	public void setVariants(List<RestrictedField> variants) {
		this.variants = variants;
	}

	/**
	 * Setter method.
	 * 
	 * @param interests
	 *            interests
	 */
	public void setInterests(List<RestrictedField> interests) {
		this.interests = interests;
	}

	/**
	 * Getter method.
	 * 
	 * @return the ISI Researcher ID
	 */
	public RestrictedField getRidISI() {
		if (ridISI == null) {
			ridISI = new RestrictedField();
		}
		return ridISI;
	}

	/**
	 * Setter method.
	 * 
	 * @param ridISI
	 *            the ISI Researcher ID
	 */
	public void setRidISI(RestrictedField ridISI) {
		this.ridISI = ridISI;
	}

	/**
	 * Getter method.
	 * 
	 * @return the link to ISI RID page
	 */
	public RestrictedField getRidLinkISI() {
		if (ridLinkISI == null) {
			ridLinkISI = new RestrictedField();
		}
		return ridLinkISI;
	}

	/**
	 * Setter method.
	 * 
	 * @param ridLinkISI
	 *            the link to ISI RID page
	 */
	public void setRidLinkISI(RestrictedField ridLinkISI) {
		this.ridLinkISI = ridLinkISI;
	}

	/**
	 * Getter method.
	 * 
	 * @return the ISI Document Count
	 */
	public RestrictedField getPaperCountISI() {
		if (paperCountISI == null) {
			paperCountISI = new RestrictedField();
		}
		return paperCountISI;
	}

	/**
	 * Setter method.
	 * 
	 * @param paperCountISI
	 *            the ISI Document Count
	 */
	public void setPaperCountISI(RestrictedField paperCountISI) {
		this.paperCountISI = paperCountISI;
	}

	/**
	 * Getter method.
	 * 
	 * @return the ISI Link to Document Count page
	 */
	public RestrictedField getPaperLinkISI() {
		if (paperLinkISI == null) {
			paperLinkISI = new RestrictedField();
		}
		return paperLinkISI;
	}

	/**
	 * Setter method.
	 * 
	 * @param paperLinkISI
	 *            the ISI Link to Document Count page
	 */
	public void setPaperLinkISI(RestrictedField paperLinkISI) {
		this.paperLinkISI = paperLinkISI;
	}

	/**
	 * Getter method.
	 * 
	 * @return the ISI Times Cited
	 */
	public RestrictedField getCitationCountISI() {
		if (citationCountISI == null) {
			citationCountISI = new RestrictedField();
		}
		return citationCountISI;
	}

	/**
	 * Setter method.
	 * 
	 * @param citationCountISI
	 *            the ISI Times Cited
	 */
	public void setCitationCountISI(RestrictedField citationCountISI) {
		this.citationCountISI = citationCountISI;
	}

	/**
	 * Getter method.
	 * 
	 * @return the link to ISI Times Cited page
	 */
	public RestrictedField getCitationLinkISI() {
		if (citationLinkISI == null) {
			citationLinkISI = new RestrictedField();
		}
		return citationLinkISI;
	}

	/**
	 * Setter method.
	 * 
	 * @param citationLinkISI
	 *            the link to ISI Times Cited page
	 */
	public void setCitationLinkISI(RestrictedField citationLinkISI) {
		this.citationLinkISI = citationLinkISI;
	}

	/**
	 * Getter method.
	 * 
	 * @return the ISI Co-Authors
	 */
	public RestrictedField getCoAuthorsISI() {
		if (coAuthorsISI == null) {
			coAuthorsISI = new RestrictedField();
		}
		return coAuthorsISI;
	}

	/**
	 * Setter method.
	 * 
	 * @param coAuthorsISI
	 *            the ISI Co-Authors
	 */
	public void setCoAuthorsISI(RestrictedField coAuthorsISI) {
		this.coAuthorsISI = coAuthorsISI;
	}

	/**
	 * Getter method.
	 * 
	 * @return the ISI h-index
	 */
	public RestrictedField getHindexISI() {
		if (hindexISI == null) {
			hindexISI = new RestrictedField();
		}
		return hindexISI;
	}

	/**
	 * Setter method.
	 * 
	 * @param hindexISI
	 *            the ISI h-index
	 */
	public void setHindexISI(RestrictedField hindexISI) {
		this.hindexISI = hindexISI;
	}

	/**
	 * Getter method.
	 * 
	 * @return the Scopus AuthorID
	 */
	public RestrictedField getAuthorIdScopus() {
		if (authorIdScopus == null) {
			authorIdScopus = new RestrictedField();
		}
		return authorIdScopus;
	}

	/**
	 * Setter method.
	 * 
	 * @param authorIdScopus
	 *            the Scopus AuthorID
	 */
	public void setAuthorIdScopus(RestrictedField authorIdScopus) {
		this.authorIdScopus = authorIdScopus;
	}

	/**
	 * Getter method.
	 * 
	 * @return the link to Scopus AuthorID page
	 */
	public RestrictedField getAuthorIdLinkScopus() {
		if (authorIdLinkScopus == null) {
			authorIdLinkScopus = new RestrictedField();
		}
		return authorIdLinkScopus;
	}

	/**
	 * Setter method.
	 * 
	 * @param authorIdLinkScopus
	 *            the link to Scopus AuthorID page
	 */
	public void setAuthorIdLinkScopus(RestrictedField authorIdLinkScopus) {
		this.authorIdLinkScopus = authorIdLinkScopus;
	}

	/**
	 * Getter method.
	 * 
	 * @return the Scopus document count
	 */
	public RestrictedField getPaperCountScopus() {
		if (paperCountScopus == null) {
			paperCountScopus = new RestrictedField();
		}
		return paperCountScopus;
	}

	/**
	 * Setter method.
	 * 
	 * @param paperCountScopus
	 *            the Scopus document count
	 */
	public void setPaperCountScopus(RestrictedField paperCountScopus) {
		this.paperCountScopus = paperCountScopus;
	}

	/**
	 * Getter method.
	 * 
	 * @return the link to Scopus Document count page
	 */
	public RestrictedField getPaperLinkScopus() {
		if (paperLinkScopus == null) {
			paperLinkScopus = new RestrictedField();
		}
		return paperLinkScopus;
	}

	/**
	 * Setter method.
	 * 
	 * @param paperLinkScopus
	 *            the link to Scopus Document count page
	 */
	public void setPaperLinkScopus(RestrictedField paperLinkScopus) {
		this.paperLinkScopus = paperLinkScopus;
	}

	/**
	 * Getter method.
	 * 
	 * @return the Scopus Cited By
	 */
	public RestrictedField getCitationCountScopus() {
		if (citationCountScopus == null) {
			citationCountScopus = new RestrictedField();
		}
		return citationCountScopus;
	}

	/**
	 * Setter method.
	 * 
	 * @param citationCountScopus
	 *            the Scopus Cited By
	 */
	public void setCitationCountScopus(RestrictedField citationCountScopus) {
		this.citationCountScopus = citationCountScopus;
	}

	/**
	 * Getter method.
	 * 
	 * @return the link to Scopus citation page
	 */
	public RestrictedField getCitationLinkScopus() {
		if (citationLinkScopus == null) {
			citationLinkScopus = new RestrictedField();
		}
		return citationLinkScopus;
	}

	/**
	 * Setter method.
	 * 
	 * @param citationLinkScopus
	 *            the link to Scopus citation page
	 */
	public void setCitationLinkScopus(RestrictedField citationLinkScopus) {
		this.citationLinkScopus = citationLinkScopus;
	}

	/**
	 * Getter method.
	 * 
	 * @return the Scopus co-Author(s)
	 */
	public RestrictedField getCoAuthorsScopus() {
		if (coAuthorsScopus == null) {
			coAuthorsScopus = new RestrictedField();
		}
		return coAuthorsScopus;
	}

	/**
	 * Setter method.
	 * 
	 * @param coAuthorsScopus
	 *            the Scopus co-Author(s)
	 */
	public void setCoAuthorsScopus(RestrictedField coAuthorsScopus) {
		this.coAuthorsScopus = coAuthorsScopus;
	}

	/**
	 * Getter method.
	 * 
	 * @return the the link to Scopus Co-Authors page
	 */
	public RestrictedField getCoAuthorsLinkScopus() {
		if (coAuthorsLinkScopus == null) {
			coAuthorsLinkScopus = new RestrictedField();
		}
		return coAuthorsLinkScopus;
	}

	/**
	 * Setter method.
	 * 
	 * @param coAuthorsLinkScopus
	 *            the link to Scopus Co-Authors page
	 */
	public void setCoAuthorsLinkScopus(RestrictedField coAuthorsLinkScopus) {
		this.coAuthorsLinkScopus = coAuthorsLinkScopus;
	}

	/**
	 * Getter method.
	 * 
	 * @return the Scopus h-index
	 */
	public RestrictedField getHindexScopus() {
		if (hindexScopus == null) {
			hindexScopus = new RestrictedField();
		}
		return hindexScopus;
	}

	/**
	 * Setter method.
	 * 
	 * @param hindexScopus
	 *            the Scopus h-index
	 */
	public void setHindexScopus(RestrictedField hindexScopus) {
		this.hindexScopus = hindexScopus;
	}

	/**
	 * Getter method.
	 * 
	 * @return the url to the personal page of the researcher
	 */
	public RestrictedField getUrlPersonal() {
		if (urlPersonal == null) {
			urlPersonal = new RestrictedField();
		}
		return urlPersonal;
	}

	/**
	 * Setter method.
	 * 
	 * @param urlPersonal
	 *            the url to the personal page of the researcher
	 */
	public void setUrlPersonal(RestrictedField urlPersonal) {
		this.urlPersonal = urlPersonal;
	}

	/**
	 * Getter method.
	 * 
	 * @return the status
	 */
	public Boolean getStatus() {
		return status;
	}

	/**
	 * Setter method.
	 * 
	 * @param id
	 *            the status of this ResearcherPage
	 */
	public void setStatus(Boolean status) {
		this.status = status;
	}

	/**
	 * Setter method.
	 * 
	 * @param media
	 *            the area of expertise list
	 */
	public void setMedia(List<RestrictedField> media) {
		this.media = media;
	}

	/**
	 * Setter method.
	 * 
	 * @param spokenLanguagesEN
	 *            the list of spoken languages in English form
	 */
	public void setSpokenLanguagesEN(List<RestrictedField> spokenLanguagesEN) {
		this.spokenLanguagesEN = spokenLanguagesEN;
	}

	/**
	 * Setter method.
	 * 
	 * @param spokenLanguagesZH
	 *            the list of spoken languages in Chinese form
	 */
	public void setSpokenLanguagesZH(List<RestrictedField> spokenLanguagesZH) {
		this.spokenLanguagesZH = spokenLanguagesZH;
	}

	/**
	 * Setter method.
	 * 
	 * @param writtenLanguagesEN
	 *            the list of written languages in English form
	 */
	public void setWrittenLanguagesEN(List<RestrictedField> writtenLanguagesEN) {
		this.writtenLanguagesEN = writtenLanguagesEN;
	}

	/**
	 * Setter method.
	 * 
	 * @param writtenLanguagesZH
	 *            the list of written languages in Chinese form
	 */
	public void setWrittenLanguagesZH(List<RestrictedField> writtenLanguagesZH) {
		this.writtenLanguagesZH = writtenLanguagesZH;
	}


	/**
	 * Decorate list for dynamic binding with spring mvc
	 * 
	 * @param list
	 * @return lazy list temporary
	 */
	private List getLazyList(List<RestrictedField> list) {
		log.debug("Decorate list for dynamic binding with spring mvc");
		List lazyList = LazyList.decorate(list,
				FactoryUtils.instantiateFactory(RestrictedField.class));

		return lazyList;
	}

}
