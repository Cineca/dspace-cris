<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div id="${holder.shortName}" class="showMoreLessBox-dark box">
		<h2 class="showMoreLessControlElement control ${holder.collapsed?"":"expanded"}">
		<img src="<%=request.getContextPath() %>/images/btn_lite_expand.gif"  ${holder.collapsed?"":"class=\"hide\""}/>
		<img src="<%=request.getContextPath() %>/images/btn_lite_collapse.gif" ${holder.collapsed?"class=\"hide\"":""} />
		${holder.title}</h2>
   		<div class="collapsable expanded-content" ${holder.collapsed?"style=\"display: none;\"":""}>

		<table border="0" cellpadding="0" cellspacing="0">

			<tr>
			<c:if
								test="${!empty researcher.pict.value && researcher.pict.visibility==1}">
				<td valign="top" class="columnBody">

				
				
				<table width="170px" border="0">
					<tr>

						<td class="image_td" colspan="2" align="center">

						<div class="image">
							
								<img id="picture" name="picture"
									alt="${researcher.fullName} picture"
									src="<%=request.getContextPath()%>/researcherimage/${authority}"
									title="${researcher.fullName} picture" />
						</div>

						</td>

					</tr>
		
						
				</table>

				
				
				</td>



				<td>&nbsp;</td>

</c:if>




				<td class="columnBody" width="70%">

				<table border="0" width="430">
					<tr>
						<td colspan="2">
						<span class="header4"> <c:if
							test="${researcher.honorific.visibility==1}">${researcher.honorific.value}</c:if>
						${researcher.fullName}</span>

<!-- R-Badge -->
                  <c:if
							test="${!empty researcher.ridISI.value && researcher.ridISI.visibility==1}">
							<span id='badgeCont100000' style='width: 26px'><script
								src='http://labs.researcherid.com/mashlets?el=badgeCont100000&mashlet=badge&showTitle=false&className=a&rid=${researcher.ridISI.value}&size=small'></script></span>
						</c:if> 

<!-- no use -->
<!--
    					<img src="rid.jpg" alt="RID Badge" width="24" height="24" />
-->
                  <br />
						 <c:if
							test="${researcher.chineseName.visibility==1}">
							<span class="header4">${researcher.chineseName.value}</span><br/>
						</c:if>
						<span class="header6"> <c:forEach
							items="${researcher.title}" var="title">
							<c:if test="${title.visibility==1}">
								<strong> ${title.value}</strong><br/>
							</c:if>
						</c:forEach></span>
						</td>
					</tr>

					
				
					
				</table>
				</td>
			</tr>
		</table>

		</div>

		</div>
		<p></p>