
<div class="container">	
  <#if 0 == pwdreseted>
  	password not eq the confirm password,<br>
  	<input type="button"  class="button"  value="后退"  onclick="javascript:history.go(-1);"> 
  </#if>
  <#if 1 == pwdreseted>
  	Reset Password successful,<a href="<s:Base/>/login">click here</a> login.
  </#if>
  <#if 2 == pwdreseted>
  	url expired.
  </#if>
</div>