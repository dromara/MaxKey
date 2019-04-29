package com.connsec.client.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class URLTest {

	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		//String encoderString="/bi/QvAJAXZfc/opendoc.htm?document=康缘药业经营决策分析   .qvw&host=QVS@bi-server51";
		
		String encoderString="康缘药业经营决策分析   .qvw";
		encoderString=URLEncoder.encode(encoderString,"UTF-8");
		System.out.println(encoderString);
		
		String decoderString="/bi/QvAJAXZfc/opendoc.htm?document=%E5%BA%B7%E7%BC%98%E8%8D%AF%E4%B8%9A%E7%BB%8F%E8%90%A5%E5%86%B3%E7%AD%96%E5%88%86%E6%9E%90.qvw&host=QVS%40bi-server51";
		decoderString=URLDecoder.decode(decoderString,"UTF-8");
		System.out.println(decoderString);
		
	}

}
