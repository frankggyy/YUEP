/*
 * $Id: HelpButtonDecorator.java, 2010-10-18 上午10:14:56 aaron Exp $
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.decorator.DefaultDecorator;

/**
 * <p>
 * Title: HelpButtonDecorator
 * </p>
 * <p>
 * Description:帮助按钮控件的装饰器
 * </p>
 * 
 * @author aaron
 * created 2010-10-18 上午10:14:56
 * modified [who date description]
 * check [who date description]
 */
public class HelpButtonDecorator extends DefaultDecorator<JButton> {

    private String helpId;
    public HelpButtonDecorator(String helpId) {
        this.helpId = helpId;
    }
    
    /**
     * @see com.yuep.core.client.component.factory.decorator.Decorator#decorate(java.lang.Object)
     */
    @Override
    public void decorate(JButton t) {
        Icon moduleIcon = ClientCoreContext.getResourceFactory().getIcon("help_icon.gif");
        Icon rolloverAndSelectIcon = ClientCoreContext.getResourceFactory().getIcon("help_icon_rollover.gif");
        t.setText(null);
        t.setToolTipText(ClientCoreContext.getString("common.button.help.tooltip"));
        t.setRolloverEnabled(true);
        t.setRolloverIcon(rolloverAndSelectIcon);
        t.setSelectedIcon(rolloverAndSelectIcon);
        t.setMargin(new Insets(0, 0, 0, 0));
        t.setHorizontalAlignment(SwingConstants.CENTER);
        t.setVerticalAlignment(SwingConstants.CENTER);
        t.setBackground(null);
        t.setForeground(null);
        t.setBorder(null);
        t.setOpaque(false);
        int iconWidth = moduleIcon.getIconWidth();
        int iconHeight = moduleIcon.getIconHeight();

        t.setPreferredSize(new Dimension(iconWidth + 5, iconHeight + 5));
        t.setFocusPainted(false);
        t.setBorderPainted(false);
        t.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                //TODO associated helpId
                System.out.println(helpId);
            }

        });
    }

}
