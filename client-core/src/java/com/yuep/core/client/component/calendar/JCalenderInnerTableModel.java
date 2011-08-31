/*
 * Copyright (c) 2004, Yotc Technologies Co,,Ltd
 * All rights reserved.
 *
 * This software is copyrighted and owned by Yotc or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
/*
 * $RCSfile: JCalenderInnerTableModel.java,v $  $Revision: 1.2 $  $Date: 2008/05/05 07:56:53 $
 */
package com.yuep.core.client.component.calendar;

import java.util.Calendar;

import javax.swing.table.AbstractTableModel;

import com.yuep.core.client.ClientCoreContext;

/**
 * This class is a TableModel, use this model and JCalendarInnerTable, we can
 * create a inner table displayed in the inner dialog.
 * 
 * @author
 * created 2004Äê4ÔÂ2ÈÕ
 * modified [who date description]
 * check [who date description]
 * @trace [Feature Id]
 * @version [1.1.1]
 */
public class JCalenderInnerTableModel extends AbstractTableModel {
    private static final long serialVersionUID = -2681188958138432290L;

    /** how many days in one week. */
    private static final int DAY_WEEK_COUNT = 7;

    /**
     * Description of the field
     */
    private final String WEEK_SUN = ClientCoreContext.getString(
            "common.calendar.sunday");
    /**
     * Description of the field
     */
    private final String WEEK_MON = ClientCoreContext.getString(
            "common.calendar.monday");
    /**
     * Description of the field
     */
    private final String WEEK_TUE = ClientCoreContext.getString(
            "common.calendar.tuesday");
    /**
     * Description of the field
     */
    private final String WEEK_WED = ClientCoreContext.getString(
            "common.calendar.wednesday");
    /**
     * Description of the field
     */
    private final String WEEK_THU = ClientCoreContext.getString(
            "common.calendar.thursday");

    /** each day name in one week. */
    private final String WEEK_FRI = ClientCoreContext.getString(
            "common.calendar.friday");
    /**
     * Description of the field
     */
    private final String WEEK_SAT = ClientCoreContext.getString(
            "common.calendar.saturday");
    /**
     * Description of the field
     */
    private Calendar calendar;

    /**
     * Constructor for the JCalenderInnerTableModel object
     * 
     * @param calendar
     *            calendar object of this table model responsible for.
     */
    public JCalenderInnerTableModel(Calendar calendar) {
        this.calendar = calendar;
    }

    /**
     * get table week header.
     * 
     * @param index
     *            int
     * @return string
     */
    private String getWeekHeader(int index) {
        switch (index) {
        case 0:
            return WEEK_SUN;
        case 1:
            return WEEK_MON;
        case 2:
            return WEEK_TUE;
        case 3:
            return WEEK_WED;
        case 4:
            return WEEK_THU;
        case 5:
            return WEEK_FRI;
        case 6:
            return WEEK_SAT;
        default:
            return null;
        }
    }

    /**
     * Gets the ColumnCount attribute of the JCalenderInnerTableModel object
     * 
     * @return The ColumnCount value
     */
    public int getColumnCount() {
        return DAY_WEEK_COUNT;
    }

    /**
     * Gets the RowCount attribute of the JCalenderInnerTableModel object
     * 
     * @return The RowCount value
     */
    public int getRowCount() {
        return DAY_WEEK_COUNT;
    }

    /**
     * Gets the ValueAt attribute of the JCalenderInnerTableModel object
     * 
     * @param row
     *            the row of table
     * @param column
     *            the column of table
     * @return The Value At given row and column
     */
    public Object getValueAt(int row, int column) {
        if (row == 0) {
            return getWeekHeader(column);
        }
        row--;

        Calendar tempcalendar = (Calendar) calendar.clone();

        tempcalendar.set(Calendar.DAY_OF_MONTH, 1);

        int dayCount = tempcalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int moreDayCount = tempcalendar.get(Calendar.DAY_OF_WEEK) - 1;
        int index = row * DAY_WEEK_COUNT + column;
        int dayIndex = index - moreDayCount + 1;

        if (index < moreDayCount || dayIndex > dayCount) {
            return null;
        } else {
            return Integer.valueOf(dayIndex);
        }
    }
}
