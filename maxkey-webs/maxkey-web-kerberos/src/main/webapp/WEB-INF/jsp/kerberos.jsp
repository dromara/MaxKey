<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
  </head>
<body  onload="document.forms[0].submit()">
	<noscript>
	     <p>
	         <strong>Note:</strong> Since your browser does not support JavaScript,
	         you must press the Continue button once to proceed.
	     </p>
	 </noscript>
	<form action="${tokenPostLocation}"  method="post">
		<div id="content"  class="datagrid" style="display:none">
			<p>
					<table border="1">
						<tr>
							<th colspan="2"><h2>kerberos </h2></th>
						</tr>
						<tr>
							<td>name</td>
							<td>value</td>
						</tr>
						<tr>
							<td>userDomain</td>
							<td><input  type="text"  name ="kerberosUserDomain"  value="${kerberosUserDomain}"></td>
						</tr>
						<tr>
							<td>kerberos Token</td>
							<td><input  type="text"  name ="kerberosToken"  value="${encryptedKerberosToken}"></td>
						</tr>
					</table>
					
			</p>
		</div>
		<noscript>
		    <div>
		        <input type="submit" value="Continue"/>
		    </div>
		</noscript>
	</form>
</body>
</html>
