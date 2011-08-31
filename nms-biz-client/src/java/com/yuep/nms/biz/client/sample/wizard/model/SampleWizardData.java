/*
 * $Id: SampleWizardData.java, 2010-3-31 ÉÏÎç10:31:37 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.biz.client.sample.wizard.model;

import com.yuep.core.client.component.window.MessageDialog.MessageType;


/**
 * <p>
 * Title: SampleWizardData
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron lee
 * created 2010-3-31 ÉÏÎç10:31:37
 * modified [who date description]
 * check [who date description]
 */
public class SampleWizardData {
    
    private long id;
    
    private String str1;
    
    private int integer1;
    
    private boolean b1;
    
    private MessageType messageType;
    
    private String ip;
    
    private String mac;
    
    private String password;

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the str1
     */
    public String getStr1() {
        return str1;
    }

    /**
     * @param str1 the str1 to set
     */
    public void setStr1(String str1) {
        this.str1 = str1;
    }

    /**
     * @return the integer1
     */
    public int getInteger1() {
        return integer1;
    }

    /**
     * @param integer1 the integer1 to set
     */
    public void setInteger1(int integer1) {
        this.integer1 = integer1;
    }

    /**
     * @return the b1
     */
    public boolean isB1() {
        return b1;
    }

    /**
     * @param b1 the b1 to set
     */
    public void setB1(boolean b1) {
        this.b1 = b1;
    }
    
    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the mac
     */
    public String getMac() {
        return mac;
    }

    /**
     * @param mac the mac to set
     */
    public void setMac(String mac) {
        this.mac = mac;
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
     * @return the messageType
     */
    public MessageType getMessageType() {
        return messageType;
    }

    /**
     * @param messageType the messageType to set
     */
    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

}
