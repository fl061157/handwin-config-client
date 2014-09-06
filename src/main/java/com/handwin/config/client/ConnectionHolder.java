package com.handwin.config.client;

import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import com.handwin.config.net.BaseFrame;
import com.handwin.config.net.ConfigQueryFrame;
import com.handwin.config.net.SessionManager.Resources;

/**
 * 
 * @author fangliang
 *
 * @param <T>
 */
public abstract class ConnectionHolder<P> {
	
	private HashMap<Resources, P> connectionCache = new HashMap<Resources, P>();
	
	private ConfigCenter configCenter = ConfigCenter.getConfigCenter("127.0.0.1", 5555 ) ;
	
	public abstract P init( ConnectionConfig connectionConfig ) ; 
	
	public abstract ConnectionConfig parse(ConfigQueryFrame configQueryFrame ) ;
	
	public abstract boolean isConnected(P p) ;
	
	public void create( ConfigQueryFrame configQueryFrmae ) {
		Resources resources = new Resources(configQueryFrmae.getConfigMessage().getRegion(), configQueryFrmae.getConfigMessage().getBusiness() ) ; 
		ConnectionConfig connectionConfig = parse(configQueryFrmae ) ;
		P p = init(connectionConfig) ;
		if( p != null && isConnected(p) ) {
			connectionCache.put( resources, p ) ; 
		}
	}
	
	
	public P get(String region , String business) throws ConnectionException {
		try {
			BaseFrame baseFrame = configCenter.getHandler().rpc(region, business) ;
			if( baseFrame != null ) {
				ConfigQueryFrame configQueryFrame = (ConfigQueryFrame) baseFrame ;
				return get(configQueryFrame) ;
			} else {
				throw new ConnectionException("Get Config From Center Error!") ;
			}
		} catch (InterruptedException e) {
			throw new ConnectionException("Interrupted By Outer") ;
		} catch (TimeoutException e) {
			throw new ConnectionException("Timeout Exception !") ;
		}
	}
	
	
	public P get(ConfigQueryFrame configQueryFrmae ) throws ConnectionException { 
		Resources resources = new Resources(configQueryFrmae.getConfigMessage().getRegion(), configQueryFrmae.getConfigMessage().getBusiness() ) ; 
		P p = connectionCache.get(resources) ;
		ConnectionConfig connectionConfig = parse(configQueryFrmae ) ;
		if( p == null ) {
			p = init(connectionConfig) ;
			if( p != null && isConnected(p)) {
				connectionCache.put(resources, p ) ;
			} else {
				throw new ConnectionException("Connected Error !") ;
			}
			return p ;
		} 
		if( ! isConnected( p ) ) {
			connectionCache.remove( resources ) ;
			throw new ConnectionException("Connection Valiad Error !") ;
		}
		return p ;
	}
	
	public abstract void close( P p )  ;
	
	public void close(Resources resources ) {
		P p = connectionCache.get(resources) ;
		if( p != null ) {
			close( p ); 
			connectionCache.remove( resources ) ;
		}
	}
	
	
}
