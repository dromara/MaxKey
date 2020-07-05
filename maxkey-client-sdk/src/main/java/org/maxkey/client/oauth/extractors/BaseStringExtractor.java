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

import org.maxkey.client.oauth.model.*;

/**
 * Simple command object that extracts a base string from a {@link OAuthRequest}
 * 
 * @author Pablo Fernandez
 */
public interface BaseStringExtractor
{
  /**
   * Extracts an url-encoded base string from the {@link OAuthRequest}.
   * 
   * See <a href="http://oauth.net/core/1.0/#anchor14">the oauth spec</a> for more info on this.
   * 
   * @param request the OAuthRequest
   * @return the url-encoded base string
   */
  String extract(OAuthRequest request);
}
