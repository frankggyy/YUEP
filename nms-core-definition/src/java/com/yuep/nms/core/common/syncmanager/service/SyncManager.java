/*
 * $Id: SyncManager.java, 2011-5-17 ����05:45:07 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.syncmanager.service;

import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.synccore.model.SyncTask;

/**
 * <p>
 * Title: SyncManager
 * </p>
 * <p>
 * Description:
 * ͬ������ӿ�
 * </p>
 * 
 * @author yangtao
 * created 2011-5-17 ����05:45:07
 * modified [who date description]
 * check [who date description]
 */
public interface SyncManager {
    /**
     * ͬ��
     * @param target
     *        ͬ������
     * @param
     *        ͬ������
     */
    public SyncTask sync(MoNaming target);
    
    /**
     * ͬ��ָ����ͬ���ڵ�
     * @param target
     *        ͬ������
     * @param syncNodes
     *        ͬ���ڵ�����
     * @return
     *        ͬ������
     */
    public SyncTask sync(MoNaming target,String...syncNodes);
    /**
     * �ر�ͬ������
     * @param target
     *        ͬ������
     * @param syncTaskId
     *        ͬ������Id
     */
    public void shutdown(MoNaming target,Long syncTaskId);
    
    
    
}
