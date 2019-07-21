<%@ page 	language="java"   import="java.util.*" 	pageEncoding="UTF-8"%>
<%@ taglib 	prefix="spring"   uri="http://www.springframework.org/tags" %>
<%@ taglib 	prefix="s" uri="http://www.connsec.com/tags" %>
<%@ taglib 	prefix="fmt"      uri="http://java.sun.com/jsp/jstl/fmt"%>

<form id="actionForm_app"  method="post" type="label" autoclose="true"  
			action="<s:Base/>/apps/basic/add"  
			forward="<s:Base/>/apps/appsList"
			enctype="multipart/form-data">		 
  	        <!-- content -->    
  	      	<!--table-->
			    	      	<!--table-->
			<table class="datatable" >
				<tbody>
				<tr>
					<td ><jsp:include page="./appAddCommon.jsp"/></td>
				</tr>
				</tbody>
			  </table>
  	      
		    <input id="_method" type="hidden" name="_method"  value="post"/>
			<input  id="status" type="hidden" name="status"  value="1"/>
    		<input class="button" id="submitBtn" type="submit" value="<s:Locale code="button.text.save" />"/>
			<input class="button" id="backBtn" type="button" value="<s:Locale code="button.text.cancel" />"/>	  
</form>