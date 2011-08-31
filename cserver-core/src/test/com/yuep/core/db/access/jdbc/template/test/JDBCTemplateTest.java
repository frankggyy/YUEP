/*
 * $Id: JDBCTemplateTest.java, 2011-3-31 下午12:25:04 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.access.jdbc.template.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.yuep.base.util.format.YuepObjectUtils;
import com.yuep.core.db.access.jdbc.template.JDBCTemplate;
import com.yuep.core.db.access.jdbc.template.JDBCTemplateImpl;
import com.yuep.core.db.access.jdbc.template.ObjectConvert;
import com.yuep.core.db.access.jdbc.template.SQLConvert;
import com.yuep.core.db.session.def.Session;
import com.yuep.core.db.sessionfactory.def.SessionFactory;
import com.yuep.core.db.test.DbBaseCase;
import com.yuep.core.db.test.SessionFactoryBuilder;
import com.yuep.core.db.test.object.TestDbObject;
import com.yuep.core.db.transaction.def.Transaction;

/**
 * <p>
 * Title: JDBCTemplateTest
 * </p>
 * <p>
 * Description:
 * JDBCTemplate单元测试类
 * </p>
 * 
 * @author yangtao
 * created 2011-3-31 下午12:25:04
 * modified [who date description]
 * check [who date description]
 */
public class JDBCTemplateTest extends DbBaseCase {
    private  SessionFactory sessionFactory;
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
    
