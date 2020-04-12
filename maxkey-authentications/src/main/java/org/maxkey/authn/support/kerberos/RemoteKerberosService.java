package org.maxkey.authn.support.kerberos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.maxkey.constants.ConstantsLoginType;
import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.util.DateUtils;
import org.maxkey.util.JsonUtils;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RemoteKerberosService  implements KerberosService{
	private static Logger _logger = LoggerFactory.getLogger(RemoteKerberosService.class);
	List<KerberosProxy> kerberosProxys;
	
	public boolean login(String kerberosTokenString,String kerberosUserDomain){
		_logger.debug("encoder Kerberos Token "+kerberosTokenString);
		_logger.debug("kerberos UserDomain "+kerberosUserDomain);
		
		String decoderKerberosToken=null;
		for(KerberosProxy kerberosProxy : kerberosProxys){
			if(kerberosProxy.getUserdomain().equalsIgnoreCase(kerberosUserDomain)){
				decoderKerberosToken=ReciprocalUtils.aesDecoder(kerberosTokenString, kerberosProxy.getCrypto());
				break;
			}
		}
		_logger.debug("decoder Kerberos Token "+decoderKerberosToken);
		KerberosToken  kerberosToken=new KerberosToken();
		kerberosToken=(KerberosToken)JsonUtils.json2Object(decoderKerberosToken, kerberosToken);
		_logger.debug("Kerberos Token "+kerberosToken);
		
		DateTime notOnOrAfter=DateUtils.toUtcDate(kerberosToken.getNotOnOrAfter());
		_logger.debug("Kerberos Token is After Now  "+notOnOrAfter.isAfterNow());
		if(notOnOrAfter.isAfterNow()){
	    	return WebContext.setAuthentication(kerberosToken.getPrincipal(),ConstantsLoginType.KERBEROS,kerberosUserDomain,"","success");
		}else{
			
			return false;
		}
			
	}

	public List<KerberosProxy> getKerberosProxys() {
		return kerberosProxys;
	}

	public void setKerberosProxys(List<KerberosProxy> kerberosProxys) {
		this.kerberosProxys = kerberosProxys;
	}
	
	public  String buildKerberosProxys(){
		List<Map<String,String>>userDomainUrlList=new ArrayList<Map<String,String>>();
		for (KerberosProxy kerberosProxy :kerberosProxys){
			Map<String,String> userDomainUrl =new HashMap<String,String>();
			userDomainUrl.put("userDomain", kerberosProxy.getUserdomain());
			userDomainUrl.put("redirectUri", kerberosProxy.getRedirectUri());
			userDomainUrlList.add(userDomainUrl);
		}
		_logger.debug(""+userDomainUrlList);
		String userDomainUrlJson=JsonUtils.object2Json(userDomainUrlList);
		_logger.debug("userDomain Url Json "+userDomainUrlJson);
		return userDomainUrlJson;
	}
}
