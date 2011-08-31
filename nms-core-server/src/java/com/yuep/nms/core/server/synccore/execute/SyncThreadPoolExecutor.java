/*
 * $Id: SyncThreadPoolExecutor.java, 2009-4-15 ����09:52:03 yangtao Exp $
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.yuep.base.log.def.Logger;
import com.yuep.core.container.def.CoreContext;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.mocore.service.MoCore;
import com.yuep.nms.core.common.synccore.model.SyncItem;
import com.yuep.nms.core.common.synccore.model.SyncResult;
import com.yuep.nms.core.common.synccore.model.SyncTask;
import com.yuep.nms.core.common.synccore.model.enums.Result;

/**
 * <p>
 * Title: SyncThreadPoolExecutor
 * </p>
 * <p>
 * Description: ͬ������ִ���̳߳�
 * </p>
 * 
 * @author yangtao
 * created 2009-4-15 ����09:52:03
 * modified [who date description]
 * check [who date description]
 */
public class SyncThreadPoolExecutor extends ThreadPoolExecutor {
    // ͬ�����񣬶���ͬһ����Ԫֻ����һ��ͬ��������ִ��
    private final ConcurrentMap<MoNaming, SyncRunnable> runningTasks = new ConcurrentHashMap<MoNaming, SyncRunnable>();
    // ͬ�����̼����ӿ�
    private final List<SyncListener> syncListeners = new CopyOnWriteArrayList<SyncListener>();

    private MoCore moCore;
    private Logger logger;
    
    public SyncThreadPoolExecutor(int corePoolSize, int maximumPoolSize) {
        super(corePoolSize, maximumPoolSize, 3, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    }

    public void setMoCore(MoCore moCore){
        this.moCore=moCore;
    }
    
    public void setLogger(Logger logger){
        this.logger=logger;
    }
    /**
     * ���ͬ��������
     * 
     * @param syncListener
     */
    public void addSyncListener(SyncListener syncListener) {
        syncListeners.add(syncListener);
    }

    /**
     * �Ƴ�ͬ��������
     * 
     * @param syncListener
     */
    public void removeSyncListener(SyncListener syncListener) {
        syncListeners.remove(syncListener);
    }

    /**
     * ������Ԫne��ͬ�������Ƿ��ܹ���ִ�У��ж���agent����Ԫ��
     * 
     * @param ne
     * @return
     */
    public boolean canExecuted(MoNaming ne) {
        return !runningTasks.containsKey(ne);
    }

    /**
     * ��ȡ�����������е�ͬ������
     * 
     * @return
     */
    public Map<MoNaming, SyncTask> getRunningTasks() {
        Map<MoNaming, SyncTask> syncTasks = new HashMap<MoNaming, SyncTask>();
        for (MoNaming ne : runningTasks.keySet()) {
            syncTasks.put(ne, runningTasks.get(ne).getSyncTask());
        }
        return syncTasks;
    }

    /**
     * (non-Javadoc)
     * 
     * @see java.util.concurrent.ThreadPoolExecutor#beforeExecute(java.lang.Thread, java.lang.Runnable)
     */
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
    }

    /**
     * ִ��ͬ������
     * 
     * @param ne
     *            ��ԪMoNaming
     * @param syncTask
     *            ͬ������,���ͬ������ִ�У�����ͬ������������б�ִ��(�����������ڵ���Ԫ��ͬ��������ִ��)������null
     */
    public SyncTask execute(SyncTask syncTask) {
        if (this.isTerminated())
            throw new IllegalStateException();
        if (!runningTasks.containsKey(syncTask.getTarget())) {
            SyncRunnable syncRunnable = new SyncRunnable(syncTask);
            runningTasks.put(syncTask.getTarget(), syncRunnable);
            execute(syncRunnable);
            return syncRunnable.getSyncTask();
        } else {
            throw new IllegalStateException();
        }
    }

    /**
     * 
     * (non-Javadoc)
     * 
     * @see java.util.concurrent.ThreadPoolExecutor#shutdownNow()
     */
    public List<Runnable> shutdownNow() {
        runningTasks.clear();
        return super.shutdownNow();
    }

