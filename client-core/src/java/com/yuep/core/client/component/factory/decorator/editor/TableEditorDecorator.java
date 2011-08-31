/*
 * $Id: XTableEditorDecorator.java, 2010-10-18 下午04:07:22 aaron Exp $
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
import com.yuep.core.client.component.validate.editor.XTableEditor;

/**
 * <p>
 * Title: XTableEditorDecorator
 * </p>
 * <p>
 * Description:表格型自动校验控件的装饰器
 * </p>
 * 
 * @author aaron
 * created 2010-10-18 下午04:07:22
 * modified [who date description]
 * check [who date description]
 */
public class TableEditorDecorator extends DefaultDecorator<XTableEditor>{

    private String propertyName;
    public TableEditorDecorator(String propertyName) {
        if(propertyName == null)
            this.propertyName = "";
        else
            this.propertyName = propertyName;
    }
    /**
     * @see com.yuep.core.client.component.factory.decorator.Decorator#decorate(java.lang.Object)
     */
    @Override
    public void decorate(XTableEditor t) {
        t.setPropertyName(propertyName);
    }
}
