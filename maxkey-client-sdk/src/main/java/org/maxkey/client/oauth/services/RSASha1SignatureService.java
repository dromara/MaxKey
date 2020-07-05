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

import java.security.*;

import org.maxkey.client.oauth.exceptions.*;

/**
 * A signature service that uses the RSA-SHA1 algorithm.
 */
public class RSASha1SignatureService implements SignatureService
{
  private static final String METHOD = "RSA-SHA1";
  private static final String RSA_SHA1 = "SHA1withRSA";
  private static final String UTF8 = "UTF-8";

  private PrivateKey privateKey;

  public RSASha1SignatureService(PrivateKey privateKey)
  {
    this.privateKey = privateKey;
  }

  /**
   * {@inheritDoc}
   */
  public String getSignature(String baseString, String apiSecret, String tokenSecret)
  {
    try
    {
      Signature signature = Signature.getInstance(RSA_SHA1);
      signature.initSign(privateKey);
      signature.update(baseString.getBytes(UTF8));
      return bytesToBase64String(signature);
    }
    catch (Exception e)
    {
      throw new OAuthSignatureException(baseString, e);
    }
  }

  private String bytesToBase64String(Signature signature) throws SignatureException
  {
    return Base64Encoder.getInstance().encode(signature.sign());
  }

  /**
   * {@inheritDoc}
   */
  public String getSignatureMethod()
  {
    return METHOD;
  }
}
