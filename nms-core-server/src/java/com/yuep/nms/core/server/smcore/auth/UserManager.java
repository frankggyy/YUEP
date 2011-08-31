/*
 * $Id: UserManager.java, 2011-3-24 下午01:41:41 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.smcore.auth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections.CollectionUtils;

import com.yuep.base.util.encry.EncryptUtils;
import com.yuep.base.util.format.WatchUtil;
import com.yuep.base.util.format.YuepObjectUtils;
import com.yuep.core.container.def.CommunicateObject;
import com.yuep.core.container.def.CoreContext;
import com.yuep.core.db.access.basedao.BaseDao;
import com.yuep.core.message.def.MessageReceiver;
import com.yuep.core.session.def.Session;
import com.yuep.core.session.def.SessionMessage;
import com.yuep.core.session.def.SessionService;
import com.yuep.core.session.def.SessionState;
import com.yuep.nms.core.common.mocore.module.constants.MoCoreModuleConstants;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.mocore.service.MoCore;
import com.yuep.nms.core.common.smcore.exception.SmException;
import com.yuep.nms.core.common.smcore.model.IpRange;
import com.yuep.nms.core.common.smcore.model.User;
import com.yuep.nms.core.common.smcore.model.UserCustom;
import com.yuep.nms.core.common.smcore.msg.SmMessage;
import com.yuep.nms.core.server.smcore.SmCoreContext;
import com.yuep.nms.core.server.smcore.mgmtscope.MgmtScopeManager;
import com.yuep.nms.core.server.smcore.perm.UserPermissionTree;

/**
 * <p>
 * Title: UserManager
 * </p>
 * <p>
 * Description:用户管理
 * </p>
 * 
 * @author sufeng created 2011-3-24 下午01:41:41
 */
public class UserManager {

    private BaseDao<User> userDao;
    private BaseDao<IpRange> ipRangeDao;
    private MgmtScopeManager mgmtScopeManager;
    private SessionService sessionService;
    private UserPermissionTree userPermissionTree;
    /** 个性化信息处理器 */
    private UserCustomProcessor userCustomProcessor;

    /**
     * 已登录的用户计数 TODO 对于超时的session，也需要将session移除掉
     */
    private Map<String, Set<Long>> loginedUserSessions = new ConcurrentHashMap<String, Set<Long>>();

    public void setUserDao(BaseDao<User> userDao) {
        this.userDao = userDao;
    }

    public void setMgmtScopeManager(MgmtScopeManager mgmtScopeManager) {
        this.mgmtScopeManager = mgmtScopeManager;
    }

    public void setUserPermissionTree(UserPermissionTree userPermissionTree) {
        this.userPermissionTree = userPermissionTree;
    }

    public void setIpRangeDao(BaseDao<IpRange> ipRangeDao) {
        this.ipRangeDao = ipRangeDao;
    }

    public void setUserCustomProcessor(UserCustomProcessor userCustomProcessor) {
        this.userCustomProcessor = userCustomProcessor;
    }

    public void init() {
        sessionService = CoreContext.getInstance().local().getService("sessionService", SessionService.class);

        // 如果session timeout了,则需要将cache的loginedUserSessions也清除掉
        SmCoreContext.subscribeLocalMessage(SessionMessage.NAME, new MessageReceiver() {
            @Override
            public void receive(CommunicateObject co, String name, Serializable msg) {
                if (msg instanceof SessionMessage) {
                    SessionMessage sessionMsg = (SessionMessage) msg;
                    Session session = sessionMsg.getSession();
                    if(session==null)
                        return;
                    if (SessionState.Linkdown == session.getSessionState()) {
                        logoutSession(session);
                    }
                }
            }
        });

        // 初始化数据，创建默认账号
        initDefaultUserData();
    }

