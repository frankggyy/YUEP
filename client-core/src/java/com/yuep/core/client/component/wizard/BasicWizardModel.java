/*
 * $Id: BasicWizardModel.java, 2009-6-4 上午11:10:48 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.wizard;

import java.util.List;

import com.yuep.core.client.mvc.AbstractClientModel;

/**
 * <p>
 * Title: BasicWizardModel
 * </p>
 * <p>
 * Description:向导控件中单步界面的Model
 * </p>
 * 
 * @author aaron lee
 * created 2009-6-4 上午11:10:48
 * modified [who date description]
 * check [who date description]
 */
public abstract class BasicWizardModel<T extends Object> extends AbstractClientModel<T> {

    /*
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.ClientModel#init(java.lang.Object[])
     */
    @Override
    public void init(Object... objects) {
        if (objects.length > 0) {
            if (objects[0] instanceof List)
                boList = (List<T>) objects[0];
            else if (objects[0] == null) 
                boList.clear();
        }
    }
}
