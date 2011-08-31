/*
 * $Id: NumberEditorDecorator.java, 2010-10-18 下午03:07:31 aaron Exp $
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
import com.yuep.core.client.component.validate.editor.XNumberEditor;
import com.yuep.core.client.component.validate.validator.NumberValidator;
import com.yuep.core.client.component.validate.validator.Validator;
import com.yuep.core.client.component.validate.validator.ValidatorRange;

/**
 * <p>
 * Title: NumberEditorDecorator
 * </p>
 * <p>
 * Description:数字型的自动校验控件的装饰器
 * </p>
 * 
 * @author aaron
 * created 2010-10-18 下午03:07:31
 * modified [who date description]
 * check [who date description]
 */
public class NumberEditorDecorator extends DefaultDecorator<XNumberEditor<NumberValidator>> {

    private Validator validator;
    private boolean isRequire;
    private String propertyName;

    /**
     * 构造函数
     * @param propertyName
     * @param isRequire
     * @param range
     */
    public NumberEditorDecorator(String propertyName, boolean isRequire, ValidatorRange range) {
        this.isRequire = isRequire;
        if (propertyName == null)
            this.propertyName = "";
        else
            this.propertyName = propertyName;
        validator = new NumberValidator(range);
    }

    /**
     * 构造函数
     * @param propertyName
     * @param isRequire
     * @param min
     * @param max
     */
    public NumberEditorDecorator(String propertyName, boolean isRequire, int min, int max) {
        this(propertyName, isRequire, new ValidatorRange(min, max));
    }

    /**
     *构造函数
     * @param propertyName
     * @param min
     * @param max
     */
    public NumberEditorDecorator(String propertyName, int min, int max) {
        this(propertyName, false, new ValidatorRange(min, max));
    }

    /**
     * 构造函数
     * @param propertyName
     * @param range
     */
    public NumberEditorDecorator(String propertyName, ValidatorRange range) {
        this(propertyName, false, range);
    }

    /**
     * @see com.yuep.core.client.component.factory.decorator.DefaultDecorator#contructEditor()
     */
    @Override
    public XNumberEditor<NumberValidator> contructEditor() {
        XNumberEditor<NumberValidator> t = new XNumberEditor<NumberValidator>();
        return t;
    }

    /**
     * @see com.yuep.core.client.component.factory.decorator.Decorator#decorate(java.lang.Object)
     */
    @Override
    public void decorate(XNumberEditor<NumberValidator> t) {
        t.setRequire(isRequire);
        t.setPropertyName(propertyName);
        if (validator != null)
            t.setValidator(validator);
    }

}
