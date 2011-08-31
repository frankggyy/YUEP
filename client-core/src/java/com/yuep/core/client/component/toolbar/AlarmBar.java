/*
 * $Id: AlarmBar.java, 2009-2-18 下午02:18:27 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.toolbar;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

import twaver.AlarmSeverity;

/**
 * <p>
 * Title: AlarmBar
 * </p>
 * <p>
 * Description:告警栏
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-18 下午02:18:27
 * modified [who date description]
 * check [who date description]
 */
public class AlarmBar extends JPanel implements AlarmBoardSelectedListener {

    private static final long serialVersionUID = 840567632622584988L;
 
    /**
     * 紧急告警灯
     */
    private BasicAlarmBoard criticalBoard;
    /**
     * 重要告警灯
     */
    private BasicAlarmBoard majorBoard;
    /**
     * 一般告警灯
     */
    private BasicAlarmBoard minorBoard;
    /**
     * 警告告警灯
     */
    private BasicAlarmBoard warningBoard;

    /**
     * Constructor
     */
    public AlarmBar() {
        criticalBoard = new BasicAlarmBoard(AlarmSeverity.CRITICAL);
        criticalBoard.addAlarmBoardSelectedListener(this);
        majorBoard = new BasicAlarmBoard(AlarmSeverity.MAJOR);
        majorBoard.addAlarmBoardSelectedListener(this);
        minorBoard = new BasicAlarmBoard(AlarmSeverity.MINOR);
        minorBoard.addAlarmBoardSelectedListener(this);
        warningBoard = new BasicAlarmBoard(AlarmSeverity.WARNING);
        warningBoard.addAlarmBoardSelectedListener(this);
        constructUi();
    }

    /**
     * Construct GUI
     */
    private void constructUi() {
        GridLayout mgr = new GridLayout(1, 4);
        mgr.setVgap(0);
        setLayout(mgr);
        setPreferredSize(new Dimension(140, 24));
        add(criticalBoard);
        add(majorBoard);
        add(minorBoard);
        add(warningBoard);
        updateColor();
    }

    /**
     * Alarm board blink method.
     * 
     * @param level
     */
    public void blink(AlarmSeverity level) {

        controlBlink(level, true);
    }

    private void controlBlink(AlarmSeverity level, boolean started) {

        if (AlarmSeverity.CRITICAL.equals(level)) {
            if (started) {
                criticalBoard.blink();
            } else {
                criticalBoard.stopBlink();
            }
        } else if (AlarmSeverity.MAJOR.equals(level)) {
            if (started) {
                majorBoard.blink();
            } else {
                majorBoard.stopBlink();
            }
        } else if (AlarmSeverity.MINOR.equals(level)) {
            if (started) {
                minorBoard.blink();
            } else {
                minorBoard.stopBlink();
            }
        } else if (AlarmSeverity.WARNING.equals(level)) {
            if (started) {
                warningBoard.blink();
            } else {
                warningBoard.stopBlink();
            }
        }
    }

    /**
     * Alarm board blink method.
     * 
     * @param level
     */
    public void stopBlink(AlarmSeverity level) {

        controlBlink(level, false);
    }

    /**
     * 设置告警板颜色
     * 
     * @param list
     */
    public void updateColor() {

        // Critical
        criticalBoard.setColor(twaver.AlarmSeverity.CRITICAL.getColor());
        // Major
        majorBoard.setColor(twaver.AlarmSeverity.MAJOR.getColor());
        // Minor
        minorBoard.setColor(twaver.AlarmSeverity.MINOR.getColor());
        // Warning
        warningBoard.setColor(twaver.AlarmSeverity.WARNING.getColor());
    }

    /**
     * 
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.toolbar.AlarmBoardSelectedListener#selected(com.yotc.opview.framework.bo.fm.enums.AlarmSeverity)
     */
    @Override
    public void selected(AlarmSeverity alarmSeverity) {

    }
}
