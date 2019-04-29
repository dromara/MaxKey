/**
 * 
 */
package com.connsec.radius;

import java.io.IOException;
import java.net.InetAddress;

import net.jradius.client.RadiusClient;
import net.jradius.client.auth.MSCHAPv2Authenticator;
import net.jradius.client.auth.RadiusAuthenticator;
import net.jradius.dictionary.Attr_AcctInputOctets;
import net.jradius.dictionary.Attr_AcctOutputOctets;
import net.jradius.dictionary.Attr_AcctSessionId;
import net.jradius.dictionary.Attr_AcctSessionTime;
import net.jradius.dictionary.Attr_AcctStatusType;
import net.jradius.dictionary.Attr_AcctTerminateCause;
import net.jradius.dictionary.Attr_NASPort;
import net.jradius.dictionary.Attr_NASPortType;
import net.jradius.dictionary.Attr_ReplyMessage;
import net.jradius.dictionary.Attr_UserName;
import net.jradius.dictionary.Attr_UserPassword;
import net.jradius.exception.RadiusException;
import net.jradius.exception.UnknownAttributeException;
import net.jradius.packet.AccessAccept;
import net.jradius.packet.AccessRequest;
import net.jradius.packet.AccountingRequest;
import net.jradius.packet.RadiusPacket;
import net.jradius.packet.attribute.AttributeFactory;
import net.jradius.packet.attribute.AttributeList;
import net.jradius.util.RadiusRandom;

/**
 * @author Crystal.Sea
 *
 */
public class RadiusTest {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws RadiusException 
	 * @throws UnknownAttributeException 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 AttributeFactory.loadAttributeDictionary("net.jradius.dictionary.AttributeDictionaryImpl");
		 
		 try{
			 InetAddress inetAddress=InetAddress.getByName("127.0.0.1");
			 String sharedSecret="testing123";
			 RadiusClient radiusClient = new RadiusClient(
	                 inetAddress,   // InetAddress - Address of remote RADIUS Server
	                 sharedSecret,
	                 1812,
	                 1813,
	                 60); // String - Shared Secret for remote RADIUS Server
			 
			 System.out.println("RADIUS response from {}: {}"+radiusClient.getRemoteInetAddress().getCanonicalHostName());
			 	AttributeList attributeList = new AttributeList();
			   
			   
			    //attributeList.add(new Attr_NASPortType(Attr_NASPortType.Wireless80211));
			    attributeList.add(new Attr_NASPort(new Long(-1)));
			    attributeList.add(new Attr_UserName("lameuser"));
			    attributeList.add(new Attr_UserPassword("tttt"));
			    
			    AccessRequest request = new AccessRequest(radiusClient, attributeList);
			   
			    System.out.println("Sending:\n" + request.toString());
			 
			   // RadiusAuthenticator auth =new MSCHAPv2Authenticator();
			    RadiusAuthenticator auth = RadiusClient.getAuthProtocol("eap2");
			    
			    RadiusPacket reply = radiusClient.authenticate(request,auth ,3);
			    
			    System.out.println("RADIUS response from {}: {}"+radiusClient.getRemoteInetAddress().getCanonicalHostName()+","+reply.getClass().getName());
			 
			    if (reply == null) return; // Request Timed-out
			    
			    
			     System.out.println("getIdentifier:" + reply.getIdentifier());
			     System.out.println("getcode:" + reply.getCode());
			     
			    
			 
			       System.out.println("getAttributeList:\n" + reply.getAttributes().getAttributeList());
			    System.out.println("Received:\n" + reply.toString());
			    
			    boolean isAuthenticated = (reply instanceof AccessAccept);
			    
			    System.out.println("isAuthenticated : \n" + isAuthenticated);
			    
			    System.out.println("AccessReject : \n" + (reply instanceof net.jradius.packet.AccessReject));
			    
			    System.out.println("PasswordReject : \n" + (reply instanceof net.jradius.packet.PasswordReject));
			   
			    String replyMessage = (String) reply.getAttributeValue(Attr_ReplyMessage.TYPE);
			 
			    if (replyMessage != null)
			    {
			        System.out.println("Reply Message: " + replyMessage);
			    }else{
			    	System.out.println("Reply Message: " + "error . ");
			    }
			    
			    /*
			    attributeList.add(new Attr_AcctSessionId(RadiusRandom.getRandomString(24)));
			    
			    request = new AccountingRequest(radiusClient, attributeList);
			    request.addAttribute(new Attr_AcctStatusType(Attr_AcctStatusType.Start));
			 
			    reply = radiusClient.accounting(request, 5);
			    
			    
			    request = new AccountingRequest(radiusClient, attributeList);
			    request.addAttribute(new Attr_AcctStatusType(Attr_AcctStatusType.Stop));
			    request.addAttribute(new Attr_AcctInputOctets(new Long(10)));
			    request.addAttribute(new Attr_AcctOutputOctets(new Long(10)));
			    request.addAttribute(new Attr_AcctSessionTime(new Long(60)));
			    request.addAttribute(new Attr_AcctTerminateCause(Attr_AcctTerminateCause.UserRequest));
			 
			    reply = radiusClient.accounting(request, 5);*/
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		    
		    

	}

	 //��InetAddress �е���Ϣ��ʾ����   
    public static void showInfo(InetAddress IP){   
        String name = IP.getHostName();   
        String address = IP.getHostAddress();   
        System.out.println(name);   
        System.out.println(address);   
        System.out.println("------------------------------");   
    }   
}
