    var captchaCountTimer;
    var captchaCount=60;
    function getCaptchaCount(){
        $("#mobile_j_otp_captcha_button").val("重新获取("+captchaCount+")秒");
        
        
        captchaCount--;
        if(captchaCount==0){
            $("#mobile_j_otp_captcha_button").val("发送验证码");
            captchaCount=60;
            clearInterval(captchaCountTimer);
        }
    }
    var fullYear=currentDate.getFullYear();
    var month=currentDate.getMonth()+1;
    var date=currentDate.getDate();
    
    var hours=currentDate.getHours();
    var minutes=currentDate.getMinutes();
    var seconds=currentDate.getSeconds();
    var strTime="";
    function formatTime(){
        strTime=fullYear+"-";
        strTime+=(month<10?"0"+month:month)+"-";
        strTime+=(date<10?"0"+date:date)+" ";
        strTime+=(hours<10?"0"+hours:hours)+":";
        strTime+=(minutes<10?"0"+minutes:minutes)+":";
        strTime+=(seconds<10?"0"+seconds:seconds);
    }
    
    function currentTime(){
        seconds++;
        if(seconds>59){
            minutes++;
            seconds=0;
        }
        if(minutes>59){
            hours++;
            minutes=0;
        }
        if(hours>23){
            date++;
            hours=0;
        }
        formatTime();
        //for timebase token
        getTimeBaseCount();
        
        $("#currentTime").val(strTime);
    }
    
    var timeBaseCount;
    function getTimeBaseCount(){
        if(seconds<30){
            timeBaseCount=30-seconds;
        }else{
            timeBaseCount=30-(seconds-30);
        }
        $("#tfa_j_otp_captcha_button").val("剩余时间("+timeBaseCount+")秒");
    };
    var currentSwitchTab="normalLogin";
    function doLoginSubmit(){
        $.cookie("mxk_login_username", $("#"+currentSwitchTab+"Form input[name=username]").val(), { expires: 7 });
        $("#"+currentSwitchTab+"SubmitButton").click();
        $.cookie("mxk_login_switch_tab", currentSwitchTab, { expires: 7 });
    };
    
    function switchTab(id){
        if($("#"+id+"Form  input[name=username]").val()==""){
            $("#"+id+"Form input[name=username]").focus();
        }else{
            $("#"+id+"Form  input[name=password]").focus();
        }
        currentSwitchTab=id;
    }
    document.onkeydown=function(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];
        if(e && e.keyCode==13){ 
            doLoginSubmit();
        };
    };
    
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
        $("#mobile_j_otp_captcha_button").on("click",function(){    
            if(captchaCount<60){
                return;
            }
            var loginName = $("#mobile_j_username").val();
            if(loginName == ""){
                return;
            }
            $.get(webContextPath +"/login/sendsms/"+loginName,function(data,status){
                //alert("Data: " + data + "\nStatus: " + status);
            });
            
            captchaCountTimer=setInterval("getCaptchaCount()", 1000);
        });
        

    });