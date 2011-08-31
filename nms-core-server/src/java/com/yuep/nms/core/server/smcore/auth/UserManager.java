/*
 * $Id: UserManager.java, 2011-3-24 ����01:41:41 sufeng Exp $
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
 * Description:�û�����
 * </p>
 * 
 * @author sufeng created 2011-3-24 ����01:41:41
 */
public class UserManager {

    private BaseDao<User> userDao;
    private BaseDao<IpRange> ipRangeDao;
    private MgmtScopeManager mgmtScopeManager;
    private SessionService sessionService;
    private UserPermissionTree userPermissionTree;
    /** ���Ի���Ϣ������ */
    private UserCustomProcessor userCustomProcessor;

    /**
     * �ѵ�¼���û����� TODO ���ڳ�ʱ��session��Ҳ��Ҫ��session�Ƴ���
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

        // ���session timeout��,����Ҫ��cache��loginedUserSessionsҲ�����
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

        // ��ʼ�����ݣ�����Ĭ���˺�
        initDefaultUserData();
    }

    /**
     * ��ʼ�����ݣ�����Ĭ���˺�
     */
    private void initDefaultUserData() {
        List<User> users = getAllUsers();
        if (CollectionUtils.isEmpty(users)) {
            User admin = new User();
            admin.setUserName("admin");           
            admin.setPassword(EncryptUtils.setEncrypt("admin", EncryptUtils.MAGIC_KEY_CLIENT));
            // ����ȱʡ��role
            List<String> roles = new ArrayList<String>();
            roles.add("admin");
            admin.setRoles(roles);
            // ����ȱʡ�Ĺ���Χ
            MoCore moCore = SmCoreContext.getLocalService(MoCoreModuleConstants.MOCORE_LOCAL_SERVICE, MoCore.class);
            MoNaming rootMo = moCore.getRootMo().getMoNaming();
            List<MoNaming> mgmtScope = new ArrayList<MoNaming>();
            mgmtScope.add(rootMo);
            admin.setMgmtScope(mgmtScope);
            addUser(admin);
        }
    }

    /**
     * ��¼
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

        // ����session�е�owner����
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
        // ��һ��login��Ҫ��ʼ��mgmt cache,permission cache
        Set<Long> userSessionIds = loginedUserSessions.get(user);
        if (userSessionIds == null) {
            userSessionIds = new HashSet<Long>();
            loginedUserSessions.put(user, userSessionIds);
        }
        userSessionIds.add(session.getSessionId());
        if (userSessionIds.size() == 1) {
            // ��ʼ��2��cache
            mgmtScopeManager.setUserMgmtScope(user, userObj.getMgmtScope());
            userPermissionTree.buildUserPermissionTree(userObj);
        }
    }

    /**
     * ע��
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
            // �����user��mgmt scope,permission cache
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
     * ����һ��user
     * 
     * @param user
     */
    public void addUser(User user) {
        String serverEncryptPwd = EncryptUtils.clientPwd2ServerPwd(user.getPassword());
        user.setPassword(serverEncryptPwd);
        userDao.saveEntity(user);

        // ������Ϣ
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

        // ɾ���û��ĸ��Ի���Ϣ
        userCustomProcessor.deleteUserCustom(user.getUserName());
        userDao.deleteEntity(user);

        // ������Ϣ
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

            // �����û��Ĺ���Χ,Ȩ����
            if (!YuepObjectUtils.collectionEquals(userInDb.getMgmtScope(), updatedUser.getMgmtScope())) {
                mgmtScopeManager.setUserMgmtScope(updatedUser.getUserName(), updatedUser.getMgmtScope());
            }
            if (!YuepObjectUtils.collectionEquals(userInDb.getRoles(), updatedUser.getRoles())) {
                userPermissionTree.userRolePermissionChanged(updatedUser);
            }
        } else {
            userDao.saveEntity(updatedUser);
        }

        // ������Ϣ
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
     * �û��ĸ��Ի���Ϣ
     * 
     * @param userName
     * @return
     */
    public UserCustom getUserCustom(String userName) {
        return userCustomProcessor.getUserCustom(userName);
    }

    /**
     * �����û��ĸ��Ի���Ϣ
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
     * ��ȡĳ��role������User
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
