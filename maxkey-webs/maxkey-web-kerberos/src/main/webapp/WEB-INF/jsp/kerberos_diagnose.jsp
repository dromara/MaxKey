<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>kerberos</title>
		<link type="text/css" rel="stylesheet" href="<c:url value='/css/defaulttable.css'/>"/>
  </head>
<body>
	<div id="content"  class="datagrid">
		<p>
				<table border="1">
					<tr>
						<th colspan="2"><h2>kerberos </h2></th>
					</tr>
					<tr>
						<td width="150">name</td>
						<td>value</td>
					</tr>
					<tr>
						<td>userDomain</td>
						<td>${kerberosUserDomain}</td>
					</tr>
					<tr>
						<td>RemoteUser</td>
						<td><%= request.getRemoteUser() %></td>
					</tr>
					<tr>
						<td>Full Principal</td>
						<td>${kerberosPrincipal}</td>
					</tr>
					<tr>
						<td>Short Principal</td>
						<td>${shortPrincipal}</td>
					</tr>
					<tr>
						<td>token Post Location</td>
						<td>${tokenPostLocation}</td>
					</tr>
					<tr>
						<td>kerberos Token</td>
						<td  style="WORD-WRAP: break-word;width:400px"  >${kerberosToken}</td>
					</tr>
					<tr>
						<td>Encrypted kerberos Token</td>
						<td  style="WORD-WRAP: break-word;width:400px"  >${encryptedKerberosToken}</td>
					</tr>
				</table>
		</p>
	</div>
</body>
</html>
