/*
 * $Id: SmManagerServiceImpl.java, 2011-3-24 下午01:28:12 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.smmanager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import com.yuep.base.util.encry.EncryptUtils;
import com.yuep.core.session.def.Session;
import com.yuep.core.session.def.SessionService;
import com.yuep.core.session.def.SessionState;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.smcore.exception.SmException;
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
import com.yuep.nms.core.common.smmanager.model.ModulePermission;
import com.yuep.nms.core.common.smmanager.serivce.SmManagerService;

/**
 * <p>
 * Title: SmManagerServiceImpl
 * </p>
 * <p>
 * Description:sm管理服务的实现类，大部分是转发到sm core serivce上
 * </p>
 * 
 * @author sufeng created 2011-3-24 下午01:28:12 modified [who date description]
 *         check [who date description]
 */
public class SmManagerServiceImpl implements SmManagerService {

    private SmCoreService smCoreService;

    public SmManagerServiceImpl(SmCoreService smCoreService) {
        this.smCoreService = smCoreService;
    }

    @Override
    public void updateUser(User updatedUser) {
        smCoreService.updateUser(updatedUser);
    }

    public void activeUser(String userName) {
        smCoreService.activeUser(userName);
    }

    public void deactiveUser(String userName) {
        smCoreService.deactiveUser(userName);
    }

    @Override
    public User getUser(String userName) {
        return smCoreService.getUser(userName);
    }

    @Override
    public void addUser(User user) {
        smCoreService.addUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        return smCoreService.getAllUsers();
    }

    @Override
    public List<IpRange> getAllUsersIpRange() {
        return smCoreService.getAllUsersIpRange();
    }

    @Override
    public void deleteUser(User user) {
        smCoreService.deleteUser(user);
    }

    @Override
    public void login(String user, String password) {
        smCoreService.login(user, password);
    }

    @Override
    public void logout(long sessionId) {
        smCoreService.logout(sessionId);
    }

    @Override
    public UserCustom getUserCustom(String userName) {
        return smCoreService.getUserCustom(userName);
    }

    @Override
    public void setUserCustom(String userName, String key, String value) {
        smCoreService.setUserCustom(userName, key, value);
    }

    @Override
    public void deleteUserCustomItem(String userName, String key) {
        smCoreService.deleteUserCustomItem(userName, key);
    }

    @Override
    public Map<String, String> getAllActionOrFunctions() {
        Map<String, String> actionPerms = new HashMap<String, String>();
        List<String> categories = smCoreService.getAllPermissionCategories();
        if (CollectionUtils.isNotEmpty(categories)) {
            for (String category : categories) {
                List<Permission> perms = smCoreService.getPermissions(category);
                if (CollectionUtils.isNotEmpty(perms)) {
                    for (Permission perm : perms) {
                        List<String> actionFunctions = perm.getActionFunctions();
                        if (CollectionUtils.isNotEmpty(actionFunctions)) {
                            for (String action : actionFunctions)
                                actionPerms.put(action, perm.getPermissionId());
                        }
                    }
                }
            }
        }
        return actionPerms;
    }

    @Override
    public Map<MoNaming, Set<String>> getPermissionTree(String userName) {
        return smCoreService.getPermissionTree(userName);
    }

    @Override
    public void addPermission(Permission perm) {
        smCoreService.addPermission(perm);
    }

    @Override
    public void addPermissionGroup(PermissionGroup permGroup) {
        smCoreService.addPermissionGroup(permGroup);
    }

    @Override
    public void addRole(Role role) {
        smCoreService.addRole(role);
    }

    @Override
    public void deletePermission(String permName) {
        smCoreService.deletePermission(permName);
    }

    @Override
    public Boolean deletePermissionGroup(String permGroupName) {
        return smCoreService.deletePermissionGroup(permGroupName);
    }

    @Override
    public void deleteRole(String... roleNames) {
        smCoreService.deleteRole(roleNames);
    }

    @Override
    public List<String> getAllPermissionCategories() {
        return smCoreService.getAllPermissionCategories();
    }

    @Override
    public List<PermissionGroup> getAllPermissionGroups() {
        return smCoreService.getAllPermissionGroups();
    }

    @Override
    public List<Role> getAllRoles() {
        return smCoreService.getAllRoles();
    }

    @Override
    public Permission getPermission(String actionOrFn) {
        return smCoreService.getPermission(actionOrFn);
    }

    @Override
    public PermissionGroup getPermissionGroup(String permGroupName) {
        return smCoreService.getPermissionGroup(permGroupName);
    }

