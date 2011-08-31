/*
 * $Id: RemoteServiceExporter.java, 2010-12-2 ����11:11:35 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.service.impl.remote.exporter;

import org.springframework.remoting.rmi.RmiServiceExporter;

import com.yuep.base.exception.YuepException;
import com.yuep.base.log.def.YuepLog;

/**
 * <p>
 * Title: RemoteServiceExporter
 * </p>
 * <p>
 * Description: ��¶Զ��RMI����
 * </p>
 * 
 * @author sufeng
 * created 2010-12-2 ����11:11:35
 * modified [who date description]
 * check [who date description]
 */
public class RemoteServiceExporter {
    
    /**
     * ��ĳ���ӿ�ע��ΪRMIԶ�̷���
     * @param serviceInterface �ӿ�����
     * @param service   ����ʵ�������
     * @param rmiPort   �˿ں�
     */
    public static void register(Class<?> serviceInterface,Object service,int rmiPort){
        // Ϊ�˱��Ȿ��bean��Զ��bean�غ�,�˴���bean����Ҫע�ᵽspring������,��Ҫ�����Լ�ά��cache
        RmiServiceExporter rmiExporter=new RmiServiceExporter();
        rmiExporter.setServiceName(serviceInterface.getSimpleName());
        rmiExporter.setService(service);
        rmiExporter.setServiceInterface(serviceInterface);
        rmiExporter.setRegistryPort(rmiPort);
        try{
            rmiExporter.afterPropertiesSet();
        }catch(Exception ex){
            YuepLog.getDefaultLogger().error("",ex);
            throw new YuepException(ex);
        }
    }

}
