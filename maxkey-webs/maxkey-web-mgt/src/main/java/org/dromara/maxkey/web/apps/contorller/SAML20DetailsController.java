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
 

package org.dromara.maxkey.web.apps.contorller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.dromara.maxkey.authn.annotation.CurrentUser;
import org.dromara.maxkey.authz.saml20.metadata.MetadataDescriptorUtil;
import org.dromara.maxkey.configuration.ApplicationConfig;
import org.dromara.maxkey.constants.ConstsProtocols;
import org.dromara.maxkey.crypto.ReciprocalUtils;
import org.dromara.maxkey.crypto.cert.X509CertUtils;
import org.dromara.maxkey.crypto.keystore.KeyStoreLoader;
import org.dromara.maxkey.crypto.keystore.KeyStoreUtil;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.apps.AppsSAML20Details;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.persistence.service.AppsSaml20DetailsService;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.SPSSODescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value={"/apps/saml20"})
public class SAML20DetailsController   extends BaseAppContorller {
	static final  Logger logger = LoggerFactory.getLogger(SAML20DetailsController.class);
	
	@Autowired
	KeyStoreLoader keyStoreLoader;
	
	@Autowired
	AppsSaml20DetailsService saml20DetailsService;
	
	@Autowired
	ApplicationConfig applicationConfig;
	
	@RequestMapping(value = { "/init" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> init() {
		AppsSAML20Details saml20Details=new AppsSAML20Details();
		saml20Details.setSecret(ReciprocalUtils.generateKey(""));
		saml20Details.setProtocol(ConstsProtocols.SAML20);
		saml20Details.setId(saml20Details.generateId());
		return new Message<AppsSAML20Details>(saml20Details);
	}
	
	@RequestMapping(value = { "/get/{id}" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> get(@PathVariable("id") String id) {
		AppsSAML20Details saml20Details=saml20DetailsService.getAppDetails(id , false);
		decoderSecret(saml20Details);
		saml20Details.transIconBase64();
		//modelAndView.addObject("authzURI",applicationConfig.getAuthzUri());
		return new Message<AppsSAML20Details>(saml20Details);
	}
	
	@ResponseBody
	@RequestMapping(value={"/add"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> add(
			@RequestBody AppsSAML20Details saml20Details,
			@CurrentUser UserInfo currentUser) {
		logger.debug("-Add  : {}" , saml20Details);
		
		try {
			transform(saml20Details);
		} catch (Exception e) {
			e.printStackTrace();
		}
		saml20Details.setInstId(currentUser.getInstId());
		saml20DetailsService.insert(saml20Details);
		if (appsService.insertApp(saml20Details)) {
			return new Message<AppsSAML20Details>(Message.SUCCESS);
		} else {
			return new Message<AppsSAML20Details>(Message.FAIL);
		}
	}
	
	@ResponseBody
	@RequestMapping(value={"/update"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> update(
			@RequestBody AppsSAML20Details saml20Details,
			@CurrentUser UserInfo currentUser) {
		logger.debug("-update  : {}" , saml20Details);
		try {
			transform(saml20Details);
		} catch (Exception e) {
			e.printStackTrace();
		}
		saml20Details.setInstId(currentUser.getInstId());
		saml20DetailsService.update(saml20Details);
		if (appsService.updateApp(saml20Details)) {
		    return new Message<AppsSAML20Details>(Message.SUCCESS);
		} else {
			return new Message<AppsSAML20Details>(Message.FAIL);
		}
	}
	
	@ResponseBody
	@RequestMapping(value={"/delete"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> delete(
			@RequestParam("ids") List<String> ids,
			@CurrentUser UserInfo currentUser) {
		logger.debug("-delete  ids : {} " , ids);
		if (saml20DetailsService.deleteBatch(ids)&&appsService.deleteBatch(ids)) {
			 return new Message<AppsSAML20Details>(Message.SUCCESS);
		} else {
			return new Message<AppsSAML20Details>(Message.FAIL);
		}
	}
	
	protected AppsSAML20Details transform(AppsSAML20Details samlDetails) throws Exception{
		super.transform(samlDetails);
		ByteArrayInputStream bArrayInputStream = null;
		if(StringUtils.isNotBlank(samlDetails.getMetaFileId())) {
			bArrayInputStream = new ByteArrayInputStream(
					fileUploadService.get(samlDetails.getMetaFileId()).getUploaded());
			fileUploadService.delete(samlDetails.getMetaFileId());
		}
		
		if(StringUtils.isNotBlank(samlDetails.getFileType())){
			if(samlDetails.getFileType().equals("certificate")){//certificate file
				try {
					if(bArrayInputStream != null) {
						samlDetails.setTrustCert(
								X509CertUtils.loadCertFromInputStream(bArrayInputStream));
					}
				} catch (IOException e) {
					logger.error("read certificate file error .", e);
				}
			}else if(samlDetails.getFileType().equals("metadata_file")){//metadata file
				if(bArrayInputStream != null) {
					samlDetails = resolveMetaData(samlDetails,bArrayInputStream);
				}
			}else if(samlDetails.getFileType().equals("metadata_url")
					&&StringUtils.isNotBlank(samlDetails.getMetaUrl())){//metadata url
			    CloseableHttpClient httpClient = HttpClients.createDefault();
			    HttpPost post = new HttpPost(samlDetails.getMetaUrl());
	            CloseableHttpResponse response = httpClient.execute(post);
	            samlDetails = resolveMetaData(samlDetails,response.getEntity().getContent());;
	            response.close();
	            httpClient.close();
			}
		}
			
		if(samlDetails.getTrustCert()!=null) {
			samlDetails.setCertSubject(samlDetails.getTrustCert().getSubjectDN().getName());
			samlDetails.setCertExpiration(samlDetails.getTrustCert().getNotAfter().toString());
		
			samlDetails.setCertIssuer(X509CertUtils.getCommonName(samlDetails.getTrustCert().getIssuerX500Principal()));
			
			KeyStore keyStore = KeyStoreUtil.clone(keyStoreLoader.getKeyStore(),keyStoreLoader.getKeystorePassword());
		
			KeyStore trustKeyStore = null;
			if (!samlDetails.getEntityId().equals("")) {
				trustKeyStore = KeyStoreUtil.importTrustCertificate(keyStore,samlDetails.getTrustCert(), samlDetails.getEntityId());
			} else {
				trustKeyStore = KeyStoreUtil.importTrustCertificate(keyStore,samlDetails.getTrustCert());
			}
		
			byte[] keyStoreByte = KeyStoreUtil.keyStore2Bytes(trustKeyStore,keyStoreLoader.getKeystorePassword());
		
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
            logger.error("metadata  file resolve error .", e);
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

        logger.info("SPSSODescriptor EntityID {}", entityDescriptor.getEntityID());
        return samlDetails;
	}
	
}
