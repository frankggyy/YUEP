/*
 * $Id: Mo.java, 2011-3-24 下午04:28:09 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.mocore.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.apache.commons.lang.ObjectUtils;
import org.hibernate.annotations.Type;

import com.yuep.nms.core.common.mocore.enums.SyncState;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.mocore.naming.MoNamingGetter;

/**
 * <p>
 * Title: Mo
 * </p>
 * <p>
 * Description:
 * 管理对象定义
 * </p>
 * 
 * @author yangtao
 * created 2011-3-24 下午04:28:09
 * modified [who date description]
 * check [who date description]
 */
@Entity
final public class Mo implements Serializable,MoNamingGetter {
   
    private static final long serialVersionUID = 6473060999018115540L;
    
    /**管理对象标识*/
    @Id
    @Type(type="com.yuep.nms.core.common.mocore.usertype.MoNamingUserType")
    private MoNaming moNaming;
    
    /**管理对象父节点标识*/
    @Type(type="com.yuep.nms.core.common.mocore.usertype.MoNamingUserType")
    private MoNaming parent;
    
    /**
     * 管理对象详细类型
     */
    private String type;
    
    /**
     * 管理对象实际类型
     */
    private String actualtype;
    
    /**
     * 同步状态
     */
    @Transient
    private SyncState syncstate=SyncState.UNKNOWN;
    
    /**
     * 连接状态
     */
    private boolean linkstate=false;
    
    /**
     * 显示名称
     */
    private String displayName;
    
    public Mo(){
    }
    
    public Mo(Mo mo){
        this.moNaming=mo.getMoNaming();
        this.parent=mo.getParent();
        this.actualtype=mo.getActualtype();
        this.displayName=mo.getDisplayName();
        this.type=mo.getType();
        this.linkstate=mo.isLinkstate();
        this.syncstate=mo.getSyncstate();
    }
    
    @Override
    public MoNaming getMoNaming() {
        return moNaming;
    }
    
    public void setMoNaming(MoNaming moNaming) {
        this.moNaming = moNaming;
    }
    public MoNaming getParent() {
        return parent;
    }
    public void setParent(MoNaming parent) {
        this.parent = parent;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getActualtype() {
        return actualtype;
    }
    public void setActualtype(String actualtype) {
        this.actualtype = actualtype;
    }
  
    public SyncState getSyncstate() {
        return syncstate;
    }
    public void setSyncstate(SyncState syncstate) {
        this.syncstate = syncstate;
    }
    public boolean isLinkstate() {
        return linkstate;
    }
    public void setLinkstate(boolean linkstate) {
        this.linkstate = linkstate;
    }
    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    
    @Override
    public String toString(){
        return this.displayName;
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
                + ((moNaming == null) ? 0 : moNaming.hashCode());
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
        Mo other = (Mo) obj;
        return ObjectUtils.equals(this.getMoNaming(), other.getMoNaming());
    }
   

    
    

}
