/*
 * $Id: ValidateMessage.java, 2009-3-12 上午11:48:11 aaron lee Exp $
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
 * Description:自动校验控件校验的消息
 * </p>
 * 
 * @author aaron lee
 * created 2009-3-12 上午11:48:11
 * modified [who date description]
 * check [who date description]
 */
public class ValidateMessage {

    /**
     * 消息类型
     */
    private String messageType;
    /**
     * 消息内容
     */
    private String msg;
    
    /**
     * <p>
     * Title: MessageType
     * </p>
     * <p>
     * Description:消息类型
     * </p>
     * 
     * @author aaron lee
 * created 2010-3-30 下午04:53:53
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
     * 获取消息内容
     * @return String 消息内容
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 设置消息内容
     * @param msg 消息内容
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 获取消息类型
     * @return String 消息类型
     */
    public String getMessageType() {
        return messageType;
    }

    /**
     * 设置消息类型
     * @param messageType 消息类型
     */
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
