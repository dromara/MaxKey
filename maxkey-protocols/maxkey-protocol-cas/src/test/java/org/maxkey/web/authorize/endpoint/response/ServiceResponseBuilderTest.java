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
 

package org.maxkey.web.authorize.endpoint.response;

import org.dromara.maxkey.authz.cas.endpoint.response.ProxyServiceResponseBuilder;
import org.dromara.maxkey.authz.cas.endpoint.response.ServiceResponseBuilder;
import org.dromara.maxkey.pretty.impl.JsonPretty;
import org.dromara.maxkey.pretty.impl.XmlPretty;


public class ServiceResponseBuilderTest {

	public ServiceResponseBuilderTest() {
	}

	public static void main(String[] args) throws Exception {
		ServiceResponseBuilder srbJson=new ServiceResponseBuilder();
		srbJson.success()
			.setUser("shi")
			.setAttribute("bbb", "bbb")
			.setAttribute("aaa", "1111")
			.setAttribute("aaa", "222")
			.setProxy("https://proxy1/pgtUrl")
			.setProxy("https://proxy2/pgtUrl")
			.setFormat("json");
		
		System.out.println(srbJson.serviceResponseBuilder());
		System.out.println(new JsonPretty().format(srbJson.serviceResponseBuilder()));
		
		ServiceResponseBuilder srbXml=new ServiceResponseBuilder();
		srbXml.success()
		.setUser("shi")
		.setAttribute("bbb", "bbb")
		.setAttribute("aaa", "1111")
		.setAttribute("aaa", "222")
		.setProxy("https://proxy1/pgtUrl")
		.setProxy("https://proxy2/pgtUrl");
		System.out.println(new XmlPretty().format(srbXml.serviceResponseBuilder()));
		
		
		ProxyServiceResponseBuilder psrbXml=new ProxyServiceResponseBuilder();
		psrbXml.success()
		.setUser("shi")
		.setTicket("PT-asdf-JESPjdnJjNjNmMUyTtGFjK");
		System.out.println(new XmlPretty().format(psrbXml.serviceResponseBuilder()));
	}

}
