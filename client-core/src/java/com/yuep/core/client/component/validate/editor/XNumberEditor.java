/*
 * $Id: XNumberEditor.java, 2009-3-17 下午05:39:28 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.validate.editor;

import java.beans.PropertyChangeSupport;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import com.yuep.core.client.component.validate.validator.Validator;

/**
 * <p>
 * Title: XNumberEditor
 * </p>
 * <p>
 * Description:数字型自动校验控件
 * </p>
 * 
 * @author aaron lee
 * created 2009-3-17 下午05:39:28
 * modified [who date description]
 * check [who date description]
 */
public class XNumberEditor<NumberValidator> extends DefaultXEditor<Validator> {

    private static final long serialVersionUID = -5958082266168578598L;

    /**
     * 数据长度
     */
    private int thisLen = 0;

    /**
     * Constructor.
     * @param attributeName 控件的属性名
     * @param isRequire 是否必填
     * @param validator 自动校验器
     */
    public XNumberEditor() {
        super();
        propertyChangeSupport = new PropertyChangeSupport(this);
        addFocusListener(focusListener);
    }

    /**
     * (non-Javadoc)
     * @see javax.swing.JTextField#createDefaultModel()
     */
    @Override
    protected Document createDefaultModel() {
        NumberDocument numberEditor = new NumberDocument();
        numberEditor.addDocumentListener(new MyDocumentListener());
        return numberEditor;
    }

    /**
     * 将输入信息转换为int数据返回
     * @return int 输入数据
     */
    public int getInt() {
        String text = getText();
        if (text.isEmpty())
            throw new RuntimeException("The NumberTextField is empty");

        long value = Long.parseLong(text);
        if (value > Integer.MAX_VALUE)
            throw new RuntimeException("The ingeger value is overflow");

        return Integer.parseInt(text);
    }

    /**
     * 设置int型数据
     * @param intValue 输入数据
     */
    public void setInt(int intValue) {
        this.setText(String.valueOf(intValue));
    }

    /**
     * 是否为空，过滤两侧空字符
     * @return
     */
    public boolean isEmpty() {
        return getText().trim().length() == 0;
    }

    private class NumberDocument extends PlainDocument {

        private static final long serialVersionUID = 7805513298683713919L;

        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            String oldStr = getText(0, getLength());

            boolean hasSign = oldStr.length() > 0;

            char[] source = str.toCharArray();
            char[] result = new char[source.length];
            int i = 0;

            for (char c : source) {
                if (!hasSign && source[0] == '-' || Character.isDigit(c)) {
                    hasSign = true;
                    result[i++] = c;
                } else {
                    break;
                }
            }

            super.insertString(offs, new String(result, 0, i), a);
            // // 超过最大长度，直接返回
            // if (str == null || thisLen >= maxLen)
            // return;
            // // 处理是否是数字
            // char[] source = str.toCharArray();
            // char[] result = new char[source.length];
            // int j = 0;
            // for (int i = 0; i < result.length; i++) {
            // if (Character.isDigit(source[i]))
            // result[j++] = source[i];
            // }
            // // 将输入的数字转换为String型
            // str = new String(result, 0, j);
            // // 处理最大长度
            // if (thisLen + str.length() > maxLen)
            // str = str.substring(0, maxLen - thisLen);
            // thisLen += str.length();
            // super.insertString(offs, str, a);
        }

        /**
         * (non-Javadoc)
         * @see javax.swing.text.AbstractDocument#remove(int, int)
         */
        public void remove(int offs, int len) throws BadLocationException {
            thisLen -= len;
            super.remove(offs, len);
        }
    }
}
