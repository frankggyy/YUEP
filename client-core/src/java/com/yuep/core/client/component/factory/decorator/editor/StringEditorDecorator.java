/*
 * $Id: StringEditorDecorator.java, 2010-10-18 下午02:58:53 aaron Exp $
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
import com.yuep.core.client.component.validate.editor.DefaultXEditor;
import com.yuep.core.client.component.validate.validator.StringValidator;
import com.yuep.core.client.component.validate.validator.Validator;
import com.yuep.core.client.component.validate.validator.ValidatorRange;

/**
 * <p>
 * Title: StringEditorDecorator
 * </p>
 * <p>
 * Description:字符串自动校验控件的装饰器
 * </p>
 * 
 * @author aaron
 * created 2010-10-18 下午02:58:53
 * modified [who date description]
 * check [who date description]
 */
public class StringEditorDecorator extends DefaultDecorator<DefaultXEditor<StringValidator>> {

    private Validator validator;
    private boolean isRequire;
    private String propertyName;

    /**
     * 构造函数
     * @param propertyName
     * @param isRequire
     * @param range
     */
    public StringEditorDecorator(String propertyName, boolean isRequire, ValidatorRange range) {
        this.isRequire = isRequire;
        if (propertyName == null)
            this.propertyName = "";
        else
            this.propertyName = propertyName;
        validator = new StringValidator(range);
    }
    /**
     * 构造函数 
     * @param propertyName
     * @param isRequire
     * @param min
     * @param max
     */
    public StringEditorDecorator(String propertyName, boolean isRequire, int min, int max) {
        this(propertyName, isRequire, new ValidatorRange(min, max));
    }
    /**
     * 构造函数
     * @param propertyName
     * @param min
     * @param max
     */
    public StringEditorDecorator(String propertyName, int min, int max) {
        this(propertyName, false, new ValidatorRange(min, max));
    }
    /**
     * 构造函数
     * @param propertyName
     * @param range
     */
    public StringEditorDecorator(String propertyName, ValidatorRange range) {
        this(propertyName, false, range);
    }

    /**
     * 构造函数
     * @param propertyName
     */
    public StringEditorDecorator(String propertyName) {
        this(propertyName, false, new ValidatorRange(0, 256));
    }

    /**
     * 
     * @see com.yuep.core.client.component.factory.decorator.DefaultDecorator#contructEditor()
     */
    @Override
    public DefaultXEditor<StringValidator> contructEditor() {
        DefaultXEditor<StringValidator> t = new DefaultXEditor<StringValidator>();
        return t;
    }

    /**
     * @see com.yuep.core.client.component.factory.decorator.Decorator#decorate(java.lang.Object)
     */
    @Override
    public void decorate(DefaultXEditor<StringValidator> t) {
        t.setRequire(isRequire);
        t.setPropertyName(propertyName);
        if (validator != null)
            t.setValidator(validator);
    }
}
