package org.maxkey.authn.realm;

/**
 * IAuthenticationServer .
 * @author Crystal.Sea
 *
 */
public interface IAuthenticationServer {

    public boolean authenticate(String username, String password);

}
