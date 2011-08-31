/*
 * $Id: DefaultClientController.java, 2010-1-20 下午04:00:20 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.mvc;

import com.yuep.core.client.mvc.validate.AbstractValidateView;



/**
 * <p>
 * Title: DefaultClientController
 * </p>
 * <p>
 * Description: 默认的控制类的实现类
 * </p>
 * 
 * @author aaron lee
 * created 2010-1-20 下午04:00:20
 * modified [who date description]
 * check [who date description]
 */
public class DefaultClientController<T extends Object ,V extends AbstractValidateView<T>,M extends AbstractClientModel<T>>
        extends AbstractClientController<T,V,M>{

    /**
     * 构造方法
     * @param viewClass
     * @param modelClass
     */
    public DefaultClientController(Class<V> viewClass, Class<M> modelClass) {
        super(viewClass, modelClass);
    }
}
