/*
 * $Id: ServerProxy.java, 2011-3-11 上午10:13:52 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.serverproxy.def;

import com.yuep.core.message.def.MessageReceiver;

/**
 * <p>
 * Title: ServerProxy
 * </p>
 * <p>
 * Description: server proxy接口
 * </p>
 * 
 * @author sufeng
 * created 2011-3-11 上午10:13:52
 * modified [who date description]
 * check [who date description]
 */
public interface ServerProxy {

    /**
     * 获取远端（比如：服务端）的服务接口
     * @param <T>
     * @param serviceName
     * @param serviceItf
     * @return
     */
    public <T> T getRemoteService(String serviceName,Class<T> serviceItf);
    
    /**
     * 订阅remote消息
     * @param name 消息名
     * @param receiver 消息接收者
     */
    public void subscribeRemote(String name, MessageReceiver receiver);
    
    /**
     * 取消订阅remote消息
     * @param name 消息名
     * @param receiver 消息接收者
     */
    public void unsubscribeRemote(String name, MessageReceiver receiver);
    
    /**
     * 断开与服务端的连接
     */
    public void disconnect();
    
    /**
     * 连接到远端
     * @param remoteSideIp
     * @param port
     */
    public void connect2Remote(String remoteSideIp, int port);
    
    /**
     * 得到session id
     * @return
     */
    public Long getSessionId();
    
    
}
