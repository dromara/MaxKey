package com.connsec.rest;

import java.util.HashMap;

import org.junit.Test;
import org.maxkey.client.oauth.OAuthClient;
import org.maxkey.client.oauth.model.Token;
import org.maxkey.client.utils.JsonUtils;


public class RestClientTest {

	//@Test
	public void main()  {
		
		
		OAuthClient tokenRestClient=new OAuthClient("https://exmail.qq.com/cgi-bin/token");
		tokenRestClient.addParameter("grant_type", "client_credentials");
		tokenRestClient.addBasicAuthorization("maxkey", "66199e4c36b6dfcfb6f1ebceda789432");
		Token token =tokenRestClient.requestAccessToken();
		System.out.println(token);
		
		OAuthClient authkeyRestClient=new OAuthClient("http://openapi.exmail.qq.com:12211/openapi/mail/authkey");
		authkeyRestClient.addBearerAuthorization(token.getAccess_token());
		authkeyRestClient.addParameter("Alias", "test@maxkey.org");
		
		
		HashMap authKey=JsonUtils.gson2Object(authkeyRestClient.execute().getBody(), HashMap.class);
		
		String login_url="https://exmail.qq.com/cgi-bin/login?fun=bizopenssologin&method=bizauth&agent=%s&user=%s&ticket=%s";
		System.out.println(String.format(login_url, "connsec","test@maxkey.org",authKey.get("auth_key")));
		//https://exmail.qq.com/cgi-bin/login?fun=bizopenssologin&method=bizauth&agent=connsec&user=test@connsec.com&ticket=25640C491CA4A056BD1A936C6AA4ABBCAB13AE76EB80E6C3A9259F5E8BFD91D7EA05D10DA3FB18F9BFB445D104CB58A0B4CDE97D9F219F3C
	}
}
