<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set value="false" var="boxmediaspokenship0"></c:set> <c:set
			value="false" var="boxmediaspokenship1"></c:set> <c:set value="false"
			var="boxmediaspokenship2"></c:set> <c:set value="false"
			var="boxmediaspokenship3"></c:set> <c:set value="false"
			var="boxmediaspokenship4"></c:set> <c:forEach
			items="${researcher.spokenLanguagesEN}" var="media">
			<c:if test="${media.visibility==1}">
				<c:set value="true" var="boxmediaspokenship0"></c:set>
			</c:if>
		</c:forEach> <c:forEach items="${researcher.spokenLanguagesZH}" var="media">
			<c:if test="${media.visibility==1}">
				<c:set value="true" var="boxmediaspokenship1"></c:set>
			</c:if>
		</c:forEach> <c:forEach items="${researcher.writtenLanguagesEN}" var="media">
			<c:if test="${media.visibility==1}">
				<c:set value="true" var="boxmediaspokenship2"></c:set>
			</c:if>
		</c:forEach> <c:forEach items="${researcher.writtenLanguagesZH}" var="media">
			<c:if test="${media.visibility==1}">
				<c:set value="true" var="boxmediaspokenship3"></c:set>
			</c:if>
		</c:forEach> 
		
		<c:if
			test="${(!empty researcher.spokenLanguagesEN || !empty researcher.spokenLanguagesZH || !empty researcher.writtenLanguagesEN || !empty researcher.writtenLanguagesZH || !empty researcher.media) && (boxmediaspokenship0==true || boxmediaspokenship1==true || boxmediaspokenship2==true || boxmediaspokenship3==true)}">



				<c:if
					test="${(!empty researcher.spokenLanguagesEN || !empty researcher.spokenLanguagesZH) && (boxmediaspokenship0==true || boxmediaspokenship1==true)}">
					<tr>
						<td colspan="3" class="columnHead"><fmt:message
							key="jsp.layout.hku.detail.researcher.spokenlanguages" /></td>
						<td><c:forEach items="${researcher.spokenLanguagesEN}"
							var="spokenEN" varStatus="i">
							<c:if test="${spokenEN.visibility==1}">
								<c:out value="${spokenEN.value}" escapeXml="false" />
								<c:choose>
									<c:when
										test="${i.index == fn:length(researcher.spokenLanguagesEN)-1}">
							(
						</c:when>
									<c:otherwise>
										<span class="sep_bar">|</span>
									</c:otherwise>
								</c:choose>

							</c:if>
						</c:forEach> <c:forEach items="${researcher.spokenLanguagesZH}" var="spokenZH"
							varStatus="i">
							<c:if test="${spokenZH.visibility==1}">
								<c:out value="${spokenZH.value}" escapeXml="false" />
								<c:choose>
									<c:when
										test="${i.index == fn:length(researcher.spokenLanguagesZH)-1}">
							)
						</c:when>
									<c:otherwise>
										<span class="sep_bar">|</span>
									</c:otherwise>
								</c:choose>
							</c:if>
						</c:forEach></td>
					</tr>
				</c:if>
				<c:if
					test="${(!empty researcher.writtenLanguagesEN || !empty researcher.writtenLanguagesZH) && (boxmediaspokenship2==true || boxmediaspokenship3==true)}">
					<tr>
						<td colspan="3" class="columnHead"><fmt:message
							key="jsp.layout.hku.detail.researcher.writtenlanguages" /></td>
						<td><c:forEach items="${researcher.writtenLanguagesEN}"
							var="writtenEN" varStatus="i">
							<c:if test="${writtenEN.visibility==1}">
								<c:out value="${writtenEN.value}" escapeXml="false" />
								<c:choose>
									<c:when
										test="${i.index == fn:length(researcher.writtenLanguagesEN)-1}">
							(
						</c:when>
									<c:otherwise>
										<span class="sep_bar">|</span>
									</c:otherwise>
								</c:choose>
							</c:if>
						</c:forEach> <c:forEach items="${researcher.writtenLanguagesZH}"
							var="writtenZH" varStatus="i">
							<c:if test="${writtenZH.visibility==1}">
								<c:out value="${writtenZH.value}" escapeXml="false" />
								<c:choose>
									<c:when
										test="${i.index == fn:length(researcher.writtenLanguagesZH)-1}">
							)
						</c:when>
									<c:otherwise>
										<span class="sep_bar">|</span>
									</c:otherwise>
								</c:choose>
							</c:if>
						</c:forEach></td>
					</tr>
				</c:if>
				
		</c:if>