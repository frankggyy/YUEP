/*
 * $Id: ManagedNodeProperty.java, 2011-3-28 上午11:18:20 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.mocore.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

import com.yuep.nms.core.common.mocore.naming.MoNaming;

/**
 * <p>
 * Title: ManagedNodeProperty
 * </p>
 * <p>
 * Description:
 * 管理节点连接属性
 * </p>
 * 
 * @author yangtao
 * created 2011-3-28 上午11:18:20
 * modified [who date description]
 * check [who date description]
 */
@Entity
public class ManagedNodeProperty implements Serializable {
    private static final long serialVersionUID = -910978949590867434L;
    @Id
    @Type(type="com.yuep.nms.core.common.mocore.usertype.MoNamingUserType")
    /**管理节点标识*/
    private MoNaming managedNode;
    /**IP地址*/
    private String ip;
    /**端口*/
    private int port;
    /**用户名*/
    private String useName;
    /**密码*/
    private String password;
  
    public MoNaming getManagedNode() {
        return managedNode;
    }
    public void setManagedNode(MoNaming managedNode) {
        this.managedNode = managedNode;
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

    public String getUseName() {
        return useName;
    }
    public void setUseName(String useName) {
        this.useName = useName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * 
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append("ip");
        sb.append(":");
        sb.append(ip);
        sb.append("\n");
        sb.append("port");
        sb.append(":");
        sb.append(port);
        sb.append("\n");
        sb.append("useName");
        sb.append(":");
        sb.append(useName);
        sb.append("\n");
        sb.append("password");
        sb.append(":");
        sb.append(password);
        sb.append("\n");
        return sb.toString();
        
    }
    

}
