/*
 * $Id: SmCoreManagerService.java, 2011-3-24 上午11:18:38 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.smcore.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yuep.nms.core.common.base.def.annotation.FacadeMethod;
import com.yuep.nms.core.common.base.def.annotation.LogType;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.smcore.model.IpRange;
import com.yuep.nms.core.common.smcore.model.MoPermGroup;
import com.yuep.nms.core.common.smcore.model.Permission;
import com.yuep.nms.core.common.smcore.model.PermissionGroup;
import com.yuep.nms.core.common.smcore.model.Role;
import com.yuep.nms.core.common.smcore.model.User;

/**
 * <p>
 * Title: SmCoreManagerService
 * </p>
 * <p>
 * Description: 用户/角色/权限管理接口
 * </p>
 * 
 * @author sufeng
 * created 2011-3-24 上午11:18:38
 * modified [who date description]
 * check [who date description]
 */
public interface SmCoreManagerService {

    /**
     * 获取用户
     * @param userName
     * @return
     */
    public User getUser(String userName);
    
    /**
     * 获取所有用户
     * @return
     */
    public List<User> getAllUsers();
    
    /**
     * 所有用户的可登陆的IP范围
     * @return
     */
    public List<IpRange> getAllUsersIpRange();
    
    /**
     * 创建用户
     * @param user
     */
    @FacadeMethod(needLog=true,oplogType=LogType.SECURITY)
    public void addUser(User user);
    
    /**
     * 删除用户
     * @param userName
     */
    @FacadeMethod(needLog=true,oplogType=LogType.SECURITY)
    public void deleteUser(User user);
    
    /**
     * 更新用户
     * @param updatedUser
     */
    @FacadeMethod(needLog=true,oplogType=LogType.SECURITY)
    public void updateUser(User updatedUser);
    
  
    /**
     * 激活用户
     * @param user
     */
    @FacadeMethod(needLog=true,oplogType=LogType.SECURITY)
    public void activeUser(String userName);
    
    /**
     * 去激活用户
     * @param user
     */
    @FacadeMethod(needLog=true,oplogType=LogType.SECURITY)
    public void deactiveUser(String userName);
    /**
     * 根据角色名获取角色
     * @param roleName
     * @return
     */
    public Role getRoleByName(String roleName);
    
    /**
     * 获取所有角色
     * @return
     */
    public List<Role> getAllRoles();
    
    /**
     * 创建角色
     * @param role
     */
    @FacadeMethod(needLog=true,oplogType=LogType.SECURITY)
    public void addRole(Role role);
    
    /**
     * 删除角色
     * @param roleName
     */
    @FacadeMethod(needLog=true,oplogType=LogType.SECURITY)
    public void deleteRole(String... roleNames);
    
    /**
     * 更新角色
     * @param updatedRole
     */
    @FacadeMethod(needLog=true,oplogType=LogType.SECURITY)
    public void updateRole(Role updatedRole);
    
    /**
     * 获取所有权限集
     * @return
     */
    public List<PermissionGroup> getAllPermissionGroups();
    
    /**
     * 获取权限集
     * @param permGroupName
     * @return
     */
    @FacadeMethod
    public PermissionGroup getPermissionGroup(String permGroupName);
    
    /**
     * 添加权限集
     * @param permGroup
     */
    @FacadeMethod(needLog=true,oplogType=LogType.SECURITY)
    public void addPermissionGroup(PermissionGroup permGroup);
    
    /**
     * 删除权限集
     * @param permGroupName
     * @return
     */
    @FacadeMethod(needLog=true,oplogType=LogType.SECURITY)
    public Boolean deletePermissionGroup(String permGroupName);
    
    /**
     * 更新权限集
     * @param updatedPermGroup
     */
    @FacadeMethod(needLog=true,oplogType=LogType.SECURITY)
    public void updatePermissionGroup(PermissionGroup updatedPermGroup);
    
    /**
     * 增加权限项 （目前不实现，都采用从XML中读取）
     * @param perm
     */
    @FacadeMethod(needLog=true,oplogType=LogType.SECURITY)
    public void addPermission(Permission perm);
    
    /**
     * 删除权限项（目前不实现）
     * @param permName
     */
    @FacadeMethod(needLog=true,oplogType=LogType.SECURITY)
    public void deletePermission(String permName);
    
    /**
     * 更新权限项（目前不实现）
     * @param updatedPerm
     */
    @FacadeMethod(needLog=true,oplogType=LogType.SECURITY)
    public void updatePermission(Permission updatedPerm);
    
    /**
     * 获取指定用户的mo权限树
     * @param userName
     * @return
     */
    public Map<MoNaming, Set<String>> getPermissionTree(String userName);
    
    /**
     * 得到所有权限项的大类
     * @return
     */
    public List<String> getAllPermissionCategories();
    
    /**
     * 获取权限项大类中的所有权限项
     * @param category
     * @return
     */
    public List<Permission> getPermissions(String category);
    
    /**
     * 根据ActionID或FunctionName来获取权限项信息
     * @param actionOrFn
     * @return
     */
    public Permission getPermission(String actionOrFn);
    
    /**
     * 添加一个角色针对mo下的权限集
     * @param moPerm
     */
    @FacadeMethod(needLog=true,oplogType=LogType.SECURITY)
    public void addMoPerm(MoPermGroup... moPerms);
    
    /**
     * 根据moName,roleName查询某个角色针对一个mo下的权限集
     * @param moName
     * @param roleName
     * @return
     */
    public MoPermGroup getMoPerm(MoNaming moName,String roleName);

    /**
     * 修改一个角色针对mo下的权限集
     * @param moPerm
     */
    @FacadeMethod(needLog=true,oplogType=LogType.SECURITY)
    public void updateMoPerm(String roleName,MoPermGroup... moPerms);
    
    /**
     * 删除一个角色针对mo下的权限集
     * @param moPerm
     */
    @FacadeMethod(needLog=true,oplogType=LogType.SECURITY)
    public void deleteMoPerm(MoPermGroup... moPerms);

}
