/*
 * $Id: ExitAction.java, 2009-2-25 下午03:21:39 aaron lee Exp $
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

import com.yuep.core.bootstrap.def.ContainerConst;
import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.AbstractXAction;
import com.yuep.core.client.util.DialogUtils;
import com.yuep.core.client.util.GuiConstants;

/**
 * <p>
 * Title: ExitAction
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-25 下午03:21:39
 * modified [who date description]
 * check [who date description]
 */
public class SystemExitAction extends AbstractXAction {

    /**
     * @param actionId
     */
    public SystemExitAction(String actionId) {
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
        String title = "common.mainframe.close.title";
        String message = "common.mainframe.close.message";
        int yesOrNo = DialogUtils.showYesNoOptionDialog(ClientCoreContext.getMainFrame(), message, title);
        if (yesOrNo == GuiConstants.RetValues.YES_OPTION) {
            try{
                // 用户logout
            }catch(Exception ex){
                DialogUtils.showErrorExpandDialog(ClientCoreContext.getMainFrame(), ex);
            }
            ContainerConst.exitSystem(0);
        }
    }
}
