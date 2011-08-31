/*
 * $Id: AuthenticationService.java, 2011-3-24 ����11:17:52 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.smcore.service;

import com.yuep.nms.core.common.base.def.annotation.FacadeMethod;
import com.yuep.nms.core.common.base.def.annotation.LogType;

/**
 * <p>
 * Title: AuthenticationService
 * </p>
 * <p>
 * Description:��֤�ӿ�
 * </p>
 * 
 * @author sufeng
 * created 2011-3-24 ����11:17:52
 * modified [who date description]
 * check [who date description]
 */
public interface AuthenticationService {

    /**
     * ��¼
     * @param user �û���
     * @param password ����
     */
    @FacadeMethod(needLog=true,oplogType=LogType.SECURITY,scopeFilter=false)
    public void login(String user,String password);

    /**
     * ע��
     * @param sessionId �Ựid
     */
    @FacadeMethod(needLog=true,oplogType=LogType.SECURITY,scopeFilter=false)
    public void logout(long sessionId);
    
}
