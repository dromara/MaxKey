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


import org.apache.mybatis.jpa.persistence.JpaPageResults;
import org.maxkey.constants.ConstsOperateMessage;
import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.entity.ExtraAttr;
import org.maxkey.entity.ExtraAttrs;
import org.maxkey.entity.apps.Apps;
import org.maxkey.web.WebContext;
import org.maxkey.web.message.Message;
import org.maxkey.web.message.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.Requirement;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.OctetSequenceKeyGenerator;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;


@Controller
@RequestMapping(value={"/apps"})
public class ApplicationsController extends BaseAppContorller {
	final static Logger _logger = LoggerFactory.getLogger(ApplicationsController.class);
	
	@RequestMapping(value={"/list"})
	public ModelAndView applicationsList(){
		return new ModelAndView("apps/appsList");
	}
	
	@RequestMapping(value={"/select"})
	public ModelAndView select(@RequestParam(name="accountMgmt",required=false) String accountMgmt){
		ModelAndView modelAndView=new ModelAndView("apps/selectAppsList");
		if(accountMgmt != null) {
			modelAndView.addObject("accountMgmt", accountMgmt);
		}else {
			modelAndView.addObject("accountMgmt", 3);
		}
		return modelAndView;
	}
	
	
	@RequestMapping(value = { "/grid" })
	@ResponseBody
	public JpaPageResults<Apps> queryDataGrid(@ModelAttribute("applications") Apps applications) {
		applications.setInstId(WebContext.getUserInfo().getInstId());
		JpaPageResults<Apps> apps=appsService.queryPageResults(applications);
		if(apps!=null&&apps.getRows()!=null){
			for (Apps app : apps.getRows()){
				app.transIconBase64();
			}
		}
		return apps;
	}
	
