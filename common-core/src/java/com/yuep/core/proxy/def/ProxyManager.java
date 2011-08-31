/*
 * $Id: ProxyManager.java, 2011-1-31 ����01:26:14 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.proxy.def;

import com.yuep.core.container.def.RemoteCommunicateObject;

/**
 * <p>
 * Title: ProxyManager
 * </p>
 * <p>
 * Description: ����Զ����ϵͳ�ڱ�����ϵͳ�Ĵ���
 * </p>
 * 
 * @author sufeng
 * created 2011-1-31 ����01:26:14
 * modified [who date description]
 * check [who date description]
 */
public interface ProxyManager {

    /**
     * ����proxy���������ϲ�Ӧ�ÿ�ͨ���÷����滻Ĭ�ϵ�ProxyProcessor��
     * @param comm
     * @param proxyProcessor
     */
    public void setProxyProcessor(RemoteCommunicateObject comm,ProxyProcessor proxyProcessor);
    
    /**
     * �õ���ǰ��ProxyProcessor
     * @param comm Զ��ͨ�Ŷ���
     * @return ProxyProcessor
     */
    public ProxyProcessor getProxyProcessor(RemoteCommunicateObject comm);
    
    /**
     * �ر�proxy
     * @param comm Զ��ͨ�Ŷ���
     */
    public void closeProxy(RemoteCommunicateObject comm);
    
}
