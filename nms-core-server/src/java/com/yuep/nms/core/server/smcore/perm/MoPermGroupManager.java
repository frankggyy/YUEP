/*
 * $Id: MoPermGroupManager.java, 2011-4-18 下午05:30:47 WangRui Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.smcore.perm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.yuep.base.util.format.YuepObjectUtils;
import com.yuep.core.db.access.basedao.BaseDao;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.smcore.model.MoPermGroup;
import com.yuep.nms.core.common.smcore.model.User;
import com.yuep.nms.core.server.smcore.SmCoreContext;
import com.yuep.nms.core.server.smcore.role.RoleManager;

/**
 * <p>
 * Title: MoPermGroupManager
 * </p>
 * <p>
 * Description:角色对应mo下的权限集管理类
 * </p>
 * 
 * @author WangRui
 * created 2011-4-18 下午05:30:47
 * modified [who date description]
 * check [who date description]
 */
public class MoPermGroupManager {
    
    private BaseDao<MoPermGroup> moPermDao;
    public void setMoPermDao(BaseDao<MoPermGroup> moPermDao) {
        this.moPermDao = moPermDao;
    }

    /**
     * 给mo授权
     * @param moPerms
     */
    public void addMoPerm(MoPermGroup... moPerms){
        if(moPerms==null || moPerms.length==0)
            return;
        moPermDao.saveBatch(moPerms);
    }

    /**
     * 得到角色在某个mo上的权限设置信息
     * @param moName
     * @param roleName
     * @return
     */
    public MoPermGroup getMoPerm(MoNaming moName,String roleName){
        MoPermGroup moPerm = moPermDao.getUniqueEntityByPropNames(new String[] { "moName", "roleName" },
                new Object[] { moName, roleName });
        return moPerm;
    }
    
    /**
     * 为角色设置每个mo的权限集
     * @param roleName
     * @param moPerms
     */
    public void updateMoPerm(String roleName,MoPermGroup... moPerms){
        List<MoPermGroup> moPermsInDb = getMoPermGroupByRole(roleName);
        List<MoPermGroup> moPermsUpdated = YuepObjectUtils.newArrayList(moPerms);
        boolean isSame=YuepObjectUtils.collectionEquals(moPermsInDb, moPermsUpdated);
        if(isSame)
            return;
        
        moPermDao.updateBatch(moPerms);
        // 添加role,删除role都不会触发用户权限cache的变化
        // 但更新role时,有可能会触发用户权限cache的变化
        // 修改role时,修改了role的mo权限集,则需要更新引用该role的user的权限cache
        rolePermissionChanged(roleName);
    }
    
    /**
     * role的权限变化了,需要更新cache
     * @param roleName
     */
    public void rolePermissionChanged(String roleName){
        RoleManager roleManager=SmCoreContext.getBean("roleManager",RoleManager.class);
        List<User> users = roleManager.getUsersByRole(roleName);
        UserPermissionTree userPermissionTree=SmCoreContext.getBean("userPermissionTree", UserPermissionTree.class);
        for(User user : users)
            userPermissionTree.userRolePermissionChanged(user);
    }
    
    /**
     * 删除mo的permisssion
     * @param moPerms
     */
    public void deleteMoPerm(MoPermGroup... moPerms){
        moPermDao.deleteAllEntities(moPerms);
    }
    
    /**
     * 得到指定role的mo权限集
     * @param roleName
     * @return
     */
    public List<MoPermGroup> getMoPermGroupByRole(String roleName){
        List<MoPermGroup> entities = moPermDao.getEntitiesByOneProperty("roleName", roleName);
        return entities;
    }
    
    /**
     * 得到permission group被哪些role引用了
     * @param roleName
     * @return
     */
    public Set<String> getAppliedRolesByPermissionGroup(String permissionGroup){
        List<MoPermGroup> entities = moPermDao.getEntitiesByOneProperty("groupName", permissionGroup);
        Set<String> roles=new HashSet<String>();
        for(MoPermGroup group : entities){
            if(group.getRoleName()!=null)
                roles.add(group.getRoleName());
        }
        return roles;
    }
    
    /**
     * 所有mo的权限设置信息
     * @return
     */
    public List<MoPermGroup> getAllMoPermGroup(){
        return moPermDao.getAllEntities();
    }

}
