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


package org.dromara.maxkey.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class QRCodeUtils {


	public static void write2File(String path,String rqCodeText,String format,int width, int height ){
		try {
			BitMatrix byteMatrix=genRQCode(rqCodeText,width,height);

	        File file = new File(path);

	        QRCode.writeToPath(byteMatrix, format, file);
		} catch (Exception e) {
            e.printStackTrace();
        }
	}

	public static BufferedImage write2BufferedImage(String rqCodeText,String format,int width, int height){
		try {
			BitMatrix byteMatrix=genRQCode(rqCodeText,width,height);

	        return QRCode.toBufferedImage(byteMatrix);
		} catch (Exception e) {
            e.printStackTrace();
        }
		return null;
	}

	public static void write2OutputStream(OutputStream stream,String rqCodeText,String format,int width, int height ){
		try {
			BitMatrix byteMatrix=genRQCode(rqCodeText,width,height);

	        QRCode.writeToStream(byteMatrix, format, stream);
		} catch (Exception e) {
            e.printStackTrace();
        }
	}


	public static BitMatrix genRQCode(String rqCodeText,int width, int height){
		if(width==0){
			width=200;
		}
		if(height==0){
			height=200;
		}
		try {
			return  new MultiFormatWriter().encode(
	        		rqCodeText,
	        		BarcodeFormat.QR_CODE,
	        		width,
	        		height);
		} catch (Exception e) {
            e.printStackTrace();
        }
		return null;
	}

}
