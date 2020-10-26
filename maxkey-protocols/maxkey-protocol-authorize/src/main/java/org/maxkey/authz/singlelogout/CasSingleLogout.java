package org.maxkey.authz.singlelogout;

public class CasSingleLogout extends SingleLogout{

    public String logoutRequestMessage=
            "<samlp:LogoutRequest xmlns:samlp=\"urn:oasis:names:tc:SAML:2.0:protocol\" ID=\"%s\" Version=\"2.0\" "
            + "IssueInstant=\"%s\"><saml:NameID xmlns:saml=\"urn:oasis:names:tc:SAML:2.0:assertion\">%s"
            + "</saml:NameID><samlp:SessionIndex>%s</samlp:SessionIndex></samlp:LogoutRequest>";

    @Override
    public void sendRequest() {
        // TODO Auto-generated method stub
        
    }
    
}
