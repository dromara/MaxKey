package org.maxkey.authn.support.socialsignon.service;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class SocialSignOnProviderService{
	private static Logger _logger = LoggerFactory.getLogger(SocialSignOnProviderService.class);
	
	List<SocialSignOnProvider> socialSignOnProviders;
	
	HashMap<String ,SocialSignOnProvider>socialSignOnProviderMaps=new HashMap<String ,SocialSignOnProvider>();
	
	
	public SocialSignOnProvider get(String provider){
		return socialSignOnProviderMaps.get(provider);
	}

	public List<SocialSignOnProvider> getSocialSignOnProviders() {
		return socialSignOnProviders;
	}

	public void setSocialSignOnProviders(
			List<SocialSignOnProvider> socialSignOnProviders) {
		
		this.socialSignOnProviders = socialSignOnProviders;
		
		for(SocialSignOnProvider socialSignOnProvider : socialSignOnProviders){
			socialSignOnProviderMaps.put(socialSignOnProvider.getProvider(), socialSignOnProvider);
		}
		
		_logger.debug(""+socialSignOnProviders);
	}
	
}
