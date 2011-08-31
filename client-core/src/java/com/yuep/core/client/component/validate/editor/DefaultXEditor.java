/*
 * $Id: DefaultXEditor.java, 2009-3-9 ����05:03:21 aaron lee Exp $
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
import javax.swing.JTextField;
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
 * Title: DefaultXEditor
 * </p>
 * <p>
 * Description:�����Զ�У��ؼ�����Ҫ�̳еĳ����࣬�̳���JTextField����ʵ����XEditor�ӿ�
 * </p>
 * 
 * @author aaron lee
 * created 2009-3-9 ����05:03:21
 * modified [who date description]
 * check [who date description]
 */
public class DefaultXEditor<T extends Validator> extends JTextField implements XEditor {

    private static final long serialVersionUID = 1400205236131767094L;
    /**
     * �Զ�У����
     */
    protected Validator validator;
    /**
     * �Ƿ��Ǳ�����
     */
    protected boolean isRequire;
    /**
     * �ؼ���������
     */
    protected String propertyName;
    /**
     * У��Ľ����Ϣ
     */
    protected ValidateMessage validateMessage;
    /**
     * �����־
     */
    protected boolean clearFlag;

    /**
     * @see PropertyChangeSupport
     */
    protected PropertyChangeSupport propertyChangeSupport;

    /**
     * ԭʼ��Ϣ
     */
    protected String originMessage = "";
    /**
     * �ؼ��Ƿ��ʼ����
     */
    protected boolean isInitialized = false;
    /**
     * Ϊ�Զ�У��ؼ����������Ϣ��listener
     */
    protected ValidateFocusListener focusListener;
    /**
     * �ؼ���Ĭ��Border
     */
    private Border defaultBorder;

    /**
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#setToolTipText(java.lang.String)
     */
    @Override
    public void setToolTipText(String text) {
        super.setToolTipText(text);
    }

    /**
     * constructor
     */
    public DefaultXEditor() {
        setMinimumSize(new Dimension(200, 20));
        validateMessage = new ValidateMessage();
        propertyChangeSupport = new PropertyChangeSupport(this);
        getDocument().addDocumentListener(new MyDocumentListener());
        focusListener = new ValidateFocusListener(this);
        addFocusListener(focusListener);
        // setInputVerifier(new MyVerifier());
        defaultBorder = getBorder();
    }

