<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>


<%@ taglib uri="jdynatags" prefix="dyna" %>




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
		<tr>		
			<td>
			
		<table id="tabletitlefields" cellpadding="0" cellspacing="4">
		<c:forEach items="${researcher.title}" var="title"
			varStatus="i">
			<tr>
				<td>Title #${i.index}:</td>
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