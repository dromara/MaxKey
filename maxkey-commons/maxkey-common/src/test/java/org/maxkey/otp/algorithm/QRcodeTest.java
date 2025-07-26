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
 

package org.maxkey.otp.algorithm;

import java.io.File;

import org.dromara.maxkey.util.QRCode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;


/**
 * VCARD
 * 
 * 
 * BEGIN:VCARD
		VERSION:3.0
		N:Gump;Forrest;;Mr.
		FN:Forrest Gump
		ORG:Bubba Gump Shrimp Co.
		TITLE:Shrimp Man
		PHOTO;VALUE=URL;TYPE=GIF:http://www.example.com/dir_photos/my_photo.gif
		TEL;TYPE=WORK,VOICE:(111) 555-12121
		TEL;TYPE=HOME,VOICE:(404) 555-1212
		ADR;TYPE=WORK:;;100 Waters Edge;Baytown;LA;30314;United States of America
		LABEL;TYPE=WORK:100 Waters Edge\nBaytown, LA 30314\nUnited States of America
		ADR;TYPE=HOME:;;42 Plantation St.;Baytown;LA;30314;United States of America
		LABEL;TYPE=HOME:42 Plantation St.\nBaytown, LA 30314\nUnited States of America
		EMAIL;TYPE=PREF,INTERNET:forrestgump@example.com
		REV:2008-04-24T19:52:43Z
	END:VCARD



	BEGIN:VCARD
		VERSION:4.0
		N:Gump;Forrest;;;
		FN:Forrest Gump
		ORG:Bubba Gump Shrimp Co.
		TITLE:Shrimp Man
		PHOTO;MEDIATYPE=image/gif:http://www.example.com/dir_photos/my_photo.gif
		TEL;TYPE=work,voice;VALUE=uri:tel:+1-111-555-1212
		TEL;TYPE=home,voice;VALUE=uri:tel:+1-404-555-1212
		ADR;TYPE=work;LABEL="100 Waters Edge\nBaytown, LA 30314\nUnited States of America"
		:;;100 Waters Edge;Baytown;LA;30314;United States of America
		ADR;TYPE=home;LABEL="42 Plantation St.\nBaytown, LA 30314\nUnited States of America"
		:;;42 Plantation St.;Baytown;LA;30314;United States of America
		EMAIL:forrestgump@example.com
		REV:20080424T195243Z
	END:VCARD
 */
public class QRcodeTest {

	  // 编码  
	public static void main(String[] args) { 
        try {  
        	
        	 String str = """
	        	 		BEGIN:VCARD
	        			    VERSION:3.0
	        			    N:石鸣
	        			    EMAIL:shimh@qq.com
	        			    TEL:15618726256
	        			    TEL;CELL:12345678912
	        			    ADR:上海
	        			    ORG:
	        			    Connsec
	        			    TITLE:技术总监
	        			    URL:http://blog.csdn.net/lidew521
	        			    NOTE:呼呼测试下吧。。。
	        			 END:VCARD
        	 		""";
        	 
        	 String str1 = """
        	 			BEGIN:VCARD
				        	 VERSION:3.0
				        	 N:Gump;Forrest;;Mr.
				        	 ORG:Bubba Gump Shrimp Co.
				        	 TITLE:Shrimp Man
				        	 TEL;TYPE=WORK,VOICE:(111) 555-12121
				        	 ADR;TYPE=WORK:;;100 Waters Edge;Baytown;LA;30314;United States of America
				        	 EMAIL;TYPE=PREF,INTERNET:forrestgump@example.com
				        	 URL:http://www.johndoe.com
				        	 GENDER:F
				        	 REV:2008-04-24T19:52:43Z
				        END:VCARD
        	 		""";
        	 
        	 System.out.println(str);
            //String str = "CN:男;COP:公司;ZW:职务";// 二维码内容  
            String path = "D:\\hwy.png";  
            BitMatrix byteMatrix;  
            byteMatrix = new MultiFormatWriter().encode(new String(str1.getBytes("UTF-8"),"iso-8859-1"),  BarcodeFormat.QR_CODE, 300, 300);  
            File file = new File(path);  
              
            QRCode.writeToPath(byteMatrix, "png", file);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    
    
}
