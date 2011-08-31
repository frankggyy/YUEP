/*
 * $Id: Session.java, 2011-3-21 上午09:32:07 Administrator Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.session.def;

import java.sql.Connection;
import java.util.List;

import com.yuep.core.db.transaction.def.Transaction;


/**
 * <p>
 * Title: Session
 * </p>
 * <p>
 * Description:
 * 数据库访问会话
 * </p>
 * 
 * @author yangtao
 * created 2011-3-21 上午09:32:07
 * modified [who date description]
 * check [who date description]
 */
public interface Session {
    /**
     * 关闭session
     */
    void close();
    /**
     * sesssion是否打开
     * @return
     */
    boolean isOpen();
    /**
     * 获取JDBC连接
     * @return
     */
    Connection getConnection(); 
    /**
     * 启动事务
     * @return Transaction
     */
    Transaction beginTransaction();
    /**
     * 获取事务
     * @return Transaction
     */
    Transaction getTransaction();
    /**
     * 删除持久化对象
     * @param <T>
     * @param objs
     *        被删除的持久化对象
     */
    <T> void delete(List<T> objs);
    
    /**
     * 删除持久化对象
     * @param <T>
     * @param objs
     *        被删除的持久化对象
     */
    <T> void delete(T...objs);
    /**
     * 更新持久化对象
     * @param <T>
     * @param objs
     *         被更新的持久对象
     */
    <T> void update (List<T> objs);
    /**
     * 更新持久化对象
     * @param <T>
     * @param objs
     *         被更新的持久对象
     */
    <T> void update(T...objs);
    /**
     * 创建持久对象
     * @param <T>
     * @param objs
     *        
     */
    <T> void save (List<T> objs);
    /**
     * 创建持久对象
     * @param <T>
     * @param objs
     *        
     */
    <T> void save (T...objs);
    
    /**
     * 从一级缓存中清除对象
     * @param <T>
     * @param objs
     */
    <T> void evict(T...objs);
    
    /**
     * 从一级缓存中清除对象
     * @param <T>
     * @param objs
     */
    <T> void evict(List<T> objs);
    
     /**
      * 一级缓存中是否包含指定对象
      * @param obj
      *        指定对象
      * @return
      *        true 包含
      *        false 不包含
      */
     boolean contains(Object obj);
    /**
     * 根据hql语句查询对象
     * @param <T>
     * @param hql
     * @return 符合条件的对象
     *  
     */
    <T> List<T> query(String hql);
    /**
     * 执行hql语句
     * @param hql
     * @return
     *       执行结果
     */
    Object execute(String hql);
    
    /**
     * 执行Hibernate回调
     * @param hibernateCallback
     * @return
     *       执行结果
     *     
     */
    public Object execute(HibernateCallback hibernateCallback);


    public <T> void merge(T... entities);
}
