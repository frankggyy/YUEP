/*
 * $Id: MessageServiceManager.java, 2011-2-25 ����11:46:37 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.message.impl;

import java.io.Serializable;

import javax.jms.MessageListener;

import com.yuep.core.container.def.RemoteCommunicateObject;
import com.yuep.core.message.def.MessageMetadata;
import com.yuep.core.message.def.MessageReceiver;

/**
 * <p>
 * Title: MessageServiceManager
 * </p>
 * <p>
 * Description:����Ϣ���й���ķ���ӿ�
 * </p>
 * 
 * @author sufeng
 * created 2011-2-25 ����11:46:37
 * modified [who date description]
 * check [who date description]
 */
public interface MessageServiceManager {
    
    /**
     * ��ʼ������JMS��Ϣ�Ĵ���
     * @param remote
     * @param msgMetadata
     * @param msgListener
     */
    public void initMessageReceive(RemoteCommunicateObject remote,MessageMetadata msgMetadata,MessageListener msgListener);
    
    /**
     * �滻MessageCenter
     * @param name
     * @param msg
     */
    public void publish(String name, Serializable msg);
    
    /**
     * ���ı�����Ϣ
     * @param name
     * @param receiver
     */
    public void subscribe(String name, MessageReceiver receiver);
    
    /**
     * �˶�������Ϣ
     * @param name
     * @param receiver
     */
    public void unsubscribe(String name, MessageReceiver receiver);
    
    /**
     * ������Ϣ�������Ļ���������Ϣ
     * @param messageMetadata
     */
    public void setSelfMessageMetadata(MessageMetadata messageMetadata);
    
    /**
     * ��ȡ��Ϣ�������Ļ���������Ϣ
     * @return
     */
    public MessageMetadata getSelfMessageMetadata();
    
    /**
     * ����Զ����Ϣ
     * @param remote
     * @param sessionId
     * @param name
     */
    public void subscribeRemoteMessage(RemoteCommunicateObject remote,long sessionId, String name);
    
    /**
     * �˶�Զ����Ϣ
     * @param remote
     * @param sessionId
     * @param name
     */
    public void unsubscribeRemoteMessage(RemoteCommunicateObject remote,long sessionId, String name);
    
    
}
