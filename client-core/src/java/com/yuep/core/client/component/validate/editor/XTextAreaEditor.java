/*
 * $Id: XTextAreaEditor.java, 2009-5-7 下午01:51:47 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.validate.editor;

import java.awt.Color;
import java.awt.Dimension;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.commons.lang.StringUtils;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.validate.validator.Validator;
import com.yuep.core.client.mvc.validate.ValidateFocusListener;
import com.yuep.core.client.mvc.validate.ValidateMessage;

/**
 * <p>
 * Title: XTextAreaEditor
 * </p>
 * <p>
 * Description:文本区自动校验控件
 * </p>
 * 
 * @author aaron lee
 * created 2009-5-7 下午01:51:47
 * modified [who date description]
 * check [who date description]
 */
public class XTextAreaEditor<T extends Validator> extends JTextArea implements XEditor {

    private static final long serialVersionUID = -7621756285290075052L;
    /**
     * 自动校验器
     */
    protected Validator validator;
    /**
     * 是否是必填项
     */
    protected boolean isRequire;
    /**
     * 控件的属性名
     */
    protected String attributeName;
    /**
     * 校验的结果信息
     */
    protected ValidateMessage validateMessage;
    /**
     * 清除标志
     */
    protected boolean clearFlag;

    /**
     * @see PropertyChangeSupport
     */
    protected PropertyChangeSupport propertyChangeSupport;

    /**
     * 原始信息
     */
    protected String originMessage = "";
    /**
     * 控件是否初始化过
     */
    protected boolean isInitialized = false;
    /**
     * 为自动校验控件添加描述信息的listener
     */
    protected ValidateFocusListener focusListener;
    /**
     * 控件的默认Border
     */
    private Border defaultBorder;

