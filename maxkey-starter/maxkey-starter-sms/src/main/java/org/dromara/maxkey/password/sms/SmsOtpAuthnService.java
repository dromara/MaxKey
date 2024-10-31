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
 

package org.dromara.maxkey.password.sms;

import java.util.concurrent.TimeUnit;

import org.dromara.maxkey.configuration.EmailConfig;
import org.dromara.maxkey.constants.ConstsBoolean;
import org.dromara.maxkey.crypto.password.PasswordReciprocal;
import org.dromara.maxkey.entity.cnf.CnfEmailSenders;
import org.dromara.maxkey.entity.cnf.CnfSmsProvider;
import org.dromara.maxkey.password.onetimepwd.AbstractOtpAuthn;
import org.dromara.maxkey.password.onetimepwd.impl.MailOtpAuthn;
import org.dromara.maxkey.password.onetimepwd.token.RedisOtpTokenStore;
import org.dromara.maxkey.password.sms.impl.SmsOtpAuthnAliyun;
import org.dromara.maxkey.password.sms.impl.SmsOtpAuthnTencentCloud;
import org.dromara.maxkey.password.sms.impl.SmsOtpAuthnYunxin;
import org.dromara.maxkey.persistence.service.CnfEmailSendersService;
import org.dromara.maxkey.persistence.service.CnfSmsProviderService;
import org.dromara.mybatis.jpa.query.LambdaQuery;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

public class SmsOtpAuthnService {

	static final Cache<String, AbstractOtpAuthn> smsAuthnStore = 
			Caffeine.newBuilder().expireAfterWrite(60, TimeUnit.MINUTES).build();

    CnfSmsProviderService smsProviderService;
    
    CnfEmailSendersService emailSendersService;
    
    RedisOtpTokenStore redisOptTokenStore;
    
    public SmsOtpAuthnService(CnfSmsProviderService smsProviderService, CnfEmailSendersService emailSendersService) {
		this.smsProviderService = smsProviderService;
		this.emailSendersService = emailSendersService;
	}

	public SmsOtpAuthnService(CnfSmsProviderService smsProviderService,CnfEmailSendersService emailSendersService,RedisOtpTokenStore redisOptTokenStore) {
		this.smsProviderService = smsProviderService;
		this.emailSendersService = emailSendersService;
		this.redisOptTokenStore = redisOptTokenStore;
	}

	public AbstractOtpAuthn getByInstId(String instId) {
    	AbstractOtpAuthn smsOtpAuthn = smsAuthnStore.getIfPresent(instId);
    	if(smsOtpAuthn == null) {
    		LambdaQuery<CnfSmsProvider> lambdaQuery = new LambdaQuery<CnfSmsProvider>();
    		lambdaQuery.eq(CnfSmsProvider::getInstId, instId);
    		CnfSmsProvider smsProvider = smsProviderService.get(lambdaQuery);
    		if(smsProvider != null ) {
    			if(smsProvider.getProvider().equalsIgnoreCase("aliyun")) {
					smsOtpAuthn = new SmsOtpAuthnAliyun(
							smsProvider.getAppKey(),
							PasswordReciprocal.getInstance().decoder(smsProvider.getAppSecret()),
							smsProvider.getTemplateId(), 
							smsProvider.getSignName());
    			}else if(smsProvider.getProvider().equalsIgnoreCase("tencentcloud")) {
					smsOtpAuthn = new SmsOtpAuthnTencentCloud(
							smsProvider.getAppKey(),
							PasswordReciprocal.getInstance().decoder(smsProvider.getAppSecret()),
							smsProvider.getSmsSdkAppId(), 
							smsProvider.getTemplateId(), smsProvider.getSignName());
    			}else if(smsProvider.getProvider().equalsIgnoreCase("neteasesms")) {
					smsOtpAuthn = new SmsOtpAuthnYunxin(
							smsProvider.getAppKey(),
							PasswordReciprocal.getInstance().decoder(smsProvider.getAppSecret()),
							smsProvider.getTemplateId());
    			}else if(smsProvider.getProvider().equalsIgnoreCase("email")) {
    				LambdaQuery<CnfEmailSenders> emailSenderslambdaQuery = new LambdaQuery<CnfEmailSenders>();
    				emailSenderslambdaQuery.eq(CnfEmailSenders::getInstId, instId);
    				CnfEmailSenders emailSender = emailSendersService.get(emailSenderslambdaQuery);
					String credentials = PasswordReciprocal.getInstance().decoder(emailSender.getCredentials());
					EmailConfig emailConfig = new EmailConfig(
							emailSender.getAccount(), 
							credentials,
							emailSender.getSmtpHost(),
							emailSender.getPort(),
							ConstsBoolean.isTrue(emailSender.getSslSwitch()), 
							emailSender.getSender());
    				smsOtpAuthn = new MailOtpAuthn(emailConfig);
    			}
    			
    			if(redisOptTokenStore != null) {
    				smsOtpAuthn.setOptTokenStore(redisOptTokenStore);
				}
    			smsAuthnStore.put(instId, smsOtpAuthn);	
    		}
    	}
    	return smsOtpAuthn;
    }

	public void setRedisOptTokenStore(RedisOtpTokenStore redisOptTokenStore) {
		this.redisOptTokenStore = redisOptTokenStore;
	}
	
	
}
