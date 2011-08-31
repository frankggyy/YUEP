/*
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co.,Ltd
 * All rights reserved. 
 *
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
/*
 * $Id: JCalendarInnerDlg.java,v 1.3 2008/09/09 08:29:30 liukai Exp $
 */
package com.yuep.core.client.component.calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.SwingFactory;
import com.yuep.core.client.component.factory.decorator.button.ButtonDecorator;

/**
 * This dialog is the Calendar container. It implements WindowFocusListener, and this dialog will as a
 * WindowFocusListener added into the parent window. So when parent window get focus, this dialog will dispose
 * at once.
 * 
 * @author
 * created 2004年4月2日
 * modified [who date description]
 * check [who date description]
 * @trace [Feature Id]
 * @version [1.1.1]
 */
public class JCalendarInnerDlg extends JDialog implements WindowFocusListener {
    private static final long serialVersionUID = -639772987841550664L;

    /**
     * Description of the field
     */
    final static int HEIGHT = 180;

    /**
     * Description of the field
     */
    final static int WIDTH = 265;

    /**
     * Description of the field
     */
    private JButton btnCancel;

    /**
     * Description of the field
     */
    private JButton btnOk;

    /**
     * Description of the field
     */
    private JButton btnReset;

    /**
     * Description of the field
     */
    private JPanel buttonPane;

    /**
     * Description of the field
     */
    private Calendar calendar;

    /**
     * Description of the field
     */
    private JPanel contentPane;

    /**
     * Description of the field
     */
    private JCalenderInnerTableModel daysModel;

    /**
     * Description of the field
     */
    private JCalendarInnerTable daysTable;

    /**
     * Description of the field
     */
    private JCalendar jcalendar;

    /**
     * Description of the field
     */
    private FlowLayout layoutButtonPane;

    /**
     * Description of the field
     */
    private JComboBox monthsComboBox;

    /**
     * Description of the field
     */
    private JLabel monthsLabel;

    /**
     * Description of the field
     */
    private Window parent;

    /**
     * Description of the field
     */
    private JLabel yearsLabel;

    /**
     * Description of the field
     */
    private JLabel timesLabel = new JLabel(ClientCoreContext.getString(
            "common.calendar.time"));

    /**
     * Description of the field
     */
    private JSpinner yearsSpinner;

    /**
     * Description of the field
     */
    private JPanel timePane = new JPanel();

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
    String dateFormatPattern = "HH:mm:ss";

