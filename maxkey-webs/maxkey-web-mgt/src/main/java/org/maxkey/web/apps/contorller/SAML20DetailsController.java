/*
 * Copyright [2020] [MaxKey of copyright http://www.maxkey.top]
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 

package org.maxkey.web.apps.contorller;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.maxkey.authz.saml20.metadata.MetadataDescriptorUtil;
import org.maxkey.configuration.ApplicationConfig;
import org.maxkey.constants.ConstsOperateMessage;
import org.maxkey.constants.ConstsProtocols;
import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.crypto.cert.X509CertUtils;
import org.maxkey.crypto.keystore.KeyStoreLoader;
import org.maxkey.crypto.keystore.KeyStoreUtil;
import org.maxkey.entity.apps.AppsSAML20Details;
import org.maxkey.persistence.service.AppsSaml20DetailsService;
import org.maxkey.web.WebContext;
import org.maxkey.web.message.Message;
import org.maxkey.web.message.MessageType;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.SPSSODescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value={"/apps/saml20"})
public class SAML20DetailsController   extends BaseAppContorller {
	final static Logger _logger = LoggerFactory.getLogger(SAML20DetailsController.class);
	
	@Autowired
	@Qualifier("keyStoreLoader")
	private KeyStoreLoader idpKeyStoreLoader;
	
	@Autowired
	AppsSaml20DetailsService saml20DetailsService;
	
	@Autowired
	ApplicationConfig applicationConfig;
	
	@RequestMapping(value = { "/forwardAdd" })
	public ModelAndView forwardAdd() {
		ModelAndView modelAndView=new ModelAndView("apps/saml20/appAdd");
		AppsSAML20Details saml20Details=new AppsSAML20Details();
		saml20Details.setSecret(ReciprocalUtils.generateKey(""));
		saml20Details.setProtocol(ConstsProtocols.SAML20);
		saml20Details.setId(saml20Details.generateId());
		modelAndView.addObject("model",saml20Details);
		 
		return modelAndView;
	}
	
	
	@RequestMapping(value={"/add"})
	public ModelAndView insert(@ModelAttribute("saml20Details") AppsSAML20Details saml20Details) {
		_logger.debug("-Add  :" + saml20Details);

		try {
			transform(saml20Details);
		} catch (Exception e) {
			e.printStackTrace();
		}
		saml20Details.setInstId(WebContext.getUserInfo().getInstId());
		saml20DetailsService.insert(saml20Details);
		if (appsService.insertApp(saml20Details)) {
			  new Message(WebContext.getI18nValue(ConstsOperateMessage.INSERT_SUCCESS),MessageType.success);
			
		} else {
			  new Message(WebContext.getI18nValue(ConstsOperateMessage.INSERT_SUCCESS),MessageType.error);
		}
		return   WebContext.forward("forwardUpdate/"+saml20Details.getId());
	}
	
	@RequestMapping(value = { "/forwardUpdate/{id}" })
	public ModelAndView forwardUpdate(@PathVariable("id") String id) {
		ModelAndView modelAndView=new ModelAndView("apps/saml20/appUpdate");
		AppsSAML20Details saml20Details=saml20DetailsService.getAppDetails(id , false);
		decoderSecret(saml20Details);
		saml20Details.transIconBase64();
		modelAndView.addObject("model",saml20Details);
		modelAndView.addObject("authzURI",applicationConfig.getAuthzUri());
		return modelAndView;
	}
	/**
	 * modify
	 * @param application
	 * @return
	 */
	@RequestMapping(value={"/update"})  
	public ModelAndView update(@ModelAttribute("saml20Details") AppsSAML20Details saml20Details) {
		//
		_logger.debug("-update  application :" + saml20Details);
	   _logger.debug("");
		try {
			transform(saml20Details);
		} catch (Exception e) {
			e.printStackTrace();
		}
		saml20Details.setInstId(WebContext.getUserInfo().getInstId());
		saml20DetailsService.update(saml20Details);
		if (appsService.updateApp(saml20Details)) {
			 new Message(WebContext.getI18nValue(ConstsOperateMessage.UPDATE_SUCCESS),MessageType.success);
			
		} else {
			 new Message(WebContext.getI18nValue(ConstsOperateMessage.UPDATE_ERROR),MessageType.error);
		}
		return   WebContext.forward("forwardUpdate/"+saml20Details.getId());
	}
	

	@ResponseBody
	@RequestMapping(value={"/delete/{id}"})
	public Message delete(@PathVariable("id") String id) {
		_logger.debug("-delete  application :" + id);
		if (saml20DetailsService.remove(id)&&appsService.remove(id)) {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.DELETE_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.DELETE_SUCCESS),MessageType.error);
		}
	}
	
	protected AppsSAML20Details transform(AppsSAML20Details samlDetails) throws Exception{
		
		super.transform(samlDetails);
		
		if(null==samlDetails.getFileType()||samlDetails.getFileType().equals("certificate")){//certificate file
			try {
			    if (null!=samlDetails.getMetaFile()&&!samlDetails.getMetaFile().isEmpty()) {
					InputStream isCert = samlDetails.getMetaFile().getInputStream();
					X509Certificate trustCert = X509CertUtils.loadCertFromInputStream(isCert);
					samlDetails.setTrustCert(trustCert);
					isCert.close();
			    }
			} catch (IOException e) {
				_logger.error("read certificate file error .", e);
				throw new Exception("read certificate file error", e);
			}
		}else if(samlDetails.getFileType().equals("metadata_file")){//metadata file
		    if (null!=samlDetails.getMetaFile()&&!samlDetails.getMetaFile().isEmpty()) {
		        samlDetails = resolveMetaData(samlDetails,samlDetails.getMetaFile().getInputStream());
		    }
		}else if(samlDetails.getFileType().equals("metadata_url")){//metadata url
		    CloseableHttpClient httpClient = HttpClients.createDefault();
		    HttpPost post = new HttpPost(samlDetails.getMetaUrl());
            CloseableHttpResponse response = httpClient.execute(post);
            samlDetails = resolveMetaData(samlDetails,response.getEntity().getContent());;
            response.close();
            httpClient.close();
		}
		
		if(samlDetails.getTrustCert()!=null) {
    		samlDetails.setCertSubject(samlDetails.getTrustCert().getSubjectDN().getName());
    		samlDetails.setCertExpiration(samlDetails.getTrustCert().getNotAfter().toString());
    
    		samlDetails.setCertIssuer(X509CertUtils.getCommonName(samlDetails.getTrustCert().getIssuerX500Principal()));
    		
    		KeyStore keyStore = KeyStoreUtil.clone(idpKeyStoreLoader.getKeyStore(),idpKeyStoreLoader.getKeystorePassword());
    
    		KeyStore trustKeyStore = null;
    		if (!samlDetails.getEntityId().equals("")) {
    			trustKeyStore = KeyStoreUtil.importTrustCertificate(keyStore,samlDetails.getTrustCert(), samlDetails.getEntityId());
    		} else {
    			trustKeyStore = KeyStoreUtil.importTrustCertificate(keyStore,samlDetails.getTrustCert());
    		}
    
    		byte[] keyStoreByte = KeyStoreUtil.keyStore2Bytes(trustKeyStore,idpKeyStoreLoader.getKeystorePassword());
    
    		// store KeyStore content
    		samlDetails.setKeyStore(keyStoreByte);
		}
		
		return samlDetails;
	}
	
	public AppsSAML20Details resolveMetaData(AppsSAML20Details samlDetails,InputStream inputStream) throws Exception {
	    X509Certificate trustCert = null;
	    EntityDescriptor entityDescriptor;
        try {
            entityDescriptor = MetadataDescriptorUtil.getInstance().getEntityDescriptor(inputStream);
        } catch (IOException e) {
            _logger.error("metadata  file resolve error .", e);
            throw new Exception("metadata  file resolve error", e);
        }
        SPSSODescriptor sPSSODescriptor = entityDescriptor.getSPSSODescriptor(SAMLConstants.SAML20P_NS);
        String b64Encoder = sPSSODescriptor.getKeyDescriptors().get(0).getKeyInfo().getX509Datas().get(0).getX509Certificates().get(0).getValue();

        trustCert = X509CertUtils.loadCertFromB64Encoded(b64Encoder);
        
        samlDetails.setTrustCert(trustCert);
        samlDetails.setSpAcsUrl(sPSSODescriptor.getAssertionConsumerServices().get(0).getLocation());
        samlDetails.setEntityId(entityDescriptor.getEntityID());
        
        if(samlDetails.getIssuer()==null || samlDetails.getIssuer().equals("")) {
            samlDetails.setIssuer(entityDescriptor.getEntityID());
        }
        
        if(samlDetails.getAudience()==null || samlDetails.getAudience().equals("")) {
            samlDetails.setAudience(entityDescriptor.getEntityID());
        }

        _logger.info("SPSSODescriptor EntityID "+ entityDescriptor.getEntityID());
        return samlDetails;
	}
	
}
