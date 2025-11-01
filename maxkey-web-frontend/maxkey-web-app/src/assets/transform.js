/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 */

/**
 * 动态加载js脚本文件
 * @param {*} scriptSrc
 */
function appendScriptSrc(scriptSrc){
  var script = document.createElement('script');
  script.src = scriptSrc; // 加载JS文件路径
  document.body.appendChild(script);
}

/**
 *   /passport/login--->/#/passport/login
 *   /passport/callback/---->/#/passport/callback/
 * @param {*} transPath
 */
function locationTransform(transPath){
    var topHref = top.location.href;
    if(topHref.indexOf('#') <= 0){
        var loginIndex = topHref.indexOf(transPath);
        if(loginIndex >- 1){
          topHref = topHref.substring(0,loginIndex) + '/#' + topHref.substring(loginIndex);
          top.location.href = topHref;
        }
    }
}
var transPaths = [
    '/passport/login',
    '/passport/callback/'
];
for (i = 0; i < transPaths.length; i++) {
    locationTransform(transPaths[i]);
}

