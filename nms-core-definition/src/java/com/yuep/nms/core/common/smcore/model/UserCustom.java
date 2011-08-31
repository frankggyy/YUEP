/*
 * $Id: UserCustom.java, 2011-3-24 上午11:29:13 sufeng Exp $
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
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

import com.yuep.nms.core.common.base.def.DisplayNameGetter;

/**
 * <p>
 * Title: UserCustom
 * </p>
 * <p>
 * Description:用户个性化信息，键值对方式存储
 * </p>
 * 
 * @author sufeng
 * created 2011-3-24 上午11:29:13
 * modified [who date description]
 * check [who date description]
 */
@Entity
public class UserCustom implements Serializable,DisplayNameGetter{

    private static final long serialVersionUID = -7377858933792372868L;
    
    /**
     * 用户名
     */
    @Id
    private String userName;
    
    /**
     * 该用户的个性化信息，键值对
     */
    @Column(name = "customInfo", columnDefinition = "text")
    @Type(type = "com.yuep.core.db.usertype.MapUserType")
    private Map<String,String> customInfo;

    /**
     * 用户
     * @return
     */
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    /**
     * 个性化信息
     * @return
     */
    public Map<String, String> getCustomInfo() {
        return customInfo;
    }

    public void setCustomInfo(Map<String, String> customInfo) {
        this.customInfo = customInfo;
    }
    
    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append(userName);
        if(customInfo!=null && customInfo.size()>0){
            for(Map.Entry<String, String> entry : customInfo.entrySet()){
                sb.append(",").append(entry.getKey()).append("=").append(entry.getValue());
            }
        }
        return sb.toString();
    }

    @Override
    public String getDisplayName() {
        return userName;
    }
    
}