    /**
     * 
     * (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    public void tearDown(){
        clearData();
        if(close)
            sessionFactory.close();
        
    }
    
    private void clearData(){
        Session session=sessionFactory.openSession();
        JDBCTemplate jdbcTemplate=new JDBCTemplateImpl(session);
        Transaction  transaction=session.beginTransaction();
        try{
            jdbcTemplate.execute("delete from "+TestDbObject.class.getSimpleName());
            transaction.commit();
        }catch(Exception ex){
            transaction.rollback();
            throw new RuntimeException(ex);
        }finally{
            session.close();
        }
    }

    public void testCreate(){
        Session session=sessionFactory.openSession();
        JDBCTemplate jdbcTemplate=new JDBCTemplateImpl(session);
        
        TestDbObject testDbObject=new TestDbObject();
        testDbObject.setId(2);
        testDbObject.setName("yang");
        testDbObject.setValue("ok");
        
        Transaction  transaction=session.beginTransaction();
        try{
        jdbcTemplate.create(YuepObjectUtils.newArrayList(testDbObject), new InsertTestDbObjectSQLConvert());
 
                transaction.commit();
        }catch(Exception ex){
            transaction.rollback();
            throw new RuntimeException(ex);
        }finally{
            session.close();
        }
    }
    
    
    public void testUpdate(){

        Session session=sessionFactory.openSession();
        JDBCTemplate jdbcTemplate=new JDBCTemplateImpl(session);
        
        TestDbObject testDbObject=new TestDbObject();
        testDbObject.setId(2);
        testDbObject.setName("yang");
        testDbObject.setValue("ok");
        
        Transaction  transaction=session.beginTransaction();
        try{
        jdbcTemplate.create(YuepObjectUtils.newArrayList(testDbObject),new InsertTestDbObjectSQLConvert());
        transaction.commit();
        
        
        transaction=session.beginTransaction();
        testDbObject.setName("tao");
        jdbcTemplate.update(YuepObjectUtils.newArrayList(testDbObject),new UpdateTestDbObjectSQLConvert());
        transaction.commit();
        
        List<TestDbObject> testDbObjectsInDb=jdbcTemplate.query("select * from "+TestDbObject.class.getSimpleName()+" where id="+testDbObject.getId(),new TestDbObjectConvert());
        
        assertTrue(testDbObjectsInDb.get(0).getName().equals(testDbObject.getName()));

      
        }catch(Exception ex){
            transaction.rollback();
            throw new RuntimeException(ex);
        }finally{
            session.close();
        }
    
    }
    
    
    public void testGuery(){
        Session session=sessionFactory.openSession();
        JDBCTemplate jdbcTemplate=new JDBCTemplateImpl(session);
        
        TestDbObject testDbObject=new TestDbObject();
        testDbObject.setId(2);
        testDbObject.setName("yang");
        testDbObject.setValue("ok");
        
        Transaction  transaction=session.beginTransaction();
        try{
            jdbcTemplate.create(YuepObjectUtils.newArrayList(testDbObject),new InsertTestDbObjectSQLConvert());
            transaction.commit();
        
            transaction=session.beginTransaction();
            List<TestDbObject> testDbObjectsInDb=jdbcTemplate.query(new String[]{"id"},new Object[]{testDbObject.getId()},TestDbObject.class.getSimpleName(),new TestDbObjectConvert());
        
            assertTrue(testDbObjectsInDb.get(0).getId()==testDbObject.getId());

        }catch(Exception ex){
            transaction.rollback();
            throw new RuntimeException(ex);
        }finally{
            session.close();
        } 
    }
    
    public void testDelete(){
        Session session=sessionFactory.openSession();
        JDBCTemplate jdbcTemplate=new JDBCTemplateImpl(session);
        
        TestDbObject testDbObject=new TestDbObject();
        testDbObject.setId(2);
        testDbObject.setName("yang");
        testDbObject.setValue("ok");
        
        Transaction  transaction=session.beginTransaction();
        try{
        jdbcTemplate.create(YuepObjectUtils.newArrayList(testDbObject), new InsertTestDbObjectSQLConvert());
        
        jdbcTemplate.delete(new String[]{"name","value"}, new Object[]{testDbObject.getName(),testDbObject.getValue()}, TestDbObject.class.getSimpleName());
        
        List<TestDbObject> testDbObjectsInDb=jdbcTemplate.query(new String[]{"id"}, new Object[]{testDbObject.getId()}, TestDbObject.class.getSimpleName(), new TestDbObjectConvert());
        
        assertTrue(CollectionUtils.isEmpty(testDbObjectsInDb));
        transaction.commit();
        }catch(Exception ex){
            transaction.rollback();
            throw new RuntimeException(ex);
        }finally{
            session.close();
        }
    }
    

   private static class TestDbObjectConvert implements ObjectConvert<TestDbObject>{
    @Override
    public List<TestDbObject> converToObject(ResultSet resultSet) {
        List<TestDbObject> testDbObjects=new ArrayList<TestDbObject>();
        try{
        while(resultSet.next()){
            TestDbObject testDbObject=new TestDbObject();
            testDbObjects.add(testDbObject);
            
            long id=resultSet.getLong("id");
            testDbObject.setId(id);
            String name=resultSet.getString("name");
            testDbObject.setName(name);
            String value=resultSet.getString("value");
            testDbObject.setValue(value);
            
        }
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }finally{
            try {
                resultSet.close();
                resultSet.getStatement().close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
           
            
        }
        return testDbObjects;
    }
   }
    
    
    
    private static class InsertTestDbObjectSQLConvert implements SQLConvert<TestDbObject>{
        @Override
        public List<String> convertToSQL(List<TestDbObject> objects) {
            StringBuilder sb=new StringBuilder();
            int i=0;
            for(TestDbObject dbObject:objects){
                sb.append("(");
                
                sb.append(dbObject.getId());
                sb.append(",");
                
                sb.append("'");
                sb.append(dbObject.getName());
                sb.append("'");
                
                sb.append(",");
                
                sb.append("'");
                sb.append(dbObject.getValue());
                sb.append("'");
                
                sb.append(")");
                
                if(i!=objects.size()-1)
                    sb.append(",");
                ++i;
            }
            String sql="INSERT INTO "+ TestDbObject.class.getSimpleName()+ "(id,name,value) VALUES " +sb.toString();
            return YuepObjectUtils.newArrayList(sql);
        }
        
    }
    
    private static class UpdateTestDbObjectSQLConvert implements SQLConvert<TestDbObject>{

        @Override
        public List<String> convertToSQL(List<TestDbObject> objects) {
            List<String> sqls=new ArrayList<String>();
            
            for(TestDbObject dbObject:objects){
             
                StringBuilder sb=new StringBuilder();
                
                sb.append("name=");
                sb.append("'");
                sb.append(dbObject.getName());
                sb.append("', ");
                
                sb.append("value=");
                sb.append("'");
                sb.append(dbObject.getValue());
                sb.append("'");

                String sql="update "+ TestDbObject.class.getSimpleName()+" set "+sb.toString()+" where id="+dbObject.getId();
                sqls.add(sql);
                
            }
          
            return sqls;
        }
        
    }
    

}
