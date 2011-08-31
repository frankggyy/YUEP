/*
 * $Id: XCheckBoxEditor.java, 2009-4-27 下午06:36:46 aaron lee Exp $
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
import javax.swing.JCheckBox;
import javax.swing.border.Border;

import com.yuep.core.client.component.validate.validator.Validator;
import com.yuep.core.client.mvc.validate.ValidateFocusListener;
import com.yuep.core.client.mvc.validate.ValidateMessage;

/**
 * <p>
 * Title: XCheckBoxEditor
 * </p>
 * <p>
 * Description:CheckBox控件自动校验
 * </p>
 * 
 * @author aaron lee
 * created 2009-4-27 下午06:36:46
 * modified [who date description]
 * check [who date description]
 */
public class XCheckBoxEditor extends JCheckBox implements XEditor {

    private static final long serialVersionUID = -5428383216437072078L;
    /**
     * 是否是原始选中状态
     */
    private Boolean isOriginSelected = null;
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
     * constructor
     */
    public XCheckBoxEditor() {
        propertyChangeSupport = new PropertyChangeSupport(this);
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
 * created 2010-4-2 上午10:09:45
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
        //boolean selected = isSelected();
        if (isOriginSelected == null)
            return false;
        return isSelected() ? (isOriginSelected ? false : true) : (!isOriginSelected ? false : true);
    }

    /**
     * (non-Javadoc)
     * 
     * @see javax.swing.AbstractButton#setSelected(boolean)
     */
    @Override
    public void setSelected(boolean b) {
        super.setSelected(b);
        isOriginSelected = b;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.validate.editor.XEditor#initValidateRequire()
     */
    @Override
    public ValidateMessage initValidateRequire() {
        return null;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.validate.editor.XEditor#isModified()
     */
    @Override
    public boolean isModified() {
        if (isOriginSelected == null)
            return false;
        return isOriginSelected ? (isSelected() ? false : true) : (isSelected() ? true : false);
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
     * @see com.ycignp.veapo.client.framework.component.validate.editor.XEditor#clearErrorBorder()
     */
    @Override
    public void clearErrorBorder() {
        setBorder(defaultBorder);
    }

    /**
     * (non-Javadoc)
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
}
