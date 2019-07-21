<%@ page 	language="java"   import="java.util.*" 	pageEncoding="UTF-8"%>
<%@ taglib 	prefix="spring"   uri="http://www.springframework.org/tags" %>
<%@ taglib 	prefix="s" uri="http://www.connsec.com/tags" %>
<%@ taglib 	prefix="fmt"      uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page 	import="org.maxkey.constants.*"%>
<script type="text/javascript">
<!--
$(function(){	
	$("#algorithm").change(function(){
		$.post("<s:Base/>/apps/generate/secret/"+$(this).val(), {_method:"post",currTime:(new Date()).getTime()}, function(data) {
			$("#secret").val(data+"");
			$("#secret_text").html(data+"");
			
		});
	}); 

});
//-->
</script>
<form id="actionForm_app"  method="post" type="label" autoclose="true"  
			action="<s:Base/>/apps/cas/add"  
			forward="<s:Base/>/apps/list"
			enctype="multipart/form-data">		 
  	        <!-- content -->    
  	      	<!--table-->
  	      	<table width="960"  class="datatable" >
				<tbody>
				<tr>
					<td ><jsp:include page="../appAddCommon.jsp"/></td>
				</tr>
				<tr>
					<td>
						<table width="960"  class="datatable" >
						<tbody>
							<tr>
								<td colspan=4><s:Locale code="apps.cas.info"/></td>
							</tr>
							<tr>
								<th style="width:15%;"><s:Locale code="apps.cas.service"/>：</th>
								<td  colspan=3>
									<input type="text" id="service" name="service"  title="" value=""/>
									<b class="orange">*</b><label for="service"></label>
								</td>
							</tr>
							<tr>
								<th style="width:15%;"><s:Locale code="apps.cas.validation"/>：</th>
								<td  colspan=3>
									<input type="text" id="validation" name="validation"  title="" value=""/>
									<b class="orange">*</b><label for="validation"></label>
								</td>
							</tr>
							</tbody>
						  </table>
					</td>
				</tr>
				</tbody>
				</table>
			   

			<input class="button"  id="status" type="hidden" name="status"  value="1"/>
    		<input class="button"  id="submitBtn" type="submit" value="<s:Locale code="button.text.save" />"/>
			<input class="button"  id="backBtn" type="button" value="<s:Locale code="button.text.cancel" />"/>	  
</form>