/*
 * $Id: CreateSampleTableDataView.java, 2010-3-29 ÏÂÎç05:33:23 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.biz.client.sample.mvc.view;

import java.util.List;

import com.yuep.core.client.component.window.MessageDialog.MessageType;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.nms.biz.client.sample.mvc.action.CreateSampleTableDataAction;

/**
 * <p>
 * Title: CreateSampleTableDataView
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron lee
 * created 2010-3-29 ÏÂÎç05:33:23
 * modified [who date description]
 * check [who date description]
 */
public class CreateSampleTableDataView extends SampleTableDataView {
    private static final long serialVersionUID = -4897261122716237144L;

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.module.mvc.validate.AbstractValidateView#addButtonListener(com.ycignp.veapo.client.framework.module.mvc.ClientController)
     */
    @Override
    protected void addButtonListener(ClientController controller) {
        CreateSampleTableDataAction createExampleTableDataAction = new CreateSampleTableDataAction("",controller);
        okButton.addActionListener(createExampleTableDataAction);
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.module.mvc.validate.AbstractValidateView#initializeData(java.util.List)
     */
    @Override
    protected void initializeData(List<Object> data) {
        idNumberField.setText("");
        numberField2.setText("");
        stringField3.setText("");
        stringField4.setText("");
        enumComboBox5.setSelectedItem(MessageType.Error);
        stringField6.setText("");
        checkBox7.setSelected(false);
    }
}
