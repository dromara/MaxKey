package org.maxkey.otp.algorithm;

import java.io.File;

import org.maxkey.util.QRCode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class RQcodeTest {

	/*
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
	  // 编码  
	public static void main(String[] args) { 
        try {  
        	
        	 String str = "BEGIN:VCARD\n" +
        			    "VERSION:3.0\n" +
        			    "N:石明海\n" +
        			    "EMAIL:shimh@qq.com\n" +
        			    "TEL:15618726256\n" +
        			    "TEL;CELL:12345678912" +
        			    "ADR:上海\n" +
        			    "ORG:" +
        			    "Connsec\n" +
        			    "TITLE:技术总监\n" +
        			    //"URL:http://blog.csdn.net/lidew521\n" +
        			    //"NOTE:呼呼测试下吧。。。\n" +
        			    "END:VCARD";
        	 
        	 String str1 = "BEGIN:VCARD\n" +
        	 "VERSION:3.0\n" +
        	 "N:Gump;Forrest;;Mr.\n" +
        	 "ORG:Bubba Gump Shrimp Co.\n" +
        	 "TITLE:Shrimp Man\n" +
        	 "TEL;TYPE=WORK,VOICE:(111) 555-12121\n" +
        	 "ADR;TYPE=WORK:;;100 Waters Edge;Baytown;LA;30314;United States of America\n" +
        	 "EMAIL;TYPE=PREF,INTERNET:forrestgump@example.com\n" +
        	 "URL:http://www.johndoe.com\n" +
        	 "GENDER:F\n"+
        	 "REV:2008-04-24T19:52:43Z\n" +
        	 "END:VCARD\n" ;
        	 
            //String str = "CN:男;COP:公司;ZW:职务";// 二维码内容  
            String path = "D:\\hwy.png";  
            BitMatrix byteMatrix;  
            byteMatrix = new MultiFormatWriter().encode(new String(str1.getBytes("UTF-8"),"iso-8859-1"),  
                    BarcodeFormat.QR_CODE, 300, 300);  
            File file = new File(path);  
              
            QRCode.writeToPath(byteMatrix, "png", file);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    
    
}
