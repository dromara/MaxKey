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
 

package org.dromara.maxkey.web.apps.controller;


import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.dromara.maxkey.authn.annotation.CurrentUser;
import org.dromara.maxkey.constants.ConstsProtocols;
import org.dromara.maxkey.crypto.ReciprocalUtils;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.apps.Apps;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.util.StringGenerator;
import org.dromara.mybatis.jpa.entity.JpaPageResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.Requirement;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.OctetSequenceKeyGenerator;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;


@RestController
@RequestMapping(value={"/apps"})
public class ApplicationsController extends BaseAppContorller {
    static final Logger logger = LoggerFactory.getLogger(ApplicationsController.class);
    
    @GetMapping(value = { "/init" }, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Message<Apps> init() {
        Apps app=new Apps();
        app.setId(app.generateId());
        app.setProtocol(ConstsProtocols.BASIC);
        app.setSecret(StringGenerator.generateKey(""));
        return new Message<>(app);
    }
    
    
    @GetMapping(value = { "/fetch" }, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Message<JpaPageResults<Apps>> fetch(@ModelAttribute Apps apps,@CurrentUser UserInfo currentUser) {
        apps.setInstId(currentUser.getInstId());
        JpaPageResults<Apps> appsList =appsService.fetchPageResults(apps);
        for (Apps app : appsList.getRows()){
            app.transIconBase64();
            app.setSecret(null);
            app.setSharedPassword(null);
        }
        logger.debug("List {}" , appsList);
        return new Message<>(appsList);
    }

    @GetMapping(value={"/query"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Message<Apps> query(@ModelAttribute Apps apps,@CurrentUser UserInfo currentUser) {
        logger.debug("-query  : {}" , apps);
        if (CollectionUtils.isNotEmpty(appsService.query(apps))) {
             return new Message<>(Message.SUCCESS);
        } else {
             return new Message<>(Message.FAIL);
        }
    }
    
    @GetMapping(value = { "/get/{id}" }, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Message<Apps> get(@PathVariable String id) {
        Apps apps = appsService.get(id);
        decoderSecret(apps);
        apps.transIconBase64();
        return new Message<>(apps);
    }
    
    @PostMapping(value={"/add"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Message<Apps> insert(@RequestBody Apps apps,@CurrentUser UserInfo currentUser) {
        logger.debug("-Add  : {}" , apps);
        transform(apps);
        apps.setInstId(currentUser.getInstId());
        if (appsService.insert(apps)) {
            return new Message<>(Message.SUCCESS);
        } else {
            return new Message<>(Message.FAIL);
        }
    }
    
    @PutMapping(value={"/update"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Message<Apps> update(@RequestBody  Apps apps,@CurrentUser UserInfo currentUser) {
        logger.debug("-update  : {}" , apps);
        transform(apps);
        apps.setInstId(currentUser.getInstId());
        if (appsService.update(apps)) {
            return new Message<>(Message.SUCCESS);
        } else {
            return new Message<>(Message.FAIL);
        }
    }
    
    @DeleteMapping(value={"/delete"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Message<Apps> delete(@RequestParam List<String> ids,@CurrentUser UserInfo currentUser) {
        logger.debug("-delete  ids : {} " , ids);
        if (appsService.deleteBatch(ids)) {
             return new Message<>(Message.SUCCESS);
        } else {
            return new Message<>(Message.FAIL);
        }
    }
    
    @PostMapping({ "/updateExtendAttr" })
    public Message<Apps> updateExtendAttr(@RequestBody Apps app) {
        logger.debug("-updateExtendAttr  id : {} , ExtendAttr : {}" , app.getId(),app.getExtendAttr());
        if (appsService.updateExtendAttr(app)) {
            return new Message<>(Message.SUCCESS);
        } else {
            return new Message<>(Message.FAIL);
        }
    }
    
    @GetMapping({ "/generate/secret/{type}" })
    public Message<String> generateSecret(@PathVariable String type,@RequestParam(required=false) String id) throws JOSEException {
        String secret="";
        type=type.toLowerCase();
        if("des".equals(type)){
            secret=StringGenerator.generateKey(ReciprocalUtils.Algorithm.DES);
        }else if("desede".equals(type)){
            secret=StringGenerator.generateKey(ReciprocalUtils.Algorithm.DESede);
        }else if("aes".equals(type)){
            secret=StringGenerator.generateKey(ReciprocalUtils.Algorithm.AES);
        }else if("blowfish".equals(type)){
            secret=StringGenerator.generateKey(ReciprocalUtils.Algorithm.Blowfish);
        }else if("RS256".equalsIgnoreCase(type)
                || "RS384".equalsIgnoreCase(type)
                || "RS512".equalsIgnoreCase(type)) {
            RSAKey rsaJWK = new RSAKeyGenerator(2048)
                    .keyID(id + "_sig")
                    .keyUse(KeyUse.SIGNATURE)
                    .algorithm(new JWSAlgorithm(type.toUpperCase(), Requirement.OPTIONAL))
                    .generate();
            secret = rsaJWK.toJSONString();
        }else if("HS256".equalsIgnoreCase(type)
                || "HS384".equalsIgnoreCase(type)
                || "HS512".equalsIgnoreCase(type)) {
            OctetSequenceKey octKey=  new OctetSequenceKeyGenerator(2048)
                    .keyID(id + "_sig")
                    .keyUse(KeyUse.SIGNATURE)
                    .algorithm(new JWSAlgorithm(type.toUpperCase(), Requirement.OPTIONAL))
                    .generate();
            secret = octKey.toJSONString();
        }else if("RSA1_5".equalsIgnoreCase(type)
                || "RSA_OAEP".equalsIgnoreCase(type)
                || "RSA-OAEP-256".equalsIgnoreCase(type)) {
            RSAKey rsaJWK = new RSAKeyGenerator(2048)
                    .keyID(id + "_enc")
                    .keyUse(KeyUse.ENCRYPTION)
                    .algorithm(new JWEAlgorithm(type.toUpperCase(), Requirement.OPTIONAL))
                    .generate();
            secret = rsaJWK.toJSONString();
        }else if("A128KW".equalsIgnoreCase(type)
                || "A192KW".equalsIgnoreCase(type)
                || "A256KW".equalsIgnoreCase(type)
                || "A128GCMKW".equalsIgnoreCase(type)
                || "A192GCMKW".equalsIgnoreCase(type)
                || "A256GCMKW".equalsIgnoreCase(type)) {
            int keyLength = Integer.parseInt(type.substring(1, 4));
            OctetSequenceKey octKey=  new OctetSequenceKeyGenerator(keyLength)
                    .keyID(id + "_enc")
                    .keyUse(KeyUse.ENCRYPTION)
                    .algorithm(new JWEAlgorithm(type.toUpperCase(), Requirement.OPTIONAL))
                    .generate();
            secret = octKey.toJSONString();
        }else{
            secret=StringGenerator.generateKey("");
        }
        
        return new Message<>(Message.SUCCESS,null,secret);
    }
    
    
}
