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
 

package org.dromara.maxkey.autoconfigure;

import java.io.IOException;
import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.dromara.maxkey.authz.saml.common.EndpointGenerator;
import org.dromara.maxkey.authz.saml.service.IDService;
import org.dromara.maxkey.authz.saml.service.TimeService;
import org.dromara.maxkey.authz.saml20.binding.decoder.OpenHTTPPostDecoder;
import org.dromara.maxkey.authz.saml20.binding.decoder.OpenHTTPPostSimpleSignDecoder;
import org.dromara.maxkey.authz.saml20.binding.decoder.OpenHTTPRedirectDecoder;
import org.dromara.maxkey.authz.saml20.binding.impl.ExtractPostBindingAdapter;
import org.dromara.maxkey.authz.saml20.binding.impl.ExtractRedirectBindingAdapter;
import org.dromara.maxkey.authz.saml20.binding.impl.PostBindingAdapter;
import org.dromara.maxkey.authz.saml20.binding.impl.PostSimpleSignBindingAdapter;
import org.dromara.maxkey.authz.saml20.provider.xml.AuthnResponseGenerator;
import org.dromara.maxkey.authz.saml20.xml.SAML2ValidatorSuite;
import org.dromara.maxkey.crypto.keystore.KeyStoreLoader;
import org.dromara.maxkey.entity.Saml20Metadata;
import org.opensaml.common.binding.security.IssueInstantRule;
import org.opensaml.common.binding.security.MessageReplayRule;
import org.opensaml.util.storage.MapBasedStorageService;
import org.opensaml.util.storage.ReplayCache;
import org.opensaml.util.storage.ReplayCacheEntry;
import org.opensaml.xml.ConfigurationException;
import org.opensaml.xml.parse.BasicParserPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

@AutoConfiguration
@ComponentScan(basePackages = {
        "org.maxkey.authz.saml20.provider.endpoint",
        "org.maxkey.authz.saml20.metadata.endpoint",
})
public class Saml20AutoConfiguration implements InitializingBean {
    private static final  Logger _logger = LoggerFactory.getLogger(Saml20AutoConfiguration.class);

    /**
     * samlBootstrapInitializer.
     * @return samlBootstrapInitializer
     * @throws ConfigurationException 
     */
    @Bean(name = "samlBootstrapInitializer")
    String samlBootstrapInitializer() throws ConfigurationException {
        org.opensaml.DefaultBootstrap.bootstrap();
        return "";
    }

    /**
     * TimeService.
     * @return timeService
     */
    @Bean(name = "timeService")
    TimeService TimeService() {
        TimeService timeService = new TimeService();
        return timeService;
    }

    /**
     * IDService.
     * @return idService
     */
    @Bean(name = "idService")
    IDService idService() {
        IDService idService = new IDService();
        return idService;
    }

    /**
     * EndpointGenerator.
     * @return endpointGenerator
     */
    @Bean(name = "endpointGenerator")
    EndpointGenerator endpointGenerator() {
        EndpointGenerator generator = new EndpointGenerator();
        return generator;
    }

    /**
     * AuthnResponseGenerator.
     * @return authnResponseGenerator
     */
    @Bean(name = "authnResponseGenerator")
    AuthnResponseGenerator authnResponseGenerator(TimeService timeService, IDService idService,
                                               @Value("${maxkey.saml.v20.idp.issuer}") String issuerEntityName) {
        _logger.debug("issuerEntityName {}" , issuerEntityName);
        AuthnResponseGenerator generator = new AuthnResponseGenerator(issuerEntityName,timeService,idService);
        return generator;
    }

    /**
     * IssuerEntityName.
     * @return issuerEntityName
     */
    @Bean(name = "issuerEntityName")
    String issuerEntityName(
            @Value("${maxkey.saml.v20.idp.issuer}") String issuerEntityName) {
        return issuerEntityName;
    }

