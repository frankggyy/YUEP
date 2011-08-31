/*
 * $Id: UserCustom.java, 2011-3-24 ����11:29:13 sufeng Exp $
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
 * Description:�û����Ի���Ϣ����ֵ�Է�ʽ�洢
 * </p>
 * 
 * @author sufeng
 * created 2011-3-24 ����11:29:13
 * modified [who date description]
 * check [who date description]
 */
@Entity
public class UserCustom implements Serializable,DisplayNameGetter{

    private static final long serialVersionUID = -7377858933792372868L;
    
    /**
     * �û���
     */
    @Id
    private String userName;
    
    /**
     * ���û��ĸ��Ի���Ϣ����ֵ��
     */
    @Column(name = "customInfo", columnDefinition = "text")
    @Type(type = "com.yuep.core.db.usertype.MapUserType")
    private Map<String,String> customInfo;

    /**
     * �û�
     * @return
     */
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    /**
     * ���Ի���Ϣ
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
