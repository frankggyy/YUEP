/*
 * $Id: SyncExecutor.java, 2009-4-14 ����08:58:35 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.synccore.execute;

import java.util.List;

import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.synccore.model.SyncTask;

/**
 * <p>
 * Title: SyncExecutor
 * </p>
 * <p>
 * Description: <br>
 * ͬ������ִ�нӿ�
 * </p>
 * 
 * @author yangtao
 * created 2009-4-14 ����08:58:35
 * modified [who date description]
 * check [who date description]
 */
public interface SyncExecutor {

    /**
     * �ر�ͬ������
     * 
     * @param target
     *        ͬ������
     */
    public void shutdown(MoNaming target);

    /**
     * ִ��ͬ������
     * 
     * @param target
     *            ͬ���������ڵ���Ԫ
     * @param syncTask
     *            ͬ������
     */
    public void execute(MoNaming target, SyncTask syncTask);

    /**
     * ��ȡ��ǰ���е�ͬ������
     * 
     * @return
     */
    public List<SyncTask> getAllSyncTasks();
    /**
     * ��ȡָ������ͬ������
     * @param target
     *          ͬ������
     * @return
     */
    public List<SyncTask> getSyncTasks(MoNaming target);
    
    /**
     * ��ȡָ�������������е�ͬ������
     * @param target
     *          ͬ������
     * @return
     *          �������е�ͬ������
     */
    public SyncTask getRunningSyncTask(MoNaming target);
    /**
     * ͬ�������Ƿ��Ѿ�����
     * 
     * @param syncTask
     *        ͬ������
     * @return
     *        true���ڣ�false������
     */
    public boolean isExisted(SyncTask syncTask);

    /**
     * ��Ԫtarget�Ƿ����ͬ������
     * 
     * @param target
     *        ͬ������
     * @return
     */
    public boolean isRunningSyncTasks(MoNaming target);

    /**
     * ��ȡ����ͬ���Ķ���
     * 
     * @return
     */
    public List<MoNaming> getAllSyncTargets();

    /**
     * �ر�ͬ������
     * 
     * @param syncTask
     */
    public void shutdown(SyncTask syncTask);

    /**
     * ����ͬ������ִ����
     */
    public void start();

    /**
     * �ر�ͬ������ִ����
     */
    public void close();

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