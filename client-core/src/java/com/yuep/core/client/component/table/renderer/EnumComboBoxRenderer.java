/*
 * $Id: EnumComboBoxRenderer.java, 2009-12-7 上午11:00:27 yangtao Exp $
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

import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import com.yuep.core.client.ClientCoreContext;

/**
 * 枚举类Enum的下拉框renderer,用于table中,被EnumComboxTableCellRenderer,EnumComboxTableCellEditor使用
 * @author sufeng
 *
 */
public class EnumComboBoxRenderer extends BasicComboBoxRenderer{

	private static final long serialVersionUID = 2922036290612779449L;

	@Override
    public Component getListCellRendererComponent(JList jlist, Object obj, int i, boolean flag, boolean flag1) {
        Component comp = super.getListCellRendererComponent(jlist, obj, i, flag, flag1);
        if(obj instanceof Enum){
        	Enum data = (Enum) obj;
            setText(ClientCoreContext.getString(data.getClass().getSimpleName()+"."+data.name()));
        }
        return comp;
    }
	
}
