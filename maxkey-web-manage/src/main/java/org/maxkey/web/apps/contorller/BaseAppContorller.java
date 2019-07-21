/**
 * 
 */
package org.maxkey.web.apps.contorller;

import java.io.IOException;

import org.maxkey.constants.PROTOCOLS;
import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.crypto.password.PasswordReciprocal;
import org.maxkey.dao.service.ApplicationsService;
import org.maxkey.domain.ExtraAttrs;
import org.maxkey.domain.apps.Applications;
import org.maxkey.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author Crystal.Sea
 *
 */
public class BaseAppContorller {

	final static Logger _logger = LoggerFactory.getLogger(BaseAppContorller.class);
	
	@Autowired
	@Qualifier("applicationsService")
	protected ApplicationsService applicationsService;

	
	@Autowired
	@Qualifier("passwordReciprocal")
	protected PasswordReciprocal passwordReciprocal;
	


	public void setApplicationsService(ApplicationsService applicationsService) {
		this.applicationsService = applicationsService;
	}
	
	protected void transform(Applications application) {
		
		encodeSharedPassword(application);
		
		encodeSecret(application);
		
		/*
		 * string field encoding
		 */
		encoding(application);
		
		/*
		 * convertExtendAttr
		 * 
		 */
		convertExtendAttr(application);
		/*
		 * upload iconFile MultipartFile  to icon Bytes
		 */
		if(null!=application.getIconFile()&&!application.getIconFile().isEmpty()){
			try {
				application.setIcon(application.getIconFile().getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		}
		
	}
	
	protected void encodeSharedPassword(Applications application){
		if(application.getCredential()!=Applications.CREDENTIALS.SHARED){
			if(application.getProtocol().equals(PROTOCOLS.DESKTOP)||application.getProtocol().equals(PROTOCOLS.FORMBASED)){
				if(StringUtils.isNotEmpty(application.getSharedPassword())){
					application.setSharedPassword(ReciprocalUtils.encode(application.getSharedPassword()));
				}
			}
		}
	}
	
	protected void decoderSharedPassword(Applications application){
		if(application.getCredential()!=Applications.CREDENTIALS.SHARED){
			if(application.getProtocol().equals(PROTOCOLS.DESKTOP)||application.getProtocol().equals(PROTOCOLS.FORMBASED)){
				if(StringUtils.isNotEmpty(application.getSharedPassword())){
					application.setSharedPassword(ReciprocalUtils.decoder(application.getSharedPassword()));
				}
			}
		}
	}
	
	protected void encoding(Applications application){
		
		//application.setName(WebContext.encoding(application.getName()));
		if(null!=application.getDescription()){
		//	application.setDescription(WebContext.encoding(application.getDescription()));
		}
		
	}
	
	
	protected void encodeSecret(Applications application){
		if(application.getSecret()!=null&&!application.getSecret().equals("")){
			//
			String encodeSecret=passwordReciprocal.encode(application.getSecret());
			application.setSecret(encodeSecret);
		}
	}
	
	protected void decoderSecret(Applications application){
		if(application.getSecret()!=null&&!application.getSecret().equals("")){
			String decodeSecret=passwordReciprocal.decoder(application.getSecret());
			application.setSecret(decodeSecret);
		}
	}
	
	protected void convertExtendAttr(Applications application) {
		if(application.getAttribute()!=null){
			String []attributes=application.getAttribute().split(",");
			String []attributeValue=application.getAttributeValue().split(",");
			ExtraAttrs extraAttrs=new ExtraAttrs();
			for(int i=0;i<attributes.length;i++){
				extraAttrs.put(attributes[i], attributeValue[i]);
			}
			application.setExtendAttr(extraAttrs.toJsonString());
		}
	}

}
