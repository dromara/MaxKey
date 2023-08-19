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

import java.awt.image.BufferedImage;
import java.util.HashMap;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.authn.annotation.CurrentUser;
import org.dromara.maxkey.crypto.Base32Utils;
import org.dromara.maxkey.crypto.Base64Utils;
import org.dromara.maxkey.crypto.password.PasswordReciprocal;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.UserInfo;
import org.dromara.maxkey.password.onetimepwd.algorithm.OtpKeyUriFormat;
import org.dromara.maxkey.password.onetimepwd.algorithm.OtpSecret;
import org.dromara.maxkey.password.onetimepwd.impl.TimeBasedOtpAuthn;
import org.dromara.maxkey.persistence.service.UserInfoService;
import org.dromara.maxkey.util.RQCodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    static final  Logger logger  =  LoggerFactory.getLogger(OneTimePasswordController.class);

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    OtpKeyUriFormat otpKeyUriFormat;

    @Autowired
    private TimeBasedOtpAuthn timeBasedOtpAuthn;

    @RequestMapping(value = {"/timebased"})
    @ResponseBody
    public ResponseEntity<?> timebased(
    			@RequestParam String generate,@CurrentUser UserInfo currentUser) {
        HashMap<String,Object >timebased =new HashMap<String,Object >();
        
        generate(generate,currentUser);
        
        String sharedSecret = 
        		PasswordReciprocal.getInstance().decoder(currentUser.getSharedSecret());
        
        otpKeyUriFormat.setSecret(sharedSecret);
        String otpauth = otpKeyUriFormat.format(currentUser.getUsername());
        byte[] byteSharedSecret = Base32Utils.decode(sharedSecret);
        String hexSharedSecret = Hex.encodeHexString(byteSharedSecret);
        BufferedImage bufferedImage  =  RQCodeUtils.write2BufferedImage(otpauth, "gif", 300, 300);
    	String rqCode = Base64Utils.encodeImage(bufferedImage);
        
        timebased.put("displayName", currentUser.getDisplayName());
        timebased.put("username", currentUser.getUsername());
        timebased.put("digits", otpKeyUriFormat.getDigits());
        timebased.put("period", otpKeyUriFormat.getPeriod());
        timebased.put("sharedSecret", sharedSecret);
        timebased.put("hexSharedSecret", hexSharedSecret);
        timebased.put("rqCode", rqCode);
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

    @RequestMapping("/verify")
    public ResponseEntity<?> verify(@RequestParam("otp") String otp, @CurrentUser UserInfo currentUser) {
        // 从当前用户信息中获取共享密钥
        String sharedSecret = PasswordReciprocal.getInstance().decoder(currentUser.getSharedSecret());
        // 计算当前时间对应的动态密码
        boolean validate = timeBasedOtpAuthn.validate(currentUser, otp);
        if (validate) {
            return new Message<>(0,"One-Time Password verification succeeded").buildResponse();
        } else {
            return new Message<>(2,"One-Time Password verification failed").buildResponse();
        }
    }
    
}
