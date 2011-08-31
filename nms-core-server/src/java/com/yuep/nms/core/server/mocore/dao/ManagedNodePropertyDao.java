/*
 * $Id: ManagedNodePropertyDao.java, 2011-3-28 ����03:20:00 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.mocore.dao;

import com.yuep.nms.core.common.mocore.model.ManagedNodeProperty;
import com.yuep.nms.core.common.mocore.naming.MoNaming;

/**
 * <p>
 * Title: ManagedNodePropertyDao
 * </p>
 * <p>
 * Description:
 * ����ڵ������������ݷ��ʽӿ�
 * </p>
 * 
 * @author yangtao
 * created 2011-3-28 ����03:20:00
 * modified [who date description]
 * check [who date description]
 */
public interface ManagedNodePropertyDao {

    /**
     * ����IP��ַ��ȡ����ڵ�
     * @param nm
     * @return
     */
    public ManagedNodeProperty getManagedNodeProperty(String ip);
    /**
     * ��ѯ����ڵ���������
     * @param nm
     *        Mo��ʶ
     * @return
     *        ����ڵ���������
     */
    public ManagedNodeProperty getManagedNodeProperty(MoNaming nm);
    
    /**
     * ���¹���ڵ���������
     * @param managedNodeProperty
     *        ����ڵ���������
     */
    public void updateManagedNodeProperty(ManagedNodeProperty managedNodeProperty);
    
    /**
     * ɾ������ڵ���������
     * @param nm
     *        Mo��ʶ
     */
    public void deleteManagerNodeProperty(MoNaming nm);
    
    /**
     * ��������ڵ���������
     * @param managedNodeProperty
     *        ����ڵ���������
     */        
    public void createManagerNodeProperty(ManagedNodeProperty managedNodeProperty);
}
