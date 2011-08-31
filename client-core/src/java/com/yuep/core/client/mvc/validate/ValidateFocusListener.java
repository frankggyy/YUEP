/*
 * $Id: ValidateFocusListener.java, 2009-6-19 上午11:27:35 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.mvc.validate;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import org.apache.commons.lang.StringUtils;

import com.yuep.core.client.component.validate.editor.XEditor;

/**
 * <p>
 * Title: ValidateFocusListener
 * </p>
 * <p>
 * Description: 为自动校验控件添加描述信息，描述信息可以显示在页面的消息输出区
 * </p>
 * 
 * @author aaron lee
 * created 2009-6-19 上午11:27:35
 * modified [who date description]
 * check [who date description]
 */
public class ValidateFocusListener extends FocusAdapter{

    /**
     * 控件的描述信息
     */
    protected String description = "";
    /**
     * 自动校验控件的属性名
     */
    protected String propertyName;
    /**
     * 自动校验控件
     */
    protected XEditor editor;
    
    /**
     * 构造方法
     * @param editor 自动校验控件
     */
    public ValidateFocusListener(XEditor editor) {
        this.editor = editor;
    }
    /**
     * (non-Javadoc)
     * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
     */
    @Override
    public void focusGained(FocusEvent e) {
        if(!StringUtils.isEmpty(description)){
            ValidateMessage validateMessage = new ValidateMessage();
            validateMessage.setMessageType(ValidateMessage.MessageType.DESC_MSG);
            validateMessage.setMsg(description);
            editor.propertyChange(propertyName, validateMessage);
        }
    }
    
    /**
     * 设置描述信息
     * @param description 描述信息
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * @param propertyName the propertyName to set
     */
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
}