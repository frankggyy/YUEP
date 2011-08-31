/*
 * $Id: TimeTableCellRenderer.java, 2011-4-21 下午06:29:46 luwei Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.smmanager.utils;

import java.awt.Component;
import java.text.SimpleDateFormat;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.yuep.core.client.ClientCoreContext;

/**
 * <p>
 * Title: TimeTableCellRenderer
 * </p>
 * <p>
 * Description:时间renderer
 * </p>
 * 
 * @author luwei
 * created 2011-4-21 下午06:29:46
 * modified [who date description]
 * check [who date description]
 */
public class TimeTableCellRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 5144097505413532896L;

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (value == null || "".equals(value.toString())) {
            setText(ClientCoreContext.getString("User.forever"));
        } else if (value instanceof Long) {
            Long time1 = (Long) value;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String timeshort = dateFormat.format(time1);
            setText(timeshort);
        }
        return comp;
    }
}
