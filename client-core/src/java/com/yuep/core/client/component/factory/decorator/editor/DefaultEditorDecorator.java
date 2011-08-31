/*
 * $Id: DefaultEditorDecorator.java, 2010-10-18 下午02:16:08 aaron Exp $
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
import com.yuep.core.client.component.validate.validator.Validator;
import com.yuep.core.client.component.validate.validator.ValidatorRange;

/**
 * <p>
 * Title: DefaultEditorDecorator
 * </p>
 * <p>
 * Description:自动校验控件的默认装饰器
 * </p>
 * 
 * @author aaron
 * created 2010-10-18 下午02:16:08
 * modified [who date description]
 * check [who date description]
 */
public class DefaultEditorDecorator extends DefaultDecorator<DefaultXEditor<Validator>>{

    private Validator validator;
    private boolean isRequire;
    private String propertyName;
    public DefaultEditorDecorator(String propertyName,boolean isRequire,ValidatorRange range,Validator validator) {
        this.isRequire = isRequire;
        if(propertyName == null)
            this.propertyName = "";
        else
            this.propertyName = propertyName;
        if(validator != null){
            validator.setRange(range);
            this.validator = validator;
        }
    }
    /**
     * 
     * @param propertyName
     * @param isRequire
     * @param min
     * @param max
     * @param validator
     */
    public DefaultEditorDecorator(String propertyName,boolean isRequire,int min,int max,Validator validator) {
        this(propertyName, isRequire, new ValidatorRange(min,max), validator);
    }
    /**
     * 
     * @param propertyName
     * @param min
     * @param max
     * @param validator
     */
    public DefaultEditorDecorator(String propertyName,int min,int max,Validator validator) {
        this(propertyName,false, new ValidatorRange(min,max), validator);
    }
    /**
     * 
     * @param propertyName
     * @param range
     * @param validator
     */
    public DefaultEditorDecorator(String propertyName,ValidatorRange range,Validator validator) {
        this(propertyName,false, range, validator);
    }

    /**
     * @see com.yuep.core.client.component.factory.decorator.DefaultDecorator#contructEditor()
     */
    @Override
    public DefaultXEditor<Validator> contructEditor() {
        DefaultXEditor<Validator> t = new DefaultXEditor<Validator>();
        return t;
    }

    /**
     * @see com.yuep.core.client.component.factory.decorator.Decorator#decorate(java.lang.Object)
     */
    @Override
    public void decorate(DefaultXEditor<Validator> t) {
        t.setRequire(isRequire);
        t.setPropertyName(propertyName);
        if(validator != null)
            t.setValidator(validator);
    }

}
