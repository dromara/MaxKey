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