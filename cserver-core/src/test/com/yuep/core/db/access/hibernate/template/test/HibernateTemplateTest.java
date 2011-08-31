/*
 * $Id: HibernateTemplateTest.java, 2011-3-31 ÉÏÎç09:38:00 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.access.hibernate.template.test;

import java.util.HashMap;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.yuep.base.util.format.YuepObjectUtils;
import com.yuep.core.db.access.hibernate.template.HibernateTemplate;
import com.yuep.core.db.access.hibernate.template.HibernateTemplateImpl;
import com.yuep.core.db.session.def.Session;
import com.yuep.core.db.sessionfactory.def.SessionFactory;
import com.yuep.core.db.test.DbBaseCase;
import com.yuep.core.db.test.SessionFactoryBuilder;
import com.yuep.core.db.test.object.TestDbObject;
import com.yuep.core.db.transaction.def.Transaction;

/**
 * <p>
 * Title: HibernateTemplateTest
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author yangtao
 * created 2011-3-31 ÉÏÎç09:38:00
 * modified [who date description]
 * check [who date description]
 */
public class HibernateTemplateTest extends DbBaseCase{
    
    
    private SessionFactory sessionFactory;
    private boolean close;
    /**
     * 
     * (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
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
    
    
    public void testCount(){
        Session session=sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();
        try{
            HibernateTemplate<TestDbObject> hibernateTemplate=new HibernateTemplateImpl<TestDbObject>(TestDbObject.class,session);
            TestDbObject testDbObject=new TestDbObject();
            testDbObject.setName("bbbbb");
            testDbObject.setValue("bbbbb");
            session.save(testDbObject);
            assertTrue(hibernateTemplate.count()==1);
            transaction.commit();
          
        }catch(Exception ex){
            transaction.rollback();
            throw new RuntimeException(ex);
        }finally{
            session.close();
        }
    }
    
    
    public void testDeleteAllEntities(){
        Session session=sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();
        try{
            HibernateTemplate<TestDbObject> hibernateTemplate=new HibernateTemplateImpl<TestDbObject>(TestDbObject.class,session);
            TestDbObject testDbObject=new TestDbObject();
            testDbObject.setName("bbbbb");
            testDbObject.setValue("bbbbb");
            session.save(testDbObject);
            transaction.commit();
            
            transaction=session.beginTransaction();
            hibernateTemplate.deleteAllEntities();
            transaction.commit();
            
            List<TestDbObject> testDbObjects=hibernateTemplate.getAllEntities();
            assertTrue(CollectionUtils.isEmpty(testDbObjects));
          
        }catch(Exception ex){
            transaction.rollback();
            throw new RuntimeException(ex);
        }finally{
            session.close();
        }
    }
    
    
    public void testDeleteEntities(){

        Session session=sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();
        try{
            HibernateTemplate<TestDbObject> hibernateTemplate=new HibernateTemplateImpl<TestDbObject>(TestDbObject.class,session);
            TestDbObject testDbObject=new TestDbObject();
            testDbObject.setName("bbbbb");
            testDbObject.setValue("bbbbb");
            session.save(testDbObject);
            transaction.commit();
            
            transaction=session.beginTransaction();
            hibernateTemplate.deleteAllEntities(YuepObjectUtils.newArrayList(testDbObject));
            transaction.commit();
            
            List<TestDbObject> testDbObjects=hibernateTemplate.getAllEntities();
            assertTrue(CollectionUtils.isEmpty(testDbObjects));
          
        }catch(Exception ex){
            transaction.rollback();
            throw new RuntimeException(ex);
        }finally{
            session.close();
        }
    
    }
    
    
    public void testGetAllEntities(){
        Session session=sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();
        try{
            HibernateTemplate<TestDbObject> hibernateTemplate=new HibernateTemplateImpl<TestDbObject>(TestDbObject.class,session);
            TestDbObject testDbObject=new TestDbObject();
            testDbObject.setName("bbbbb");
            testDbObject.setValue("bbbbb");
            session.save(testDbObject);
            
            TestDbObject testDbObject1=new TestDbObject();
            testDbObject1.setName("bbbbb2");
            testDbObject1.setValue("bbbbb2");
            session.save(testDbObject1);
            
            transaction.commit();
      
            List<TestDbObject> testDbObjects=hibernateTemplate.getAllEntities();
            assertTrue(testDbObjects.size()==2);
          
        }catch(Exception ex){
            transaction.rollback();
            throw new RuntimeException(ex);
        }finally{
            session.close();
        }
    
    
    }
    
    
    public void testGetEntitiesByOrCondition(){
        Session session=sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();
        try{
            HibernateTemplate<TestDbObject> hibernateTemplate=new HibernateTemplateImpl<TestDbObject>(TestDbObject.class,session);
            TestDbObject testDbObject=new TestDbObject();
            testDbObject.setName("bbbbb");
            testDbObject.setValue("bbbbb");
            session.save(testDbObject);
            transaction.commit();
            
            List<TestDbObject> testBbObjectInDbs=hibernateTemplate.getEntitiesByOrCondition("name", "bbbb");
            assertNotNull(!CollectionUtils.isEmpty(testBbObjectInDbs));
        }catch(Exception ex){
            transaction.rollback();
            throw new RuntimeException(ex);
        }finally{
            session.close();
        }
        
    
    }
    
    public void testDeleteEntityByProperty(){
        Session session=sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();
        try{
            HibernateTemplate<TestDbObject> hibernateTemplate=new HibernateTemplateImpl<TestDbObject>(TestDbObject.class,session);
            TestDbObject testDbObject=new TestDbObject();
            testDbObject.setName("bbbbb");
            testDbObject.setValue("bbbbb");
            session.save(testDbObject);
            transaction.commit();
            
            transaction=session.beginTransaction();
            hibernateTemplate.deleteEntityByProperty("id", testDbObject.getId());
            transaction.commit();
            
            TestDbObject testBbObjectInDb=hibernateTemplate.getUniqueEntityByOneProperty("id", testDbObject.getId());
            assertNull(testBbObjectInDb);
        }catch(Exception ex){
            transaction.rollback();
            throw new RuntimeException(ex);
        }finally{
            session.close();
        }
        
    }
    
    public void testGetEntitiesByPropNames(){
        Session session=sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();
        try{
            HibernateTemplate<TestDbObject> hibernateTemplate=new HibernateTemplateImpl<TestDbObject>(TestDbObject.class,session);
            TestDbObject testDbObject=new TestDbObject();
            testDbObject.setName("bbbbb");
            testDbObject.setValue("bbbbb");
            session.save(testDbObject);
            transaction.commit();
            
            List<TestDbObject> testBbObjectInDbs=hibernateTemplate.getEntitiesByPropNames(new String[]{"name","value"}, new String[]{"bbbbb","bbbbb"});
            assertTrue(!CollectionUtils.isEmpty(testBbObjectInDbs));
        }catch(Exception ex){
            transaction.rollback();
            throw new RuntimeException(ex);
        }finally{
            session.close();
        }
        
    
    }
    
    public void testGetEntityProps(){
        Session session=sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();
        try{
            HibernateTemplate<TestDbObject> hibernateTemplate=new HibernateTemplateImpl<TestDbObject>(TestDbObject.class,session);
            TestDbObject testDbObject=new TestDbObject();
            testDbObject.setName("bbbbb");
            testDbObject.setValue("bbbbb");
            session.save(testDbObject);
            transaction.commit();
            
            List ids=hibernateTemplate.getEntityProps(new String[]{"id"}, new String[]{"name"}, new String[]{"bbbbb"},true);
            assertTrue(ids.size()==1&&ids.contains(testDbObject.getId()));
        }catch(Exception ex){
            transaction.rollback();
            throw new RuntimeException(ex);
        }finally{
            session.close();
        }
    }
    
    public void testGetUniqueEntityByOneProperty(){

        Session session=sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();
        try{
            HibernateTemplate<TestDbObject> hibernateTemplate=new HibernateTemplateImpl<TestDbObject>(TestDbObject.class,session);
            TestDbObject testDbObject=new TestDbObject();
            testDbObject.setName("bbbbb");
            testDbObject.setValue("bbbbb");
            session.save(testDbObject);
            transaction.commit();
            
            TestDbObject testDbObject1=hibernateTemplate.getUniqueEntityByOneProperty("id", testDbObject.getId());
            assertTrue(testDbObject1.getId()==testDbObject.getId());
        }catch(Exception ex){
            transaction.rollback();
            throw new RuntimeException(ex);
        }finally{
            session.close();
        }
    }

    public void testGetUniqueEntityByPropNames(){
        Session session=sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();
        try{
            HibernateTemplate<TestDbObject> hibernateTemplate=new HibernateTemplateImpl<TestDbObject>(TestDbObject.class,session);
            TestDbObject testDbObject=new TestDbObject();
            testDbObject.setName("bbbbb1");
            testDbObject.setValue("bbbbb2");
            session.save(testDbObject);
            transaction.commit();
            
            TestDbObject testDbObject1=hibernateTemplate.getUniqueEntityByPropNames(new String[]{"name","value"}, new String[]{"bbbbb1","bbbbb2"});
            assertTrue(testDbObject1.getId()==testDbObject.getId());
        }catch(Exception ex){
            transaction.rollback();
            throw new RuntimeException(ex);
        }finally{
            session.close();
        }
    }
    
    
    public void testIsEntityExisted(){
        Session session=sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();
        try{
            HibernateTemplate<TestDbObject> hibernateTemplate=new HibernateTemplateImpl<TestDbObject>(TestDbObject.class,session);
            TestDbObject testDbObject=new TestDbObject();
            testDbObject.setName("bbbbb1");
            testDbObject.setValue("bbbbb2");
            session.save(testDbObject);
            transaction.commit();
           
            assertTrue(hibernateTemplate.isEntityExisted(new String[]{"name","value"}, new String[]{"bbbbb1","bbbbb2"}));
        }catch(Exception ex){
            transaction.rollback();
            throw new RuntimeException(ex);
        }finally{
            session.close();
        }
    }

    
    public void testUpdate(){

        Session session=sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();
        try{
            HibernateTemplate<TestDbObject> hibernateTemplate=new HibernateTemplateImpl<TestDbObject>(TestDbObject.class,session);
            TestDbObject testDbObject=new TestDbObject();
            testDbObject.setName("bbbbb1");
            testDbObject.setValue("bbbbb2");
            session.save(testDbObject);
            testDbObject.setValue("bbbbb3");
            session.update(testDbObject);
            transaction.commit();

            assertTrue(hibernateTemplate.isEntityExisted(new String[]{"name","value"}, new String[]{"bbbbb1","bbbbb3"}));
        }catch(Exception ex){
            transaction.rollback();
            throw new RuntimeException(ex);
        }finally{
            session.close();
        }
    }
    
    
    public void testFindPageByQuery(){
        Session session=sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();
        try{
            HibernateTemplate<TestDbObject> hibernateTemplate=new HibernateTemplateImpl<TestDbObject>(TestDbObject.class,session);
            TestDbObject testDbObject=new TestDbObject();
            for(int i=0;i<100;i++){
                testDbObject.setName("bbbbb"+i);
                testDbObject.setValue("bbbbb"+i);
                session.save(testDbObject); 
            }
            transaction.commit();
            
            transaction=session.beginTransaction();
            for(int i=1;i<=10;i++){
                String hql="from "+TestDbObject.class.getSimpleName();
                List<TestDbObject> testDbObjects=hibernateTemplate.findPageByQuery(i, 10, hql, new HashMap());
                assertTrue(testDbObjects.size()==10);
            }
       
        }catch(Exception ex){
            transaction.rollback();
            throw new RuntimeException(ex);
        }finally{
            session.close();
        } 
    }

}
