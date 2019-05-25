package org.maxkey.crypto.password;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordReciprocalTest {

	public PasswordReciprocalTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BCryptPasswordEncoder spe= new BCryptPasswordEncoder();
		String pass=PasswordReciprocal.getInstance().rawPassword("admin", "admin");
		String epass=spe.encode(pass);
		System.out.println("PasswordEncoder "+epass); 
		
		System.out.println(PasswordReciprocal.getInstance().decoder("bb2002b9f55b05d3e0e6f34ec5321051"));
	}

}
