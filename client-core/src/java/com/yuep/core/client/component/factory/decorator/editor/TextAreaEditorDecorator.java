/*
 * $Id: TextAreaEditorDecorator.java, 2010-10-18 下午03:55:26 aaron Exp $
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
import com.yuep.core.client.component.validate.editor.XTextAreaEditor;
import com.yuep.core.client.component.validate.validator.StringValidator;
import com.yuep.core.client.component.validate.validator.Validator;
import com.yuep.core.client.component.validate.validator.ValidatorRange;

/**
 * <p>
 * Title: TextAreaEditorDecorator
 * </p>
 * <p>
 * Description:文本区自动校验控件的装饰器
 * </p>
 * 
 * @author aaron
 * created 2010-10-18 下午03:55:26
 * modified [who date description]
 * check [who date description]
 */
public class TextAreaEditorDecorator extends DefaultDecorator<XTextAreaEditor<StringValidator>> {

    private Validator validator;
    private boolean isRequire;
    private String propertyName;

    public TextAreaEditorDecorator(String propertyName, boolean isRequire, ValidatorRange range) {
        this.isRequire = isRequire;
        if (propertyName == null)
            this.propertyName = "";
        else
            this.propertyName = propertyName;
        validator = new StringValidator(range);
    }
    /**
     * 
     * @param propertyName
     * @param isRequire
     * @param min
     * @param max
     */
    public TextAreaEditorDecorator(String propertyName, boolean isRequire, int min, int max) {
        this(propertyName, isRequire, new ValidatorRange(min, max));
    }
    /**
     * 
     * @param propertyName
     * @param min
     * @param max
     */
    public TextAreaEditorDecorator(String propertyName, int min, int max) {
        this(propertyName, false, new ValidatorRange(min, max));
    }
    /**
     * 
     * @param propertyName
     * @param range
     */
    public TextAreaEditorDecorator(String propertyName, ValidatorRange range) {
        this(propertyName, false, range);
    }
    /**
     * 
     * @param propertyName
     */
    public TextAreaEditorDecorator(String propertyName) {
        this(propertyName, false, new ValidatorRange(0, 256));
    }

    /**
     * @see com.yuep.core.client.component.factory.decorator.DefaultDecorator#contructEditor()
     */
    @Override
    public XTextAreaEditor<StringValidator> contructEditor() {
        XTextAreaEditor<StringValidator> t = new XTextAreaEditor<StringValidator>();
        return t;
    }

    /**
     * @see com.yuep.core.client.component.factory.decorator.Decorator#decorate(java.lang.Object)
     */
    @Override
    public void decorate(XTextAreaEditor<StringValidator> t) {
        t.setRequire(isRequire);
        t.setPropertyName(propertyName);
        if (validator != null)
            t.setValidator(validator);
    }
}
