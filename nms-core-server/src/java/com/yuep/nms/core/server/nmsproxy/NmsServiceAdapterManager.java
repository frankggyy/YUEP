/*
 * $Id: NmsServiceAdapterManager.java, 2011-5-5 ����02:54:52 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.nmsproxy;

/**
 * <p>
 * Title: NmsServiceAdapterManager
 * </p>
 * <p>
 * Description:
 * �¼����ܷ�������������
 * </p>
 * 
 * @author yangtao
 * created 2011-5-5 ����02:54:52
 * modified [who date description]
 * check [who date description]
 */
public interface NmsServiceAdapterManager {
    /**
     * ע���¼����ܷ���������
     * @param serviceName
     *        �¼����ܷ�������
     * @param nmsServiceAdapterClass
     */
    public void registerNmsServiceAdapter(String serviceName,Class<?> nmsServiceAdapterClass);
    /**
     * ע���¼����ܷ���������
     * @param serviceName
     *        �¼����ܷ�������
     */
    public void deRegisterNmsServiceAdapter(String serviceName);
    
    /**
     * ��ȡ�¼����ܷ���������
     * @param serviceName
     *        �¼����ܷ�������
     * @return
     *       Class<?>
     */
    public Class<?> getNmsServiceAdapterClass(String serviceName);
}
