<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<tr>

						<td class="image_td" colspan="2" align="center">

						<div class="image"><c:choose>
							<c:when
								test="${!empty researcher.pict.value && researcher.pict.visibility==1}">
								<img id="picture" name="picture"
									alt="${researcher.fullName} picture"
									src="<%=request.getContextPath()%>/researcherimage/${authority}"
									title="${researcher.fullName} picture" />
							</c:when>
							<c:otherwise>
								<img id="picture" name="picture" alt="No image"
									src="<%=request.getContextPath()%>/image/authority/noimage.jpg"
									title="No picture for ${researcher.fullName}" />
							</c:otherwise>
						</c:choose></div>

						</td>

					</tr>