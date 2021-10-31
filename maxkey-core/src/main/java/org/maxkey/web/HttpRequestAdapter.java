/*
 * Copyright [2021] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.maxkey.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.maxkey.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HttpRequestAdapter {
	private static final Logger _logger = LoggerFactory.getLogger(HttpRequestAdapter.class);
	 
    private String mediaType = MediaType.FORM;
    
    public static class MediaType{
        public static String JSON   =   "JSON";
        public static String XML    =   "XML";
        public static String FORM   =   "FORM";
    }
    
    public HttpRequestAdapter(){}
    
    public HttpRequestAdapter(String mediaType){
        this.mediaType = mediaType;
    }
    
	public String post(String url,Map<String, Object> parameterMap) {
		HashMap<String,String> headers = new HashMap<String,String>();
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		return post(url , parameterMap , headers);
	}
	
    public String post(String url,Map<String, Object> parameterMap,HashMap<String,String> headers) {
        // 创建httpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = null;
        // 创建httpPost远程连接实例
        HttpPost httpPost = new HttpPost(url);
        // 配置请求参数实例
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 设置连接主机服务超时时间
                .setConnectionRequestTimeout(35000)// 设置连接请求超时时间
                .setSocketTimeout(60000)// 设置读取数据连接超时时间
                .build();
        // 为httpPost实例设置配置
        httpPost.setConfig(requestConfig);
        // 设置请求头
        if (null != headers && headers.size() > 0) {
        	  Set<Entry<String, String>> entrySet = headers.entrySet();
              // 循环遍历，获取迭代器
              Iterator<Entry<String, String>> iterator = entrySet.iterator();
              while (iterator.hasNext()) {
                  Entry<String, String> mapEntry = iterator.next();
                  _logger.trace("Name " + mapEntry.getKey() + " , Value " +mapEntry.getValue());
                  httpPost.addHeader(mapEntry.getKey(), mapEntry.getValue());
              }
        }
        
        // 封装post请求参数
        if (null != parameterMap && parameterMap.size() > 0) {
            if(mediaType.equals(MediaType.FORM)) {
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                // 通过map集成entrySet方法获取entity
                Set<Entry<String, Object>> entrySet = parameterMap.entrySet();
                // 循环遍历，获取迭代器
                Iterator<Entry<String, Object>> iterator = entrySet.iterator();
                while (iterator.hasNext()) {
                    Entry<String, Object> mapEntry = iterator.next();
                    _logger.debug("Name " + mapEntry.getKey() + " , Value " +mapEntry.getValue());
                    nvps.add(new BasicNameValuePair(mapEntry.getKey(), mapEntry.getValue().toString()));
                }
    
                // 为httpPost设置封装好的请求参数
                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }else if(mediaType.equals(MediaType.JSON)) {
                String jsonString = JsonUtils.gson2Json(parameterMap);
                StringEntity stringEntity =new StringEntity(jsonString, "UTF-8");
                stringEntity.setContentType("text/json");
                httpPost.setEntity(stringEntity);

                
            }
            _logger.debug("Post Message \n{} ", httpPost.getEntity().toString());
        }
        
        
        try {
            // httpClient对象执行post请求,并返回响应参数对象
            httpResponse = httpClient.execute(httpPost);
            // 从响应对象中获取响应内容
            HttpEntity entity = httpResponse.getEntity();
            String content = EntityUtils.toString(entity);
            _logger.debug("Http Response StatusCode {} , Content {}",
                    httpResponse.getStatusLine().getStatusCode(),
                    content
            );
            return content;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != httpResponse) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    
    
	public String get(String url) {
		HashMap<String,String> headers = new HashMap<String,String>();
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		return get(url ,  headers);
	}
	
    public String get(String url,HashMap<String,String> headers) {
        // 创建httpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = null;
        // 创建httpPost远程连接实例
        HttpGet httpGet = new HttpGet(url);
        // 配置请求参数实例
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 设置连接主机服务超时时间
                .setConnectionRequestTimeout(35000)// 设置连接请求超时时间
                .setSocketTimeout(60000)// 设置读取数据连接超时时间
                .build();
        // 为httpGet实例设置配置
        httpGet.setConfig(requestConfig);
        // 设置请求头
        if (null != headers && headers.size() > 0) {
        	  Set<Entry<String, String>> entrySet = headers.entrySet();
              // 循环遍历，获取迭代器
              Iterator<Entry<String, String>> iterator = entrySet.iterator();
              while (iterator.hasNext()) {
                  Entry<String, String> mapEntry = iterator.next();
                  _logger.trace("Name " + mapEntry.getKey() + " , Value " +mapEntry.getValue());
                  httpGet.addHeader(mapEntry.getKey(), mapEntry.getValue());
              }
        }        
        
        try {
            // httpClient对象执行post请求,并返回响应参数对象
            httpResponse = httpClient.execute(httpGet);
            // 从响应对象中获取响应内容
            HttpEntity entity = httpResponse.getEntity();
            String content = EntityUtils.toString(entity);
            _logger.debug("Http Response StatusCode {} , Content {}",
                    httpResponse.getStatusLine().getStatusCode(),
                    content
            );
            return content;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != httpResponse) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    
}
