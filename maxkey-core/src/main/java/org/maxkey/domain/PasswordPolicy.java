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
 

package org.maxkey.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.apache.mybatis.jpa.persistence.JpaBaseDomain;
import org.maxkey.constants.ConstantsServiceMessage;
import org.maxkey.exception.PasswordPolicyException;

/**
 * @author Crystal.Sea
 *
 */

@Table(name = "MXK_PASSWORD_POLICY")
public class PasswordPolicy extends JpaBaseDomain implements java.io.Serializable {

    private static final long serialVersionUID = -4797776994287829182L;
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
    String id;
    /**
     * minimum password lengths
     */
    @NotNull
    @Column
    private int minLength;
    /**
     * maximum password lengths
     */
    @NotNull
    @Column
    private int maxLength;
    /**
     * least lowercase letter
     */
    @NotNull
    @Column
    private int lowerCase;
    /**
     * least uppercase letter
     */
    @NotNull
    @Column
    private int upperCase;
    /**
     * inclusion of numerical digits
     */
    @NotNull
    @Column
    private int digits;
    /**
     * inclusion of special characters
     */
    @NotNull
    @Column
    private int specialChar;
    /**
     * correct password attempts
     */
    @NotNull
    @Column
    private int attempts;
    /**
     * attempts lock Duration
     */
    @NotNull
    @Column
    private int duration;
    /**
     * require users to change passwords periodically
     */
    @Column
    private int expiration;

    /**
     * 0 no 1 yes
     */
    @Column
    private int username;

    /**
     * not include password list
     */
    @Column
    private int history;
    
    @Column
    private int dictionary;
    
    @Column
    private int alphabetical;
    
    @Column
    private int numerical;
    
    @Column
    private int qwerty;
    
    @Column
    private int occurances;
    
    private int randomPasswordLength;

    /**
     * @return the minLength
     */
    public int getMinLength() {
        return minLength;
    }

    /**
     * @param minLength the minLength to set
     */
    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    /**
     * @return the maxLength
     */
    public int getMaxLength() {
        return maxLength;
    }

    /**
     * @param maxLength the maxLength to set
     */
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    /**
     * @return the lowerCase
     */
    public int getLowerCase() {
        return lowerCase;
    }

    /**
     * @param lowerCase the lowerCase to set
     */
    public void setLowerCase(int lowerCase) {
        this.lowerCase = lowerCase;
    }

    /**
     * @return the upperCase
     */
    public int getUpperCase() {
        return upperCase;
    }

    /**
     * @param upperCase the upperCase to set
     */
    public void setUpperCase(int upperCase) {
        this.upperCase = upperCase;
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
     * @return the specialChar
     */
    public int getSpecialChar() {
        return specialChar;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @param specialChar the specialChar to set
     */
    public void setSpecialChar(int specialChar) {
        this.specialChar = specialChar;
    }

    /**
     * @return the attempts
     */
    public int getAttempts() {
        return attempts;
    }

    /**
     * @param attempts the attempts to set
     */
    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    /**
     * @return the duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * @return the expiration
     */
    public int getExpiration() {
        return expiration;
    }

    /**
     * @param expiration the expiration to set
     */
    public void setExpiration(int expiration) {
        this.expiration = expiration;
    }

    /**
     * @return the username
     */
    public int getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(int username) {
        this.username = username;
    }


    public int getHistory() {
        return history;
    }

    public void setHistory(int history) {
        this.history = history;
    }

    public int getDictionary() {
        return dictionary;
    }

    public void setDictionary(int dictionary) {
        this.dictionary = dictionary;
    }

    public int getAlphabetical() {
        return alphabetical;
    }

    public void setAlphabetical(int alphabetical) {
        this.alphabetical = alphabetical;
    }

    public int getNumerical() {
        return numerical;
    }

    public void setNumerical(int numerical) {
        this.numerical = numerical;
    }

    public int getQwerty() {
        return qwerty;
    }

    public void setQwerty(int qwerty) {
        this.qwerty = qwerty;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public int getOccurances() {
        return occurances;
    }

    public void setOccurances(int occurances) {
        this.occurances = occurances;
    }
    
    public int getRandomPasswordLength() {
        return randomPasswordLength;
    }

    public void setRandomPasswordLength(int randomPasswordLength) {
        this.randomPasswordLength = randomPasswordLength;
    }

    public void check(String username, String newPassword, String oldPassword) throws PasswordPolicyException {
        if ((1 == this.getUsername()) && newPassword.toLowerCase().contains(username.toLowerCase())) {
            throw new PasswordPolicyException(ConstantsServiceMessage.PASSWORDPOLICY.XW00000001);
        }
        if (oldPassword != null && newPassword.equalsIgnoreCase(oldPassword)) {
            throw new PasswordPolicyException(ConstantsServiceMessage.PASSWORDPOLICY.XW00000002);
        }
        if (newPassword.length() < this.getMinLength()) {
            throw new PasswordPolicyException(ConstantsServiceMessage.PASSWORDPOLICY.XW00000003, this.getMinLength());
        }
        if (newPassword.length() > this.getMaxLength()) {
            throw new PasswordPolicyException(ConstantsServiceMessage.PASSWORDPOLICY.XW00000004, this.getMaxLength());
        }
        int numCount = 0, upperCount = 0, lowerCount = 0, spacil = 0;
        char[] chPwd = newPassword.toCharArray();
        for (int i = 0; i < chPwd.length; i++) {
            char ch = chPwd[i];
            if (Character.isDigit(ch)) {
                numCount++;
                continue;
            }
            if (Character.isLowerCase(ch)) {
                lowerCount++;
                continue;
            }
            if (Character.isUpperCase(ch)) {
                upperCount++;
                continue;
            }
            spacil++;
        }
        if (numCount < this.getDigits()) {
            throw new PasswordPolicyException(ConstantsServiceMessage.PASSWORDPOLICY.XW00000005, this.getDigits());
        }
        if (lowerCount < this.getLowerCase()) {
            throw new PasswordPolicyException(ConstantsServiceMessage.PASSWORDPOLICY.XW00000006, this.getLowerCase());
        }
        if (upperCount < this.getUpperCase()) {
            throw new PasswordPolicyException(ConstantsServiceMessage.PASSWORDPOLICY.XW00000007, this.getUpperCase());
        }
        if (spacil < this.getSpecialChar()) {
            throw new PasswordPolicyException(ConstantsServiceMessage.PASSWORDPOLICY.XW00000008, this.getSpecialChar());
        }
    }

    @Override
    public String toString() {
        return "PasswordPolicy [id=" + id + ", minLength=" + minLength + ", maxLength=" + maxLength + ", lowerCase="
                + lowerCase + ", upperCase=" + upperCase + ", digits=" + digits + ", specialChar=" + specialChar
                + ", attempts=" + attempts + ", duration=" + duration + ", expiration=" + expiration + ", username="
                + username + ", history=" + history + ", dictionary=" + dictionary + ", alphabetical=" + alphabetical
                + ", numerical=" + numerical + ", qwerty=" + qwerty + "]";
    }

  
}
