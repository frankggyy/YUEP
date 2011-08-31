/*
 * $Id: SyncNode.java, 2011-5-17 下午05:46:36 yangtao Exp $
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

/**
 * <p>
 * Title: SyncNode
 * </p>
 * <p>
 * Description:
 * 同步接点
 * </p>
 * 
 * @author yangtao
 * created 2011-5-17 下午05:46:36
 * modified [who date description]
 * check [who date description]
 */
public class SyncNode implements Serializable {
    private static final long serialVersionUID = -7630994227158649348L;
    /**同步节点名称*/
    private String name;
    /**同步接点类型*/
    private String nodeType;
    /**同步action名称*/
    private String syncAction;
    /**当前同步节点依赖的同步节点*/
    private List<String> depends;
    /**当前同步节点支持的管理对象类型*/
    private List<String> includes;
    
    private boolean requested;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getNodeType() {
        return nodeType;
    }
    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }
    public String getSyncAction() {
        return syncAction;
    }
    public void setSyncAction(String syncAction) {
        this.syncAction = syncAction;
    }
    public List<String> getDepends() {
        return depends;
    }
    public void setDepends(List<String> depends) {
        this.depends = depends;
    }

    public List<String> getIncludes() {
        return includes;
    }
    public void setIncludes(List<String> includes) {
        this.includes = includes;
    }
    public boolean isRequested() {
        return requested;
    }
    public void setRequested(boolean requested) {
        this.requested = requested;
    }
    /**
     * 
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString(){
        return name;
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
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        SyncNode other = (SyncNode) obj;
        return ObjectUtils.equals(name, other.getName());
    }
    
    
}
