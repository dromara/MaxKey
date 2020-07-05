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
 

package org.maxkey.configuration;

import java.io.UnsupportedEncodingException;

import org.maxkey.constants.ConstantsProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 字符集转换及转换配置.
 * 
 * @author Crystal.Sea
 *
 */
@Configuration
@PropertySource(ConstantsProperties.applicationPropertySource)
public class CharacterEncodingConfig {

    /**
     * 源字符集.
     */
    @Value("${server.servlet.encoding.charset.from:UTF-8}")
    String fromCharSet;

    /**
     * 目标字符集.
     */
    @Value("${server.servlet.encoding.charset:UTF-8}")
    String toCharSet;

    /**
     * 转换标志.
     */
    @Value("${server.servlet.encoding.enabled:false}")
    boolean encoding = false;

    public CharacterEncodingConfig() {

    }

    public String getFromCharSet() {
        return fromCharSet;
    }

    public void setFromCharSet(String fromCharSet) {
        this.fromCharSet = fromCharSet;
    }

    public String getToCharSet() {
        return toCharSet;
    }

    public void setToCharSet(String toCharSet) {
        this.toCharSet = toCharSet;
    }

    public boolean isEncoding() {
        return encoding;
    }

    public void setEncoding(boolean encoding) {
        this.encoding = encoding;
    }

    /**
     * 字符集转换.
     * 
     * @param encodingString 源字符串
     * @return encoded目标字符串
     */
    public String encoding(String encodingString) {
        if (!this.encoding || encodingString == null) {
            return encodingString;
        }

        try {
            return new String(encodingString.getBytes(this.fromCharSet), this.toCharSet);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
