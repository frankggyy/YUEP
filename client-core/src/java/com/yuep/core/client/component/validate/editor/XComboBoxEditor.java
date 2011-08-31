/*
 * $Id: XComboBoxEditor.java, 2009-4-24 上午11:09:03 aaron lee Exp $
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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.border.Border;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.validate.validator.Validator;
import com.yuep.core.client.mvc.validate.ValidateFocusListener;
import com.yuep.core.client.mvc.validate.ValidateMessage;

/**
 * <p>
 * Title: XComboBoxEditor
 * </p>
 * <p>
 * Description:ComboBox自动校验控件
 * </p>
 * 
 * @author aaron lee
 * created 2009-4-24 上午11:09:03
 * modified [who date description]
 * check [who date description]
 */
public class XComboBoxEditor extends JComboBox implements XEditor {

    private static final long serialVersionUID = -1361361600961129842L;

    /**
     * 初始的选择项
     */
    private Object originSelectItem;
    /**
     * 控件属性
     */
    protected String attributeName;
    /**
     * @see PropertyChangeSupport
     */
    protected PropertyChangeSupport propertyChangeSupport;
    /**
     * 是否初始化选择过
     */
    private boolean isInitialized = false;
    /**
     * 为自动校验控件添加描述信息的listener
     */
    protected ValidateFocusListener focusListener;
    /**
     * 控件的Border
     */
    private Border defaultBorder;
    /**
     * 是否是必填项
     */
    protected boolean isRequire;
    /**
     * 校验结果信息
     */
    protected ValidateMessage validateMessage;

    /**
     * constructor
     */
    public XComboBoxEditor() {
        propertyChangeSupport = new PropertyChangeSupport(this);
        validateMessage = new ValidateMessage();
        focusListener = new ValidateFocusListener(this);
        this.addItemListener(new MyItemListener());
        addFocusListener(focusListener);
        defaultBorder = getBorder();
    }

    /**
     * <p>
     * Title: MyItemListener
     * </p>
     * <p>
     * Description:控件选择状态改变的Listener
     * </p>
     * 
     * @author aaron lee
 * created 2010-4-2 上午10:13:19
     * modified [who date description]
     * check [who date description]
     */
    public class MyItemListener implements ItemListener {
        /**
         * (non-Javadoc)
         * 
         * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
         */
        @Override
        public void itemStateChanged(ItemEvent e) {
            ValidateMessage validateMessage = new ValidateMessage();
            validateMessage.setMessageType(ValidateMessage.MessageType.CLEAN);
            propertyChange(attributeName, validateMessage);
        }

    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.validate.editor.XEditor#getPropertyName()
     */
    @Override
    public String getPropertyName() {
        return attributeName;
    }

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.component.validate.editor.XEditor#propertyChange(java.lang.String, java.lang.Object)
     */
    public void propertyChange(String propertyName, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, null, newValue);
    }

    /**
     * Fire property change event.
     * @param propertyName property name
     * @param oldValue old value
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

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.validate.editor.XEditor#getValidator()
     */
    @Override
    public Validator getValidator() {
        return null;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.validate.editor.XEditor#isCommited()
     */
    @Override
    public boolean isCommited() {
        Object selectedItem = getSelectedItem();
        if(!isEnabled())
            return true;
        if (isRequire) {
            return selectedItem == null ? false : true;
        } else {
            return originSelectItem == null ? (selectedItem != null)
                    : (selectedItem.equals(originSelectItem) ? false : true);
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see javax.swing.JComboBox#setSelectedItem(java.lang.Object)
     */
    @Override
    public void setSelectedItem(Object anObject) {
        super.setSelectedItem(anObject);
        if (!isInitialized) {
            originSelectItem = anObject;
            isInitialized = true;
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.validate.editor.XEditor#initValidateRequire()
     */
    @Override
    public ValidateMessage initValidateRequire() {
        validateMessage.setMessageType(ValidateMessage.MessageType.CLEAN);
        validateMessage.setMsg("");
        if (isRequire) {
            Object selectedItem = getSelectedItem();
            if (selectedItem == null) {
                String string = ClientCoreContext.getString(attributeName);
                validateMessage.setMessageType(ValidateMessage.MessageType.ERROR);
                String message = ClientCoreContext.getString("common.validate.require.message", string);
                validateMessage.setMsg(message);
            }
        }
        return validateMessage;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.validate.editor.XEditor#isModified()
     */
    @Override
    public boolean isModified() {
        Object selectedItem = getSelectedItem();
        if (isRequire) {
            return true;
        } else
            return isInitialized ? (selectedItem == null) ? false
                    : (selectedItem.equals(originSelectItem) ? false : true) : false;
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
     * (non-Javadoc)
     * @see javax.swing.JComboBox#removeAllItems()
     */
    @Override
    public void removeAllItems() {
        super.removeAllItems();
        isInitialized = false;
    }

    /**
     * @see com.yuep.core.client.component.validate.editor.XEditor#setPropertyName(java.lang.String)
     */
    @Override
    public void setPropertyName(String propertyName) {
        this.attributeName = propertyName;
        focusListener.setPropertyName(propertyName);
    }
}
