/*
 * $Id: SubSystemProxyProperty.java, 2011-4-26 下午03:09:35 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.subsystemproxycore;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

import com.yuep.nms.core.common.mocore.naming.MoNaming;

/**
 * <p>
 * Title: SubSystemProxyProperty
 * </p>
 * <p>
 * Description:
 * 子系统基本属性
 * </p>
 * 
 * @author yangtao
 * created 2011-4-26 下午03:09:35
 * modified [who date description]
 * check [who date description]
 */
@Entity
public class SubSystemProxyProperty implements Serializable {
    private static final long serialVersionUID = 2319634411530534532L;

    @Id
    @Type(type="com.yuep.nms.core.common.mocore.usertype.MoNamingUserType")
    /**
     * 子系统Id
     */
    private MoNaming subSystemId;
    /**
     * 子系统名称
     */
    private String subSystemName;
    /**子系统类型*/
    private String subSystemType;
    /**IP地址*/
    private String ip;
    /**端口*/
    private int port;
    /**用户名*/
    private String userName;
    /**密码*/
    private String password;
    
    public MoNaming getSubSystemId() {
        return subSystemId;
    }
    public void setSubSystemId(MoNaming subSystemId) {
        this.subSystemId = subSystemId;
    }
    
    public String getSubSystemType() {
        return subSystemType;
    }
    public void setSubSystemType(String subSystemType) {
        this.subSystemType = subSystemType;
    }

    public String getSubSystemName() {
        return subSystemName;
    }
    public void setSubSystemName(String subSystemName) {
        this.subSystemName = subSystemName;
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ip == null) ? 0 : ip.hashCode());
        result = prime * result + port;
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SubSystemProxyProperty other = (SubSystemProxyProperty) obj;
        if (ip == null) {
            if (other.ip != null) {
                return false;
            }
        } else if (!ip.equals(other.ip)) {
            return false;
        }
        if (port != other.port) {
            return false;
        }
        return true;
    }
    
    
    
}
