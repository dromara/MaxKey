package org.maxkey.web.contorller;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.util.DateUtils;
import org.maxkey.util.JsonUtils;
import org.maxkey.web.KerberosToken;
import org.maxkey.web.KerberosTokenConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



/**
 * @author Crystal.Sea
 *
 */

@Controller
public class KerberosController {
	private static Logger _logger = LoggerFactory.getLogger(KerberosController.class);
 	
	@Autowired
	@Qualifier("kerberosTokenConfig")
	private KerberosTokenConfig kerberosTokenConfig;
	
	/**
	 * this is Kerberos Logined Controller
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
 	@RequestMapping(value={"/authn"})
	public ModelAndView authn(HttpServletRequest request,HttpServletResponse response) {
 		_logger.debug("kerberos /authn.");
 		ModelAndView modelAndView = new ModelAndView();
 		String kerberosPrincipal=request.getRemoteUser();
 		_logger.debug("kerberos Principal is "+kerberosPrincipal);
 	
 		if(kerberosPrincipal==null){
 			modelAndView.setViewName("error");
 			modelAndView.addObject("errorMessage", "kerberos Principal is null .");
 			return modelAndView;
 		}else{
	 		String shortPrincipal=kerberosPrincipal;
	 		if(kerberosPrincipal.indexOf("@")>-1){
	 			shortPrincipal=kerberosPrincipal.substring(0, kerberosPrincipal.indexOf("@"));
	 		}
	 		
	 		_logger.debug("kerberos Short Principal is "+shortPrincipal);
	 		
	 		/**
	 		 * get current date time ,
	 		 * then plus  token validity Minutes,
	 		 * this is token expired time
	 		 */
	 		DateTime datetime=new DateTime(new Date(), ISOChronology.getInstanceUTC());
	 		datetime=datetime.plusMinutes(kerberosTokenConfig.getValidity());
	 		
	 		/**
	 		 * build Kerberos Token
	 		 * include parameter
	 		 * Principal
	 		 * FullPrincipal
	 		 * NotOnOrAfter
	 		 * UserDomain
	 		 */
	 		KerberosToken kerberosToken=new KerberosToken();
	 		kerberosToken.setFullPrincipal(kerberosPrincipal);
	 		kerberosToken.setPrincipal(shortPrincipal);
	 		kerberosToken.setNotOnOrAfter(DateUtils.toUtc(datetime));
	 		kerberosToken.setUserDomain(kerberosTokenConfig.getUserDomain());
	 		
	 		/**
	 		 * transform kerberosToken to json format
	 		 */
	 		String kerberosTokenString=JsonUtils.object2Json(kerberosToken);
			
			_logger.debug("kerberosToken json : \n"+kerberosTokenString);
			
			/**
			 * encrypted KerberosToken use AES Algorithm ,
			 * then to HEX
			 */
			String encryptedKerberosTokenString=ReciprocalUtils.aesEncode(kerberosTokenString, kerberosTokenConfig.getCrypto());

	 		_logger.debug("kerberosToken json encrypted : \n"+encryptedKerberosTokenString);
	 		
			modelAndView.addObject("kerberosPrincipal", kerberosPrincipal);
			modelAndView.addObject("shortPrincipal", shortPrincipal);
			modelAndView.addObject("kerberosToken", kerberosTokenString);
			modelAndView.addObject("encryptedKerberosToken", encryptedKerberosTokenString);
			modelAndView.addObject("kerberosUserDomain", kerberosTokenConfig.getUserDomain());
			modelAndView.addObject("tokenPostLocation", kerberosTokenConfig.getTokenPostLocation());
 		}
		
 		_logger.debug("Diagnose View  : "+kerberosTokenConfig.isDiagnose());
 		
		if(kerberosTokenConfig.isDiagnose()){
			/**
			 * forward to kerberos_diagnose.jsp, 
			 * then show  details
			 */
			modelAndView.setViewName("kerberos_diagnose");
		}else{
			/**
			 * forward to kerberos.jsp,
			 * then post to ConnSec  Access login URI
			 */
			modelAndView.setViewName("kerberos");
		}
		return modelAndView;
 	}

	public void setKerberosTokenConfig(KerberosTokenConfig kerberosTokenConfig) {
		this.kerberosTokenConfig = kerberosTokenConfig;
	}
}
