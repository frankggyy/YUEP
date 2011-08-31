/*
 * $Id: Decorator.java, 2010-10-15 下午03:26:17 aaron Exp $
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
 * Title: Decorator
 * </p>
 * <p>
 * Description:客户端控件的装饰器,是客户端控件扩展接口
 * </p>
 * 
 * @author aaron
 * created 2010-10-15 下午03:26:17
 * modified [who date description]
 * check [who date description]
 */
public interface Decorator<T> {

    /**
     * 构造控件,并返回构造的控件对象
     * @return T 具体的控件
     */
    T contructEditor();
    
    /**
     * 装饰控件，对参数中的控件进行装饰
     * @param t 要进行装饰的控件对象
     */
    void decorate(T t);
}
