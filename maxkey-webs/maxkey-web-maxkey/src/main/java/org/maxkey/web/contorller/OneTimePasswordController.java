package org.maxkey.web.contorller;

import java.awt.image.BufferedImage;
import java.util.UUID;

import org.apache.commons.codec.binary.Hex;
import org.maxkey.crypto.Base32Utils;
import org.maxkey.crypto.password.PasswordReciprocal;
import org.maxkey.crypto.password.opt.algorithm.KeyUriFormat;
import org.maxkey.crypto.password.opt.algorithm.OTPSecret;
import org.maxkey.dao.service.UserInfoService;
import org.maxkey.domain.UserInfo;
import org.maxkey.util.RQCodeUtils;
import org.maxkey.web.WebContext;
import org.maxkey.web.endpoint.ImageEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * @author Crystal.Sea
 *
 */
@Controller
@RequestMapping(value = { "/otp" })
public class OneTimePasswordController {
	final static Logger _logger = LoggerFactory.getLogger(OneTimePasswordController.class);

	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;
	
	@Autowired
	@Qualifier("timeBasedKeyUriFormat")
	KeyUriFormat timeBasedKeyUriFormat;
	
	@Autowired
	@Qualifier("counterBasedKeyUriFormat")
	KeyUriFormat counterBasedKeyUriFormat;
	
	@Autowired
	@Qualifier("hotpKeyUriFormat")
	KeyUriFormat hotpKeyUriFormat;

	@Autowired
	@Qualifier("passwordReciprocal")
	PasswordReciprocal passwordReciprocal;
	

	@RequestMapping(value={"/timebased"})
	public ModelAndView timebased(){
		ModelAndView modelAndView=new ModelAndView("otp/timeBased");
		UserInfo userInfo=WebContext.getUserInfo();
		String sharedSecret=passwordReciprocal.decoder(userInfo.getSharedSecret());
		timeBasedKeyUriFormat.setSecret(sharedSecret);
		String otpauth=timeBasedKeyUriFormat.format(userInfo.getUsername());
		byte[] byteSharedSecret=Base32Utils.decode(sharedSecret);
		String hexSharedSecret=Hex.encodeHexString(byteSharedSecret);
		modelAndView.addObject("id", genRQCode(otpauth));
		modelAndView.addObject("userInfo", userInfo);
		modelAndView.addObject("format", timeBasedKeyUriFormat);
		modelAndView.addObject("sharedSecret", sharedSecret);
		modelAndView.addObject("hexSharedSecret", hexSharedSecret);
		return modelAndView;
	}
	
	@RequestMapping(value={"gen/timebased"})
	public ModelAndView gentimebased(){
		UserInfo userInfo=WebContext.getUserInfo();
		byte[] byteSharedSecret=OTPSecret.generate(timeBasedKeyUriFormat.getCrypto());
		String sharedSecret=Base32Utils.encode(byteSharedSecret);
		sharedSecret=passwordReciprocal.encode(sharedSecret);
		userInfo.setSharedSecret(sharedSecret);
		userInfoService.changeSharedSecret(userInfo);
		WebContext.setUserInfo(userInfo);
		return WebContext.redirect("/otp/timebased");
	}
	

	@RequestMapping(value={"/counterbased"})
	public ModelAndView counterbased(){
		ModelAndView modelAndView=new ModelAndView("otp/counterBased");
		UserInfo userInfo=WebContext.getUserInfo();
		String sharedSecret=passwordReciprocal.decoder(userInfo.getSharedSecret());
		counterBasedKeyUriFormat.setSecret(sharedSecret);
		counterBasedKeyUriFormat.setCounter(Long.parseLong(userInfo.getSharedCounter()));
		String otpauth=counterBasedKeyUriFormat.format(userInfo.getUsername());
	
		byte[] byteSharedSecret=Base32Utils.decode(sharedSecret);
		String hexSharedSecret=Hex.encodeHexString(byteSharedSecret);
		modelAndView.addObject("id", genRQCode(otpauth));
		modelAndView.addObject("userInfo", userInfo);
		modelAndView.addObject("format", counterBasedKeyUriFormat);
		modelAndView.addObject("sharedSecret", sharedSecret);
		modelAndView.addObject("hexSharedSecret", hexSharedSecret);
		return modelAndView;
		
	}
	
	@RequestMapping(value={"gen/counterbased"})
	public ModelAndView gencounterbased(){
		UserInfo userInfo=WebContext.getUserInfo();
		byte[] byteSharedSecret=OTPSecret.generate(counterBasedKeyUriFormat.getCrypto());
		String sharedSecret=Base32Utils.encode(byteSharedSecret);
		sharedSecret=passwordReciprocal.encode(sharedSecret);
		userInfo.setSharedSecret(sharedSecret);
		userInfo.setSharedCounter("0");
		userInfoService.changeSharedSecret(userInfo);
		WebContext.setUserInfo(userInfo);
		return WebContext.redirect("/otp/counterbased");
	}

	@RequestMapping(value={"/hotp"})
	public ModelAndView hotp(){
		ModelAndView modelAndView=new ModelAndView("otp/hotp");
		UserInfo userInfo=WebContext.getUserInfo();
		String sharedSecret=passwordReciprocal.decoder(userInfo.getSharedSecret());
		hotpKeyUriFormat.setSecret(sharedSecret);
		hotpKeyUriFormat.setCounter(Long.parseLong(userInfo.getSharedCounter()));
		String otpauth=hotpKeyUriFormat.format(userInfo.getUsername());
		byte[] byteSharedSecret=Base32Utils.decode(sharedSecret);
		String hexSharedSecret=Hex.encodeHexString(byteSharedSecret);
		modelAndView.addObject("id", genRQCode(otpauth));
		modelAndView.addObject("userInfo", userInfo);
		modelAndView.addObject("format", hotpKeyUriFormat);
		modelAndView.addObject("sharedSecret", sharedSecret);
		modelAndView.addObject("hexSharedSecret", hexSharedSecret);
		return modelAndView;
		
	}
	
	@RequestMapping(value={"gen/hotp"})
	public ModelAndView genhotp(){
		UserInfo userInfo=WebContext.getUserInfo();
		byte[] byteSharedSecret=OTPSecret.generate(hotpKeyUriFormat.getCrypto());
		String sharedSecret=Base32Utils.encode(byteSharedSecret);
		sharedSecret=passwordReciprocal.encode(sharedSecret);
		userInfo.setSharedSecret(sharedSecret);
		userInfo.setSharedCounter("0");
		userInfoService.changeSharedSecret(userInfo);
		WebContext.setUserInfo(userInfo);
		return WebContext.redirect("/otp/hotp");
	}
	
	
	public  String genRQCode(String otpauth){
		BufferedImage bufferedImage = RQCodeUtils.write2BufferedImage(otpauth, "gif", 300, 300);
		byte[] imageByte=ImageEndpoint.bufferedImage2Byte(bufferedImage);
		String uuid=UUID.randomUUID().toString().toLowerCase();
		WebContext.getSession().setAttribute(uuid, imageByte);
		return uuid;
	}
}
