/*
 * $Id: DefaultSessionFactory.java, 2011-3-21 上午10:23:01 Administrator Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.sessionfactory.impl;


import com.yuep.core.db.session.def.Session;
import com.yuep.core.db.session.impl.SessionImpl;
import com.yuep.core.db.sessionfactory.def.SessionFactory;

/**
 * <p>
 * Title: DefaultSessionFactory
 * </p>
 * <p>
 * Description:
 * 默认SessionFactory实现
 * </p>
 * 
 * @author yangtao
 * created 2011-3-21 上午10:23:01
 * modified [who date description]
 * check [who date description]
 */
public class SessionFactoryImpl implements SessionFactory {

    private org.hibernate.SessionFactory hibernateSessionFactory;
    public SessionFactoryImpl(org.hibernate.SessionFactory hibernateSessionFactory){
        this.hibernateSessionFactory=hibernateSessionFactory;
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.db.sessionfactory.def.SessionFactory#close()
     */
    @Override
    public void close() {
        hibernateSessionFactory.close();
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.db.sessionfactory.def.SessionFactory#isClosed()
     */
    @Override
    public boolean isClosed() {
        return hibernateSessionFactory.isClosed();
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.db.sessionfactory.def.SessionFactory#openSession()
     */
    @Override
    public Session openSession() {
        org.hibernate.Session hibernateSession=hibernateSessionFactory.openSession();
        return new SessionImpl(hibernateSession);
    }
}
