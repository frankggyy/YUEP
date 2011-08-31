/*
 * $Id: ContainerWizardController.java, 2009-6-4 ����06:10:00 aaron lee Exp $
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
 * Description:�򵼿ؼ����������
 * </p>
 * 
 * @author aaron lee
 * created 2009-6-4 ����06:10:00
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
     * ���ص�һ����Controller������Override�÷�������������޷���ʼ��
     * @return BasicWizardController ��һ����Controller
     */
    protected abstract BasicWizardController getFirstBasicController();
    
    /**
     * ���ص�ǰ���ڲ�����������Controller
     * @return BasicWizardController<T, BasicWizardView<T>, BasicWizardModel<T>> ��ǰ������������Controller
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
