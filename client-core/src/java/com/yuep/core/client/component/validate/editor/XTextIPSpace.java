/*
 * $Id: XTextIPSpace.java, 2009-3-16 下午08:34:29 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.validate.editor;

import java.awt.Container;
import java.awt.event.ComponentAdapter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeSupport;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import org.apache.commons.lang.StringUtils;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.validate.validator.IpAddressValidator;
import com.yuep.core.client.component.validate.validator.Validator;
import com.yuep.core.client.mvc.validate.ValidateMessage;

/**
 * <p>
 * Title: XTextIPSpace
 * </p>
 * <p>
 * Description:IP控件的一段，一个IP控件有四段
 * </p>
 * 
 * @author aaron lee
 * created 2009-3-16 下午08:34:29
 * modified [who date description]
 * check [who date description]
 */
public class XTextIPSpace extends DefaultXEditor<Validator> {

    private static final long serialVersionUID = 6273920835883507371L;

    public XTextIPSpace textipspace;

    public boolean bFirstLostFocus = true;

    public boolean isCanFocus = false;

    public JTextField prevComponent;

    public JTextField nextComponent;

    private String index;

    private XIpAddressEditor parent;

    /**
     * @param attributeName
     * @param isRequire
     * @param validator
     */
    public XTextIPSpace(boolean isCanFocus,
            String index, XIpAddressEditor parent) {
        super();
        this.setColumns(3); // 设置IP段的最大列数为3，超出长度自动截断
        propertyChangeSupport = new PropertyChangeSupport(this);
        this.index = index;
        getDocument().addDocumentListener(new MyDocumentListener());
        addFocusListener(focusListener);
        setNoEdge();
        setMidHorizontal();
        addKeySet();
        addComponentChange();
        textipspace = this;
        this.isCanFocus = isCanFocus;
        this.parent = parent;
    }

    public void setNoEdge() {
        setBorder(null);
        setOpaque(true);
    }

    public void setPrevNextComponent(JTextField prev, JTextField next) {
        this.prevComponent = prev;
        this.nextComponent = next;
    }

    private void addComponentChange() {
        addComponentListener(new ComponentAdapter() {
        });
    }

    private void addKeySet() {
        KeyAdapter ka = new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    int pos = getCaretPosition();
                    if (pos == 0) {
                        gotoComponent(prevComponent);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (prevComponent != null)
                        prevComponent.selectAll();
                    gotoComponent(prevComponent);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (nextComponent != null)
                        nextComponent.selectAll();
                    gotoComponent(nextComponent);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_DELETE) {
                    int pos = getCaretPosition();
                    int maxPos = getText().trim().length();
                    if (pos == maxPos || maxPos == 0) {
                        gotoComponent(nextComponent);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    int pos = getCaretPosition();
                    if (pos == 0) {
                        gotoComponent(prevComponent);
                    }
                } else if (e.getKeyChar() == '.') {
                    int maxPos = getText().trim().length();
                    if (maxPos > 0) {
                        if (getSelectedText() == null) {
                            if (nextComponent != null)
                                nextComponent.selectAll();
                            gotoComponent(nextComponent);
                        }
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (Character.isDigit(e.getKeyChar())) {
                    int pos = getCaretPosition();
                    if (pos >= 3) {
                        gotoComponent(nextComponent);
                    }
                }
            }
        };
        addKeyListener(ka);
    }

    public void gotoComponent(JComponent jc) {
        if (jc != null) {
            jc.requestFocus();
            ((JTextField) jc).selectAll();
        }
    }

    public void setMidHorizontal() {
        setHorizontalAlignment(JTextField.CENTER);
    }

    @Override
    protected Document createDefaultModel() {
        return new IPDocument();
    }

    @Override
    public boolean isFocusTraversable() {
        return isCanFocus;
    }

    protected static class IPDocument extends PlainDocument {

        private static final long serialVersionUID = 6867783023640502445L;

        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            if (getLength() >= 3)
                return;

            String oldStr = getText(0, getLength());
            String newStr = null;
            char[] source = str.toCharArray();
            char[] result = new char[source.length];
            int i = 0;
            for (char c : source) {
                if (!Character.isDigit(c))
                    break;
                result[i++] = c;
            }

            newStr = new String(result, 0, i);
            String text = oldStr + newStr;
            if (text.isEmpty() || Integer.parseInt(text) > 255) {
                return;
            }
            super.insertString(offs, newStr, a);
        }

        // public void insertString(int offset, String str, AttributeSet a)
        // throws BadLocationException {
        // char[] insertChars = str.toCharArray();
        // boolean valid = true;
        // if (getLength() >= 3) {
        // valid = false;
        // return;
        // }
        // for (int i = 0; i < Math.min(insertChars.length, 3); i++) {
        // if (!(Character.isDigit(insertChars[i]))) {
        // valid = false;
        // break;
        // } else {
        // valid = true;
        // break;
        // }
        // }
        // if (valid)
        // super.insertString(offset, str, a);
        // if (getLength() > 3)
        // super.remove(offset + str.length(), getLength() - 2);
        // }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.validate.editor.AbstractXEditor#validateField()
     */
    @Override
    public boolean validateField() {
        if (clearFlag)
            return false;
        Container parent = this.getParent();
        if (parent instanceof XIpAddressEditor) {
            XIpAddressEditor<IpAddressValidator> ipAddressEditor = (XIpAddressEditor<IpAddressValidator>) parent;
            ValidateMessage validateRequire = ipAddressEditor.validateRequire();
            ipAddressEditor.propertyChange(propertyName, validateRequire);
        }
        String text = getText();
        if (!StringUtils.isEmpty(text)) {
            validateMessage = validator.validate(text);
            String msg = validateMessage.getMsg();
            if (!StringUtils.isEmpty(msg)) {
                String string = ClientCoreContext.getString(propertyName);
                validateMessage.setMsg(string + msg);
            }
            propertyChange(propertyName + index, validateMessage);
        }
        if (StringUtils.isEmpty(validateMessage.getMsg())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.validate.editor.AbstractXEditor#validateRequire()
     */
    @Override
    public ValidateMessage validateRequire() {
        String value = getText();
        if (isRequire && StringUtils.isEmpty(value)) {
            String string = ClientCoreContext.getString(propertyName);
            validateMessage.setMessageType(ValidateMessage.MessageType.ERROR);
            String message = ClientCoreContext.getString("common.validate.require.message", string);
            validateMessage.setMsg(message);
            propertyChange(propertyName + index, validateMessage);
            requestFocusInWindow();
        } else {
            validateMessage.setMessageType(ValidateMessage.MessageType.CLEAN);
            validateMessage.setMsg("");
        }
        return validateMessage;
    }

    /**
     * @return the index
     */
    public String getIndex() {
        return index;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.validate.editor.AbstractXEditor#propertyChange(java.lang.String,
     *      java.lang.Object)
     */
    @Override
    public void propertyChange(String propertyName, Object newValue) {
        if (parent != null)
            parent.propertyChange(propertyName, newValue);
    }
}
