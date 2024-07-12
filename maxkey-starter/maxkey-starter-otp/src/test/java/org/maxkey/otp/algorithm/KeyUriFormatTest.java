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

import org.dromara.maxkey.password.onetimepwd.algorithm.OtpKeyUriFormat;
import org.dromara.maxkey.util.QRCode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class KeyUriFormatTest {
	public static void main(String[] args) { 
        try {  
        	OtpKeyUriFormat kuf=new OtpKeyUriFormat(OtpKeyUriFormat.Types.TOTP,
        			"GIWVWOL7EI5WLVZPDMROEPSTFBEVO77Q",
        			"connsec.com");
        	kuf.setPeriod(60);
            String path = "D:\\totp.png";  
            BitMatrix byteMatrix;  
            byteMatrix = new MultiFormatWriter().encode(new String(kuf.format("shiming").getBytes("GBK"),"iso-8859-1"),  
                    BarcodeFormat.QR_CODE, 300, 300);  
            File file = new File(path);  
              
            QRCode.writeToPath(byteMatrix, "png", file);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    } 
}
