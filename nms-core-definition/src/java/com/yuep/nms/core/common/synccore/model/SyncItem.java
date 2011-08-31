/*
 * $Id: SyncItem.java, 2011-5-17 下午05:57:42 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.synccore.model;

import java.io.Serializable;

import org.apache.commons.lang.ObjectUtils;

import com.yuep.nms.core.common.mocore.naming.MoNaming;

/**
 * <p>
 * Title: SyncItem
 * </p>
 * <p>
 * Description:
 * 同步任务子项
 * </p>
 * 
 * @author yangtao
 * created 2011-5-17 下午05:57:42
 * modified [who date description]
 * check [who date description]
 */
public class SyncItem implements Serializable {
    private static final long serialVersionUID = -5316641520949788557L;
    /** 同步任务Id */
    private Long taskId;
    /** 同步节点 */
    private String syncNode;
    /** 同步节点类型 */
    private String syncNodeType;
    /** 同步对象对应的同步类spring bean名称 */
    private String syncAction;
    /**是否为必需的同步项*/
    private boolean requested;
    /** 同步对象 */
    private MoNaming target;
    public Long getTaskId() {
        return taskId;
    }
    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
    public String getSyncNode() {
        return syncNode;
    }
    public void setSyncNode(String syncNode) {
        this.syncNode = syncNode;
    }
    public String getSyncNodeType() {
        return syncNodeType;
    }
    public void setSyncNodeType(String syncNodeType) {
        this.syncNodeType = syncNodeType;
    }
    public String getSyncAction() {
        return syncAction;
    }
    public void setSyncAction(String syncAction) {
        this.syncAction = syncAction;
    }
    public boolean isRequested() {
        return requested;
    }
    public void setRequested(boolean requested) {
        this.requested = requested;
    }
    public MoNaming getTarget() {
        return target;
    }
    public void setTarget(MoNaming target) {
        this.target = target;
    }
    /**
     * 
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((syncNode == null) ? 0 : syncNode.hashCode());
        result = prime * result + ((taskId == null) ? 0 : taskId.hashCode());
        return result;
    }
    /**
     * 
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SyncItem other = (SyncItem) obj;
        
        if(!ObjectUtils.equals(taskId, other.getTaskId()))
            return false;
        
        if(!ObjectUtils.equals(syncNode, other.getSyncNode()))
            return false;
        
        return true;
    }
    
    
    
    
}
