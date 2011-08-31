/*
 * $Id: ModifySampleTableDataView.java, 2010-3-29 ÏÂÎç05:33:43 aaron lee Exp $
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

import org.apache.commons.collections.CollectionUtils;

import com.yuep.core.client.mvc.ClientController;
import com.yuep.nms.biz.client.sample.mvc.action.ModifySampleTableDataAction;
import com.yuep.nms.biz.client.sample.mvc.model.RealSampleTableData;
import com.yuep.nms.biz.client.sample.mvc.model.SampleTableData;

/**
 * <p>
 * Title: ModifySampleTableDataView
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron lee
 * created 2010-3-29 ÏÂÎç05:33:43
 * modified [who date description]
 * check [who date description]
 */
public class ModifySampleTableDataView extends SampleTableDataView{

    private static final long serialVersionUID = -4897261122716237144L;
    /**
     * @param controller
     */
    @Override
    protected void addButtonListener(ClientController controller) {
        ModifySampleTableDataAction createExampleTableDataAction = new ModifySampleTableDataAction("",controller);
        okButton.addActionListener(createExampleTableDataAction);
    }
    
    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.module.mvc.validate.AbstractValidateView#initializeData(java.util.List)
     */
    @Override
    protected void initializeData(List<Object> data) {
        if(CollectionUtils.isEmpty(data))
            return;
        Object object = data.get(0);
        if(object != null && object instanceof SampleTableData){
            RealSampleTableData realExampleTableData = ((SampleTableData)object).getRealExampleTableData();
            idNumberField.setText(String.valueOf(realExampleTableData.getId()));
            numberField2.setText(String.valueOf(realExampleTableData.getColumn2()));
            stringField3.setText(realExampleTableData.getColumn3());
            stringField4.setText(realExampleTableData.getColumn4());
            enumComboBox5.setSelectedItem(realExampleTableData.getColumn5());
            stringField6.setText(String.valueOf(realExampleTableData.getColumn6()));
            checkBox7.setSelected(realExampleTableData.isColumn7());
        }
    }
}
