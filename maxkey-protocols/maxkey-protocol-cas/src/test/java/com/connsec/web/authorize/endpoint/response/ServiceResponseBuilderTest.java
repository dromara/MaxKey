package com.connsec.web.authorize.endpoint.response;

import org.maxkey.authz.cas.endpoint.response.ProxyServiceResponseBuilder;
import org.maxkey.authz.cas.endpoint.response.ServiceResponseBuilder;
import org.maxkey.pretty.impl.JsonPretty;
import org.maxkey.pretty.impl.XmlPretty;


public class ServiceResponseBuilderTest {

	public ServiceResponseBuilderTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
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
