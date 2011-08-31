/*
 * $Id: XNumberEditor.java, 2009-3-17 ����05:39:28 aaron lee Exp $
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
 * Description:�������Զ�У��ؼ�
 * </p>
 * 
 * @author aaron lee
 * created 2009-3-17 ����05:39:28
 * modified [who date description]
 * check [who date description]
 */
public class XNumberEditor<NumberValidator> extends DefaultXEditor<Validator> {

    private static final long serialVersionUID = -5958082266168578598L;

    /**
     * ���ݳ���
     */
    private int thisLen = 0;

    /**
     * Constructor.
     * @param attributeName �ؼ���������
     * @param isRequire �Ƿ����
     * @param validator �Զ�У����
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
     * ��������Ϣת��Ϊint���ݷ���
     * @return int ��������
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
     * ����int������
     * @param intValue ��������
     */
    public void setInt(int intValue) {
        this.setText(String.valueOf(intValue));
    }

    /**
     * �Ƿ�Ϊ�գ�����������ַ�
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
            // // ������󳤶ȣ�ֱ�ӷ���
            // if (str == null || thisLen >= maxLen)
            // return;
            // // �����Ƿ�������
            // char[] source = str.toCharArray();
            // char[] result = new char[source.length];
            // int j = 0;
            // for (int i = 0; i < result.length; i++) {
            // if (Character.isDigit(source[i]))
            // result[j++] = source[i];
            // }
            // // �����������ת��ΪString��
            // str = new String(result, 0, j);
            // // ������󳤶�
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
