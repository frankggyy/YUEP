/*
 * $Id: XIpAddressEditor.java, 2009-3-24 上午09:13:36 yangtao Exp $
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
 * Title: XIpAddressEditor
 * </p>
 * <p>
 * Description:枚举型ComboBox的自动校验控件
 * </p>
 * 
 * @author yangtao
 * created 2009-3-24 上午09:13:36
 * @modified aaron lee add auto validate funcation.
 * check [who date description]
 */
public class XEnumComboBox extends JComboBox implements XEditor {
    private static final long serialVersionUID = -5286787393772307745L;
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
     * 属性是否变化
     */
    private boolean propertyChanged;
    /**
     * Item的过滤器
     */
    private ItemFilter filter = null;
    /**
     * 枚举的具体类型
     */
    private Class clazz;

    /**
     * Contructor.
     * @param clz 枚举的具体类型
     * @param attributeName 控件属性名
     */
    public XEnumComboBox(Class<? extends Enum<?>> clz) {
        this.clazz = clz;
        propertyChangeSupport = new PropertyChangeSupport(this);
        focusListener = new ValidateFocusListener(this);
        this.addItemListener(new MyItemListener());
        addFocusListener(focusListener);
        EnumObject[] enumValues = getEnumValues(clz);
        for (EnumObject enumObject : enumValues) {
            addItem(enumObject);
        }
        defaultBorder = getBorder();
    }

    /**
     * 设置控件的过滤器
     * @param filter 过滤器
     */
    public void setFilter(ItemFilter filter) {
        this.filter = filter;
        if (filter == null)
            return;
        if (this.getItemCount() > 0) {
            this.removeAllItems();
        }
        EnumObject[] enumValues = getFilterEnumValues(clazz);
        for (EnumObject enumObject : enumValues) {
            addItem(enumObject);
        }
    }

    /**
     * 
     * (non-Javadoc)
     * 
     * @see javax.swing.JComboBox#getSelectedItem()
     */
    public Object getSelectedEnumItem() {
        if (super.getSelectedItem() == null)
            return null;
        EnumObject selectedObject = (EnumObject) super.getSelectedItem();
        return selectedObject.getSourceObject();

    }

    /**
     * 
     * (non-Javadoc)
     * 
     * @see javax.swing.JComboBox#setSelectedItem(java.lang.Object)
     */
    public void setSelectedItem(Object obj) {
        if (obj instanceof Enum<?>)
            super.setSelectedItem(new EnumObject((Enum<?>) obj));
        else
            super.setSelectedItem(obj);
        if (!isInitialized) {
            originSelectItem = obj;
            isInitialized = true;
        }
    }

    /**
     * 
     * (non-Javadoc)
     * 
     * @see javax.swing.JComboBox#getSelectedObjects()
     */
    public Object[] getSelectedObjects() {
        if (super.getSelectedObjects() == null)
            return null;
        Object[] selectedObjects = super.getSelectedObjects();
        Enum<?>[] enums = new Enum<?>[selectedObjects.length];
        for (int i = 0; i < selectedObjects.length; i++) {
            enums[i] = ((EnumObject) selectedObjects[i]).getSourceObject();
        }

        return enums;
    }

    private EnumObject[] getEnumValues(Class<? extends Enum<?>> clz) {
        try {
            Enum<?>[] enums = (Enum<?>[]) clz.getMethod("values").invoke(null);
            EnumObject[] enumObjects = new EnumObject[enums.length];
            for (int i = 0; i < enumObjects.length; i++) {
                enumObjects[i] = new EnumObject(enums[i]);
            }
            return enumObjects;
        } catch (Exception e) {
            e.printStackTrace();
            return new EnumObject[0];
        }
    }

    private EnumObject[] getFilterEnumValues(Class<? extends Enum<?>> clz) {
        try {
            Enum<?>[] enums = filter.filterEnums();
            EnumObject[] enumObjects = new EnumObject[enums.length];
            for (int i = 0; i < enumObjects.length; i++) {
                enumObjects[i] = new EnumObject(enums[i]);
            }
            return enumObjects;
        } catch (Exception e) {
            e.printStackTrace();
            return new EnumObject[0];
        }
    }

    public class EnumObject {
        private Enum<?> sourceObject;

        /**
         * 
         * @param sourceObject
         */
        public EnumObject(Enum<?> sourceObject) {
            this.sourceObject = sourceObject;
        }

        public Enum<?> getSourceObject() {
            return sourceObject;
        }

        /**
         * 
         * (non-Javadoc)
         * 
         * @see java.lang.Object#equals(java.lang.Object)
         */
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null || !(obj instanceof EnumObject))
                return false;
            EnumObject other = (EnumObject) obj;
            return other.getSourceObject() == this.getSourceObject();
        }

        /**
         * 
         * (non-Javadoc)
         * 
         * @see java.lang.Object#hashCode()
         */
        public int hashCode() {
            return this.getSourceObject().hashCode();
        }

        /**
         * 
         * (non-Javadoc)
         * 
         * @see java.lang.Object#toString()
         */
        public String toString() {
            return ClientCoreContext.getString(
                    clazz.getSimpleName() + "." + sourceObject.toString());
        }
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
 * created 2010-4-2 上午10:29:34
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
        if (!propertyChanged)
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
        if (!propertyChanged)
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
        Object selectedItem = getSelectedEnumItem();
        return originSelectItem == null ? (selectedItem != null)
                : (selectedItem.equals(originSelectItem) ? false : true);
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
        if (getSelectedEnumItem() == null)
            return false;
        Object selectedItem = getSelectedEnumItem();
        return isInitialized ? (selectedItem.equals(originSelectItem) ? false : true) : false;
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
     * 设置属性是否变化
     * @param propertyChanged
     *            the propertyChanged to set
     */
    public void setPropertyChanged(boolean propertyChanged) {
        this.propertyChanged = propertyChanged;
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
