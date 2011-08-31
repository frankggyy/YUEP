/*
 * $Id: SessionTest.java, 2011-3-30 下午04:28:45 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.session.test;

import java.util.List;

import org.hibernate.Query;

import com.yuep.core.db.access.hibernate.template.HibernateTemplate;
import com.yuep.core.db.access.hibernate.template.HibernateTemplateImpl;
import com.yuep.core.db.session.def.HibernateCallback;
import com.yuep.core.db.session.def.Session;
import com.yuep.core.db.sessionfactory.def.SessionFactory;
import com.yuep.core.db.test.DbBaseCase;
import com.yuep.core.db.test.SessionFactoryBuilder;
import com.yuep.core.db.test.object.TestDbObject;
import com.yuep.core.db.transaction.def.Transaction;

/**
 * <p>
 * Title: SessionTest
 * </p>
 * <p>
 * Description:
 * Session单元测试
 * </p>
 * 
 * @author yangtao
 * created 2011-3-30 下午04:28:45
 * modified [who date description]
 * check [who date description]
 */
public class SessionTest extends DbBaseCase {
     
    private SessionFactory sessionFactory;
    private boolean close;
    @Override
    public void setUp(){
        sessionFactory=this.getSessionFactory();
        if(sessionFactory==null){
            sessionFactory=new SessionFactoryBuilder().build();
            close=true;
        }
    }
    
    @Override
    public void tearDown(){
        clearData();
        if(close){
            sessionFactory.close(); 
        }
    }
    
    
    private void clearData(){
        Session session=sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();
        try{
            HibernateTemplate<TestDbObject> hibernateTemplate=new HibernateTemplateImpl<TestDbObject>(TestDbObject.class,session);
            hibernateTemplate.deleteAllEntities();
            transaction.commit();
        }catch(Exception ex){
            transaction.rollback();
            throw new RuntimeException(ex);
        }finally{
            session.close();
        }
    }
    
     
    public void testClose(){
       Session session= sessionFactory.openSession();
       try{
       assertNotNull(session);
       }finally{
           session.close();
       }
    }
    
    
    public void testGetConnection(){
        Session session= sessionFactory.openSession();
        try{
            assertNotNull(session.getConnection());
        }finally{
            session.close();
        }
    }
    
    
    public void testBeginTransaction(){
        Session session= sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();
        try{
            assertNotNull(transaction);
        }finally{
            transaction.commit();
            session.close();
        }
    }
    
    public void testGetTransaction(){
        Session session= sessionFactory.openSession();
        Transaction transaction=session.getTransaction();
        try{
            assertNotNull(transaction);
        }finally{
            session.close();
        }
    }
    
    
    public void testDelete(){
        Session session= sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();
        try{
            TestDbObject testDbObject=new TestDbObject();
            testDbObject.setName("aaa");
            testDbObject.setValue("bbb");
            session.save(testDbObject);
            transaction.commit();
            
            transaction.begin();
            session.delete(testDbObject);
            transaction.commit();
            
        }catch(Exception ex){
            transaction.rollback();
            throw new RuntimeException(ex);
        } finally{
            session.close();
        }
    }
    
    
    public void testUpdate(){
        Session session= sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();
        try{
            TestDbObject testDbObject=new TestDbObject();
            testDbObject.setName("aaa");
            testDbObject.setValue("bbb");
            session.save(testDbObject);
            transaction.commit();
            
            transaction.begin();
            testDbObject.setName("bbbb");
            session.update(testDbObject);
            transaction.commit();
            
            transaction.begin();
            session.delete(testDbObject);
            transaction.commit();
            
        }catch(Exception ex){
            transaction.rollback();
            throw new RuntimeException(ex);
        } finally{
            session.close();
        }
    }
    
    
    public void testSave(){
        Session session= sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();
        try{
            TestDbObject testDbObject=new TestDbObject();
            testDbObject.setName("aaacc");
            testDbObject.setValue("bbbccc");
            session.save(testDbObject);
            transaction.commit();
            session.evict(testDbObject);
            List<TestDbObject> results=session.query(" from "+TestDbObject.class.getSimpleName()+" obj where obj.id="+testDbObject.getId());
            assertTrue(results.size()==1);
            transaction.begin();
            session.delete(results);
            transaction.commit();
        }catch(Exception ex){
            transaction.rollback();
            throw new RuntimeException(ex);
        } finally{
            session.close();
        }
    }
    
    
    public void testEvict(){

        Session session= sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();
        try{
            TestDbObject testDbObject=new TestDbObject();
            testDbObject.setName("aaacc");
            testDbObject.setValue("bbbccc");
            session.save(testDbObject);
            transaction.commit();
            session.evict(testDbObject);
            
            assertTrue(!session.contains(testDbObject));
            
            transaction=session.beginTransaction();
            session.execute("delete from "+TestDbObject.class.getSimpleName()+" obj where obj.id="+testDbObject.getId());
            transaction.commit();
            
        }catch(Exception ex){
            transaction.rollback();
            throw new RuntimeException(ex);
        } finally{
            session.close();
        }
    }
    
    
    public void testQuery(){
        Session session= sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();
        try{
            TestDbObject testDbObject=new TestDbObject();
            testDbObject.setName("aaacc");
            testDbObject.setValue("bbbccc");
            session.save(testDbObject);
            transaction.commit();
            session.evict(testDbObject);
            
            List<TestDbObject> results=session.query(" from "+TestDbObject.class.getSimpleName()+" obj where obj.id="+testDbObject.getId());
            assertTrue(results.size()==1);
            session.delete(results);
            
        }catch(Exception ex){
            transaction.rollback();
            throw new RuntimeException(ex);
        } finally{
            session.close();
        }
    
    }
    
    
    public void testExecute(){

        Session session= sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();
        try{
            TestDbObject testDbObject=new TestDbObject();
            testDbObject.setName("aaacc");
            testDbObject.setValue("bbbccc");
            session.save(testDbObject);
            session.execute("delete from "+TestDbObject.class.getSimpleName()+" obj where obj.id="+testDbObject.getId());
            transaction.commit();
        }catch(Exception ex){
            transaction.rollback();
            throw new RuntimeException(ex);
        } finally{
            session.close();
        }
    }
    
    
    public void testExecuteHibernateCallback(){


        Session session= sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();
        try{
            
            session.execute(new HibernateCallback(){
                @Override
                public Object doInHibernate(final org.hibernate.Session session) {
                    TestDbObject testDbObject=new TestDbObject();
                    testDbObject.setName("aaacc");
                    testDbObject.setValue("bbbccc");
                    session.save(testDbObject);
                    
                    Query query=session.createQuery("delete from "+TestDbObject.class.getSimpleName()+" obj where obj.id="+testDbObject.getId());
                    query.executeUpdate();
                    
                    return null;
                }
            });
           
          
            transaction.commit();
        }catch(Exception ex){
            transaction.rollback();
            throw new RuntimeException(ex);
        } finally{
            session.close();
        }
    
    }
    
}
