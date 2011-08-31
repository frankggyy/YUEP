/*
 * $Id: IpAddressEditorDecorator.java, 2010-10-18 下午03:20:52 aaron Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.factory.decorator.editor;

import com.yuep.core.client.component.factory.decorator.DefaultDecorator;
import com.yuep.core.client.component.validate.editor.XIpAddressEditor;
import com.yuep.core.client.component.validate.validator.IpAddressValidator;
import com.yuep.core.client.component.validate.validator.Validator;
import com.yuep.core.client.component.validate.validator.ValidatorRange;

/**
 * <p>
 * Title: IpAddressEditorDecorator
 * </p>
 * <p>
 * Description:IP地址自动校验控件的装饰器
 * </p>
 * 
 * @author aaron
 * created 2010-10-18 下午03:20:52
 * modified [who date description]
 * check [who date description]
 */
public class IpAddressEditorDecorator extends DefaultDecorator<XIpAddressEditor<IpAddressValidator>> {

    private Validator validator;
    private boolean isRequire;
    private String propertyName;

    /**
     * 
     * @param propertyName
     * @param isRequire
     * @param range
     */
    public IpAddressEditorDecorator(String propertyName, boolean isRequire, ValidatorRange range) {
        this.isRequire = isRequire;
        if (propertyName == null)
            this.propertyName = "";
        else
            this.propertyName = propertyName;
        validator = new IpAddressValidator(range);
    }

    /**
     * 
     * @param propertyName
     * @param isRequire
     */
    public IpAddressEditorDecorator(String propertyName, boolean isRequire) {
        this(propertyName, isRequire, new ValidatorRange(1, 255));
    }

    /**
     * 
     * @param propertyName
     */
    public IpAddressEditorDecorator(String propertyName) {
        this(propertyName, false, new ValidatorRange(1, 255));
    }

    /**
     * @see com.yuep.core.client.component.factory.decorator.DefaultDecorator#contructEditor()
     */
    @Override
    public XIpAddressEditor<IpAddressValidator> contructEditor() {
        XIpAddressEditor<IpAddressValidator> t = new XIpAddressEditor<IpAddressValidator>();
        return t;
    }

    /**
     * @see com.yuep.core.client.component.factory.decorator.Decorator#decorate(java.lang.Object)
     */
    @Override
    public void decorate(XIpAddressEditor<IpAddressValidator> t) {
        t.setRequire(isRequire);
        t.setPropertyName(propertyName);
        if (validator != null)
            t.setValidator(validator);
    }

}
