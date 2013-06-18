package org.dspace.app.cris.network;

			  
public class VisualizationGraphKeywordsPublication extends AVisualizationGraphModeTwo  {

	public final static String CONNECTION_NAME = "kwdpub";
	private static final String FIELD_QUERY = "dc.subject_sort";
	public static final String FA_VALUE = "author_filter";


	@Override
	public String getType() {
		return CONNECTION_NAME;
	}

	@Override
	public String getLineWidthToOverride() {
		return "1.5";
	}

	
	@Override
	public String getConnectionName() {
		return CONNECTION_NAME;
	}
	
	@Override
	protected String getFacetFieldQuery() {
		return FIELD_QUERY;
	}

	@Override
	public String getFacet(String value) {
		return FA_VALUE;
	}
	
    @Override
    protected String buildExtraCustom(String extra)
    {
        // TODO Auto-generated method stub
        return null;
    }
}
