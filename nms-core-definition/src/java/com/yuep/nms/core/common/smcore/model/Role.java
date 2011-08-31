/*
 * $Id: Role.java, 2011-3-24 上午11:20:35 sufeng Exp $
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.yuep.nms.core.common.base.def.DisplayNameGetter;
import com.yuep.nms.core.common.base.def.ValueCompareObject;
import com.yuep.nms.core.common.base.def.ValueCompareObjectMapGetter;
import com.yuep.nms.core.common.mocore.naming.MoNaming;

/**
 * <p>
 * Title: Role
 * </p>
 * <p>
 * Description: 角色
 * </p>
 * 
 * @author sufeng
 * created 2011-3-24 上午11:20:35
 * modified [who date description]
 * check [who date description]
 */
@Entity
public class Role implements Serializable,ValueCompareObjectMapGetter<Role>,DisplayNameGetter{

    private static final long serialVersionUID = -5299069293943853768L;

    /**
     * 角色名
     */
    @Id
    private String roleName;
    
    /**
     * 描述
     */
    private String description;
    
    /**
     * 角色可管理的mo
     */
    @Column(name = "mo", columnDefinition = "text")
    @Type(type = "com.yuep.nms.core.common.mocore.usertype.CommonListUserType", parameters = {
            @Parameter(name = "userType", value = "com.yuep.nms.core.common.mocore.usertype.MoNamingListUserType"),
            @Parameter(name = "elementClass", value = "com.yuep.nms.core.common.mocore.naming.MoNaming") })
    private List<MoNaming> moList;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the moList
     */
    public List<MoNaming> getMoList() {
        return moList;
    }

    /**
     * @param moList the moList to set
     */
    public void setMoList(List<MoNaming> moList) {
        this.moList = moList;
    }

    /**
     * @see com.yuep.nms.core.common.base.ValueCompareObjectMapGetter#getValueCompareObjectMap(java.lang.Object)
     */
    @Override
    public Map<String, ValueCompareObject> getValueCompareObjectMap(Role otherObject) {
        Map<String,ValueCompareObject> map=new HashMap<String, ValueCompareObject>();
        if(otherObject==null)
            return map;
        if(!StringUtils.equals(roleName, otherObject.getRoleName())){
           map.put("roleName", new ValueCompareObject("roleName",roleName,otherObject.getRoleName())); 
        }
        return map;
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
        Role other = (Role) obj;
        if (roleName == null) {
            if (other.roleName != null)
                return false;
        } else if (!roleName.equals(other.roleName))
            return false;
        return true;
    }
    
    
      
}
