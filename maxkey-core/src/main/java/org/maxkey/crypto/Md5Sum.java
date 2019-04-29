/**
 * 
 */
package org.maxkey.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

/**
 * 类似linux或Unix上，md5sum是用来计算和校验文件报文摘要的工具程序
 * @author Crystal.Sea
 *
 */
public class Md5Sum {

	/**
	 * 
	 */
	public Md5Sum() {

	}

	public static String produce(File file) throws FileNotFoundException {
		String md5value = null;
		FileInputStream in = new FileInputStream(file);
		try {
			MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(byteBuffer);
			byte[] bCipher=messageDigest.digest();
			md5value=HexUtils.bytes2HexString(bCipher);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		md5value+=" *"+file.getName();
		return md5value;
	}
	
	public static boolean check(File file,String md5String) throws FileNotFoundException{
		
		String md5value	=	produce( file);
		
		return md5value.equals(md5String)?true:false;
	}
}
