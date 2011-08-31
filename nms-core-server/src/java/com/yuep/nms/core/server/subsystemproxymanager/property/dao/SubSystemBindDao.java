/*
 * $Id: SubSystemBindsDao.java, 2011-5-16 ����06:02:49 yangtao Exp $
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
import com.yuep.nms.core.common.subsystemproxycore.SubSystemBind;

/**
 * <p>
 * Title: SubSystemBindsDao
 * </p>
 * <p>
 * Description:
 * ��ϵͳ����Ϣ������ڵ����ϵͳ�İ󶨹�ϵ
 * </p>
 * 
 * @author yangtao
 * created 2011-5-16 ����06:02:49
 * modified [who date description]
 * check [who date description]
 */
public interface SubSystemBindDao {
    
    /**
     * ������ϵͳ����Ϣ
     * @param subSystemId
     *        ��ϵͳId
     * @param mangedNode
     *        ����ڵ�
     */
    public void setSubSystemBind(MoNaming subSystemId, MoNaming mangedNode);
    /**
     * ɾ����ϵͳ����Ϣ
     * @param subSystemId
     *        ��ϵͳId
     * @param mangedNode
     *        ����ڵ�
     */
    public void deleteSubSystemBind(MoNaming subSystemId, MoNaming mangedNode);
    
    /**
     * ��ȡ���е���ϵͳ����Ϣ
     * @return
     *       ���е���ϵͳ����Ϣ
     */
    public List<SubSystemBind> getAllSubSystemBinds();

}
