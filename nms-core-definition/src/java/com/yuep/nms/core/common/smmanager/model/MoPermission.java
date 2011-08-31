/*
 * $Id: MoPermission.java, 2011-4-19 ����01:18:58 WangRui Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.smmanager.model;

import java.io.Serializable;

import com.yuep.nms.core.common.mocore.model.Mo;

/**
 * <p>
 * Title: MoPermission
 * </p>
 * <p>
 * Description:����ڵ�Ȩ�޼�,��Ӧ���ɫ������Ȩ�޼����õ������е�һ��
 * </p>
 * 
 * @author WangRui
 * created 2011-4-19 ����01:18:58
 * modified [who date description]
 * check [who date description]
 */
public class MoPermission implements Serializable{
    private static final long serialVersionUID = -8408599953497558188L;
    /** ���ܶ���mo*/
    private Mo mo;
    /**���ܶ����Ӧ��Ȩ�޼���*/
    private String groupName;
    /**
     * @return the mo
     */
    public Mo getMo() {
        return mo;
    }
    /**
     * @param mo the mo to set
     */
    public void setMo(Mo mo) {
        this.mo = mo;
    }
    /**
     * @return the groupName
     */
    public String getGroupName() {
        return groupName;
    }
    /**
     * @param groupName the groupName to set
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    
    
    
}
