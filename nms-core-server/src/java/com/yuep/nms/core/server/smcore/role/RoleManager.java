/*
 * $Id: RoleManager.java, 2011-3-31 上午10:31:13 WangRui Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.smcore.role;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.yuep.core.db.access.basedao.BaseDao;
import com.yuep.nms.core.common.smcore.exception.SmException;
import com.yuep.nms.core.common.smcore.model.Role;
import com.yuep.nms.core.common.smcore.model.User;
import com.yuep.nms.core.common.smcore.msg.SmMessage;
import com.yuep.nms.core.server.smcore.SmCoreContext;
import com.yuep.nms.core.server.smcore.auth.UserManager;

/**
 * <p>
 * Title: RoleManager
 * </p>
 * <p>
 * Description:角色管理类，封装roleDao的持久层操作
 * </p>
 * 
 * @author WangRui
 * created 2011-3-31 上午10:31:13
 * modified [who date description]
 * check [who date description]
 */
public class RoleManager {

    private BaseDao<Role> roleManagerDao;

    /**
     * @param roleManagerDao
     *            the roleManagerDao to set
     */
    public void setRoleManagerDao(BaseDao<Role> roleManagerDao) {
        this.roleManagerDao = roleManagerDao;
    }

    public void addRole(Role role) {
        roleManagerDao.saveEntity(role);
        // 发送消息
        SmMessage msg = new SmMessage();
        msg.setMessageType(SmMessage.TYPE_ROLE_ADD);
        msg.setMessageBody(role.getRoleName());
        SmCoreContext.publishMessage(msg.getName(), msg);
    }

    @SuppressWarnings("unchecked")
    public void updateRole(Role updatedRole) {
        Role roleInDb = roleManagerDao.getUniqueEntityByOneProperty("roleName", updatedRole.getRoleName());

        final boolean existed = (roleInDb != null);
        if (existed) {
            roleManagerDao.updateEntity(updatedRole);
        } else {
            roleManagerDao.saveEntity(updatedRole);
        }

        SmMessage msg = new SmMessage();
        if (existed) {
            msg.setMessageType(SmMessage.TYPE_ROLE_UPDATE);
            Map changedInfo = roleInDb.getValueCompareObjectMap(updatedRole);
            msg.setAdditions(changedInfo);
        } else {
            msg.setMessageType(SmMessage.TYPE_ROLE_ADD);
        }
        msg.setMessageBody(updatedRole.getRoleName());
        SmCoreContext.publishMessage(msg.getName(), msg);
    }

    public void deleteRole(String... roleNames) {
        // 已被user引用的role不能被删除
        for (String roleName : roleNames) {
            List<User> users = getUsersByRole(roleName);
            if (CollectionUtils.isNotEmpty(users)) {
                StringBuilder sb = new StringBuilder();
                boolean first = true;
                for (User user : users) {
                    if (first) {
                        sb.append(user.getUserName());
                        first = false;
                    } else {
                        sb.append(",").append(user.getUserName());
                    }
                }
                throw new SmException(SmException.USER_REFERED_ROLE, roleName, sb.toString());
            }
        }

        StringBuilder names = new StringBuilder();
        for (String name : roleNames) {
            roleManagerDao.deleteEntityByProperty("roleName", name);
            names.append(name).append(",");
        }
        // 发送消息
        SmMessage msg = new SmMessage();
        msg.setMessageType(SmMessage.TYPE_ROLE_DELETE);
        msg.setMessageBody(names);
        SmCoreContext.publishMessage(msg.getName(), msg);
    }

    public List<Role> getAllRoles() {
        return roleManagerDao.getAllEntities();
    }

    public Role getRoleByName(String roleName) {
        return roleManagerDao.getUniqueEntityByOneProperty("roleName", roleName);
    }

    public List<User> getUsersByRole(String role) {
        UserManager userManager = SmCoreContext.getBean("userManager", UserManager.class);
        return userManager.getUsersByRole(role);
    }

}
