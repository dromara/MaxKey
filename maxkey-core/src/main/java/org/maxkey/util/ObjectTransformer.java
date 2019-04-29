/**
 * 
 */
package org.maxkey.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.maxkey.crypto.HexUtils;

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
	 * 
	 */
	public ObjectTransformer() {
	}
	
	/**
	 * Object 2 ByteArray
	 * @param state is Object
	 * @return ByteArray
	 */
	public static byte[] object2Bytes(Object state) {
		ObjectOutputStream oos = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
			oos = new ObjectOutputStream(baos);
			oos.writeObject(state);
			oos.flush();
			return baos.toByteArray();
		}
		catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		finally {
			if (oos != null) {
				try {
					oos.close();
				}
				catch (IOException e) {
					// eat it
				}
			}
		}
	}

	/**
	 * ByteArray 2 Object
	 * @param byteArray
	 * @return Object
	 */
	public static <T> T bytes2Object(byte[] byteArray) {
		ObjectInputStream oip = null;
		try {
			oip = new ObjectInputStream(new ByteArrayInputStream(byteArray));
			@SuppressWarnings("unchecked")
			T result = (T) oip.readObject();
			return result;
		}
		catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(e);
		}
		finally {
			if (oip != null) {
				try {
					oip.close();
				}
				catch (IOException e) {
					// eat it
				}
			}
		}
	}

	
	/**
	 * serialize Serializable Object 2 HEX String
	 * @param Serializable Object
	 * @return String
	 */
	public static final String serialize(Serializable s){ 
	    return HexUtils.hex2String(object2Bytes(s)); 
	} 
	
	/**
	 * deserialize 2 Object
	 * @param HEX String
	 * @return Object
	 */
	public static final <T> T deserialize(String hex) { 
	    return bytes2Object(HexUtils.hex2Bytes(hex)); 
	} 
	

	
	
	
}
