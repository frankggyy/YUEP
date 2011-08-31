/*
 * $Id: AutoLogoutClientMonitor.java, 2011-4-25 上午11:51:21 sufeng Exp $
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
 * Description:自动注销
 * </p>
 * 
 * @author sufeng
 * created 2011-4-25 上午11:51:21
 * modified [who date description]
 * check [who date description]
 */
public class AutoLogoutClientMonitor {

    private Logger logger;
    
    /**
     * 间隔时间，单位秒，如果小于等于0，则不监控。
     */
    protected int intervalTime;

    /**
     * 定时器间隔时间，10秒检查一次
     */
    protected final int checkInteval = 10 * 1000;

    /**
     * 最后一次操作时间戳
     */
    protected long lastOperTimeStamp;

    /**
     * 定时器任务
     */
    protected Timer timer;

    /**
     * 当前是否进行检视
     */
    protected boolean isMonitor = false;

    /**
     * 监听器类，当在指定时间内，用户没有操作，则回调该接口
     */
    protected ActionListener listener;

    /**
     * 用户操作时间过滤器
     */
    private long eventMask = AWTEvent.KEY_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK;

    protected AWTEventListener eventListener;

    /**
     * 构造函数
     * 
     * @param intervalTime
     *            间隔时间，单位秒，如果小于等于0，则不监控。
     * @param lst
     *            监听器
     */
    public AutoLogoutClientMonitor(int intervalTime, ActionListener lst) {
        SmManagerClientModule module = ModuleContext.getInstance().getModule(SmManagerClientModule.class);
        logger=module.getLogger();
        
        // 判断监听器是否未null
        if (lst == null) 
            return;

        // 记录间隔时间
        this.intervalTime = intervalTime;
        listener = lst;
    }

    protected void initialize() {
        if (eventListener == null) {
            // 构造事件监听器
            eventListener = new AWTEventListener() {
                public void eventDispatched(AWTEvent event) {
                    lastOperTimeStamp = System.currentTimeMillis();
                }
            };
        }

        if (timer == null) {
            // 构造定时器
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
     * 启动监听
     */
    public void start() {
        // 如果间隔时间小于等于0，则返回
        if (intervalTime <= 0) {
            return;
        }

        lastOperTimeStamp = System.currentTimeMillis();
        
        // 初始化
        initialize();

        isMonitor = true;
        Toolkit.getDefaultToolkit().addAWTEventListener(eventListener, eventMask);
    }

    /**
     * 停止监听
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
     * 客户端自动操作时间间隔
     * 
     * @param intervalTime
     *            间隔时间，单位秒，如果小于等于0，则不监控。
     */
    public void setIntervalTime(int intervalTime) {
        this.intervalTime = intervalTime;
    }
    
}
