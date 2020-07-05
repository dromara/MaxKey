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
 

package org.maxkey.client.http;

import java.io.*;
import java.net.*;
import java.nio.charset.*;
import java.util.*;
import java.util.concurrent.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.maxkey.client.oauth.exceptions.*;
import org.maxkey.client.utils.HttpsTrusts;
import org.maxkey.client.utils.JsonUtils;

/**
 * Represents an HTTP Request object
 * 
 */
public class Request
{
	private   static  Log _log  =  LogFactory.getLog(Request. class );
	public  static final String 	DEFAULT_CONTENT_TYPE 	= "application/x-www-form-urlencoded";
	private static final String 	CONTENT_LENGTH 			= 	"Content-Length";
	private static final String 	CONTENT_TYPE 			= 	"Content-Type";
	private static RequestTuner 	NOOP 					= 	new RequestTuner() {
		@Override public void tune(Request request){}
	};
	
	
	private String 				url;
	private HttpVerb 				verb;
	private ParameterList 		querystringParams;
	private ParameterList 		bodyParams;
	private Map<String, String> headers;
	private String 				payload				= 	null;
	private HttpURLConnection 	connection;
	private String 				charset;
	private byte[] 				bytePayload 		= 	null;
	private boolean 			connectionKeepAlive = 	false;
	private boolean 			followRedirects 	= 	true;
	private Long 				connectTimeout 		= 	null;
	private Long 				readTimeout 		= 	null;
	private String 				realm;
  /**
   * Creates a new Http Request
   * 
   * @param verb Http Verb (GET, POST, etc)
   * @param url url with optional querystring parameters.
   */
  public Request(HttpVerb verb, String url)
  {
    this.verb = verb;
    this.url = url;
    this.querystringParams = new ParameterList();
    this.bodyParams = new ParameterList();
    this.headers = new HashMap<String, String>();
  }

  /**
   * Execute the request and return a {@link Response}
   * 
   * @return Http Response
   * @throws RuntimeException
   *           if the connection cannot be created.
   */
  public Response send(RequestTuner tuner)
  {
    try
    {
      createConnection();
      return doSend(tuner);
    }
    catch (Exception e)
    {
      throw new OAuthConnectionException(e);
    }
  }

  public Response send()
  {
    return send(NOOP);
  }

  private void createConnection() throws IOException
  {
    String completeUrl = getCompleteUrl();
    _log.debug("verb method : "+verb);
    _log.debug("completeUrl : "+completeUrl);
    
    if (connection == null)
    {
      System.setProperty("http.keepAlive", connectionKeepAlive ? "true" : "false");
      connection = (HttpURLConnection) new URL(completeUrl).openConnection();
      if(completeUrl.trim().startsWith("https")){
    	  HttpsTrusts.beforeConnection();
      }
     
      connection.setInstanceFollowRedirects(followRedirects);
    }
  }

  /**
   * Returns the complete url (host + resource + encoded querystring parameters).
   *
   * @return the complete url.
   */
  public String getCompleteUrl()
  {
    return querystringParams.appendTo(url);
  }

  Response doSend(RequestTuner tuner) throws IOException
  {
    connection.setRequestMethod(this.verb.name());
    if (connectTimeout != null) 
    {
      connection.setConnectTimeout(connectTimeout.intValue());
    }
    if (readTimeout != null)
    {
      connection.setReadTimeout(readTimeout.intValue());
    }
    addHeaders(connection);
    if (verb.equals(HttpVerb.PUT) || verb.equals(HttpVerb.POST))
    {
      addBody(connection, getByteBodyContents());
    }
    tuner.tune(this);
    return new Response(connection);
  }

  void addHeaders(HttpURLConnection conn)
  {
    for (String key : headers.keySet())
      conn.setRequestProperty(key, headers.get(key));
  }

