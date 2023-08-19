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


import org.dromara.maxkey.authn.annotation.CurrentUser;
import org.dromara.maxkey.constants.ConstsProtocols;
import org.dromara.maxkey.crypto.ReciprocalUtils;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.UserInfo;
import org.dromara.maxkey.entity.apps.Apps;
import org.dromara.mybatis.jpa.entity.JpaPageResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
	static final Logger logger = LoggerFactory.getLogger(ApplicationsController.class);
	
	@RequestMapping(value = { "/init" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> init() {
		Apps app=new Apps();
		app.setId(app.generateId());
		app.setProtocol(ConstsProtocols.BASIC);
		app.setSecret(ReciprocalUtils.generateKey(""));
		return new Message<Apps>(app).buildResponse();
	}
	
	
	@RequestMapping(value = { "/fetch" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseEntity<?> fetch(@ModelAttribute Apps apps,@CurrentUser UserInfo currentUser) {
		apps.setInstId(currentUser.getInstId());
		JpaPageResults<Apps> appsList =appsService.fetchPageResults(apps);
		for (Apps app : appsList.getRows()){
			app.transIconBase64();
			app.setSecret(null);
			app.setSharedPassword(null);
		}
		logger.debug("List {}" , appsList);
		return new Message<JpaPageResults<Apps>>(appsList).buildResponse();
	}

	@ResponseBody
	@RequestMapping(value={"/query"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> query(@ModelAttribute Apps apps,@CurrentUser UserInfo currentUser) {
		logger.debug("-query  : {}" , apps);
		if (appsService.query(apps)!=null) {
			 return new Message<Apps>(Message.SUCCESS).buildResponse();
		} else {
			 return new Message<Apps>(Message.SUCCESS).buildResponse();
		}
	}
	
	@RequestMapping(value = { "/get/{id}" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		Apps apps = appsService.get(id);
		decoderSecret(apps);
		apps.transIconBase64();
		return new Message<Apps>(apps).buildResponse();
	}
	
	@ResponseBody
	@RequestMapping(value={"/add"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> insert(@RequestBody Apps apps,@CurrentUser UserInfo currentUser) {
		logger.debug("-Add  : {}" , apps);
		transform(apps);
		apps.setInstId(currentUser.getInstId());
		if (appsService.insert(apps)) {
			return new Message<Apps>(Message.SUCCESS).buildResponse();
		} else {
			return new Message<Apps>(Message.FAIL).buildResponse();
		}
	}
	
	@ResponseBody
	@RequestMapping(value={"/update"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> update(@RequestBody  Apps apps,@CurrentUser UserInfo currentUser) {
		logger.debug("-update  : {}" , apps);
		transform(apps);
		apps.setInstId(currentUser.getInstId());
		if (appsService.update(apps)) {
		    return new Message<Apps>(Message.SUCCESS).buildResponse();
		} else {
			return new Message<Apps>(Message.FAIL).buildResponse();
		}
	}
	
	@ResponseBody
	@RequestMapping(value={"/delete"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> delete(@RequestParam("ids") String ids,@CurrentUser UserInfo currentUser) {
		logger.debug("-delete  ids : {} " , ids);
		if (appsService.deleteBatch(ids)) {
			 return new Message<Apps>(Message.SUCCESS).buildResponse();
		} else {
			return new Message<Apps>(Message.FAIL).buildResponse();
		}
	}
	
	@ResponseBody
	@RequestMapping(value = { "/updateExtendAttr" })
	public ResponseEntity<?> updateExtendAttr(@RequestBody Apps app) {
		logger.debug("-updateExtendAttr  id : {} , ExtendAttr : {}" , app.getId(),app.getExtendAttr());
		if (appsService.updateExtendAttr(app)) {
			return new Message<Apps>(Message.SUCCESS).buildResponse();
		} else {
			return new Message<Apps>(Message.FAIL).buildResponse();
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value = { "/generate/secret/{type}" })
	public ResponseEntity<?> generateSecret(@PathVariable("type") String type,@RequestParam(name="id",required=false) String id) throws JOSEException {
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
		
		return new Message<Object>(Message.SUCCESS,(Object)secret).buildResponse();
	}
	
	
}
