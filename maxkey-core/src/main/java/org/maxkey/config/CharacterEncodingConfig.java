/**
 * 
 */
package org.maxkey.config;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 字符集转换及转换配置
 * @author Crystal.Sea
 *
 */
@Configuration
@PropertySource("classpath:/config/applicationConfig.properties")
public class CharacterEncodingConfig {
	
	/**
	 * 源字符集
	 */
	@Value("${config.characterencoding.charset.from}")
	String fromCharSet;
	
	/**
	 * 目标字符集
	 */
	@Value("${config.characterencoding.charset.to}")
	String toCharSet;
	
	/**
	 * 转换标志
	 */
	@Value("${config.characterencoding.encoding}")
	boolean encoding	=	false;

	
	public CharacterEncodingConfig() {
		
	}

	/**
	 * @return the fromCharSet
	 */
	public String getFromCharSet() {
		return fromCharSet;
	}

	/**
	 * @param fromCharSet the fromCharSet to set
	 */
	public void setFromCharSet(String fromCharSet) {
		this.fromCharSet = fromCharSet;
	}

	/**
	 * @return the toCharSet
	 */
	public String getToCharSet() {
		return toCharSet;
	}

	/**
	 * @param toCharSet the toCharSet to set
	 */
	public void setToCharSet(String toCharSet) {
		this.toCharSet = toCharSet;
	}

	/**
	 * @return the encoding
	 */
	public boolean isEncoding() {
		return encoding;
	}

	/**
	 * @param encoding the encoding to set
	 */
	public void setEncoding(boolean encoding) {
		this.encoding = encoding;
	}
	
	/**
	 * 字符集转换
	 * @param encodingString 源字符串
	 * @return encoded目标字符串
	 */
	public String encoding(String encodingString){
		if(!this.encoding||encodingString==null) {
			return encodingString;
		}
		
		try {
			return new String(encodingString.getBytes(this.fromCharSet),this.toCharSet);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
