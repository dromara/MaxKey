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


import org.maxkey.client.http.ParameterList;
import org.maxkey.client.oauth.exceptions.*;
import org.maxkey.client.oauth.model.*;
import org.maxkey.client.utils.HttpEncoder;
import org.maxkey.client.utils.Preconditions;

/**
 * Default implementation of {@link BaseStringExtractor}. Conforms to OAuth 1.0a
 * 
 * @author Pablo Fernandez
 *
 */
public class BaseStringExtractorImpl implements BaseStringExtractor
{

  private static final String AMPERSAND_SEPARATED_STRING = "%s&%s&%s";

  /**
   * {@inheritDoc}
   */
  public String extract(OAuthRequest request)
  {
    checkPreconditions(request);
    String verb = HttpEncoder.encode(request.getVerb().name());
    String url = HttpEncoder.encode(request.getSanitizedUrl());
    String params = getSortedAndEncodedParams(request);
    return String.format(AMPERSAND_SEPARATED_STRING, verb, url, params);
  }

  private String getSortedAndEncodedParams(OAuthRequest request)
  {
    ParameterList params = new ParameterList();
    params.addAll(request.getQueryStringParams());
    params.addAll(request.getBodyParams());
    params.addAll(new ParameterList(request.getOauthParameters()));
    return params.sort().asOauthBaseString();
  }

  private void checkPreconditions(OAuthRequest request)
  {
    Preconditions.checkNotNull(request, "Cannot extract base string from null object");

    if (request.getOauthParameters() == null || request.getOauthParameters().size() <= 0)
    {
      throw new OAuthParametersMissingException(request);
    }
  }
}
