/*
 * $Id: MgmtScopeModel.java, 2011-4-25 下午12:20:20 luwei Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.smmanager.model;

import java.util.Collection;

import com.yuep.core.client.component.navigator.AbstractTabModel;

/**
 * <p>
 * Title:MgmtScopeModel
 * </p>
 * <p>
 * Description:为用户分配网元管理范围的Model
 * </p>
 * 
 * @author luwei
 * created 2011-4-25 下午12:20:20
 * modified [who date description]
 * check [who date description]
 */
public class MgmtScopeModel extends AbstractTabModel<Object> {

    /**
     * @see com.yuep.core.client.mvc.ClientModel#init(java.lang.Object[])
     */
    @Override
    public void init(Object... objects) {
        boList.clear();
        if (objects != null && objects.length > 0) {
            Object object = objects[0];
            boList.addAll((Collection<? extends Object>) object);
        }
    }
}
