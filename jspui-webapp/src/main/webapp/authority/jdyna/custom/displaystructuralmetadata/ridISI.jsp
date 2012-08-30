<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

    <c:if
							test="${!empty researcher.ridISI.value && researcher.ridISI.visibility==1}">
							<span id='badgeCont100000' style='width: 26px'><script
								src='http://labs.researcherid.com/mashlets?el=badgeCont100000&mashlet=badge&showTitle=false&className=a&rid=${researcher.ridISI.value}&size=small'></script></span>
						</c:if> 