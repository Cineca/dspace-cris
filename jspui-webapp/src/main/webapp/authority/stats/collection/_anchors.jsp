<c:set var="oldsubscription"><c:if test="${dailysubscribed}">&freq=1</c:if><c:if test="${weeklysubscribed}">&freq=7</c:if><c:if test="${monthlysubscribed}">&freq=30</c:if></c:set>
<table class="statanchors">
<tr>
<th>1)<span class="titlestats"><fmt:message key="view.stats/collection.selectedObject.page.title" /></span></th>
<th>2)<span class="titlestats"><fmt:message key="view.stats/collection.top.item.page.title" /></span></th>
<th>3)<span class="titlestats"><fmt:message key="view.stats/collection.top.bitstream.page.title" /></span></th>
</tr>
<tr>
	<td>
	<a href="#selectedObject.geo.continent"><fmt:message key="view.stats/collection.data.selectedObject.geo.continent.graph" /></a><br/>
	<a href="#selectedObject.geo.countryCode"><fmt:message key="view.stats/collection.data.selectedObject.geo.countryCode.graph" /></a><br/>
	<a href="#selectedObject.geo.city"><fmt:message key="view.stats/collection.data.selectedObject.geo.city.graph" /></a><br/>
	<a href="#selectedObject.time.time"><fmt:message key="view.stats/collection.data.selectedObject.time.allMonths.title" /></a><br/>
	<a href="#selectedObject.geo.location"><fmt:message key="view.stats/collection.data.selectedObject.geo.location.title" /></a><br/>
	</td>
	<td>
	<a href="#top.item.continent"><fmt:message key="view.stats/collection.data.top.item.continent.graph" /></a><br/>
	<a href="#top.item.countryCode"><fmt:message key="view.stats/collection.data.top.item.countryCode.graph" /></a><br/>
	<a href="#top.item.city"><fmt:message key="view.stats/collection.data.top.item.city.graph" /></a><br/>
	<a href="#top.item.time"><fmt:message key="view.stats/collection.data.top.item.allMonths.title" /></a><br/>
	<a href="#top.item.location"><fmt:message key="view.stats/collection.data.top.item.location.title" /></a><br/>
	</td>
	<td>
	<a href="#top.bitstream.continent"><fmt:message key="view.stats/collection.data.top.bitstream.continent.graph" /></a><br/>
	<a href="#top.bitstream.countryCode"><fmt:message key="view.stats/collection.data.top.bitstream.countryCode.graph" /></a><br/>
	<a href="#top.bitstream.city"><fmt:message key="view.stats/collection.data.top.bitstream.city.graph" /></a><br/>
	<a href="#top.bitstream.time"><fmt:message key="view.stats/collection.data.top.bitstream.allMonths.title" /></a><br/>
	<a href="#top.bitstream.location"><fmt:message key="view.stats/collection.data.top.bitstream.location.title" /></a><br/>
	</td>
</tr>
</table>
<div class="subscribestatoption">
<table border="0">
	<tr>
		<td><fmt:message key="view.stats.subscribe.label.daily" />:</td>
		<td>
			<c:choose>
			<c:when test="${!dailysubscribed}">						
			<a href="<%=request.getContextPath()%>/rp/tools/stats/subscription/subscribe?handle=${data.object.handle}&freq=1${oldsubscription}" title="Subscribe statistics email update">
			<img border="0" alt="Email alert" src="<%=request.getContextPath()%>/image/start-bell.png" 
				 /></a>
			</c:when>
			<c:otherwise>
			<a href="<%=request.getContextPath()%>/rp/tools/stats/subscription/subscribe?handle=${data.object.handle}${fn:replace(oldsubscription,"&freq=1","")}" title="Unubscribe the statistics update">
			<img border="0" alt="Email alert" src="<%=request.getContextPath()%>/image/stop-bell.png" 
				 /></a>
			</c:otherwise>
			</c:choose>	
			<a href="<%=request.getContextPath()%>/rp/stats/rss/daily?handle=${data.object.handle}" title="Subscribe RSS statistics update">
			<img border="0" alt="RSS" src="<%=request.getContextPath()%>/image/feed.png" />
			</a>
		</td>
	</tr>
	<tr>
		<td><fmt:message key="view.stats.subscribe.label.weekly" />:</td>
		<td>
			<c:choose>
			<c:when test="${!weeklysubscribed}">						
			<a href="<%=request.getContextPath()%>/rp/tools/stats/subscription/subscribe?handle=${data.object.handle}&freq=7${oldsubscription}" title="Subscribe statistics email update">
			<img border="0" alt="Email alert" src="<%=request.getContextPath()%>/image/start-bell.png" 
				 /></a>
			</c:when>
			<c:otherwise>
			<a href="<%=request.getContextPath()%>/rp/tools/stats/subscription/subscribe?handle=${data.object.handle}${fn:replace(oldsubscription,"&freq=7","")}" title="Unubscribe the statistics update">
			<img border="0" alt="Email alert" src="<%=request.getContextPath()%>/image/stop-bell.png" 
				 /></a>
			</c:otherwise>
			</c:choose>	
			<a href="<%=request.getContextPath()%>/rp/stats/rss/weekly?handle=${data.object.handle}" title="Subscribe RSS statistics update">
			<img border="0" alt="RSS" src="<%=request.getContextPath()%>/image/feed.png" />
			</a>
		
		</td>
	</tr>
	<tr>
		<td><fmt:message key="view.stats.subscribe.label.monthly" />:</td>
		<td>
			<c:choose>
			<c:when test="${!monthlysubscribed}">						
			<a href="<%=request.getContextPath()%>/rp/tools/stats/subscription/subscribe?handle=${data.object.handle}&freq=30${oldsubscription}" title="Subscribe statistics email update">
			<img border="0" alt="Email alert" src="<%=request.getContextPath()%>/image/start-bell.png" 
				 /></a>
			</c:when>
			<c:otherwise>
			<a href="<%=request.getContextPath()%>/rp/tools/stats/subscription/subscribe?handle=${data.object.handle}${fn:replace(oldsubscription,"&freq=30","")}" title="Unubscribe the statistics update">
			<img border="0" alt="Email alert" src="<%=request.getContextPath()%>/image/stop-bell.png" 
				 /></a>
			</c:otherwise>
			</c:choose>	
			<a href="<%=request.getContextPath()%>/rp/stats/rss/monthly?handle=${data.object.handle}" title="Subscribe RSS statistics update">
			<img border="0" alt="RSS" src="<%=request.getContextPath()%>/image/feed.png" />
			</a>
		
		</td>
	</tr>
</table>
</div>