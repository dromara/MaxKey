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
 

package org.maxkey.client.oauth.services;

/**
 * Signs a base string, returning the OAuth signature
 * 
 * @author Pablo Fernandez
 *
 */
public interface SignatureService
{
  /**
   * Returns the signature
   * 
   * @param baseString url-encoded string to sign
   * @param apiSecret api secret for your app
   * @param tokenSecret token secret (empty string for the request token step)
   * 
   * @return signature
   */
  public String getSignature(String baseString, String apiSecret, String tokenSecret);

  /**
   * Returns the signature method/algorithm
   * 
   * @return
   */
  public String getSignatureMethod();
}
