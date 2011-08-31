/*
 * $Id: PermissionGroup.java, 2011-3-24 上午11:21:57 sufeng Exp $
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
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

import com.yuep.nms.core.common.base.def.DisplayNameGetter;

/**
 * <p>
 * Title: PermissionGroup
 * </p>
 * <p>
 * Description: 权限集
 * </p>
 * 
 * @author sufeng
 * created 2011-3-24 上午11:21:57
 * modified [who date description]
 * check [who date description]
 */
@Entity
public class PermissionGroup implements Serializable,DisplayNameGetter {

    private static final long serialVersionUID = 1373259681857202492L;

    /**
     * 权限集名
     */
    @Id
    private String groupName;
    
    /**
     * 权限项
     */
    @Type(type="com.yuep.core.db.usertype.ListUserType")
    @Column(columnDefinition = "text", nullable = true)
    private List<String> permissions=new ArrayList<String>();

    /**
     * 描述
     */
    private String description;
    
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getDisplayName() {
        return groupName;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((groupName == null) ? 0 : groupName.hashCode());
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
        PermissionGroup other = (PermissionGroup) obj;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (groupName == null) {
            if (other.groupName != null)
                return false;
        } else if (!groupName.equals(other.groupName))
            return false;
        return true;
    }
    
    
    
    
}
