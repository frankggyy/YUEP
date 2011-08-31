/*
 * $Id: ModifyPermissionBundleModel.java, 2011-4-14 下午03:17:21 WangRui Exp $
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
import java.util.List;

import com.yuep.core.client.mvc.AbstractClientModel;
import com.yuep.nms.core.common.smcore.model.PermissionGroup;

/**
 * <p>
 * Title: ModifyPermissionBundleModel
 * </p>
 * <p>
 * Description:修改权限集
 * </p>
 * 
 * @author WangRui
 * created 2011-4-14 下午03:17:21
 * modified [who date description]
 * check [who date description]
 */
public class ModifyPermissionBundleModel extends AbstractClientModel<PermissionGroup> {

    /**
     * @see com.yuep.core.client.mvc.ClientModel#init(java.lang.Object[])
     */
    @SuppressWarnings("unchecked")
    @Override
    public void init(Object... objects) {
        this.boList.clear();
        if (objects != null && objects.length > 0) {
            Object object = objects[0];
            if (object instanceof PermissionGroup)
                this.boList.add((PermissionGroup) object);
            else if (object instanceof List) {
                this.boList.addAll((Collection<? extends PermissionGroup>) object);
            }
        }
    }
}
