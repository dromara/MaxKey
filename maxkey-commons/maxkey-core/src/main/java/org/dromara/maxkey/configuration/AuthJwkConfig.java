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
 

package org.dromara.maxkey.configuration;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class AuthJwkConfig {
	private static final Logger _logger = LoggerFactory.getLogger(AuthJwkConfig.class);
	
	static final String DEFAULT_JWT_KEY ="FhJoipSbwWz8FwFOD0sgJZBSdQly49I8oRpHAJiX9JcEredzlkOWNgbuJN9qKX-W5DF_vQ_O6Czs51eYzqxsu136sZxturT9a7Rg-Ao5B0hBrXSLGIAdAxvZGwF74gZAA5js47EP6fcfpkymPRmoiAb5ROOGASr3w5w8L8gfiZJ16RxDyj487sTm0Fjzq20vz8wQka50H1sapLd2lEe450DEKG_GVfYd6dhAwXou4Tgc7E1mRVys4PD-6ZRv90sKQW3BLKoblMh6gI0BD8BGHL5293TjDkrvydpHHNyk79N3e1N1EufEYcUUusfc44d4_YN4uy4w05iYU2Z2FZk8Gg";
    
	static final String DEFAULT_JWT_REFRESH_KEY ="Ri4VouCQdGhiH9iGiRvlmWV1yWTVDDYKp8VKiuQ4CHNRPj_JAC9DG_hO70KjUl1DOvIMsvTOen8l7dJ6qR3JK7BNTSJf6ExpQRqlz1tyBQo4084LdyhQyxonmplU_HFwQxo5Ieis5yiMRAnv6HCe3D1t06tUaXaX1iiz4HdzR3korl1iSPRGAWEacRPbUVsOv-BQFi5wWEx94MPW2Uh3MfQYKvGpnKgkDQrE5f4DgOcn1ob8rvOYXgeR1TelDpwKLZlqaLInU9sBzZomiM31cZZ541yFRi5HNLd-FXAhIJQwBxq4Rp4c0Z5poM_R9nhpv0nrFrAdZYF-mnZ2tXBxTA";
	
	@Value("${maxkey.auth.jwt.expires:28800}")
    int     expires;
    
    @Value("${maxkey.auth.jwt.secret}")
    String     secret;
    
    @Value("${maxkey.auth.jwt.refresh.expires:86400}")
    int     refreshExpires;
    
    @Value("${maxkey.auth.jwt.refresh.secret}")
    String     refreshSecret;
    
    @Value("${maxkey.auth.jwt.issuer:https://sso.maxkey.top/}")
    String     issuer;

    public AuthJwkConfig() {
        super();
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }


    public int getExpires() {
        return expires;
    }

    public void setExpires(int expires) {
        this.expires = expires;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
    
    public int getRefreshExpires() {
        return refreshExpires;
    }

    public void setRefreshExpires(int refreshExpires) {
        this.refreshExpires = refreshExpires;
    }

    public String getRefreshSecret() {
        return refreshSecret;
    }

    public void setRefreshSecret(String refreshSecret) {
        this.refreshSecret = refreshSecret;
    }
    
    public void checkProductionEnvironmentSecret() {
        if (StringUtils.isBlank(secret) || secret.equals(DEFAULT_JWT_KEY)) {
        	_logger.warn("Production Environment [maxkey.auth.jwt.secret] is default value, it will be a security risk.");
        }
        if (StringUtils.isBlank(refreshSecret) || refreshSecret.equals(DEFAULT_JWT_REFRESH_KEY)) {
        	_logger.warn("Production Environment [maxkey.auth.jwt.refresh.secret]  is default value, it will be a security risk.");
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AuthJwkConfig [issuer=");
        builder.append(issuer);
        builder.append(", expires=");
        builder.append(expires);
        builder.append(", secret=");
        builder.append(secret);
        builder.append("]");
        return builder.toString();
    }
    
}
