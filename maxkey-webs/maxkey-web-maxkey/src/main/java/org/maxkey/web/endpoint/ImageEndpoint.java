package org.maxkey.web.endpoint;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.maxkey.web.WebConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.code.kaptcha.Producer;

/**
 * @author Crystal.Sea
 *
 */
@Controller
public class ImageEndpoint {
	private static final Logger _logger = LoggerFactory.getLogger(ImageEndpoint.class);
	
	@Autowired
 	private Producer captchaProducer;

	/**
	 * captcha image Producer
	 * @param request
	 * @param response
	 */
 	@RequestMapping(value = "/captcha")
    public void captchaHandleRequest(HttpServletRequest request,HttpServletResponse response){
 		try{
 			// Set to expire far in the past.
            response.setDateHeader("Expires", 0);
            // Set standard HTTP/1.1 no-cache headers.
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            // Set standard HTTP/1.0 no-cache header.
            response.setHeader("Pragma", "no-cache");
            // return a jpeg
            response.setContentType("image/jpeg");
            // create the text for the image
            String capText = captchaProducer.createText();
            _logger.debug("Sesssion id " + request.getSession().getId() + " , Captcha Text is " + capText);
            // store the text in the session
            request.getSession().setAttribute(WebConstants.KAPTCHA_SESSION_KEY, capText);
            // create the image with the text
            BufferedImage bi = captchaProducer.createImage(capText);
            ServletOutputStream out = response.getOutputStream();
            // write the data out
            ImageIO.write(bi, "jpg", out);
    
            out.flush();
            out.close();
		}catch(Exception e) {
			_logger.error("captcha Producer Error " + e.getMessage());
        }
    }
 	
	/**
	 * image Producer
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	@RequestMapping("/image/{id}")
    public void imageHandleRequest(HttpServletRequest request,HttpServletResponse response,@PathVariable("id") String id) throws Exception {
            // Set to expire far in the past.
            response.setDateHeader("Expires", 0);
            // Set standard HTTP/1.1 no-cache headers.
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            // Set standard HTTP/1.0 no-cache header.
            response.setHeader("Pragma", "no-cache");
            // return a jpeg/gif
            response.setContentType("image/gif");
            // create the text for the image
            byte[]image=(byte[]) request.getSession().getAttribute(id);
            //request.getSession().removeAttribute(id);
            // create the image with the text
            if(image!=null){
	            ServletOutputStream out = response.getOutputStream();
	            // write the data out
	            ImageIO.write(byte2BufferedImage(image), "gif", out);
	            try{
                    out.flush();
	            }finally{
	                    out.close();
	            }
            }
    }
	
	public static BufferedImage byte2BufferedImage(byte[]imageByte){
        try {
        	 InputStream in = new ByteArrayInputStream(imageByte);
        	 BufferedImage bufferedImage = ImageIO.read(in);
        	 return bufferedImage;
		} catch (IOException e) {
			e.printStackTrace();
		}
         return null;
	}
	
	public static byte[] bufferedImage2Byte(BufferedImage  bufferedImage  ){
        try {
        	ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        	ImageIO.write(bufferedImage,"gif",byteArrayOutputStream);
        	return byteArrayOutputStream.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
         return null;
	}

	public Producer getCaptchaProducer() {
		return captchaProducer;
	}

	public void setCaptchaProducer(Producer captchaProducer) {
		this.captchaProducer = captchaProducer;
	}
	
	
}
