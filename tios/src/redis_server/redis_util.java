package redis_server;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class redis_util {

	    private static String ADDR = "23.228.103.10";

	    private static int PORT = 6379;

	    private static String AUTH = "123456";

	    private static int MAX_ACTIVE = 1024;
	
	    private static int MAX_IDLE = 200;
	    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException
	    private static int MAX_WAIT = 10000;
     //连接超时的时间　　
	    private static int TIMEOUT = 10000;

// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	    private static boolean TEST_ON_BORROW = true;

	    private static JedisPool jedisPool = null;

	    /**
	     * 初始化Redis连接池
	     */

	    static {

	        try {

	            JedisPoolConfig conf = new JedisPoolConfig();
	            
//	            conf.setMaxTotal(MAX_ACTIVE);
//	            conf.setMaxIdle(MAX_IDLE);
//	            conf.setMaxWaitMillis(MAX_WAIT);
//	            conf.setTestOnBorrow(TEST_ON_BORROW);
	           // jedisPool = new JedisPool(conf,ADDR,PORT,TIMEOUT,AUTH);

	            
	            
	            
	        } catch (Exception e) {

	            e.printStackTrace();
	        }

	    }

	    /**
	     * 获取Jedis实例
	     */

	    public synchronized static Jedis getJedis() {

	        try {

	            if (jedisPool != null) {
	                Jedis resource = jedisPool.getResource();
	                return resource;
	            } else {
	                return null;
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }

	    }

	    /***
	     * 
	     * 释放资源
	     */
	    
	    public static void returnResource(final Jedis jedis) {
	            if(jedis != null) {
	                jedisPool.returnResource(jedis);
	            }
	        
	    }
	    
	    
	}