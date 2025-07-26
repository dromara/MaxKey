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
 

/**
 * 
 */
package org.dromara.maxkey.util;

import java.io.Serializable;

import org.dromara.maxkey.crypto.HexUtils;

/**
 * ObjectTransformer<br>
 * serialize & deserialize<br>
 * object serialize to ByteArray,and ByteArray deserialize to object<br>
 * object serialize to HEX String,and HEX String  deserialize to object<br>
 * @version 2.0
 * @since 1.6
 * @author Crystal.Sea
 */

public class ObjectTransformer {
	
	/**
	 * serialize Serializable Object 2 HEX String
	 * @param Serializable Object
	 * @return String
	 */
	public static final String serialize(Serializable s){ 
	    return HexUtils.hex2String(SerializationUtils.serialize(s)); 
	} 
	
	/**
	 * deserialize 2 Object
	 * @param HEX String
	 * @return Object
	 */
	public static final <T> T deserialize(String hex) { 
	    return SerializationUtils.deserialize(HexUtils.hex2Bytes(hex)); 
	} 
	

	
	
	
}
