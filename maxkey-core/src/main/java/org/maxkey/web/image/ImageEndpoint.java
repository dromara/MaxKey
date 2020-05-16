package org.maxkey.web.image;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * ImageEndpoint  Producer Image and captcha.
 * @author Crystal.Sea
 *
 */
@Controller
public class ImageEndpoint extends AbstractImageEndpoint {
    private static final Logger _logger = LoggerFactory.getLogger(ImageEndpoint.class);

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

}
