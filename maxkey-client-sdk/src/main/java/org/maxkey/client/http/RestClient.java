package org.maxkey.client.http;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.maxkey.client.http.AuthorizationHeader;
import org.maxkey.client.http.HttpVerb;
import org.maxkey.client.http.Response;
import org.maxkey.client.utils.Preconditions;


public class RestClient {
	
	private   static  Log _log  =  LogFactory.getLog(RestClient. class );
	
	//action method
	private HttpVerb method = HttpVerb.GET;
	
	//request Service 
	private Request request;
	
	
	public RestClient(String url) {
		Preconditions.checkEmptyString(url, "Invalid request url");
		
		request = new Request(method, url);
	}
	
	
	/**
	 * 鏋勯�犳柟娉�
	 * 
	 * @param method 璇锋眰鏂规硶
	 * @param url 璇锋眰url
	 */
	public RestClient(String url,HttpVerb method) {
		Preconditions.checkEmptyString(url, "Invalid request url");
		if(method != null) {
			this.method = method;
		}
		request = new Request(method, url);
	}
	
	
	/**
	 * 娣诲姞璇锋眰鍙傛暟
	 * 
	 * @param name 鍙傛暟鍚�
	 * @param value 鍙傛暟鍊�
	 */
	public void addParameter(String name,String value) {
		Preconditions.checkEmptyString(name, "parameter name is null");
		Preconditions.checkNotNull(request, "RestClient is null");
		if(HttpVerb.GET == method) {
			request.addQuerystringParameter(name, value);
		} else if(HttpVerb.POST == method) {
			request.addBodyParameter(name, value);
		}
	}
	
	/**
	 * 娣诲姞璇锋眰Header
	 * 
	 * @param name 鍙傛暟鍚�
	 * @param value 鍙傛暟鍊�
	 */
	public void addHeader(String name,String value) {
		Preconditions.checkEmptyString(name, "parameter name is null");
		Preconditions.checkNotNull(request, "OAuthRequest is null");
		request.addHeader(name, value);
	}
	
	/**
	 * 鍙戦�佽姹傦紝鑾峰彇杩斿洖鐨勬暟鎹�
	 * 
	 * @return Response 鏈嶅姟绔繑鍥炵殑鏁版嵁
	 */
	public Response execute() {
		return request.send();
	}
	
	
	public void addBasicAuthorization(String username,String password) {
		Preconditions.checkEmptyString(username, "parameter username is null");
		Preconditions.checkEmptyString(password, "parameter password is null");
		Preconditions.checkNotNull(request, "RestClient is null");
		request.addHeader(AuthorizationHeader.AUTHORIZATION_HEADERNAME, AuthorizationHeader.createBasic(username, password));
	}
	
	public void addBearerAuthorization(String bearer) {
		Preconditions.checkEmptyString(bearer, "parameter bearer is null");
		Preconditions.checkNotNull(request, "RestClient is null");
		request.addHeader(AuthorizationHeader.AUTHORIZATION_HEADERNAME, AuthorizationHeader.createBearer(bearer));
	}
	
	  /**
	   * set REST Content
	   *
	   * @param content
	   */
	  public void addRestContent(String content)
	  {
	    this.request.addRestContent(content) ;
	  }
	  
	  /**
	   * set REST Content
	   *
	   * @param content
	   */
	  public void addRestObject(Object content)
	  {
		  this.request.addRestObject(content);
	  }
	  
	  /**
	   * set REST Content
	   *
	   * @param content
	   */
	  public void addRestContent(byte[] content)
	  {
		  this.request.addRestContent(content);
	  }
	
}
