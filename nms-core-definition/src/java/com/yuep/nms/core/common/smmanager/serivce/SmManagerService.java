/*
 * $Id: SmManagerService.java, 2011-3-24 ����11:51:47 sufeng Exp $
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
 * Description: ��ȫ�����������client��
 * </p>
 * 
 * @author sufeng
 * created 2011-3-24 ����11:51:47
 * modified [who date description]
 * check [who date description]
 */
public interface SmManagerService extends AuthenticationService, UserProfileService, SmCoreManagerService,
        OperationLogService {

    /**
     * ��ȡϵͳ�����е�actionid, function id
     * 
     * @return key=actionid/functionid value=permissionID
     */
    public Map<String, String> getAllActionOrFunctions();

    /**
     * �õ��û���Ȩ����
     * 
     * @param userName
     * @return
     */
    public Map<MoNaming, Set<String>> getPermissionTree(String userName);

    /**
     * ��ȡ����Ȩ����
     * 
     * @return
     */
    public List<ModulePermission> getAllModulePermission();

    /**
     * �����û���Ϣ
     * 
     * @return
     */
    public List<Session> getAllOnlineUsers();

    /**
     * �߳��Ự
     * 
     * @param sessionId
     */
    @FacadeMethod(needLog = true, oplogType = LogType.SECURITY)
    public void kickOff(long sessionId);

    /**
     * �߳��û�
     * @param userName
     */
    @FacadeMethod(needLog=true,oplogType=LogType.SECURITY)
    public void kickOff(String userName);
    
    /**
     * ��ǰ�û��޸��Լ�������
     * 
     * @param userName
     * @param newPassword
     */
    @FacadeMethod(needLog = true, oplogType = LogType.SECURITY)
    public void setNewPassword(String userName, String oldPassword, String newPassword);
    
    /**
     * �����û�������
     * 
     * @param userName
     * @param newPassword
     */
    @FacadeMethod(needLog = true, oplogType = LogType.SECURITY)
    public void resetPassword(String userName,String newPassword);

    /**
     * �ͻ��˽�����ʱ�����������֤
     * 
     * @param userName
     * @param password
     * @return
     */
    @FacadeMethod(needLog = true, oplogType = LogType.SECURITY)
    public boolean accessCheck(String userName, String password);

}
