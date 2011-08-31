/*
 * $Id: UserMaintenanceModel.java, 2011-4-25 下午12:57:19 luwei Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.smmanager.model;

import com.yuep.core.client.mvc.AbstractClientModel;

/**
 * <p>
 * Title: UserMaintenanceModel
 * </p>
 * <p>
 * Description:展示用户详细信息和管理网元范围的Model
 * </p>
 * 
 * @author luwei
 * created 2011-4-25 下午12:57:19
 * modified [who date description]
 * check [who date description]
 */
public class UserMaintenanceModel extends AbstractClientModel<Object> {

    /**
     * @see com.yuep.core.client.mvc.ClientModel#init(java.lang.Object[])
     */
    @Override
    public void init(Object... objects) {
        boList.clear();
        if (objects != null && objects.length > 0) {
            for (int i = 0; i < objects.length; i++) {
                if (objects[i] != null) {
                    boList.add(objects[i]);
                }
            }

        }
    }

}
