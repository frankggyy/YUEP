/*
 * $Id: SubSystemPropertyDao.java, 2011-5-16 ����06:01:55 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.subsystemproxymanager.property.dao;

import java.util.List;

import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyProperty;

/**
 * <p>
 * Title: SubSystemPropertyDao
 * </p>
 * <p>
 * Description:
 * ��ϵͳ�����������ݷ��ʲ�ӿ�
 * </p>
 * 
 * @author yangtao
 * created 2011-5-16 ����06:01:55
 * modified [who date description]
 * check [who date description]
 */
public interface SubSystemProxyPropertyDao {

    /**
     * ����SubSystemProxyProperty
     * @param subSystemProxyProperty
     */
    public void createSubSystemProxyProperty(SubSystemProxyProperty subSystemProxyProperty);

    /**
     * ����SubSystemProxyProperty
     * @param subSystemProxyProperty
     */
    public void updateSubSystemProxyProperty(SubSystemProxyProperty subSystemProxyProperty);
    /**
     * ɾ����ϵͳ��������
     * @param subSystemId
     *        ��ϵͳId
     */
    public void deleteSubSystemProxyProperty(MoNaming subSystemId);
    
    /**
     * ��ȡ���е���ϵͳ��������
     * @return
     */
    public List<SubSystemProxyProperty> getAllSubSystemProxyProperties();
}
