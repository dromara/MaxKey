/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.dromara.maxkey.web.contorller;

import com.google.code.kaptcha.Producer;
import java.awt.image.BufferedImage;
import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.authn.jwt.AuthTokenService;
import org.dromara.maxkey.crypto.Base64Utils;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.persistence.cache.MomentaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * ImageCaptchaEndpoint  Producer captcha.
 * @author Crystal.Sea
 *
 */
@RestController
public class ImageCaptchaEndpoint {
    private static final Logger _logger = LoggerFactory.getLogger(ImageCaptchaEndpoint.class);
    
    @Autowired
    Producer captchaProducer;
    
    @Autowired 
	MomentaryService momentaryService;
    
    @Autowired
	AuthTokenService authTokenService;

    /**
     * captcha image Producer.
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    @GetMapping(value={"/captcha"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public  Message<ImageCaptcha> captchaHandleRequest( 
    			@RequestParam(value="captcha",required=false,defaultValue="text") String captchaType,
    			@RequestParam(value="state",required=false,defaultValue="state") String state) {
        try {
            String kaptchaText = captchaProducer.createText();
            String kaptchaValue = kaptchaText;
            if (captchaType.equalsIgnoreCase("Arithmetic")) {
            	//去除0，增加计算复杂度
            	kaptchaText = kaptchaText.replace("0", "");
                Integer minuend = Integer.valueOf(kaptchaText.substring(0, 1));
                Integer subtrahend = Integer.valueOf(kaptchaText.substring(1, 2));
                if (minuend - subtrahend > 0) {
                	kaptchaValue = (minuend - subtrahend ) + "";
                    kaptchaText = minuend + "-" + subtrahend + "=?";
                } else {
                	kaptchaValue = (minuend + subtrahend) + "";
                    kaptchaText = minuend + "+" + subtrahend + "=?";
                }
            }
            String kaptchaKey = "";
            if(StringUtils.isNotBlank(state) && !state.equalsIgnoreCase("state")) {
            	//just validate state Token
            	if(!authTokenService.validateJwtToken(state)) {
            		return new Message<>(Message.FAIL,"JwtToken is not Validate  ");
            	}
            }else {
            	state = authTokenService.genRandomJwt();
            }
            kaptchaKey = authTokenService.resolveJWTID(state);
            _logger.trace("kaptchaKey {} , Captcha Text is {}" ,kaptchaKey, kaptchaValue);
           
            momentaryService.put("", kaptchaKey, kaptchaValue);
            // create the image with the text
            BufferedImage bufferedImage = captchaProducer.createImage(kaptchaText);
			String b64Image = Base64Utils.encodeImage(bufferedImage);
           
            _logger.trace("b64Image {}" , b64Image);
            
            return new Message<>(new ImageCaptcha(state,b64Image));
        } catch (Exception e) {
            _logger.error("captcha Producer Error" , e);
        }
        return new Message<>(Message.FAIL);
    }

	public void setCaptchaProducer(Producer captchaProducer) {
        this.captchaProducer = captchaProducer;
    }


}
