package org.maxkey.crypto;

import org.maxkey.crypto.password.PasswordGen;

public class PasswordGenTest {

	public PasswordGenTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PasswordGen gen=new PasswordGen();
		for(int i=1;i<100;i++){
			System.out.println(gen.gen());
			System.out.println(gen.gen(6));
			System.out.println(gen.gen(2,2,2,2));
		}
		
	}

}
