/*
 * $Id: PasswordEditorDecorator.java, 2010-10-18 下午03:58:34 aaron Exp $
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
import com.yuep.core.client.component.validate.editor.XPasswordFieldEditor;

/**
 * <p>
 * Title: PasswordEditorDecorator
 * </p>
 * <p>
 * Description:密码型自动校验控件的装饰器
 * </p>
 * 
 * @author aaron
 * created 2010-10-18 下午03:58:34
 * modified [who date description]
 * check [who date description]
 */
public class PasswordEditorDecorator extends DefaultDecorator<XPasswordFieldEditor>{

    private String propertyName;
    public PasswordEditorDecorator(String propertyName) {
        if(propertyName == null)
            this.propertyName = "";
        else
            this.propertyName = propertyName;
    }

    /**
     * @see com.yuep.core.client.component.factory.decorator.DefaultDecorator#contructEditor()
     */
    @Override
    public XPasswordFieldEditor contructEditor() {
        XPasswordFieldEditor t = new XPasswordFieldEditor();
        return t;
    }

    /**
     * @see com.yuep.core.client.component.factory.decorator.Decorator#decorate(java.lang.Object)
     */
    @Override
    public void decorate(XPasswordFieldEditor t) {
        t.setPropertyName(propertyName);
    }
}
