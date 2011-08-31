/*
 * Copyright (c) 2004, Yotc Technologies Co,,Ltd
 * All rights reserved.
 *
 * This software is copyrighted and owned by Yotc or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
/*
 * $RCSfile: JCalendarInnerTable.java,v $  $Revision: 1.3 $  $Date: 2008/05/05 07:56:53 $
 */
package com.yuep.core.client.component.calendar;

import java.awt.Color;
import java.awt.Component;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;

/**
 * This class define a table component which will displayed in the JCalendar
 * dialog as a inner table. The table show one month's days and user can click
 * table cell to select day.
 * 
 * @author
 * created 2004Äê4ÔÂ2ÈÕ
 * modified [who date description]
 * check [who date description]
 * @trace [Feature Id]
 * @version [1.1.1]
 */
public class JCalendarInnerTable extends JTable {
    private static final long serialVersionUID = -8794680584447426085L;

    /**
     * Description of the field
     */
    private final Color BACKGROUND = Color.white;

    /**
     * Description of the field
     */
    private final Color FOREGROUND = Color.black;

    /**
     * Description of the field
     */
    private final Color HEADER_BACKGROUND = Color.black;

    /**
     * Description of the field
     */
    private final Color HEADER_FOREGROUND = Color.white;

    /**
     * Description of the field
     */
    private final Color SELECTED_BACKGROUND = Color.blue;

    /**
     * Description of the field
     */
    private final Color SELECTED_FOREGROUND = Color.white;

    // i18n resource bundle handle.

    /**
     * Description of the Field
     */
    protected Calendar calendar;

    /**
     * Constructor for the CalendarInnerTable object
     * 
     * @param model
     *            table's model.
     * @param calendar
     *            calendar this table responsible for.
     */
    public JCalendarInnerTable(JCalenderInnerTableModel model, Calendar calendar) {
        super(model);
        this.calendar = calendar;
        // init table
        setCellSelectionEnabled(true);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setDefaultRenderer(getColumnClass(0), new TableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus,
                    int row, int column) {
                String text = (value == null) ? "" : value.toString();
                JLabel cell = new JLabel(text);

                cell.setOpaque(true);
                if (row == 0) {
                    cell.setForeground(HEADER_FOREGROUND);
                    cell.setBackground(HEADER_BACKGROUND);
                } else {
                    if (isSelected) {
                        cell.setForeground(SELECTED_FOREGROUND);
                        cell.setBackground(SELECTED_BACKGROUND);
                    } else {
                        cell.setForeground(FOREGROUND);
                        cell.setBackground(BACKGROUND);
                    }
                }

                return cell;
            }
        });

    }

    /**
     * Updates the selection models of the table, depending on the state of the
     * two flags: toggle and extend. All changes to the selection that are the
     * result of keyboard or mouse events received by the UI are channeled
     * through this method so that the behavior may be overridden by a subclass.
     * 
     * @param row
     *            affects the selection at row
     * @param column
     *            affects the selection at column
     * @param toggle
     *            see description above
     * @param extend
     *            if true, extend the current selection
     */
    public void changeSelection(int row, int column, boolean toggle,
            boolean extend) {
        Object obj = getValueAt(row, column);

        // if move to the available cell, then select it and change calendar
        // date
        if ((obj != null) && (row != 0)) {
            calendar.set(Calendar.DAY_OF_MONTH, ((Integer) obj).intValue());
            super.changeSelection(row, column, toggle, extend);
        }
        // else forbiden the move and select.
        else {
            super.changeSelection(row, column, true, true);
        }
    }

}
