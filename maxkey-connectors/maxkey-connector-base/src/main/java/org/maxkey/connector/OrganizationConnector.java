package org.maxkey.connector;

import org.maxkey.domain.Organizations;

public abstract class   OrganizationConnector extends AbstractConnector <Organizations> {
	
	@Override
	public boolean create(Organizations organization) throws Exception{
		return true;
	}
	
	@Override
	public boolean update(Organizations organization) throws Exception{
		return true;
	}
	
	@Override
	public boolean delete(Organizations organization) throws Exception{
		return true;
	}
	
}
