/*
 * $Id: SmCoreServiceImpl.java, 2011-3-24 下午01:26:27 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.smcore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yuep.nms.core.common.mocore.module.constants.MoCoreModuleConstants;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.mocore.service.MoCore;
import com.yuep.nms.core.common.smcore.model.IpRange;
import com.yuep.nms.core.common.smcore.model.MoPermGroup;
import com.yuep.nms.core.common.smcore.model.OperationLog;
import com.yuep.nms.core.common.smcore.model.OperationLogCondition;
import com.yuep.nms.core.common.smcore.model.Permission;
import com.yuep.nms.core.common.smcore.model.PermissionGroup;
import com.yuep.nms.core.common.smcore.model.Role;
import com.yuep.nms.core.common.smcore.model.User;
import com.yuep.nms.core.common.smcore.model.UserCustom;
import com.yuep.nms.core.common.smcore.service.SmCoreService;
import com.yuep.nms.core.server.smcore.auth.UserManager;
import com.yuep.nms.core.server.smcore.oplog.OpLogManager;
import com.yuep.nms.core.server.smcore.perm.MoPermGroupManager;
import com.yuep.nms.core.server.smcore.perm.PermGroupManager;
import com.yuep.nms.core.server.smcore.perm.PermissionManager;
import com.yuep.nms.core.server.smcore.perm.UserPermissionTree;
import com.yuep.nms.core.server.smcore.role.RoleManager;

/**
 * <p>
 * Title: SmCoreServiceImpl
 * </p>
 * <p>
 * Description: sm core service的实现类
 * </p>
 * 
 * @author sufeng
 * created 2011-3-24 下午01:26:27
 * modified [who date description]
 * check [who date description]
 */
public class SmCoreServiceImpl implements SmCoreService {

    /**
     * 用户管理
     */
    private UserManager userManager;
    
    /**
     * 日志管理
     */
    private OpLogManager opLogManager;
    
    /**
     * 角色管理
     */
    private RoleManager roleManager;
    
    /**
     * 权限管理
     */
    private PermissionManager permissionManager;
    
    /**
     * 权限组
     */
    private PermGroupManager permGroupManager;
    
    /**
     * 角色的mo权限设置
     */
    private MoPermGroupManager moPermGroupManager;
    
    /**
     * 用户权限cache
     */
    private UserPermissionTree userPermissionTree;

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public void setOpLogManager(OpLogManager opLogManager) {
        this.opLogManager = opLogManager;
    }

    public void setRoleManager(RoleManager roleManager) {
        this.roleManager = roleManager;
    }

    public void setPermissionManager(PermissionManager permissionManager) {
        this.permissionManager = permissionManager;
    }

    public void setPermGroupManager(PermGroupManager permGroupManager) {
        this.permGroupManager = permGroupManager;
    }

    public void setUserPermissionTree(UserPermissionTree userPermissionTree) {
        this.userPermissionTree = userPermissionTree;
    }

    /**
     * @param moPermGroupManager
     *            the moPermGroupManager to set
     */
    public void setMoPermGroupManager(MoPermGroupManager moPermGroupManager) {
        this.moPermGroupManager = moPermGroupManager;
    }

    /**
     * 服务初始化
     */
    public void init() {
        userManager.init();
        permissionManager.init();

        // 加一些内置的数据
        initDefaultRoleData();
        initDefaultPermissionGroupData();
    }

    /**
     * 缺省角色
     */
    private void initDefaultRoleData() {
        // init role data,模拟代码,请补充完善
        MoCore moCore = SmCoreContext.getLocalService(MoCoreModuleConstants.MOCORE_LOCAL_SERVICE, MoCore.class);
        MoNaming rootMo = moCore.getRootMo().getMoNaming();
        List<Role> roles = roleManager.getAllRoles();
        boolean hasAdminRole = false;
        for (Role role : roles) {
            if ("admin".equals(role.getRoleName())) {
                hasAdminRole = true;
                break;
            }
        }
        if (!hasAdminRole) {
            Role adminRole = new Role();
            adminRole.setRoleName("admin");
            List<MoNaming> moList = new ArrayList<MoNaming>();
            moList.add(rootMo);
            adminRole.setMoList(moList);
            roleManager.addRole(adminRole);

            MoPermGroup moPermGroup = new MoPermGroup();
            moPermGroup.setGroupName("all");
            moPermGroup.setMoName(rootMo);
            moPermGroup.setRoleName(adminRole.getRoleName());
            moPermGroupManager.addMoPerm(moPermGroup);
        }
    }

