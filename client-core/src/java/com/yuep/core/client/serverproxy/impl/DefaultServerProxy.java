/*
 * $Id: DefaultServerProxy.java, 2011-3-11 ����10:14:02 sufeng Exp $
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
 * Description: ȱʡ��serverproxyʵ�֣������lct�¿����滻
 * </p>
 * 
 * @author sufeng
 * created 2011-3-11 ����10:14:02
 * modified [who date description]
 * check [who date description]
 */
public class DefaultServerProxy implements ServerProxy{
    
    private RemoteCommunicateObject remoteCommObject;
    
    @Override
    public void connect2Remote(String remoteSideIp, int port) {
        remoteCommObject = CoreContext.getInstance().remote(remoteSideIp,port);
        
        // ���ӳɹ��󣬶��ı�����ϵͳ�ĻỰ�����Ϣ
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
                        // ����ping��⵽��session״̬�仯
                        sb.append("link status=").append(remote.getLinkStatus()==1 ? "linkup," : "linkdown,");
                        sb.append("remote ip=").append(remote.getRemoteIp()).append(",");
                        sb.append("remote port=").append(remote.getRemoteServerPort());
                    }else{
                        // ����˷��͹������߳�session��Ϣ
                        Session session = sysMsg.getSession();
                        if(session!=null){
                            if(remoteCommObject instanceof RemoteCommunicateObjectImpl){
                                if(session.getSessionId().equals(((RemoteCommunicateObjectImpl)remoteCommObject).getSessionId())){
                                    // ֻ��עlink down����Ϣ
                                    if(SessionState.Linkdown.equals(session.getSessionState())){
                                        sb.append("link status=").append(session.getSessionState()).append("]");
                                    }else{
                                        return; //����������session��Ϣ
                                    }
                                }else{
                                    // �����Լ�����Ϣ��discard��
                                    return;
                                }
                            }else{
                                ModuleContext.getInstance().getCoreModule().getLogger().info("can not find session");
                                return;
                            }
                        }
                    }
                    // �ڹ�����־������������ӡ��Ϣ
                    ClientCoreContext.getOutputManager().addSystemMessage(sb.toString());
                    // ��ע����Ϣ,��ֹƵ���������״̬(����û�ûȷ���˳�,��û��ȡ��ע�����Ϣ,����ܵ���Ƶ������ý���)
                    ClientCoreContext.unsubscribeLocal(SessionMessage.NAME, this);
                    // �˳�clientϵͳ
                    SystemExitAction systemExitAction = new SystemExitAction(SystemExitAction.class.getSimpleName());
                    systemExitAction.setMessageReceiver(this);
                    systemExitAction.actionPerformed(null);
                }
            }
        };
        ClientCoreContext.subscribeLocal(SessionMessage.NAME, receiver);  // proxy��ping�����,��linkdown��
        ClientCoreContext.subscribeRemote(SessionMessage.NAME, receiver); // ����������߳�����Ϣ
    }
    
    /**
     * ��ȡԶ�ˣ����磺����ˣ��ķ���ӿ�
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
     * ����remote��Ϣ
     * @param name ��Ϣ��
     * @param receiver ��Ϣ������
     */
    @Override
    public void subscribeRemote(String name, MessageReceiver receiver){
        remoteCommObject.subscribe(name, receiver);
    }
    
    /**
     * ȡ������remote��Ϣ
     * @param name ��Ϣ��
     * @param receiver ��Ϣ������
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
