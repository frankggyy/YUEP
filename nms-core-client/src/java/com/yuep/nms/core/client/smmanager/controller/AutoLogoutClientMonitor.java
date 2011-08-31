/*
 * $Id: AutoLogoutClientMonitor.java, 2011-4-25 ����11:51:21 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.smmanager.controller;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import com.yuep.base.log.def.Logger;
import com.yuep.core.bootstrap.def.ModuleContext;
import com.yuep.nms.core.client.smmanager.module.SmManagerClientModule;

/**
 * <p>
 * Title: AutoLogoutClientMonitor
 * </p>
 * <p>
 * Description:�Զ�ע��
 * </p>
 * 
 * @author sufeng
 * created 2011-4-25 ����11:51:21
 * modified [who date description]
 * check [who date description]
 */
public class AutoLogoutClientMonitor {

    private Logger logger;
    
    /**
     * ���ʱ�䣬��λ�룬���С�ڵ���0���򲻼�ء�
     */
    protected int intervalTime;

    /**
     * ��ʱ�����ʱ�䣬10����һ��
     */
    protected final int checkInteval = 10 * 1000;

    /**
     * ���һ�β���ʱ���
     */
    protected long lastOperTimeStamp;

    /**
     * ��ʱ������
     */
    protected Timer timer;

    /**
     * ��ǰ�Ƿ���м���
     */
    protected boolean isMonitor = false;

    /**
     * �������࣬����ָ��ʱ���ڣ��û�û�в�������ص��ýӿ�
     */
    protected ActionListener listener;

    /**
     * �û�����ʱ�������
     */
    private long eventMask = AWTEvent.KEY_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK;

    protected AWTEventListener eventListener;

    /**
     * ���캯��
     * 
     * @param intervalTime
     *            ���ʱ�䣬��λ�룬���С�ڵ���0���򲻼�ء�
     * @param lst
     *            ������
     */
    public AutoLogoutClientMonitor(int intervalTime, ActionListener lst) {
        SmManagerClientModule module = ModuleContext.getInstance().getModule(SmManagerClientModule.class);
        logger=module.getLogger();
        
        // �жϼ������Ƿ�δnull
        if (lst == null) 
            return;

        // ��¼���ʱ��
        this.intervalTime = intervalTime;
        listener = lst;
    }

    protected void initialize() {
        if (eventListener == null) {
            // �����¼�������
            eventListener = new AWTEventListener() {
                public void eventDispatched(AWTEvent event) {
                    lastOperTimeStamp = System.currentTimeMillis();
                }
            };
        }

        if (timer == null) {
            // ���춨ʱ��
            timer = new Timer("Monitor LockScreen", true);
            TimerTask task = new TimerTask() {
                public void run() {
                    if (isMonitor) {
                        if (logger.isDebugEnabled()) {
                            //logger.debug("monitor.... lastOperTimeStamp: " + DateUtil.getLongDate(lastOperTimeStamp)
                            //        + "\t intervalTime: " + intervalTime);
                        }
                        long currentTimeStamp = System.currentTimeMillis();
                        //if (logger.isDebugEnabled())
                           // logger.debug("currentTime: " + DateUtil.getLongDate(currentTimeStamp));

                        if ((currentTimeStamp - lastOperTimeStamp) > intervalTime * 1000) {
                            if (logger.isDebugEnabled())
                                logger.debug("call listener ....");
                            if (listener != null) {
                                ActionEvent eventLock = new ActionEvent("Monitor LockScreen", 0,
                                        "lock_system_command");
                                if (logger.isDebugEnabled())
                                    logger.debug("call listener.actionPermformed");
                                listener.actionPerformed(eventLock);
                            }
                        }
                    }
                }
            };
            timer.scheduleAtFixedRate(task, 0, checkInteval);
        }
    }
    
    /**
     * ��������
     */
    public void start() {
        // ������ʱ��С�ڵ���0���򷵻�
        if (intervalTime <= 0) {
            return;
        }

        lastOperTimeStamp = System.currentTimeMillis();
        
        // ��ʼ��
        initialize();

        isMonitor = true;
        Toolkit.getDefaultToolkit().addAWTEventListener(eventListener, eventMask);
    }

    /**
     * ֹͣ����
     */
    public void pause() {
        if (isMonitor) {
            isMonitor = false;
            Toolkit.getDefaultToolkit().removeAWTEventListener(eventListener);
        }
    }

    public void cancel() {
        if (timer != null) {
            timer.cancel();
        }
    }

    /**
     * �ͻ����Զ�����ʱ����
     * 
     * @param intervalTime
     *            ���ʱ�䣬��λ�룬���С�ڵ���0���򲻼�ء�
     */
    public void setIntervalTime(int intervalTime) {
        this.intervalTime = intervalTime;
    }
    
}
