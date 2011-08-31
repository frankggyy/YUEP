/*
 * $Id: BasicAlarmBoard.java, 2009-2-18 下午03:30:59 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.toolbar;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.AbstractAction;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import twaver.AlarmSeverity;

import com.yuep.base.scheduler.FixedIntervalSchedulerJob;
import com.yuep.base.scheduler.SchedulerExecutor;
import com.yuep.base.scheduler.SchedulerExecutorFactory;
import com.yuep.base.scheduler.SchedulerExecutorFactoryImpl;
import com.yuep.core.client.ClientCoreContext;

/**
 * <p>
 * Title: BasicAlarmBoard
 * </p>
 * <p>
 * Description:告警灯
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-18 下午03:30:59
 * modified [who date description]
 * check [who date description]
 */
public class BasicAlarmBoard extends JLabel {

    private static final long serialVersionUID = 522154065439443367L;

    /**
     * 背景颜色
     */
    private Color backgroudColor;

    /**
     * 当前颜色
     */
    private volatile Color currentColor;

    /**
     * 告警级别
     */
    private final AlarmSeverity severity;
    // 每一秒闪烁一次
    private final BlinkJob blinkJob = new BlinkJob(2);

    /**
     * 告警等选择事件监听者
     */
    private final List<AlarmBoardSelectedListener> alarmBoardSelectedListeners = new CopyOnWriteArrayList<AlarmBoardSelectedListener>();

    /**
     * 停止所有告警灯闪烁
     */
    private final StopAllAction stopAllAction = new StopAllAction(ClientCoreContext.getString("alarm.stop"));

    /**
     * 开始所有告警灯闪烁
     */
    private final FlashAllAction flashAllAction = new FlashAllAction(ClientCoreContext.getString("alarm.start"));

    /**
     * 是否正在闪烁
     */
    private volatile boolean flashFlag = true;
    
    private transient SchedulerExecutorFactory schedulerExecutorFactory=new SchedulerExecutorFactoryImpl();