    /**
     * Saml20Metadata.
     * @return saml20Metadata
     */
    @Bean(name = "saml20Metadata")
    Saml20Metadata saml20Metadata(
            @Value("${maxkey.saml.v20.metadata.orgName}") String orgName,
            @Value("${maxkey.saml.v20.metadata.orgDisplayName}") String orgDisplayName,
            @Value("${maxkey.saml.v20.metadata.orgURL}") String orgURL,
            @Value("${maxkey.saml.v20.metadata.company}") String company,
            @Value("${maxkey.saml.v20.metadata.contactType}") String contactType,
            @Value("${maxkey.saml.v20.metadata.givenName}") String givenName,
            @Value("${maxkey.saml.v20.metadata.surName}") String surName,
            @Value("${maxkey.saml.v20.metadata.emailAddress}") String emailAddress,
            @Value("${maxkey.saml.v20.metadata.telephoneNumber}") String telephoneNumber) {
        Saml20Metadata metadata = new Saml20Metadata();
        metadata.setOrgName(orgName);
        metadata.setOrgDisplayName(orgDisplayName);
        metadata.setOrgURL(orgURL);
        metadata.setCompany(company);
        metadata.setContactType(contactType);
        metadata.setGivenName(givenName);
        metadata.setSurName(surName);
        metadata.setEmailAddress(emailAddress);
        metadata.setTelephoneNumber(telephoneNumber);
        return metadata;
    }

    /**
     * SAML2ValidatorSuite.
     * @return samlValidaotrSuite
     */
    @Bean(name = "samlValidaotrSuite")
    SAML2ValidatorSuite validatorSuite() {
        SAML2ValidatorSuite validatorSuite = new SAML2ValidatorSuite();
        return validatorSuite;
    }

    /**
     * MapBasedStorageService.
     * @return mapBasedStorageService
     */
    @SuppressWarnings("rawtypes")
    @Bean(name = "mapBasedStorageService")
    MapBasedStorageService mapBasedStorageService() {
        MapBasedStorageService mapBasedStorageService = new MapBasedStorageService();
        return mapBasedStorageService;
    }

