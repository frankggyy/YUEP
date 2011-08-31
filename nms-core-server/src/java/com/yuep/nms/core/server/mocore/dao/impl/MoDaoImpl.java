/*
 * $Id: MoDaoImpl.java, 2011-3-28 下午02:34:28 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.mocore.dao.impl;

import java.util.List;

import com.yuep.base.exception.YuepException;
import com.yuep.core.db.access.hibernate.template.HibernateTemplate;
import com.yuep.core.db.access.hibernate.template.HibernateTemplateImpl;
import com.yuep.core.db.session.def.Session;
import com.yuep.core.db.sessionfactory.def.SessionFactory;
import com.yuep.core.db.transaction.def.Transaction;
import com.yuep.nms.core.common.mocore.model.Mo;
import com.yuep.nms.core.server.mocore.dao.MoDao;

/**
 * <p>
 * Title: MoDaoImpl
 * </p>
 * <p>
 * Description:
 * MoDao实现类
 * </p>
 * 
 * @author yangtao
 * created 2011-3-28 下午02:34:28
 * modified [who date description]
 * check [who date description]
 */
public class MoDaoImpl implements MoDao {

    private SessionFactory sessionFactory;
    
    public MoDaoImpl(SessionFactory sessionFactory){
        this.sessionFactory=sessionFactory;
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.mocore.dao.MoDao#createMos(java.util.List)
     */
    @Override
    public void createMos(List<Mo> mos) {
        Session session=sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();
        try{
            HibernateTemplate<Mo> hibernateTemplate=new HibernateTemplateImpl<Mo>(Mo.class,session);
            hibernateTemplate.saveBatch(mos);
            transaction.commit();
        }catch(Throwable th){
            transaction.rollback();
            throw new YuepException(th) ;
        }finally{
            session.close();
        }
        
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.mocore.dao.MoDao#deleteMos(java.util.List)
     */
    @Override
    public void deleteMos(List<Mo> mos) {
        Session session=sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();
        try{
            HibernateTemplate<Mo> hibernateTemplate=new HibernateTemplateImpl<Mo>(Mo.class,session);
            hibernateTemplate.deleteAllEntities(mos);
            transaction.commit();
        }catch(Throwable th){
            transaction.rollback();
            throw new YuepException(th) ;
        }finally{
            session.close();
        }
        
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.mocore.dao.MoDao#getAllMos()
     */
    @Override
    public List<Mo> getAllMos() {
        Session session=sessionFactory.openSession();
        try{
            HibernateTemplate<Mo> hibernateTemplate=new HibernateTemplateImpl<Mo>(Mo.class,session);
            return hibernateTemplate.getAllEntities();
        }catch(Throwable th){
            throw new YuepException(th) ;
        }finally{
            session.close();
        }
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.mocore.dao.MoDao#updateMos(java.util.List)
     */
    @Override
    public void updateMos(List<Mo> mos) {
        Session session=sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();
        try{
            HibernateTemplate<Mo> hibernateTemplate=new HibernateTemplateImpl<Mo>(Mo.class,session);
            hibernateTemplate.updateBatch(mos);
            transaction.commit();
        }catch(Throwable th){
            transaction.rollback();
            throw new YuepException(th) ;
        }finally{
            session.close();
        }
        
    }

}
