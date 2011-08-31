/*
 * $Id: UpdateRoleModel.java, 2011-4-13 ÉÏÎç11:36:29 WangRui Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.smmanager.model;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.yuep.core.client.mvc.AbstractClientModel;
import com.yuep.nms.core.common.smcore.model.Role;

/**
 * <p>
 * Title: UpdateRoleModel
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author WangRui
 * created 2011-4-13 ÉÏÎç11:36:29
 * modified [who date description]
 * check [who date description]
 */
public class UpdateRoleModel extends AbstractClientModel<Object> {

    /**
     * @see com.yuep.core.client.mvc.ClientModel#init(java.lang.Object[])
     */
    @SuppressWarnings("unchecked")
    @Override
    public void init(Object... objects) {
        boList.clear();
        if(objects != null && objects.length > 0){
            if(null!=objects[0] && objects[0] instanceof List){
               Role role =  ((List<Role>)objects[0]).get(0);
               boList.add(role);
            }
        }
    }
    /**
     * @see com.yuep.core.client.mvc.AbstractClientModel#modifyDatas(java.util.List)
     */
    @Override
    public void modifyDatas(List<Object> datas) {
        if(CollectionUtils.isNotEmpty(datas)){           
                setChanged();
                notifyObservers(datas);
            }            
    }
}
