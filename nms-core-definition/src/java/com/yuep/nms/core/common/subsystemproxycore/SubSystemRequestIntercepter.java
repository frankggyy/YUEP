/*
 * $Id: SubSystemServiceRequestIntecepter.java, 2011-5-16 ����08:29:02 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.subsystemproxycore;

import java.lang.reflect.Method;

import com.yuep.nms.core.common.mocore.naming.MoNaming;

/**
 * <p>
 * Title: SubSystemServiceRequestIntecepter
 * </p>
 * <p>
 * Description:
 * ��ϵͳ������������
 * </p>
 * 
 * @author yangtao
 * created 2011-5-16 ����08:29:02
 * modified [who date description]
 * check [who date description]
 */
public interface SubSystemRequestIntercepter {
    /**
     * ��ϵͳ��������
     * @param <T>
     * @param subSystemProxy
     *         ��ϵͳ����
     * @param managedNode
     *          ����ڵ�
     * @param serviceName
     *          ��������
     * @param serviceIntefaceClass
     *           ����ӿ�
     * @param proxy
     * @param method
     * @param args
     * @return
     */
    public <T> Object invoke(SubSystemProxy subSystemProxy,MoNaming managedNode, String serviceName, Class<T> serviceIntefaceClass,Object proxy, Method method, Object[] args);
    

}
