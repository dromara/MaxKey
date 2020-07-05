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

import java.util.*;

import org.maxkey.client.oauth.exceptions.*;
import org.maxkey.client.oauth.model.*;
import org.maxkey.client.utils.HttpEncoder;
import org.maxkey.client.utils.Preconditions;

/**
 * Default implementation of {@link HeaderExtractor}. Conforms to OAuth 1.0a
 * 
 * @author Pablo Fernandez
 *
 */
public class HeaderExtractorImpl implements HeaderExtractor
{
  private static final String PARAM_SEPARATOR = ", ";
  private static final String PREAMBLE = "OAuth ";
  public static final int ESTIMATED_PARAM_LENGTH = 20;

  /**
   * {@inheritDoc}
   */
  public String extract(OAuthRequest request)
  {
    checkPreconditions(request);
    Map<String, String> parameters = request.getOauthParameters();
    StringBuilder header = new StringBuilder(parameters.size() * ESTIMATED_PARAM_LENGTH);
    header.append(PREAMBLE);
    for (Map.Entry<String, String> entry : parameters.entrySet())
    {
      if(header.length() > PREAMBLE.length())
      { 
        header.append(PARAM_SEPARATOR);
      }
      header.append(String.format("%s=\"%s\"", entry.getKey(), HttpEncoder.encode(entry.getValue())));
    }
    return header.toString();
  }

  private void checkPreconditions(OAuthRequest request)
  {
    Preconditions.checkNotNull(request, "Cannot extract a header from a null object");

    if (request.getOauthParameters() == null || request.getOauthParameters().size() <= 0)
    {
      throw new OAuthParametersMissingException(request);
    }
  }

}
