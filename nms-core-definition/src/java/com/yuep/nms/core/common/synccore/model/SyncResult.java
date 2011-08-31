/*
 * $Id: SyncResult.java, 2009-1-9 上午10:59:32 yangtao Exp $
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

import com.yuep.base.exception.YuepException;
import com.yuep.nms.core.common.synccore.model.enums.Result;


/**
 * <p>
 * Title: SyncResult
 * </p>
 * <p>
 * Description:同步结果记录
 * </p>
 * 
 * @author yangtao
 * created 2009-1-9 上午10:59:32
 * modified [who date description]
 * check [who date description]
 */
public class SyncResult implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -7671307955414372589L;
    /** 同步任务的同步项 */
    private SyncItem syncItem;
    /** 同步结果 */
    private Result result;
    /** 如果同步失败，同步失败的原因： */
    private String failCause;
    /** sufeng:如果同步失败，同步失败的yotc异常： */
    private YuepException failException;

    /**
     * 
     * @param taskId
     * @param source
     * @param target
     * @param result
     */
    public SyncResult(SyncItem syncItem, Result result) {
        this.syncItem = syncItem;
        this.result = result;
    }

    public SyncItem getSyncItem() {
        return syncItem;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getFailCause() {
        return failCause;
    }

    public void setFailCause(String failCause) {
        this.failCause = failCause;
    }
    


    public YuepException getFailException() {
        return failException;
    }

    public void setFailException(YuepException failException) {
        this.failException = failException;
    }

    /**
     * 
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((failCause == null) ? 0 : failCause.hashCode());
        result = prime * result
                + ((this.result == null) ? 0 : this.result.hashCode());
        result = prime * result
                + ((syncItem == null) ? 0 : syncItem.hashCode());
        return result;
    }

    /**
     * 
     * (non-Javadoc)
     * 
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
        SyncResult other = (SyncResult) obj;
        return ObjectUtils.equals(this.getSyncItem(), other.getSyncItem())
                && ObjectUtils.equals(this.getResult(), other.getResult())
                && ObjectUtils
                        .equals(this.getFailCause(), other.getFailCause());
    }

    /**
     * 
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Synchonized Item=");
        sb.append(syncItem);
        sb.append("\n");
        sb.append("Result=");
        sb.append(result);
        sb.append("\n");
        if (result == Result.failure) {
            sb.append("FailCause=");
            sb.append(failCause);
        }
        return sb.toString();
    }

}
