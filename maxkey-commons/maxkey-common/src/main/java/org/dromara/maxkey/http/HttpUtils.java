/*
 * Copyright [2025] [MaxKey of copyright http://www.maxkey.top]
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

 

package org.dromara.maxkey.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.ObjectUtils;

public class HttpUtils {

    /**
     * https://cas.maxkey.top/casserver/authz?aaa=aaa&bbb=bbb <br/>
     * return <br/>
     * https://cas.maxkey.top/casserver/authz
     * @param param
     * @return
     */
    public static String requestUrl(String param){
        String url = param;
        if(param.indexOf("?") > -1) {
            url = param.substring(0, param.indexOf("?"));
        }
        return url;
    }
    
    /**
     * 把map参数加到url后
     * @param url
     * @param parameterMap
     * @return 
     */
    public static String appendToUrl(String url,Map<String,String> parameterMap){
        StringBuffer appendedUrl = new StringBuffer(url);
        if(ObjectUtils.isNotEmpty(parameterMap)) {
            for (Entry<String, String> entry : parameterMap.entrySet()) {
                appendedUrl.append((appendedUrl.indexOf("?") == -1)?"?":"&")
                           .append(entry.getKey()).append("=").append(entry.getValue());
            }
        }
        return appendedUrl.toString();
    }
    
    /**
     * https://cas.maxkey.top/casserver/authz?aaa=aaa&bbb=bbb <br/>
     * 把aaa=aaa&bbb=bbb转换成map
     * @param param
     * @return Map
     */
    public static Map<String,String> queryStringToMap(String param){
        String urlParam = param;
        Map<String,String> paramMap = new HashMap<String,String>();
        if(param.indexOf("?") > -1) {
            urlParam = param.substring(param.indexOf("?") + 1);
        }
        String[] params =  urlParam.split("&");
        for(String keyValue : params){
           String[] paramValue = keyValue.split("=");
           if(paramValue.length == 2){
               paramMap.put(paramValue[0], paramValue[1]);
           }
        }
        return paramMap;
     }
}
