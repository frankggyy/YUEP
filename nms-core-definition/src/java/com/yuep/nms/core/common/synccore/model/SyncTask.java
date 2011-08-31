/*
 * $Id: SyncTask.java, 2011-5-17 下午05:46:04 yangtao Exp $
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
import java.util.List;

import org.apache.commons.lang.ObjectUtils;

import com.yuep.nms.core.common.mocore.naming.MoNaming;

/**
 * <p>
 * Title: SyncTask
 * </p>
 * <p>
 * Description:
 * 同步任务
 * </p>
 * 
 * @author yangtao
 * created 2011-5-17 下午05:46:04
 * modified [who date description]
 * check [who date description]
 */
public class SyncTask implements Serializable {
    private static final long serialVersionUID = 1806776195661894274L;
    /**同步任务Id*/
    private Long taskId;
    /**同步对象*/
    private MoNaming target;
    /**同步子项*/
    private List<SyncItem> syncItems;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public MoNaming getTarget() {
        return target;
    }

    public void setTarget(MoNaming target) {
        this.target = target;
    }

    public List<SyncItem> getSyncItems() {
        return syncItems;
    }

    public void setSyncItems(List<SyncItem> syncItems) {
        this.syncItems = syncItems;
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
        result = prime * result + ((taskId == null) ? 0 : taskId.hashCode());
        result = prime * result + ((target == null) ? 0 : target.hashCode());
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
        SyncTask other = (SyncTask) obj;
        if(!ObjectUtils.equals(taskId, other.getTaskId()))
            return false;
        if(!ObjectUtils.equals(target, other.getTarget()))
            return false;
        return true;
    }
    
    
    
}
