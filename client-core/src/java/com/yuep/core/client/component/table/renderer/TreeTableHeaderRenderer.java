/*
 * $Id: TreeTableHeaderRenderer.java, 2010-1-25 下午03:20:30 aaron lee Exp $
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

import twaver.TUIManager;

/**
 * <p>
 * Title: TreeTableHeaderRenderer
 * </p>
 * <p>
 * Description:树标表头国际化Renderer
 * </p>
 * 
 * @author aaron lee
 * created 2010-1-25 下午03:20:30
 * modified [who date description]
 * check [who date description]
 */
public class TreeTableHeaderRenderer extends DefaultTableCellRenderer{

    private static final long serialVersionUID = 8974839947095473334L;
    private String displayName;
    public TreeTableHeaderRenderer(String displayName)
    {
        this.displayName = displayName;
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        setToolTipText(displayName);
        if(!isSelected)
            setBackground(TUIManager.getPanelBackground());
        setText(" " + displayName);
        this.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        return this;
    }
    
}
