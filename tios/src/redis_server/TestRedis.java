package redis_server;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;

public class TestRedis {

    private Jedis jedis;
    
    /**
     * ����redis������
     */
    public void connectRedis() {
        jedis=redis_util.getJedis();
    }
    
    /**
     * redis�����ַ���
     */
    public void testString() {
        //�������
        jedis.set("name", "youcong");
        System.out.println(jedis.get("name"));
        
        //ƴ���ַ���
        jedis.append("name", ".com");
        System.out.println(jedis.get("name"));
        
        //ɾ������
        jedis.del("name");
        System.out.println(jedis.get("name"));
        
        //���ö����ֵ��
        jedis.mset("name","yc","age","22","qq","1933108196");
        jedis.incr("age");//��1����
        System.out.println(jedis.get("name") + "-" + jedis.get("age") + "-" +jedis.get("qq"));
    }
    
    
    /**
     * redis����map����
     */
    public void testMap() {
        //�������
        Map<String,String> map = new HashMap<String,String>();
        
        map.put("name", "yc");
        map.put("age", "22");
        map.put("qq", "1933108196");
        jedis.hmset("user", map);
        
        //ȡ��users�е�Name,ִ�н��:[minxr]-->ע������һ�����͵�List
        //��һ�������Ǵ���redis��map�����key,��������Ƿ���map�ж����key,�����key�����Ƕ�����ǿɱ��
        List<String> rsmap = jedis.hmget("user", "name","age","qq");
        System.out.println(rsmap);
        
    
        //ɾ��map�е�ĳ����ֵ
        jedis.hdel("user", "age");
        System.out.println(jedis.hmget("user", "age"));//��Ϊɾ���ˣ����Է��ص���Null
        System.out.println(jedis.hlen("user"));//����keyΪuser�ļ��д�ŵ�ֵ�ĸ���2
        System.out.println(jedis.exists("user"));//�Ƿ����keyΪuser�ļ�¼������true
        System.out.println(jedis.hkeys("user"));//����map�����е�����key
        System.out.println(jedis.hvals("user"));//����map�����е�����value
        
        Iterator<String> iter = jedis.hkeys("user").iterator();
        while(iter.hasNext()) {
            String key = iter.next();
            System.out.println(key+":" + jedis.hmget("user", key));
        }
    
    }
    
    /**
     * redis����List����
     */
    public void testList() {
        //��ʼǰ�����Ƴ����е�����
        jedis.del("java framework");
        System.out.println(jedis.lrange("java framework", 0, -1));
        
        //����key java framework �д����������
        jedis.lpush("java framework","spring");
        jedis.lpush("java framework", "struts");
        jedis.lpush("java framework", "hibernate");
        
        //��ȡ����������jedis.lrange�ǰ���Χȡ��
        //��һ����key,�ڶ�������ʼλ�ã��������ǽ���λ�ã�jedis.llen��ȡ���� -1��ʾȡ������
        System.out.println(jedis.lrange("java framework", 0, -1));
        
        jedis.del("java framework");
        jedis.rpush("java framework", "spring");
        jedis.rpush("java framework", "struts");
        jedis.rpush("java framework","hibernate");
        System.out.println(jedis.lrange("java framework", 0, -1));
        
        
    }
    
    
    /**
     * redis����set����
     * 
     */
    
    public void testSet() {
        
        //���
        jedis.sadd("user", "liuling");
        jedis.sadd("user", "xinxin");
        jedis.sadd("user","ling");
        jedis.sadd("user", "zhangxinxin");
        jedis.sadd("user", "who");
        
        //ɾ��
        jedis.srem("user", "who");
        System.out.println(jedis.smembers("user"));//��ȡ���м����value
        System.out.println(jedis.sismember("user", "who"));//�ж�who�Ƿ���user���ϵ�Ԫ��
        System.out.println(jedis.srandmember("user"));
        System.out.println(jedis.scard("user"));//���ؼ��ϵ�Ԫ�ظ���
        
        
        
        
    }
    
    
    /**
     * redis����
     */
    
    public void testSort() {
        
        //jedis ����
        //ע�⣬�˴���rpush��lpush��List�Ĳ�������һ��˫���������ӱ���������)
        jedis.del("a");//��������ݣ��ټ������ݽ��в���
        jedis.rpush("a", "1");
        jedis.lpush("a", "6");
        jedis.lpush("a", "3");
        jedis.lpush("a", "9");
        System.out.println(jedis.lrange("a", 0, -1));
        System.out.println(jedis.sort("a"));//[1,3,6,9] //�����������
        System.out.println(jedis.lrange("a", 0, -1));
        
        
        
        
    }
    
    
    /**
     * redis���ӳ�
     */
    
    public void testRedisPool() {
        
        redis_util.getJedis().set("newname", "test");
        
        System.out.println(redis_util.getJedis().get("newname"));
        
        
    }
    
    public static void main(String[] args) {
        TestRedis test = new TestRedis();
        test.connectRedis();
        test.testSort();
    }
}