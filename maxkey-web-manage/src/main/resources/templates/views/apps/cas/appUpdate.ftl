<%@ page 	language="java"   import="java.util.*" 	pageEncoding="UTF-8"%>
<%@ taglib 	prefix="spring"   uri="http://www.springframework.org/tags" %>
<%@ taglib 	prefix="s" uri="http://www.connsec.com/tags" %>
<%@ taglib 	prefix="fmt"      uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib  prefix="c"       	uri="http://java.sun.com/jsp/jstl/core"  %>
<script type="text/javascript">
<!--
$(function(){	

	$("#algorithm").change(function(){
		$.post("<s:Base/>/apps/generate/secret/"+$(this).val(), {_method:"post",currTime:(new Date()).getTime()}, function(data) {
			$("#algorithmKey").val(data+"");
			$("#algorithmKey_text").html(data+"");
			$("#secret").val(data+"");
			$("#secret_text").html(data+"");
		});
	}); 
	
	$("#generateSecret").on("click",function(){
		$.post("<s:Base/>/apps/generate/secret/"+$("#algorithm").val(), {_method:"post",currTime:(new Date()).getTime()}, function(data) {
			$("#algorithmKey").val(data+"");
			$("#algorithmKey_text").html(data+"");
			$("#secret").val(data+"");
			$("#secret_text").html(data+"");
		}); 
	});
});
//-->
</script>
<form id="actionForm_app"  method="post" type="label" autoclose="true"  
			action="<s:Base/>/apps/cas/update"  
			forward="<s:Base/>/apps/list"
			enctype="multipart/form-data">		 
  	        <!-- content -->    
  	      	<!--table-->
  	      	<table   class="datatable" >
				<tbody>
				<tr>
					<td ><jsp:include page="../appUpdateCommon.jsp"/></td>
				</tr>
				<tr>
					<td>
				 			<table width="960"  class="datatable" >
								<tbody>
								
								<tr>
									<th colspan=4><s:Locale code="apps.cas.info"/></th>
								</tr>
								<tr>
									<th style="width:15%;"><s:Locale code="apps.cas.service"/>：</th>
									<td  colspan=3>
										<input type="text" id="service" name="service"  title="" value="${model.service}"/>
										<b class="orange">*</b><label for="service"></label>
									</td>
								</tr>
								<tr>
									<th style="width:15%;"><s:Locale code="apps.cas.validation"/>：</th>
									<td  colspan=3>
										<input type="text" id="redirectUri" name="validation"  title="" value="${model.validation}"/>
										<b class="orange">*</b><label for="validation"></label>
									</td>
								</tr>
								</tbody>
							  </table>
			  </td>
				</tr>
				</tbody>
				</table>
			  
  	      
    		<input class="button" id="submitBtn" type="submit" value="<s:Locale code="button.text.save" />"/>
			<input class="button" id="backBtn" type="button" value="<s:Locale code="button.text.cancel" />"/>	  
</form>