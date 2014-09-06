package com.handwin.config.client.jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

/**
 * 封装 Redis Client 端
 * @author fangliang
 *
 */
public class RedisImpl implements Redis {
	
	private JedisPool jedisPool ;
	public RedisImpl(JedisPool jedisPool) { 
		this.jedisPool = jedisPool ;
	}
	

	public String set(final String key, final String value) {
		return (String) safeExecute( new CallBack() {
			public Object execute(Jedis jedis) {
				return jedis.set(key, value) ;  
			}
		} ) ;
	}

	public String set(final String key, final byte[] value) {
		return (String) safeExecute( new CallBack() {
			
			public Object execute(Jedis jedis) {
				return jedis.set(key.getBytes(), value) ; 
			}
		} ) ;
	}
	
	
	public String get(final String key) {
		return (String) safeExecute(new CallBack() { 
			public Object execute(Jedis jedis) {
				return jedis.get(key);  
			}
		} ); 
	}
	
	public byte[] get(final byte[] key) {
		return (byte[]) safeExecute( new CallBack() {
			public Object execute(Jedis jedis) {
				return jedis.get(key); 
			}
		} ) ;
	}

	public long delete(String key) {
		// TODO Auto-generated method stub
		return 0;
	}

	public long incrBy(String key, long value) {
		// TODO Auto-generated method stub
		return 0;
	}

	public long decrBy(String key, long value) {
		// TODO Auto-generated method stub
		return 0;
	}

	public long lpush(String key, String value) {
		// TODO Auto-generated method stub
		return 0;
	}

	public long[] lpush(String[] keys, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	public long[] lpush(String key, String[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	public long[] lpush(String[] keys, String[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	public long rpush(String key, String value) {
		// TODO Auto-generated method stub
		return 0;
	}

	public long[] rpush(String[] keys, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	public long[] rpush(String key, String[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	public long[] rpush(String[] keys, String[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> rPop(String key, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> lPop(String key, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	public long llen(String key) {
		// TODO Auto-generated method stub
		return 0;
	}

	public long lrem(String key, long count, String value) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String ltrim(String key, long start, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> lrangeAndLtrim(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> lrange(String key, int start, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> List<T> lrange(String key, int start, int limit, Class<T> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	public long zadd(String key, double score, String member) {
		// TODO Auto-generated method stub
		return 0;
	}

	public long zrem(String key, String member) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Set<Tuple> zrevrangeWithScores(String key, int start, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<Tuple> zrangeWithScores(String key, int start, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	public void zremrangeByRank(String key, int start, int end) {
		// TODO Auto-generated method stub

	}

	public long zcount(String key, double min, double max) {
		// TODO Auto-generated method stub
		return 0;
	}

	public long zcard(String key) {
		// TODO Auto-generated method stub
		return 0;
	}

	public long hset(String key, String field, String value) {
		// TODO Auto-generated method stub
		return 0;
	}

	public long hdel(String key, String field) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String hget(String key, String field) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, String> hgetAll(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	public String hmset(String key, Map<String, String> hashMap) {
		// TODO Auto-generated method stub
		return null;
	}

	public long hincrBy(String key, String field, long value) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Set<String> smembers(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	public long sadd(String key, String value) {
		// TODO Auto-generated method stub
		return 0;
	}

	public long srem(String key, String value) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Set<String> smembersAndDelKey(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean sisMember(String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	public long sadd(String globalKey, String key, String value) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Set<String> smembers(String globalKey, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<String> sinter(String globalKey, String key1, String key2) {
		// TODO Auto-generated method stub
		return null;
	}

	public long expire(String key, int seconds) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void close() {
		// TODO Auto-generated method stub

	}

	public String getPoolName(String key) {
		// TODO Auto-generated method stub
		return null;
	}
	
	 private Object safeExecute(CallBack callBack) {
		 Jedis jedis = jedisPool.getResource() ;
		 if( jedis == null ) {
			 throw new RuntimeException("Jedis NotExists Exception !");
		 }
		 try {
			 return callBack.execute(jedis) ;
		 } catch (Exception e) {
			 jedisPool.returnBrokenResource( jedis ); 
			 throw new RuntimeException(e);
		 } finally {
			 jedisPool.returnResource( jedis ); 
		 }
	 }
	
	

}
