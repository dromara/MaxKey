/**
 * 
 */
package org.maxkey.crypto.password;

import org.maxkey.crypto.ReciprocalUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Crystal.Sea
 *
 */
public class PasswordReciprocal implements PasswordEncoder{

	public static PasswordReciprocal passwordReciprocal;
	/**
	 * 
	 */
	public PasswordReciprocal() {
		
	}

	public static PasswordReciprocal getInstance(){
		
		if(passwordReciprocal==null){
			passwordReciprocal=new PasswordReciprocal();
		}
		
		return passwordReciprocal;
	}
	
	public String rawPassword(String username,String password) {
		return password+"@"+username;
	}
	
	public String encode(CharSequence rawPassword) {
		return ReciprocalUtils.encode(rawPassword.toString());
	}


	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return ReciprocalUtils.encode(rawPassword.toString()).equals(encodedPassword);
	}
	
	public String decoder(CharSequence encodedPassword) {
		return ReciprocalUtils.decoder(encodedPassword.toString());
	}

}
