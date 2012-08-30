<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>


<%@ taglib uri="jdynatags" prefix="dyna" %>

<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax" %>
<c:set var="root"><%= request.getContextPath() %></c:set>
<%
Boolean isAdminB = (Boolean) request.getAttribute("is.admin");
boolean isAdmin = (isAdminB != null?isAdminB.booleanValue():false);
%>
<c:set var="admin"><%=isAdmin%></c:set>
<script type="text/javascript">


	function addTitle() {
		 var ni = document.getElementById('myDivTitle');
		 var numi = document.getElementById('theValueTitle');
		 var num = (document.getElementById("theValueTitle").value - 1) + 2;
		 numi.value = num;

		 var tbody = (document.getElementById('tabletitlefields')).getElementsByTagName("tbody")[0];
		 
		 var td1 = document.createElement("td")
		 var row = document.createElement('tr');		
		 var newTD0 = document.createElement('td');
		 var newTD1 = document.createElement('td');
		 var newTD2 = document.createElement('td');

		 
		 //newdiv.setAttribute("id", divIdName);		 	 
		 newTD0.innerHTML = "Title #"+(num-1);
		 newTD1.innerHTML = "<input size=\"80\%\" type=\"text\" name=\"title[" + (num - 1) + "].value\" value=\"\">";
		 newTD2.innerHTML = "<input checked=\"checked\" type=\"checkbox\" name=\"title["+ (num-1) +"].visibility\" value=\"1\"> <input type=\"hidden\" name=\"_title["+ (num-1) +"].visibility\" value=\"on\">";
 			  
		 row.appendChild(newTD0);
		 row.appendChild(newTD1);
		 row.appendChild(newTD2);
		 tbody.appendChild(row);
	}


</script>




<div id="hidden_first${holder.shortName}">
						<fieldset><legend> <a
							onclick="Effect.toggle('hidden_appear${holder.shortName}', 'appear'); Effect.toggle('toggle_appear${holder.shortName}', 'blind'); Effect.toggle('hidden_first${holder.shortName}', 'blind'); return false;"
							href="#"> <span id="toggle_appear${holder.shortName}"> <img
							src="<%=request.getContextPath()%>/images/collapse.gif"
							border="0" /> </span></a> ${holder.title} </legend><label>
<!--c:if test="${isThereMetadataNoEditable eq true}"-->

	<span class="green"><fmt:message
		key='jsp.layout.hku.researcher.message.sendemail'>
		<fmt:param>
			<fmt:message
				key='jsp.layout.hku.researcher.message.personsandemail.${holder.shortName}' />
		</fmt:param>
	</fmt:message></span>

<!--/c:if--> </label>
	<table width="98%" cellpadding="0"
		cellspacing="4">

		<tr>		
			<td>
			
		<table id="tabletitlefields" cellpadding="0" cellspacing="4">
		<c:forEach items="${researcher.title}" var="title"
			varStatus="i">
			<tr>
				<td><span class="dynaLabel">Title #${i.index}:</span></td>
				<td><form:input path="title[${i.index}].value"
					size="80%" /></td>
				<td><form:checkbox
					path="title[${i.index}].visibility" value="1" /></td>
			</tr>
		</c:forEach>
		<tr>											
			<td colspan="4"><div id="myDivTitle"><input type="hidden" id="theValueTitle" value="${fn:length(researcher.title)}"/></div></td>
		</tr>
		&nbsp;
		</table>
		
		</td>
		</tr>
		<tr>
			<td><input id="addTitles" name="addTitles" type="button" onclick="addTitle()" value="Add Title"/></td>
		</tr>
		
		
	</table>
</fieldset></div>
		<div id="hidden_appear${holder.shortName}" style="display: none;">
						<fieldset><legend> <a
							onclick="Effect.toggle('hidden_appear${holder.shortName}', 'appear'); Effect.toggle('toggle_appear${holder.shortName}', 'appear'); Effect.toggle('hidden_first${holder.shortName}', 'slide');return false;"
							href="#"> <span id="less_more${holder.shortName}"> <img
							src="<%=request.getContextPath()%>/images/expand.gif" border="0" />
						</span> </a>${holder.title}</legend><label></label></fieldset>
						</div>
		<p></p>