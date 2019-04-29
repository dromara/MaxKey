<%@ page   contentType="text/html; charset=UTF-8" import="java.util.Map,java.util.LinkedHashMap" %>
<%@ page   import="org.maxkey.domain.*"%> 
<%@ page   import="org.maxkey.web.*"%>
<%@ taglib prefix="s" 	uri="http://www.connsec.com/tags" %>
<%@ taglib prefix="spring" 		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"			uri="http://java.sun.com/jsp/jstl/core" %>


 <div id="datagrid">
	<s:Grid id="list" url="/userinfo/grid" multiselect="true">	
		<s:Column width="0" field="id" title="id" hidden="true"/>
		<s:Column width="100" field="username" title="userinfo.username"/>
		<s:Column width="100" field="employeeNumber" title="userinfo.employeeNumber"/>
		<s:Column width="100" field="displayName" title="userinfo.displayName"/>
		<s:Column width="100" field="department" title="userinfo.department"/>				
		<s:Column width="100" field="email" title="userinfo.email" />
		<s:Column width="0" field="createdBy" title="common.text.createdby" hidden="true"/>
		<s:Column width="0" field="createdDate" title="common.text.createddate" hidden="true"/>
		<s:Column width="0" field="modifiedBy" title="common.text.modifiedby" hidden="true"/>
		<s:Column width="0" field="modifiedDate" title="common.text.modifieddate" hidden="true"/>
	</s:Grid>
</div>