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
 

package org.maxkey.client.oauth.builder.api;


import org.maxkey.client.http.HttpVerb;
import org.maxkey.client.oauth.extractors.*;
import org.maxkey.client.oauth.model.*;
import org.maxkey.client.oauth.oauth.*;
import org.maxkey.client.oauth.services.*;

/**
 * Default implementation of the OAuth protocol, version 1.0a
 * 
 * This class is meant to be extended by concrete implementations of the API,
 * providing the endpoints and endpoint-http-verbs.
 * 
 * If your Api adheres to the 1.0a protocol correctly, you just need to extend 
 * this class and define the getters for your endpoints.
 * 
 * If your Api does something a bit different, you can override the different 
 * extractors or services, in order to fine-tune the process. Please read the 
 * javadocs of the interfaces to get an idea of what to do.
 * 
 * @author Pablo Fernandez
 *
 */
public abstract class DefaultApi10a implements Api
{
  /**
   * Returns the access token extractor.
   * 
   * @return access token extractor
   */
  public AccessTokenExtractor getAccessTokenExtractor()
  {
    return new TokenExtractorImpl();
  }

  /**
   * Returns the base string extractor.
   * 
   * @return base string extractor
   */
  public BaseStringExtractor getBaseStringExtractor()
  {
    return new BaseStringExtractorImpl();
  }

  /**
   * Returns the header extractor.
   * 
   * @return header extractor
   */
  public HeaderExtractor getHeaderExtractor()
  {
    return new HeaderExtractorImpl();
  }

  /**
   * Returns the request token extractor.
   * 
   * @return request token extractor
   */
  public RequestTokenExtractor getRequestTokenExtractor()
  {
    return new TokenExtractorImpl();
  }

  /**
   * Returns the signature service.
   * 
   * @return signature service
   */
  public SignatureService getSignatureService()
  {
    return new HMACSha1SignatureService(); 
  }

  /**
   * Returns the timestamp service.
   * 
   * @return timestamp service
   */
  public TimestampService getTimestampService()
  {
    return new TimestampServiceImpl();
  }
  
  /**
   * Returns the verb for the access token endpoint (defaults to POST)
   * 
   * @return access token endpoint verb
   */
  public HttpVerb getAccessTokenVerb()
  {
    return HttpVerb.POST;
  }
  
  /**
   * Returns the verb for the request token endpoint (defaults to POST)
   * 
   * @return request token endpoint verb
   */
  public HttpVerb getRequestTokenVerb()
  {
    return HttpVerb.POST;
  }
  
  /**
   * Returns the URL that receives the request token requests.
   * 
   * @return request token URL
   */
  public abstract String getRequestTokenEndpoint();
  
  /**
   * Returns the URL that receives the access token requests.
   * 
   * @return access token URL
   */
  public abstract String getAccessTokenEndpoint();
  
  /**
   * Returns the URL where you should redirect your users to authenticate
   * your application.
   * 
   * @param requestToken the request token you need to authorize
   * @return the URL where you should redirect your users
   */
  public abstract String getAuthorizationUrl(Token requestToken);
  
  /**
   * Returns the {@link OAuthService} for this Api
   * 
   * @param apiKey Key
   * @param apiSecret Api Secret
   * @param callback OAuth callback (either URL or 'oob')
   * @param scope OAuth scope (optional) 
   */
  public OAuthService createService(OAuthConfig config)
  {
    return new OAuth10aServiceImpl(this, config);
  }
}
