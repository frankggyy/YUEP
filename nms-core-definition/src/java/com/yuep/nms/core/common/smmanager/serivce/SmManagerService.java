/*
 * $Id: SmManagerService.java, 2011-3-24 上午11:51:47 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.smmanager.serivce;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yuep.core.session.def.Session;
import com.yuep.nms.core.common.base.def.annotation.FacadeMethod;
import com.yuep.nms.core.common.base.def.annotation.LogType;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.smcore.service.AuthenticationService;
import com.yuep.nms.core.common.smcore.service.OperationLogService;
import com.yuep.nms.core.common.smcore.service.SmCoreManagerService;
import com.yuep.nms.core.common.smcore.service.UserProfileService;
import com.yuep.nms.core.common.smmanager.model.ModulePermission;

/**
 * <p>
 * Title: SmManagerService
 * </p>
 * <p>
 * Description: 安全管理服务（面向client）
 * </p>
 * 
 * @author sufeng
 * created 2011-3-24 上午11:51:47
 * modified [who date description]
 * check [who date description]
 */
public interface SmManagerService extends AuthenticationService, UserProfileService, SmCoreManagerService,
        OperationLogService {

    /**
     * 获取系统中所有的actionid, function id
     * 
     * @return key=actionid/functionid value=permissionID
     */
    public Map<String, String> getAllActionOrFunctions();

    /**
     * 得到用户的权限树
     * 
     * @param userName
     * @return
     */
    public Map<MoNaming, Set<String>> getPermissionTree(String userName);

    /**
     * 获取所有权限项
     * 
     * @return
     */
    public List<ModulePermission> getAllModulePermission();

    /**
     * 在线用户信息
     * 
     * @return
     */
    public List<Session> getAllOnlineUsers();

    /**
     * 踢出会话
     * 
     * @param sessionId
     */
    @FacadeMethod(needLog = true, oplogType = LogType.SECURITY)
    public void kickOff(long sessionId);

    /**
     * 踢出用户
     * @param userName
     */
    @FacadeMethod(needLog=true,oplogType=LogType.SECURITY)
    public void kickOff(String userName);
    
    /**
     * 当前用户修改自己的密码
     * 
     * @param userName
     * @param newPassword
     */
    @FacadeMethod(needLog = true, oplogType = LogType.SECURITY)
    public void setNewPassword(String userName, String oldPassword, String newPassword);
    
    /**
     * 重置用户的密码
     * 
     * @param userName
     * @param newPassword
     */
    @FacadeMethod(needLog = true, oplogType = LogType.SECURITY)
    public void resetPassword(String userName,String newPassword);

    /**
     * 客户端解锁的时候进行密码验证
     * 
     * @param userName
     * @param password
     * @return
     */
    @FacadeMethod(needLog = true, oplogType = LogType.SECURITY)
    public boolean accessCheck(String userName, String password);

}
