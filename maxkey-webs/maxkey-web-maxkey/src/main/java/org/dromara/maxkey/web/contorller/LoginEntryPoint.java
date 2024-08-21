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
import java.text.ParseException;
import java.util.HashMap;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.authn.LoginCredential;
import org.dromara.maxkey.authn.QrCodeCredentialDto;
import org.dromara.maxkey.authn.ScanCode;
import org.dromara.maxkey.authn.jwt.AuthJwt;
import org.dromara.maxkey.authn.jwt.AuthTokenService;
import org.dromara.maxkey.authn.provider.AbstractAuthenticationProvider;
import org.dromara.maxkey.authn.session.Session;
import org.dromara.maxkey.authn.session.SessionManager;
import org.dromara.maxkey.authn.support.kerberos.KerberosService;
import org.dromara.maxkey.authn.support.rememberme.AbstractRemeberMeManager;
import org.dromara.maxkey.authn.support.rememberme.RemeberMe;
import org.dromara.maxkey.authn.support.socialsignon.service.SocialSignOnProviderService;
import org.dromara.maxkey.authn.web.AuthorizationUtils;
import org.dromara.maxkey.configuration.ApplicationConfig;
import org.dromara.maxkey.constants.ConstsLoginType;
import org.dromara.maxkey.crypto.Base64Utils;
import org.dromara.maxkey.crypto.password.PasswordReciprocal;
import org.dromara.maxkey.entity.*;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.exception.BusinessException;
import org.dromara.maxkey.password.onetimepwd.AbstractOtpAuthn;
import org.dromara.maxkey.password.sms.SmsOtpAuthnService;
import org.dromara.maxkey.authn.provider.scancode.ScanCodeService;
import org.dromara.maxkey.persistence.service.SocialsAssociatesService;
import org.dromara.maxkey.persistence.service.UserInfoService;
import org.dromara.maxkey.util.RQCodeUtils;
import org.dromara.maxkey.web.WebConstants;
import org.dromara.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static org.reflections.Reflections.log;

/**
 * @author Crystal.Sea
 *
 */
@Tag(name = "1-1-登录接口文档模块")
@RestController
@RequestMapping(value = "/login")
public class LoginEntryPoint {
	private static Logger logger = LoggerFactory.getLogger(LoginEntryPoint.class);

	@Autowired
	AuthTokenService authTokenService;

	@Autowired
  	ApplicationConfig applicationConfig;

	@Autowired
	AbstractAuthenticationProvider authenticationProvider ;

	@Autowired
	SocialSignOnProviderService socialSignOnProviderService;

	@Autowired
	SocialsAssociatesService socialsAssociatesService;

	@Autowired
	KerberosService kerberosService;

	@Autowired
	UserInfoService userInfoService;

	@Autowired
    AbstractOtpAuthn tfaOtpAuthn;

	@Autowired
    SmsOtpAuthnService smsAuthnService;

	@Autowired
	ScanCodeService scanCodeService;

	@Autowired
	AbstractRemeberMeManager remeberMeManager;

	@Autowired
	SessionManager sessionManager;

	/**
	 * init login
	 * @return
	 */
	@Operation(summary  = "登录接口", description  = "用户登录地址",method="GET")
	@GetMapping(value={"/get"})
	public Message<?> get(@RequestParam(value = "remember_me", required = false) String rememberMeJwt) {
		logger.debug("/get.");
		//Remember Me
		if(StringUtils.isNotBlank(rememberMeJwt)
				&& authTokenService.validateJwtToken(rememberMeJwt)) {
			try {
				RemeberMe remeberMe = remeberMeManager.resolve(rememberMeJwt);
				if(remeberMe != null) {
					LoginCredential credential = new LoginCredential();
					String remeberMeJwt = remeberMeManager.updateRemeberMe(remeberMe);
					credential.setUsername(remeberMe.getUsername());
					Authentication  authentication = authenticationProvider.authenticate(credential,true);
					if(authentication != null) {
			 			AuthJwt authJwt = authTokenService.genAuthJwt(authentication);
			 			authJwt.setRemeberMe(remeberMeJwt);
			 			return new Message<AuthJwt>(authJwt);
					}
				}
			} catch (ParseException e) {
			}
		}
		//for normal login
		HashMap<String , Object> model = new HashMap<>();
		model.put("isRemeberMe", applicationConfig.getLoginConfig().isRemeberMe());
		model.put("isKerberos", applicationConfig.getLoginConfig().isKerberos());
		if(applicationConfig.getLoginConfig().isMfa()) {
			model.put("otpType", tfaOtpAuthn.getOtpType());
			model.put("otpInterval", tfaOtpAuthn.getInterval());
		}

		if( applicationConfig.getLoginConfig().isKerberos()){
			model.put("userDomainUrlJson", kerberosService.buildKerberosProxys());
		}

		Institutions inst = (Institutions)WebContext.getAttribute(WebConstants.CURRENT_INST);
		model.put("inst", inst);
		if(applicationConfig.getLoginConfig().isCaptcha()) {
			model.put("captcha", "true");
		}
		model.put("state", authTokenService.genRandomJwt());
		//load Social Sign On Providers
		model.put("socials", socialSignOnProviderService.loadSocials(inst.getId()));

		return new Message<HashMap<String , Object>>(model);
	}


