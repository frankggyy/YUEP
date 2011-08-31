/*
 * $Id: PermissionManager.java, 2011-4-14 下午03:45:22 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.smcore.perm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import com.yuep.nms.core.common.smcore.model.Permission;

/**
 * <p>
 * Title: PermissionManager
 * </p>
 * <p>
 * Description: 权限项的管理
 * </p>
 * 
 * @author sufeng
 * created 2011-4-14 下午03:45:22
 * modified [who date description]
 * check [who date description]
 */
public class PermissionManager {

    private Set<String> categories=new HashSet<String>();
    private Map<String,List<Permission>> permissionCategory=new HashMap<String, List<Permission>>();
    private Map<String,String> actions=new HashMap<String, String>();
    private Map<String,Permission> permissions=new HashMap<String, Permission>();
    
    private PermissionLoader permissionLoader;
    public void setPermissionLoader(PermissionLoader permissionLoader) {
        this.permissionLoader = permissionLoader;
    }
    
    /**
     * 初始化：加载权限项
     */
    public void init(){
        List<Permission> permList = permissionLoader.readPermissionFromXml();
        for(Permission perm : permList){
            categories.add(perm.getCategory());
            permissions.put(perm.getPermissionId(),perm);
            
            // build category,permission list map
            List<Permission> permsInCategory = permissionCategory.get(perm.getCategory());
            if(permsInCategory==null){
                permsInCategory=new ArrayList<Permission>();
                permissionCategory.put(perm.getCategory(), permsInCategory);
            }
            permsInCategory.add(perm);
            
            // build action map
            List<String> actionFns = perm.getActionFunctions();
            if(CollectionUtils.isNotEmpty(actionFns)){
                for(String action : actionFns)
                    actions.put(action, perm.getPermissionId());
            }
        }
    }
    
    /**
     * 权限项大类
     * @return
     */
    public Set<String> getCategories() {
        return categories;
    }
    
    /**
     * 获取权限项的大类包含的权限项
     * @param category
     * @return
     */
    public List<Permission> getPermissionByCategory(String category){
        return permissionCategory.get(category);
    }
    
    /**
     * 根据action id或function获取对应的权限项
     * @param actionOrFn
     * @return
     */
    public Permission getPermission(String actionOrFn){
        String permName = actions.get(actionOrFn);
        if(permName==null)
            return null;
        return permissions.get(permName);
    }
    
}
