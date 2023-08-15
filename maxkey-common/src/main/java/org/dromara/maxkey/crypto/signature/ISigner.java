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
 

package org.dromara.maxkey.crypto.signature;



/**
 * Digital signature
 * interface for RSA & DSA
 * sign with private key ,then verify use public key
 * scenario
 *   1. A genKeyPair include private key & public key
 *   2. A give public key to B
 *   3. A sign a data with private key to signedData
 *   4. A send data and signedData to B
 *   5. B received data & signedData from A
 *   6. B verify data & signedData use public key
 * 
 * @author Crystal.Sea
 *
 */
public  interface  ISigner {
	
	public byte[] sign(byte[] data,byte[] privateKey)throws Exception;
	
	public String signB64(String data,String privateKey)throws Exception;
	
	public boolean verify(byte[] data,byte[] publicKey,byte[] sign)throws Exception;
	
	public boolean verifyB64(String data,String publicKey,String sign)throws Exception;
	

}
