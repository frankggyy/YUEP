/*
 * $Id: MoPermGroup.java, 2011-4-18 下午04:30:22 WangRui Exp $
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

import org.hibernate.annotations.Type;

import com.yuep.nms.core.common.base.def.DisplayNameGetter;
import com.yuep.nms.core.common.mocore.naming.MoNaming;

/**
 * <p>
 * Title: MoPermGroup
 * </p>
 * <p>
 * Description: mo权限集,存储角色对应的mo下的权限集
 * </p>
 * 
 * @author WangRui
 * created 2011-4-18 下午04:30:22
 * modified [who date description]
 * check [who date description]
 */
@Entity
public class MoPermGroup implements Serializable,DisplayNameGetter {
    private static final long serialVersionUID = 7022658277069622421L;
    
    
    /**
     * mo权限集主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    /**
     * 角色对应的mo的moNaming
     */
    @Type(type="com.yuep.nms.core.common.mocore.usertype.MoNamingUserType")
    private MoNaming moName;
    
    /**
     * 角色名
     */
    private String roleName;
    
    /**
     * mo权限集名称
     */
    private String groupName;
      
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * @return the moName
     */
    public MoNaming getMoName() {
        return moName;
    }
    /**
     * @param moName the moName to set
     */
    public void setMoName(MoNaming moName) {
        this.moName = moName;
    }
    /**
     * @return the roleName
     */
    public String getRoleName() {
        return roleName;
    }
    /**
     * @param roleName the roleName to set
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
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
    
    @Override
    public String getDisplayName() {
        return roleName;
    }
    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((groupName == null) ? 0 : groupName.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((moName == null) ? 0 : moName.hashCode());
        result = prime * result + ((roleName == null) ? 0 : roleName.hashCode());
        return result;
    }
    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MoPermGroup other = (MoPermGroup) obj;
        if (groupName == null) {
            if (other.groupName != null)
                return false;
        } else if (!groupName.equals(other.groupName))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (moName == null) {
            if (other.moName != null)
                return false;
        } else if (!moName.equals(other.moName))
            return false;
        if (roleName == null) {
            if (other.roleName != null)
                return false;
        } else if (!roleName.equals(other.roleName))
            return false;
        return true;
    }
    
    
    
}