    /**
     * �ر�ͬ������
     * 
     * @param syncTask
     */
    public void shutdown(SyncTask syncTask) {
        if (syncTask == null)
            throw new NullPointerException();
        SyncRunnable syncRunnable = runningTasks.get(syncTask.getTarget());
        try {
            if (syncRunnable != null)
                syncRunnable.interrupt();
        } finally {
            runningTasks.remove(syncTask.getTarget());
        }

    }

    /**
     * ɾ����Ԫne��ͬ������
     * 
     * @param ne
     */
    public void shutdown(MoNaming ne) {
        SyncRunnable syncRunnable = runningTasks.get(ne);
        if (syncRunnable != null) {
            try {
                syncRunnable.interrupt();
            } finally {
                runningTasks.remove(ne);
            }
        }

    }

    /**
     * 
     * (non-Javadoc)
     * 
     * @see java.util.concurrent.ThreadPoolExecutor#afterExecute(java.lang.Runnable, java.lang.Throwable)
     */
    protected void afterExecute(Runnable r, Throwable th) {
        try {
            super.afterExecute(r, th);
        } finally {
            if (r instanceof SyncRunnable) {
                SyncRunnable sync = (SyncRunnable) r;
                removeSyncTask(sync.getSyncTask());
            }
        }

    }

    /**
     * ɾ��ͬ������
     * 
     * @param syncTask
     */
    private void removeSyncTask(SyncTask syncTask) {
        if (!runningTasks.containsKey(syncTask.getTarget()))
            return;
        runningTasks.remove(syncTask.getTarget());
    }

    /**
     * 
     * <p>
     * Title: SyncTaskCallable
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @author yangtao
 * created 2009-3-20 ����09:15:00
     * modified [who date description]
     * check [who date description]
     */
    private class SyncRunnable implements Runnable {

        private final SyncTask syncTask;

        private volatile boolean isOver = false;

        private volatile boolean stoped = false;

        private final Object lock = new Object();

        public SyncRunnable(SyncTask syncTask) {
            this.syncTask = syncTask;
        }

        public SyncTask getSyncTask() {
            return syncTask;
        }

        /**
         * �ж�����ִ�е�ͬ������
         */
        public void interrupt() {
            synchronized (lock) {
                stoped = true;
                while (!isOver) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
            
        }

        /**
         * 
         * (non-Javadoc)
         * 
         * @see java.lang.Runnable#run()
         */
        public void run() {
            logger.info("start SyncTask=" + moCore.getMo(syncTask.getTarget()));

            List<SyncJob> syncJobs = new ArrayList<SyncJob>();

            for (SyncItem syncItem : syncTask.getSyncItems()) {
                SyncJob syncJob = new SyncJob(syncTask,syncItem, syncListeners);
                syncJob.setMoCore(moCore);
                syncJob.setLogger(logger);
                syncJob.setCoreContext(CoreContext.getInstance());
                syncJobs.add(syncJob);
            }

            for (SyncListener syncListener : syncListeners) {// ����ͬ������ʼ֪ͨ
                syncListener.startSyncTask(syncTask);
            }
            try {
                int i = 0;
                for (SyncJob syncJob : syncJobs) {
                    SyncResult syncResult;
                    try {
                        syncResult = syncJob.call();
                        if (syncResult != null && syncResult.getResult()==Result.failure&&syncResult.getSyncItem().isRequested()) { // ͬ������������,��ֹͬ������
                            isOver = true;// ͬ���������
                            break;
                        }
                    } catch (Throwable th) {//�������ɴ�����쳣
                        logger.error("", th);
                        isOver = true;// ͬ���������
                        break;
                    } finally {
                        synchronized (lock) {
                            ++i;
                            if (i == syncJobs.size() || stoped || isOver) {
                                isOver = true;
                                if(stoped){//���û��رյ�ͬ������
                                    for (SyncListener syncListener : syncListeners) {// ����ͬ���������֪ͨ
                                        syncListener.shutdownSyncTask(syncTask);
                                    }
                                }
                                lock.notifyAll();
                                break;
                            }
                        }
                    }
                }
            } finally {
                for (SyncListener syncListener : syncListeners) {// ����ͬ���������֪ͨ
                    syncListener.endSyncTask(syncTask);
                }
            }
            logger.info("end SyncTask=" + moCore.getMo(syncTask.getTarget()).getDisplayName());
        }
    }

}
