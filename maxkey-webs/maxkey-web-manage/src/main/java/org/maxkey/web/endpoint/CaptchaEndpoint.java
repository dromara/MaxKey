package org.maxkey.web.endpoint;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.maxkey.web.WebConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Producer;

/**
 * @author Crystal.Sea
 *
 */
@Controller
@RequestMapping(value = "/captcha")
public class CaptchaEndpoint {
	private static final Logger _logger = LoggerFactory.getLogger(CaptchaEndpoint.class);
	
 	@Autowired
 	private Producer captchaProducer;
 	
	/**
	 * captcha image Producer
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	@RequestMapping
    public ModelAndView captchaHandleRequest(HttpServletRequest request,HttpServletResponse response) throws Exception {
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
            _logger.debug("Captcha Text : "+capText);
            // store the text in the session
            request.getSession().setAttribute(WebConstants.KAPTCHA_SESSION_KEY, capText);
            // create the image with the text
            BufferedImage bi = captchaProducer.createImage(capText);
            ServletOutputStream out = response.getOutputStream();
            // write the data out
            ImageIO.write(bi, "jpg", out);
            try{
                    out.flush();
            }finally{
                    out.close();
            }
            return null;
    }
}
