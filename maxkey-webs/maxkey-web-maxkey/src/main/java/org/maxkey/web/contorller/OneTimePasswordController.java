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

import java.awt.image.BufferedImage;
import java.util.Base64;
import java.util.HashMap;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.maxkey.authn.annotation.CurrentUser;
import org.maxkey.crypto.Base32Utils;
import org.maxkey.crypto.password.PasswordReciprocal;
import org.maxkey.entity.Message;
import org.maxkey.entity.UserInfo;
import org.maxkey.password.onetimepwd.algorithm.OtpKeyUriFormat;
import org.maxkey.password.onetimepwd.algorithm.OtpSecret;
import org.maxkey.persistence.service.UserInfoService;
import org.maxkey.util.RQCodeUtils;
import org.maxkey.web.image.ImageEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * .
 * @author Crystal.Sea
 *
 */
@Controller
@RequestMapping(value  =  { "/config" })
public class OneTimePasswordController {
    static final  Logger _logger  =  LoggerFactory.getLogger(OneTimePasswordController.class);

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;

    @Autowired
    @Qualifier("otpKeyUriFormat")
    OtpKeyUriFormat otpKeyUriFormat;

    @RequestMapping(value = {"/timebased"})
    @ResponseBody
    public ResponseEntity<?> timebased(@RequestParam String generate,@CurrentUser UserInfo currentUser) {
        HashMap<String,Object >timebased =new HashMap<String,Object >();
        
        generate(generate,currentUser);
        
        String sharedSecret = 
        		PasswordReciprocal.getInstance().decoder(currentUser.getSharedSecret());
        
        otpKeyUriFormat.setSecret(sharedSecret);
        String otpauth = otpKeyUriFormat.format(currentUser.getUsername());
        byte[] byteSharedSecret = Base32Utils.decode(sharedSecret);
        String hexSharedSecret = Hex.encodeHexString(byteSharedSecret);
        
        timebased.put("displayName", currentUser.getDisplayName());
        timebased.put("username", currentUser.getUsername());
        timebased.put("digits", otpKeyUriFormat.getDigits());
        timebased.put("period", otpKeyUriFormat.getPeriod());
        timebased.put("sharedSecret", sharedSecret);
        timebased.put("hexSharedSecret", hexSharedSecret);
        timebased.put("rqCode", genRqCode(otpauth));
        return new Message<HashMap<String,Object >>(timebased).buildResponse();
    }

    public void generate(String generate,@CurrentUser UserInfo currentUser) {
    	if((StringUtils.isNotBlank(generate)
        		&& generate.equalsIgnoreCase("YES"))
        		||StringUtils.isBlank(currentUser.getSharedSecret())) {
    		
        	byte[] byteSharedSecret = OtpSecret.generate(otpKeyUriFormat.getCrypto());
            String sharedSecret = Base32Utils.encode(byteSharedSecret);
            sharedSecret = PasswordReciprocal.getInstance().encode(sharedSecret);
            currentUser.setSharedSecret(sharedSecret);
            userInfoService.updateSharedSecret(currentUser);
            
        }
    }

    public  String genRqCode(String otpauth) {
        BufferedImage bufferedImage  =  RQCodeUtils.write2BufferedImage(otpauth, "gif", 300, 300);
        byte[] imageByte = ImageEndpoint.bufferedImage2Byte(bufferedImage);
        return "data:image/png;base64," + Base64.getEncoder().encodeToString(imageByte);
    }
}
