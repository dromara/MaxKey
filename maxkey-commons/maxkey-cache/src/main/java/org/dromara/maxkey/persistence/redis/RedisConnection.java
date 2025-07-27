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
 

package org.dromara.maxkey.persistence.redis;

import java.io.Serializable;
import java.util.List;

import org.dromara.maxkey.util.ObjectTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

public class RedisConnection {
	private static final Logger _logger = LoggerFactory.getLogger(RedisConnection.class);
	
	Jedis conn ;
	RedisConnectionFactory connectionFactory;
	
	Pipeline pipeline ;
	 
	public RedisConnection() {
		
	}
	
	public RedisConnection(RedisConnectionFactory connectionFactory) {
		this.conn=connectionFactory.open();
		this.connectionFactory=connectionFactory;
	}

	/**
	 * @param key
	 * @param value
	 */
	public  void set(String key, String value){
		conn.set(key, value);
	}
	

	/**
	 * @param key
	 * @param value
	 */
	public  void setObject(String key, Object value){
		if(value instanceof Serializable) {
			set(key, ObjectTransformer.serialize((Serializable)value));
		}else {
			_logger.error("value must implements of Serializable .");
		}
	}
	
	public  void setexObject(String key,int seconds, Object value){
		if(value instanceof Serializable) {
			setex(key, seconds, ObjectTransformer.serialize((Serializable)value));
		}else {
			_logger.error("value must implements of Serializable .");
		}
	}
	
	/**
	 * @param key
	 * @param seconds
	 * @param value
	 */
	public  void setex(String key,long seconds, String value){
		_logger.trace("setex key {} ..." , key);
		if(seconds==0){
			conn.setex(key, RedisConnectionFactory.DEFAULT_CONFIG.DEFAULT_LIFETIME, value);
		}else{
			conn.setex(key, seconds, value);
		}
		_logger.trace("setex successful .");
	}
	
	
	/**
	 * @param key
	 * @return String 
	 */
	public  String get(String key){
		_logger.trace("get key {} ..." , key);
		String value = null;
		if(key != null){
			value = conn.get(key);
		}
		return value;
	}
	
	/**
	 * @param key
	 * @return String 
	 */
	public  <T> T getObject(String key){
		String value = null;
		if(key != null){
			value = get(key);
			if(value!=null){
				return ObjectTransformer.deserialize(value);
			}
		}
		return null;
	}
	
	public void expire(String key,long seconds){
		_logger.trace("expire key {} , {}" , key , seconds);
		conn.expire(key, seconds);
	}
	
	public void delete(String key){
		_logger.trace("del key {}" , key);
		conn.del(key);
	}
	
	public  void rPush(String key, Serializable object){
		conn.rpush(key, ObjectTransformer.serialize(object));
	}
	
	public long  lRem(String key,int count,String value){
		return conn.lrem(key, count, value);
	}
	
	
	public List<String>  lRange(String key,int start,int end){
		return conn.lrange(key, start, end);
	}
	
	public void openPipeline(){
		this.pipeline=conn.pipelined();
	}
	
	public List<Object> closePipeline(){
		return pipeline.syncAndReturnAll();
	}
	/**
     * 释放jedis资源
     * @param jedis
     */
	public  void close() {
        if (conn != null) {
        	connectionFactory.close(conn);
        }
    }

	public Jedis getConn() {
		return conn;
	}

	public void setConn(Jedis conn) {
		this.conn = conn;
	}

	public Pipeline getPipeline() {
		return pipeline;
	}
	
	
}
