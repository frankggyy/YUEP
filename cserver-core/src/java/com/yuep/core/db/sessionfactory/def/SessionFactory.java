/*
 * $Id: SessionFactory.java, 2011-3-21 上午09:32:33 Administrator Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.sessionfactory.def;

import com.yuep.core.db.session.def.Session;
/**
 * <p>
 * Title: SessionFactory
 * </p>
 * <p>
 * Description:
 * 数据库访问会话Session工厂
 * </p>
 * 
 * @author yangtao
 * created 2011-3-21 上午09:32:33
 * modified [who date description]
 * check [who date description]
 */
public interface SessionFactory {

    /**
     * 打开一个Session
     * @return
     *       返回被打开的Session
     */
    public abstract Session openSession();

    /**
     * 关闭SessionFactory
     */
    public abstract void close();
    /**
     * SessionFactory是否被关闭
     * @return
     *     false:没有关闭
     *     true:关闭
     */
    public abstract boolean isClosed();


}
