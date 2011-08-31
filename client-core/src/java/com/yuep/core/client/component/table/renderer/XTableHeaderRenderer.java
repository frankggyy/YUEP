/*
 * $Id: XTableHeaderRenderer.java, 2010-1-25 下午04:22:06 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.table.renderer;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import org.apache.commons.lang.StringUtils;

import twaver.TUIManager;

/**
 * <p>
 * Title: XTableHeaderRenderer
 * </p>
 * <p>
 * Description:表格列头的Renderer，所有列头默认居左显示
 * </p>
 * 
 * @author aaron lee
 * created 2010-1-25 下午04:22:06
 * modified [who date description]
 * check [who date description]
 */
public class XTableHeaderRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 8974839947095473334L;

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (!isSelected)
            setBackground(TUIManager.getPanelBackground());
        if (table != null) {
            TableColumn column2 = table.getColumnModel().getColumn(column);
            String string = column2.toString();
            if (!StringUtils.isEmpty(string)) {
                setToolTipText(string);
                setText(" " + string);
                this.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
                return this;
            }
        }

        setToolTipText(getText());
        setText(" " + getText());
        this.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        return this;
    }
}