    /**
     * 初始化数据，创建默认账号
     */
    private void initDefaultUserData() {
        List<User> users = getAllUsers();
        if (CollectionUtils.isEmpty(users)) {
            User admin = new User();
            admin.setUserName("admin");           
            admin.setPassword(EncryptUtils.setEncrypt("admin", EncryptUtils.MAGIC_KEY_CLIENT));
            // 设置缺省的role
            List<String> roles = new ArrayList<String>();
            roles.add("admin");
            admin.setRoles(roles);
            // 设置缺省的管理范围
            MoCore moCore = SmCoreContext.getLocalService(MoCoreModuleConstants.MOCORE_LOCAL_SERVICE, MoCore.class);
            MoNaming rootMo = moCore.getRootMo().getMoNaming();
            List<MoNaming> mgmtScope = new ArrayList<MoNaming>();
            mgmtScope.add(rootMo);
            admin.setMgmtScope(mgmtScope);
            addUser(admin);
        }
    }

    /**
     * 登录
     * 
     * @param user
     * @param password
     */
    public void login(String user, String password) {
        //System.out.println("UserManager.login:" + user + "," + password);
        Session session = sessionService.getSession();
        session.setOwner(user);

        User userObj = userDao.getUniqueEntityByOneProperty("userName", user);
        if (userObj == null)
            throw new SmException(SmException.USER_NOT_FOUND, user);
        String serverPwd =EncryptUtils.clientPwd2ServerPwd(password);
        if (!serverPwd.equals(userObj.getPassword()))
            throw new SmException(SmException.PASSWORD_WRONG, user);

        if (userObj.getState() == User.STATE_DISABLE)
            throw new SmException(SmException.USER_DISABLE);

        long now = System.currentTimeMillis();
        if (userObj.getExpiredTime() != null) {
            if (now >= userObj.getExpiredTime()) {
                throw new SmException(SmException.USER_EXPIRED);
            }
        }

        if (userObj.getPasswordExpiredTime() != null) {
            if (now >= userObj.getPasswordExpiredTime()) {
                throw new SmException(SmException.PASSWORD_EXPIRED);
            }
        }

        // 更新session中的owner属性
        String clientIp = session.getIp();
        if (CollectionUtils.isNotEmpty(userObj.getIpRanges())) {
            long ip = WatchUtil.getAddrLong(clientIp);
            boolean inRange = false;
            for (IpRange ipRange : userObj.getIpRanges()) {
                if (ipRange.isInRange(ip)) {
                    inRange = true;
                    break;
                }
            }
            if (!inRange)
                throw new SmException(SmException.NOT_IN_IPRANGE);
        }

        session.setSessionState(SessionState.Active);
        // 第一次login需要初始化mgmt cache,permission cache
        Set<Long> userSessionIds = loginedUserSessions.get(user);
        if (userSessionIds == null) {
            userSessionIds = new HashSet<Long>();
            loginedUserSessions.put(user, userSessionIds);
        }
        userSessionIds.add(session.getSessionId());
        if (userSessionIds.size() == 1) {
            // 初始化2个cache
            mgmtScopeManager.setUserMgmtScope(user, userObj.getMgmtScope());
            userPermissionTree.buildUserPermissionTree(userObj);
        }
    }

    /**
     * 注销
     * 
     * @param sessionId
     */
    public void logout(long sessionId) {
        Session session = sessionService.getSessionById(sessionId);
        session.setSessionState(SessionState.Deactive);
        logoutSession(session);
    }

    private void logoutSession(Session session) {
        if (session == null)
            return;
        String userName = session.getOwner();
        if (userName == null)
            return;
        Set<Long> userSessionIds = loginedUserSessions.get(userName);
        if (userSessionIds == null)
            return;

        userSessionIds.remove(session.getSessionId());
        if (userSessionIds.size() == 0) {
            // 清理该user的mgmt scope,permission cache
            loginedUserSessions.remove(userName);
            mgmtScopeManager.removeUserMgmtScope(userName);
            userPermissionTree.removeUserMoPermission(userName);
        }
    }

    public List<User> getAllUsers() {
        return userDao.getAllEntities();
    }

    public List<IpRange> getAllUsersIpRange() {
        return ipRangeDao.getAllEntities();
    }

