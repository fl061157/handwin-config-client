package com.handwin.config.client.jedis;

import redis.clients.jedis.Jedis;

/**
 * 
 * @author fangliang
 *
 */
public interface CallBack {
	
	public Object execute(Jedis jedis);

}
