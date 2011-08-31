/*
 * $Id: CheckBoxEditorDecorator.java, 2010-10-18 下午04:03:05 aaron Exp $
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
import com.yuep.core.client.component.validate.editor.XCheckBoxEditor;

/**
 * <p>
 * Title: CheckBoxEditorDecorator
 * </p>
 * <p>
 * Description: CheckBox自动校验控件的装饰器
 * </p>
 * 
 * @author aaron
 * created 2010-10-18 下午04:03:05
 * modified [who date description]
 * check [who date description]
 */
public class CheckBoxEditorDecorator extends DefaultDecorator<XCheckBoxEditor>{

    private String propertyName;
    public CheckBoxEditorDecorator(String propertyName) {
        if(propertyName == null)
            this.propertyName = "";
        else
            this.propertyName = propertyName;
    }
    
    /**
     * @see com.yuep.core.client.component.factory.decorator.DefaultDecorator#contructEditor()
     */
    @Override
    public XCheckBoxEditor contructEditor() {
        XCheckBoxEditor t = new XCheckBoxEditor();
        return t;
    }

    /**
     * @see com.yuep.core.client.component.factory.decorator.Decorator#decorate(java.lang.Object)
     */
    @Override
    public void decorate(XCheckBoxEditor t) {
        t.setPropertyName(propertyName);
    }
}
