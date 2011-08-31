/*
 * $Id: OpenComboBoxEditorAction.java, 2010-3-31 ÏÂÎç03:49:22 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.biz.client.sample.editor.action;

import java.awt.event.ActionEvent;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.menu.action.AbstractXAction;
import com.yuep.nms.biz.client.sample.ClientSampleModule;
import com.yuep.nms.biz.client.sample.editor.controller.SampleEditorController;
import com.yuep.nms.biz.client.sample.editor.model.SampleEditorModel;
import com.yuep.nms.biz.client.sample.editor.view.SampleEditorView;

/**
 * <p>
 * Title: OpenComboBoxEditorAction
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron lee
 * created 2010-3-31 ÏÂÎç03:49:22
 * modified [who date description]
 * check [who date description]
 */
public class OpenComboBoxEditorAction extends AbstractXAction{
    private static final long serialVersionUID = 7616530137074964843L;

    /**
     * @param actionId
     */
    public OpenComboBoxEditorAction(String actionId) {
        super(actionId);
    }

    /**
     * (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        ClientSampleModule module = ClientCoreContext.getModule(ClientSampleModule.class);
        SampleEditorController sampleEditorController = module.getController(SampleEditorModel.class,
                SampleEditorView.class, SampleEditorController.class);
        ClientCoreContext.getWindowManager().openAsDialog(sampleEditorController);
        sampleEditorController.initData();
    }
}
