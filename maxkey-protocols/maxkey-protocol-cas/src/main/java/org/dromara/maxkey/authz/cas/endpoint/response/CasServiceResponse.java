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

import java.util.ArrayList;
import java.util.HashMap;

import org.dromara.maxkey.web.HttpResponseConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CasServiceResponse {
	static final  Logger _logger = LoggerFactory.getLogger(CasServiceResponse.class);

	protected String code;
	protected String description;
	protected boolean result=false;
	protected String user;
	protected String ticket;
	protected String format=HttpResponseConstants.FORMAT_TYPE.XML;
	protected ArrayList<String >proxies=new ArrayList<String>();
	
	protected HashMap<String,ArrayList<String>>casAttributes=new HashMap<String,ArrayList<String>>();
	
	public CasServiceResponse() {
		
	}

	public String getCode() {
		return code;
	}

	public CasServiceResponse setAttribute(String attr,String value){
		if(casAttributes.containsKey(attr)){
			casAttributes.get(attr).add(value);
		}else{
			ArrayList<String> newList=new ArrayList<String>();
			newList.add(value);
			casAttributes.put(attr, newList);
		}
		return this;
	}
	public CasServiceResponse setProxy(String proxy){
		proxies.add(proxy);
		return this;
	}
	public CasServiceResponse setCode(String code) {
		this.code = code;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public CasServiceResponse setDescription(String description) {
		this.description = description;
		return this;
	}
	
	public CasServiceResponse success(){
		result=true;
		return this;
	}
	
	public CasServiceResponse failure(){
		result=false;
		return this;
	}
	
	public String getUser() {
		return user;
	}

	public CasServiceResponse setUser(String user) {
		this.user = user;
		return this;
	}

	public String getTicket() {
		return ticket;
	}

	public CasServiceResponse setTicket(String ticket) {
		this.ticket = ticket;
		return this;
	}

	public String getFormat() {
		return format;
	}

	public CasServiceResponse setFormat(String format) {
		this.format = format;
		return this;
	}


	public String  serviceResponseBuilder(){
		return null;
	}

}
