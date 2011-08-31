/*
 * $Id: TestInvokeAndMsgAction.java, 2010-4-23 ÏÂÎç03:26:30 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.biz.client.sample.action;

import java.awt.event.ActionEvent;

import com.yuep.core.client.component.menu.action.AbstractXAction;

/**
 * <p>
 * Title: TestInvokeAndMsgAction
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron lee
 * created 2010-4-23 ÏÂÎç03:26:30
 * modified [who date description]
 * check [who date description]
 */
public class TestInvokeAndMsgAction extends AbstractXAction {

    /**
     * @param actionId
     */
    public TestInvokeAndMsgAction(String actionId) {
        super(actionId);
    }

    private static final long serialVersionUID = 496026480131451967L;

    /**
     * (non-Javadoc)
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
//        SmClientModule smClientModule = ClientContext.getSmClientModule();
//        smClientModule.init();

//        User1 user1 = new User1();
//        user1.setUserName("admin");
//        user1.setPassword("admin");
//        smClientModule.getSmFacade().addUser(user1);
//        smClientModule.getSmFacade().login("127.0.0.1", user1.getUserName(), user1.getPassword());
    }
}
