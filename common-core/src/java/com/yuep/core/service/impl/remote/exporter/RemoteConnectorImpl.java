/*
 * $Id: RemoteConnectorImpl.java, 2010-12-2 上午11:49:20 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.service.impl.remote.exporter;

import java.rmi.server.RemoteServer;

import com.yuep.core.container.def.CoreContext;
import com.yuep.core.message.def.MessageMetadata;
import com.yuep.core.session.def.Session;
import com.yuep.core.session.def.SessionMessage;
import com.yuep.core.session.def.SessionService;
import com.yuep.core.session.def.SessionState;

/**
 * <p>
 * Title: RemoteConnectorImpl
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author sufeng
 * created 2010-12-2 上午11:49:20
 * modified [who date description]
 * check [who date description]
 */
public class RemoteConnectorImpl implements RemoteConnector{

    @Override
    public Long connect(String serverIp) {
        SessionService sessionService = CoreContext.getInstance().local().getService("sessionService", SessionService.class);
        // 如果线程上下文已经有了session，则直接返回
        Long sessionId=sessionService.getSessionId();
        if(sessionId!=null){
            // 测试代码：循环发送消息
            return sessionId;
        }
        
        // 否则创建一个新的session
        String requestIp=getRequestIp();
        Session session = sessionService.connect(requestIp, serverIp);
        return session.getSessionId();
    }

    @Override
    public void shutdown(Long sessionId) {
        SessionService sessionService = CoreContext.getInstance().local().getService("sessionService", SessionService.class);
        sessionService.cleanup(sessionId);
    }
    
    private String getRequestIp(){
        String ip=null;
        try{
            ip=RemoteServer.getClientHost();
            return ip;
        }catch(Exception ex){
            return "127.0.0.1";
        }
    }

    @Override
    public void ping(Long sessionId){
        SessionService sessionService = CoreContext.getInstance().local().getService("sessionService", SessionService.class);
        Session session = sessionService.getSessionById(sessionId);
        if(session!=null){
            session.setLastActivetime(System.currentTimeMillis());
            if(SessionState.Linkdown.equals(session.getSessionState())){
                session.setSessionState(SessionState.Linkup);
                // 发送session reconnect成功的消息
                CoreContext.getInstance().publish(SessionMessage.NAME, new SessionMessage(session));
            }
        }
    }

    @Override
    public MessageMetadata getMessageMetadata(Long sessionId) {
        CoreContext coreContext = CoreContext.getInstance();
        MessageMetadata messageMetadata=new MessageMetadata();
        messageMetadata.copy(coreContext.getSelfMessageMetadata());
        if(messageMetadata.getServerIp()==null){ 
            SessionService sessionService = CoreContext.getInstance().local().getService("sessionService", SessionService.class);
            Session session = sessionService.getSessionById(sessionId);
            messageMetadata.setServerIp(session.getServerIp());
        }
        return messageMetadata;
    }

}
