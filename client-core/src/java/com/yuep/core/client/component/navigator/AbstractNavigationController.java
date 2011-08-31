/*
 * $Id: AbstractNavigationController.java, 2009-3-16 上午10:49:35 Victor Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.navigator;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.mvc.AbstractClientController;

/**
 * <p>
 * Title: AbstractNavigationController 
 * </p>
 * <p>
 * Description:抽象的导航控件控制器
 * </p>
 * 
 * @author Victor,aaron lee
 * created 2009-3-16 上午10:49:35
 * @modified aaron lee 2010-3-30 将其从<code>AbstractClientController<T, V, M></code>继承
 * check [who date description]
 */
public class AbstractNavigationController<T, V extends AbstractNavigationView<T>, M extends AbstractNavigationModel<T>>
        extends AbstractClientController<T, V, M> {

    /**
     * 构造方法
     * @param viewClass
     * @param modelClass
     */
    public AbstractNavigationController(Class<V> viewClass, Class<M> modelClass) {
        super(viewClass, modelClass);
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.module.mvc.AbstractClientController#initData()
     */
    @Override
    public boolean initData(Object... objects) {
        boolean result = super.initData(objects);
        clientView.resetNavigation();
        clientModel.contructNavigation();
        return result;
    }

    /**
     * 国际化获取接口封装
     * @return <code>String</code> 国际化后的字符串
     */
    protected String getString(String key, Object... values) {
        return ClientCoreContext.getString(key, values);
    }
}
