<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="jdynatags" prefix="dyna"%>

<div id="${holder.shortName}" class="showMoreLessBox box">
<h2 class="showMoreLessControlElement control ${holder.collapsed?"":"expanded"}">
<img src="<%=request.getContextPath() %>/images/btn_lite_expand.gif"  ${holder.collapsed?"":"class=\"hide\""}/>
<img src="<%=request.getContextPath() %>/images/btn_lite_collapse.gif" ${holder.collapsed?"class=\"hide\"":""} />
${holder.title}</h2>
<div class="collapsable expanded-content" ${holder.collapsed?"style=\"display: none;\"":""}>

<table width="100%" cellpadding="0" cellspacing="4">
<tr>
<td><c:set var="hideLabel">${fn:length(propertiesDefinitionsInHolder[holder.shortName]) le 1}</c:set>
<c:forEach items="${propertiesDefinitionsInHolder[holder.shortName]}" var="tipologiaDaVisualizzare" varStatus="status">
<c:if test="${tipologiaDaVisualizzare.class.simpleName eq 'DecoratorRPPropertiesDefinition'}">

<dyna:display tipologia="${tipologiaDaVisualizzare.real}" hideLabel="true" values="${anagraficaObject.anagrafica4view[tipologiaDaVisualizzare.shortName]}" />

</c:if>
</c:forEach></td>
</tr>
</table>

</div>
</div>
<p></p>
