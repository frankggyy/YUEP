/*
 * $Id: SyncCore.java, 2011-5-17 ����05:49:54 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.synccore.service;

import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.synccore.model.SyncTask;
import com.yuep.nms.core.server.synccore.execute.SyncListener;

/**
 * <p>
 * Title: SyncCore
 * </p>
 * <p>
 * Description:
 * ͬ��ģ����ķ���
 * </p>
 * 
 * @author yangtao
 * created 2011-5-17 ����05:49:54
 * modified [who date description]
 * check [who date description]
 */
public interface SyncCore {
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
    
    /**
     * ���ͬ��������
     * 
     * @param syncListener
     *        ͬ�����ȼ����ӿ�
     */
    public void addSyncListener(SyncListener syncListener);

    /**
     * �Ƴ�ͬ��������
     * 
     * @param syncListener
     *          ͬ�����ȼ����ӿ�
     */
    public void removeSyncListener(SyncListener syncListener);
    

}