    /**
     * 缺省权限集
     */
    private void initDefaultPermissionGroupData() {
        // init permissiongroup data,模拟代码,请补充完善
        List<PermissionGroup> permGroups = permGroupManager.getAllPermGroups();
        boolean hasAllPermGroup = false;
        for (PermissionGroup permGroup : permGroups) {
            if ("all".equals(permGroup.getGroupName())) {
                hasAllPermGroup = true;
                break;
            }
        }
        if (!hasAllPermGroup) {
            List<String> permNames = new ArrayList<String>();
            Set<String> cates = permissionManager.getCategories();
            for (String category : cates) {
                List<Permission> permissions = permissionManager.getPermissionByCategory(category);
                for (Permission permission : permissions) {
                    permNames.add(permission.getPermissionId());
                }
            }
            PermissionGroup all = new PermissionGroup();
            all.setGroupName("all");
            all.setPermissions(permNames);
            permGroupManager.addPermGroup(all);
        }
    }

    @Override
    public void login(String user, String password) {
        userManager.login(user, password);
    }

    @Override
    public void logout(long sessionId) {
        userManager.logout(sessionId);
    }

    @Override
    public boolean check(String user, String actionId, MoNaming... moNamings) {
        if (moNamings == null)
            return false;
        Permission permission = permissionManager.getPermission(actionId);
        if (permission == null) {
            System.out.println("have no permission,action=" + actionId);
            return false;
        }

        String permissionId = permission.getPermissionId();
        Map<MoNaming, Set<String>> moPerms = userPermissionTree.getUserMoPermission(user);
        if (moPerms == null)
            return false;
        for (MoNaming naming : moNamings) {
            Set<String> perms = moPerms.get(naming);
            if (perms == null)
                return false;
            if (!perms.contains(permissionId))
                return false;
        }
        return true;
    }

    @Override
    public boolean isInMgmtScope(String user, MoNaming... moNamings) {
        return userManager.isInMgmtScope(user, moNamings);
    }

    @Override
    public UserCustom getUserCustom(String userName) {
        return userManager.getUserCustom(userName);
    }

    @Override
    public void setUserCustom(String userName, String key, String value) {
        userManager.setUserCustom(userName, key, value);
    }

    @Override
    public void deleteUserCustomItem(String userName, String key) {
        userManager.deleteUserCustomItem(userName, key);
    }

    @Override
    public List<User> getAllUsers() {
        return userManager.getAllUsers();
    }

    @Override
    public List<IpRange> getAllUsersIpRange() {
        return userManager.getAllUsersIpRange();
    }

    @Override
    public User getUser(String userName) {
        return userManager.getUser(userName);
    }

    @Override
    public void addUser(User user) {
        userManager.addUser(user);
    }

    @Override
    public void deleteUser(User user) {
        userManager.deleteUser(user);
    }

    @Override
    public void updateUser(User updatedUser) {
        userManager.updateUser(updatedUser);
    }

    public void activeUser(String userName) {
        User user = userManager.getUser(userName);
        user.setState(User.STATE_NORMAL);
        userManager.updateUser(user);
    }

    public void deactiveUser(String userName) {
        User user = userManager.getUser(userName);
        user.setState(User.STATE_DISABLE);
        userManager.updateUser(user);
    }

    @Override
    public List<String> getAllOperationNames() {
        return opLogManager.getAllOperationNames();
    }

    @Override
    public List<String> getAllOperationObjects() {
        return opLogManager.getAllOperationObjects();
    }

