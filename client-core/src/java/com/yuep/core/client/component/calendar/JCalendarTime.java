/*
 * Copyright (c) 2004, Yotc Technologies Co,,Ltd
 * All rights reserved.
 *
 * This software is copyrighted and owned by Yotc or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
/*
 * $RCSfile: JCalendarTime.java,v $  $Revision: 1.2 $  $Date: 2008/05/05 07:56:53 $
 */
package com.yuep.core.client.component.calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Window;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.border.EtchedBorder;

/**
 * @author
 * created 2004Äê11ÔÂ4ÈÕ
 * modified [who date description]
 * check [who date description]
 * @trace [Feature Id]
 * @version 1.0
 */

public class JCalendarTime extends JPanel {
    private static final long serialVersionUID = -6042786212786089051L;

    // JFrame frame = new JFrame();
    /**
     * Description of the field
     */
    Window frame = null;

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

    /**
     * Description of the field
     */
    DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM);

    /**
     * Description of the field
     */
    JCalendar calender;

    /**
     * Description of the field
     */
    JPanel timePanel = new JPanel();

    // JSpinner jSpinner1 = new JSpinner();
    /**
     * Description of the field
     */
    GridLayout gridLayout1 = new GridLayout();

    /**
     * Description of the field
     */
    BorderLayout borderLayout1 = new BorderLayout();

    /**
     * Constructor for the JCalendarTime object
     * 
     * @param frame
     *            Description of the Parameter
     */
    public JCalendarTime(Window frame) {
        this.frame = frame;
        if (frame instanceof JFrame) {
            JFrame frm = (JFrame) frame;

            calender = new JCalendar(frm);
        }
        if (frame instanceof JDialog) {
            JDialog frm = (JDialog) frame;

            calender = new JCalendar(frm);
        }

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Gets the Date attribute of the JCalendarTime object
     * 
     * @return The Date value
     */
    public Date getDate() {

        Date first = this.calender.getDate();

        Date second = (Date) this.startSpinner.getModel().getValue();
        if (first != null) {
            Date date = new Date(first.getYear(), first.getMonth(), first
                    .getDate(), second.getHours(), second.getMinutes(), second
                    .getSeconds());
            return date;
        }
        return null;
    }

    /**
     * Sets the Date attribute of the JCalendar object
     * 
     * @param date
     *            The new Date value
     */
    public void setDate(Date date) {

        if (date != null) {
            calender.setDate(date);
            startSpinner.getModel().setValue(date);
        } else {
            calender.txtDate.setText("");
            ((JSpinner.DefaultEditor) startSpinner.getEditor()).getTextField()
                    .setText("");
        }
    }

    /**
     * init gui component
     * 
     * @throws Exception
     *             exception
     */
    void jbInit() throws Exception {
        this.setLayout(gridLayout1);
        startSpnModel.setCalendarField(java.util.Calendar.SECOND);
        startSpinner.setPreferredSize(new Dimension(300, 22));
        JSpinner.DateEditor startEditor = new JSpinner.DateEditor(startSpinner,
                dateFormatPattern);
        startSpinner.setEditor(startEditor);
        setDate(null);
        calender.setBorder(BorderFactory.createCompoundBorder(new EtchedBorder(
                EtchedBorder.RAISED, Color.white, new Color(148, 145, 140)),
                BorderFactory.createEmptyBorder(0, 0, 0, 15)));
        timePanel.setBorder(BorderFactory.createCompoundBorder(
                new EtchedBorder(EtchedBorder.RAISED, Color.white, new Color(
                        148, 145, 140)), BorderFactory.createEmptyBorder(0, 15,
                        0, 0)));
        timePanel.setLayout(borderLayout1);
        gridLayout1.setColumns(2);

        startSpinner.setBorder(BorderFactory.createLoweredBevelBorder());
        this.add(calender, null);
        this.add(timePanel, null);
        timePanel.add(startSpinner, BorderLayout.CENTER);

    }

    /**
     * 
     * @param bl
     *            boolean
     */
    public void setIDNEnable(boolean bl) {
        calender.txtDate.setEnabled(bl);
        calender.btnShowCalendar.setEnabled(bl);
        startSpinner.setEnabled(bl);
    }

    /**
     * Description of the Method
     * 
     * @param argv
     *            Description of the Parameter
     */
    public static void main(String argv[]) {
        JFrame fr = new JFrame("test");
        JCalendarTime test = new JCalendarTime(fr);

        fr.getContentPane().setLayout(new BorderLayout());
        fr.setSize(600, 600);
        fr.getContentPane().add(test, BorderLayout.CENTER);
        fr.setVisible(true);
        System.out.print("dddddddd" + test.calender.getText() + "aaaaaa");
    }
}
