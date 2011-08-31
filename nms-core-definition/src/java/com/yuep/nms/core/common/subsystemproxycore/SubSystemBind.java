/*
 * $Id: SubSystemBinds.java, 2011-5-16 下午05:59:30 yangtao Exp $
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
 * Title: SubSystemBinds
 * </p>
 * <p>
 * Description:
 * 子系统subSystemId和管理节点Mo的绑定信息
 * </p>
 * 
 * @author yangtao
 * created 2011-5-16 下午05:59:30
 * modified [who date description]
 * check [who date description]
 */
@Entity
public class SubSystemBind implements Serializable {
    private static final long serialVersionUID = -6671654201647841793L;
    
    @Id
    @Type(type="com.yuep.nms.core.common.mocore.usertype.MoNamingUserType")
    private MoNaming managedNode;
    
    @Type(type="com.yuep.nms.core.common.mocore.usertype.MoNamingUserType")
    private MoNaming subSystemId;
   
    public MoNaming getSubSystemId() {
        return subSystemId;
    }
    public void setSubSystemId(MoNaming subSystemId) {
        this.subSystemId = subSystemId;
    }
    public MoNaming getManagedNode() {
        return managedNode;
    }
    public void setManagedNode(MoNaming managedNode) {
        this.managedNode = managedNode;
    }
 
    
    
    
}
