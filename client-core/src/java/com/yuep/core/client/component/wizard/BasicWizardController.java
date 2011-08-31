/*
 * $Id: BasicWizardController.java, 2009-6-4 ����11:10:03 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.wizard;

import com.yuep.core.client.mvc.AbstractClientController;


/**
 * <p>
 * Title: BasicWizardController
 * </p>
 * <p>
 * Description:�򵼿ؼ��е�������Ŀ�����
 * </p>
 * 
 * @author aaron lee
 * created 2009-6-4 ����11:10:03
 * modified [who date description]
 * check [who date description]
 */
public abstract class BasicWizardController<T extends Object, V extends BasicWizardView<T>, M extends BasicWizardModel<T>> extends AbstractClientController<T ,V ,M>{

    /**
     * Contructor
     * @param viewClass
     * @param modelClass
     */
    public BasicWizardController(Class<V> viewClass, Class<M> modelClass) {
        super(viewClass, modelClass);
    }

    /**
     * ��ȡ��ǰ����ǰһ����Controller
     * @return BasicWizardController ǰһ����Controller
     */
    protected abstract BasicWizardController getPreviousBasicWizardController();
    
    /**
     * ��ȡ��ǰ������һ����Controller
     * @return BasicWizardController ��һ����Controller
     */
    protected abstract BasicWizardController getNextBasicWizardController();
    
    /**
     * ��ȡ�ͻ���Model
     * @return M ����Model
     */
    protected M getClientModel(){
        return clientModel;
    }
}
