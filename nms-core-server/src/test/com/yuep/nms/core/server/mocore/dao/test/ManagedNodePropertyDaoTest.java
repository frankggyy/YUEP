/*
 * $Id: ManagedNodePropertyDaoTest.java, 2011-5-10 下午01:13:20 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.mocore.dao.test;

import com.yuep.core.db.access.hibernate.template.HibernateTemplate;
import com.yuep.core.db.access.hibernate.template.HibernateTemplateImpl;
import com.yuep.core.db.session.def.Session;
import com.yuep.core.db.sessionfactory.def.SessionFactory;
import com.yuep.core.db.test.DbBaseCase;
import com.yuep.core.db.test.SessionFactoryBuilder;
import com.yuep.core.db.transaction.def.Transaction;
import com.yuep.nms.core.common.mocore.model.ManagedNodeProperty;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.server.mocore.dao.ManagedNodePropertyDao;
import com.yuep.nms.core.server.mocore.dao.impl.ManagedNodePropertyDaoImpl;

/**
 * <p>
 * Title: ManagedNodePropertyDaoTest
 * </p>
 * <p>
 * Description:
 * ManagedNodePropertyDao管理节点单元测试类
 * </p>
 * 
 * @author yangtao
 * created 2011-5-10 下午01:13:20
 * modified [who date description]
 * check [who date description]
 */
public class ManagedNodePropertyDaoTest extends DbBaseCase {
    
    private ManagedNodePropertyDao managedNodePropertyDao;
    private SessionFactory sessionFactory;
    @Override
    public void setUp(){
         sessionFactory=this.getSessionFactory();
        if(sessionFactory==null)
            sessionFactory=new SessionFactoryBuilder().build();
        managedNodePropertyDao=new ManagedNodePropertyDaoImpl(sessionFactory);
    }
    
    @Override
    public void tearDown(){
        clearData();
    }
    
    private void clearData(){
        Session session=sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();
        try{
            HibernateTemplate<ManagedNodeProperty> hibernateTemplate=new HibernateTemplateImpl<ManagedNodeProperty>(ManagedNodeProperty.class,session);
            hibernateTemplate.deleteAllEntities();
            transaction.commit();
        }catch(Exception ex){
            transaction.rollback();
            throw new RuntimeException(ex);
        }finally{
            session.close();
        }
    }
    
    

    public void testCreateManagedNodeProperty(){
        ManagedNodeProperty managedNodeProperty=newManagedNodeProperty();
     
        managedNodePropertyDao.createManagerNodeProperty(managedNodeProperty);
        
        ManagedNodeProperty managedNodePropertyInDb=managedNodePropertyDao.getManagedNodeProperty(managedNodeProperty.getManagedNode());
        assertNotNull(managedNodePropertyInDb);
    }
    
    
    
    public void testGetManagedNodePropertyByIp(){
        ManagedNodeProperty managedNodeProperty=newManagedNodeProperty();
        managedNodePropertyDao.createManagerNodeProperty(managedNodeProperty);
        
        ManagedNodeProperty managedNodePropertyInDb=managedNodePropertyDao.getManagedNodeProperty(managedNodeProperty.getIp());
        assertNotNull(managedNodePropertyInDb);
    }
    
    public void testUpdateManagedNodeProperty(){
        ManagedNodeProperty managedNodeProperty=newManagedNodeProperty();
        managedNodePropertyDao.createManagerNodeProperty(managedNodeProperty);
        
        managedNodeProperty.setIp("192.169.1.1");
        managedNodePropertyDao.updateManagedNodeProperty(managedNodeProperty);
        
        ManagedNodeProperty managedNodePropertyInDb=managedNodePropertyDao.getManagedNodeProperty(managedNodeProperty.getManagedNode());
        assertEquals("192.169.1.1", managedNodePropertyInDb.getIp());
    }
    
    
    public void testDeleteManagerNodeProperty(){
        ManagedNodeProperty managedNodeProperty=newManagedNodeProperty();
        managedNodePropertyDao.createManagerNodeProperty(managedNodeProperty);
        
        managedNodePropertyDao.deleteManagerNodeProperty(managedNodeProperty.getManagedNode());
        
        ManagedNodeProperty managedNodePropertyInDb=managedNodePropertyDao.getManagedNodeProperty(managedNodeProperty.getManagedNode());
        assertNull(managedNodePropertyInDb);
    }
    
    
    private ManagedNodeProperty newManagedNodeProperty(){
        ManagedNodeProperty managedNodeProperty=new ManagedNodeProperty();
        managedNodeProperty.setManagedNode(new MoNaming("nm=1/"));
        managedNodeProperty.setIp("192.168.11.11");
        managedNodeProperty.setPort(8888);
        return managedNodeProperty;
    }
}
