/*
 * $Id: Permission.java, 2011-3-24 上午11:21:34 sufeng Exp $
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

/**
 * <p>
 * Title: Permission
 * </p>
 * <p>
 * Description: 权限项
 * </p>
 * 
 * @author sufeng
 * created 2011-3-24 上午11:21:34
 * modified [who date description]
 * check [who date description]
 */
public class Permission implements Serializable{

    private static final long serialVersionUID = -8456422319918913018L;

    /**
     * 权限项id
     */
    private String permissionId;
    
    /**
     * 大类
     */
    private String category;
    
    /**
     * action/function,对应着客户端的菜单action或者后台的函数
     */
    private List<String> actionFunctions;
    
    /**
     * 描述
     */
    private String description;

    public Permission(){
    }

    public Permission(String category,String permissionId,List<String> actionFunctions){
        this(category,permissionId);
        this.actionFunctions=actionFunctions;
    }
    
    public Permission(String category,String permissionId,String ... actionFnList){
        this.category=category;
        this.permissionId=permissionId;
        if(actionFnList!=null && actionFnList.length>0){
            this.actionFunctions=new ArrayList<String>();
            for(int i=0;i<actionFnList.length;i++)
                this.actionFunctions.add(actionFnList[i]);
        }
    }
    
    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getActionFunctions() {
        return actionFunctions;
    }

    public void setActionFunctions(List<String> actionFnList) {
        this.actionFunctions = actionFnList;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((permissionId == null) ? 0 : permissionId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Permission other = (Permission) obj;
        if (permissionId == null) {
            if (other.permissionId != null)
                return false;
        } else if (!permissionId.equals(other.permissionId))
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        return "category:"+category+",permission="+permissionId;
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
    
    

}
