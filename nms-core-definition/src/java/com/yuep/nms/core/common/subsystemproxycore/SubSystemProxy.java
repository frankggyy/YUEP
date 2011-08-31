/*
 * $Id: SubSystemProxy.java, 2011-5-16 ����06:37:06 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.subsystemproxycore;

import java.io.Serializable;

import com.yuep.core.container.def.CommunicateObject;
import com.yuep.core.message.def.MessageReceiver;
import com.yuep.nms.core.common.mocore.naming.MoNaming;

/**
 * <p>
 * Title: SubSystemProxy
 * </p>
 * <p>
 * Description:
 * ��ϵͳ����ӿ�
 * </p>
 * 
 * @author yangtao
 * created 2011-5-16 ����06:37:06
 * modified [who date description]
 * check [who date description]
 */
public interface SubSystemProxy extends Serializable{

    /**
     * ��ʼ����ϵͳ����
     * @param subSystemProxyProperty
     *        ��ϵͳ��������
     */
    public void init(SubSystemProxyProperty subSystemProxyProperty);
    /**
     * ��ȡ��ϵͳ�����������
     * @return
     *        ��ϵͳ�����������
     */
    public SubSystemProxyProperty getSubSystemProxyProperty();
    
    /**
     * ��ȡͨ�Ŷ���
     * @return
     *    CommunicateObject
     */
    public CommunicateObject getCommunicateObject();
    /**
     * ������ϵͳ����
     */
    public void destory();
    
    /**
     * ���ݷ�������,����ӿڻ�ȡ����
     * @param <T>
     * @param managedNode
     *        ����ڵ�MoNaming
     * @param serviceName
     *         ��������
     * @param serviceIntefaceClass
     *         ����ӿ���
     * @return
     *      ��ϵͳԶ�̷���
     */
    public <T> T getService(MoNaming managedNode,String serviceName,Class<T> serviceIntefaceClass);

    /**
     * ͨ����ϵͳ��������Ϣ
     * @param name
     *        ��Ϣ����
     * @param messageReceiver
     *        ��Ϣ������
     */
    public void subscribe(String name,MessageReceiver messageReceiver);
    /**
     * ͨ����ϵͳ����ⶩ����Ϣ
     * @param name
     *        ��Ϣ����
     * @param messageReceiver
     *        ��Ϣ������
     */
    public  void unsubscribe(String name,MessageReceiver messageReceiver);
}
