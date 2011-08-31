/*
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co.,Ltd
 * All rights reserved. 
 *
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
/*
 * $Id: JCalendar.java,v 1.4 2008/09/09 08:29:30 liukai Exp $
 */
package com.yuep.core.client.component.calendar;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This class is a Calendar, it show in a Dialog. You can show this Calendar, and select Date/Time.
 * 日历控件
 * @author
 * created 2004年4月1日
 * modified [who date description]
 * check [who date description]
 */
public class JCalendar extends JPanel {
    private static final long serialVersionUID = 8295525052194256328L;

    private final static String BUTTON_TEXT = "...";

    private JCalendarInnerDlg dlg;

    private Window parent;

    /**
     * user selected time value.
     */
    private Date selectedDate;

    private boolean isFormatterData = false;

    public JTextField txtDate = new JTextField();

    // i18n resource bundle handle.
    // DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM);
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private SimpleDateFormat dataFormatter = new SimpleDateFormat("yyyy-MM-dd");

    public JButton btnShowCalendar = new JButton(BUTTON_TEXT);

    /**
     * 构造函数
     */
    public JCalendar() {
        super();
        this.parent = null;
        init();
    }

    /**
     * 构造函数
     * 
     * @param parent
     *            parent
     */
    public JCalendar(Dialog parent) {
        super();
        this.parent = parent;
        init();
    }

    /**
     * 构造函数
     * 
     * @param parent
     *            parent
     */
    public JCalendar(Frame parent) {
        super();
        this.parent = parent;
        init();
    }

    /**
     * 构造函数
     * 
     * @param parent
     *            parent
     */
    public JCalendar(Window parent) {
        super();
        this.parent = parent;
        init();
    }

    /**
     * 设置使能状态
     * @param isEnable
     *            true or false
     */
    public void setEnabled(boolean isEnable) {
        this.txtDate.setEnabled(isEnable);
        this.btnShowCalendar.setEnabled(isEnable);
    }

    /**
     * Gets the user selected Date. If user cancel select, return null.
     * 
     * @return The Date value
     */
    public Date getDate() {
        return selectedDate;
    }

    /**
     * 获取时间
     * @return
     */
    public Long getTime() {
        return getDate() == null ? null : getDate().getTime();
    }

    /**
     * Gets the Text attribute of the JCalendar object
     * 
     * @return The Text value
     */
    public String getText() {
        return this.txtDate.getText();
    }

    /**
     * 设置文件
     * @param txt
     *            string
     */
    public void setText(String txt) {
        this.txtDate.setText(txt);
    }

    /**
     * Sets the Date attribute of the JCalendar object
     * 
     * @param date
     *            The new Date value
     */
    public void setDate(Date date) {
        selectedDate = date;
        if (selectedDate != null) {
            if (isFormatterData)
                this.txtDate.setText(dataFormatter.format(selectedDate));
            else
                this.txtDate.setText(formatter.format(selectedDate));
        } else {
            this.txtDate.setText("");
        }
    }
    
    
    public void setTime(Long time){
        if(time==null)
            this.clearTime();
        else
            this.setDate(new Date(time));
    }

    private void init() {
        this.setLayout(new BorderLayout());
        this.add(txtDate, BorderLayout.CENTER);
        this.add(btnShowCalendar, BorderLayout.EAST);
        // the date text field can not editable.
        txtDate.setEditable(false);
        btnShowCalendar.setMargin(new Insets(0, 2, 0, 2));
        btnShowCalendar.setPreferredSize(new Dimension(35, 20));
        // btnShowCalendar.setToolTipText(ClientContext.getResourceFactory().getString("ShowCalendarToolTip"));
        btnShowCalendar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (JCalendar.this.parent == null) {
                    JCalendar.this.parent = getTopWindow(JCalendar.this);
                }
                if (JCalendar.this.parent instanceof java.awt.Frame) {
                    dlg = new JCalendarInnerDlg((java.awt.Frame) JCalendar.this.parent, JCalendar.this);
                } else {
                    dlg = new JCalendarInnerDlg((java.awt.Dialog) JCalendar.this.parent, JCalendar.this);
                }
                if (isFormatterData)
                    dlg.setTimeVisable(false);
                dlg.showMe();
            }
        });

        // When first show JCalendar component, set it's text to today.
        Calendar tempCalendar = Calendar.getInstance();

        tempCalendar.set(Calendar.HOUR_OF_DAY, 0);
        tempCalendar.set(Calendar.MINUTE, 0);
        tempCalendar.set(Calendar.SECOND, 0);

        // txtDate.setText(format.format(tempCalendar.getTime()));
        // this.selectedDate = tempCalendar.getTime();
        // by default, we set no time selected.
        txtDate.setText("");
        this.selectedDate = null;
    }

    public static Window getTopWindow(Component component) {
        if (isWindow(component))
            return (Window) component;

        Component parent = component.getParent();
        while (parent != null && !isWindow(parent)) {
            parent = parent.getParent();
        }

        // while( (parent = parent.getParent()) != null && !isWindow(parent) );
        if (parent != null)
            return (Window) parent;
        else
            return null;
    }

    private static boolean isWindow(Component component) {
        if ((component instanceof Window))
            return true;
        else
            return false;
    }

    /**
     * this method will callbacked by the dialog when user select new date. In this method, JCalendar will
     * receive the new result date value and save it. If the new date is null, means user cancel the select
     * dialog.
     * 
     * @param selectedDate
     *            date user has selected.
     */

    void informResult(Date selectedDate) {
        JCalendar.this.selectedDate = selectedDate;
        // if user select new date, then display it in txt component.
        if (JCalendar.this.selectedDate != null) {
            // SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd
            // kk:mm:ss");
            if (isFormatterData)
                txtDate.setText(dataFormatter.format(selectedDate));
            else
                txtDate.setText(formatter.format(selectedDate));

            txtDate.updateUI();

        }
    }

    /**
     * clear time
     */
    public void clearTime() {
        txtDate.setText("");
        this.selectedDate = null;
    }

    /**
     * 是否已经设置了日期
     * 
     * @return
     */
    public boolean hasSetData() {
        return (selectedDate != null && (!"".equals(txtDate.getText().trim())));
    }

    public JButton getCalendarButton() {
        return btnShowCalendar;
    }

    /**
     * @param isFormatterData
     *            the isFormatterData to set
     */
    public void setFormatterData(boolean isFormatterData) {
        this.isFormatterData = isFormatterData;
        // dlg.setTimeVisable(false);

    }

    /**
     * @return the isFormatterData
     */
    public boolean isFormatterData() {
        return isFormatterData;
    }
}
