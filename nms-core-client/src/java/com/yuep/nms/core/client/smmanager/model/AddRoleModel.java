/*
 * $Id: AddRoleModel.java, 2011-4-2 下午03:11:41 WangRui Exp $
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

/**
 * <p>
 * Title: AddRoleModel
 * </p>
 * <p>
 * Description:增加角色
 * </p>
 * 
 * @author WangRui
 * created 2011-4-2 下午03:11:41
 * modified [who date description]
 * check [who date description]
 */
public class AddRoleModel extends AbstractClientModel<Object> {
    @Override
    public void init(Object... objects) {
        boList.clear();           
    }

    /**
     * @see com.yuep.core.client.mvc.AbstractClientModel#modifyDatas(java.util.List)
     */
    @Override
    public void modifyDatas(List<Object> datas) {
        if(!CollectionUtils.isEmpty(datas)){
            setChanged();
            notifyObservers(datas);
        }   
    }   
}