  void addBody(HttpURLConnection conn, byte[] content) throws IOException
  {
    conn.setRequestProperty(CONTENT_LENGTH, String.valueOf(content.length));

    // Set default content type if none is set.
    if (conn.getRequestProperty(CONTENT_TYPE) == null)
    {
      conn.setRequestProperty(CONTENT_TYPE, DEFAULT_CONTENT_TYPE);
    }
    conn.setDoOutput(true);
    conn.getOutputStream().write(content);
  }

  /**
   * Add an HTTP Header to the Request
   * 
   * @param key the header name
   * @param value the header value
   */
  public void addHeader(String key, String value)
  {
    this.headers.put(key, value);
  }

  /**
   * Add a body Parameter (for POST/ PUT Requests)
   * 
   * @param key the parameter name
   * @param value the parameter value
   */
  public void addBodyParameter(String key, String value)
  {
    this.bodyParams.add(key, value);
  }

  /**
   * Add a QueryString parameter
   *
   * @param key the parameter name
   * @param value the parameter value
   */
  public void addQuerystringParameter(String key, String value)
  {
    this.querystringParams.add(key, value);
  }

  public void addParameter(String key, String value) {
      if (hasBodyContent()) {
          bodyParams.add(key, value);
      } else {
          querystringParams.add(key, value);
      }
  }
  
  protected boolean hasBodyContent() {
      return verb == HttpVerb.PUT || verb == HttpVerb.POST;
  }
  /**
   * Add body payload.
   * 
   * This method is used when the HTTP body is not a form-url-encoded string,
   * but another thing. Like for example XML.
   * 
   * Note: The contents are not part of the OAuth signature
   * 
   * @param payload the body of the request
   */
  public void addPayload(String payload)
  {
    this.payload = payload;
  }

  /**
   * Overloaded version for byte arrays
   *
   * @param payload
   */
  public void addPayload(byte[] payload)
  {
    this.bytePayload = payload.clone();
  }

  /**
   * set REST Content
   *
   * @param content
   */
  public void addRestContent(String content)
  {
    this.payload = content;
  }
  
