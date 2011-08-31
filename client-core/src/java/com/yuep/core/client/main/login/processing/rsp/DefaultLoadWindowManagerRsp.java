/*
 * $Id: DefaultLoadWindowManagerRsp.java, 2010-9-28 ����07:09:10 aaron Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.main.login.processing.rsp;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.window.WindowManager;
import com.yuep.core.client.component.window.WindowManagerImpl;
import com.yuep.core.client.main.process.rsp.AbstractResponsibility;

/**
 * <p>
 * Title: DefaultLoadWindowManagerRsp
 * </p>
 * <p>
 * Description:Ĭ�ϼ��ش��ڹ�������Responsibility��ְ��飩
 * </p>
 * 
 * @author aaron
 * created 2010-9-28 ����07:09:10
 * modified [who date description]
 * check [who date description]
 */
public class DefaultLoadWindowManagerRsp<T> extends AbstractResponsibility<T> {
    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.main.process.rsp.Responsibility#execute(java.lang.Object)
     */
    @Override
    public boolean execute(T t) {
        WindowManager windowManager = new WindowManagerImpl();
        ClientCoreContext.setWindowManager(windowManager);
        return true;
    }

}
