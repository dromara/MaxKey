$(function(){
	$("#emailLogin").on("click",function(){
		$("#div_emailLogin").show();
		$("#div_companyLogin").hide();
		$("input[name=j_email]").focus(); 
		$("input[name=j_username]").val(""); 
		$("input[name=j_password]").val(""); 
		$("input[name=j_company]").val("");
	});
	
	$("#companyLogin").on("click",function(){
		$("#div_emailLogin").hide();
		$("#div_companyLogin").show();
		$("input[name=j_username]").focus(); 
		$("input[name=j_email]").val("");
		$("input[name=j_password]").val("");  
	});
	
	$("#emailLoginSubmit").on("click",function(){
		$.cookie("email", $("input[name=j_email]").val(), { expires: 7 });
		$.removeCookie("username");
		$.removeCookie("company");
		$("#emailLoginForm").submit();
	});
	
	$("#companyLoginSubmit").on("click",function(){
		$.cookie("username", $("input[name=j_username]").val(), { expires: 7 });
		$.cookie("company", $("input[name=j_company]").val(), { expires: 7 });
		$.removeCookie("email");
		$("#companyLoginForm").submit();
	});
	
	if($.cookie("email")!=undefined&&$.cookie("email")!=""){
		$("#div_emailLogin").show();
		$("#div_companyLogin").hide();
		$("input[name=j_email]").val($.cookie("email")); 
		$("input[name=j_password]").focus();
	}else if($.cookie("username")!=undefined&&$.cookie("username")!=""){
		$("#div_emailLogin").hide();
		$("#div_companyLogin").show();
		$("input[name=j_username]").val($.cookie("username")==undefined?"":$.cookie("username")); 
		$("input[name=j_company]").val($.cookie("company")==undefined?"":$.cookie("company")); 
		$("input[name=j_password]").focus();
	}else{
		$("input[name=j_email]").focus(); 
	}
});