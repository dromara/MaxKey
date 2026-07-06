/*
 * Copyright (c) 2024, MaxKey and/or its affiliates. All rights reserved.
 *
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * 
 * License Restrictions
 * This software and related documentation are provided under a license 
 * agreement containing restrictions on use and disclosure and are 
 * protected by intellectual property laws. Except as expressly permitted
 * in your license agreement or allowed by law, you may not use, copy, 
 * reproduce, translate, broadcast, modify, license, transmit, distribute, 
 * exhibit, perform, publish, or display any part, in any form, or by any means. 
 * Reverse engineering, disassembly, or decompilation of this software, 
 * unless required by law for interoperability, is prohibited.
 *
 * Please contact MaxKey, visit www.maxkey.top if you need additional information
 * or have any questions,support email support@maxsso.net .
 * 
 */
 

 

package org.dromara.maxkey.util;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tools.jackson.core.StreamReadFeature;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;


public class JsonUtils {
	private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
	
    /**
     * jackson Transform json string to java bean object.
     * 
     * @param json String
     * @param bean Object 
     * @return Object 
     */
    public static Object stringToObject(String json, Object bean) {
        try {
            bean = (new ObjectMapper()).readValue(json, bean.getClass());
        } catch (Exception e) {
            logger.error("Exception readValue", e);
        }
        return bean;
    }
    
    /**
     * jackson Transform json string to java bean object.
     * 
     * @param json String
     * @param bean Object 
     * @return Object 
     */
    public static Object stringToObject(String json, Object bean, String dateFormat) {
        try {
            JsonMapper mapper = JsonMapper.builder()
                    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    .enable(StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION)
                    .defaultDateFormat(new SimpleDateFormat(dateFormat))
                    .build();
            bean = mapper.readValue(json, bean.getClass());
        } catch (Exception e) {
            logger.error("Exception DateFormat readValue", e);
        }
        return bean;
    }

    /**
     * jackson Transform json string to java bean object.
     * 
     * @param json String
     * @param cls Class
     * @return Object
     */
    public static <T> T stringToObject(String json, Class<T> cls) {
        T bean = null;
        try {
            JsonMapper mapper = JsonMapper.builder()
                    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    .enable(StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION)
                    .build();
            bean = mapper.readValue(json, cls);
        } catch (Exception e) {
            logger.error("Exception Class readValue", e);
        }
        return bean;
    }

    /**
     * jackson Transform json string to java bean object.
     * 
     * @param json String
     * @param cls Class
     * @return Object
     */
    public static <T> T stringToObject(String json, Class<T> cls , String dateFormat) {
        T bean = null;
        try {
            JsonMapper mapper = JsonMapper.builder()
                    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    .enable(StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION)
                    .defaultDateFormat(new SimpleDateFormat(dateFormat))
                    .build();
            bean = mapper.readValue(json, cls);
        } catch (Exception e) {
            logger.error("Exception DateFormat readValue", e);
        }
        return bean;
    }
    
    

    /**
     * jackson Transform java bean object to json string.
     * 
     * @param bean Object
     * @return string
     */
    public static String toString(Object bean) {
        String json = "";
        try {
            json = (new ObjectMapper()).writeValueAsString(bean);
        } catch (Exception e) {
            logger.error("Exception writeValueAsString", e);
        }
        return json;
    }

}
