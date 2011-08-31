/*
 * $Id: SyncListener.java, 2009-3-6 ����03:25:40 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.synccore.execute;

import com.yuep.nms.core.common.synccore.model.SyncItem;
import com.yuep.nms.core.common.synccore.model.SyncResult;
import com.yuep.nms.core.common.synccore.model.SyncTask;

/**
 * <p>
 * Title: SyncListener
 * </p>
 * <p>
 * Description:
 * ͬ�����������
 * </p>
 * 
 * @author yangtao
 * created 2009-3-6 ����03:25:40
 * modified [who date description]
 * check [who date description]
 */
public interface SyncListener {

    /**
     * ��ʼͬ������
     * 
     * @param syncTask
     */
    public void startSyncTask(SyncTask syncTask);

    /**
     * ����ͬ������
     * 
     * @param syncTask
     */
    public void endSyncTask(SyncTask syncTask);

    /**
     * ��ʼִ��ͬ����
     * 
     * @param syncItem
     */
    public void startSyncItem(SyncItem syncItem);

    /**
     * ����ִ��ͬ����
     * 
     * @param syncResult
     */
    public void endSyncItem(SyncResult syncResult);
    
    /**
     * �ر�ͬ������
     * @param syncTask
     */
    public void shutdownSyncTask(SyncTask syncTask);

}
