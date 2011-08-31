/*
 * $Id: OplogController.java, 2011-4-13 下午05:59:14 WangRui Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.smmanager.controller;

import com.yuep.core.client.component.navigator.AbstractTabController;
import com.yuep.nms.core.client.smmanager.model.OplogModel;
import com.yuep.nms.core.client.smmanager.view.OplogView;

/**
 * <p>
 * Title: OplogController
 * </p>
 * <p>
 * Description:操作日志
 * </p>
 * 
 * @author sufeng
 * created 2011-4-13 下午05:59:14
 * modified [who date description]
 * check [who date description]
 */
public class OplogController extends AbstractTabController<Object, OplogView, OplogModel> {

    public OplogController(Class<OplogView> v, Class<OplogModel> m) {
        super(v, m);
    }

}
