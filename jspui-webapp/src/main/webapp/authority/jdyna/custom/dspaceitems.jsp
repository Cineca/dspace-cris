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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="jdynatags" prefix="dyna"%>
<%@ taglib uri="researchertags" prefix="researcher"%>
<%@ taglib uri="http://www.dspace.org/dspace-tags.tld" prefix="dspace" %>
<c:set var="root"><%=request.getContextPath()%></c:set>
<c:set var="researcher" value="${researcher}" scope="request" />

<%
	EPerson user = (EPerson) request.getAttribute("dspace.current.user");
	String order = (String)request.getAttribute("order");
	String type = (String)request.getAttribute("type");
	SortOption so = (SortOption)request.getAttribute("sortedBy");
	String sortedBy = (so == null) ? null : so.getName();
	Item      [] items       = (Item[])request.getAttribute("items");
	int pageTotal   = ((Integer)request.getAttribute("pagetotal"  )).intValue();
	int pageCurrent = ((Integer)request.getAttribute("pagecurrent")).intValue();
	int pageLast    = ((Integer)request.getAttribute("pagelast"   )).intValue();
	int pageFirst   = ((Integer)request.getAttribute("pagefirst"  )).intValue();
	int rpp         = ((Integer)request.getAttribute("rpp"  )).intValue();
	int etAl        = ((Integer)request.getAttribute("etAl"  )).intValue();
	int total		= ((Long)request.getAttribute("total"  )).intValue();
	int start		= ((Integer)request.getAttribute("start"  )).intValue();

	if (items.length > 0) {
%>

<div id="${holder.shortName}" class="showMoreLessBox box">
	<h2 class="showMoreLessControlElement control expanded">
		<img src="<%=request.getContextPath() %>/image/cris/btn_lite_expand.gif" class="hide" />
		<img src="<%=request.getContextPath() %>/image/cris/btn_lite_collapse.gif" />
			<fmt:message key="jsp.layout.hku.detail.fieldset-legend.itemhub.${type}">
				<fmt:param>${researcher.academicName.value}</fmt:param>
			</fmt:message>
	</h2>
<div class="collapsable expanded-content" ${holder.collapsed?"style=\"display: none;\"":""}>


<!-- prepare pagination controls -->
<%
    // create the URLs accessing the previous and next search result pages
    StringBuilder sb = new StringBuilder();
	sb.append("<tr><td align=\"center\" class=\"menuBar\">");
	sb.append("Result pages:");
	
    String prevURL =  "?open=" + type
                    + "&amp;sort_by=" + (so != null ? so.getNumber() : 0)
                    + "&amp;order=" + order
                    + "&amp;rpp=" + rpp
                    + "&amp;etal=" + etAl
                    + "&amp;start=";

    String nextURL = prevURL;

    prevURL = prevURL
            + (pageCurrent-2) * rpp;

    nextURL = nextURL
            + (pageCurrent) * rpp;


if (pageFirst != pageCurrent) {
  sb.append(" <a class=\"pagination\" href=\"");
  sb.append(prevURL);
  sb.append("\">previous</a>");
};

for( int q = pageFirst; q <= pageLast; q++ )
{
    String myLink = "<a class='pagination' href=\""
    				+ "?open=" + type
                    + "&amp;sort_by=" + (so != null ? so.getNumber() : 0)
                    + "&amp;order=" + order
                    + "&amp;rpp=" + rpp
                    + "&amp;etal=" + etAl
                    + "&amp;start=";

    if( q == pageCurrent )
    {
        myLink = "" + q;
    }
    else
    {
        myLink = myLink
            + (q-1) * rpp
            + "\">"
            + q
            + "</a>";
    }
    sb.append(" " + myLink);
} // for

if (pageTotal > pageCurrent) {
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
        <fmt:param><%=start+1%></fmt:param>
        <fmt:param><%=start+items.length%></fmt:param>
        <fmt:param><%=total%></fmt:param>
    </fmt:message></p>

</td></tr>
<%
if (pageTotal > 1)
{
%>
<%= sb %>
<%
	}
%>
<tr><td>
<form id="sortform" action="#<%= type %>" method="get">
<input id="sort_by" type="hidden" name="sort_by"
<%
			Set<SortOption> sortOptions = SortOption.getSortOptions();
			if (sortOptions.size() > 1)
			{
               for (SortOption sortBy : sortOptions)
               {
                   if (sortBy.isVisible())
                   {
                       String selected = (sortBy.getName().equals(sortedBy) ? "value=\""+ sortBy.getName()+"\"" : "");
                   }
               }
			}
%>
/>

           <input id="order" type="hidden" name="order" value="<%= order %>" />
		   <input type="hidden" name="open" value="<%= type %>" />
</form>
			
<dspace:itemlist items="<%= items %>" sortOption="<%= so %>" order="<%= order.toUpperCase() %>" 
	authorLimit="<%= etAl %>" itemStart="1"/>

			
</td></tr>
<%-- show pagniation controls at bottom --%>
<%
	if (pageTotal > 1)
	{
%>
<%= sb %>
<%
	}
%>
</table>


</div>

<p></p>

<div class="showMoreLessBox box" style="border: 1px solid gray; padding: 5px; margin:5px;">
<form class="citationExportBottom" action="<%= request.getContextPath() %>/references/${authority}"
	method="post">


<table cellspacing="0" cellpadding="10">
	<tbody>
		<tr>
			<td class="columnBody" colspan="6"><strong><fmt:message key="jsp.exportcitations.box.label"/></strong></td>
		</tr>
		<tr>
			<td width="10"><img src="<%= request.getContextPath() %>/image/spacer.gif"></td>
			<td nowrap="nowrap" valign="top" class="columnBody">
			<div id="material" class="columnHead"><u><fmt:message key="jsp.exportcitations.box.selectmaterial"/></u></div>


			
				<c:forEach var="snav" items="${sublinktoexport}">
					<input type="checkbox" value="${snav[0]}" name="recordtype" />
                                ${snav[1]}<br />

				</c:forEach>
			
			</td>		
		
		
		
			<td class="columnSeparator"><img height="50" border="1" width="1" src="<%= request.getContextPath() %>/image/spacer.gif"></td>
			<td nowrap="nowrap" valign="top" class="columnBody">
			<div class="columnHead"><u><fmt:message key="jsp.exportcitations.box.selectcontent"/></u></div>
			<input type="radio" checked="checked" value="false" id="citation"
				name="fulltext"> <fmt:message key="jsp.exportcitations.box.selectcontent.citationonly"/><br>
			<input <% if(user==null) { %> disabled="disabled" <% } %>	type="radio" value="true" id="fulltext"
				name="fulltext"> <fmt:message key="jsp.exportcitations.box.selectcontent.citationfulltext"/><br>
			<% if(user==null) { %> <span style="font-size: small;"> <fmt:message key="jsp.exportcitations.box.messagelogin"/> </span> <% } %>
			</td>
			<td class="columnSeparator"><img height="50" border="1" width="1" src="../../image/spacer.gif"></td>
			<td valign="top" class="columnBody">
			<div class="columnHead"><u><fmt:message key="jsp.exportcitations.box.selectmode"/></u></div>
			<br />
			<div class="columnBody">
			<select name="format">
				<c:forEach items="${exportscitations}" var="citation">
					<option value="${citation}"><fmt:message
						key="exportcitation.option.${citation}"></fmt:message></option>
				</c:forEach>
			</select> <br />
				&nbsp;&nbsp;&nbsp;
										
				<% if(user!=null) {%>
				<input border="0" align="absmiddle" type="image"
				title="E-mail the selected records" src="<%= request.getContextPath() %>/image/email.png"
				name="submitemail" id="submitemail"/> 
				
				&nbsp;&nbsp;&nbsp;
				<% } %>	
				
				<input
				border="0" align="absmiddle" type="image"
				title="Save the selected records" src="<%= request.getContextPath() %>/image/disk.png" 
				name="submitsave" id="submitsave"/></div>
				
				<input type="hidden" name="email" id="email" value="false"/>
				
				
			</td>
		
			
		</tr>
		<tr>
		<td width="10"><img src="<%= request.getContextPath() %>/image/spacer.gif"></td>
		</tr>
	</tbody>
</table>
	
</form>


</div>
<br/>
<span id="message" style="display:none; font-size: small; font: red;"> Select a material type, please.</span>
<% } %>

<script type="text/javascript"><!--

		var j = jQuery.noConflict();
		j(document).ready(function()
		{
		var opt = {};
	  	j("#submitemail").click(function() {
	  		j("#email").val("true");
	  		if(j("input[name='recordtype']").is(':checked')) {
	  			j(this).submit();
	  		}		    
	  		else {
	  			j("#message").show();
	  				  			 	  			
	  			return false;
	  		}
		});
	  	j("#submitsave").click(function() {
	  		j("#email").val("false");
	  		if(j("input[name='recordtype']").is(':checked')) {
	  			j(this).submit();
	  		}		    
	  		else {
	  			j("#message").show();
	  			 	  			
	  			return false;
	  		} 
		});
		
    	  j("#fulltext").click(function()
		  {
			  j("#submitsave").hide();
			  
			  
		  });
		  j("#citation").click(function()
		  {
			  j("#submitsave").show();
			  
					  
		  });

		  j("input[name='recordtype']").click(function() {
			  j("#message").hide();
		  });

});
-->
</script>