	@RequestMapping(value = { "/forwardAdd" })
	public ModelAndView forwardAdd() {
		return new ModelAndView("apps/appAdd");
	}
	
	
	@ResponseBody
	@RequestMapping(value={"/add"})
	public Message insert(@ModelAttribute("application") Apps application) {
		_logger.debug("-Add  :" + application);
		
		transform(application);
		application.setInstId(WebContext.getUserInfo().getInstId());
		if (appsService.insert(application)) {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.INSERT_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.INSERT_SUCCESS),MessageType.error);
		}
		
	}
	
	@RequestMapping(value = { "/forwardAppsExtendAttr/{id}" })
	public ModelAndView forwardExtendAttr(@PathVariable("id") String id) {
		ModelAndView modelAndView=new ModelAndView("apps/appsExtendAttr");
		modelAndView.addObject("model",appsService.get(id));
		return modelAndView;
	}
	
	@ResponseBody
	@RequestMapping(value = { "/updateExtendAttr" })
	public Message updateExtendAttr(@ModelAttribute("application") Apps application,@ModelAttribute("extraAttrs") ExtraAttr extraAttr) {
		if(extraAttr.getAttr()!=null){
			String []attributes=extraAttr.getAttr().split(",");
			String []attributeType=extraAttr.getType().split(",");
			String []attributeValue=extraAttr.getValue().split(",");
			ExtraAttrs extraAttrs=new ExtraAttrs();
			for(int i=0;i<attributes.length;i++){
				extraAttrs.put(attributes[i],attributeType[i], attributeValue[i]);
			}
			application.setExtendAttr(extraAttrs.toJsonString());
		}
		
		if (appsService.updateExtendAttr(application)) {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.INSERT_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.INSERT_ERROR),MessageType.error);
		}
	}
	
	/**
	 * query
	 * @param application
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value={"/query"}) 
	public Message query(@ModelAttribute("application") Apps application) {
		_logger.debug("-query  :" + application);
		if (appsService.load(application)!=null) {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.INSERT_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.INSERT_ERROR),MessageType.error);
		}
		
	}
	
	/**
	 * modify
	 * @param application
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value={"/update"})  
	public Message update(@ModelAttribute("application") Apps application) {
		_logger.debug("-update  application :" + application);
		application.setInstId(WebContext.getUserInfo().getInstId());
		if (appsService.update(application)) {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.UPDATE_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.UPDATE_ERROR),MessageType.error);
		}
		
	}
	

	@ResponseBody
	@RequestMapping(value={"/delete"})
	public Message delete(@ModelAttribute("application") Apps application) {
		_logger.debug("-delete  application :" + application);
		if (appsService.deleteBatch(application.getId())) {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.DELETE_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.DELETE_SUCCESS),MessageType.error);
		}
		
	}
	
	@ResponseBody
	@RequestMapping(value = { "/generate/secret/{type}" })
	public String generateSecret(@PathVariable("type") String type,@RequestParam(name="id",required=false) String id) throws JOSEException {
		String secret="";
		type=type.toLowerCase();
		if(type.equals("des")){
			secret=ReciprocalUtils.generateKey(ReciprocalUtils.Algorithm.DES);
		}else if(type.equals("desede")){
			secret=ReciprocalUtils.generateKey(ReciprocalUtils.Algorithm.DESede);
		}else if(type.equals("aes")){
			secret=ReciprocalUtils.generateKey(ReciprocalUtils.Algorithm.AES);
		}else if(type.equals("blowfish")){
			secret=ReciprocalUtils.generateKey(ReciprocalUtils.Algorithm.Blowfish);
		}else if(type.equalsIgnoreCase("RS256")
				||type.equalsIgnoreCase("RS384")
				||type.equalsIgnoreCase("RS512")) {
			RSAKey rsaJWK = new RSAKeyGenerator(2048)
				    .keyID(id + "_sig")
				    .keyUse(KeyUse.SIGNATURE)
				    .algorithm(new JWSAlgorithm(type.toUpperCase(), Requirement.OPTIONAL))
				    .generate();
			secret = rsaJWK.toJSONString();
		}else if(type.equalsIgnoreCase("HS256")
				||type.equalsIgnoreCase("HS384")
				||type.equalsIgnoreCase("HS512")) {
			OctetSequenceKey octKey=  new OctetSequenceKeyGenerator(2048)
					.keyID(id + "_sig")
					.keyUse(KeyUse.SIGNATURE)
					.algorithm(new JWSAlgorithm(type.toUpperCase(), Requirement.OPTIONAL))
					.generate();
			secret = octKey.toJSONString();
		}else if(type.equalsIgnoreCase("RSA1_5")
				||type.equalsIgnoreCase("RSA_OAEP")
				||type.equalsIgnoreCase("RSA-OAEP-256")) {
			RSAKey rsaJWK = new RSAKeyGenerator(2048)
				    .keyID(id + "_enc")
				    .keyUse(KeyUse.ENCRYPTION)
				    .algorithm(new JWEAlgorithm(type.toUpperCase(), Requirement.OPTIONAL))
				    .generate();
			secret = rsaJWK.toJSONString();
		}else if(type.equalsIgnoreCase("A128KW")
				||type.equalsIgnoreCase("A192KW")
				||type.equalsIgnoreCase("A256KW")
				||type.equalsIgnoreCase("A128GCMKW")
				||type.equalsIgnoreCase("A192GCMKW")
				||type.equalsIgnoreCase("A256GCMKW")) {
			int keyLength = Integer.parseInt(type.substring(1, 4));
			OctetSequenceKey octKey=  new OctetSequenceKeyGenerator(keyLength)
					.keyID(id + "_enc")
					.keyUse(KeyUse.ENCRYPTION)
					.algorithm(new JWEAlgorithm(type.toUpperCase(), Requirement.OPTIONAL))
					.generate();
			secret = octKey.toJSONString();
		}else{
			secret=ReciprocalUtils.generateKey("");
		}
		
		return secret;
	}
	
	
}
