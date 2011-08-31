/*
 * $Id: XMacAddressEditor.java, 2009-3-17 下午02:46:04 aaron lee Exp $
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

import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import org.apache.commons.lang.StringUtils;

import com.yuep.core.client.component.validate.validator.Validator;

/**
 * <p>
 * Title: XMacAddressEditor
 * </p>
 * <p>
 * Description:mac地址自动校验控件
 * </p>
 * 
 * @author aaron lee
 * created 2009-3-17 下午02:46:04
 * modified [who date description]
 * check [who date description]
 */
public class XMacAddressEditor<MacAddressValidator> extends DefaultXEditor<Validator> {

    private static final long serialVersionUID = 3778930038758476093L;
    /**
     * 控件输入信息的Listener
     */
    private InputListener listener;

    /**
     * Contructor
     * @param attributeName 控件属性
     * @param isRequire 是否必填
     * @param validator 自动校验器
     */
    public XMacAddressEditor() {
        super();
        propertyChangeSupport = new PropertyChangeSupport(this);
        listener = new InputListener();
        this.setDocument(new MacAddrDocument());
        this.getDocument().addDocumentListener(listener);
        addFocusListener(focusListener);
    }

    /**
     * 格式化MAC地址
     * @param macAddr 需要格式化的MAC地址
     * @return String 输入的信息111111111111转换为11-11-11-11-11-11
     */
    public String formatMacAddr(String macAddr) {
        if (StringUtils.isEmpty(macAddr))
            return "";
        if (macAddr.length() != 17) {
            return macAddr;
        }
        // 重新组装
        StringBuffer bf = new StringBuffer();
        bf.append(macAddr.substring(0, 2)).append(macAddr.substring(3, 5)).append(macAddr.substring(6, 8))
                .append(macAddr.substring(9, 11)).append(macAddr.substring(12, 14)).append(
                        macAddr.substring(15, 17));
        return bf.toString().toUpperCase();
    }

    /**
     * 格式化MAC地址
     * @param macAddr 需要格式化的MAC地址
     * @return String 输入的信息11-11-11-11-11-11转换为111111111111
     */
    public void formatText2Mac(String macAddr) {
        StringBuffer bf = new StringBuffer();
        if (macAddr.length() <= 17) {
            String[] split = macAddr.split("-");
            for (int i = 0; i < split.length; i++) {
                String string = split[i];
                int length = string.length();
                if (length > 2) {
                    formatString(bf, string);
                } else {
                    if (i != 5 && string.length() == 2)
                        bf.append(string).append("-");
                    else
                        bf.append(string);
                }
            }
        } else {
            bf.append(macAddr.substring(0, 17));
        }
        final String upperCase = bf.toString().toUpperCase();
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                getDocument().removeDocumentListener(listener);
                setText(upperCase);
                getDocument().addDocumentListener(listener);
            }

        });

    }

    private void formatString(StringBuffer bf, String string) {
        String substring = string.substring(2);
        if(substring.length() > 2){
            bf.append(string.substring(0, 2)).append("-");
            formatString(bf, substring);
        }else
            bf.append(string.substring(0, 2)).append("-").append(substring);
    }
    /**
     * <p>
     * Title: InputListener
     * </p>
     * <p>
     * Description: 输入信息的Listener
     * </p>
     * 
     * @author aaron lee
 * created 2010-4-2 上午10:49:56
     * modified [who date description]
     * check [who date description]
     */
    class InputListener implements DocumentListener {

        @Override
        public void changedUpdate(DocumentEvent e) {
            if (checkValidator()) {
                validate();
            }
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            if (checkValidator()) {
                validate();
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            if (checkValidator()) {
                romoveValidate();
            }
        }

        private void validate() {
            String inputText = getInput();
            formatText2Mac(inputText);
            validateMessage = validator.validate(inputText);
            propertyChange(propertyName, validateMessage);
        }
        /**
         * 对于删除，只需要得到数据，根据数据判断是否合法即可<br>
         * 去掉MAC地址的格式化功能formatText2Mac，实现删除功能（包括删除横钱）
         */
        private void romoveValidate(){
            String inputText = getInput();
            validateMessage = validator.validate(inputText);
            propertyChange(propertyName, validateMessage);
        }
    }

    /**
     * 返回输入信息
     * @return String 输入信息
     */
    public String getInput() {
        return super.getText();
    }

    /**
     * (non-Javadoc)
     * 
     * @see javax.swing.text.JTextComponent#getText()
     */
    @Override
    public String getText() {
        String input = getInput();
        String formatMacAddr = formatMacAddr(input);
        return formatMacAddr;
    }

    /**
     * <p>
     * Title: MacAddrDocument
     * </p>
     * <p>
     * Description:控制输入信息合法性的Document
     * </p>
     * 
     * @author aaron lee
 * created 2010-4-2 上午10:51:02
     * modified [who date description]
     * check [who date description]
     */
    class MacAddrDocument extends PlainDocument {

        // 对文本框的输入进行输入限制，构造PlainDocument实现

        private static final long serialVersionUID = -6472743317489015963L;

        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            String temp = this.getText(0, getLength());

            char[] source = str.toCharArray();
            char[] result = new char[source.length];
            int j = 0;
            for (int i = 0; i < result.length; i++) {
                if ((i + 1) % 3 == 0) {
                    result[j++] = source[i];
                } else if (Character.isDigit(source[i]) || (Character.toLowerCase(source[i])) >= 'a'
                        && Character.toLowerCase(source[i]) <= 'f')
                    result[j++] = source[i];
            }
            int originLength = new String(temp.getBytes()).length();
            if ((originLength + j) > 17) {
                j = 17 - originLength;
            }
            super.insertString(offs, new String(result, 0, j), a);
        }
    }
}
