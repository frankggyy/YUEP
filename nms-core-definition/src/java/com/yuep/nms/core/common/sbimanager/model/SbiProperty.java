/*
 * $Id: SbiProperty.java, 2011-4-15 下午12:29:48 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.sbimanager.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

import com.yuep.nms.core.common.mocore.naming.MoNaming;

/**
 * <p>
 * Title: SbiProperty
 * </p>
 * <p>
 * Description:
 * SBI基本属性
 * </p>
 * 
 * @author yangtao
 * created 2011-4-15 下午12:29:48
 * modified [who date description]
 * check [who date description]
 */
@Entity
public class SbiProperty implements Serializable {
    private static final long serialVersionUID = 6422088657787388129L;

    @Id
    @Type(type="com.yuep.nms.core.common.mocore.usertype.MoNamingUserType")
    /**SBI标识*/
    private MoNaming sbiNaming;
    /**SBI名称*/
    private String sbiName;
    /**IP地址*/
    private String ip;
    /**端口*/
    private int port;
    /**用户名*/
    private String userName;
    /**密码*/
    private String password;
    
    public MoNaming getSbiNaming() {
        return sbiNaming;
    }
    public void setSbiNaming(MoNaming sbiNaming) {
        this.sbiNaming = sbiNaming;
    }
    public String getSbiName() {
        return sbiName;
    }
    public void setSbiName(String sbiName) {
        this.sbiName = sbiName;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public String toString() {
        return sbiName;
    }
    
    
}