    /**
     * VelocityEngineFactoryBean.
     * @return velocityEngine
     * @throws IOException 
     * @throws VelocityException 
     */
    @SuppressWarnings({"deprecation"})
    @Bean(name = "velocityEngine")
    VelocityEngine velocityEngine() throws VelocityException, IOException {
        VelocityEngineFactoryBean factory = new VelocityEngineFactoryBean();
        factory.setPreferFileSystemAccess(false);
        Properties velocityProperties = new Properties();
        velocityProperties.put("resource.loader", "classpath");
        velocityProperties.put("classpath.resource.loader.class", 
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        factory.setVelocityProperties(velocityProperties);
        return factory.createVelocityEngine();
    }

    /**
     * ReplayCache.
     * @return replayCache
     */
    @Bean(name = "replayCache")
    ReplayCache replayCache(MapBasedStorageService<String, ReplayCacheEntry> mapBasedStorageService,
                         @Value("${maxkey.saml.v20.replay.cache.life.in.millis}") long duration) {
        ReplayCache replayCache = new ReplayCache(mapBasedStorageService,duration);
        return replayCache;
    }

    /**
     * MessageReplayRule.
     * @return messageReplayRule
     */
    @Bean(name = "messageReplayRule")
    MessageReplayRule messageReplayRule(ReplayCache replayCache) {
        MessageReplayRule messageReplayRule = new MessageReplayRule(replayCache);
        return messageReplayRule;
    }

    /**
     * BasicParserPool.
     * @return samlParserPool
     */
    @Bean(name = "samlParserPool")
    BasicParserPool samlParserPool(
            @Value("${maxkey.saml.v20.max.parser.pool.size}") int maxPoolSize) {
        BasicParserPool samlParserPool = new BasicParserPool();
        samlParserPool.setMaxPoolSize(maxPoolSize);
        return samlParserPool;
    }

    /**
     * IssueInstantRule.
     * @return issueInstantRule
     */
    @Bean(name = "issueInstantRule")
    IssueInstantRule issueInstantRule(
            @Value("${maxkey.saml.v20.issue.instant.check.clock.skew.in.seconds}") int newClockSkew,
            @Value("${maxkey.saml.v20.issue.instant.check.validity.time.in.seconds}") int newExpires) {
        IssueInstantRule decoder = new IssueInstantRule(newClockSkew,newExpires);
        decoder.setRequiredRule(true);
        return decoder;
    }

    /**
     * OpenHTTPPostSimpleSignDecoder.
     * @return openHTTPPostSimpleSignDecoder
     */
    @Bean(name = "openHTTPPostSimpleSignDecoder")
    OpenHTTPPostSimpleSignDecoder openHTTPPostSimpleSignDecoder(BasicParserPool samlParserPool,
                                                             @Value("${maxkey.saml.v20.idp.receiver.endpoint}") String receiverEndpoint) {
        OpenHTTPPostSimpleSignDecoder decoder = new OpenHTTPPostSimpleSignDecoder(samlParserPool);
        decoder.setReceiverEndpoint(receiverEndpoint);
        return decoder;
    }

    /**
     * OpenHTTPPostDecoder.
     * @return openHTTPPostDecoder
     */
    @Bean(name = "openHTTPPostDecoder")
    OpenHTTPPostDecoder openHTTPPostDecoder(BasicParserPool samlParserPool,
                                         @Value("${maxkey.saml.v20.idp.receiver.endpoint}") String receiverEndpoint) {
        OpenHTTPPostDecoder decoder = new OpenHTTPPostDecoder(samlParserPool);
        decoder.setReceiverEndpoint(receiverEndpoint);
        return decoder;
    }

    /**
     * OpenHTTPRedirectDecoder.
     * @return openHTTPRedirectDecoder
     */
    @Bean(name = "openHTTPRedirectDecoder")
    OpenHTTPRedirectDecoder openHTTPRedirectDecoder(BasicParserPool samlParserPool,
                                                 @Value("${maxkey.saml.v20.idp.receiver.endpoint}") String receiverEndpoint) {
        OpenHTTPRedirectDecoder decoder = new OpenHTTPRedirectDecoder(samlParserPool);
        decoder.setReceiverEndpoint(receiverEndpoint);
        return decoder;
    }

    /**
     * ExtractPostBindingAdapter.
     * @return extractPostBindingAdapter
     */
    @Bean(name = "extractPostBindingAdapter")
    ExtractPostBindingAdapter extractPostBindingAdapter(OpenHTTPPostDecoder openHTTPPostDecoder,
                                                     @Qualifier("keyStoreLoader") KeyStoreLoader keyStoreLoader, IssueInstantRule issueInstantRule, MessageReplayRule messageReplayRule) {
        ExtractPostBindingAdapter adapter = new ExtractPostBindingAdapter(openHTTPPostDecoder);
        adapter.setIssueInstantRule(issueInstantRule);
        adapter.setKeyStoreLoader(keyStoreLoader);
        adapter.setMessageReplayRule(messageReplayRule);
        return adapter;
    }

    /**
     * ExtractRedirectBindingAdapter.
     * @return extractRedirectBindingAdapter
     */
    @Bean(name = "extractRedirectBindingAdapter")
    ExtractRedirectBindingAdapter extractRedirectBindingAdapter(OpenHTTPRedirectDecoder openHTTPRedirectDecoder,
                                                             @Qualifier("keyStoreLoader") KeyStoreLoader keyStoreLoader, IssueInstantRule issueInstantRule, MessageReplayRule messageReplayRule) {
        ExtractRedirectBindingAdapter adapter = new ExtractRedirectBindingAdapter(openHTTPRedirectDecoder);
        adapter.setIssueInstantRule(issueInstantRule);
        adapter.setKeyStoreLoader(keyStoreLoader);
        adapter.setMessageReplayRule(messageReplayRule);
        return adapter;
    }


    /**
     * PostSimpleSignBindingAdapter.
     * @return postSimpleSignBindingAdapter
     */
    @Bean(name = "postSimpleSignBindingAdapter")
    PostSimpleSignBindingAdapter postSimpleSignBindingAdapter(VelocityEngine velocityEngine,
                                                           @Value("${maxkey.saml.v20.idp.issuer}") String issuerEntityName) {
        PostSimpleSignBindingAdapter adapter = new PostSimpleSignBindingAdapter();
        adapter.setVelocityEngine(velocityEngine);
        adapter.setIssuerEntityName(issuerEntityName);
        return adapter;
    }

    /**
     * PostBindingAdapter.
     * @return postBindingAdapter
     */
    @Bean(name = "postBindingAdapter")
    PostBindingAdapter postBindingAdapter(VelocityEngine velocityEngine,
                                       @Value("${maxkey.saml.v20.idp.issuer}") String issuerEntityName) {
        PostBindingAdapter adapter = new PostBindingAdapter();
        adapter.setVelocityEngine(velocityEngine);
        adapter.setIssuerEntityName(issuerEntityName);
        return adapter;
    }
    
    
    
   

    @Override
    public void afterPropertiesSet() throws Exception {
        
    }
}
