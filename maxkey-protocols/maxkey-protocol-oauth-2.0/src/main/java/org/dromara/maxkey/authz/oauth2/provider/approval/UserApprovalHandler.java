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
 

package org.dromara.maxkey.authz.oauth2.provider.approval;

import java.util.Map;

import org.dromara.maxkey.authz.oauth2.provider.AuthorizationRequest;
import org.springframework.security.core.Authentication;

/**
 * Basic interface for determining whether a given client authentication request has been approved by the current user.
 * 
 * @author Ryan Heaton
 * @author Dave Syer
 * @author Amanda Anganes
 */
public interface UserApprovalHandler {

	/**
	 * <p>
	 * Tests whether the specified authorization request has been approved by the current user (if there is one).
	 * </p>
	 * 
	 * @param authorizationRequest the authorization request.
	 * @param userAuthentication the user authentication for the current user.
	 * @return true if the request has been approved, false otherwise
	 */
	boolean isApproved(AuthorizationRequest authorizationRequest, Authentication userAuthentication);

	/**
	 * <p>
	 * Provides a hook for allowing requests to be pre-approved (skipping the User Approval Page). Some implementations
	 * may allow users to store approval decisions so that they only have to approve a site once. This method is called
	 * in the AuthorizationEndpoint before sending the user to the Approval page. If this method sets
	 * oAuth2Request.approved to true, the Approval page will be skipped.
	 * </p>
	 * 
	 * @param authorizationRequest the authorization request.
	 * @param userAuthentication the user authentication
	 * @return the AuthorizationRequest, modified if necessary
	 */
	AuthorizationRequest checkForPreApproval(AuthorizationRequest authorizationRequest,
			Authentication userAuthentication);

	/**
	 * <p>
	 * Provides an opportunity to update the authorization request before it is checked for approval in cases where the
	 * incoming approval parameters contain richer information than just true/false (e.g. some scopes are approved, and
	 * others are rejected), implementations may need to be able to modify the {@link AuthorizationRequest} before a
	 * token is generated from it.
	 * </p>
	 * 
	 * @param authorizationRequest the authorization request.
	 * @param userAuthentication the user authentication
	 * @return the AuthorizationRequest, modified if necessary
	 */
	AuthorizationRequest updateAfterApproval(AuthorizationRequest authorizationRequest,
			Authentication userAuthentication);

	/**
	 * Generate a request for the authorization server to ask for the user's approval. Typically this will be rendered
	 * into a view (HTML etc.) to prompt for the approval, so it needs to contain information about the grant (scopes
	 * and client id for instance).
	 * 
	 * @param authorizationRequest the authorization request
	 * @param userAuthentication the user authentication
	 * @return a model map for rendering to the user to ask for approval
	 */
	Map<String, Object> getUserApprovalRequest(AuthorizationRequest authorizationRequest,
			Authentication userAuthentication);

}
