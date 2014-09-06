package com.handwin.config;

import redis.clients.jedis.JedisPool;

import com.handwin.config.client.RedisConnectionHolder;
import com.handwin.config.client.jedis.Redis;
import com.handwin.config.client.jedis.RedisImpl;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception 
    {
    	RedisConnectionHolder redisHolder = RedisConnectionHolder.getInstance() ;
    	JedisPool jedisPool = redisHolder.get("0086", "REDIS") ;
    	Redis redis = new RedisImpl(jedisPool) ;
    	//redis.set("hello_1", "hello_1_1") ; 
    	System.out.println( redis.get("hello_1") ); 
    }
}
