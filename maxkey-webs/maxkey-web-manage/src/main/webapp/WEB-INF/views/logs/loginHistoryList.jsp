<%@ page 	contentType="text/html; charset=UTF-8"%>
<%@ page 	import="org.maxkey.domain.*"%> 
<%@ page 	import="java.util.Map,java.util.LinkedHashMap"%>
<%@ page 	import="org.maxkey.web.*"%>
<%@ taglib  prefix="s"   uri="http://www.connsec.com/tags" %>
<%@ taglib  prefix="c"			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix="spring"  	uri="http://www.springframework.org/tags" %>

	<div id="tool_box">
		<table  class="datatable">
			<tr>
				<td  width="120px">
			 		 <s:Locale code="userinfo.username"/>:
				</td>
				<td  width="375px">
					<form id="basic_search_form">
				 			<input name="username" type="text" style ="width:150px">
				 			<input class="button primary"  id="searchBtn" type="button" size="50" value="<s:Locale code="button.text.search"/>">
				 			<input class="button"  id="advancedSearchExpandBtn" type="button" size="50"  value="<s:Locale code="button.text.expandsearch"/>" expandValue="<s:Locale code="button.text.expandsearch"/>"  collapseValue="<s:Locale code="button.text.collapsesearch"/>">
					 	</form>
				</td>
				<td colspan="2"> 
					 
				</td>
			</tr>
		</table>
 		
		
 	</div>
 	
 	<div id="advanced_search">
 		<form id="advanced_search_form">
 			<table   class="datatable">
	 			<tr>
	 				<td width="120px"><s:Locale code="userinfo.displayName"/></td>
		 			<td width="360px">
		 				<input name="displayName" type="text" >
		 			</td>
		 			<td width="120px"><s:Locale code="loginhistory.sourceip"/></td>
		 			<td width="360px">
						<input style="width:70%"  type="text" id="sourceIp" name="sourceIp"  title="" value=""/>
			 		</td>
			 </tr>
			 <tr>
	 				<td width="120px"><s:Locale code="common.text.startdate"/></td>
		 			<td width="360px">
		 				<input id="datepickerstart" name="startDate" type="text" >
		 			</td>
		 			<td width="120px"><s:Locale code="common.text.enddate"/></td>
		 			<td width="360px">
						<input id="datepickerend"  style="width:70%"  type="text" id="endDate" name="endDate"  title="" value=""/>
			 		</td>
			 </tr>
			</table>
 		</form>
 	</div>


<div class="mainwrap" id="main">
	<s:Grid id="list" url="/logs/loginHistory/grid" multiselect="false">	
		<s:Column width="0" field="id" title="id" hidden="true"/>
		<s:Column width="20" field="sessionId" title="sessionId"  hidden="true"/>
		<s:Column width="15" field="username" title="userinfo.username"/>
		<s:Column width="15" field="displayName" title="userinfo.displayName"/>
		<s:Column width="10" field="provider" title="loginhistory.provider"/>
		<s:Column width="10" field="message" title="loginhistory.message"/>				
		<s:Column width="10" field="loginType" title="loginhistory.logintype" />
		<s:Column width="10" field="sourceIp" title="loginhistory.sourceip" />
		<s:Column width="15" field="browser" title="loginhistory.browser"/>
		<s:Column width="15" field="loginTime" title="loginhistory.logintime" />
		<s:Column width="15" field="logoutTime" title="loginhistory.logouttime" />
		
		<s:Column width="50" field="platform" title="loginhistory.platform"  hidden="true"/>
		<s:Column width="100" field="application" title="loginhistory.application"  hidden="true"/>
		<s:Column width="100" field="loginUrl" title="loginhistory.loginurl"  hidden="true"/>
		<s:Column width="100" field="code" title="code" hidden="true"/>
		<s:Column width="100" field="rpUserInfo" title="rpUserInfo"  hidden="true"/>
	</s:Grid>
</div>