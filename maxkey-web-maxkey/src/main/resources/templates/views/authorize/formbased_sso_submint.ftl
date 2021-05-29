<!DOCTYPE html>
<html >
<head>
  	<#include  "authorize_common.ftl">
  	
  	<script type="text/javascript">
			$(function(){
				<#if true	== isExtendAttr>
					var attrIndex = 0;
	
					function addExtendAttr(attribute,attributeValue){
						var html =  '<tr  id="extendTr_' + attrIndex + '"><td>'+attribute+'：</td><td>';   
							html += '<input type="text" id="attribute_' + attrIndex + '" name="'+attribute+'" class="" title="" value="'+attributeValue+'"/>';   
				       	 	html += '</td></tr>'; 
				       	 	
						$('#extendAttrBody').append(html);
						attrIndex++;
					}
					
					var extendAttrJson = eval("("+'${extendAttr}'+")");
					for(extendAttrIndex in extendAttrJson){
						addExtendAttr(extendAttrJson[extendAttrIndex].attr,extendAttrJson[extendAttrIndex].value);
					};
				</#if>
				
				$("#formbasedsubmit").submit();

			});
		</script>
</head>

<body  style="display:none">
	<form id="formbasedsubmit" name="formbasedsubmit" action="${action}" method="post">
		<table>
			<tr>
				<td>username</td>
				<td><input type="text" id="identity_formbased_username" name="${usernameMapping}" value="${username}" /></td>
			</tr>
			<tr>
				<td>password</td>
				<td><input type="password" id="identity_formbased_password" name="${passwordMapping}" value="${password}" /></td>
			</tr>
			<tr>
				<td colspan="2">
					<table>
						<tbody id="extendAttrBody"></tbody>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2"><input id="formbasedsubmitbutton" type="button" value="submit"/></td>
			</tr>
		</table>
	</form>
</body>
</html>
