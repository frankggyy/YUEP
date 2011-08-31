/*
 * $Id: ResultHandlerAdaptor.java, 2009-11-30 下午02:34:14 jimsu Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.concurrent.impl.collaboration.syncwait;

import com.yuep.base.concurrent.def.collaboration.syncwait.ResultHandler;
import com.yuep.base.concurrent.def.collaboration.syncwait.ResultStauts;

/**
 * <p>
 * Title: ResultHandlerAdaptor
 * </p>
 * <p>
 * Description: 适配器，方便写匿名类
 * </p>
 * 
 * @author sufeng
 * created 2009-11-30 下午02:34:14
 * modified [who date description]
 * check [who date description]
 */
public class ResultHandlerAdaptor implements ResultHandler {

    @Override
    public ResultStauts getSingleResult() {
        return null;
    }

    @Override
    public void postData(Object data) {
    }

}
