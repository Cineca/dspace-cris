/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * https://github.com/CILEA/dspace-cris/wiki/License
 */
package org.dspace.app.webui.cris.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.queryParser.QueryParser.Operator;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.params.FacetParams;
import org.dspace.app.cris.discovery.CrisSearchService;
import org.dspace.app.cris.model.CrisConstants;
import org.dspace.app.cris.model.ResearcherPage;
import org.dspace.app.cris.util.ResearcherPageUtils;
import org.dspace.app.webui.cris.dto.RPSearchDTO;
import org.dspace.app.webui.cris.dto.ResearcherPageDTO;
import org.dspace.app.webui.cris.util.RPDisplayTagData;
import org.dspace.app.webui.util.UIUtil;
import org.dspace.authorize.AuthorizeManager;
import org.dspace.core.ConfigurationManager;
import org.dspace.core.Context;
import org.dspace.discovery.DiscoverResult;
import org.dspace.discovery.SearchUtils;
import org.dspace.discovery.configuration.DiscoveryConfiguration;
import org.dspace.discovery.configuration.DiscoverySearchFilter;
import org.dspace.discovery.configuration.DiscoverySearchFilterFacet;
import org.dspace.eperson.EPerson;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

public class RPSearchFormController extends BaseFormController {

	private CrisSearchService searchService;

	public void setSearchService(CrisSearchService searchService) {
		this.searchService = searchService;
	}

	protected boolean isFormSubmission(HttpServletRequest request) {
		// this form is implemented as a GET, so look for the "search" URL
		// parameter
		if (super.isFormSubmission(request)) {
			return true;
		} else if (request.getParameter("searchMode") != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected Map referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		Context context = UIUtil.obtainContext(request);
		EPerson currUser = context.getCurrentUser();
		boolean isAdmin = AuthorizeManager.isAdmin(context);
		if (currUser != null) {
			model.put("researcher_page_menu", new Boolean(true));
		}
		if (isAdmin) {
			model.put("see_search_staffno", new Boolean(true));
			model.put("see_search_rp", new Boolean(true));// not used on jsp
															// (now search for
															// RP
															// is public)
		}

		SolrQuery query = new SolrQuery();
		query.setQuery("disabled:false");

		query.setFacet(true);
		query.setFacetLimit(-1);
		query.setFacetMinCount(1);
		query.setFacetMissing(true);
		query.setFacetSort(FacetParams.FACET_SORT_INDEX);
		// check table name
		query.addFacetField("faculty_filter");
		query.setRows(0);

		QueryResponse qResponse = ((CrisSearchService) searchService)
				.search(query);
		
		FacetField facetField = qResponse.getFacetField("faculty_filter");
		
		List<DiscoverResult.FacetResult> faculties = new ArrayList<DiscoverResult.FacetResult>();
		List<Count> values = facetField.getValues();
		if (values != null)
		{
			for (FacetField.Count facetValue : values) {
				DiscoverResult.FacetResult fr = searchService.getDiscoveryFacet(context, facetField, facetValue);
				faculties.add(fr);
			}
		}
		DiscoveryConfiguration discoveryConf = SearchUtils.getDiscoveryConfigurationByName("crisrp");
		
		List<String> searchFields = new LinkedList<String>();
		for (DiscoverySearchFilter field : discoveryConf.getSearchFilters()) {
			searchFields.add(field.getIndexFieldName());
		}
		model.put("faculties", faculties);
		model.put("fields", searchFields);
		return model;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		return null;
	}
}
