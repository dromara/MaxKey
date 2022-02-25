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
 

package org.maxkey.web.contorller;

import com.google.code.kaptcha.Producer;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * ImageCaptchaEndpoint  Producer captcha.
 * @author Crystal.Sea
 *
 */
@Controller
public class ImageCaptchaEndpoint {
    private static final Logger _logger = LoggerFactory.getLogger(ImageCaptchaEndpoint.class);

    public static final	String IMAGE_GIF 			= "image/gif";
    
    public static final	String KAPTCHA_SESSION_KEY 	= "kaptcha_session_key";
    
    @Autowired
    private Producer captchaProducer;

    /**
     * captcha image Producer.
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    @RequestMapping(value = "/captcha")
    public void captchaHandleRequest(HttpServletRequest  request, 
    								 HttpServletResponse response, 
    								 @RequestParam(value="captcha",required=false,defaultValue="text") String captchaType) {
        try {
        	
            String kaptchaText = captchaProducer.createText();
            if (captchaType.equalsIgnoreCase("Arithmetic")) {
                Integer intParamA = Integer.valueOf(kaptchaText.substring(0, 1));
                Integer intParamB = Integer.valueOf(kaptchaText.substring(1, 2));
                Integer calculateValue = 0;
                if ((intParamA > intParamB) && ((intParamA + intParamB) % 5 > 3)) {
                    calculateValue = intParamA - intParamB;
                    kaptchaText = intParamA + "-" + intParamB + "=?";
                } else {
                    calculateValue = intParamA + intParamB;
                    kaptchaText = intParamA + "+" + intParamB + "=?";
                }
                _logger.trace("Sesssion id " + request.getSession().getId() 
                        + " , Arithmetic calculate Value is " + calculateValue);
                request.getSession().setAttribute(
                        KAPTCHA_SESSION_KEY, calculateValue + "");
            } else {
                // store the text in the session
                request.getSession().setAttribute(KAPTCHA_SESSION_KEY, kaptchaText);
            }
            _logger.trace("Sesssion id " + request.getSession().getId() 
                                + " , Captcha Text is " + kaptchaText);
           
            // create the image with the text
            BufferedImage bufferedImage = captchaProducer.createImage(kaptchaText);
            producerImage(request,response,bufferedImage);
        } catch (Exception e) {
            _logger.error("captcha Producer Error " + e.getMessage());
        }
    }

    /**
     * producerImage.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param bufferedImage BufferedImage
     * @throws IOException error
     */
    public static void producerImage(HttpServletRequest request, 
                              HttpServletResponse response,
                              BufferedImage bufferedImage) throws IOException {
        // Set to expire far in the past.
        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        // return a jpeg/gif
        response.setContentType(IMAGE_GIF);
        _logger.trace("create the image");
        // create the image
        if (bufferedImage != null) {
            ServletOutputStream out = response.getOutputStream();
            // write the data out
            ImageIO.write(bufferedImage, "gif", out);
            try {
                out.flush();
            } finally {
                out.close();
            }
        }
    }

	public void setCaptchaProducer(Producer captchaProducer) {
        this.captchaProducer = captchaProducer;
    }


}
