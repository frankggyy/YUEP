/*
 * $Id: ManagedNodePropertyDaoImpl.java, 2011-3-28 下午03:20:43 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.mocore.dao.impl;

import com.yuep.base.exception.YuepException;
import com.yuep.core.db.access.hibernate.template.HibernateTemplate;
import com.yuep.core.db.access.hibernate.template.HibernateTemplateImpl;
import com.yuep.core.db.session.def.Session;
import com.yuep.core.db.sessionfactory.def.SessionFactory;
import com.yuep.core.db.transaction.def.Transaction;
import com.yuep.nms.core.common.mocore.model.ManagedNodeProperty;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.server.mocore.dao.ManagedNodePropertyDao;

/**
 * <p>
 * Title: ManagedNodePropertyDaoImpl
 * </p>
 * <p>
 * Description:
 * ManagedNodePropertyDao接口实现类
 * </p>
 * 
 * @author yangtao
 * created 2011-3-28 下午03:20:43
 * modified [who date description]
 * check [who date description]
 */
public class ManagedNodePropertyDaoImpl implements ManagedNodePropertyDao {

    public SessionFactory sessionFactory;
    
    public  ManagedNodePropertyDaoImpl(SessionFactory sessionFactory){
        this.sessionFactory=sessionFactory;
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.mocore.dao.ManagedNodePropertyDao#getManagedNodeProperty(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public ManagedNodeProperty getManagedNodeProperty(MoNaming nm) {
        Session session=sessionFactory.openSession();
        try{
            HibernateTemplate<ManagedNodeProperty> hibernateTemplate=new HibernateTemplateImpl<ManagedNodeProperty>(ManagedNodeProperty.class,session);
            return hibernateTemplate.getUniqueEntityByOneProperty("managedNode", nm);
        }finally{
            session.close();
        }
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.mocore.dao.ManagedNodePropertyDao#updateManagedNodeProperty(com.yuep.nms.core.common.mocore.model.ManagedNodeProperty)
     */
    @Override
    public void updateManagedNodeProperty(ManagedNodeProperty managedNodeProperty) {
        Session session=sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();
        try{
            HibernateTemplate<ManagedNodeProperty> hibernateTemplate=new HibernateTemplateImpl<ManagedNodeProperty>(ManagedNodeProperty.class,session);
            hibernateTemplate.updateBatch(managedNodeProperty);
            transaction.commit();
        }catch(Throwable th){
            transaction.rollback();
           throw new YuepException(th);
        } finally{
            session.close();
        }
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.mocore.dao.ManagedNodePropertyDao#deleteManagerNodeProperty(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public void deleteManagerNodeProperty(MoNaming nm) {
        Session session=sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();
        try{
            HibernateTemplate<ManagedNodeProperty> hibernateTemplate=new HibernateTemplateImpl<ManagedNodeProperty>(ManagedNodeProperty.class,session);
            hibernateTemplate.deleteEntityByProperty("managedNode", nm);
            transaction.commit();
        }catch(Throwable th){
            transaction.rollback();
           throw new YuepException(th);
        } finally{
            session.close();
        }
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.mocore.dao.ManagedNodePropertyDao#createManagerNodeProperty(com.yuep.nms.core.common.mocore.naming.MoNaming)
     */
    @Override
    public void createManagerNodeProperty(ManagedNodeProperty managedNodeProperty) {
        Session session=sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();
        try{
            HibernateTemplate<ManagedNodeProperty> hibernateTemplate=new HibernateTemplateImpl<ManagedNodeProperty>(ManagedNodeProperty.class,session);
            hibernateTemplate.saveBatch(managedNodeProperty);
            transaction.commit();
        }catch(Throwable th){
            transaction.rollback();
           throw new YuepException(th);
        } finally{
            session.close();
        }
    }
    @Override
    public ManagedNodeProperty getManagedNodeProperty(final String ip) {

        Session session=sessionFactory.openSession();
        try{
            HibernateTemplate<ManagedNodeProperty> hibernateTemplate=new HibernateTemplateImpl<ManagedNodeProperty>(ManagedNodeProperty.class,session);
            return hibernateTemplate.getUniqueEntityByOneProperty("ip", ip);
        }finally{
            session.close();
        }
    
    }

}
