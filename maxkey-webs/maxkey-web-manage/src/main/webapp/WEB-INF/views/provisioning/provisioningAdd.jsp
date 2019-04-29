<%@ page 	language="java"   import="java.util.*" 	pageEncoding="UTF-8"%>
<%@ page 	language="java"   import="org.maxkey.constants.*"%>
<%@ taglib 	prefix="spring"   uri="http://www.springframework.org/tags" %>
<%@ taglib 	prefix="s" uri="http://www.connsec.com/tags" %>
<%@ taglib  prefix="c"       	uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib 	prefix="fmt"      uri="http://java.sun.com/jsp/jstl/fmt"%>


<script type="text/javascript">
<!--
$(function(){	

	var attrIndex = 0;
	
	function addExtendAttr(attribute,attributeValue){ 
		var html = '<tr  id="extendTr_' + attrIndex + '"><th><s:Locale code="provisioning.properties" />：'; 
		html += '<input type="button" name="delExtendTr" class="delExtendTr button" attrTrId="extendTr_'+attrIndex+'" value="<s:Locale code="button.text.delete" />"/>';
		html += '</th><td>';   
		
		html += '<input type="text" id="attribute_' + attrIndex + '" name="propertieName" class="int" title="" value="'+attribute+'"/>';   
        html += '</td><th><s:Locale code="provisioning.value" />：</th>	<td><span class="intspan">';
        html += '<input type="text" id="attributeValue_' + attrIndex + '" name="propertieValue" class="int" title="" value="'+attributeValue+'"/>';
        html += '</span>';
        
        html += '</td></tr>'; 
		$('#extendAttrBody').append(html);
		attrIndex++;
	}
	
	$("#addExtendAttr").on('click',function(){
		addExtendAttr("","");
	});	
	$("#extendAttrBody").delegate(".delExtendTr",'click',function(){
		$("#"+$(this).attr("attrTrId")).remove();
	});
	
	});
//-->
</script>

<form id="actionForm"  method="post" type="label" autoclose="true" 
 action="<s:Base/>/provisioning/add"
 forward="<s:Base/>/provisioning/list"  >
	 <div class="golBlock tableBlock" >
	    <div class="main">
	    <div class="mainin">			 
  	        <!-- content -->     
  	      	<!--table-->
			  <table width="980px" class="datatable" >
				<tbody>
				<tr style="display:none">
					<th><s:Locale code="provisioning.id" />：</th>
					<td nowrap colspan="3">
						<input id="id" type="hidden" name="id"  value=""/>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="provisioning.name" />：</th>
					<td nowrap colspan="3" width="75%">
						<span class="intspan"><input type="text" id="name" name="name" class="int required" title="" value=""/></span>
						<b class="orange">*</b><label for="name"></label>
					</td>
				</tr>
				<tr >
					<th><s:Locale code="provisioning.type" />：</th>
					<td nowrap >
						<select name="type"  class="gender">
							<option value="0"  selected><s:Locale code="provisioning.type.jit" /></option>
							<option value="1" ><s:Locale code="provisioning.type.schedule" /></option>
							<option value="2" ><s:Locale code="provisioning.type.manual" /></option>
						</select>
					</td>
					<th><s:Locale code="common.text.status" />：</th>
					<td nowrap >
						<select name="status" >
							<option value="<%=STATUS.ACTIVE%>" ><s:Locale code="common.text.status.11" /></option>
							<option value="<%=STATUS.INACTIVE%>"  selected><s:Locale code="common.text.status.12" /></option>
						</select>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="provisioning.userConnector" />：</th>
					<td nowrap colspan="3">
						<span class="intspan"><input type="text" id="userConnector" name="userConnector" class="int" title="" value=""/></span>
						
					</td>
				</tr>
				
				<tr>
					<th><s:Locale code="provisioning.groupConnector" />：</th>
					<td nowrap colspan="3">
						<span class="intspan"><input type="text" id="groupConnector" name="groupConnector" class="int" title="" value=""/></span>
						
					</td>
				</tr>
				<tr>
					<th><s:Locale code="provisioning.orgConnector" />：</th>
					<td nowrap colspan="3">
						<span class="intspan"><input type="text" id="orgConnector" name="orgConnector" class="int" title="" value=""/></span>
						
					</td>
				</tr>
				<tr>
					<th><s:Locale code="provisioning.passwordConnector" />：</th>
					<td nowrap colspan="3">
						<span class="intspan"><input type="text" id="passwordConnector" name="passwordConnector" class="int" title="" value=""/></span>
						
					</td>
				</tr>
				<tr>
					<th><s:Locale code="provisioning.url" />：</th>
					<td nowrap colspan="3">
						<span class="intspan"><input type="text" id="url" name="url" class="int" title="" value=""/></span>
						
					</td>
				</tr>
				
				<tr>
					<th><s:Locale code="provisioning.principal" />：</th>
					<td nowrap>
						<span class="intspan"><input type="text" id="principal" name="principal" class="int" title="" value=""/></span>
						
					</td>
					<th><s:Locale code="provisioning.credentials" />：</th>
					<td nowrap>
						<span class="intspan"><input type="text" id="credentials" name="credentials" class="int" title="" value=""/></span>
						
					</td>
				</tr>
				<tr>
					 <th><s:Locale code="common.text.sortorder"/>：</th>
			         <td nowrap colspan="3">
			         	<input  type="text" id="sortOrder" name="sortOrder"  title="" value="0"/>
			            <b class="orange">*</b><label for="sortOrder"></label>
			         </td>
		         </tr>
				
				</tbody>
				<tbody id="extendAttrBody">
				</tbody>
				<tbody>
					<tr>
						<th><s:Locale code="common.text.description" />：</th>
						<td nowrap colspan="3">
							<span class="intspan"><input type="text" id="description" name="description" class="" title="" value=""/></span>
							
						</td>
					</tr>
					<tr>
						<td colspan="4"  class="center">
							<div class="btmin btmin_btn">
								<input id="_method" type="hidden" name="_method"  value="post"/>
								
								<input class="button"   id="addExtendAttr" 	type="button"  	value='<s:Locale code="button.text.add.property" />' />
					    		<input class="button"   id="submitBtn" 		type="button" 	value="<s:Locale code="button.text.save" />">
				  				<input class="button"   id="backBtn"   		type="button"	value="<s:Locale code="button.text.cancel" />">	   
							</div>
						</td>
					</tr>
				</tbody>
			  </table>
  	        <div class="clear"></div>
		</div>
		</div>
	 </div> 
</form>
