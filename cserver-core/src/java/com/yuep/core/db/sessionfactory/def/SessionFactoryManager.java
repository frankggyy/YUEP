/*
 * $Id: SessionFactoryManager.java, 2011-3-21 上午10:10:50 Administrator Exp $
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
 * Title: SessionFactoryManager
 * </p>
 * <p>
 * Description:
 * SessionFactory管理接口,用于管理多个SessionFactory
 * </p>
 * 
 * @author yangtao
 * created 2011-3-21 上午10:10:50
 * modified [who date description]
 * check [who date description]
 */
public interface SessionFactoryManager {
    /**
     * 根据数据库名称获取SessionFactory
     * @param dbName
     * @return 
     *       SessionFactory
     */
    public SessionFactory getSessionFactory(String dbName);
    
    /**
     * 根据持久化类获取Session
     * @param boClass
     *        被持久化类
     * @return Sessoin
     */
    public Session openSession(Class<?> boClass);
    
    /**
     * 根据持久化类获取SessionFactory
     * @param boClass
     * @return
     *      SessionFactory
     */
    public SessionFactory getSessionFactory(Class<?> boClass);

}
