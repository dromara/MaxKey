package org.maxkey.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.mybatis.jpa.persistence.JpaBaseDomain;
import org.maxkey.constants.SERVICEMESSAGE;
import org.maxkey.exception.PasswordPolicyException;

/**
 * @author Crystal.Sea
 *
 */

@Table(name = "PASSWORD_POLICY")  
public class PasswordPolicy extends JpaBaseDomain implements java.io.Serializable {
	
	private static final long serialVersionUID = -4797776994287829182L;
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.AUTO,generator="uuid")
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
	 * least  uppercase letter
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
	 * 0   no
	 * 1   yes
	 */
	@Column
	private int username;
	
	/**
	 * not include password list
	 */
	@Column
	private String simplePasswords;



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



	/**
	 * @return the simplePasswords
	 */
	public String getSimplePasswords() {
		return simplePasswords;
	}



	/**
	 * @param simplePasswords the simplePasswords to set
	 */
	public void setSimplePasswords(String simplePasswords) {
		this.simplePasswords = simplePasswords;
	}



	public void check(String username, String newPassword,String oldPassword)
			throws PasswordPolicyException {
		if((1==this.getUsername())
				&&newPassword.toLowerCase().contains(username.toLowerCase())){
			throw new PasswordPolicyException(
					SERVICEMESSAGE.PASSWORDPOLICY.XW00000001);
		}
		if(oldPassword!=null&&newPassword.equalsIgnoreCase(oldPassword)){
			throw new PasswordPolicyException(
					SERVICEMESSAGE.PASSWORDPOLICY.XW00000002);
		}
		if (newPassword.length() < this.getMinLength()) {
			throw new PasswordPolicyException(
					SERVICEMESSAGE.PASSWORDPOLICY.XW00000003,
					this.getMinLength());
		}
		if (newPassword.length() > this.getMaxLength()) {
			throw new PasswordPolicyException(
					SERVICEMESSAGE.PASSWORDPOLICY.XW00000004,
					this.getMaxLength());
		}
		int numCount = 0, upperCount = 0, lowerCount = 0, spacil = 0;
		char[] chPwd = newPassword.toCharArray();
		for (int i = 0; i < chPwd.length; i++) {
			char ch = chPwd[i];
			if (Character.isDigit(ch)) {
				numCount++;
				continue;
			}
			if(Character.isLowerCase(ch)){
				lowerCount++;
				continue;
			}
			if(Character.isUpperCase(ch)){
				upperCount++;
				continue;
			}
			spacil++;
		}
		if(numCount<this.getDigits()){
			throw new PasswordPolicyException(
					SERVICEMESSAGE.PASSWORDPOLICY.XW00000005,
					this.getDigits());
		}
		if(lowerCount<this.getLowerCase()){
			throw new PasswordPolicyException(
					SERVICEMESSAGE.PASSWORDPOLICY.XW00000006,
					this.getLowerCase());
		}
		if(upperCount<this.getUpperCase()){
			throw new PasswordPolicyException(
					SERVICEMESSAGE.PASSWORDPOLICY.XW00000007,
					this.getUpperCase());
		}
		if(spacil<this.getSpecialChar()){
			throw new PasswordPolicyException(
					SERVICEMESSAGE.PASSWORDPOLICY.XW00000008,
					this.getSpecialChar());
		}
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PasswordPolicy [minLength=" + minLength + ", maxLength="
				+ maxLength + ", lowerCase=" + lowerCase + ", upperCase="
				+ upperCase + ", digits=" + digits + ", specialChar="
				+ specialChar + ", attempts=" + attempts + ", duration="
				+ duration + ", expiration=" + expiration + ", username="
				+ username + ", simplePasswords=" + simplePasswords + "]";
	}
	
	
}
