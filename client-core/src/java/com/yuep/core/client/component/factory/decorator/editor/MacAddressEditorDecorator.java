/*
 * $Id: MacAddressEditorDecorator.java, 2010-10-18 下午03:12:10 aaron Exp $
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
import com.yuep.core.client.component.validate.editor.XMacAddressEditor;
import com.yuep.core.client.component.validate.validator.MacAddressValidator;
import com.yuep.core.client.component.validate.validator.Validator;
import com.yuep.core.client.component.validate.validator.ValidatorRange;

/**
 * <p>
 * Title: MacAddressEditorDecorator
 * </p>
 * <p>
 * Description:MAC地址自动校验控件的装饰器
 * </p>
 * 
 * @author aaron
 * created 2010-10-18 下午03:12:10
 * modified [who date description]
 * check [who date description]
 */
public class MacAddressEditorDecorator extends DefaultDecorator<XMacAddressEditor<MacAddressValidator>> {

    private Validator validator;
    private boolean isRequire;
    private String propertyName;

    public MacAddressEditorDecorator(String propertyName, boolean isRequire) {
        this.isRequire = isRequire;
        if (propertyName == null)
            this.propertyName = "";
        else
            this.propertyName = propertyName;
        validator = new MacAddressValidator(new ValidatorRange(17, 17));
    }

    public MacAddressEditorDecorator(String propertyName) {
        this(propertyName, false);
    }

    /**
     * @see com.yuep.core.client.component.factory.decorator.Decorator#decorate(java.lang.Object)
     */
    @Override
    public void decorate(XMacAddressEditor<MacAddressValidator> t) {
        t.setRequire(isRequire);
        t.setPropertyName(propertyName);
        if (validator != null)
            t.setValidator(validator);
    }

    /**
     * @see com.yuep.core.client.component.factory.decorator.DefaultDecorator#contructEditor()
     */
    @Override
    public XMacAddressEditor<MacAddressValidator> contructEditor() {
        XMacAddressEditor<MacAddressValidator> t = new XMacAddressEditor<MacAddressValidator>();
        return t;
    }
}
