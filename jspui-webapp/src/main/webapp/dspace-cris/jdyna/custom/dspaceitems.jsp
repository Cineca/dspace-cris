<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.util.List"%>
<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>
<%@ page import="java.net.URLEncoder"            %>
<%@ page import="org.dspace.content.Item"        %>
<%@ page import="org.dspace.search.QueryResults" %>
<%@ page import="org.dspace.sort.SortOption" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="java.util.Set" %>
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
	String orderpublicationlist = (String)request.getAttribute("orderpublicationlist");
	String typepublicationlist = (String)request.getAttribute("typepublicationlist");
	SortOption sopublicationlist = (SortOption)request.getAttribute("sortedBypublicationlist");
	String sortedBypublicationlist = (sopublicationlist == null) ? null : sopublicationlist.getName();
	Item[] itemspublicationlist   = (Item[])request.getAttribute("itemspublicationlist");	
	int pagetotalpublicationlist   = ((Integer)request.getAttribute("pagetotalpublicationlist"  )).intValue();
	int pagecurrentpublicationlist = ((Integer)request.getAttribute("pagecurrentpublicationlist")).intValue();
	int pagelastpublicationlist    = ((Integer)request.getAttribute("pagelastpublicationlist"   )).intValue();
	int pagefirstpublicationlist   = ((Integer)request.getAttribute("pagefirstpublicationlist"  )).intValue();
	int rpppublicationlist         = ((Integer)request.getAttribute("rpppublicationlist"  )).intValue();
	int etAlpublicationlist        = ((Integer)request.getAttribute("etAlpublicationlist"  )).intValue();
	int totalpublicationlist		= ((Long)request.getAttribute("totalpublicationlist"  )).intValue();
	int startpublicationlist		= ((Integer)request.getAttribute("startpublicationlist"  )).intValue();

	if (itemspublicationlist.length > 0) {
%>

	
<div id="${holder.shortName}" class="box ${holder.collapsed?"":"expanded"}">
	<h3>
		<a href="#"><fmt:message
				key="jsp.layout.dspace.detail.fieldset-legend.component.${typepublicationlist}">
				<fmt:param>${researcher.preferredName.value}</fmt:param>
			</fmt:message> </a>
	</h3>
<div>
	<p>


<!-- prepare pagination controls -->
<%
    // create the URLs accessing the previous and next search result pages
    StringBuilder sb = new StringBuilder();
	sb.append("<tr><td align=\"center\" class=\"menuBar\">");
	sb.append("Result pages:");
	
    String prevURL =  "?open=" + typepublicationlist
                    + "&amp;sort_by=" + (sopublicationlist != null ? sopublicationlist.getNumber() : 0)
                    + "&amp;order=" + orderpublicationlist
                    + "&amp;rpp=" + rpppublicationlist
                    + "&amp;etal=" + etAlpublicationlist
                    + "&amp;start=";

    String nextURL = prevURL;

    prevURL = prevURL
            + (pagecurrentpublicationlist-2) * rpppublicationlist;

    nextURL = nextURL
            + (pagecurrentpublicationlist) * rpppublicationlist;


if (pagefirstpublicationlist != pagecurrentpublicationlist) {
  sb.append(" <a class=\"pagination\" href=\"");
  sb.append(prevURL);
  sb.append("\">previous</a>");
};

for( int q = pagefirstpublicationlist; q <= pagelastpublicationlist; q++ )
{
    String myLink = "<a class='pagination' href=\""
    				+ "?open=" + typepublicationlist
                    + "&amp;sort_by=" + (sopublicationlist != null ? sopublicationlist.getNumber() : 0)
                    + "&amp;order=" + orderpublicationlist
                    + "&amp;rpp=" + rpppublicationlist
                    + "&amp;etal=" + etAlpublicationlist
                    + "&amp;start=";

    if( q == pagecurrentpublicationlist )
    {
        myLink = "" + q;
    }
    else
    {
        myLink = myLink
            + (q-1) * rpppublicationlist
            + "\">"
            + q
            + "</a>";
    }
    sb.append(" " + myLink);
} // for

if (pagetotalpublicationlist > pagecurrentpublicationlist) {
  sb.append(" <a class=\"pagination\" href=\"");
  sb.append(nextURL);
  sb.append("\">next</a>");
}

sb.append("</td></tr>");

%>

<table align="center" class="miscTable">
	<tr>
		<td width="100%" colspan="3">

	<p align="center"><fmt:message key="jsp.search.results.results">
        <fmt:param><%=startpublicationlist+1%></fmt:param>
        <fmt:param><%=startpublicationlist+itemspublicationlist.length%></fmt:param>
        <fmt:param><%=totalpublicationlist%></fmt:param>
    </fmt:message></p>

</td></tr>
<%
if (pagetotalpublicationlist > 1)
{
%>
<%= sb %>
<%
	}
%>
<tr><td>
<form id="sortform" action="#<%= typepublicationlist %>" method="get">
<input id="sort_by" type="hidden" name="sort_by"
<%
			Set<SortOption> sortOptions = SortOption.getSortOptions();
			if (sortOptions.size() > 1)
			{
               for (SortOption sortBy : sortOptions)
               {
                   if (sortBy.isVisible())
                   {
                       String selected = (sortBy.getName().equals(sortedBypublicationlist) ? "value=\""+ sortBy.getName()+"\"" : "");
                   }
               }
			}
%>
/>

           <input id="order" type="hidden" name="order" value="<%= orderpublicationlist %>" />
		   <input type="hidden" name="open" value="<%= typepublicationlist %>" />
</form>
			
<dspace:itemlist items="<%= itemspublicationlist %>" sortOption="<%= sopublicationlist %>" authorLimit="<%= etAlpublicationlist %>" />

			
</td></tr>
<%-- show pagniation controls at bottom --%>
<%
	if (pagetotalpublicationlist > 1)
	{
%>
<%= sb %>
<%
	}
%>
</table>

</p>
</div>
</div>

<% } %>