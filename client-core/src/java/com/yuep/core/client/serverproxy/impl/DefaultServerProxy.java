/*
 * $Id: DefaultServerProxy.java, 2011-3-11 上午10:14:02 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.serverproxy.impl;

import java.io.Serializable;

import com.yuep.base.util.format.DateFormatter;
import com.yuep.core.bootstrap.def.ModuleContext;
import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.main.mainframe.action.system.SystemExitAction;
import com.yuep.core.client.serverproxy.def.ServerProxy;
import com.yuep.core.container.def.CommunicateObject;
import com.yuep.core.container.def.CoreContext;
import com.yuep.core.container.def.RemoteCommunicate4Container;
import com.yuep.core.container.def.RemoteCommunicateObject;
import com.yuep.core.container.impl.RemoteCommunicateObjectImpl;
import com.yuep.core.message.def.MessageReceiver;
import com.yuep.core.session.def.Session;
import com.yuep.core.session.def.SessionMessage;
import com.yuep.core.session.def.SessionState;

/**
 * <p>
 * Title: DefaultServerProxy
 * </p>
 * <p>
 * Description: 缺省的serverproxy实现，如果在lct下可以替换
 * </p>
 * 
 * @author sufeng
 * created 2011-3-11 上午10:14:02
 * modified [who date description]
 * check [who date description]
 */
public class DefaultServerProxy implements ServerProxy{
    
    private RemoteCommunicateObject remoteCommObject;
    
    @Override
    public void connect2Remote(String remoteSideIp, int port) {
        remoteCommObject = CoreContext.getInstance().remote(remoteSideIp,port);
        
        // 连接成功后，订阅本地子系统的会话检查消息
        MessageReceiver receiver=new MessageReceiver(){
            @Override
            public void receive(CommunicateObject co, String name, Serializable msg) {
                if(msg instanceof SessionMessage){
                    SessionMessage sysMsg=(SessionMessage)msg;
                    StringBuilder sb=new StringBuilder();
                    sb.append("Session change,");
                    String time=DateFormatter.getLongDate(sysMsg.getOccuredTime());
                    sb.append("time=").append(time).append(",content=[");
                    
                    RemoteCommunicateObject remote = sysMsg.getRemoteCommunicateObject();
                    if(remote!=null){
                        // 本地ping检测到的session状态变化
                        sb.append("link status=").append(remote.getLinkStatus()==1 ? "linkup," : "linkdown,");
                        sb.append("remote ip=").append(remote.getRemoteIp()).append(",");
                        sb.append("remote port=").append(remote.getRemoteServerPort());
                    }else{
                        // 服务端发送过来的踢出session消息
                        Session session = sysMsg.getSession();
                        if(session!=null){
                            if(remoteCommObject instanceof RemoteCommunicateObjectImpl){
                                if(session.getSessionId().equals(((RemoteCommunicateObjectImpl)remoteCommObject).getSessionId())){
                                    // 只关注link down的消息
                                    if(SessionState.Linkdown.equals(session.getSessionState())){
                                        sb.append("link status=").append(session.getSessionState()).append("]");
                                    }else{
                                        return; //忽略其他的session消息
                                    }
                                }else{
                                    // 不是自己的消息就discard掉
                                    return;
                                }
                            }else{
                                ModuleContext.getInstance().getCoreModule().getLogger().info("can not find session");
                                return;
                            }
                        }
                    }
                    // 在滚动日志输出栏上输出打印信息
                    ClientCoreContext.getOutputManager().addSystemMessage(sb.toString());
                    // 反注册消息,防止频繁进入这个状态(如果用户没确认退出,而没有取消注册该消息,则可能导致频繁进入该界面)
                    ClientCoreContext.unsubscribeLocal(SessionMessage.NAME, this);
                    // 退出client系统
                    SystemExitAction systemExitAction = new SystemExitAction(SystemExitAction.class.getSimpleName());
                    systemExitAction.setMessageReceiver(this);
                    systemExitAction.actionPerformed(null);
                }
            }
        };
        ClientCoreContext.subscribeLocal(SessionMessage.NAME, receiver);  // proxy来ping服务端,当linkdown会
        ClientCoreContext.subscribeRemote(SessionMessage.NAME, receiver); // 监听服务端踢出的消息
    }
    
    /**
     * 获取远端（比如：服务端）的服务接口
     * @param <T>
     * @param serviceName
     * @param serviceItf
     * @return
     */
    @Override
    public <T> T getRemoteService(String serviceName,Class<T> serviceItf){
        return remoteCommObject.getService(serviceName, serviceItf);
    }
    
    /**
     * 订阅remote消息
     * @param name 消息名
     * @param receiver 消息接收者
     */
    @Override
    public void subscribeRemote(String name, MessageReceiver receiver){
        remoteCommObject.subscribe(name, receiver);
    }
    
    /**
     * 取消订阅remote消息
     * @param name 消息名
     * @param receiver 消息接收者
     */
    @Override
    public void unsubscribeRemote(String name, MessageReceiver receiver){
        remoteCommObject.unsubscribe(name, receiver);
    }
    
    @Override
    public void disconnect() {
        if(remoteCommObject!=null){
            remoteCommObject.cleanup();
            remoteCommObject=null;
        }
    }
    
    @Override
    public Long getSessionId() {
        if(remoteCommObject!=null){
            if(remoteCommObject instanceof RemoteCommunicate4Container){
                return ((RemoteCommunicate4Container)remoteCommObject).getSessionId();
            }
        }
        return null;
    }
    
}
