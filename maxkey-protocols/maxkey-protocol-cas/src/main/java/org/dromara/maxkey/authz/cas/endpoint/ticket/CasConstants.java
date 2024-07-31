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
 

package org.dromara.maxkey.authz.cas.endpoint.ticket;


public class CasConstants {
	/* CAS Protocol Parameters. **/
	public static final class PARAMETER{
		public static final  String ENDPOINT_CAS_DETAILS			= "CAS_AUTHORIZE_ENDPOINT_CAS_DETAILS";
		
		public static final  String PARAMETER_MAP				= "CAS_AUTHORIZE_ENDPOINT_PARAMETER_MAP";

		/** Constant representing the ticket parameter in the request. */
		public static final  String TICKET						= "ticket";
		
		/** Constant representing the service parameter in the request. */
		public static final String SERVICE 						= "service";
		
		/** Constant representing the targetService parameter in the request. */
		public static final String TARGET_SERVICE 				= "targetService";
		
		/** Constant representing the method parameter in the request. */
		public static final String METHOD 						= "method";
		
		/** Constant representing the proxy callback url parameter in the request. */
		public static final String PROXY_CALLBACK_URL 			= "pgtUrl";

	    /** Constant representing the renew parameter in the request. */
		public static final String RENEW 						= "renew";

	    /** Constant representing the gateway parameter in the request. */
		public static final String GATEWAY 						= "gateway";

	    /** Constant representing the format parameter in the request. */
		public static final String FORMAT 						= "format";
	    
	    /** Constant representing the pgtId parameter in the request. */
		public static final String PROXY_GRANTING_TICKET_ID 	= "pgtId";

	    /** Constant representing the pgt parameter in the request. */
		public static final String PROXY_GRANTING_TICKET 		= "pgt";

	    /** Constant representing the pgtIou parameter in the request. */
		public static final String PROXY_GRANTING_TICKET_IOU 	= "pgtIou";
		
		public static final String REST_USERNAME 				= "username";
		
		public static final String REST_PASSWORD 				= "password";
	}
	
	/* CAS Protocol PREFIX */
	public static final class PREFIX{
		/** Proxy ticket prefix applied to unique ids. */
		public static final String PROXY_TICKET_PREFIX 				= "PT";
		/**
	     * Prefix generally applied to unique ids generated
	     * by UniqueTicketIdGenerator.
	     */
		public static final String SERVICE_TICKET_PREFIX 			= "ST";
	    /** The prefix to use when generating an id for a Proxy Granting Ticket. */
		public static final String PROXY_GRANTING_TICKET_PREFIX 	= "PGT";
	    /** The prefix to use when generating an id for a Proxy Granting Ticket IOU. */
		public static final String PROXY_GRANTING_TICKET_IOU_PREFIX = "PGTIOU";
		
		public static final String TICKET_GRANTING_TICKET_PREFIX 	= "TGT";
	}
	
	/* CAS Protocol Error Codes. **/
	public static class ERROR_CODE{
		/** Constant representing an invalid request for validation. */
		public static  String INVALID_REQUEST				=	"INVALID_REQUEST";
		/** Constant representing an invalid pgt request. */
		public static String INVALID_REQUEST_PROXY 			= 	"INVALID_REQUEST_PROXY";
		public static String INVALID_TICKET_SPEC			=	"INVALID_TICKET_SPEC";
		/** Constant representing an invalid proxy callback for validation. */
		public static String INVALID_PROXY_CALLBACK			=	"INVALID_PROXY_CALLBACK";
		/** Constant representing an invalid ticket for validation. */
		public static String INVALID_TICKET					=	"INVALID_TICKET";
		public static String INVALID_SERVICE				=	"INVALID_SERVICE";
		public static String INTERNAL_ERROR					=	"INTERNAL_ERROR";
		
		/** Constant representing an invalid service proxy request. */
		public static String UNAUTHORIZED_SERVICE_PROXY		=	"UNAUTHORIZED_SERVICE_PROXY";
		/** Constant representing an invalid service request. */
		public static String UNAUTHORIZED_SERVICE		 	= 	"UNAUTHORIZED_SERVICE";
	}
	
	/* CAS Protocol endpoint. **/
	public static class ENDPOINT{
		public static final  String ENDPOINT_BASE 				= "/authz/cas";
	    /**
	     * Constant representing login.
	     */
		public static final  String ENDPOINT_LOGIN 				= ENDPOINT_BASE + "/login";
	
	    /**
	     * Constant representing logout.
	     */
		public static final  String ENDPOINT_LOGOUT 			= ENDPOINT_BASE + "/logout";
	
	    /**
	     * Constant representing proxy validate.
	     */
		public static final  String ENDPOINT_PROXY_VALIDATE 	= ENDPOINT_BASE + "/proxyValidate";
	
	    /**
	     * Constant representing v3 proxy validate.
	     */
		public static final  String ENDPOINT_PROXY_VALIDATE_V3 	= ENDPOINT_BASE + "/p3/proxyValidate";
	
	    /**
	     * Constant representing legacy validate.
	     */
		public static final  String ENDPOINT_VALIDATE 			= ENDPOINT_BASE + "/validate";
	
	    /**
	     * Constant representing service validate.
	     */
		public static  final String ENDPOINT_SERVICE_VALIDATE 	= ENDPOINT_BASE + "/serviceValidate";
	
	    /**
	     * Constant representing v3 service validate.
	     */
	    public static final  String ENDPOINT_SERVICE_VALIDATE_V3 = ENDPOINT_BASE + "/p3/serviceValidate";
	
	    /**
	     * Constant representing proxy endpoint.
	     */
	    public static final  String ENDPOINT_PROXY 				= ENDPOINT_BASE + "/proxy";
	    
	    /**
	     * Constant representing v3 proxy endpoint.
	     */
	    public static final  String ENDPOINT_PROXY_V3 			= ENDPOINT_BASE + "/p3/proxy";
	    
	    public static final  String ENDPOINT_SERVICE_TICKET_GRANTING = ENDPOINT_BASE + "/granting";
	    
	    public static final  String ENDPOINT_REST_TICKET_V1 	= ENDPOINT_BASE + "/v1/tickets";
	    
	    public static final  String ENDPOINT_REST_USERS_V1 		= ENDPOINT_BASE + "/v1/users";
	    
	    
	}
}
