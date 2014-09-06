package com.handwin.config.client.jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Tuple;


/**
 * 封装 Jedis 客户端
 * @author fangliang
 *
 */

public interface Redis {
	
	public static final String NULL = "nil";
	
	/********************************* string *************************************/
	public  String set( String key,  String value);
	
	public  String set( String key,  byte[] value); 
	
	public  String get( String key);
	
	public byte[] get(byte[]  key ) ;
	
	public long delete(String key);
	public long incrBy(final String key, final long value);
	public long decrBy(final String key, final long value);
	
	/********************************* list ****************************************/
	public long   lpush(String   key, String   value);
	public long[] lpush(String[] keys,String   value);
	public long[] lpush(String   key, String[] values);
	public long[] lpush(String[] keys,String[] values);
	
	public long   rpush(String   key, String   value);
	public long[] rpush(String[] keys,String   value);
	public long[] rpush(String   key, String[] values);
	public long[] rpush(String[] keys,String[] values);
	
	public List<String> rPop(String key , int size);
	
	public List<String> lPop(String key , int size);
	
	//队列大小
	public long   llen(String key);
	public long   lrem(String key, long count, String value);
	public String ltrim(String key,  long start,long end);
	
	public List<String> lrangeAndLtrim(String key);
	public List<String> lrange(String key, int start, int limit);
	public <T> List<T>  lrange(String key, int start, int limit,Class<T> clazz);
    
	/********************************* sorted set ***********************************/
	public long zadd( String key, double score, String member);
//	public long[] zadd( String[] keys, double score,String member);
//	public long[] zadd( String[] keys, double[] scores, String[] members);
	public long   zrem( String    key, String member);
	public Set<Tuple> zrevrangeWithScores(String key, int start, int limit);
	
	public Set<Tuple> zrangeWithScores(String key, int start, int limit);
	public void zremrangeByRank(String key, int start, int end);
	public long zcount(String key, final double min, final double max);
	public long zcard(String key);
	
	/********************************** hash ********************************************/
	public long hset( String key,  String field, String value);
	public long hdel( String key,  String field);
	public String hget( String key,  String field);
	public Map<String, String> hgetAll( String key);
	public String hmset( String key, Map<String,String> hashMap);
	public long hincrBy( String key,  String field, long value);
	
	/********************************** set  *************************************************/
	public Set<String> smembers(String key);
	public long sadd(String key, String value);
	public long srem(String key, String value);
	public Set<String> smembersAndDelKey(String key) ;
	public Boolean sisMember( String key , final String value );
	
	
	
	public long sadd(String globalKey , String key, final String value);
	public Set<String> smembers(String globalKey , String key);
	public Set<String> sinter(String globalKey , String key1 , String key2);
	public long expire(String key, int seconds);
	public void close();
	
	
	public String getPoolName(String key);
	
}