    /**
     * constructor
     */
    public XTextAreaEditor() {
        setMinimumSize(new Dimension(200, 20));
        validateMessage = new ValidateMessage();
        propertyChangeSupport = new PropertyChangeSupport(this);
        focusListener = new ValidateFocusListener(this);
        addFocusListener(focusListener);
        getDocument().addDocumentListener(new MyDocumentListener());
        // setInputVerifier(new MyVerifier());
        defaultBorder = getBorder();
        
    }
    /**
     * 校验必填项
     * @return ValidateMessage 返回校验结果信息
     */
    protected ValidateMessage validateRequire() {
        String value = getText();
        if (isRequire && StringUtils.isEmpty(value)) {
            String string = ClientCoreContext.getString(attributeName);
            validateMessage.setMessageType(ValidateMessage.MessageType.ERROR);
            String message = ClientCoreContext.getString("common.validate.require.message", string);
            validateMessage.setMsg(message);
            propertyChange(attributeName, validateMessage);
            // requestFocusInWindow();
        } else {
            validateMessage.setMessageType(ValidateMessage.MessageType.CLEAN);
            validateMessage.setMsg("");
            propertyChange(attributeName, validateMessage);
        }
        return validateMessage;
    }

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.component.validate.editor.XEditor#initValidateRequire()
     */
    public ValidateMessage initValidateRequire() {
        String value = getText();
        if (isRequire && (StringUtils.isEmpty(value))) {
            String string = ClientCoreContext.getString(attributeName);
            validateMessage.setMessageType(ValidateMessage.MessageType.ERROR);
            String message = ClientCoreContext.getString("common.validate.require.message", string);
            validateMessage.setMsg(message);
        } else {
            validateMessage.setMessageType(ValidateMessage.MessageType.CLEAN);
            validateMessage.setMsg("");
        }
        return validateMessage;
    }

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.component.validate.editor.XEditor#propertyChange(java.lang.String, java.lang.Object)
     */
    public void propertyChange(String propertyName, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, null, newValue);
    }
    /**
     * fire property change event。
     * @param propertyName property name
     * @param oldValue ole value
     * @param newValue new value
     * @see PropertyChangeSupport
     */
    public void propertyChange(String propertyName, Object oldValue, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }

    /**
     * (non-Javadoc)
     * @see java.awt.Container#addPropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
     */
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
    }

    /**
     * (non-Javadoc)
     * @see java.awt.Component#removePropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
     */
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
    }

    // class MyVerifier extends InputVerifier{
    //
    // public boolean shouldYieldFocus(JComponent input) {
    // boolean inputOK = verify(input);
    // if (inputOK) {
    // return true;
    // } else {
    // Toolkit.getDefaultToolkit().beep();
    // return false;
    // }
    // }
    // /**
    // * (non-Javadoc)
    // * @see javax.swing.InputVerifier#verify(javax.swing.JComponent)
    // */
    // @Override
    // public boolean verify(JComponent input) {
    // return validateField();
    // }
    // }

    /**
     * 通过自动校验器，校验控件内填充的内容
     * @return 如果通过校验返回<code>true</code>，否则返回<code>false</code>
     */
    protected boolean validateField() {
        if (clearFlag)
            return false;
        if (validator == null)
            return true;
        validateMessage = validator.validate(getText());
        String msg = validateMessage.getMsg();
        if (StringUtils.isEmpty(msg))
            validateRequire();
        else {
            String string = ClientCoreContext.getString(attributeName);
            validateMessage.setMsg(string + msg);
        }

        propertyChange(attributeName, validateMessage);
        if (StringUtils.isEmpty(validateMessage.getMsg())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <p>
     * Title: MyDocumentListener
     * </p>
     * <p>
     * Description:文本输入控件的Document Listener,通过重写changedUpdate，insertUpdate和removeUpdate方法来实现自动校验
     * </p>
     * 
     * @author aaron lee
 * created 2010-4-2 上午10:36:29
     * modified [who date description]
     * check [who date description]
     */
    public class MyDocumentListener implements DocumentListener {

        @Override
        public void changedUpdate(DocumentEvent e) {
            if (checkValidator()) {
                validateField();
            }
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            if (checkValidator()) {
                validateField();
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            if (checkValidator()) {
                validateField();
            }
        }
    }

    /**
     * 清除输入信息
     */
    public void clearInput() {
        clearFlag = true;
        setText("");
        clearFlag = false;
    }

    /**
     * 检查控件是否有自动校验器
     * @return 如果有返回<code>true</code>，否则返回<code>false</code>
     */
    protected boolean checkValidator() {
        if (validator == null)
            return false;
        else
            return true;
    }
    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.component.validate.editor.XEditor#getValidator()
     */
    public Validator getValidator() {
        return validator;
    }

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.component.validate.editor.XEditor#getPropertyName()
     */
    public String getPropertyName() {
        return attributeName;
    }

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.component.validate.editor.XEditor#isCommited()
     */
    public boolean isCommited() {
        return isRequire ? (StringUtils.isEmpty(getText()) ? false : (isModified()) ? judgeMessage() : true)
                : (isModified() ? judgeMessage() : true);
    }

    /**
     * 判断校验结果消息是否是Error消息
     * @return 如果不是error返回<code>true</code>，否则返回<code>false</code>
     */
    protected boolean judgeMessage() {
        String messageType = validateMessage.getMessageType();
        return !StringUtils.isEmpty(messageType) ? (ValidateMessage.MessageType.ERROR.equals(messageType) ? false
                : true)
                : true;
    }

    /**
     * (non-Javadoc)
     * 
     * @see javax.swing.text.JTextComponent#setText(java.lang.String)
     */
    @Override
    public void setText(String t) {
        super.setText(t);
        if (!isInitialized) {
            originMessage = t;
            isInitialized = true;
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.validate.editor.XEditor#isModified()
     */
    @Override
    public boolean isModified() {
        if (!isInitialized) {
            isInitialized = true;
        }
        String text = getText();
        if (isRequire) {
            if (StringUtils.isEmpty(text))
                return true;
        }
        return isInitialized ? (StringUtils.isEmpty(text) && StringUtils.isEmpty(originMessage) ? false
                : (!text.equals(originMessage)) ? true : false) : false;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.validate.editor.XEditor#setInitialized(boolean)
     */
    @Override
    public void setInitialized(boolean isInitialized) {
        this.isInitialized = isInitialized;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.validate.editor.XEditor#setDescription(java.lang.String)
     */
    @Override
    public void setDescription(String description) {
        focusListener.setDescription(description);
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.validate.editor.XEditor#clearErrorBorder()
     */
    @Override
    public void clearErrorBorder() {
        setBorder(defaultBorder);
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.validate.editor.XEditor#setErrorBorder()
     */
    @Override
    public void setErrorBorder() {
        setBorder(BorderFactory.createLineBorder(Color.RED));
    }
    /**
     * @see com.yuep.core.client.component.validate.editor.XEditor#setPropertyName(java.lang.String)
     */
    @Override
    public void setPropertyName(String propertyName) {
        this.attributeName = propertyName;
        focusListener.setPropertyName(propertyName);
    }
    /**
     * @param isRequire the isRequire to set
     */
    public void setRequire(boolean isRequire) {
        this.isRequire = isRequire;
    }
    /**
     * @param validator the validator to set
     */
    public void setValidator(Validator validator) {
        this.validator = validator;
    }
}