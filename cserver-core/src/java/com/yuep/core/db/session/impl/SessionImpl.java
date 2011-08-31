/*
 * $Id: DefaultSession.java, 2011-3-21 上午10:28:32 Administrator Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.session.impl;

import java.sql.Connection;
import java.util.List;

import org.hibernate.Query;

import com.yuep.base.util.format.YuepObjectUtils;
import com.yuep.core.db.session.def.HibernateCallback;
import com.yuep.core.db.session.def.Session;
import com.yuep.core.db.transaction.def.Transaction;
import com.yuep.core.db.transaction.impl.TransactionImpl;

/**
 * <p>
 * Title: DefaultSession
 * </p>
 * <p>
 * Description:
 * 数据库会话默认实现
 * </p>
 * 
 * @author yangtao
 * created 2011-3-21 上午10:28:32
 * modified [who date description]
 * check [who date description]
 */
public class SessionImpl implements Session {
    
    private org.hibernate.Session hibernateSession;
   
    /**
     * 批量处理每批次的处理条目数
     */
    public static final int FLUSH_SIZE=50;
    
    public SessionImpl(org.hibernate.Session hibernateSession){
        this.hibernateSession=hibernateSession;
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.db.session.def.Session#close()
     */
    @Override
    public void close() {
        hibernateSession.close();
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.db.session.def.Session#isOpen()
     */
    @Override
    public  boolean isOpen(){
        return hibernateSession.isOpen();
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.db.session.def.Session#getConnection()
     */
    @SuppressWarnings("deprecation")
	@Override
    public  Connection getConnection(){
        return hibernateSession.connection();
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.db.session.def.Session#beginTransaction()
     */
    @Override
    public  Transaction beginTransaction(){
        return new TransactionImpl(hibernateSession.beginTransaction());
        
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.db.session.def.Session#getTransaction()
     */
    @Override
    public Transaction getTransaction(){
        return new TransactionImpl(hibernateSession.getTransaction());
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.db.session.def.Session#delete(java.util.List)
     */
    @Override
    public <T> void delete(List<T> objs){
        int i=0;
        for (T t : objs) {
            hibernateSession.delete(t);
            if (i++ % FLUSH_SIZE == 0) {
                hibernateSession.flush();
                hibernateSession.clear();
            }
        } 
        hibernateSession.flush();
        hibernateSession.clear();
    
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.db.session.def.Session#delete(T[])
     */
    @Override
    public <T> void delete(T...objs){
    	delete(YuepObjectUtils.newArrayList(objs));
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.db.session.def.Session#update(java.util.List)
     */
    @Override
    public <T> void update (List<T> objs){
        int i=0;
        for (T t : objs) {
            hibernateSession.update(t);
            if (i++ % FLUSH_SIZE == 0) {
                hibernateSession.flush();
                hibernateSession.clear();
            }
        } 
        hibernateSession.flush();
        hibernateSession.clear();
    
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.db.session.def.Session#update(T[])
     */
    @Override
    public <T> void update(T...objs){
    	update(YuepObjectUtils.newArrayList(objs));
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.db.session.def.Session#save(java.util.List)
     */
    @Override
    public <T> void save (List<T> objs){
        int i=0;
        for (T t : objs) {
            hibernateSession.save(t);
            if (i++ % FLUSH_SIZE == 0) {
                hibernateSession.flush();
                hibernateSession.clear();
            }
        } 
        hibernateSession.flush();
        hibernateSession.clear();
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.db.session.def.Session#save(T[])
     */
    @Override
    public <T> void save (T...objs){
    	save(YuepObjectUtils.newArrayList(objs));
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.db.session.def.Session#evict(T[])
     */
    @Override
    public <T> void evict(T... objs) {
        evict(YuepObjectUtils.newArrayList(objs));
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.db.session.def.Session#contains(java.lang.Object)
     */
    @Override
    public boolean contains(Object obj) {
        return hibernateSession.contains(obj);
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.db.session.def.Session#evict(java.util.List)
     */
    @Override
    public <T> void evict(List<T> objs) {
        for(T obj:objs){
            hibernateSession.evict(obj);
        }
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.db.session.def.Session#query(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> query(String hql){
        Query query=hibernateSession.createQuery(hql);
        return query.list();
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.db.session.def.Session#execute(java.lang.String)
     */
    @Override
    public Object execute(String hql){
        Query query=hibernateSession.createQuery(hql);
        return query.executeUpdate();
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.db.session.def.Session#execute(com.yuep.core.db.session.def.HibernateCallback)
     */
    @Override
    public Object execute(HibernateCallback hibernateCallback) {
        return hibernateCallback.doInHibernate(hibernateSession);
    }
    /**
     * @see com.yuep.core.db.session.def.Session#merge(T[])
     */
    @Override
    public <T> void merge(T... entities) {
        int i=0;
        for (T t : entities) {
            hibernateSession.merge(t);
            if (i++ % FLUSH_SIZE == 0) {
                hibernateSession.flush();
                hibernateSession.clear();
            }
        } 
        hibernateSession.flush();
        hibernateSession.clear();
        
    }
    


}
