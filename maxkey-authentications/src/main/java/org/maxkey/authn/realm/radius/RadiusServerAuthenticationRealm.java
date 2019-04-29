package org.maxkey.authn.realm.radius;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import net.jradius.packet.attribute.AttributeFactory;

import org.maxkey.authn.realm.AbstractAuthenticationRealm;
import org.maxkey.authn.realm.IAuthenticationServer;
import org.maxkey.domain.UserInfo;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;


public class RadiusServerAuthenticationRealm extends AbstractAuthenticationRealm{
	private final static Logger _logger = LoggerFactory.getLogger(RadiusServerAuthenticationRealm.class);

	 /** Load the dictionary implementation. */
    static {
        AttributeFactory.loadAttributeDictionary("net.jradius.dictionary.AttributeDictionaryImpl");
    }
    
    /** Array of RADIUS servers to authenticate against. */
    @NotNull
    @Size(min=1)
    private List<IAuthenticationServer> jradiusServers;
    
    
	/**
	 * @param ldapCluster
	 */
	public RadiusServerAuthenticationRealm() {

	}


	@Override
	public boolean passwordMatches(UserInfo userInfo, String password) {
		 boolean isAuthenticated=false;
		 for (final IAuthenticationServer radiusServer : this.jradiusServers) {
            _logger.debug("Attempting to authenticate {} at {}", userInfo.getUsername(), radiusServer);
            isAuthenticated= radiusServer.authenticate(userInfo.getUsername(), password);
            if (isAuthenticated ) {
            	return true;
            }
		 }
		 if(!isAuthenticated){
			 throw new BadCredentialsException(WebContext.getI18nValue("login.error.password"));
		 }
		return false;
	}


	public void setJradiusServers(List<IAuthenticationServer> jradiusServers) {
		this.jradiusServers = jradiusServers;
	}

}
