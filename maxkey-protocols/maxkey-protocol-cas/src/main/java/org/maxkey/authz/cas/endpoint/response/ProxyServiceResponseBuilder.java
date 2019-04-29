package org.maxkey.authz.cas.endpoint.response;

import org.maxkey.authz.cas.endpoint.ticket.CasConstants;

public class ProxyServiceResponseBuilder extends  ServiceResponseBuilder{

	public ProxyServiceResponseBuilder() {
		
	}

	@Override
	public String  serviceResponseBuilder() {
		if(format.equalsIgnoreCase(CasConstants.FORMAT_TYPE.XML)){
			return serviceResponseXmlBuilder();
		}else{
			return serviceResponseJsonBuilder();
		}
	}
	
	@Override
	public String  serviceResponseXmlBuilder() {
		StringBuffer responseResult=new StringBuffer("");
		responseResult.append("<cas:serviceResponse xmlns:cas=\"http://www.yale.edu/tp/cas\">");
		if(result){
			responseResult.append("<cas:proxySuccess>");
			responseResult.append("<cas:proxyTicket>").append(ticket).append("</cas:proxyTicket>");
			responseResult.append("</cas:proxySuccess>");
		}else{
			responseResult.append("<cas:proxyFailure code=\""+code+"\">");
			responseResult.append(this.description);
			responseResult.append("</cas:proxyFailure>");
		}
		responseResult.append("</cas:serviceResponse>");
		return responseResult.toString();
	}
	
	@Override
	public String  serviceResponseJsonBuilder() {
		StringBuffer responseResult=new StringBuffer("");
		responseResult.append("{\"serviceResponse\" :{");
		if(result){
			responseResult.append("\"proxySuccess\" : {");
			responseResult.append("\"proxyTicket\" : \"").append(ticket).append("\"");
			responseResult.append("}");
		}else{
			responseResult.append("\"authenticationFailure\" : {");
			responseResult.append("\"code\" : \"").append(this.code).append("\"");
			responseResult.append(",\"description\" : \"").append(this.description).append("\"");
			responseResult.append("}");
		}
		responseResult.append("}");
		responseResult.append("}");
		return responseResult.toString();
	}
	

}
