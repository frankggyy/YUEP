/*
 * $Id: SmMessage.java, 2011-3-24 ����11:42:26 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.smcore.msg;

import java.io.Serializable;
import java.util.Map;

import com.yuep.core.message.def.BaseMessage;

/**
 * <p>
 * Title: SmMessage
 * </p>
 * <p>
 * Description: ��ȫģ�����Ϣ
 * </p>
 * 
 * @author sufeng
 * created 2011-3-24 ����11:42:26
 * modified [who date description]
 * check [who date description]
 */
public class SmMessage extends BaseMessage{

    private static final long serialVersionUID = 3901482006190284464L;
    
    public final static String NAME="sm.msg";
    
    /**
     * ��¼
     */
    public final static String TYPE_LOGIN="login";
    
    /**
     * ע��
     */
    public final static String TYPE_LOGOUT="logout";
    
    /**
     * ��Ч��¼
     */
    public final static String TYPE_INVALID_LOGIN="invalid.login";
    
    /**
     * �߳�
     */
    public final static String TYPE_USER_KICK_OUT="kick";
    
    /**
     * �û�����
     */
    public final static String TYPE_USER_EXPIRED="user.expired";
    
    /**
     * �������
     */
    public final static String TYPE_PWD_EXPIRED="password.expired";
    
    /**
     * Ȩ�ޱ仯
     */
    public final static String TYPE_USER_PERMISSION_CHANGED="permission.changed";
    
    /**
     * �û����Ի���Ϣ�仯
     */
    public final static String TYPE_CUSTOM_CHANGED="custom.changed";
    
    /**
     * �û����Ի���Ϣ��ɾ��
     */
    public final static String TYPE_CUSTOM_DELETED="custom.delete";
    
    /** userɾ�� */
    public final static String TYPE_USER_DELETE="user.delete";
    
    /** ���user */
    public final static String TYPE_USER_ADD="user.add";
    
    /** ����user */
    public final static String TYPE_USER_UPDATE="user.update";
    
    /**��ɫ�Ĳ�������**/
    public final static String TYPE_ROLE_DELETE="role.delete";
    
    /**
     * ��ӽ�ɫ
     */
    public final static String TYPE_ROLE_ADD="role.add";
    
    /**
     * ���½�ɫ
     */
    public final static String TYPE_ROLE_UPDATE="role.update";

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
    
    public SmMessage() {
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
