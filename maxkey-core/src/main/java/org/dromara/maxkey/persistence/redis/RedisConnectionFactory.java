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

    public static class DEFAULT_CONFIG {
        /**
         * Redis默认服务器IP
         */
        public static String DEFAULT_ADDRESS = "127.0.0.1";
        /**
         * Redis默认端口号
         */
        public static int DEFAULT_PORT = 6379;
        /**
         * 访问密码
         */
        public static String DEFAULT_AUTH = "admin";
        /**
         * 可用连接实例的最大数目，默认值为8；<br>
         * 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
         **/
        public static int DEFAULT_MAX_ACTIVE = 5000;

        /**
         * 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
         */
        public static int DEFAULT_MAX_IDLE = 5000;

        /**
         * 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
         */
        public static int DEFAULT_MAX_WAIT_MILLIS = 10000;

        public static int DEFAULT_TIMEOUT = 10000;

        /**
         * 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
         */
        public static boolean DEFAULT_TEST_ON_BORROW = true;
        /**
         * 默认过期时间
         */
        public static long DEFAULT_LIFETIME = 600;
    }

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
                    hostName = DEFAULT_CONFIG.DEFAULT_ADDRESS;
                }
                if (port == 0) {
                    port = DEFAULT_CONFIG.DEFAULT_PORT;
                }
                if (timeOut == 0) {
                    timeOut = DEFAULT_CONFIG.DEFAULT_TIMEOUT;
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
