/*
 * $Id: EditorDecorator.java, 2010-10-18 下午04:17:43 aaron Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.factory.decorator;


/**
 * <p>
 * Title: EditorDecorator
 * </p>
 * <p>
 * Description:默认空实现的装饰器
 * </p>
 * 
 * @author aaron
 * created 2010-10-18 下午04:17:43
 * modified [who date description]
 * check [who date description]
 */
public abstract class DefaultDecorator<T> implements Decorator<T>{

    /**
     * @see com.yuep.core.client.component.factory.decorator.Decorator#contructEditor()
     */
    @Override
    public T contructEditor() {
        return null;
    }
}
