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

import org.maxkey.client.oauth.exceptions.*;
import org.maxkey.client.oauth.model.*;
import org.maxkey.client.utils.JsonUtils;
import org.maxkey.client.utils.Preconditions;

public class GsonJsonTokenExtractor implements AccessTokenExtractor {

	public Token extract(String response) {
		System.out.println("extract a token from : " + response);
		Preconditions.checkEmptyString(response,"Cannot extract a token from a null or empty String");
		try {
			Token token;
			try {
				token = JsonUtils.gson2Object(response, Token.class);
			} catch (Exception e) {
				token = JsonUtils.gson2Object("{\""+ response.replace("&", "\",\"").replace("=","\":\"") + "\"}", Token.class);
			}

			if (token != null) {
				token.setRawResponse(response);
				token.setToken(token.getAccess_token());
			}

			return token;
		} catch (Exception e) {
			throw new OAuthException("Cannot extract an acces token. Response was: " + response);
		}
	}

}