    /**
     * 根据告警级别构造告警灯
     * 
     * @param severity
     */
    public BasicAlarmBoard(final AlarmSeverity severity) {
        FlowLayout flowLayout = ClientCoreContext.getSwingFactory().getFlowLayout(SwingConstants.CENTER);
        flowLayout.setVgap(0);
        setLayout(flowLayout);
        this.severity = severity;
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    getPopupMenu().show(e.getComponent(), e.getX(), e.getY());
                } else if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() >= 2) {
                    for (AlarmBoardSelectedListener alarmBoardSelectedListener : alarmBoardSelectedListeners) {
                        alarmBoardSelectedListener.selected(BasicAlarmBoard.this.severity);
                    }
                    blinkJob.close();
                }
            }
        });
        if (severity == AlarmSeverity.CRITICAL)
            this.setToolTipText(ClientCoreContext.getString("alarm.severity.critical"));
        else if (severity == AlarmSeverity.MAJOR)
            this.setToolTipText(ClientCoreContext.getString("alarm.severity.major"));
        else if (severity == AlarmSeverity.MINOR)
            this.setToolTipText(ClientCoreContext.getString("alarm.severity.minor"));
        else if (severity == AlarmSeverity.WARNING)
            this.setToolTipText(ClientCoreContext.getString("alarm.severity.warning"));
    }

    private JPopupMenu getPopupMenu() {
        JPopupMenu menu = new JPopupMenu();
        if (flashFlag)
            menu.add(stopAllAction);
        else
            menu.add(flashAllAction);
        return menu;
    }

    /**
     * @see java.awt.Component#paint(java.awt.Graphics)
     */
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Point2D center = new Point2D.Float(13, 13);
        float radius = 22;
        float[] dist = { .1f, .9f };
        Color[] colors = { currentColor, Color.BLACK };
        RadialGradientPaint p = new RadialGradientPaint(center, radius, dist, colors);
        g2.setPaint(p);
        g2.fillOval(5, 1, 23, 23);
        super.paint(g2);
    }

    /**
     * 获取告警严重程度
     * 
     * @return
     */
    public AlarmSeverity getAlarmSeverity() {
        return severity;
    }

    /**
     * 设置告警颜色
     * 
     * @param color
     */
    public void setColor(Color color) {
        currentColor = color;
        this.backgroudColor = color;
        reset(currentColor);
    }

    /**
     * 添加alarmBoardSelectedListener
     * 
     * @param alarmBoardSelectedListener
     */
    public void addAlarmBoardSelectedListener(AlarmBoardSelectedListener alarmBoardSelectedListener) {
        alarmBoardSelectedListeners.add(alarmBoardSelectedListener);
    }

    /**
     * 删除alarmBoardSelectedListener
     * 
     * @param alarmBoardSelectedListener
     */
    public void removeAlarmBoardSelectedListener(AlarmBoardSelectedListener alarmBoardSelectedListener) {
        alarmBoardSelectedListeners.remove(alarmBoardSelectedListener);
    }

    /**
     * 启动闪烁
     */
    public void blink() {
        if (blinkJob.isClosed())
            blinkJob.start();
    }

    /**
     * 停止闪烁
     */
    public void stopBlink() {
        if (!blinkJob.isClosed()) {
            blinkJob.close();
        }
    }

    /**
     * 重设当前颜色
     * 
     * @param color
     */
    private void reset(Color color) {
        this.currentColor = color;
        repaint();
    }

    /**
     * 
     * <p>
     * Title: BlinkJob
     * </p>
     * <p>
     * Description: 闪烁任务
     * </p>
     * 
     * @author yangtao
 * created 2009-6-6 上午11:19:48
     * modified [who date description]
     * check [who date description]
     */
    private class BlinkJob extends FixedIntervalSchedulerJob {

        private volatile boolean isClosed = true;

        private volatile boolean isPaused = false;

        private SchedulerExecutor schedulerExecutor=schedulerExecutorFactory.createSchedulerExecutor();
        /**
         * 
         * @param interval
         *            闪烁间隔
         */
        public BlinkJob(int interval) {
            super(null, null, interval);
        }

        /**
         * 
         * (non-Javadoc)
         * 
         * @see com.yotc.opview.framework.schedule.SchedulerJob#execute()
         */
        @Override
        public void execute() {
            if (!flashFlag)
                return;
            try {
                Thread.sleep(200);
                reset(Color.WHITE);
                Thread.sleep(200);
            } catch (InterruptedException e) {
            } finally {
                reset(BasicAlarmBoard.this.backgroudColor);
            }
        }

        /**
         * 闪烁任务是否被关闭
         * 
         * @return
         */
        public boolean isClosed() {
            return isClosed;
        }

        /**
         * 启动闪烁任务
         */
        public void start() {
            if (isClosed()) {
                schedulerExecutor.startSchedulerJob(this);
                isClosed = false;
            }
        }

        /**
         * 关闭闪烁任务
         */
        public void close() {
            if (!isClosed()) {
                schedulerExecutor.shutdownSchedulerJob(this.getJobName(), this.getJobGroupName());
                isClosed = true;
            }
        }

        /**
         * 闪烁任务是否被暂停
         * 
         * @return
         */
        public boolean isPaused() {
            return isPaused;
        }
    }

    /**
     * <p>
     * Title: StopAllAction
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @author aaron lee
 * created 2009-2-18 下午03:35:10
     * modified [who date description]
     * check [who date description]
     */
    private class StopAllAction extends AbstractAction {

        /**
         * Comment for <code>serialVersionUID</code>
         */
        private static final long serialVersionUID = 1L;

        public StopAllAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            flashFlag = false;
        }
    }

    /**
     * <p>
     * Title: FlashAllAction
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @author aaron lee
 * created 2009-2-18 下午03:35:26
     * modified [who date description]
     * check [who date description]
     */
    private class FlashAllAction extends AbstractAction {

        /**
         * Comment for <code>serialVersionUID</code>
         */
        private static final long serialVersionUID = 1L;

        public FlashAllAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            flashFlag = true;
        }
    }

}
