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
 

package org.dromara.maxkey.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoginConfig {
    
	@Value("${maxkey.login.captcha}")
    boolean captcha;
	
    @Value("${maxkey.login.mfa}")
    boolean mfa;
    
    @Value("${maxkey.login.kerberos}")
    boolean kerberos;
    
    @Value("${maxkey.login.remeberme}")
    boolean remeberMe;
    
    @Value("${maxkey.login.wsfederation}")
    boolean wsFederation;

    /**
     *  .
     */
    public LoginConfig() {
    }

    public boolean isCaptcha() {
		return captcha;
	}

	public void setCaptcha(boolean captcha) {
		this.captcha = captcha;
	}

	public boolean isKerberos() {
        return kerberos;
    }

    public void setKerberos(boolean kerberos) {
        this.kerberos = kerberos;
    }

    public boolean isMfa() {
        return mfa;
    }

    public void setMfa(boolean mfa) {
        this.mfa = mfa;
    }



    public boolean isRemeberMe() {
        return remeberMe;
    }

    public void setRemeberMe(boolean remeberMe) {
        this.remeberMe = remeberMe;
    }

    public boolean isWsFederation() {
        return wsFederation;
    }

    public void setWsFederation(boolean wsFederation) {
        this.wsFederation = wsFederation;
    }
    
    @Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LoginConfig [mfa=");
		builder.append(mfa);
		builder.append(", kerberos=");
		builder.append(kerberos);
		builder.append(", remeberMe=");
		builder.append(remeberMe);
		builder.append(", wsFederation=");
		builder.append(wsFederation);
		builder.append("]");
		return builder.toString();
	}

}
