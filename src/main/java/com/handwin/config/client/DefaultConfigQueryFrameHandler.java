package com.handwin.config.client;

import com.handwin.config.net.ConfigQueryFrame;
import com.handwin.config.net.SessionManager.Resources;
import com.handwin.config.net.client.ConfigQueryFrameHandler;

/**
 * 
 * @author fangliang
 * Event 更改事件
 *
 */
public class DefaultConfigQueryFrameHandler implements ConfigQueryFrameHandler {

	
	
	public void handle(ConfigQueryFrame configQuery) { //更改的列表 包需要修改一下吗 加上增删改 字段
		//TODO 暂时先这么做下 后面处理之
		Resources resources = new Resources(configQuery.getConfigMessage().getRegion(), configQuery.getConfigMessage().getBusiness() ) ; 
		if( resources.getBusiness().equals("REDIS") ) { //TODO
			
			System.out.println("FUCK!!!!!!!!!!!!");
			
			RedisConnectionHolder.getInstance().close(resources); 
			RedisConnectionHolder.getInstance().create( configQuery );
		}
		//TODO
	}

}
