package org.maxkey.authn.support.socialsignon.service;

import java.util.List;


public interface SocialSignOnUserTokenService{

	public boolean insert(SocialSignOnUserToken socialSignOnUserToken); 
	
	public List<SocialSignOnUserToken> query (SocialSignOnUserToken socialSignOnUserToken);
	
	public SocialSignOnUserToken get (SocialSignOnUserToken socialSignOnUserToken);
	
	public boolean delete (SocialSignOnUserToken socialSignOnUserToken);
	
	public boolean update (SocialSignOnUserToken socialSignOnUserToken);
	
}