  /**
   * set REST Content
   *
   * @param content
   */
  public void addRestObject(Object content)
  {
    try {
		this.bytePayload = JsonUtils.gson2Json(content).getBytes("UTF-8");
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
  
  /**
   * set REST Content
   *
   * @param content
   */
  public void addRestContent(byte[] content)
  {
    this.bytePayload = content.clone();
  }

  /**
   * Get a {@link ParameterList} with the query string parameters.
   * 
   * @return a {@link ParameterList} containing the query string parameters.
   * @throws OAuthException if the request URL is not valid.
   */
  public ParameterList getQueryStringParams()
  {
    try
    {
      ParameterList result = new ParameterList();
      String queryString = new URL(url).getQuery();
      result.addQuerystring(queryString);
      result.addAll(querystringParams);
      return result;
    }
    catch (MalformedURLException mue)
    {
      throw new OAuthException("Malformed URL", mue);
    }
  }

  /**
   * Obtains a {@link ParameterList} of the body parameters.
   * 
   * @return a {@link ParameterList}containing the body parameters.
   */
  public ParameterList getBodyParams()
  {
    return bodyParams;
  }

  /**
   * Obtains the URL of the HTTP Request.
   * 
   * @return the original URL of the HTTP Request
   */
  public String getUrl()
  {
    return url;
  }

  /**
   * Returns the URL without the port and the query string part.
   * 
   * @return the OAuth-sanitized URL
  
  public String getSanitizedUrl()
  {
    return url.replaceAll("\\?.*", "").replace("\\:\\d{4}", "");
  }
 */
  /**
   * Returns the URL without the port and the query string part.
   *
   * @return the OAuth-sanitized URL
   */
  public String getSanitizedUrl() {
      if (url.startsWith("http://") && (url.endsWith(":80") || url.contains(":80/"))) {
          return url.replaceAll("\\?.*", "").replaceAll(":80", "");
      } else if (url.startsWith("https://") && (url.endsWith(":443") || url.contains(":443/"))) {
          return url.replaceAll("\\?.*", "").replaceAll(":443", "");
      } else {
          return url.replaceAll("\\?.*", "");
      }
  }
  /**
   * Returns the body of the request
   * 
   * @return form encoded string
   * @throws OAuthException if the charset chosen is not supported
   */
  public String getBodyContents()
  {
    try
    {
      return new String(getByteBodyContents(),getCharset());
    }
    catch(UnsupportedEncodingException uee)
    {
      throw new OAuthException("Unsupported Charset: "+charset, uee);
    }
  }

  byte[] getByteBodyContents()
  {
    if (bytePayload != null) return bytePayload;
    String body = (payload != null) ? payload : bodyParams.asFormUrlEncodedString();
    
    _log.debug("getByteBodyContents  : "+body);
    try
    {
      return body.getBytes(getCharset());
    }
    catch(UnsupportedEncodingException uee)
    {
      throw new OAuthException("Unsupported Charset: "+getCharset(), uee);
    }
  }


  /**
   * Returns the HTTP Verb
   * 
   * @return the verb
   */
  public HttpVerb getVerb()
  {
    return verb;
  }
  
  public void setRealm(String realm) {
      this.realm = realm;
  }

  public String getRealm() {
      return realm;
  }
  
  /**
   * Returns the connection headers as a {@link Map}
   * 
   * @return map of headers
   */
  public Map<String, String> getHeaders()
  {
    return headers;
  }

  /**
   * Returns the connection charset. Defaults to {@link Charset} defaultCharset if not set
   *
   * @return charset
   */
  public String getCharset()
  {
    return charset == null ? Charset.defaultCharset().name() : charset;
  }

  /**
   * Sets the connect timeout for the underlying {@link HttpURLConnection}
   * 
   * @param duration duration of the timeout
   * 
   * @param unit unit of time (milliseconds, seconds, etc)
   */
  public void setConnectTimeout(int duration, TimeUnit unit)
  {
    this.connectTimeout = unit.toMillis(duration);
  }

  /**
   * Sets the read timeout for the underlying {@link HttpURLConnection}
   * 
   * @param duration duration of the timeout
   * 
   * @param unit unit of time (milliseconds, seconds, etc)
   */
  public void setReadTimeout(int duration, TimeUnit unit)
  {
    this.readTimeout = unit.toMillis(duration);
  }

  /**
   * Set the charset of the body of the request
   *
   * @param charsetName name of the charset of the request
   */
  public void setCharset(String charsetName)
  {
    this.charset = charsetName;
  }

  /**
   * Sets whether the underlying Http Connection is persistent or not.
   *
   * @see http://download.oracle.com/javase/1.5.0/docs/guide/net/http-keepalive.html
   * @param connectionKeepAlive
   */
  
  @Deprecated
  public void setConnectionKeepAlive(boolean connectionKeepAlive){
    this.connectionKeepAlive = connectionKeepAlive;
  }
  
  /**
   * 
   * @param connectionKeepAlive
   * true or false
   */
  public void setConnectionKeepAlive(String connectionKeepAlive){
	  System.setProperty("http.keepAlive", connectionKeepAlive);    
  }

  /**
   * Sets whether the underlying Http Connection follows redirects or not.
   *
   * Defaults to true (follow redirects)
   *
   * @see http://docs.oracle.com/javase/6/docs/api/java/net/HttpURLConnection.html#setInstanceFollowRedirects(boolean)
   * @param followRedirects
   */
  public void setFollowRedirects(boolean followRedirects)
  {
    this.followRedirects = followRedirects;
  }

  /*
   * We need this in order to stub the connection object for test cases
   */
  void setConnection(HttpURLConnection connection)
  {
    this.connection = connection;
  }

  @Override
  public String toString()
  {
    return String.format("@Request(%s %s)", getVerb(), getUrl());
  }
}
