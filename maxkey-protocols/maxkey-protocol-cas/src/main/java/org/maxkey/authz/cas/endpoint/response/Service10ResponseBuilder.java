package org.maxkey.authz.cas.endpoint.response;

public class Service10ResponseBuilder extends CasServiceResponse {
	
	@Override
	public String  serviceResponseBuilder() {
		StringBuffer responseResult=new StringBuffer("");
		if(result){
			responseResult.append("yes").append("\n").append(user);
		}else{
			responseResult.append("no").append("\n").append("\n");
		}
		return responseResult.toString();
	}

}
