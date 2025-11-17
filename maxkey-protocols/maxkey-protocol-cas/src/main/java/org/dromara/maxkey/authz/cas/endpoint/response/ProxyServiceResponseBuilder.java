/*
 * Copyright [2020] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.dromara.maxkey.authz.cas.endpoint.response;

import org.dromara.maxkey.http.HttpResponseConstants;

public class ProxyServiceResponseBuilder extends  ServiceResponseBuilder{

    public ProxyServiceResponseBuilder() {
        
    }
    
    public ProxyServiceResponseBuilder(String format) {
        this.format = format;
    }

    @Override
    public String  serviceResponseBuilder() {
        String responseString = "";
        if(format.equalsIgnoreCase(HttpResponseConstants.FORMAT_TYPE.XML)){
            responseString = serviceResponseXmlBuilder();
        }else{
            responseString =serviceResponseJsonBuilder();
        }
        _logger.trace("Response String : {}",responseString);
        return responseString;
    }
    
    @Override
    public String  serviceResponseXmlBuilder() {
    	StringBuilder responseResult=new StringBuilder("");
        responseResult.append("<cas:serviceResponse xmlns:cas=\"http://www.yale.edu/tp/cas\">");
        if(result){
            responseResult.append("<cas:proxySuccess>")
            .append("<cas:proxyTicket>").append(ticket).append("</cas:proxyTicket>")
            .append("</cas:proxySuccess>");
        }else{
            responseResult
            .append("<cas:proxyFailure code=\"").append(code).append("\">")
            .append(this.description)
            .append("</cas:proxyFailure>");
        }
        responseResult.append("</cas:serviceResponse>");
        return responseResult.toString();
    }
    
    @Override
    public String  serviceResponseJsonBuilder() {
    	StringBuilder responseResult=new StringBuilder("");
        responseResult.append("{\"serviceResponse\" :{");
        if(result){
            responseResult.append("\"proxySuccess\" : {")
	            .append("\"proxyTicket\" : \"").append(ticket).append("\"")
	            .append("}");
        }else{
            responseResult.append("\"authenticationFailure\" : {")
	            .append("\"code\" : \"").append(this.code).append("\"")
	            .append(",\"description\" : \"").append(this.description).append("\"")
	            .append("}");
        }
        responseResult.append("}").append("}");
        return responseResult.toString();
    }
    

}
