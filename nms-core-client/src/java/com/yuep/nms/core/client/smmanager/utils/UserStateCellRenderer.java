/*
 * $Id: UserStateCellRenderer.java, 2011-4-22 下午05:11:36 luwei Exp $
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

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.nms.core.common.smcore.model.User;

/**
 * <p>
 * Title: UserStateCellRenderer
 * </p>
 * <p>
 * Description:用户状态renderer
 * </p>
 * 
 * @author luwei
 * created 2011-4-22 下午05:11:36
 * modified [who date description]
 * check [who date description]
 */
public class UserStateCellRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = -8173608061247046765L;

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        int state = (Integer) value;
        if (state == User.STATE_DISABLE) {
            setText(ClientCoreContext.getString("User.STATE_DISABLE"));
        }
        if (state == User.STATE_LOCK) {
            setText(ClientCoreContext.getString("User.STATE_LOCK"));
        }
        if (state == User.STATE_NORMAL) {
            setText(ClientCoreContext.getString("User.STATE_NORMAL"));
        }
        return comp;
    }
}
