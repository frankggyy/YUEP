/*
 * $Id: SystemMessage.java, 2011-4-20 ����11:06:10 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.base.def;

import java.io.Serializable;
import java.util.Map;

import com.yuep.core.message.def.BaseMessage;

/**
 * <p>
 * Title: SystemMessage
 * </p>
 * <p>
 * Description: ϵͳ�ڲ���Ϣ
 * </p>
 * 
 * @author sufeng
 * created 2011-4-20 ����11:06:10
 * modified [who date description]
 * check [who date description]
 */
public class SystemMessage extends BaseMessage{
    
    private static final long serialVersionUID = -3295508456697766979L;

    /**
     * ϵͳ��Ϣname
     */
    public final static String NAME="system.msg";
    
    /**
     * user��¼����Ϣ
     */
    public final static String TYPE_LOGIN="login";
    
    /**
     * user logout����Ϣ
     */
    public final static String TYPE_LOGOUT="logout";
    
    /**
     * ��Ϣ���
     */
    private String messageType;
    
    /**
     * ��Ϣ��
     */
    private Serializable messageBody;
    
    /**
     * ������Ϣ
     */
    private Map<String,Serializable> additions;
    
    public SystemMessage() {
        super(NAME,"system");
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public void setMessageBody(Serializable messageBody) {
        this.messageBody = messageBody;
    }

    public Serializable getMessageBody() {
        return messageBody;
    }

    public void setAdditions(Map<String,Serializable> additions) {
        this.additions = additions;
    }

    public Map<String,Serializable> getAdditions() {
        return additions;
    }
}
