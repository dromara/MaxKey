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
 

package org.maxkey.authn.support.rememberme;

import java.util.Date;
import java.util.regex.Pattern;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.joda.time.DateTime;
import org.maxkey.authn.AbstractAuthenticationProvider;
import org.maxkey.configuration.ApplicationConfig;
import org.maxkey.constants.ConstantsLoginType;
import org.maxkey.constants.ConstantsTimeInterval;
import org.maxkey.crypto.Base64Utils;
import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.util.JsonUtils;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class AbstractRemeberMeService {
    private static final Logger _logger = LoggerFactory.getLogger(AbstractRemeberMeService.class);

    protected Integer remeberMeValidity = ConstantsTimeInterval.TWO_WEEK;

    protected String validity;

    @Autowired
    @Qualifier("applicationConfig")
    protected ApplicationConfig applicationConfig;
    
    @Autowired
    @Qualifier("authenticationProvider")
    AbstractAuthenticationProvider authenticationProvider ;

    // follow function is for persist
    public abstract void save(RemeberMe remeberMe);

    public abstract void update(RemeberMe remeberMe);

    public abstract RemeberMe read(RemeberMe remeberMe);

    public abstract void remove(String username);
    // end persist

    public boolean createRemeberMe(String username, HttpServletRequest request, HttpServletResponse response) {
        if (request.getSession().getAttribute(WebConstants.REMEBER_ME_SESSION) != null
                && applicationConfig.getLoginConfig().isRemeberMe()) {
            _logger.debug("Remeber Me ...");
            RemeberMe remeberMe = new RemeberMe();
            remeberMe.setAuthKey(WebContext.genId());
            remeberMe.setId(WebContext.genId());
            remeberMe.setUsername(WebContext.getUserInfo().getUsername());
            remeberMe.setLastLogin(new Date());
            save(remeberMe);
            _logger.debug("Remeber Me " + remeberMe);
            _logger.debug("Cookie Name : " + WebConstants.REMEBER_ME_COOKIE);

            String jsonRemeberMe = JsonUtils.object2Json(remeberMe);
            _logger.debug("Remeber Me JSON " + jsonRemeberMe);

            jsonRemeberMe = ReciprocalUtils.encode(jsonRemeberMe);

            String cookieValue = Base64Utils.base64UrlEncode(jsonRemeberMe.getBytes());

            _logger.debug("Remeber Me JSON " + cookieValue);
            Cookie cookie = new Cookie(WebConstants.REMEBER_ME_COOKIE, cookieValue);

            Integer maxAge = getRemeberMeValidity();
            _logger.debug("Cookie Max Age :" + maxAge + " seconds.");
            cookie.setMaxAge(maxAge);

            // cookie.setPath("/");
            cookie.setDomain(applicationConfig.getDomainName());
            response.addCookie(cookie);
            request.getSession().removeAttribute(WebConstants.REMEBER_ME_SESSION);
        }
        return true;
    }

    public boolean login(String remeberMe, HttpServletResponse response) {
        _logger.debug("RemeberMe : " + remeberMe);

        remeberMe = new String(Base64Utils.base64UrlDecode(remeberMe));

        remeberMe = ReciprocalUtils.decoder(remeberMe);

        _logger.debug("decoder RemeberMe : " + remeberMe);
        RemeberMe remeberMeCookie = new RemeberMe();
        remeberMeCookie = (RemeberMe) JsonUtils.json2Object(remeberMe, remeberMeCookie);
        _logger.debug("Remeber Me Cookie : " + remeberMeCookie);

        RemeberMe storeRemeberMe = read(remeberMeCookie);
        if (storeRemeberMe == null)  {
            return false;
        }
        DateTime loginDate = new DateTime(storeRemeberMe.getLastLogin());
        DateTime expiryDate = loginDate.plusSeconds(getRemeberMeValidity());
        DateTime now = new DateTime();
        if (now.isBefore(expiryDate)) {
            authenticationProvider.trustAuthentication(
                    storeRemeberMe.getUsername(), 
                    ConstantsLoginType.REMEBER_ME, 
                    "", 
                    "", 
                    "success");
            return updateRemeberMe(remeberMeCookie, response);

        }
        return false;
    }

    public boolean updateRemeberMe(RemeberMe remeberMe, HttpServletResponse response) {
        remeberMe.setAuthKey(WebContext.genId());
        remeberMe.setLastLogin(new Date());
        update(remeberMe);
        _logger.debug("update Remeber Me " + remeberMe);
        _logger.debug("Cookie Name : " + WebConstants.REMEBER_ME_COOKIE);

        String jsonRemeberMe = JsonUtils.object2Json(remeberMe);
        _logger.debug("Remeber Me JSON " + jsonRemeberMe);

        _logger.debug("Encode Remeber Me JSON ...");
        jsonRemeberMe = ReciprocalUtils.encode(jsonRemeberMe);
        _logger.debug("Encode Remeber Me JSON " + jsonRemeberMe);

        String cookieValue = Base64Utils.base64UrlEncode(jsonRemeberMe.getBytes());

        Cookie cookie = new Cookie(WebConstants.REMEBER_ME_COOKIE, cookieValue);

        Integer maxAge = getRemeberMeValidity();
        _logger.debug("Cookie Max Age :" + maxAge + " seconds.");
        cookie.setMaxAge(maxAge);

        // cookie.setPath("/");
        cookie.setDomain(applicationConfig.getDomainName());
        response.addCookie(cookie);
        return true;
    }

    public boolean removeRemeberMe(HttpServletResponse response) {
        Cookie cookie = new Cookie(WebConstants.REMEBER_ME_COOKIE, null);
        cookie.setMaxAge(0);

        cookie.setDomain(applicationConfig.getDomainName());
        response.addCookie(cookie);

        remove(WebContext.getUserInfo().getUsername());

        return true;
    }

    public Integer getRemeberMeValidity() {
        return remeberMeValidity;
    }

    public void setRemeberMeValidity(Integer remeberMeValidity) {
        this.remeberMeValidity = remeberMeValidity;
    }

    public String getValidity() {
        return validity;
    }

    public void setApplicationConfig(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    public void setValidity(String validity) {
        _logger.debug("validity : " + validity);
        this.validity = validity;
        if (Pattern.matches("[0-9]+", validity)) {
            remeberMeValidity = Integer.parseInt(validity);
        } else if (validity.equalsIgnoreCase("ONE_DAY")) {
            remeberMeValidity = ConstantsTimeInterval.ONE_DAY;
        } else if (validity.equalsIgnoreCase("ONE_WEEK")) {
            remeberMeValidity = ConstantsTimeInterval.ONE_WEEK;
        } else if (validity.equalsIgnoreCase("TWO_WEEK")) {
            remeberMeValidity = ConstantsTimeInterval.TWO_WEEK;
        } else if (validity.equalsIgnoreCase("ONE_YEAR")) {
            remeberMeValidity = ConstantsTimeInterval.ONE_YEAR;
        }

        _logger.debug("Remeber Me Validity : " + remeberMeValidity);
    }
}
