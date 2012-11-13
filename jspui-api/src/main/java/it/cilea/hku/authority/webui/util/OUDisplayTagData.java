/**
 * The contents of this file are subject to the license and copyright
 *  detailed in the LICENSE and NOTICE files at the root of the source
 *  tree and available online at
 *  
 *  https://github.com/CILEA/dspace-cris/wiki/License
 */
package it.cilea.hku.authority.webui.util;

import it.cilea.hku.authority.webui.dto.OrganizationUnitDTO;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;

public class OUDisplayTagData implements PaginatedList, Serializable {

	public static final int PAGE_SIZE_DEFAULT = 20;

	private long totalCount;

	private List<OrganizationUnitDTO> pageItems;

	private int pageSize;

	private int page;

	private String sort;

	private String dir;

	public OUDisplayTagData() {
		this(0, Collections.EMPTY_LIST, "id", "asc", 1, PAGE_SIZE_DEFAULT);
	}

	public OUDisplayTagData(long count, List pageItems, String sort, String dir,
			int page, int pageSize) {
		this.totalCount = count;
		this.pageItems = pageItems;
		this.sort = sort;
		this.dir = dir;
		this.page = page;
		this.pageSize = pageSize;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public void setPageItems(List<OrganizationUnitDTO> pageItems) {
		this.pageItems = pageItems;
	}
	
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	// PaginatedList

	public int getFullListSize() {
		return new Long(totalCount).intValue();
	}

	public List<OrganizationUnitDTO> getList() {
		return pageItems;
	}

	public int getObjectsPerPage() {
		return pageSize;
	}

	public int getPageNumber() {
		return page;
	}

	public String getSearchId() {
		return null;
	}

	public String getSortCriterion() {
		return sort;
	}

	public SortOrderEnum getSortDirection() {
		return "asc".equals(dir) ? SortOrderEnum.ASCENDING : ("desc"
				.equals(dir) ? SortOrderEnum.DESCENDING : null);
	}

}
