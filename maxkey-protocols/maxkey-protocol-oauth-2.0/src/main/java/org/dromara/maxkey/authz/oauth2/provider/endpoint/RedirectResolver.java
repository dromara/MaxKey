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
 

package org.dromara.maxkey.authz.oauth2.provider.endpoint;

import org.dromara.maxkey.authz.oauth2.common.exceptions.OAuth2Exception;
import org.dromara.maxkey.entity.apps.oauth2.provider.ClientDetails;

/**
 * Basic interface for determining the redirect URI for a user agent.
 * 
 * @author Ryan Heaton
 */
public interface RedirectResolver {

  /**
   * Resolve the redirect for the specified client.
   *
   * @param requestedRedirect The redirect that was requested (may not be null).
   * @param client The client for which we're resolving the redirect.
   * @return The resolved redirect URI.
   * @throws OAuth2Exception If the requested redirect is invalid for the specified client.
   */
  String resolveRedirect(String requestedRedirect, ClientDetails client) throws OAuth2Exception;

}
