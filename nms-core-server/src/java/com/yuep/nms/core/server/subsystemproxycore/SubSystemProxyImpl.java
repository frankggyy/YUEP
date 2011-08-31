/*
 * $Id: SubSystemSystemImpl.java, 2011-5-16 下午06:37:59 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.subsystemproxycore;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.yuep.core.container.def.CommunicateObject;
import com.yuep.core.container.def.CoreContext;
import com.yuep.core.container.def.RemoteCommunicateObject;
import com.yuep.core.message.def.MessageReceiver;
import com.yuep.core.session.def.SessionMessage;
import com.yuep.nms.core.common.mocore.exception.MoException;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxy;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyCore;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyProperty;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemRequestIntercepter;

/**
 * <p>
 * Title: SubSystemSystemImpl
 * </p>
 * <p>
 * Description:
 * SubSystemProxy接口实现类
 * </p>
 * 
 * @author yangtao
 * created 2011-5-16 下午06:37:59
 * modified [who date description]
 * check [who date description]
 */
public class SubSystemProxyImpl implements SubSystemProxy,MessageReceiver {
    
    private static final long serialVersionUID = -3900687132283713396L;
    
    private SubSystemProxyProperty subSystemProxyProperty;
    
    private transient CoreContext coreContext=null;
    
    private transient SubSystemProxyCore subSystemProxyCore=null;
    
    private transient CommunicateObject communicateObject=null;
    
    private Map<String, ArrayList<MessageReceiver>> messageReceivers = new ConcurrentHashMap<String, ArrayList<MessageReceiver>>();
    
    public SubSystemProxyImpl(){
    }

    public void setCoreContext(CoreContext coreContext){
        this.coreContext=coreContext;
    }

