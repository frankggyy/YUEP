/*
 * $Id: SmMessage.java, 2011-3-24 上午11:42:26 sufeng Exp $
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
 * Description: 安全模块的消息
 * </p>
 * 
 * @author sufeng
 * created 2011-3-24 上午11:42:26
 * modified [who date description]
 * check [who date description]
 */
public class SmMessage extends BaseMessage{

    private static final long serialVersionUID = 3901482006190284464L;
    
    public final static String NAME="sm.msg";
    
    /**
     * 登录
     */
    public final static String TYPE_LOGIN="login";
    
    /**
     * 注销
     */
    public final static String TYPE_LOGOUT="logout";
    
    /**
     * 无效登录
     */
    public final static String TYPE_INVALID_LOGIN="invalid.login";
    
    /**
     * 踢出
     */
    public final static String TYPE_USER_KICK_OUT="kick";
    
    /**
     * 用户过期
     */
    public final static String TYPE_USER_EXPIRED="user.expired";
    
    /**
     * 密码过期
     */
    public final static String TYPE_PWD_EXPIRED="password.expired";
    
    /**
     * 权限变化
     */
    public final static String TYPE_USER_PERMISSION_CHANGED="permission.changed";
    
    /**
     * 用户个性化信息变化
     */
    public final static String TYPE_CUSTOM_CHANGED="custom.changed";
    
    /**
     * 用户个性化信息被删除
     */
    public final static String TYPE_CUSTOM_DELETED="custom.delete";
    
    /** user删除 */
    public final static String TYPE_USER_DELETE="user.delete";
    
    /** 添加user */
    public final static String TYPE_USER_ADD="user.add";
    
    /** 更新user */
    public final static String TYPE_USER_UPDATE="user.update";
    
    /**角色的操作类型**/
    public final static String TYPE_ROLE_DELETE="role.delete";
    
    /**
     * 添加角色
     */
    public final static String TYPE_ROLE_ADD="role.add";
    
    /**
     * 更新角色
     */
    public final static String TYPE_ROLE_UPDATE="role.update";

    /**
     * 消息类别
     */
    private String messageType;
    
    /**
     * 消息体
     */
    private Serializable messageBody;
    
    /**
     * 附加信息
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
