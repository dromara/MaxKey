     <script src="${sspLogin.feiShuLogin}://sf3-cn.feishucdn.com/obj/static/lark/passport/qrcode/LarkSSOSDKWebQRCode-1.0.1.js"></script>
     <script type="text/javascript"> 
        var redirectUri = "";
        var QRLoginObj ;
        var handleMessage = function (event) {        
            var origin = event.origin;    
            // 使用 matchOrigin 方法来判断 message 是否来自飞书页面
            if( QRLoginObj.matchOrigin(origin) ) {           
                var loginTmpCode = event.data; 
                // 在授权页面地址上拼接上参数 tmp_code，并跳转
                redirectUri = redirectUri+"&tmp_code="+loginTmpCode;
                console.log("loginTmpCode", loginTmpCode);
                console.log("redirectUri " + redirectUri);
                window.top.location.href = redirectUri;
            }
        };
        if (typeof window.addEventListener != 'undefined') {   
            window.addEventListener('message', handleMessage, false);} 
        else if (typeof window.attachEvent != 'undefined') { 
            window.attachEvent('onmessage', handleMessage);
        }

        $(function(){
           $("#qrcodelogin").on("click",function(){
              $.get("<@base />/logon/oauth20/scanqrcode/feishu",function(data,status){
                      redirectUri = "https://passport.feishu.cn/suite/passport/oauth/authorize?client_id="+data.clientId+"&redirect_uri="+encodeURIComponent(data.redirectUri)+"&response_type=code&state="+data.state ;
                      $("#div_qrcodelogin").html("");
                      QRLoginObj = QRLogin({
                            id:"div_qrcodelogin",
                            goto: redirectUri,
                            width: "300",
                            height: "300",
                        });
                        $('#div_qrcodelogin').show();
                    });
            });
        });
    </script> 