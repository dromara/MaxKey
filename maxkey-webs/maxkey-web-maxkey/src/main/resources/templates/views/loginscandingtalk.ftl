     <script src="${sspLogin.dingTalkLogin}://g.alicdn.com/dingding/dinglogin/0.0.5/ddLogin.js"></script>
     <script type="text/javascript"> 
        var dingtalkredirect_uri="";
        var handleMessage = function (event) {
        var origin = event.origin;
        console.log("origin", event.origin);
        if( origin == "https://login.dingtalk.com" ) { //判断是否来自ddLogin扫码事件。
            var loginTmpCode = event.data; 
             dingtalkredirect_uri = dingtalkredirect_uri+'&loginTmpCode='+loginTmpCode;
            //获取到loginTmpCode后就可以在这里构造跳转链接进行跳转了
            console.log("loginTmpCode", loginTmpCode);
            console.log("dingtalkredirect_uri", dingtalkredirect_uri);
            window.top.location.href = dingtalkredirect_uri;
          }
        };
        if (typeof window.addEventListener != 'undefined') {
            window.addEventListener('message', handleMessage, false);
        } else if (typeof window.attachEvent != 'undefined') {
            window.attachEvent('onmessage', handleMessage);
        }
        $(function(){
           $("#qrcodelogin").on("click",function(){
              $.get("<@base />/logon/oauth20/scanqrcode/dingtalk",function(data,status){
                        var url = encodeURIComponent(data.redirectUri);
                        var gotodingtalk = encodeURIComponent('https://oapi.dingtalk.com/connect/oauth2/sns_authorize?appid='+data.clientId+'&response_type=code&scope=snsapi_login&state='+data.state+'&redirect_uri='+url)
                        dingtalkredirect_uri = 'https://oapi.dingtalk.com/connect/oauth2/sns_authorize?appid='+data.clientId+'&response_type=code&scope=snsapi_login&state='+data.state+'&redirect_uri='+data.redirectUri;
                        console.log("dingtalkredirect_uri", dingtalkredirect_uri);
                        console.log("gotodingtalk", gotodingtalk);
                        var obj = DDLogin({
                             id:"div_qrcodelogin",//这里需要你在自己的页面定义一个HTML标签并设置id，例如<div id="login_container"></div>或<span id="login_container"></span>
                             goto: gotodingtalk, //请参考注释里的方式
                             style: "border:none;background-color:#FFFFFF;",
                             width : "365",
                             height: "400"
                         });
                        $('#div_qrcodelogin').show();
                    });
            });
        });
    </script> 