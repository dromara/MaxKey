<%@ page 	contentType="text/html; charset=UTF-8"%>
<%@ page 	import="org.maxkey.domain.*"%> 
<%@ page 	import="java.util.Map,java.util.LinkedHashMap"%>
<%@ page 	import="org.maxkey.web.*"%>
<%@ taglib  prefix="s"   uri="http://www.connsec.com/tags" %>
<%@ taglib  prefix="c"			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix="spring"  	uri="http://www.springframework.org/tags" %>

<script type="text/javascript" src="<s:Base/>/resources/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<s:Base/>/jquery/jsonformatter.js"></script>
<script type="text/javascript">


</script>
	<div id="tool_box">
		<table  class="datatable">
			<tr>
				<td  width="120px">
			 		 <s:Locale code="apps.name"/>:
				</td>
				<td  width="375px">
					<form id="basic_search_form">
				 			<input name="appName" type="text" style ="width:150px">
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
	 				<td width="120px"><s:Locale code="common.text.startdate"/></td>
		 			<td width="360px">
		 				<input class="datetimepicker"  name="startDate" type="text" >
		 			</td>
		 			<td width="120px"><s:Locale code="common.text.enddate"/></td>
		 			<td width="360px">
						<input style="width:70%" class="datetimepicker"   type="text" id="endDate" name="endDate"  title="" value=""/>
			 		</td>
			 </tr>
			</table>
 		</form>
 	</div>
 	
<div class="mainwrap" id="main">
	<s:Grid id="list" url="/logs/loginAppsHistory/grid" multiselect="false">	
		<s:Column width="0" field="id" title="id" hidden="true"/>
		<s:Column width="20" field="sessionId" title="loginhistory.sessionId"/>
		<s:Column width="10" field="uid" title="loginhistory.uid"  hidden="true"/>
		<s:Column width="15" field="username" title="userinfo.username"/>
		<s:Column width="15" field="displayName" title="userinfo.displayName"/>
		<s:Column width="10" field="appId" title="apps.appId"  hidden="true"/>
		<s:Column width="10" field="appName" title="apps.name"/>		
		<s:Column width="15" field="loginTime" title="loginhistory.logintime" />
	</s:Grid>
</div>