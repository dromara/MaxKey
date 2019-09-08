<script type="text/javascript">	
$(function () {
	$(".configUser").on("click",function(){
		$.window({url:$(this).attr("url"),width:480,height:200});
	});
		
	$(".configProtected").on("click",function(){
		$.window({url:$(this).attr("url"),width:480,height:200});
	});
});
</script>
<%if(WebContext.getUserInfo().getGridList()==0) {%>
<table class="datatable">
	<c:forEach begin="1" end="${(length(appList)+4)/5}" var="num">
		<tr>
			<c:forEach items="${appList}" var="app" begin="${(num-1)*5}" end="${5*num-1}">
			<td align="left" nowrap  style="width:20%">
				<c:if test="${null!=app.id}">
	  				<table class="none" >
	  				<tr><td  style="text-align: center;border-spacing: 0;border-collapse: collapse;border: 0px;">
	  					<!-- <a href="<s:Base />/authz/${app.id}" target="_blank" title="${app.name}" ></a> -->
	  						<img src="<s:Base />/image/${app.id}" title="${app.name}" width="65px;" height="65px;"/>
	  					
	  				</td></tr>
	  				<tr><td style="text-align: center;border-spacing: 0;border-collapse: collapse;border: 0px;">${app.name}</td></tr>
	  				<tr><td  style="text-align: center;border-spacing: 0;border-collapse: collapse;border: 0px;">${app.protocol}<div>
	  				<input class="configUser button"  type="button" 
	  						value="<s:Locale code="access.security.applogin.credential" />" 
				 		    url="<s:Base/>/forward/appUserConfig/${app.protocol}/${app.credential}/${app.id}"
				 		    target="window">
  					<input class="configProtected button"  type="button" 
  							value="<s:Locale code="access.security.applogin.protection" />" 
			 		    	url="<s:Base/>/forward/appProtectedConfig/${app.protocol}/${app.credential}/${app.id}"
			 		    	target="window"></div></td></tr>
	  				</table>
	  			</c:if>
			</td>
			</c:forEach>
		</tr>
	</c:forEach>
</table>
<%}else{%>
<table  class="datatable">
	<tr>
			<td>
				<s:Locale code="apps.icon"/>
			</td>
			<td><s:Locale code="apps.name"/></td>
			<td><s:Locale code="apps.protocol"/></td>
			<td><s:Locale code="apps.category"/></td>
			<td>
				<s:Locale code="button.text.action"/>
			</td>
			<td>
				<s:Locale code="button.text.action"/>
			</td>
		</tr>
	<c:forEach items="${appList}" var="app">
	<c:if test="${null!=app.id}">
		<tr>
			<td>
				<a href="<s:Base />/authz/${app.id}" target="_blank" title="${app.name}" >
					<img src="<s:Base />/image/${app.id}" title="${app.name}" width="65px;" height="65px;"/>
				</a>
			</td>
			<td>${app.name}</td>
			<td>${app.protocol}</td>
			<td>${app.category}</td>
			
			<td>
				<input class="configUser button"  type="button" 
	  						value="<s:Locale code="access.security.applogin.credential" />" 
				 		    url="<s:Base/>/forward/appUserConfig/${app.protocol}/${app.credential}/${app.id}"
				 		    target="window">
			</td>
			<td>
				<input class="configProtected button"  type="button" 
  							value="<s:Locale code="access.security.applogin.protection" />" 
			 		    	url="<s:Base/>/forward/appProtectedConfig/${app.protocol}/${app.credential}/${app.id}"
			 		    	target="window">
			</td>
		</tr>
	</c:if>
	</c:forEach>
</table>
<%} %>