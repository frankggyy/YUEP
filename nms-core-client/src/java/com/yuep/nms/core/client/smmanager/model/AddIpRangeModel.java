/*
 * $Id: AddIpRangeModel.java, 2011-4-25 下午12:20:20 luwei Exp $
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
import com.yuep.nms.core.common.smcore.model.IpRange;
/**
 * <p>
 * Title:AddIpRangeModel
 * </p>
 * <p>
 * Description:为用户添加登陆IP范围的Model
 * </p>
 * 
 * @author luwei
 * created 2011-4-25 下午12:20:20
 * modified [who date description]
 * check [who date description]
 */
public class AddIpRangeModel extends AbstractClientModel<IpRange> {

    @Override
    public void init(Object... objects) {
        if (objects != null && objects.length > 0) {
            Object object = objects[0];
            if (object instanceof List) {
                boList.addAll((Collection<? extends IpRange>) object);
            }
        }

    }

}
