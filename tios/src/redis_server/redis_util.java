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
	    //�ȴ��������ӵ����ʱ�䣬��λ���룬Ĭ��ֵΪ-1����ʾ������ʱ����������ȴ�ʱ�䣬��ֱ���׳�JedisConnectionException
	    private static int MAX_WAIT = 10000;
     //���ӳ�ʱ��ʱ�䡡��
	    private static int TIMEOUT = 10000;

// ��borrowһ��jedisʵ��ʱ���Ƿ���ǰ����validate���������Ϊtrue����õ���jedisʵ�����ǿ��õģ�
	    private static boolean TEST_ON_BORROW = true;

	    private static JedisPool jedisPool = null;

	    /**
	     * ��ʼ��Redis���ӳ�
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
	     * ��ȡJedisʵ��
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
	     * �ͷ���Դ
	     */
	    
	    public static void returnResource(final Jedis jedis) {
	            if(jedis != null) {
	                jedisPool.returnResource(jedis);
	            }
	        
	    }
	    
	    
	}