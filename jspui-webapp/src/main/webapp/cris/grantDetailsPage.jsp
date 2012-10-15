<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.dspace.org/dspace-tags.tld" prefix="dspace"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>


<c:set var="dspace.layout.head" scope="request">
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/jquery-1.4.2.min.js"></script>
	<style>
ul#css3menu1,ul#css3menu1 ul {
	list-style: none outside none;
	margin: 0;
	padding: 0;
}

ul#css3menu1 {
	display: inline;
	float: left;
	text-align: left;
	width: 250px;
}

ul#css3menu1 a:active,ul#css3menu1 a:focus {
	outline-style: none;
}

ul#css3menu1 li:hover>* {
	display: block;
}

ul#css3menu1 li:hover {
	position: relative;
}

ul#css3menu1 li {
	display: block;
	float: left;
	white-space: nowrap;
}

ul#css3menu1>li,ul#css3menu1 li {
	margin: 0;
}

ul#css3menu1 li:hover>a {
	text-decoration: none;
}

ul#css3menu1 ul {
	-moz-border-radius: 6px 6px 6px 6px;
	-moz-box-shadow: 3.5px 3.5px 5px #000000;
	background-color: #CCCCFF;
	border-color: #000000;
	display: none;
	left: 10px;
	padding: 0;
	position: absolute;
}

ul#css3menu1 ul li {
	float: none;
	margin: 0;
	padding: 5px;
}

ul#css3menu1 ul li a {
	background-color: #CCCCFF;
	color: #000000;
	text-decoration: none;
}

ul#css3menu1 ul li:hover>a {
	text-decoration: underline;
}
</style>
</c:set>
<tr>
	<td>
<dspace:layout style="hku-researcher" titlekey="jsp.grant-page.details">

			<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<tr>

					<td width="200" valign="top" align="left"><%@ include
							file="/layout/navbar-hku-grant.jsp"%>
					</td>
					<td width="700" valign="top" align="left"><%@ include
							file="/authority/_grantDetailsPage.jsp"%>
					</td>
				</tr>
			</table>
			
</dspace:layout>