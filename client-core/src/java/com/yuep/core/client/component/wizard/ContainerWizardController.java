/*
 * $Id: ContainerWizardController.java, 2009-6-4 下午06:10:00 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.wizard;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.mvc.AbstractClientController;
import com.yuep.core.client.mvc.AbstractClientModel;
import com.yuep.core.client.util.DialogUtils;

/**
 * <p>
 * Title: ContainerWizardController
 * </p>
 * <p>
 * Description:向导控件整体控制器
 * </p>
 * 
 * @author aaron lee
 * created 2009-6-4 下午06:10:00
 * modified [who date description]
 * check [who date description]
 */
public abstract class ContainerWizardController<T extends Object, V extends ContainerWizardView<T>, M extends AbstractClientModel<T>>
        extends AbstractClientController<T, V, M> {

    /**
     * Contructor
     * @param viewClass
     * @param modelClass
     */
    public ContainerWizardController(Class<V> viewClass, Class<M> modelClass) {
        super(viewClass, modelClass);
    }

    /**
     * 返回第一步的Controller，必须Override该方法，否则界面无法初始化
     * @return BasicWizardController 第一步的Controller
     */
    protected abstract BasicWizardController getFirstBasicController();
    
    /**
     * 返回当前正在操作步骤界面的Controller
     * @return BasicWizardController<T, BasicWizardView<T>, BasicWizardModel<T>> 当前操作步骤界面的Controller
     */
    public BasicWizardController<T, BasicWizardView<T>, BasicWizardModel<T>> getCurrentController() {
        return clientView.getCurrentController();
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.AbstractClientController#initData(java.lang.Object[])
     */
    @Override
    public boolean initData(Object... objects) {
        BasicWizardController firstBasicController = getFirstBasicController();
        if (firstBasicController == null) {
            DialogUtils.showWarnDialog(clientView.getWindow(), ClientCoreContext
                    .getString("common.wizard.container.nullfirst.message"));
            return false;
        }
        clientView.setCurrentController(firstBasicController, null);
        super.initData(objects);
        return firstBasicController.initData(objects);
    }
}
