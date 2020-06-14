package org.maxkey.connector;

import org.maxkey.domain.GroupMember;
import org.maxkey.domain.Groups;

public abstract class   GroupConnector  extends AbstractConnector <Groups> {
	
	@Override
	public boolean create(Groups group) throws Exception{
		return true;
	}
	
	@Override
	public boolean update(Groups group) throws Exception{
		return true;
	}
	
	@Override
	public boolean delete(Groups group) throws Exception{
		return true;
	}
	

	public boolean addMember(GroupMember groupMember) throws Exception{
		return true;
	}
	

	public boolean deleteMember(GroupMember groupMember) throws Exception{
		return true;
	}
	
}
