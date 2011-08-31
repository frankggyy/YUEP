/*
 * $Id: IpRange.java, 2011-4-22 下午03:20:23 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.smcore.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.yuep.base.util.format.WatchUtil;

/**
 * <p>
 * Title: IpRange
 * </p>
 * <p>
 * Description: 用于限定用户登陆的ip段范围
 * </p>
 * 
 * @author sufeng
 * created 2011-4-22 下午03:20:23
 * modified [who date description]
 * check [who date description]
 */
@Entity
public class IpRange implements Serializable {
 
    private static final long serialVersionUID = 4728751411063389171L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 起始IP地址
     */
    private String startIpAddress;

    /**
     * 结束IP地址
     */
    private String endIpAddress;

    public String getStartIpAddress() {
        return startIpAddress;
    }

    public void setStartIpAddress(String startIpAddress) {
        this.startIpAddress = startIpAddress;
    }

    public String getEndIpAddress() {
        return endIpAddress;
    }

    public void setEndIpAddress(String endIpAddress) {
        this.endIpAddress = endIpAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * ip是否在范围内
     * @param ipAddr
     * @return
     */
    public boolean isInRange(String ipAddr){
        long ip = WatchUtil.getAddrLong(ipAddr);
        return isInRange(ip);
    }
    
    /**
     * ip是否在范围内
     * @param ip
     * @return
     */
    public boolean isInRange(long ip){
        long ipStart = WatchUtil.getAddrLong(startIpAddress);
        if(ip<ipStart)
            return false;
        long ipEnd= WatchUtil.getAddrLong(endIpAddress);
        return ip<=ipEnd;
    }
    

}
