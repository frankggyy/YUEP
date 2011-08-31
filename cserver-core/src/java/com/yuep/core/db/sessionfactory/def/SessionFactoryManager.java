/*
 * $Id: SessionFactoryManager.java, 2011-3-21 ����10:10:50 Administrator Exp $
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
 * SessionFactory����ӿ�,���ڹ�����SessionFactory
 * </p>
 * 
 * @author yangtao
 * created 2011-3-21 ����10:10:50
 * modified [who date description]
 * check [who date description]
 */
public interface SessionFactoryManager {
    /**
     * �������ݿ����ƻ�ȡSessionFactory
     * @param dbName
     * @return 
     *       SessionFactory
     */
    public SessionFactory getSessionFactory(String dbName);
    
    /**
     * ���ݳ־û����ȡSession
     * @param boClass
     *        ���־û���
     * @return Sessoin
     */
    public Session openSession(Class<?> boClass);
    
    /**
     * ���ݳ־û����ȡSessionFactory
     * @param boClass
     * @return
     *      SessionFactory
     */
    public SessionFactory getSessionFactory(Class<?> boClass);

}