    /**
     * 创建一个user
     * 
     * @param user
     */
    public void addUser(User user) {
        String serverEncryptPwd = EncryptUtils.clientPwd2ServerPwd(user.getPassword());
        user.setPassword(serverEncryptPwd);
        userDao.saveEntity(user);

        // 发送消息
        SmMessage msg = new SmMessage();
        msg.setMessageType(SmMessage.TYPE_USER_ADD);
        msg.setMessageBody(user);
        SmCoreContext.publishMessage(msg.getName(), msg);
    }

    public void deleteUser(final User user) {
        Set<Long> sessions = loginedUserSessions.get(user.getUserName());
        if (CollectionUtils.isNotEmpty(sessions)) {
            throw new SmException(SmException.USER_IN_SESSION);
        }

        // 删除用户的个性化信息
        userCustomProcessor.deleteUserCustom(user.getUserName());
        userDao.deleteEntity(user);

        // 发送消息
        SmMessage msg = new SmMessage();
        msg.setMessageType(SmMessage.TYPE_USER_DELETE);
        msg.setMessageBody(user);
        SmCoreContext.publishMessage(msg.getName(), msg);
    }

    public User getUser(final String userName) {
        return userDao.getUniqueEntityByOneProperty("userName", userName);
    }

    @SuppressWarnings("unchecked")
    public void updateUser(final User updatedUser) {
        String serverEncryptPassword = EncryptUtils.clientPwd2ServerPwd(updatedUser.getPassword());
        updatedUser.setPassword(serverEncryptPassword);
        User userInDb = getUser(updatedUser.getUserName());
        final boolean existed = (userInDb != null);
        if (existed) {
            userDao.merge(updatedUser);

            // 更新用户的管理范围,权限树
            if (!YuepObjectUtils.collectionEquals(userInDb.getMgmtScope(), updatedUser.getMgmtScope())) {
                mgmtScopeManager.setUserMgmtScope(updatedUser.getUserName(), updatedUser.getMgmtScope());
            }
            if (!YuepObjectUtils.collectionEquals(userInDb.getRoles(), updatedUser.getRoles())) {
                userPermissionTree.userRolePermissionChanged(updatedUser);
            }
        } else {
            userDao.saveEntity(updatedUser);
        }

        // 发送消息
        SmMessage msg = new SmMessage();
        msg.setMessageBody(updatedUser);
        if (existed) {
            msg.setMessageType(SmMessage.TYPE_USER_UPDATE);
            Map changedInfo = userInDb.getValueCompareObjectMap(updatedUser);
            msg.setAdditions(changedInfo);
        } else {
            msg.setMessageType(SmMessage.TYPE_USER_ADD);
        }
        SmCoreContext.publishMessage(msg.getName(), msg);
    }

    /**
     * 用户的个性化信息
     * 
     * @param userName
     * @return
     */
    public UserCustom getUserCustom(String userName) {
        return userCustomProcessor.getUserCustom(userName);
    }

    /**
     * 设置用户的个性化信息
     * 
     * @param userName
     * @param key
     * @param value
     */
    public void setUserCustom(String userName, String key, String value) {
        userCustomProcessor.setUserCustom(userName, key, value);
    }

    public void deleteUserCustomItem(String userName, String key) {
        userCustomProcessor.deleteUserCustomItem(userName, key);
    }

    public boolean isInMgmtScope(String user, MoNaming... moNamings) {
        return mgmtScopeManager.isInMgmtScope(user, moNamings);
    }

    /**
     * 获取某个role关联的User
     * 
     * @param role
     * @return
     */
    public List<User> getUsersByRole(String role) {
        List<User> users = getAllUsers();
        for (Iterator<User> it = users.iterator(); it.hasNext();) {
            User user = it.next();
            if (user.getRoles() != null) {
                if (!user.getRoles().contains(role)) {
                    it.remove();
                }
            } else {
                it.remove();
            }
        }
        return users;
    }



}
