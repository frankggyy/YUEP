/*
 * $Id: ServerProxy.java, 2011-3-11 ����10:13:52 sufeng Exp $
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
 * Description: server proxy�ӿ�
 * </p>
 * 
 * @author sufeng
 * created 2011-3-11 ����10:13:52
 * modified [who date description]
 * check [who date description]
 */
public interface ServerProxy {

    /**
     * ��ȡԶ�ˣ����磺����ˣ��ķ���ӿ�
     * @param <T>
     * @param serviceName
     * @param serviceItf
     * @return
     */
    public <T> T getRemoteService(String serviceName,Class<T> serviceItf);
    
    /**
     * ����remote��Ϣ
     * @param name ��Ϣ��
     * @param receiver ��Ϣ������
     */
    public void subscribeRemote(String name, MessageReceiver receiver);
    
    /**
     * ȡ������remote��Ϣ
     * @param name ��Ϣ��
     * @param receiver ��Ϣ������
     */
    public void unsubscribeRemote(String name, MessageReceiver receiver);
    
    /**
     * �Ͽ������˵�����
     */
    public void disconnect();
    
    /**
     * ���ӵ�Զ��
     * @param remoteSideIp
     * @param port
     */
    public void connect2Remote(String remoteSideIp, int port);
    
    /**
     * �õ�session id
     * @return
     */
    public Long getSessionId();
    
    
}
