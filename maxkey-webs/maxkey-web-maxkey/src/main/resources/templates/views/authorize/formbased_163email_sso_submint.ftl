<html>
<head>
  	<#include  "authorize_common.ftl">
  	
  	<script type="text/javascript">
			$(function(){
				<#if  isExtendAttr >
					var attrIndex = 0;
	
					function addExtendAttr(attribute,attributeValue){
						var html =  '<tr  id="extendTr_' + attrIndex + '"><td>'+attribute+'：</td><td>';   
							html += '<input type="text" id="attribute_' + attrIndex + '" name="'+attribute+'" class="" title="" value="'+attributeValue+'"/>';   
				       	 	html += '</td></tr>'; 
				       	 	
						$('#extendAttrBody').append(html);
						attrIndex++;
					}
					
					var extendAttrJson = eval("("+'${extendAttr!""}'+")");
					$.each(extendAttrJson,function(key,val){
						addExtendAttr(key,val);
					});
				</#if>
				
				$("#loginForm").submit();

			});
		</script>
</head>

<body style="display:none">
	<form class="bd" name="frmLogin" method="post" id="loginForm"  target="_top"
		action="https://ssl.mail.163.com/entry/coremail/fcg/ntesdoor2?funcid=loginone&amp;language=-1&amp;passtype=1&amp;iframe=1&amp;product=mail163&amp;from=web&amp;df=email163&amp;race=-2_146_-2_hz&amp;module=&amp;uid=${email}&amp;style=-1&amp;net=t&amp;skinid=null">
		<input type="hidden" id="idInput"/>
		<input type="hidden" id="account" name="username" value="${email}"/><!-- ssl加密传输用户名 -->
		<input type="hidden" name="url2" id="url2"/>
		<input type="hidden" name="savalogin" id="savelogin" value="0"/><!-- 兼容base 无自动登录 -->
		
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
