var currentSwitchTab="normalLogin";
function doLoginSubmit(){
    currentSwitchTab = $(".switch_tab_current").attr("id") ;
    $.cookie("mxk_login_username", $("#"+currentSwitchTab+"Form input[name=username]").val(), { expires: 7 });
    $("#"+currentSwitchTab+"SubmitButton").click();
    $.cookie("mxk_login_switch_tab", currentSwitchTab, { expires: 7 });
};
    
document.onkeydown=function(event){
    var e = event || window.event || arguments.callee.caller.arguments[0];
    if(e && e.keyCode==13){ 
        doLoginSubmit();
    };
};
    
var countDownTimer;var captchaCount=60;
function doCountDownTimer(){
    $("#mobile_j_otp_button").val("重新获取("+captchaCount+")秒");
    captchaCount--;
    if( captchaCount<=0 ){
        $("#mobile_j_otp_button").val("发送验证码");
        captchaCount=60;
        clearInterval(countDownTimer);
    }
}

$(function(){
    //setInterval("currentTime()", 1000);

    $(".doLoginSubmit").on("click",function(){
            doLoginSubmit();
    });
    var cookieLoginUsername = $.cookie("mxk_login_username");
    if(cookieLoginUsername != undefined && cookieLoginUsername != ""){
        var switch_tab=$.cookie("mxk_login_switch_tab")==undefined ? "normalLogin" : $.cookie("mxk_login_switch_tab");
        $("#"+switch_tab).click();
        $("#"+switch_tab+"Form input[name=username]").val(cookieLoginUsername ==undefined ? "" : cookieLoginUsername);
        $("#div_"+switch_tab+" input[name=password]").focus();
    }else{
        $("#div_normalLogin input[name=username]").focus();
    }
    
    //send sms to mobile
    $("#mobile_j_otp_button").on("click",function(){  
        var loginName = $("#mobile_j_username").val();  
        if(captchaCount<60 || loginName == ""){return; }
        $.get(webContextPath +"/login/sendsms/"+loginName,function(data,status){
            //alert("Data: " + data + "\nStatus: " + status);
        });
        //倒计时60秒
        countDownTimer=setInterval("doCountDownTimer()", 1000);
    });
    
});