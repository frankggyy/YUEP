/*
 * $Id: ValidateFocusListener.java, 2009-6-19 ����11:27:35 aaron lee Exp $
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
 * Description: Ϊ�Զ�У��ؼ����������Ϣ��������Ϣ������ʾ��ҳ�����Ϣ�����
 * </p>
 * 
 * @author aaron lee
 * created 2009-6-19 ����11:27:35
 * modified [who date description]
 * check [who date description]
 */
public class ValidateFocusListener extends FocusAdapter{

    /**
     * �ؼ���������Ϣ
     */
    protected String description = "";
    /**
     * �Զ�У��ؼ���������
     */
    protected String propertyName;
    /**
     * �Զ�У��ؼ�
     */
    protected XEditor editor;
    
    /**
     * ���췽��
     * @param editor �Զ�У��ؼ�
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
     * ����������Ϣ
     * @param description ������Ϣ
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