 	@RequestMapping(value={"/sendotp/{mobile}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Message<AuthJwt> produceOtp(@PathVariable("mobile") String mobile) {
        UserInfo userInfo=userInfoService.findByEmailMobile(mobile);
        if(userInfo != null) {
        	smsAuthnService.getByInstId(WebContext.getInst().getId()).produce(userInfo);
        	return new Message<AuthJwt>(Message.SUCCESS);
        }

        return new Message<AuthJwt>(Message.FAIL);
    }

	@PostMapping(value={"/signin/bindusersocials"})
	public Message<AuthJwt> bindusersocials(@RequestBody LoginCredential credential) {
		//短信验证码
		String code = credential.getCode();
		//映射社交服务的账号
		String username = credential.getUsername();
		//maxkey存储的手机号
		String mobile = credential.getMobile();
		//社交服务类型
		String authType = credential.getAuthType();

		UserInfo userInfo = userInfoService.findByEmailMobile(mobile);
		//验证码验证是否合法
		if (smsAuthnService.getByInstId(WebContext.getInst().getId()).validate(userInfo,code)) {
			//合法进行用户绑定
			SocialsAssociate socialsAssociate = new SocialsAssociate();
			socialsAssociate.setUserId(userInfo.getId());
			socialsAssociate.setUsername(userInfo.getUsername());
			socialsAssociate.setProvider(authType);
			socialsAssociate.setSocialUserId(username);
			socialsAssociate.setInstId(userInfo.getInstId());
			//插入Maxkey和社交服务的用户映射表
			socialsAssociatesService.insert(socialsAssociate);

			//设置完成后，进行登录认证
			LoginCredential loginCredential =new LoginCredential(
					socialsAssociate.getUsername(),"", ConstsLoginType.SOCIALSIGNON);

			SocialsProvider socialSignOnProvider = socialSignOnProviderService.get(socialsAssociate.getInstId(),socialsAssociate.getProvider());

			loginCredential.setProvider(socialSignOnProvider.getProviderName());

			Authentication  authentication = authenticationProvider.authenticate(loginCredential,true);

			return new Message<AuthJwt>(authTokenService.genAuthJwt(authentication));

		}
		return new Message<AuthJwt>(Message.FAIL);
	}


 	/**
 	 * normal
 	 * @param credential
 	 * @return
 	 */
	@Operation(summary = "登录接口", description = "登录接口",method="POST")
 	@PostMapping(value={"/signin"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<AuthJwt> signin( HttpServletRequest request, HttpServletResponse response,@RequestBody LoginCredential credential) {
 		Message<AuthJwt> authJwtMessage = new Message<>(Message.FAIL);
 		if(authTokenService.validateJwtToken(credential.getState())){
 			String authType =  credential.getAuthType();
 			 logger.debug("Login AuthN Type  {}" , authType);
 	        if (StringUtils.isNotBlank(authType)){
		 		Authentication  authentication = authenticationProvider.authenticate(credential);
		 		if(authentication != null) {
		 			AuthJwt authJwt = authTokenService.genAuthJwt(authentication);
		 			if(StringUtils.isNotBlank(credential.getRemeberMe())
		 					&&credential.getRemeberMe().equalsIgnoreCase("true")) {
		 				String remeberMe = remeberMeManager.createRemeberMe(authentication, request, response);
		 				authJwt.setRemeberMe(remeberMe);
			 		}
		 			if(WebContext.getAttribute(WebConstants.CURRENT_USER_PASSWORD_SET_TYPE)!=null) {
		 				authJwt.setPasswordSetType(
		 					(Integer)WebContext.getAttribute(WebConstants.CURRENT_USER_PASSWORD_SET_TYPE));
		 			}
		 			authJwtMessage = new Message<>(authJwt);

		 		}else {//fail
	 				String errorMsg = WebContext.getAttribute(WebConstants.LOGIN_ERROR_SESSION_MESSAGE) == null ?
							  "" : WebContext.getAttribute(WebConstants.LOGIN_ERROR_SESSION_MESSAGE).toString();
	 				authJwtMessage.setMessage(errorMsg);
	 				logger.debug("login fail , message {}",errorMsg);
		 		}
 	        }else {
 	        	logger.error("Login AuthN type must eq normal , tfa or mobile . ");
 	        }
 		}
 		return authJwtMessage;
 	}

 	/**
 	 * for congress
 	 * @param credential
 	 * @return
 	 */
 	@PostMapping(value={"/congress"})
	public Message<AuthJwt> congress( @RequestBody LoginCredential credential) {
 		if(StringUtils.isNotBlank(credential.getCongress())){
 			AuthJwt authJwt = authTokenService.consumeCongress(credential.getCongress());
 			if(authJwt != null) {
 				return new Message<>(authJwt);
 			}
 		}
 		return new Message<>(Message.FAIL);
 	}

	 @Operation(summary = "生成登录扫描二维码", description = "生成登录扫描二维码", method = "GET")
	 @GetMapping("/genScanCode")
	 public Message<HashMap<String,String>> genScanCode() {
		 log.debug("/genScanCode.");
		 String ticket = scanCodeService.createTicket();
		 log.debug("ticket: {}",ticket);
		 String encodeTicket = PasswordReciprocal.getInstance().encode(ticket);
		 BufferedImage bufferedImage  =  RQCodeUtils.write2BufferedImage(encodeTicket, "gif", 300, 300);
		 String rqCode = Base64Utils.encodeImage(bufferedImage);
		 HashMap<String,String> codeMap = new HashMap<>();
		 codeMap.put("rqCode", rqCode);
		 codeMap.put("ticket", encodeTicket);
		 return new Message<>(Message.SUCCESS, codeMap);
	 }

	@Operation(summary = "web二维码登录", description = "web二维码登录", method = "POST")
	@PostMapping("/sign/qrcode")
	public Message<AuthJwt> signByQrcode(@Validated @RequestBody ScanCode scanCode) {
		LoginCredential loginCredential = new LoginCredential();
		loginCredential.setAuthType(scanCode.getAuthType());
		loginCredential.setUsername(scanCode.getCode());

		if(authTokenService.validateJwtToken(scanCode.getState())){
			try {
				Authentication authentication = authenticationProvider.authenticate(loginCredential);
				if (Objects.nonNull(authentication)) {
					//success
					AuthJwt authJwt = authTokenService.genAuthJwt(authentication);
					return new Message<>(authJwt);
				} else {
					return new Message<>(Message.FAIL, "尚未扫码");
				}
			} catch (BusinessException businessException) {
				return new Message<>(businessException.getCode(), businessException.getMessage());
			}
		} else {
			return new Message<>(20005, "state失效重新获取");
		}
	}

	@Operation(summary = "app扫描二维码", description = "扫描二维码登录", method = "POST")
	@PostMapping("/scanCode")
	public Message<String> scanCode(@Validated @RequestBody QrCodeCredentialDto credentialDto) throws ParseException {
		log.debug("/scanCode.");
		String jwtToken = credentialDto.getJwtToken();
		String code = credentialDto.getCode();
		try {
			//获取登录会话
			Session session = AuthorizationUtils.getSession(sessionManager, jwtToken);
			if (Objects.isNull(session)) {
				return new Message<>(Message.FAIL, "登录会话失效，请重新登录");
			}
			//查询二维码是否过期
			String ticketString = PasswordReciprocal.getInstance().decoder(code);
			boolean codeResult = scanCodeService.validateTicket(ticketString, session);
			if (!codeResult) {
				return new Message<>(Message.FAIL, "二维码已过期，请重新获取");
			}

		} catch (ParseException e) {
			log.error("ParseException.",e);
			return new Message<>(Message.FAIL, "token格式错误");
		}
		return new Message<>(Message.SUCCESS, "成功");
	}
}
