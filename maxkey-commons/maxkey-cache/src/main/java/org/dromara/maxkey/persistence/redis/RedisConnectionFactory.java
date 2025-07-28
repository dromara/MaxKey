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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisConnectionFactory {
	private static final  Logger _logger = LoggerFactory.getLogger(RedisConnectionFactory.class);

    JedisPoolConfig poolConfig;

    private JedisPool jedisPool = null;

    private String hostName;
    private int port;
    private String password;
    private int timeOut;

    public RedisConnectionFactory() {

    }

    public void initConnectionFactory() {
        if (jedisPool == null) {
        	_logger.debug("init Jedis Pool .");
            try {
                if (this.hostName == null || hostName.equals("")) {
                    hostName = RedisDefaultConfig.DEFAULT_ADDRESS;
                }
                if (port == 0) {
                    port = RedisDefaultConfig.DEFAULT_PORT;
                }
                if (timeOut == 0) {
                    timeOut = RedisDefaultConfig.DEFAULT_TIMEOUT;
                }

                if (this.password == null || this.password.equals("")) {
                    this.password = null;
                }
                jedisPool = new JedisPool(poolConfig, hostName, port, timeOut, password);
                _logger.debug("init Jedis Pool successful .");
            } catch (Exception e) {
                e.printStackTrace();
                _logger.error("Exception", e);
            }
        }
    }

    public synchronized RedisConnection getConnection() {
        initConnectionFactory();
        _logger.trace("get connection .");
        RedisConnection redisConnection = new RedisConnection(this);
        _logger.trace("return connection .");
        return redisConnection;
    }

    public Jedis open() {
    	_logger.trace("get jedisPool Resource ...");
    	Jedis jedis = jedisPool.getResource();
    	_logger.trace("return jedisPool Resource .");
        return jedis;

    }

    public void close(Jedis conn) {
        // jedisPool.returnResource(conn);
    	_logger.trace("close conn .");
        conn.close();
        _logger.trace("closed conn .");
    }


    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public void setPoolConfig(JedisPoolConfig poolConfig) {
        this.poolConfig = poolConfig;
    }

    public JedisPoolConfig getPoolConfig() {
        return poolConfig;
    }

}
