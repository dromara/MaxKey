package org.maxkey.util;

import java.security.Provider;
import java.security.Security;

import org.maxkey.client.crypto.ReciprocalUtils;

public class InstanceTest {

    public static void main(String[] args) {
        if(System.getProperty("java.version").startsWith("1.8")) {
            System.out.println("1.8");
            Security.addProvider((Provider)Instance.newInstance("com.sun.crypto.provider.SunJCE"));
            System.out.println(ReciprocalUtils.encode("ddddd"));
            
            System.out.println(ReciprocalUtils.encode("ddfs"));
        }else {
            System.out.println("other");
        }
        
    }

}