    public void setSubSystemProxyCore(SubSystemProxyCore subSystemProxyCore){
       this.subSystemProxyCore=subSystemProxyCore;
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.subsystemproxycore.SubSystemProxy#getSubSystemProxyProperty()
     */
    @Override
    public SubSystemProxyProperty getSubSystemProxyProperty() {
        return subSystemProxyProperty;
    }
    
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.subsystemproxycore.SubSystemProxy#init(com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyProperty)
     */
    @Override
    public void init(SubSystemProxyProperty subSystemProxyProperty) {
        this.subSystemProxyProperty=subSystemProxyProperty;
        if(subSystemProxyProperty.getIp().equals("localhost")){
            communicateObject=coreContext.local();
            return;
        }
        if(communicateObject==null){
            try{
                communicateObject=coreContext.remote(subSystemProxyProperty.getIp(), subSystemProxyProperty.getPort());
            }catch(Exception ex){
                connectSubSystem();//连接子系统
            }
        }
        else{
            ((RemoteCommunicateObject)communicateObject).resetRemoteServer(subSystemProxyProperty.getIp(), subSystemProxyProperty.getPort());
        }
    }
    
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.subsystemproxycore.SubSystemProxy#getCommunicateObject()
     */
    @Override
    public CommunicateObject getCommunicateObject() {
        return communicateObject;
    }

    private void connectSubSystem(){
        Thread connectSubSystemThread=new Thread(){
            @SuppressWarnings("static-access")
            public void run(){
                while(communicateObject==null){
                    try{
                        communicateObject=coreContext.remote(subSystemProxyProperty.getIp(), subSystemProxyProperty.getPort());
                    }catch(Exception ex){
                        try {
                            Thread.currentThread().sleep(5000);
                        } catch (InterruptedException ie) {
                            
                        }
                    }
                }
                //订阅子系统Session消息
                subscribeSessionMessage();
                //订阅所有消息(如果有消息的话)
                subscribe();
            }
        };
        connectSubSystemThread.start();
    }
    
    private void subscribe(){
        for(Entry<String,ArrayList<MessageReceiver>> entry:messageReceivers.entrySet()){
            String messageName=entry.getKey();
            List<MessageReceiver> messageReceivers=entry.getValue();
            for(MessageReceiver messageReceiver:messageReceivers){
                communicateObject.subscribe(messageName, messageReceiver);
            }
        }
    }
    
    private void subscribeSessionMessage(){
        //remoteCommunicationObject.subscribe(SessionMessage.NAME, this);
        // 订阅本地proxy的session状态变化的消息
        coreContext.local().subscribe(SessionMessage.NAME, this);
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.subsystemproxycore.SubSystemProxy#destory()
     */
    @Override
    public void destory() {
        if(communicateObject!=null&&communicateObject instanceof RemoteCommunicateObject){
            RemoteCommunicateObject remoteCommunicationObject=(RemoteCommunicateObject)communicateObject;
            remoteCommunicationObject.cleanup();
        }
        communicateObject=null;
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.subsystemproxycore.SubSystemProxy#getService(com.yuep.nms.core.common.mocore.naming.MoNaming, java.lang.String, java.lang.Class)
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T getService(final MoNaming managedNode, final String serviceName,final Class<T> serviceInterfaceClass) {
        final SubSystemRequestIntercepter subSystemRequestIntercepter=subSystemProxyCore.getSubSystemRequestIntecepter(this.getSubSystemProxyProperty().getSubSystemType());
        if(subSystemRequestIntercepter!=null){
            return (T)Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{serviceInterfaceClass}, new InvocationHandler(){
                @Override
                public Object invoke(Object proxy, Method method, Object[] args)throws Throwable {
                    validateRemoteCommunicationObject();
                    return subSystemRequestIntercepter.invoke(SubSystemProxyImpl.this, managedNode, serviceName, serviceInterfaceClass, proxy, method, args);
                }
            });
        }else{
            return (T)communicateObject.getService(serviceName, serviceInterfaceClass);
        }
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.subsystemproxycore.SubSystemProxy#subscribe(java.lang.String, com.yuep.core.message.def.MessageReceiver)
     */
    @Override
    public void subscribe(String name, MessageReceiver messageReceiver) {
        if(!messageReceivers.containsKey(name)){
            messageReceivers.put(name, new ArrayList<MessageReceiver>());
        }
        messageReceivers.get(name).add(messageReceiver);
        
        if(communicateObject!=null)
            communicateObject.subscribe(name, messageReceiver);
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.subsystemproxycore.SubSystemProxy#unsubscribe(java.lang.String, com.yuep.core.message.def.MessageReceiver)
     */
    @Override
    public void unsubscribe(String name, MessageReceiver messageReceiver) {
        messageReceivers.get(name).remove(messageReceiver);
        
        if(communicateObject!=null)
            communicateObject.unsubscribe(name, messageReceiver);
    }

    
    private void validateRemoteCommunicationObject(){
        if(communicateObject==null){
            throw new MoException(MoException.CONNETION_FAILURE,new String[]{subSystemProxyProperty.getIp(),subSystemProxyProperty.getPort()+""});
        }
   }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.message.def.MessageReceiver#receive(com.yuep.core.container.def.CommunicateObject, java.lang.String, java.io.Serializable)
     */
    @Override
    public void receive(CommunicateObject co, String name, Serializable msg) {
        SessionMessage sessionMessage=(SessionMessage)msg;
        RemoteCommunicateObject remote = sessionMessage.getRemoteCommunicateObject();
        if(communicateObject.equals(remote)){
            if(remote.getLinkStatus()==1){
                RemoteCommunicateObject remoteCommunicateObject=(RemoteCommunicateObject)communicateObject;
                remoteCommunicateObject.resetRemoteServer(subSystemProxyProperty.getIp(), subSystemProxyProperty.getPort());
            }
        }
        //if(sessionMessage.getSession().getSessionState()==SessionState.Active){
        //    remoteCommunicationObject.resetRemoteServer(ip, port);
        //}
    }

}
