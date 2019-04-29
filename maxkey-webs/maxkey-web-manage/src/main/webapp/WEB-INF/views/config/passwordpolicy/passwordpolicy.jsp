<%@ page 	language="java"   import="java.util.*" 	pageEncoding="UTF-8"%>
<%@ taglib 	prefix="spring"   uri="http://www.springframework.org/tags" %>
<%@ taglib 	prefix="s" uri="http://www.connsec.com/tags" %>
<%@ taglib 	prefix="fmt"      uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib  prefix="c"       	uri="http://java.sun.com/jsp/jstl/core"  %>

		<script type="text/javascript">
			function beforeAction() {
				$("label[for='maxLength']").html("");
				$("label[for='specialChar']").html("");
				var minLength = $("#minLength").val();
				var maxLength = $("#maxLength").val();
				var lowerCase = $("#lowerCase").val();
				var upperCase = $("#upperCase").val();
				var digits = $("#digits").val();
				var specialChar = $("#specialChar").val();
				if(parseInt(minLength) > parseInt(maxLength)) {
					$("label[for='maxLength']").html("");
					return false;
				}
				if(parseInt(lowerCase)+parseInt(upperCase)+parseInt(digits)+parseInt(specialChar) > parseInt(maxLength)) {
					$("label[for='specialChar']").html("");
					return false;
				}
				if(parseInt(lowerCase)+parseInt(upperCase)+parseInt(digits)+parseInt(specialChar) < parseInt(minLength)) {
					$("label[for='specialChar']").html("");
					return false;
				}
				return true;
			}
		</script>
  </head>
  

			<form method="post" type="label" validate="true" action="<s:Base/>/config/passwordpolicy/update" id="actionForm" >
				  <table width="980px"  class="datatable" >
					<tbody>
					<tr>
						<th  width="170px" nowrap><s:Locale code="passwordpolicy.minlength" />：</th>
						<td  width="310px" nowrap>
						   
						   		<input id="id" name="id" type="hidden" value="${model.id}"/>
						   		<input type="text" id="minLength" name="minLength" class="int {required: true,digits:true,min:3}" title="" value="${model.minLength}" maxlength="2"/>
						  
							<b class="orange">*</b><label for="minLength"></label>
						</td>
						<th  width="170px"  nowrap><s:Locale code="passwordpolicy.maxlength" />：</th>
						<td  width="310px" nowrap><input type="text" id="maxLength" name="maxLength" class="int {required: true,digits:true,range:[3,20]}" title="" value="${model.maxLength}" maxlength="2"/>
							<b class="orange">*</b><label for="maxLength"></label>
						</td>
					</tr>
					<tr>
						<th><s:Locale code="passwordpolicy.lowercase" />：</th>
						<td nowrap>	
							<span class="intspan"><input type="text" id="lowerCase" name="lowerCase" class="int {required: true,digits:true}" title="" value="${model.lowerCase}" maxlength="2"/></span>
							<b class="orange">*</b><label for="lowerCase"></label>
						</td>
						<th><s:Locale code="passwordpolicy.uppercase" />：</th>
						<td nowrap>	
							<span class="intspan"><input type="text" id="upperCase" name="upperCase" class="int {required: true,digits:true}" title="" value="${model.upperCase}" maxlength="2"/></span>
							<b class="orange">*</b><label for="upperCase"></label>
						</td>
					</tr>
					<tr>
						<th><s:Locale code="passwordpolicy.digits" />：</th>
						<td nowrap>	
							<span class="intspan"><input type="text" id="digits" name="digits" class="int {required: true,digits:true}" title="" value="${model.digits}" maxlength="2"/></span>
							<b class="orange">*</b><label for="digits"></label>
						</td>
						<th><s:Locale code="passwordpolicy.specialchar" />：</th>
						<td nowrap>	
							<span class="intspan"><input type="text" id="specialChar" name="specialChar" class="int {required: true,digits:true}" title="" value="${model.specialChar}" maxlength="2"/></span>
							<b class="orange">*</b><label for="specialChar"></label>
						</td>
					</tr>
					<tr>
						<th><s:Locale code="passwordpolicy.attempts" />：</th>
						<td nowrap>	
							<span class="intspan"><input type="text" id="attempts" name="attempts" class="int {required: true,digits:true}" title="" value="${model.attempts}" maxlength="2"/></span>
							<b class="orange">*</b><label for="attempts"></label>
						</td>
						<th><s:Locale code="passwordpolicy.duration" />(Unit:Hour)：</th>
						<td nowrap>	
							<span class="intspan"><input type="text" id="duration" name="duration" class="int {required: true,digits:true}" title="" value="${model.duration}"/></span>
							<b class="orange">*</b><label for="duration"></label>
						</td>
					</tr>
					<tr>
						
						<th><s:Locale code="passwordpolicy.expiration" />(Unit:Day)：</th>
						<td nowrap>	
							<span class="intspan"><input type="text" id="expiration" name="expiration" class="int {required: true,digits:true}" title="" value="${model.expiration}" maxlength="2"/></span>
							<b class="orange">*</b><label for="expiration"></label>
						</td>
						<th><s:Locale code="passwordpolicy.username" />：</th>
						<td nowrap>	
								<select  id="username" name="username"  class="select_t">
									<option  <c:if test="${1==model.username}">selected</c:if>  value="1"><s:Locale code="common.text.status.3"/></option>
									<option  <c:if test="${0==model.username}">selected</c:if>  value="0"><s:Locale code="common.text.status.4"/></option>
								</select>
								<label for="username"></label>
							</td>
					</tr>
					<tr>
						<th><s:Locale code="passwordpolicy.simplepasswords" />：</th>
						<td nowrap colspan="3">	
							<span class="intspan">
								<textarea id="simplePasswords" name="simplePasswords" rows="6" cols="80">${model.simplePasswords}</textarea>
							</span>
							<b class="orange">*</b><label for="simplePasswords"></label>
						</td>
						
					</tr>
				
					</tbody>
				  </table>
			<div class="btm btm_btn">
				<input class="button"  type="button"    id="submitBtn" value="<s:Locale code="button.text.save" />"/>
			</div>
		</form>
