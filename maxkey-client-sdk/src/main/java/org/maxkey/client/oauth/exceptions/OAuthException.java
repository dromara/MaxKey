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
 

package org.maxkey.client.oauth.exceptions;

/**
 * Default scribe exception. 
 * Represents a problem in the OAuth signing process
 * 
 * @author Pablo Fernandez
 */
public class OAuthException extends RuntimeException
{

  /**
   * Default constructor 
   * @param message message explaining what went wrong
   * @param e original exception
   */
  public OAuthException(String message, Exception e)
  {
    super(message, e);
  }

  /**
   * No-exception constructor. Used when there is no original exception
   *  
   * @param message message explaining what went wrong
   */
  public OAuthException(String message)
  {
    super(message, null);
  }

  private static final long serialVersionUID = 1L;
}