    /**
     * У�������
     * @return ValidateMessage ����У������Ϣ
     */
    protected ValidateMessage validateRequire() {
        String value = getText();
        if (this instanceof XIpAddressEditor) {
            validateMessage.setMessageType(ValidateMessage.MessageType.CLEAN);
            validateMessage.setMsg("");
            String[] split = StringUtils.split(value, '.');
            if (isRequire && split.length != 4) {
                String string = ClientCoreContext.getString(propertyName);
                validateMessage.setMessageType(ValidateMessage.MessageType.ERROR);
                String message = ClientCoreContext.getString("common.validate.require.message", string);
                validateMessage.setMsg(message);
            }
        } else {
            if (!isInitialized) {
                validateMessage.setMessageType(ValidateMessage.MessageType.CLEAN);
                validateMessage.setMsg("");
                propertyChange(propertyName, validateMessage);
            }
            if (isRequire && StringUtils.isEmpty(value)) {
                String string = ClientCoreContext.getString(propertyName);
                validateMessage.setMessageType(ValidateMessage.MessageType.ERROR);
                String message = ClientCoreContext.getString("common.validate.require.message", string);
                validateMessage.setMsg(message);
                propertyChange(propertyName, validateMessage);
                // requestFocusInWindow();
            } else {
                validateMessage.setMessageType(ValidateMessage.MessageType.CLEAN);
                validateMessage.setMsg("");
                propertyChange(propertyName, validateMessage);
            }
        }
        return validateMessage;
    }

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.component.validate.editor.XEditor#initValidateRequire()
     */
    public ValidateMessage initValidateRequire() {
        String value = getText();
        if (isRequire
                && (StringUtils.isEmpty(value) || ((this instanceof XIpAddressEditor)
                        && !StringUtils.isEmpty(value) && value.equals("...")))) {
            String string = ClientCoreContext.getString(propertyName);
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
     * fire property change event��
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
     * ͨ���Զ�У������У��ؼ�����������
     * @return ���ͨ��У�鷵��<code>true</code>�����򷵻�<code>false</code>
     */
    protected boolean validateField() {
        if (!isEnabled()) {
            validateMessage.setMessageType(ValidateMessage.MessageType.CLEAN);
            validateMessage.setMsg("");
        }
        if (clearFlag)
            return false;
        if (validator == null)
            return true;
        validateMessage = validator.validate(getText());
        String msg = validateMessage.getMsg();
        if (!isInitialized) {
            validateMessage.setMessageType(ValidateMessage.MessageType.CLEAN);
            validateMessage.setMsg("");
        } else {
            if (StringUtils.isEmpty(msg))
                validateRequire();
            else {
                String string = ClientCoreContext.getString(propertyName);
                validateMessage.setMsg(string + msg);
            }
        }

        propertyChange(propertyName, validateMessage);
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
     * Description:�ı�����ؼ���Document Listener,ͨ����дchangedUpdate��insertUpdate��removeUpdate������ʵ���Զ�У��
     * </p>
     * 
     * @author aaron lee
 * created 2010-4-2 ����09:30:19
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
     * ���������Ϣ
     */
    public void clearInput() {
        clearFlag = true;
        setText("");
        clearFlag = false;
    }

    /**
     * ���ؼ��Ƿ����Զ�У����
     * @return ����з���<code>true</code>�����򷵻�<code>false</code>
     */
    protected boolean checkValidator() {
        if (validator == null)
            return false;
        else
            return true;
    }

    /**
     * @see com.yuep.core.client.component.validate.editor.XEditor#getValidator()
     */
    public Validator getValidator() {
        return validator;
    }

    /**
     * �����Զ�У����
     * @param validator �Զ�У����
     */
    public void setValidator(Validator validator) {
        this.validator = validator;
        validateField();
    }
    
    /**
     * @see com.yuep.core.client.component.validate.editor.XEditor#getPropertyName()
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.component.validate.editor.XEditor#isCommited()
     */
    public boolean isCommited() {
        if (!isEnabled()) {
            return true;
        }
        if (isRequire) {
            String text = getText();
            if (StringUtils.isEmpty(text)) {
                setErrorBorder();
                return false;
            } else {
                if (isModified()) {
                    if (judgeMessage()) {
                        clearErrorBorder();
                        return true;
                    } else {
                        setErrorBorder();
                        return false;
                    }
                } else {
                    setErrorBorder();
                    return false;
                }
            }
        } else {
            if (isModified()) {
                if (judgeMessage()) {
                    clearErrorBorder();
                    return true;
                } else {
                    setErrorBorder();
                    return false;
                }
            } else {
                setErrorBorder();
                return false;
            }
        }
    }

    /**
     * �ж�У������Ϣ�Ƿ���Error��Ϣ
     * @return �������error����<code>true</code>�����򷵻�<code>false</code>
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
        if (t == null)
            t = "";
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
        if(isEnabled())
            setBorder(BorderFactory.createLineBorder(Color.RED));
    }

    /**
     * ���ÿؼ��Ƿ��Ǳ���ؼ�
     * @param isRequire
     *            the isRequire to set
     */
    public void setRequire(boolean isRequire) {
        this.isRequire = isRequire;
    }

    /**
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#setEnabled(boolean)
     */
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (!isEnabled()) {
            validateMessage.setMessageType(ValidateMessage.MessageType.CLEAN);
            validateMessage.setMsg("");
            propertyChange(propertyName, validateMessage);
            clearErrorBorder();
        }
    }

    /**
     * @see com.yuep.core.client.component.validate.editor.XEditor#setPropertyName(java.lang.String)
     */
    @Override
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
        focusListener.setPropertyName(propertyName);
    }
}