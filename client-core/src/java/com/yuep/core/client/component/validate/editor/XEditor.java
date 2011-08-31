/*
 * $Id: XEditor.java, 2009-3-12 上午10:31:15 aaron lee Exp $
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

import com.yuep.core.client.component.validate.validator.Validator;
import com.yuep.core.client.mvc.validate.ValidateMessage;

/**
 * <p>
 * Title: Editor
 * </p>
 * <p>
 * Description:自动校验控件的接口
 * </p>
 * 
 * @author aaron lee
 * created 2009-3-12 上午10:31:15
 * modified [who date description]
 * check [who date description]
 */
public interface XEditor {

    /**
     * 返回控件的必填校验结果
     * @return ValidateMessage 必填校验结果
     */
    ValidateMessage initValidateRequire();

    /**
     * 返回控件的自动校验器
     * @return Validator 控件的自动校验器
     */
    Validator getValidator();

    /**
     * 返回控件的属性名
     * @return String 控件的属性名
     */
    String getPropertyName();
    
    void setPropertyName(String propertyName);

    /**
     * 控件的填充内容是否可以提交
     * @return boolean 是返回<code>true</code>，否则返回<code>false</code>
     */
    boolean isCommited();

    /**
     * 控件是否经过修改
     * @return boolean 是返回<code>true</code>，否则返回<code>false</code>
     */
    boolean isModified();

    /**
     * 添加属性变化的监听者
     * @param propertyName 属性名
     * @param listener 监听者
     */
    void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

    /**
     * 删除属性变化的监听者
     * @param propertyName 属性名
     * @param listener 监听者
     */
    void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);
    
    /**
     * 发送属性变化事件，主要是发送控件检验结果信息
     * @param propertyName 属性名
     * @param newValue 校验结果信息
     */
    void propertyChange(String propertyName, Object newValue);

    /**
     * 设置是否初始化过该控件
     * @param isInitialized 是否初始化
     */
    void setInitialized(boolean isInitialized);
    
    /**
     * 设置控件的描述信息
     * @param description 描述信息
     */
    void setDescription(String description);
    
    /**
     * 设置控件错误边框
     */
    void setErrorBorder();
    
    /**
     * 清除控件的错误边框
     */
    void clearErrorBorder();
}
