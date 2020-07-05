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
