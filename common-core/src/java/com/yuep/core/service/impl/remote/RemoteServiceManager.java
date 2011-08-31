/*
 * $Id: RemoteServiceManager.java, 2011-1-31 ����12:22:10 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.service.impl.remote;

import com.yuep.core.container.def.RemoteCommunicateObject;

/**
 * <p>
 * Title: RemoteServiceManager
 * </p>
 * <p>
 * Description: service��ģ�����Զ�̷���
 * </p>
 * 
 * @author sufeng
 * created 2011-1-31 ����12:22:10
 * modified [who date description]
 * check [who date description]
 */
public interface RemoteServiceManager {

    /**
     * ��ʼ��Զ�˷��������
     */
    public void init();
    
    /**
     * �ͷ��񵼳�ΪRMI����
     * @param rmiPort
     * @param serviceName
     * @param interfaceClass
     * @param service
     */
    public void exportRemoteService(int rmiPort,String serviceName, Class<?> interfaceClass,Object service);

    /**
     * ��ȡԶ�̷���(�˴������棬��ȫ���ϲ���cache)
     * @param remoteCommunicationObject
     * @param serviceName
     * @param serviceItf ����ӿ�����
     * 
     */
    public <T> T getRemoteService(RemoteCommunicateObject remoteCommunicationObject,String serviceName, Class<T> serviceItf);
    
    /**
     * ��ע�����
     * @param serviceName
     * @param serviceItf
     */
    public void unregisterService(String serviceName, Class<?> serviceItf);
    
}