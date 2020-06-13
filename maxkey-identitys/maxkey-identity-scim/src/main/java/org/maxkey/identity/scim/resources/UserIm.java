package org.maxkey.identity.scim.resources;

import java.io.Serializable;

public class UserIm extends MultiValuedAttribute implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -529297556948872883L;

    public static class UserImType {
        public static final String AIM = "aim";
        public static final String GTALK = "gtalk";
        public static final String ICQ = "icq";
        public static final String XMPP = "xmpp";
        public static final String MSN = "msn";
        public static final String SKYPE = "skype";
        public static final String QQ = "qq";
        public static final String YAHOO = "yahoo";
    }
}
