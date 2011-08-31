/*
 * $Id: ValidateMessage.java, 2009-3-12 ����11:48:11 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.mvc.validate;

/**
 * <p>
 * Title: ValidateMessage
 * </p>
 * <p>
 * Description:�Զ�У��ؼ�У�����Ϣ
 * </p>
 * 
 * @author aaron lee
 * created 2009-3-12 ����11:48:11
 * modified [who date description]
 * check [who date description]
 */
public class ValidateMessage {

    /**
     * ��Ϣ����
     */
    private String messageType;
    /**
     * ��Ϣ����
     */
    private String msg;
    
    /**
     * <p>
     * Title: MessageType
     * </p>
     * <p>
     * Description:��Ϣ����
     * </p>
     * 
     * @author aaron lee
 * created 2010-3-30 ����04:53:53
     * modified [who date description]
     * check [who date description]
     */
    public interface MessageType{
        public static String WARNING = "warning";
        public static String ERROR = "error";
        public static String CLEAN = "clean";
        public static String DESC_MSG = "desc_msg";
    }

    /**
     * ��ȡ��Ϣ����
     * @return String ��Ϣ����
     */
    public String getMsg() {
        return msg;
    }

    /**
     * ������Ϣ����
     * @param msg ��Ϣ����
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * ��ȡ��Ϣ����
     * @return String ��Ϣ����
     */
    public String getMessageType() {
        return messageType;
    }

    /**
     * ������Ϣ����
     * @param messageType ��Ϣ����
     */
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
