<%@ page   language="java"  import="java.util.*"   pageEncoding="UTF-8"%>
<%@ page   import="org.maxkey.domain.*"%> 
<%@ page   import="org.maxkey.web.*"%>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt"     uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib 	prefix="s" uri="http://www.connsec.com/tags" %>
<%@ taglib prefix="c"       	uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fn"     	 	uri="http://java.sun.com/jsp/jstl/functions" %>


<%if(WebContext.getUserInfo().getGridList()==0) {%>
<table  class="datatable">
	<c:forEach begin="1" end="${(fn:length(appList)+4)/5}" var="num">
		<tr>
			<c:forEach items="${appList}" var="app" begin="${(num-1)*5}" end="${5*num-1}">
			<td align="left" nowrap style="width:20%">
				<c:if test="${null!=app.id}">
	  				<table  class="none" style="width:100%; border-spacing: 0;border-collapse: collapse;">
	  				<tr><td style="text-align: center;border-spacing: 0;border-collapse: collapse;border: 0px;">
	  					<c:if test="${'Desktop'==app.protocol}">
	  						<a href="javascript:void(0);" title="${app.name}" 
	  						onclick="window.open('<s:Base/>/authz/${app.id}');">
	  							<img src="<s:Base />/image/${app.id}" title="${app.name}" width="65px" height="65px"  style="border:0;"/>
	  						</a>
	  					</c:if>
	  					<c:if test="${'Desktop'!=app.protocol}">
	  						<a href="<s:Base />/authz/${app.id}" target="_blank" title="${app.name}" >
	  							<img src="<s:Base />/image/${app.id}" title="${app.name}" width="65px" height="65px"  style="border:0;"/>
	  						</a>
	  					</c:if>
	  				</td></tr>
	  				<tr><td style="text-align: center;border-spacing: 0;border-collapse: collapse;border: 0px;">${app.name}</td></tr>
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
		</tr>
	<c:forEach items="${appList}" var="app">
	<c:if test="${null!=app.id}">
		<tr>
			<td>
				<c:if test="${'Desktop'==app.protocol}">
					<a href="javascript:void(0);"  title="${app.name}" 
					onclick="window.open('<s:Base/>/authz/${app.id}');">
						<img src="<s:Base />/image/${app.id}" title="${app.name}" width="65px" height="65px"  style="border:0;"/>
					</a>
				</c:if>
				<c:if test="${'Desktop'!=app.protocol}">
					<a href="<s:Base />/authz/${app.id}" target="_blank" title="${app.name}" >
						<img src="<s:Base />/image/${app.id}" title="${app.name}" width="65px" height="65px"  style="border:0;"/>
					</a>
				</c:if>
			</td>
			<td>${app.name}</td>
			<td>${app.protocol}</td>
			<td>${app.category}</td>
			
			
			<td>
				<c:if test="${'Desktop'==app.protocol}">
					<a href="javascript:void(0);" title="${app.name}" 
					onclick="window.open('<s:Base/>/authz/${app.id}');">
						<s:Locale code="button.text.visit"/>
					</a>
				</c:if>
				<c:if test="${'Desktop'!=app.protocol}">
					<a href="<s:Base />/authz/${app.id}" target="_blank" title="${app.name}" >
						<s:Locale code="button.text.visit"/>
					</a>
				</c:if>
			</td>
		</tr>
	</c:if>
	</c:forEach>
</table>
<%} %>