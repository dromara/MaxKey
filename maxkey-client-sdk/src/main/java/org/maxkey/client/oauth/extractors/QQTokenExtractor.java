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

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.maxkey.client.oauth.exceptions.*;
import org.maxkey.client.oauth.model.*;
import org.maxkey.client.utils.Preconditions;

public class QQTokenExtractor implements AccessTokenExtractor
{
	final static Logger log = Logger.getLogger(QQTokenExtractor.class);
	public Token extract(String response){
	
	log.debug("extract a token from : "+response);
	Preconditions.checkEmptyString(response, "Cannot extract a token from a null or empty String");
	try{
		
		String [] fields=response.split("&");
		HashMap<String,Object>  tokenMap=new HashMap<String,Object>();
		for(String field : fields){
			String [] sfield=field.split("=");
			if(sfield.length==2){
				tokenMap.put(sfield[0], sfield[1]);
			}
		}
		log.debug("token map : "+tokenMap);
		Token token =new Token(tokenMap.get("access_token").toString(),"",response,tokenMap);
		return token;
	}
   catch(Exception e){
      throw new OAuthException("Cannot extract an acces token. Response was: " + response);
    }
  }

}
