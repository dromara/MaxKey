/**
 * 
 */
package org.maxkey.authn.realm;

/**
 * @author Crystal.Sea
 *
 */
public interface IAuthenticationServer {

	public boolean authenticate(String username, String password) ;

}
