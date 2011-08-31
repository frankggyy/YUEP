/*
 * $Id: SyncExecutorImpl.java, 2009-4-14 ����09:03:28 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.synccore.execute;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.collections.CollectionUtils;

import com.yuep.base.log.def.Logger;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.mocore.service.MoCore;
import com.yuep.nms.core.common.synccore.model.SyncTask;

/**
 * <p>
 * Title: SyncExecutorImpl
 * </p>
 * <p>
 * Description: <br>
 * ͬ������ִ����ʵ����
 * </p>
 * 
 * @author yangtao
 * created 2009-4-14 ����09:03:28
 * modified [who date description]
 * check [who date description]
 */
public class SyncExecutorImpl implements SyncExecutor {
    // �ȴ�ִ�е�ͬ���������
    private final ConcurrentMap<MoNaming, List<SyncTask>> waitingTasks = new ConcurrentHashMap<MoNaming, List<SyncTask>>();
    // ͬ������ִ���̳߳�
    private final SyncThreadPoolExecutor syncExecutor;
    // ͬ������ȴ����м�����
    private final Monitor monitor = new Monitor();

    private Logger logger;
    
    private MoCore moCore;

    /**
     * 
     * @param corePoolSize
     *            ͬ������ִ�к����̳߳ش�С
     * @param maximumPoolSize
     *            ͬ������ִ�е�����̸߳���
     */
    public SyncExecutorImpl(int corePoolSize, int maximumPoolSize) {
        syncExecutor = new SyncThreadPoolExecutor(corePoolSize, maximumPoolSize);
    }

    
    public void setLogger(Logger logger){
        this.logger=logger;
        syncExecutor.setLogger(logger);
    }
    
    
    public void setMoCore(MoCore moCore){
        this.moCore=moCore;
        syncExecutor.setMoCore(this.moCore);
    }
    /**
     * �ر���Ԫ��ͬ������
     * 
     * @param ne
     */
    public void shutdown(MoNaming ne) {
        if (ne == null)
            throw new NullPointerException();
        waitingTasks.remove(ne);
        SyncTask syncTask = syncExecutor.getRunningTasks().get(ne);
        if (syncTask != null)
            syncExecutor.shutdown(ne);
        logger.debug("shutdown " + ne + " syncTasks");
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.yotc.opview.sync.execute.SyncExecutor#close()
     */
    @Override
    public synchronized void close() {
        // �������ȴ�����
        waitingTasks.clear();
        // �жϼ����߳�
        monitor.interrupt();
        // �ر�ͬ�������̳߳�
        syncExecutor.shutdownNow();
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.yotc.opview.sync.execute.SyncExecutor#execute(com.yotc.opview.framework.sync.SyncTask)
     */
    @Override
    public synchronized void execute(MoNaming target, SyncTask syncTask) {
        if (!waitingTasks.containsKey(target))
            waitingTasks.put(target, Collections.synchronizedList(new ArrayList<SyncTask>()));
        waitingTasks.get(target).add(syncTask);
        logger.debug("sync task" + syncTask.getTaskId() + "is inserted to waiting queue");
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.yotc.opview.sync.execute.SyncExecutor#getAllSyncTasks()
     */
    @Override
    public synchronized List<SyncTask> getAllSyncTasks() {
        List<SyncTask> syncTasks = new ArrayList<SyncTask>();
        // �ȴ�ִ�е�ͬ������
        for (MoNaming ne : waitingTasks.keySet())
            syncTasks.addAll(waitingTasks.get(ne));
        // �̳߳����������е�ͬ������
        syncTasks.addAll(syncExecutor.getRunningTasks().values());
        // �����ܵ����񼯺�
        return syncTasks;
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.synccore.execute.SyncExecutor#getAllSyncTargets()
     */
    @Override
    public List<MoNaming> getAllSyncTargets() {
        List<MoNaming> allRunningNe = new ArrayList<MoNaming>();
        allRunningNe.addAll(waitingTasks.keySet());
        allRunningNe.addAll(syncExecutor.getRunningTasks().keySet());
        return allRunningNe;
    }
   /**
    * 
    * (non-Javadoc)
    * @see com.yuep.nms.core.server.synccore.execute.SyncExecutor#getRunningSyncTask(com.yuep.nms.core.common.mocore.naming.MoNaming)
    */
   @Override
   public SyncTask getRunningSyncTask(MoNaming ne) {
       return syncExecutor.getRunningTasks().get(ne);
   }
   /**
    * 
    * (non-Javadoc)
    * @see com.yuep.nms.core.server.synccore.execute.SyncExecutor#getSyncTasks(com.yuep.nms.core.common.mocore.naming.MoNaming)
    */
   @Override
   public List<SyncTask> getSyncTasks(MoNaming ne) {
       List<SyncTask> syncTasks=new ArrayList<SyncTask>();
       SyncTask runningSyncTask=syncExecutor.getRunningTasks().get(ne);
       if(runningSyncTask!=null)
           syncTasks.add(runningSyncTask);
       List<SyncTask> waitingSyncTasks=waitingTasks.get(ne);
       if(CollectionUtils.isNotEmpty(waitingSyncTasks))
           syncTasks.addAll(waitingSyncTasks);
       return syncTasks;
   }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.synccore.execute.SyncExecutor#isExisted(com.yuep.nms.core.common.synccore.model.SyncTask)
     */
    @Override
    public boolean isExisted(SyncTask syncTask) {
        List<SyncTask> syncTasks = getAllSyncTasks();
        for (SyncTask runningTask : syncTasks) {
            if ((syncTask.getTarget().isChild(runningTask.getTarget()) || syncTask.getTarget().equals(
                    runningTask.getTarget()))
                    && runningTask.getSyncItems().containsAll(syncTask.getSyncItems())) {
                return true;
            }
        }
        return false;
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.synccore.execute.SyncExecutor#isRunningSyncTasks(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public boolean isRunningSyncTasks(MoNaming ne) {
        return waitingTasks.containsKey(ne) || syncExecutor.getRunningTasks().containsKey(ne);
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.yotc.opview.sync.execute.SyncExecutor#addSyncListener(com.yotc.opview.sync.execute.SyncListener)
     */
    @Override
    public void addSyncListener(SyncListener syncListener) {
        syncExecutor.addSyncListener(syncListener);
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.yotc.opview.sync.execute.SyncExecutor#removeSyncListener(com.yotc.opview.sync.execute.SyncListener)
     */
    @Override
    public void removeSyncListener(SyncListener syncListener) {
        syncExecutor.removeSyncListener(syncListener);
    }
    /**
     * (non-Javadoc)
     * 
     * @see com.yotc.opview.sync.execute.SyncExecutor#shutdown(java.lang.Long)
     */
    @Override
    public synchronized void shutdown(SyncTask syncTask) {
        if (waitingTasks.containsKey(syncTask.getTarget())) {
            waitingTasks.remove(syncTask.getTarget());
        }
        syncExecutor.shutdown(syncTask);
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.yotc.opview.sync.execute.SyncExecutor#start()
     */
    @Override
    public void start() {
        // ����������
        monitor.start();
    }

    /**
     * 
     * <p>
     * Title: Monitor
     * </p>
     * <p>
     * Description: <br>
     * ͬ������ȴ����м����
     * </p>
     * 
     * @author yangtao
 * created 2009-4-14 ����10:44:22
     * modified [who date description]
     * check [who date description]
     */
    private class Monitor extends Thread {

        private volatile boolean stop = false;

        public void run() {
            while (!stop) {
                for (MoNaming ne : waitingTasks.keySet()) {
                    if (syncExecutor.canExecuted(ne)) {
                        List<SyncTask> syncTasks = waitingTasks.get(ne);
                        if (syncTasks.size() > 0) {
                            syncExecutor.execute(syncTasks.get(0));
                            syncTasks.remove(syncTasks.get(0));
                            if (syncTasks.size() == 0)
                                waitingTasks.remove(ne);
                        }
                    }
                }
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    // ���»ָ��жϱ��
                    interrupt();
                    return;
                }

            }
        }

        /**
         * 
         * (non-Javadoc)
         * 
         * @see java.lang.Thread#interrupt()
         */
        public void interrupt() {
            super.interrupt();
            stop = true;
        }

    }


}
