<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>


<%@ taglib uri="jdynatags" prefix="dyna" %>


<c:set var="root"><%= request.getContextPath() %></c:set>

<script type="text/javascript">

	function addVariant() {
		 var ni = document.getElementById('myDiv');
		 var numi = document.getElementById('theValue');
		 var num = (document.getElementById("theValue").value - 1) + 2;
		 numi.value = num;

		 var tbody = (document.getElementById('tablevariantfields')).getElementsByTagName("tbody")[0];
		 
		 var td1 = document.createElement("td")
		 var row = document.createElement('tr');		
		 var newTD0 = document.createElement('td');
		 var newTD1 = document.createElement('td');
		 var newTD2 = document.createElement('td');

		 
		 //newdiv.setAttribute("id", divIdName);		 	 
		 newTD0.innerHTML = "Variant #"+(num-1);
		 newTD1.innerHTML = "<input size=\"80\%\" type=\"text\" name=\"variants[" + (num - 1) + "].value\" value=\"\">";
		 newTD2.innerHTML = "<input checked=\"checked\" type=\"checkbox\" name=\"variants["+ (num-1) +"].visibility\" value=\"1\"> <input type=\"hidden\" name=\"_variants["+ (num-1) +"].visibility\" value=\"on\">";
 			  
		 row.appendChild(newTD0);
		 row.appendChild(newTD1);
		 row.appendChild(newTD2);
		 tbody.appendChild(row);
		 		 
	}
	
</script>

	<table width="98%" cellpadding="0"
		cellspacing="4">

		<tr>
		<td>
					<table id="tablevariantfields" align="left" cellpadding="0" cellspacing="4">

				<c:forEach items="${researcher.variants}" var="variant"
					varStatus="i">
				<tr>
				<td>Variant #${i.index}:</td>
				<td><form:input path="variants[${i.index}].value"
					size="80%" /></td>
				<td><form:checkbox
					path="variants[${i.index}].visibility" value="1" /></td>
				</tr>
				</c:forEach>
				<tr>
					<td colspan="4"><div id="myDiv"><input type="hidden" id="theValue" value="${fn:length(researcher.variants)}"/></div></td>
				</tr>

				&nbsp;

			</table>
		</td></tr></table>