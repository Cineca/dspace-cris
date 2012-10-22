<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.dspace.org/dspace-tags.tld" prefix="dspace" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<c:if test="${!empty navigation}">


				<c:forEach var="snav" items="${navigation[areashortName]}">		
					<li><a href="<%= request.getContextPath() %>/cris/${specificPath}/${authority}/${areashortName}.html?open=${snav[0]}#${snav[2]}">${snav[1]}</a></li>
				</c:forEach>

			
		
</c:if>
    