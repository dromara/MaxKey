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
 

package org.maxkey.password.onetimepwd;

import java.sql.Types;
import java.util.concurrent.TimeUnit;

import org.maxkey.configuration.EmailConfig;
import org.maxkey.constants.ConstsBoolean;
import org.maxkey.crypto.password.PasswordReciprocal;
import org.maxkey.entity.EmailSenders;
import org.maxkey.entity.SmsProvider;
import org.maxkey.password.onetimepwd.impl.MailOtpAuthn;
import org.maxkey.password.onetimepwd.impl.sms.SmsOtpAuthnAliyun;
import org.maxkey.password.onetimepwd.impl.sms.SmsOtpAuthnTencentCloud;
import org.maxkey.password.onetimepwd.impl.sms.SmsOtpAuthnYunxin;
import org.maxkey.password.onetimepwd.token.RedisOtpTokenStore;
import org.maxkey.persistence.service.EmailSendersService;
import org.maxkey.persistence.service.SmsProviderService;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

public class OtpAuthnService {

    protected static final Cache<String, AbstractOtpAuthn> otpAuthnStore = 
            Caffeine.newBuilder()
                .expireAfterWrite(60, TimeUnit.MINUTES)
                .build();
    
    SmsProviderService smsProviderService;
    
    EmailSendersService emailSendersService;
    
    RedisOtpTokenStore redisOptTokenStore;
    
    public OtpAuthnService(SmsProviderService smsProviderService, EmailSendersService emailSendersService) {
		this.smsProviderService = smsProviderService;
		this.emailSendersService = emailSendersService;
	}

	public OtpAuthnService(SmsProviderService smsProviderService,RedisOtpTokenStore redisOptTokenStore) {
		this.smsProviderService = smsProviderService;
		this.redisOptTokenStore = redisOptTokenStore;
	}

	public AbstractOtpAuthn getByInstId(String instId) {
    	AbstractOtpAuthn otpAuthn = otpAuthnStore.getIfPresent(instId);
    	if(otpAuthn == null) {
    		SmsProvider smsProvider = 
    				smsProviderService.findOne("where instid = ? ", new Object[]{instId}, new int[]{Types.VARCHAR});
    		if(smsProvider != null ) {
    			
    			if(smsProvider.getProvider().equalsIgnoreCase("aliyun")) {
    				SmsOtpAuthnAliyun aliyun = new SmsOtpAuthnAliyun(
													smsProvider.getAppKey(),
													smsProvider.getAppSecret(),
													smsProvider.getTemplateId(),
													smsProvider.getSignName()
												);
    				if(redisOptTokenStore != null) {
    					aliyun.setOptTokenStore(redisOptTokenStore);
    				}
    				otpAuthn = aliyun;
    			}else if(smsProvider.getProvider().equalsIgnoreCase("tencentcloud")) {
    				SmsOtpAuthnTencentCloud tencentCloud = new SmsOtpAuthnTencentCloud(
    												smsProvider.getAppKey(),
    												smsProvider.getAppSecret(),
    												smsProvider.getSmsSdkAppId(),
    												smsProvider.getTemplateId(),
    												smsProvider.getSignName()
    											);
    				if(redisOptTokenStore != null) {
    					tencentCloud.setOptTokenStore(redisOptTokenStore);
    				}
    				otpAuthn = tencentCloud;
    			}else if(smsProvider.getProvider().equalsIgnoreCase("neteasesms")) {
    				SmsOtpAuthnYunxin yunxin = new SmsOtpAuthnYunxin(
    												smsProvider.getAppKey(),
    												smsProvider.getAppSecret(),
    												smsProvider.getTemplateId()
    											);
    				if(redisOptTokenStore != null) {
    					yunxin.setOptTokenStore(redisOptTokenStore);
    				}
    				otpAuthn = yunxin;
    			}else if(smsProvider.getProvider().equalsIgnoreCase("email")) {
    				EmailSenders emailSender = 
    						emailSendersService.findOne("where instid = ? ", new Object[]{instId}, new int[]{Types.VARCHAR});
    				
    				String credentials = PasswordReciprocal.getInstance().decoder(emailSender.getCredentials());
    				EmailConfig emailConfig = 
    								new EmailConfig(
    										emailSender.getAccount(),
    										credentials,
    										emailSender.getSmtpHost(),
    										emailSender.getPort(),
    										ConstsBoolean.isTrue(emailSender.getSslSwitch()),
    										emailSender.getSender());
    				MailOtpAuthn mailOtpAuthn = new MailOtpAuthn(emailConfig);
    				if(redisOptTokenStore != null) {
    					mailOtpAuthn.setOptTokenStore(redisOptTokenStore);
    				}
    				otpAuthn = mailOtpAuthn;
    			}
    			
    			otpAuthnStore.put(instId, otpAuthn);	
    		}
    	}
    	return otpAuthn;
    }

	public void setRedisOptTokenStore(RedisOtpTokenStore redisOptTokenStore) {
		this.redisOptTokenStore = redisOptTokenStore;
	}
	
	
}