    @Override
    public List<Permission> getPermissions(String category) {
        return smCoreService.getPermissions(category);
    }

    @Override
    public void updatePermission(Permission updatedPerm) {
        smCoreService.updatePermission(updatedPerm);
    }

    @Override
    public void updatePermissionGroup(PermissionGroup updatedPermGroup) {
        smCoreService.updatePermissionGroup(updatedPermGroup);
    }

    @Override
    public void updateRole(Role updatedRole) {
        smCoreService.updateRole(updatedRole);
    }

    @Override
    public List<ModulePermission> getAllModulePermission() {
        List<ModulePermission> moduleList = new ArrayList<ModulePermission>();
        List<String> categorieList = this.getAllPermissionCategories();
        for (String moduleName : categorieList) {
            ModulePermission modulePerm = new ModulePermission(moduleName, moduleName);
            List<Permission> permList = getPermissions(moduleName);
            modulePerm.setPermissions(permList);
            moduleList.add(modulePerm);
        }
        return moduleList;
    }

    @Override
    public void addMoPerm(MoPermGroup... moPerms) {
        smCoreService.addMoPerm(moPerms);
    }

    @Override
    public void deleteMoPerm(MoPermGroup... moPerms) {
        smCoreService.deleteMoPerm(moPerms);
    }

    @Override
    public MoPermGroup getMoPerm(MoNaming moName, String roleName) {
        return smCoreService.getMoPerm(moName, roleName);
    }

    @Override
    public void updateMoPerm(String roleName, MoPermGroup... moPerms) {
        smCoreService.updateMoPerm(roleName, moPerms);
    }

    @Override
    public Role getRoleByName(String roleName) {
        return smCoreService.getRoleByName(roleName);
    }

    @Override
    public List<String> getAllOperationNames() {
        return smCoreService.getAllOperationNames();
    }

    @Override
    public List<String> getAllOperationObjects() {
        return smCoreService.getAllOperationObjects();
    }

    @Override
    public List<String> getAllOperationUserNames() {
        return smCoreService.getAllOperationUserNames();
    }

    @Override
    public List<OperationLog> getOperationLog(OperationLogCondition cond) {
        return smCoreService.getOperationLog(cond);
    }
    
    @Override
    public void addOperationLog(OperationLog ... logs) {
        smCoreService.addOperationLog(logs);
    }

    @Override
    public List<Session> getAllOnlineUsers() {
        SessionService sessionService = SmManagerContext.getLocalService("sessionService", SessionService.class);
        Collection<Session> allSessions = sessionService.getAllSessions();
        List<Session> list = new ArrayList<Session>();
        for (Session session : allSessions) {
            // 只返回那些登录过的会话
            if (session != null && SessionState.Active.equals(session.getSessionState()))
                list.add(session);
        }
        return list;
    }

    @Override
    public void kickOff(long sessionId) {
        SessionService sessionService = SmManagerContext.getLocalService("sessionService", SessionService.class);
        sessionService.cleanup(sessionId);
    }

    @Override
    public void kickOff(String userName) {
        SessionService sessionService = SmManagerContext.getLocalService("sessionService", SessionService.class);
        Collection<Session> sessions = sessionService.getAllSessions();
        for (Session session : sessions) {
            if (userName.equals(session.getOwner())) {
                sessionService.cleanup(session.getSessionId());
            }
        }
    }

    @Override
    public void setNewPassword(String userName, String oldPassword, String newPassword) {
        User user = getUser(userName);
        if (user == null)
            throw new SmException(SmException.USER_NOT_FOUND, userName);
        if (!oldPassword.equals(EncryptUtils.serverPwd2ClientPwd(user.getPassword())))
            throw new SmException(SmException.PASSWORD_WRONG, userName);
        user.setPassword(newPassword);
        updateUser(user);
    }

    @Override
    public void resetPassword(String userName, String newPassword) {
        User user = getUser(userName);
        if (user == null)
            throw new SmException(SmException.USER_NOT_FOUND, userName);
        user.setPassword(newPassword);
        updateUser(user);
    }

    @Override
    public boolean accessCheck(String userName, String password) {
        User user = getUser(userName);
        if (user == null)
            throw new SmException(SmException.USER_NOT_FOUND, userName);
        if (!password.equals(EncryptUtils.serverPwd2ClientPwd(user.getPassword())))
            throw new SmException(SmException.PASSWORD_WRONG, userName);
        return true;
    }

}
