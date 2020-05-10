package org.maxkey.authn.support.socialsignon.service;

import java.util.List;


public interface SocialsAssociateService{

	public boolean insert(SocialsAssociate socialsAssociate); 
	
	public List<SocialsAssociate> query (SocialsAssociate socialsAssociate);
	
	public SocialsAssociate get (SocialsAssociate socialsAssociate);
	
	public boolean delete (SocialsAssociate socialsAssociate);
	
	public boolean update (SocialsAssociate socialsAssociate);
	
}
