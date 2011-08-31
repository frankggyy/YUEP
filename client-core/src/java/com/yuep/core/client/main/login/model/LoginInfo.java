/*
 * $Id: LoginInfo.java, 2009-3-7 ����12:15:07 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.main.login.model;

import java.io.Serializable;


/**
 * <p>
 * Title: LoginInfo
 * </p>
 * <p>
 * Description:�ͻ��˵�¼��Ϣ����
 * </p>
 * 
 * @author aaron lee
 * created 2009-3-7 ����12:15:07
 * modified [who date description]
 * check [who date description]
 */
public class LoginInfo implements Serializable{
    
    private static final long serialVersionUID = 5765076995527430928L;

    public final static String loginInfoFile = "login.properties";
    
    public final static String USERNAME = "userName";

    public final static String SERVER_IP = "serverIp";

    public final static String PORT = "serverPort";
    
    /** �����ip */
    private String server;
    
    /** �����port */
    private String port;
    
    /** �û��� */
    private String user;
    
    /** ���� */
    private String password;
    
    /** �û�ģʽ */
    private String userModel="MultiUserMode";

    /**
     * @return the server
     */
    public String getServer() {
        return server;
    }

    /**
     * @param server the server to set
     */
    public void setServer(String server) {
        this.server = server;
    }

    /**
     * @return the port
     */
    public String getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the userModel
     */
    public String getUserModel() {
        return userModel; //MultiUserMode, SingleUserMode
    }

    /**
     * @param userModel the userModel to set
     */
    public void setUserModel(String userModel) {
        this.userModel = userModel;
    }
    
    // TODO ��Ҫ�ع�
    private Long sessionId;
    public Long getSessionId() {
        return sessionId;
    }
    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }
    
}
