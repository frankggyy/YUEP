/*
 * $Id: TextIconButtonDecorator.java, 2010-10-18 上午10:46:54 aaron Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.factory.decorator.button;

import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.decorator.DefaultDecorator;

/**
 * <p>
 * Title: TextIconButtonDecorator
 * </p>
 * <p>
 * Description: 图标 + 字符型按钮的装饰器
 * </p>
 * 
 * @author aaron
 * created 2010-10-18 上午10:46:54
 * modified [who date description]
 * check [who date description]
 */
public class TextIconButtonDecorator extends DefaultDecorator<JButton> {

    private String tipKey;
    private String textKey;
    private String iconName;

    public TextIconButtonDecorator() {
    }
    
    public TextIconButtonDecorator(String textKey,String iconName,String tipKey) {
        this.tipKey = tipKey;
        this.textKey = textKey;
        this.iconName = iconName;
    }

    /**
     * @see com.yuep.core.client.component.factory.decorator.Decorator#decorate(java.lang.Object)
     */
    @Override
    public void decorate(JButton t) {
        Icon icon = ClientCoreContext.getResourceFactory().getIcon(iconName);
        t.setIcon(icon);
        t.setText(null);
        t.setToolTipText(ClientCoreContext.getString(tipKey));
        t.setMargin(new Insets(0, 0, 0, 0));
        t.setHorizontalAlignment(SwingConstants.CENTER);
        t.setVerticalAlignment(SwingConstants.CENTER);
        t.setBackground(null);
        t.setForeground(null);
        t.setBorder(null);
        t.setOpaque(false);
        int iconWidth = icon.getIconWidth();
        int iconHeight = icon.getIconHeight();

        t.setPreferredSize(new Dimension(iconWidth + 5 + 60, iconHeight + 5));
        t.setText(ClientCoreContext.getString(textKey));
        t.setFocusPainted(false);
        t.setBorderPainted(false);
    }

}
