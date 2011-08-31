/*
 * $Id: OperationLogCondition.java, 2011-3-24 下午01:18:12 sufeng Exp $
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
import java.util.List;

/**
 * <p>
 * Title: OperationLogCondition
 * </p>
 * <p>
 * Description: 日志查询条件
 * </p>
 * 
 * @author sufeng
 * created 2011-3-24 下午01:18:12
 * modified [who date description]
 * check [who date description]
 */
public class OperationLogCondition implements Serializable {

    private static final long serialVersionUID = -1163820207861161576L;
    
    /**
     * 用户名
     */
    private String userName;
    
    /**
     * 客户端登录ip
     */
    private String ip;
    
    /*** 日志大类，可以为null */ 
    private Integer category;
    
    /** 操作名，可以为null */
    private List<String> operateNames;
    
    /**
     * 开始时间
     */
    private Long startTime;
    
    /**
     * 结束时间
     */
    private Long endTime;

    /** 日志对象 */
    private List<String> objects;
    
    /**
     * 操作结果
     */
    private Integer result;

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public List<String> getOperateNames() {
        return operateNames;
    }

    public void setOperateNames(List<String> operateNames) {
        this.operateNames = operateNames;
    }

    public List<String> getObjects() {
        return objects;
    }

    public void setObjects(List<String> objects) {
        this.objects = objects;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }


}
