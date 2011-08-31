/*
 * $Id: OperationLog.java, 2011-3-24 ����11:31:27 sufeng Exp $
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

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * Title: OperationLog
 * </p>
 * <p>
 * Description: ������־
 * </p>
 * 
 * @author sufeng
 * created 2011-3-24 ����11:31:27
 * modified [who date description]
 * check [who date description]
 */
@Entity
public class OperationLog implements Serializable{

    private static final long serialVersionUID = -5345780551772009527L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    /**
     * ��������
     */
    private Integer category=LOG_CATALOG_OPERATION;
    
    /**
     * �û���
     */
    private String userName;
    
    /**
     * �ͻ��˵�¼��ip
     */
    private String ip;

    /** �������ͣ�����Ȩ�޶�Ӧ���Ǹ��������� */
    private String operateName;
    
    /**
     * ��������
     */
    private String source;
    
    /**
     * ����ʱ��
     */
    private Long operateTime;
    
    /**
     * �������
     */
    private Integer operateResult=LOG_RESULT_SUCCESSED; 
    
    /**
     * ������Ϣ
     */
    private String detailInfo;

    public static final int LOG_RESULT_SUCCESSED = 0;
    public static final int LOG_RESULT_FAILED = 1;
    public static final int LOG_RESULT_EXCEPTION = 2;
    
    public static final int LOG_CATALOG_OPERATION = 0;
    public static final int LOG_CATALOG_SYS = 1;
    public static final int LOG_CATALOG_SECURITY = 2;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Long operateTime) {
        this.operateTime = operateTime;
    }

    public Integer getOperateResult() {
        return operateResult;
    }

    public void setOperateResult(Integer operateResult) {
        this.operateResult = operateResult;
    }

    public String getDetailInfo() {
        return detailInfo;
    }

    public void setDetailInfo(String detailInfo) {
        this.detailInfo = detailInfo;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public String getOperateName() {
        return operateName;
    }
    
    public void setOperateName(String operateName) {
        this.operateName = operateName;
    }
    
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
    
}
