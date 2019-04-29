<%@ page   contentType="text/html; charset=UTF-8" language="java"  %>
<%@ page   import="org.maxkey.domain.*"%> 
<%@ page   import="org.maxkey.web.*"%>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s"  	uri="http://www.connsec.com/tags" %>

<div id="topBar"  > 
	<div class="container">
		<div style="float:left;margin-left:20px;margin-top: 5px;"><IMG SRC="<s:Base/>/images/logo.jpg" style="width:55px;heigth:55px"></div>
		<div style="margin-top:15px;margin-left:10px;float:left">
			<div style="letter-spacing:2px;font-size:28px;font-weight:bolder;"><s:Locale code="global.access.application"/></div>
			
		</div>
		<div style="margin-top:25px;margin-right:10px;float:right;">
			<table style="height: 31px;">
				<tr>
					<td>
						<s:Locale code="global.text.welcome"/>：<%=WebContext.getUserInfo()==null?"":WebContext.getUserInfo().getDisplayName()%>(<%=WebContext.getUserInfo()==null?"":WebContext.getUserInfo().getUsername()%>)&nbsp;&nbsp;
					</td>
					<%if(WebContext.getUserInfo().getGridList()==0) {%>
					<td>
						<img src='<s:Base/>/images/grid_sel.png'  style="width=:31px;height:31px;border:0;">
					</td>
					<td>
						<a href="<s:Base/>/appList?mnid=110101020000&gridList=1"><img src='<s:Base/>/images/list.png'  style="width=:31px;height:31px;border:0;"></a>
					</td>
					<%}else{%>
					<td>
						<a href="<s:Base/>/appList?mnid=110101020000&gridList=0" ><img src='<s:Base/>/images/grid.png'  style="width=:31px;height:31px;border:0;"></a>
					</td>
					<td>
						<img src='<s:Base/>/images/list_sel.png'  style="width=:31px;height:31px;border:0;">
					</td>
					<%} %>
					<authz:authorize ifAllGranted="SUPER_ADMIN,IDENTITY_MANAGER">
					<td id="manage" nowrap>
						<a target="_blank"  href="<s:Base/>/authz/manage">
							<div  style="float:right;" >&nbsp;&nbsp;<s:Locale code="global.text.manage"/>&nbsp;&nbsp;</div>
						</a>
					</td>
					</authz:authorize>
					<td class="ui-widget-header" >
						<a  href="<s:Base/>/logout">
							<div  style="float:right;" >&nbsp;&nbsp;<s:Locale code="global.text.logout"/>&nbsp;&nbsp;</div>
						</a>
					</td>
				</tr>
			</table>
			
			
		</div>
		
	</div>
</div>

