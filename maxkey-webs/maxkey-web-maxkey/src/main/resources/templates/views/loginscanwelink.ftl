     <script src="${sspLogin.weLinkLogin}://login.welink.huaweicloud.com/sso-proxy-front/public/qrcode/0.0.1/wlQrcodeLogin.js"></script>
     <script type="text/javascript"> 
        $(function(){
           $("#qrcodelogin").on("click",function(){
              $.get("<@base />/logon/oauth20/scanqrcode/welink",function(data,status){
                       $("#div_qrcodelogin").html("");
                       var wlqrcodeLogin = wlQrcodeLogin({
                          id:"div_qrcodelogin",//这里需要你在自己的页面定义一个HTML标签并设置id，例如<div id="login_container"></div>或<span id="login_container"></span>
                          client_id: data.clientId,
                          response_type: "code", 
                          scope: "snsapi_login", 
                          state: data.state, 
                          redirect_uri: encodeURIComponent(data.redirectUri),
                          style: "border:none;background-color:#FFFFFF;",
                          width : "365",
                          height: "400",
                          self_redirect: false});
                        $('#div_qrcodelogin').show();
                    });
            });
        });
    </script> 