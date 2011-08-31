/*
 * $Id: CheckLoginInfoRsp.java, 2011-3-29 下午02:46:23 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.smcore.impl;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.main.login.model.LoginInfo;
import com.yuep.core.client.main.mainframe.processing.rsp.DefaultCheckLoginInfoRsp;
import com.yuep.nms.core.client.smcore.def.SmCoreClientService;
import com.yuep.nms.core.common.base.def.SystemMessage;
import com.yuep.nms.core.common.smmanager.serivce.SmManagerService;

/**
 * <p>
 * Title: CheckLoginInfoRsp
 * </p>
 * <p>
 * Description: 登录，检查密码
 * </p>
 * 
 * @author sufeng
 * created 2011-3-29 下午02:46:23
 * modified [who date description]
 * check [who date description]
 */
public class CheckLoginInfoRsp<T> extends DefaultCheckLoginInfoRsp<T> {

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.client.main.mainframe.processing.rsp.DefaultCheckLoginInfoRsp#login(com.yuep.core.client.main.login.model.LoginInfo)
     */
    @Override
    protected void login(LoginInfo loginInfo) {
        super.login(loginInfo);

        // login
        SmManagerService smManager=ClientCoreContext.getRemoteService("smManagerService", SmManagerService.class);
        smManager.login(loginInfo.getUser(), loginInfo.getPassword());
        SystemMessage smMessage=new SystemMessage();
        smMessage.setMessageType(SystemMessage.TYPE_LOGIN);
        smMessage.setMessageBody(loginInfo);
        ClientCoreContext.publicLocalMessage(smMessage.getName(), smMessage);
        
        // 登录后需要进行初始化
        SmCoreClientService smCoreClientService = ClientCoreContext.getLocalService("smCoreClientService", SmCoreClientService.class);
        smCoreClientService.initAfterLogin(loginInfo.getUser());
    }
    
}
