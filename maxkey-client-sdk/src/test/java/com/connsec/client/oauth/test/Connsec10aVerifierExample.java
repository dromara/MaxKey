package com.connsec.client.oauth.test;

import java.util.*;

import org.maxkey.client.http.HttpVerb;
import org.maxkey.client.http.Response;
import org.maxkey.client.oauth.builder.ServiceBuilder;
import org.maxkey.client.oauth.builder.api.MaxkeyApi10a;
import org.maxkey.client.oauth.model.OAuthRequest;
import org.maxkey.client.oauth.model.Token;
import org.maxkey.client.oauth.model.Verifier;
import org.maxkey.client.oauth.oauth.OAuthService;


public class Connsec10aVerifierExample
{
	private static final String PROTECTED_RESOURCE_URL = "http://api.foursquare.com/v1/user";
	  
	  public static void main(String[] args)
	  {
	    OAuthService service = new ServiceBuilder()
	                                .provider(MaxkeyApi10a.class)
	                                .apiKey("tonr-consumer-key")
	                                .apiSecret("SHHHHH!!!!!!!!!!")
	                                .callback(PROTECTED_RESOURCE_URL)
	                                .build();
	    Scanner in = new Scanner(System.in);

	    System.out.println("=== Foursquare's OAuth Workflow ===");
	    System.out.println();

	    // Obtain the Request Token
	    /*  System.out.println("Fetching the Request Token...");
	    Token requestToken = service.getRequestToken();
	    System.out.println("Got the Request Token!");
	    System.out.println();

	    System.out.println("Now go and authorize Scribe here:");
	    System.out.println(service.getAuthorizationUrl(requestToken));
	    	    System.out.println("And paste the verifier here");
	    System.out.print(">>");
	    Verifier verifier = new Verifier(in.nextLine());
	    System.out.println();
*/
	    Token requestToken =new  Token("d5df60ae-78fa-4a6e-9a66-cd7a84e746ea","");
	    Verifier verifier = new Verifier("MSHAor");
	    // Trade the Request Token and Verfier for the Access Token
	    System.out.println("Trading the Request Token for an Access Token...");
	    Token accessToken = service.getAccessToken(requestToken, verifier);
	    System.out.println("Got the Access Token!");
	    System.out.println("(if your curious it looks like this: " + accessToken + " )");
	    System.out.println();

	    // Now let's go and ask for a protected resource!
	    System.out.println("Now we're going to access a protected resource...");
	    OAuthRequest request = new OAuthRequest(HttpVerb.GET, PROTECTED_RESOURCE_URL);
	    service.signRequest(accessToken, request);
	    Response response = request.send();
	    System.out.println("Got it! Lets see what we found...");
	    System.out.println();
	    System.out.println(response.getBody());

	    System.out.println();
	    System.out.println("Thats it man! Go and build something awesome with Scribe! :)");
	    
	  }
}