    @Override
    public List<String> getAllOperationUserNames() {
        return opLogManager.getAllOperationUserNames();
    }

    @Override
    public void addRole(Role role) {
        roleManager.addRole(role);
    }

    @Override
    public void deleteRole(String... roleNames) {
        roleManager.deleteRole(roleNames);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleManager.getAllRoles();
    }

    @Override
    public void updateRole(Role updatedRole) {
        roleManager.updateRole(updatedRole);
    }

    @Override
    public void addPermission(Permission perm) {
        throw new UnsupportedOperationException("v0.2不用实现");
    }

    @Override
    public void addPermissionGroup(PermissionGroup permGroup) {
        permGroupManager.addPermGroup(permGroup);
    }

    @Override
    public void deletePermission(String permName) {
        throw new UnsupportedOperationException("v0.2不用实现");
    }

    @Override
    public Boolean deletePermissionGroup(String permGroupName) {
        return permGroupManager.deletePermGroup(permGroupName);
    }

    @Override
    public List<String> getAllPermissionCategories() {
        List<String> categories = new ArrayList<String>();
        categories.addAll(permissionManager.getCategories());
        return categories;
    }

    @Override
    public List<PermissionGroup> getAllPermissionGroups() {
        return permGroupManager.getAllPermGroups();
    }

    @Override
    public Permission getPermission(String actionOrFn) {
        Permission permission = permissionManager.getPermission(actionOrFn);
        return permission;
    }

    @Override
    public PermissionGroup getPermissionGroup(String permGroupName) {
        return permGroupManager.getPermGroupByName(permGroupName);
    }

    @Override
    public List<Permission> getPermissions(String category) {
        List<Permission> perms = permissionManager.getPermissionByCategory(category);
        return perms;
    }

    @Override
    public void updatePermission(Permission updatedPerm) {
        throw new UnsupportedOperationException("v0.2不用实现");
    }

    @Override
    public void updatePermissionGroup(PermissionGroup updatedPermGroup) {
        permGroupManager.updatePermGroup(updatedPermGroup);
    }

    @Override
    public List<OperationLog> getOperationLog(OperationLogCondition cond) {
        return opLogManager.getOperationLog(cond);
    }

    /**
     * @see com.yuep.nms.core.common.smcore.service.SmCoreManagerService#addMoPerm(com.yuep.nms.core.common.smcore.model.MoPermGroup)
     */
    @Override
    public void addMoPerm(MoPermGroup... moPerms) {
        moPermGroupManager.addMoPerm(moPerms);
    }

    /**
     * @see com.yuep.nms.core.common.smcore.service.SmCoreManagerService#deleteMoPerm(com.yuep.nms.core.common.smcore.model.MoPermGroup)
     */
    @Override
    public void deleteMoPerm(MoPermGroup... moPerms) {
        moPermGroupManager.deleteMoPerm(moPerms);
    }

    /**
     * @see com.yuep.nms.core.common.smcore.service.SmCoreManagerService#getMoPerm(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public MoPermGroup getMoPerm(MoNaming moName, String roleName) {
        return moPermGroupManager.getMoPerm(moName, roleName);
    }

    /**
     * @see com.yuep.nms.core.common.smcore.service.SmCoreManagerService#updateMoPerm(com.yuep.nms.core.common.smcore.model.MoPermGroup)
     */
    @Override
    public void updateMoPerm(String roleName, MoPermGroup... moPerms) {
        moPermGroupManager.updateMoPerm(roleName, moPerms);
    }

    @Override
    public Map<MoNaming, Set<String>> getPermissionTree(String userName) {
        return userPermissionTree.getUserMoPermission(userName);
    }

    /**
     * @see com.yuep.nms.core.common.smcore.service.SmCoreManagerService#getRoleByName(java.lang.String)
     */
    @Override
    public Role getRoleByName(String roleName) {
        return roleManager.getRoleByName(roleName);
    }

    @Override
    public void addOperationLog(OperationLog ...logs) {
        opLogManager.addOperationLog(logs);
    }

}
