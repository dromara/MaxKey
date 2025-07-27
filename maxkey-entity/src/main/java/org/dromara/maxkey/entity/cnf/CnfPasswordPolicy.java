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


package org.dromara.maxkey.entity.cnf;

import org.dromara.mybatis.jpa.entity.JpaEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * @author Crystal.Sea
 *
 */

@Entity
@Table(name = "MXK_CNF_PASSWORD_POLICY")
public class CnfPasswordPolicy extends JpaEntity implements java.io.Serializable {

    private static final long serialVersionUID = -4797776994287829182L;
    @Id
    @Column
    @GeneratedValue
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

    List<String> policMessageList;

    public List<String> getPolicMessageList() {
        return policMessageList;
    }

    public void setPolicMessageList(List<String> policMessageList) {
        this.policMessageList = policMessageList;
    }

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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PasswordPolicy [id=");
        builder.append(id);
        builder.append(", minLength=");
        builder.append(minLength);
        builder.append(", maxLength=");
        builder.append(maxLength);
        builder.append(", lowerCase=");
        builder.append(lowerCase);
        builder.append(", upperCase=");
        builder.append(upperCase);
        builder.append(", digits=");
        builder.append(digits);
        builder.append(", specialChar=");
        builder.append(specialChar);
        builder.append(", attempts=");
        builder.append(attempts);
        builder.append(", duration=");
        builder.append(duration);
        builder.append(", expiration=");
        builder.append(expiration);
        builder.append(", username=");
        builder.append(username);
        builder.append(", history=");
        builder.append(history);
        builder.append(", dictionary=");
        builder.append(dictionary);
        builder.append(", alphabetical=");
        builder.append(alphabetical);
        builder.append(", numerical=");
        builder.append(numerical);
        builder.append(", qwerty=");
        builder.append(qwerty);
        builder.append(", occurances=");
        builder.append(occurances);
        builder.append(", randomPasswordLength=");
        builder.append(randomPasswordLength);
        builder.append("]");
        return builder.toString();
    }


}
