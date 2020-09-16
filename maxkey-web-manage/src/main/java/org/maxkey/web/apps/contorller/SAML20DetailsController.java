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
import java.util.List;

import org.maxkey.authz.saml20.metadata.MetadataDescriptorUtil;
import org.maxkey.configuration.ApplicationConfig;
import org.maxkey.constants.ConstantsOperateMessage;
import org.maxkey.constants.ConstantsProtocols;
import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.crypto.cert.NameUtil;
import org.maxkey.crypto.cert.X509CertUtils;
import org.maxkey.crypto.keystore.KeyStoreLoader;
import org.maxkey.crypto.keystore.KeyStoreUtil;
import org.maxkey.domain.apps.AppsSAML20Details;
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
		saml20Details.setProtocol(ConstantsProtocols.SAML20);
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
		saml20DetailsService.insert(saml20Details);
		if (appsService.insertApp(saml20Details)) {
			  new Message(WebContext.getI18nValue(ConstantsOperateMessage.INSERT_SUCCESS),MessageType.success);
			
		} else {
			  new Message(WebContext.getI18nValue(ConstantsOperateMessage.INSERT_SUCCESS),MessageType.error);
		}
		return   WebContext.forward("forwardUpdate/"+saml20Details.getId());
	}
	
	@RequestMapping(value = { "/forwardUpdate/{id}" })
	public ModelAndView forwardUpdate(@PathVariable("id") String id) {
		ModelAndView modelAndView=new ModelAndView("apps/saml20/appUpdate");
		AppsSAML20Details saml20Details=saml20DetailsService.getAppDetails(id);
		decoderSecret(saml20Details);
		WebContext.setAttribute(saml20Details.getId(), saml20Details.getIcon());
		modelAndView.addObject("model",saml20Details);
		modelAndView.addObject("maxKeyURI",applicationConfig.getMaxKeyUri());
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
		saml20DetailsService.update(saml20Details);
		if (appsService.updateApp(saml20Details)) {
			 new Message(WebContext.getI18nValue(ConstantsOperateMessage.UPDATE_SUCCESS),MessageType.success);
			
		} else {
			 new Message(WebContext.getI18nValue(ConstantsOperateMessage.UPDATE_ERROR),MessageType.error);
		}
		return   WebContext.forward("forwardUpdate/"+saml20Details.getId());
	}
	

	@ResponseBody
	@RequestMapping(value={"/delete/{id}"})
	public Message delete(@PathVariable("id") String id) {
		_logger.debug("-delete  application :" + id);
		if (saml20DetailsService.remove(id)&&appsService.remove(id)) {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.DELETE_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.DELETE_SUCCESS),MessageType.error);
		}
	}
	
	protected AppsSAML20Details transform(AppsSAML20Details samlDetails) throws Exception{
		
		super.transform(samlDetails);
		
		X509Certificate trustCert = null;
		if (null!=samlDetails.getCertMetaFile()&&!samlDetails.getCertMetaFile().isEmpty()) {
			if(null==samlDetails.getFileType()||samlDetails.getFileType().equals("certificate")){//certificate file
				try {
					InputStream isCert = samlDetails.getCertMetaFile().getInputStream();
					trustCert = X509CertUtils.loadCertFromInputStream(isCert);
					isCert.close();
				} catch (IOException e) {
					_logger.error("read certificate file error .", e);
					throw new Exception("read certificate file error", e);
				}
			}else if(samlDetails.getFileType().equals("metadata")){//metadata file
				EntityDescriptor entityDescriptor;
				try {
					entityDescriptor = MetadataDescriptorUtil.getInstance().getEntityDescriptor(samlDetails.getCertMetaFile().getInputStream());
				} catch (IOException e) {
					_logger.error("metadata  file resolve error .", e);
					throw new Exception("metadata  file resolve error", e);
				}
				SPSSODescriptor sPSSODescriptor = entityDescriptor.getSPSSODescriptor(SAMLConstants.SAML20P_NS);
				String b64Encoder = sPSSODescriptor.getKeyDescriptors().get(0).getKeyInfo().getX509Datas().get(0).getX509Certificates().get(0).getValue();

				trustCert = X509CertUtils.loadCertFromB64Encoded(b64Encoder);

				samlDetails.setSpAcsUrl(sPSSODescriptor.getAssertionConsumerServices().get(0).getLocation());
				samlDetails.setEntityId(entityDescriptor.getEntityID());

				_logger.info("SPSSODescriptor EntityID"+ entityDescriptor.getEntityID());
			}

			samlDetails.setCertSubject(trustCert.getSubjectDN().getName());
			samlDetails.setCertExpiration(trustCert.getNotAfter().toString());

			samlDetails.setCertIssuer(NameUtil.getCommonName(trustCert.getIssuerX500Principal()));
			
			KeyStore keyStore = KeyStoreUtil.clone(idpKeyStoreLoader.getKeyStore(),idpKeyStoreLoader.getKeystorePassword());

			KeyStore trustKeyStore = null;
			if (!samlDetails.getEntityId().equals("")) {
				trustKeyStore = KeyStoreUtil.importTrustCertificate(keyStore,trustCert, samlDetails.getEntityId());
			} else {
				trustKeyStore = KeyStoreUtil.importTrustCertificate(keyStore,trustCert);
			}

			byte[] keyStoreByte = KeyStoreUtil.keyStore2Bytes(trustKeyStore,idpKeyStoreLoader.getKeystorePassword());

			// store KeyStore content
			samlDetails.setKeyStore(keyStoreByte);
		} 
		
		return samlDetails;
	}
	
	
}
