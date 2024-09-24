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
 

package org.dromara.maxkey.password.onetimepwd.algorithm;

public class OtpKeyUriFormat {

    public class Types {
        public static final String HOTP = "hotp";
        public static final String TOTP = "totp";

    }

    String crypto = "HmacSHA1";
    String type;
    String secret;
    String issuer;
    String domain;
    int digits = 6;
    // just for hotp
    Long counter = 0L;
    // just for totp
    int period = 30;

    String account;

    public OtpKeyUriFormat() {

    }

    /**
     * @param type
     * @param secret
     */
    public OtpKeyUriFormat(String type, String secret) {
        this.type = type;
        this.secret = secret;
    }

    /**
     * @param type
     * @param secret
     * @param issuer
     */
    public OtpKeyUriFormat(String type, String secret, String issuer) {
        this.type = type;
        this.secret = secret;
        this.issuer = issuer;
    }

    public OtpKeyUriFormat(String crypto, String type, String secret, String issuer, String domain, int digits,
            Long counter, int period, String account) {
        super();
        this.crypto = crypto;
        this.type = type;
        this.secret = secret;
        this.issuer = issuer;
        this.domain = domain;
        this.digits = digits;
        this.counter = counter;
        this.period = period;
        this.account = account;
    }

    public OtpKeyUriFormat(String type,  String issuer, String domain, int digits,int period) {
        super();
        this.type = type;
        this.issuer = issuer;
        this.domain = domain;
        this.digits = digits;
        this.period = period;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the secret
     */
    public String getSecret() {
        return secret;
    }

    /**
     * @param secret the secret to set
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }

    /**
     * @return the issuer
     */
    public String getIssuer() {
        return issuer;
    }

    /**
     * @param issuer the issuer to set
     */
    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    /**
     * @return the digits
     */
    public int getDigits() {
        return digits;
    }

    /**
     * @param digits the digits to set
     */
    public void setDigits(int digits) {
        this.digits = digits;
    }

    /**
     * @return the counter
     */
    public Long getCounter() {
        return counter;
    }

    /**
     * @param counter the counter to set
     */
    public void setCounter(Long counter) {
        this.counter = counter;
    }

    /**
     * @return the period
     */
    public int getPeriod() {
        return period;
    }

    /**
     * @param period the period to set
     */
    public void setPeriod(int period) {
        this.period = period;
    }

    /**
     * @return the account
     */
    public String getAccount() {
        return account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * @return the crypto
     */
    public String getCrypto() {
        return crypto;
    }

    /**
     * @param crypto the crypto to set
     */
    public void setCrypto(String crypto) {
        this.crypto = crypto;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String format() {
        return format(this.account);
    }

    /**
     * format account.
     * @param account String
     * @return
     */
    public String format(String account) {
        StringBuffer uri = new StringBuffer("otpauth://");
        uri.append(type).append("/");
        if (null != this.domain) {
            uri.append(this.domain).append("/").append(account);
        } else {
            uri.append(account);
        }
        uri.append("?secret=").append(secret);

        if (null != issuer) {
            uri.append("&issuer=").append(issuer);
        }
        if (digits != 6) {
            uri.append("&digits=").append(digits);
        }

        if (type.equalsIgnoreCase(Types.TOTP) && period != 30) {
            uri.append("&period=").append(period);
        }

        if (type.equalsIgnoreCase(Types.HOTP)) {
            uri.append("&counter=").append(counter);
        }

        return uri.toString();
    }

    @Override
    public String toString() {
        return "KeyUriFormat [crypto=" + crypto + ", type=" + type + ", secret=" + secret + ", issuer=" + issuer
                + ", domain=" + domain + ", digits=" + digits + ", counter=" + counter + ", period=" + period
                + ", account=" + account + "]";
    }

}
