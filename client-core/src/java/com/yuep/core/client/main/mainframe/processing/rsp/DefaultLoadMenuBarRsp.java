/*
 * $Id: DefaultLoadMenuBarRsp.java, 2009-2-21 ����01:03:59 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.main.mainframe.processing.rsp;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.XMenuBarView;
import com.yuep.core.client.main.process.rsp.AbstractResponsibility;

/**
 * <p>
 * Title: DefaultLoadMenuBarRsp
 * </p>
 * <p>
 * Description:���ز˵�����Ĭ��ʵ��,�ο�start-process.xml
 * </p>
 * 
 * @author aaron lee
 * created 2010-4-27 ����03:25:36
 * modified [who date description]
 * check [who date description]
 */
public class DefaultLoadMenuBarRsp<T> extends AbstractResponsibility<T>{

	/**
	 * (non-Javadoc)
	 * @see com.yuep.core.client.main.process.rsp.Responsibility#execute(java.lang.Object)
	 */
    @Override
    public boolean execute(T t) {
        XMenuBarView menuBarView = new XMenuBarView();
        ClientCoreContext.setMenuBarView(menuBarView);
        return true;
    }

}
