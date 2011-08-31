/*
 * $Id: ModifyRealSampleTableDataModel.java, 2010-3-29 обнГ05:02:30 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.biz.client.sample.mvc.model;

import java.util.List;

import com.yuep.core.client.mvc.AbstractClientModel;

/**
 * <p>
 * Title: ModifyRealSampleTableDataModel
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron lee
 * created 2010-3-29 обнГ05:02:30
 * modified [who date description]
 * check [who date description]
 */
public class ModifyRealSampleTableDataModel extends AbstractClientModel<Object>{

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientModel#init(java.lang.Object[])
     */
    @Override
    public void init(Object... objects) {
        boList.clear();
        if(objects != null){
            Object object = objects[0];
            if(object instanceof List){
                List list = (List) object;
                boList.addAll(list);
            }
        }
    }

}
