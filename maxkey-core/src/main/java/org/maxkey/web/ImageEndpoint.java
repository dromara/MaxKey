package org.maxkey.web;

import com.google.code.kaptcha.Producer;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.maxkey.config.ApplicationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * ImageEndpoint  Producer Image and captcha.
 * @author Crystal.Sea
 *
 */
@Controller
public class ImageEndpoint {
    private static final Logger _logger = LoggerFactory.getLogger(ImageEndpoint.class);

    @Autowired
    private Producer captchaProducer;
    
    @Autowired
    @Qualifier("applicationConfig")
    ApplicationConfig applicationConfig;

    /**
     * captcha image Producer.
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    @RequestMapping(value = "/captcha")
    public void captchaHandleRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
           
            String kaptchaText = captchaProducer.createText();
            if (applicationConfig.getLoginConfig().getCaptchaType()
                                        .equalsIgnoreCase("Arithmetic")) {
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
                        WebConstants.KAPTCHA_SESSION_KEY, calculateValue + "");
            } else {
                // store the text in the session
                request.getSession().setAttribute(WebConstants.KAPTCHA_SESSION_KEY, kaptchaText);
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
     * Session Image Producer.
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */

    @RequestMapping("/image/{id}")
    public void imageHandleRequest(HttpServletRequest request, HttpServletResponse response,
            @PathVariable("id") String id) {
        try {
            // get session image bytes
            byte[] image = (byte[]) request.getSession().getAttribute(id);
            producerImage(request,response,byte2BufferedImage(image));
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
        response.setContentType("image/gif");
        
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
    
    /**
     * byte2BufferedImage.
     * @param imageByte bytes
     * @return
     */
    public static BufferedImage byte2BufferedImage(byte[] imageByte) {
        try {
            InputStream in = new ByteArrayInputStream(imageByte);
            BufferedImage bufferedImage = ImageIO.read(in);
            return bufferedImage;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * bufferedImage2Byte.
     * @param bufferedImage  BufferedImage
     * @return
     */
    public static byte[] bufferedImage2Byte(BufferedImage bufferedImage) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "gif", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setCaptchaProducer(Producer captchaProducer) {
        this.captchaProducer = captchaProducer;
    }

    public void setApplicationConfig(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

}
