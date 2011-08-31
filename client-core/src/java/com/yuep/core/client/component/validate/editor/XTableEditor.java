/*
 * $Id: YotcTTableEditor.java, 2009-5-13 下午09:27:42 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.validate.editor;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import org.apache.commons.collections.CollectionUtils;

import twaver.table.TTableAdapter;
import twaver.table.TTableModelEvent;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.table.XTable;
import com.yuep.core.client.component.validate.validator.Validator;
import com.yuep.core.client.mvc.validate.ValidateFocusListener;
import com.yuep.core.client.mvc.validate.ValidateMessage;

/**
 * <p>
 * Title: YotcTTableEditor
 * </p>
 * <p>
 * Description:表格自动校验控件，只能校验表格内是否存在数据
 * </p>
 * 
 * @author aaron lee
 * created 2009-5-13 下午09:27:42
 * modified [who date description]
 * check [who date description]
 */
public class XTableEditor<T> extends XTable<T> implements XEditor {

    private static final long serialVersionUID = 4060944135430043786L;

    /**
     * 控件的属性名
     */
    protected String attributeName;
    /**
     * @see PropertyChangeSupport
     */
    protected PropertyChangeSupport propertyChangeSupport;
    /**
     * 默认的错误信息
     */
    protected String errorMsg = ClientCoreContext.getString("common.validate.table.defaulterror.msg");
    /**
     * 为自动校验控件添加描述信息的listener
     */
    protected ValidateFocusListener focusListener;
    /**
     * 控件边框
     */
    private Border defaultBorder;
    /**
     * 是否变化过
     */
    private boolean changed = true;

    /**
     * Contructor
     * 
     * @param clazz
     *            表格中存储数据的类型
     * @param realClass
     *            真实数据类型
     * @param uniquePropertyName
     *            表格中存储数据的唯一属性
     * @param propertyNames
     *            表格显示的列（与存储数据的属性对应）
     */
    public <R> XTableEditor(Class<T> clazz, Class<R> realClass, String uniquePropertyName,
            String... propertyNames) {
        super(clazz, realClass, uniquePropertyName, propertyNames);
        propertyChangeSupport = new PropertyChangeSupport(this);
        this.getTableModel().addTableListener(new TTableAdapter() {

            @Override
            public void tableDataChanged(TTableModelEvent ttablemodelevent) {
                if (isEnabled()) {
                    List<T> allDatas = getAllDatas();
                    if (!CollectionUtils.isEmpty(allDatas)) {
                        ValidateMessage validateMessage = new ValidateMessage();
                        validateMessage.setMessageType(ValidateMessage.MessageType.CLEAN);
                        propertyChange(attributeName, validateMessage);
                    } else {
                        ValidateMessage validateMessage = new ValidateMessage();
                        validateMessage.setMessageType(ValidateMessage.MessageType.ERROR);
                        validateMessage.setMsg(errorMsg);
                        propertyChange(attributeName, validateMessage);
                    }
                }
            }

        });
        focusListener = new ValidateFocusListener(this);
        addFocusListener(focusListener);
        defaultBorder = getBorder();
    }

    public <R> XTableEditor(Class<T> clazz, Class<R> realClass, String uniquePropertyName) {
        this(clazz, realClass, uniquePropertyName, new String[] {});
    }

    public <R> XTableEditor(Class<T> clazz, String uniquePropertyName) {
        this(clazz, uniquePropertyName, new String[] {});
    }

    public <R> XTableEditor(Class<T> clazz, String uniquePropertyName, String... propertyNames) {
        this(clazz, null, uniquePropertyName, propertyNames);
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
     * 
     * @see com.ycignp.veapo.client.framework.component.validate.editor.XEditor#propertyChange(java.lang.String,
     *      java.lang.Object)
     */
    public void propertyChange(final String propertyName, final Object newValue) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                propertyChangeSupport.firePropertyChange(propertyName, null, newValue);
            }

        });

    }

    /**
     * fire property change event。
     * 
     * @param propertyName
     *            property name
     * @param oldValue
     *            ole value
     * @param newValue
     *            new value
     * @see PropertyChangeSupport
     */
    public void propertyChange(final String propertyName, final Object oldValue, final Object newValue) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
            }

        });

    }

    /**
     * (non-Javadoc)
     * 
     * @see java.awt.Container#addPropertyChangeListener(java.lang.String,
     *      java.beans.PropertyChangeListener)
     */
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
    }

    /**
     * (non-Javadoc)
     * 
     * @see java.awt.Component#removePropertyChangeListener(java.lang.String,
     *      java.beans.PropertyChangeListener)
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
     * @see com.ycignp.veapo.client.framework.component.validate.editor.XEditor#initValidateRequire()
     */
    @Override
    public ValidateMessage initValidateRequire() {
        return null;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.validate.editor.XEditor#isCommited()
     */
    @Override
    public boolean isCommited() {
        if (!changed)
            return false;
        if (isEnabled()) {
            List<T> allDatas = getAllDatas();
            if (CollectionUtils.isEmpty(allDatas)) {
                return false;
            }
        }
        return true;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.validate.editor.XEditor#isModified()
     */
    @Override
    public boolean isModified() {
        return true;
    }

    /**
     * 设置错误信息
     * 
     * @param errorMsg
     *            the errorMsg to set
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#setEnabled(boolean)
     */
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (!enabled) {
            ValidateMessage validateMessage = new ValidateMessage();
            validateMessage.setMessageType(ValidateMessage.MessageType.CLEAN);
            propertyChange(attributeName, validateMessage);
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.validate.editor.XEditor#setInitialized(boolean)
     */
    @Override
    public void setInitialized(boolean isInitialized) {
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
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.validate.editor.XEditor#setErrorBorder()
     */
    @Override
    public void setErrorBorder() {
    }

    /**
     * 设置是否变化过
     * 
     * @param changed
     */
    public void setChanged(boolean changed) {
        this.changed = changed;
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
