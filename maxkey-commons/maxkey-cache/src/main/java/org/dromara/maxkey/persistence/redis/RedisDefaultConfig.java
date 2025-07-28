package org.dromara.maxkey.persistence.redis;

public class RedisDefaultConfig {
	 /**
     * Redis默认服务器IP
     */
    public static final String DEFAULT_ADDRESS = "127.0.0.1";
    /**
     * Redis默认端口号
     */
    public static final int DEFAULT_PORT = 6379;
    /**
     * 访问密码
     */
    public static final String DEFAULT_AUTH = "admin";
    /**
     * 可用连接实例的最大数目，默认值为8；<br>
     * 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
     **/
    public static final int DEFAULT_MAX_ACTIVE = 5000;

    /**
     * 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
     */
    public static final int DEFAULT_MAX_IDLE = 5000;

    /**
     * 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
     */
    public static final int DEFAULT_MAX_WAIT_MILLIS = 10000;

    public static final int DEFAULT_TIMEOUT = 10000;

    /**
     * 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
     */
    public static final boolean DEFAULT_TEST_ON_BORROW = true;
    /**
     * 默认过期时间
     */
    public static final long DEFAULT_LIFETIME = 600;
}
