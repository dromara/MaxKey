package org.maxkey.web.tag;

public class St {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String cv="com.ddm.core.utils.TestClass@InnerClass1@InnerClass2@p2";
		
		String[] c = cv.trim().split("@");
		for(String cs : c) {
			System.out.println(cs);
		}
	}

}