    /**
     * 添加时间控件的显示/隐藏功能
     */
    private boolean isTimeVisable = false;
    /**
     * Description of the field
     */
    DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM);

    // i18n resource bundle handle.

    /**
     * Constructor for the JCalendar object
     * 
     * @param parent
     *            parent Dialog window
     * @param calendar
     *            owner jcalendar component
     */
    public JCalendarInnerDlg(java.awt.Dialog parent, JCalendar calendar) {
        super(parent, false);
        this.parent = parent;
        this.jcalendar = calendar;
        init();
    }

    /**
     * Constructor for the JCalendar object
     * 
     * @param parent
     *            parent Frame window
     * @param calendar
     *            owner jcalendar component
     */
    public JCalendarInnerDlg(java.awt.Frame parent, JCalendar calendar) {
        super(parent, false);
        this.parent = parent;
        this.jcalendar = calendar;
        init();
    }

    /**
     * when cancel button clicked.
     */
    private void cancelPerformed() {
        // if cancel, set calendar null
        this.calendar = null;
        // remove myself from parent.
        parent.removeWindowFocusListener(this);
        // close myself.
        dispose();
    }

    /**
     * when reset button clicked.
     * 
     */

    private void resetPerformed() {
        jcalendar.clearTime();
        dispose();
    }

    /**
     * init my self
     */
    private void init() {
        // add myself as a listener into parent
        this.parent.addWindowFocusListener(this);
        contentPane = (JPanel) getContentPane();
        JSpinner.DateEditor startEditor = new JSpinner.DateEditor(startSpinner, dateFormatPattern);
        startSpinner.setEditor(startEditor);
        // set contentpane's layout and border.
        contentPane.setLayout(new BorderLayout());
        contentPane.setBorder(BorderFactory.createLineBorder(Color.black));
        calendar = Calendar.getInstance();
        // if JCalendar component's current date not set,then use component's
        // date.
        if (this.jcalendar.getDate() == null) {
            calendar.setTime(new Date());
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
        }
        // if JCalendar component's current date is not today, then use
        // component's date.
        else if (this.jcalendar.getDate().compareTo(calendar.getTime()) != 0) {

            calendar.setTime(this.jcalendar.getDate());
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            this.startSpnModel.setValue(this.jcalendar.getDate());
        }
        yearsLabel = new JLabel(ClientCoreContext.getString("common.calendar.year"));
        yearsSpinner = new JSpinner();
        yearsSpinner.setEditor(new JSpinner.NumberEditor(yearsSpinner, "0000"));
        yearsSpinner.setValue(Integer.valueOf(calendar.get(Calendar.YEAR)));
        yearsSpinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.YEAR, ((Integer) yearsSpinner.getValue()).intValue());

                int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

                calendar.set(Calendar.DAY_OF_MONTH, day > maxDay ? maxDay : day);
                updateView();
            }
        });

        JPanel yearMonthPanel = new JPanel();

        contentPane.add(yearMonthPanel, BorderLayout.NORTH);
        yearMonthPanel.setLayout(new BorderLayout());
        timePane.setLayout(new BorderLayout());
        timePane.add(timesLabel, BorderLayout.WEST);
        timePane.add(startSpinner, BorderLayout.CENTER);
        // yearMonthPanel.add(timePane, BorderLayout.CENTER);
        yearMonthPanel.add(timePane, BorderLayout.EAST);

        JPanel yearPanel = new JPanel();

        yearMonthPanel.add(yearPanel, BorderLayout.WEST);
        yearPanel.setLayout(new BorderLayout());
        yearPanel.add(yearsLabel, BorderLayout.WEST);
        yearPanel.add(yearsSpinner, BorderLayout.CENTER);

        monthsLabel = new JLabel(ClientCoreContext.getString("common.calendar.month"));
        monthsComboBox = new JComboBox();
        for (int i = 1; i <= 12; i++) {
            monthsComboBox.addItem(Integer.valueOf(i));
        }
        monthsComboBox.setSelectedIndex(calendar.get(Calendar.MONTH));
        monthsComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.MONTH, monthsComboBox.getSelectedIndex());

                int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

                calendar.set(Calendar.DAY_OF_MONTH, day > maxDay ? maxDay : day);
                updateView();
            }
        });

        JPanel monthPanel = new JPanel();

        // yearMonthPanel.add(monthPanel, BorderLayout.EAST);
        yearMonthPanel.add(monthPanel, BorderLayout.CENTER);
        monthPanel.setLayout(new BorderLayout());
        monthPanel.add(monthsLabel, BorderLayout.WEST);
        monthPanel.add(monthsComboBox, BorderLayout.CENTER);
        daysModel = new JCalenderInnerTableModel(calendar);
        daysTable = new JCalendarInnerTable(daysModel, calendar);
        updateView();
        contentPane.add(daysTable, BorderLayout.CENTER);
        buttonPane = new JPanel();
        layoutButtonPane = new FlowLayout();
        layoutButtonPane.setAlignment(FlowLayout.RIGHT);
        buttonPane.setLayout(layoutButtonPane);
        SwingFactory swingFactory = ClientCoreContext.getSwingFactory();
        btnOk = swingFactory.getButton(new ButtonDecorator("common.calendar.okbutton",'o'));
        btnOk.setPreferredSize(new Dimension(65, 20));
        btnOk.setMargin(new Insets(0, 0, 0, 0));
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                okPerformed();
            }
        });
        btnCancel = swingFactory.getButton(new ButtonDecorator("common.calendar.cancelbutton",'c'));
        btnCancel.setPreferredSize(new Dimension(65, 20));
        btnCancel.setMargin(new Insets(0, 0, 0, 0));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelPerformed();
            }
        });
        btnReset = swingFactory.getButton(new ButtonDecorator("common.calendar.clearbutton",'l'));
        // btnReset.setMnemonic('l');
        btnReset.setPreferredSize(new Dimension(65, 20));
        btnReset.setMargin(new Insets(0, 0, 0, 0));
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetPerformed();
            }
        });

        buttonPane.add(btnOk);
        buttonPane.add(btnCancel);
        buttonPane.add(btnReset);
        contentPane.add(buttonPane, BorderLayout.SOUTH);
        setUndecorated(true);
        setSize(WIDTH, HEIGHT);
        setAlwaysOnTop(true);
    }

    /**
     * when ok button clicked, this mothed will invoked
     */
    private void okPerformed() {
        Date result = null;

        // return user selected time
        if (calendar != null) {
            // set time to 0, only return the Date value.
            Date first = calendar.getTime();

            Date second = (Date) this.startSpinner.getModel().getValue();
            if (!isTimeVisable)
                result = new Date(first.getYear(), first.getMonth(), first.getDate(), second.getHours(),
                        second.getMinutes(), second.getSeconds());
            else
                result = new Date(first.getYear(), first.getMonth(), first.getDate(), 0, 0, 0);
            // result = this.calendar.getTime();

        } else {
            result = null;
        }
        // inform JCalendar the result date.
        jcalendar.informResult(result);
        dispose();
    }

    /**
     * update the table view
     */
    private void updateView() {
        daysModel.fireTableDataChanged();
        daysTable.setRowSelectionInterval(calendar.get(Calendar.WEEK_OF_MONTH), calendar
                .get(Calendar.WEEK_OF_MONTH));
        daysTable.setColumnSelectionInterval(calendar.get(Calendar.DAY_OF_WEEK) - 1, calendar
                .get(Calendar.DAY_OF_WEEK) - 1);
    }

    /**
     * show dialog.
     */
    public void showMe() {
        // int x = (int) jcalendar.btnShowCalendar.getLocationOnScreen().getX()
        // + jcalendar.btnShowCalendar.getWidth();
        // int y = (int) jcalendar.btnShowCalendar.getLocationOnScreen().getY()
        // + jcalendar.btnShowCalendar.getHeight();
        int x = (int) jcalendar.txtDate.getLocationOnScreen().getX();
        // + jcalendar.btnShowCalendar.getWidth();
        int y = (int) jcalendar.btnShowCalendar.getLocationOnScreen().getY()
                + jcalendar.btnShowCalendar.getHeight();

        // if the dialog's right/bottom side beyond screen size, cut it.
        if ((x + WIDTH) > (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()) {
            x = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - WIDTH;
        }
        if ((y + HEIGHT) > (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()) {
            y = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - HEIGHT;
        }
        setLocation(x, y);
        this.setModal(true);
        this.setVisible(true);
    }

    /**
     * when window Gained Focus, this will call backed. Here will dispose dialog.
     * 
     * @param e
     *            WindowEvent
     */
    public void windowGainedFocus(WindowEvent e) {
        cancelPerformed();
    }

    /**
     * when window lost Focus, this will call backed. Do nothing here.
     * 
     * @param e
     *            WindowEvent
     */
    public void windowLostFocus(WindowEvent e) {
    }

    /**
     * @param isTimeVisable
     *            the isTimeVisable to set
     */
    public void setTimeVisable(boolean isTimeVisable) {
        this.isTimeVisable = isTimeVisable;
        startSpinner.setEnabled(false);
        this.startSpnModel.setValue(new Date(0, 0, 0));
    }

    /**
     * @return the isTimeVisable
     */
    public boolean isTimeVisable() {
        return isTimeVisable;
    }
}
