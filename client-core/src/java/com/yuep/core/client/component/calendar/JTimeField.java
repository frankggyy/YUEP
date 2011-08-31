/*
 * $Id: JTimeField.java, 2009-6-9 下午03:45:41 Administrator Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.calendar;

import java.awt.Dimension;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.yuep.core.client.ClientCoreContext;

/**
 * <p>
 * Title: JTimeField
 * </p>
 * <p>
 * Description:时间输入控件，精确到秒
 * </p>
 * 
 * @author Yao
 * created 2009-6-9 下午03:45:41
 * modified [who date description]
 * check [who date description]
 */
public class JTimeField extends JPanel {
    private static final long serialVersionUID = -3451562658832698969L;

    private Boolean isStart = null;

    private JTimeField pairField = null;

    /**
     * Description of the field
     */
    SpinnerDateModel startSpnModel = new SpinnerDateModel();

    /**
     * Description of the field
     */
    JSpinner startSpinner = new JSpinner(startSpnModel);

    /**
     * Description of the field
     */
    final static String dateFormatPattern = "HH:mm:ss";

    private transient ChangeListener changeListener = null;

    /**
     * Description of the field
     */
    DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM);

    public JTimeField() {
        jbInit();
    }

    public void setPairField(Boolean isStart, JTimeField pairField) {
        this.isStart = isStart;
        this.pairField = pairField;
        if (isStart == null || pairField == null)
            return;
        initListener();
        startSpnModel.addChangeListener(changeListener);
    }

    private void initListener() {
        changeListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (isStart == null || pairField == null)
                    return;
                int time = getTime();
                int pairtime = pairField.getTime();
                if (isStart) {
                    if (time > pairtime)
                        setTime(pairtime);
                } else {
                    if (time < pairtime)
                        setTime(pairtime);
                }
            }
        };
    }

    /**
     * 
     */
    private void jbInit() {
        this.setLayout(ClientCoreContext.getSwingFactory().getBorderLayout());
        startSpnModel.setCalendarField(java.util.Calendar.SECOND);
        startSpinner.setPreferredSize(new Dimension(300, 22));
        JSpinner.DateEditor startEditor = new JSpinner.DateEditor(startSpinner, dateFormatPattern);
        startSpinner.setEditor(startEditor);
        setTime(0);
        this.add(startSpinner, null);
    }

    /**
     * Sets the Date attribute of the JCalendar object
     * 
     * @param date
     *            The new Date value
     */
    public void setTime(int seconds) {
        startSpinner.getModel().setValue(convertSecond2Time(seconds));
    }

    public int getTime() {
        Date date = (Date) startSpinner.getModel().getValue();
        return date.getHours() * 3600 + date.getMinutes() * 60 + date.getSeconds();
    }

    private Date convertSecond2Time(int seconds) {
        int hour = (int) (seconds / 3600);
        int minute = (int) ((seconds - hour * 3600) / 60);
        int second = seconds - hour * 3600 - minute * 60;
        return new Date(0, 0, 0, hour, minute, second);
    }

    /**
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#setEnabled(boolean)
     */
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        startSpinner.setEnabled(enabled);
    }

}
