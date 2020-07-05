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
import java.util.UUID;
import org.apache.commons.codec.binary.Hex;
import org.maxkey.crypto.Base32Utils;
import org.maxkey.crypto.password.PasswordReciprocal;
import org.maxkey.crypto.password.opt.algorithm.KeyUriFormat;
import org.maxkey.crypto.password.opt.algorithm.OtpSecret;
import org.maxkey.domain.UserInfo;
import org.maxkey.persistence.service.UserInfoService;
import org.maxkey.util.RQCodeUtils;
import org.maxkey.web.WebContext;
import org.maxkey.web.image.ImageEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * .
 * @author Crystal.Sea
 *
 */
@Controller
@RequestMapping(value  =  { "/safe/otp" })
public class OneTimePasswordController {
    static final  Logger _logger  =  LoggerFactory.getLogger(OneTimePasswordController.class);

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;

    @Autowired
    @Qualifier("keyUriFormat")
    KeyUriFormat keyUriFormat;

    @Autowired
    @Qualifier("passwordReciprocal")
    PasswordReciprocal passwordReciprocal;

    @RequestMapping(value = {"/timebased"})
    public ModelAndView timebased() {
        ModelAndView modelAndView = new ModelAndView("safe/timeBased");
        UserInfo userInfo = WebContext.getUserInfo();
        String sharedSecret = passwordReciprocal.decoder(userInfo.getSharedSecret());
        keyUriFormat.setSecret(sharedSecret);
        String otpauth = keyUriFormat.format(userInfo.getUsername());
        byte[] byteSharedSecret = Base32Utils.decode(sharedSecret);
        String hexSharedSecret = Hex.encodeHexString(byteSharedSecret);
        modelAndView.addObject("id", genRqCode(otpauth));
        modelAndView.addObject("userInfo", userInfo);
        modelAndView.addObject("format", keyUriFormat);
        modelAndView.addObject("sharedSecret", sharedSecret);
        modelAndView.addObject("hexSharedSecret", hexSharedSecret);
        return modelAndView;
    }

    @RequestMapping(value = {"gen/timebased"})
    public ModelAndView gentimebased() {
        UserInfo userInfo = WebContext.getUserInfo();
        byte[] byteSharedSecret = OtpSecret.generate(keyUriFormat.getCrypto());
        String sharedSecret = Base32Utils.encode(byteSharedSecret);
        sharedSecret = passwordReciprocal.encode(sharedSecret);
        userInfo.setSharedSecret(sharedSecret);
        userInfoService.changeSharedSecret(userInfo);
        WebContext.setUserInfo(userInfo);
        return WebContext.redirect("/safe/otp/timebased");
    }


    @RequestMapping(value = {"/counterbased"})
    public ModelAndView counterbased() {
        ModelAndView modelAndView = new ModelAndView("safe/counterBased");
        UserInfo userInfo = WebContext.getUserInfo();
        String sharedSecret = passwordReciprocal.decoder(userInfo.getSharedSecret());
        keyUriFormat.setSecret(sharedSecret);
        keyUriFormat.setCounter(Long.parseLong(userInfo.getSharedCounter()));
        String otpauth = keyUriFormat.format(userInfo.getUsername());

        byte[] byteSharedSecret = Base32Utils.decode(sharedSecret);
        String hexSharedSecret = Hex.encodeHexString(byteSharedSecret);
        modelAndView.addObject("id", genRqCode(otpauth));
        modelAndView.addObject("userInfo", userInfo);
        modelAndView.addObject("format", keyUriFormat);
        modelAndView.addObject("sharedSecret", sharedSecret);
        modelAndView.addObject("hexSharedSecret", hexSharedSecret);
        return modelAndView;

    }

    @RequestMapping(value = {"gen/counterbased"})
    public ModelAndView gencounterbased() {
        UserInfo userInfo = WebContext.getUserInfo();
        byte[] byteSharedSecret = OtpSecret.generate(keyUriFormat.getCrypto());
        String sharedSecret = Base32Utils.encode(byteSharedSecret);
        sharedSecret = passwordReciprocal.encode(sharedSecret);
        userInfo.setSharedSecret(sharedSecret);
        userInfo.setSharedCounter("0");
        userInfoService.changeSharedSecret(userInfo);
        WebContext.setUserInfo(userInfo);
        return WebContext.redirect("/safe/otp/counterbased");
    }

    @RequestMapping(value = {"/hotp"})
    public ModelAndView hotp() {
        ModelAndView modelAndView = new ModelAndView("safe/hotp");
        UserInfo userInfo = WebContext.getUserInfo();
        String sharedSecret = passwordReciprocal.decoder(userInfo.getSharedSecret());
        keyUriFormat.setSecret(sharedSecret);
        keyUriFormat.setCounter(Long.parseLong(userInfo.getSharedCounter()));
        String otpauth = keyUriFormat.format(userInfo.getUsername());
        byte[] byteSharedSecret = Base32Utils.decode(sharedSecret);
        String hexSharedSecret = Hex.encodeHexString(byteSharedSecret);
        modelAndView.addObject("id", genRqCode(otpauth));
        modelAndView.addObject("userInfo", userInfo);
        modelAndView.addObject("format", keyUriFormat);
        modelAndView.addObject("sharedSecret", sharedSecret);
        modelAndView.addObject("hexSharedSecret", hexSharedSecret);
        return modelAndView;

    }

    @RequestMapping(value = {"gen/hotp"})
    public ModelAndView genhotp() {
        UserInfo userInfo = WebContext.getUserInfo();
        byte[] byteSharedSecret = OtpSecret.generate(keyUriFormat.getCrypto());
        String sharedSecret = Base32Utils.encode(byteSharedSecret);
        sharedSecret = passwordReciprocal.encode(sharedSecret);
        userInfo.setSharedSecret(sharedSecret);
        userInfo.setSharedCounter("0");
        userInfoService.changeSharedSecret(userInfo);
        WebContext.setUserInfo(userInfo);
        return WebContext.redirect("/safe/otp/hotp");
    }


    public  String genRqCode(String otpauth) {
        BufferedImage bufferedImage  =  RQCodeUtils.write2BufferedImage(otpauth, "gif", 300, 300);
        byte[] imageByte = ImageEndpoint.bufferedImage2Byte(bufferedImage);
        String uuid = UUID.randomUUID().toString().toLowerCase();
        WebContext.getSession().setAttribute(uuid, imageByte);
        return uuid;
    }
}
