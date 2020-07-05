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
 

package org.maxkey.client.oauth.extractors;

import java.io.UnsupportedEncodingException;
import java.util.regex.*;

import org.maxkey.client.oauth.exceptions.*;
import org.maxkey.client.oauth.model.*;
import org.maxkey.client.utils.HttpEncoder;
import org.maxkey.client.utils.Preconditions;

/**
 * Default implementation of {@RequestTokenExtractor} and {@AccessTokenExtractor}. Conforms to OAuth 1.0a
 *
 * The process for extracting access and request tokens is similar so this class can do both things.
 * 
 * @author Pablo Fernandez
 */
public class TokenExtractorImpl implements RequestTokenExtractor, AccessTokenExtractor
{
  private static final Pattern TOKEN_REGEX = Pattern.compile("oauth_token=([^&]+)");
  private static final Pattern SECRET_REGEX = Pattern.compile("oauth_token_secret=([^&]*)");

  /**
   * {@inheritDoc} 
   */
  public Token extract(String response){
    Preconditions.checkEmptyString(response, "Response body is incorrect. Can't extract a token from an empty string");
    String token = extract(response, TOKEN_REGEX);
    String secret = extract(response, SECRET_REGEX);
    return new Token(token, secret, response);
  }

  private String extract(String response, Pattern p){
    Matcher matcher = p.matcher(response);
    if (matcher.find() && matcher.groupCount() >= 1){
    	try {
    	  return HttpEncoder.decode(matcher.group(1));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }else{
      throw new OAuthException("Response body is incorrect. Can't extract token and secret from this: '" + response + "'", null);
    }
  }
}
