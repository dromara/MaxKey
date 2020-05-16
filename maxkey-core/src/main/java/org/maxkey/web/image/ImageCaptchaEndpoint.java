package org.maxkey.web.image;

import com.google.code.kaptcha.Producer;
import java.awt.image.BufferedImage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.maxkey.web.WebConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * ImageCaptchaEndpoint  Producer captcha.
 * @author Crystal.Sea
 *
 */
@Controller
public class ImageCaptchaEndpoint extends AbstractImageEndpoint {
    private static final Logger _logger = LoggerFactory.getLogger(ImageCaptchaEndpoint.class);

    @Autowired
    private Producer captchaProducer;

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

 

    public void setCaptchaProducer(Producer captchaProducer) {
        this.captchaProducer = captchaProducer;
    }


}
