/*
 * $Id: ButtonDecorator.java, 2010-10-15 下午03:33:52 aaron Exp $
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
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.KeyEvent;

import javax.swing.JButton;

import org.apache.commons.lang.StringUtils;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.decorator.DefaultDecorator;
import com.yuep.core.client.util.XGuiUtils;

/**
 * <p>
 * Title: ButtonDecorator
 * </p>
 * <p>
 * Description: Button控件的装饰器
 * </p>
 * 
 * @author aaron
 * created 2010-10-15 下午03:33:52
 * modified [who date description]
 * check [who date description]
 */
public class ButtonDecorator extends DefaultDecorator<JButton> {

    private String textKey;

    private char mnemonic;

    public ButtonDecorator() {
    }
    
    public ButtonDecorator(String textKey) {
        this.textKey = textKey;
    }
    
    public ButtonDecorator(String textKey, char mnemonic) {
        this.textKey = textKey;
        this.mnemonic = mnemonic;
    }

    /**
     * @see com.yuep.core.client.component.factory.decorator.Decorator#decorate(java.lang.Object)
     */
    @Override
    public void decorate(JButton t) {
        if (StringUtils.isNotEmpty(textKey)) {
            String text = ClientCoreContext.getString(textKey);
            int vk = XGuiUtils.getMnemonicKey(mnemonic);
            if (text.toLowerCase().indexOf(String.valueOf(mnemonic).toLowerCase()) == -1) {
                text = new StringBuilder(text).append("(").append(KeyEvent.getKeyText(vk)).append(")").toString();
                t.setText(text);
            } else {
                t.setText(text);
            }
            // 根据文字多少决定按钮长度
            Font font = t.getFont();
            if (font != null) {
                FontMetrics fontMetrics = t.getFontMetrics(font);
                int width = fontMetrics.stringWidth(text) + 30;
                width = width < 90 ? 90 : width;
                t.setPreferredSize(new Dimension(width, t.getPreferredSize().height));
            } else {
                t.setPreferredSize(new Dimension(90, t.getPreferredSize().height));
            }
        }else{
            t.setMnemonic(mnemonic);
            t.setPreferredSize(new Dimension(90, 20));
        }
    }

}
