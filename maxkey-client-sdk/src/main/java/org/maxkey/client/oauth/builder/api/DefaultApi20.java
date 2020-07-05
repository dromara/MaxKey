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

/**
 * Default implementation of the OAuth protocol, version 2.0 (draft 11)
 *
 * This class is meant to be extended by concrete implementations of the API,
 * providing the endpoints and endpoint-http-verbs.
 *
 * If your Api adheres to the 2.0 (draft 11) protocol correctly, you just need to extend
 * this class and define the getters for your endpoints.
 *
 * If your Api does something a bit different, you can override the different
 * extractors or services, in order to fine-tune the process. Please read the
 * javadocs of the interfaces to get an idea of what to do.
 *
 * @author Diego Silveira
 *
 */
public abstract class DefaultApi20 implements Api
{

  /**
   * Returns the access token extractor.
   * 
   * @return access token extractor
   */
  public AccessTokenExtractor getAccessTokenExtractor()
  {
    return new TokenExtractor20Impl();
  }
	
  /**
   * Returns the verb for the access token endpoint (defaults to GET)
   * 
   * @return access token endpoint verb
   */
  public HttpVerb getAccessTokenVerb()
  {
    return HttpVerb.GET;
  }
	
  /**
   * Returns the URL that receives the access token requests.
   * 
   * @return access token URL
   */
  public abstract String getAccessTokenEndpoint();

  
  public abstract String getGrantType() ;
  /**
   * Returns the URL where you should redirect your users to authenticate
   * your application.
   *
   * @param config OAuth 2.0 configuration param object
   * @return the URL where you should redirect your users
   */
  public abstract String getAuthorizationUrl(OAuthConfig config);

  /**
   * {@inheritDoc}
   */
  public OAuthService createService(OAuthConfig config)
  {
    return new OAuth20ServiceImpl(this, config);
  }

}
