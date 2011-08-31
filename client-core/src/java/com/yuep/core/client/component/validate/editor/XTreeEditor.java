/*
 * $Id: XTreeEditor.java, 2009-5-13 下午06:10:22 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.validate.editor;

import java.awt.event.FocusEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import org.apache.commons.collections.CollectionUtils;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.tree.XTree;
import com.yuep.core.client.component.validate.validator.Validator;
import com.yuep.core.client.mvc.validate.ValidateFocusListener;
import com.yuep.core.client.mvc.validate.ValidateMessage;

/**
 * <p>
 * Title: XTreeEditor
 * </p>
 * <p>
 * Description:树自动校验控件
 * </p>
 * 
 * @author aaron lee
 * created 2009-5-13 下午06:10:22
 * modified [who date description]
 * check [who date description]
 */
public class XTreeEditor extends XTree implements XEditor {
    private static final long serialVersionUID = -7270539085407137232L;
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
    protected String errorMsg = ClientCoreContext.getString(
            "common.validate.tree.defaulterror.msg");
    /**
     * 为自动校验控件添加描述信息的listener
     */
    protected transient ValidateFocusListener focusListener;
    /**
     * 控件的边框
     */
    private Border defaultBorder;
    /**
     * 是否是必选
     */
    private boolean isRequsted = false;

    public XTreeEditor(String propertyName) {
        this.attributeName = propertyName;
        propertyChangeSupport = new PropertyChangeSupport(this);
        focusListener = new TreeValidateFocusListener(this);
        defaultBorder = getBorder();
        this.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {

            @SuppressWarnings("unchecked")
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                List allSelectedElement = getDataBox().getSelectionModel().getAllSelectedElement();
                if (!CollectionUtils.isEmpty(allSelectedElement)) {
                    ValidateMessage validateMessage = new ValidateMessage();
                    validateMessage.setMessageType(ValidateMessage.MessageType.CLEAN);
                    propertyChange(attributeName, validateMessage);
                } else {
                    if (XTreeEditor.this.isRequsted) {
                        ValidateMessage validateMessage = new ValidateMessage();
                        validateMessage.setMessageType(ValidateMessage.MessageType.ERROR);
                        validateMessage.setMsg(errorMsg);
                        propertyChange(attributeName, validateMessage);
                    }
                }
            }

        });
        addFocusListener(focusListener);
    }
    
    /**
     * <p>
     * Title: TreeValidateFocusListener
     * </p>
     * <p>
     * Description:tree控件添加描述信息的listener
     * </p>
     * 
     * @author aaron lee
 * created 2010-4-2 上午10:31:36
     * modified [who date description]
     * check [who date description]
     */
    class TreeValidateFocusListener extends ValidateFocusListener{

        public TreeValidateFocusListener(XEditor editor) {
            super(editor);
        }
        
        /**
         * (non-Javadoc)
         * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
         */
        @Override
        public void focusGained(FocusEvent e) {
            super.focusGained(e);
            List allSelectedElement = getDataBox().getSelectionModel().getAllSelectedElement();
            if (CollectionUtils.isEmpty(allSelectedElement) && isRequsted) {
                ValidateMessage validateMessage = new ValidateMessage();
                validateMessage.setMessageType(ValidateMessage.MessageType.ERROR);
                validateMessage.setMsg(errorMsg);
                propertyChange(attributeName, validateMessage);
            } else {
                ValidateMessage validateMessage = new ValidateMessage();
                validateMessage.setMessageType(ValidateMessage.MessageType.CLEAN);
                propertyChange(attributeName, validateMessage);
            }
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

    public void propertyChange(final String propertyName, final Object newValue) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                propertyChangeSupport.firePropertyChange(propertyName, null, newValue);
            }

        });

    }

    /**
     * Fire property change event.
     * @param propertyName property name
     * @param oldValue old value
     * @param newValue new value
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
        List allSelectedElement = getDataBox().getSelectionModel().getAllSelectedElement();
        if (CollectionUtils.isEmpty(allSelectedElement) && this.isRequsted) {
            return false;
        } else {
            return true;
        }
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
     * 设置校验错误的信息
     * @param errorMsg
     *            the errorMsg to set
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
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
     * @see com.yuep.core.client.component.validate.editor.XEditor#setPropertyName(java.lang.String)
     */
    @Override
    public void setPropertyName(String propertyName) {
        this.attributeName = propertyName;
    }

    /**
     * @param isRequsted the isRequsted to set
     */
    public void setRequsted(boolean isRequsted) {
        this.isRequsted = isRequsted;
    }
}
