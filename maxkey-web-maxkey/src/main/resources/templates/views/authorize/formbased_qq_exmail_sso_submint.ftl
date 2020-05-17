<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <#include  "formbased_common.ftl">
	<script type="text/javascript">
	<!--
	var public_ts=(new Date()).getTime();
	
	var public_key ="CF87D7B4C864F4842F1D337491A48FFF54B73A17300E8E42FA365420393AC0346AE55D8AFAD975DFA175FAF0106CBA81AF1DDE4ACEC284DAC6ED9A0D8FEB1CC070733C58213EFFED46529C54CEA06D774E3CC7E073346AEBD6C66FC973F299EB74738E400B22B1E7CDC54E71AED059D228DFEB5B29C530FF341502AE56DDCFE9";
	
	function do_encrypt() {
	  var before = new Date();
	  var rsaKey = new RSAKey();
	  rsaKey.setPublic(public_key, "10001");
	  var res = rsaKey.encrypt(document.form1.pp.value + '\n' + public_ts + '\n');
	  var after = new Date();
	  if(res) {
		var encrypt_text=hex2b64(res);
		document.form1.p.value=encrypt_text;
		//alert(encrypt_text);
	  }
	  return true;
	}
	
	$(function(){
		document.loginform.ts.value=public_ts;
		$("#loginForm").submit();
	});
			
			
	//-->
	</script>
</head>

<body style="display:none"  onload="document.forms[0].submit()" >
	<form id="loginForm" name="form1" method="post" action="https://exmail.qq.com/cgi-bin/login" onSubmit="do_encrypt();" >
		<input type="hidden" name="sid" value=""/>
		<input type="hidden" name="firstlogin" value="false"/>
		<input type="hidden" name="domain" value="connsec.com"/>
		<input type="hidden" name="aliastype" value="other"/>
		<input type="hidden" name="errtemplate" value="dm_loginpage"/>
		<input type="hidden" name="starttime"/>
		<input type="hidden" name="redirecturl"/>
		<input type="hidden" name="f" value="biz"/>
		<input type="hidden" name="uin" value="test"/>
		<input type="hidden" name="p"/>
		<input type="hidden" name="delegate_url" value="" />
		<input type="hidden" name="ts" value="1111111111111" />
		<input type="hidden" name="from" value="" />
		<input type="hidden" name="ppp" value="" />
		<input type="hidden" name="chg" value="0" />
		<input type="hidden" name="loginentry" value="3" />
		<input type="hidden" name="s" value="" />
		<input type="hidden" name="dmtype" value="bizmail" />
		<input type="hidden" name="fun" value="" />
		
		<input type="text" id="inputuin" name="inputuin" value="test@connsec.com" tabindex="1" />
		<input type="password" id="pp" name="pp" value="1qaz@WSX" tabindex="2"   />
		
		<input type="text" id="vc" name="verifycode" value="" tabindex="3"  maxlength=4  />
		<input type="checkbox" id="ss" name="ss" value="1" tabindex="7" />
		
		<input id="formbasedsubmitbutton" type="submit" value="submit"/>

	</form>
</body>
</html>
