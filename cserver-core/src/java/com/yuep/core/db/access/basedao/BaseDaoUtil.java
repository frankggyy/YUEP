/*
 * $Id: BaseDaoUtil.java, 2011-3-31 ����01:57:08 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.access.basedao;

import com.yuep.base.exception.YuepException;
import com.yuep.core.container.def.CoreContext;
import com.yuep.core.db.access.hibernate.template.HibernateTemplate;
import com.yuep.core.db.access.hibernate.template.HibernateTemplateImpl;
import com.yuep.core.db.module.constants.DbModuleConstants;
import com.yuep.core.db.session.def.Session;
import com.yuep.core.db.sessionfactory.def.SessionFactory;
import com.yuep.core.db.sessionfactory.def.SessionFactoryManager;
import com.yuep.core.db.transaction.def.Transaction;

/**
 * <p>
 * Title: BaseDaoUtil
 * </p>
 * <p>
 * Description:ͨ�õ�dao������
 * </p>
 * 
 * @author sufeng
 * created 2011-3-31 ����01:57:08
 * modified [who date description]
 * check [who date description]
 */
public class BaseDaoUtil {

    /**
     * ִ��sql
     * @param <T>
     * @param clazz
     * @param dao
     * @return sqlִ�н��
     */
    public static <T> Object doSql(Class<T> clazz,DaoExecute<T> dao){
        // ��ȡsession factory manager
        SessionFactoryManager sessionFactoryManager=CoreContext.getInstance().local().getService(DbModuleConstants.SESSIONFACTORYMANAGER_LOCAL_SERVICE, SessionFactoryManager.class);
        // open session
        Session session = sessionFactoryManager.openSession(clazz);
        // ��������
        Transaction transaction=session.beginTransaction();
        try{
            // ִ��
            HibernateTemplate<T> template=new HibernateTemplateImpl<T>(clazz,session);
            Object obj=dao.execute(template);
            // �ύ����
            transaction.commit();
            return obj;
        }catch(Exception th){
            // �����쳣��ع�����
            transaction.rollback();
            throw new YuepException(th);
        }finally{
            session.close();
        }
    }
    
    /**
     * ִ��sql
     * @param <T>
     * @param sessionFactory
     * @param clazz
     * @param dao
     * @return
     */
    public static <T> Object doSql(SessionFactory sessionFactory,Class<T> clazz,DaoExecute<T> dao){
        Session session = sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();
        try{
            HibernateTemplate<T> template=new HibernateTemplateImpl<T>(clazz,session);
            Object obj=dao.execute(template);
            transaction.commit();
            return obj;
        }catch(Exception th){
            transaction.rollback();
            throw new YuepException(th);
        }finally{
            session.close();
        }
    }
    
}
