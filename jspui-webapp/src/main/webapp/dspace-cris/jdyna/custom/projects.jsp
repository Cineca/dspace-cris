<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.util.List"%>
<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>
<%@ page import="java.net.URLEncoder"            %>
<%@ page import="org.dspace.search.QueryResults" %>
<%@ page import="org.dspace.sort.SortOption" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="java.util.Set" %>
<%@ page import="org.dspace.browse.BrowseItem" %>

<%@page import="org.dspace.eperson.EPerson"%>
<%@ taglib uri="http://www.dspace.org/dspace-tags.tld" prefix="dspace" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="jdynatags" prefix="dyna"%>

<c:set var="root"><%=request.getContextPath()%></c:set>
<c:set var="researcher" value="${researcher}" scope="request" />

<%
	EPerson user = (EPerson) request.getAttribute("dspace.current.user");
	String orderpprojectlist = (String)request.getAttribute("orderprojectlist");
	String typeprojectlist = (String)request.getAttribute("typeprojectlist");
	SortOption soprojectlist = (SortOption)request.getAttribute("sortedByprojectlist");
	String sortedByprojectlist = (soprojectlist == null) ? null : soprojectlist.getName();
	BrowseItem[] itemsprojectlist   = (BrowseItem[])request.getAttribute("itemsprojectlist");	
	int pagetotalprojectlist   = ((Integer)request.getAttribute("pagetotalprojectlist"  )).intValue();
	int pagecurrentprojectlist = ((Integer)request.getAttribute("pagecurrentprojectlist")).intValue();
	int pagelastprojectlist    = ((Integer)request.getAttribute("pagelastprojectlist"   )).intValue();
	int pagefirstprojectlist   = ((Integer)request.getAttribute("pagefirstprojectlist"  )).intValue();
	int rppprojectlist         = ((Integer)request.getAttribute("rppprojectlist"  )).intValue();
	int etAlprojectlist        = ((Integer)request.getAttribute("etAlprojectlist"  )).intValue();
	int totalprojectlist		= ((Long)request.getAttribute("totalprojectlist"  )).intValue();
	int startprojectlist		= ((Integer)request.getAttribute("startprojectlist"  )).intValue();

	if (itemsprojectlist.length > 0) {
%>

	
	
<div id="${holder.shortName}" class="box ${holder.collapsed?"":"expanded"}">
	<h3>
		<a href="#"><fmt:message
				key="jsp.layout.dspace.detail.fieldset-legend.component.${typeprojectlist}">
				<fmt:param>${researcher.preferredName.value}</fmt:param>
			</fmt:message> </a>
	</h3>
<div>
	<p>


<!-- prepare pagination controls -->
<%
    // create the URLs accessing the previous and next search result pages
    StringBuilder sb = new StringBuilder();
	sb.append("<div align=\"center\">");
	sb.append("Result pages:");
	
    String prevURL =  "?open=" + typeprojectlist
                    + "&amp;sort_by"+typeprojectlist+"=" + (soprojectlist != null ? soprojectlist.getNumber() : 0)
                    + "&amp;order"+typeprojectlist+"=" + orderpprojectlist
                    + "&amp;rpp"+typeprojectlist+"=" + rppprojectlist
                    + "&amp;etal"+typeprojectlist+"=" + etAlprojectlist
                    + "&amp;start"+typeprojectlist+"=";

    String nextURL = prevURL;

    prevURL = prevURL
            + (pagecurrentprojectlist-2) * rppprojectlist;

    nextURL = nextURL
            + (pagecurrentprojectlist) * rppprojectlist;


if (pagefirstprojectlist != pagecurrentprojectlist) {
  sb.append(" <a class=\"pagination\" href=\"");
  sb.append(prevURL);
  sb.append("\">previous</a>");
};

for( int q = pagefirstprojectlist; q <= pagelastprojectlist; q++ )
{
    String myLink = "<a class='pagination' href=\""
    				+ "?open=" + typeprojectlist
                    + "&amp;sort_by"+typeprojectlist+"=" + (soprojectlist != null ? soprojectlist.getNumber() : 0)
                    + "&amp;order"+typeprojectlist+"=" + orderpprojectlist
                    + "&amp;rpp"+typeprojectlist+"=" + rppprojectlist
                    + "&amp;etal"+typeprojectlist+"=" + etAlprojectlist
                    + "&amp;start"+typeprojectlist+"=";

    if( q == pagecurrentprojectlist )
    {
        myLink = "" + q;
    }
    else
    {
        myLink = myLink
            + (q-1) * rppprojectlist
            + "\">"
            + q
            + "</a>";
    }
    sb.append(" " + myLink);
} // for

if (pagetotalprojectlist > pagecurrentprojectlist) {
  sb.append(" <a class=\"pagination\" href=\"");
  sb.append(nextURL);
  sb.append("\">next</a>");
}

sb.append("</div>");

%>

<div align="center" class="browse_range">

	<p align="center"><fmt:message key="jsp.search.results.results">
        <fmt:param><%=startprojectlist+1%></fmt:param>
        <fmt:param><%=startprojectlist+itemsprojectlist.length%></fmt:param>
        <fmt:param><%=totalprojectlist%></fmt:param>
    </fmt:message></p>

</div>

<%
if (pagetotalprojectlist > 1)
{
%>
<%= sb %>
<%
	}
%>
			
<dspace:browselist items="<%= itemsprojectlist %>" config="crisproject" />

			

<%-- show pagniation controls at bottom --%>
<%
	if (pagetotalprojectlist > 1)
	{
%>
<%= sb %>
<%
	}
%>

</p>
</div>
</div>

<% } %>