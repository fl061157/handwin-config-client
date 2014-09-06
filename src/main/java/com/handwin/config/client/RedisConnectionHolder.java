package com.handwin.config.client;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.alibaba.fastjson.JSON;
import com.handwin.config.net.ConfigQueryFrame;

/**
 * 
 * @author fangliang
 *
 */
public class RedisConnectionHolder extends ConnectionHolder<JedisPool> {
	
	
	private RedisConnectionHolder() { 
		
	}
	
	private static RedisConnectionHolder instance = new RedisConnectionHolder() ;
	
	public static RedisConnectionHolder getInstance() {
		return instance ;
	}
	
	
	@Override
	public void close(JedisPool t) {
		t.destroy(); 
	}
	
	@Override
	public JedisPool init(ConnectionConfig connectionConfig) {
		JedisConfig jedisConfig = (JedisConfig) connectionConfig ;
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(jedisConfig.getMaxActive());
        poolConfig.setMaxIdle(jedisConfig.getMaxIdle());
        System.out.println(jedisConfig.getHost() + ":" + jedisConfig.getPort() );
        JedisPool pool = new JedisPool(poolConfig , jedisConfig.getHost() , jedisConfig.getPort() ) ; 
		return pool ;
	}
	
	
	@Override
	public boolean isConnected(JedisPool p) {
		Jedis jedis = null ;
		try {
			jedis = p.getResource() ;
			return jedis != null && jedis.isConnected() ;
		} finally {
			if( jedis != null ) {
				if( ! jedis.isConnected() ) {
					p.returnBrokenResource( jedis ) ;
				} else {
					p.returnResource( jedis ); 
				}
			}
		}
	}
	
	@Override
	public ConnectionConfig parse(ConfigQueryFrame configQueryFrame) {
		return JSON.parseObject(configQueryFrame.getConfigMessage().getContent(), JedisConfig.class );  
	}

}
