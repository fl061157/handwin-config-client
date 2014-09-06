package com.handwin.config.client;

import java.util.HashMap;

import com.handwin.config.net.client.ClientFrameHandler;

/**
 * 
 * @author fangliang
 *
 */
public class ConfigCenter {
	
	private final String host ;
	private final int port ;
	private ClientFrameHandler handler ;
	
	private static HashMap<String, ConfigCenter> cache = new HashMap<String, ConfigCenter>();
	
	
	public static ConfigCenter getConfigCenter(String host, int port) {
		String address = formatAddress(host, port) ;
		synchronized ( cache ) { 
			ConfigCenter configCenter = cache.get( address ) ;
			if( configCenter == null ) {
				configCenter = new ConfigCenter(host, port) ;
				cache.put(address, configCenter ) ;
			}
			return configCenter ;
		}
	}
	
	
	private ConfigCenter(String host, int port) {
		this.host = host ;
		this.port = port ;
		handler = new ClientFrameHandler(host , port , new DefaultConfigQueryFrameHandler() ) ; 
	}
	
	
	public ClientFrameHandler getHandler() throws InterruptedException {
		return handler.start() ;
	}
	
	public String getHost() {
		return host;
	}
	
	public int getPort() {
		return port;
	}
	
	
	public static String formatAddress(String host, int port) {
		return String.format("%s:%d", host , port ) ;
	}
	
	
	
	

}
