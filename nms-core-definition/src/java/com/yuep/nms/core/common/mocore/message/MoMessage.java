/*
 * $Id: MoMessage.java, 2011-3-28 ����11:11:33 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.mocore.message;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.yuep.core.message.def.BaseMessage;

/**
 * <p>
 * Title: MoMessage
 * </p>
 * <p>
 * Description:
 * �������Mo����¼�
 * </p>
 * 
 * @author yangtao
 * created 2011-3-28 ����11:11:33
 * modified [who date description]
 * check [who date description]
 */
final public class MoMessage extends BaseMessage{
    private static final long serialVersionUID = 5937930795907013556L;
    /** Mo����*/
    public static final String MO_CREATE="mo.create";
    /**Moɾ��*/
    public static final String MO_DELETE="mo.delete";
    /**Mo���Ա仯 */
    public static final String MO_ATTR_CHANGED="mo.attr.change";
    /** Mo״̬�仯**/
    public static final String MO_STATE_CHANGED="mo.state.change";
    /** ����ڵ����Ա仯*/
    public static final String MANAGEDNODEPROPERTY_UPDATE="mangedNodeProperty.update";
    /**MoMessage ��Ϣ����*/
    private String messageType;
    /**��Ϣ��,����Ϣ���;����������MANAGEDNODEPROPERTY_UPDATE��Ϣ����Ϣ��ΪManagedNodeProperty*/
    private Serializable messageBody;
    /**������Ϣ�������Ϣ����ΪMO_ATTR_CHANGED��MO_STATE_CHANGED��key����������ƣ�valueΪValueCompareObject����*/
    private Map<String,Serializable> additions=new HashMap<String,Serializable>();
    
    public MoMessage(Serializable messageBody,String messageType,Map<String,Serializable> additions){
        super(messageType,messageBody);
        this.messageBody=messageBody;
        this.messageType=messageType;
        this.additions=additions;
    }

   
    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public Serializable getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(Serializable messageBody) {
        this.messageBody = messageBody;
    }


    public Map<String, Serializable> getAdditions() {
        return additions;
    }

    public void setAdditions(Map<String, Serializable> additions) {
        this.additions = additions;
    }
    
    

}
