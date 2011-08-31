/*
 * $Id: Session.java, 2011-3-21 ����09:32:07 Administrator Exp $
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
 * ���ݿ���ʻỰ
 * </p>
 * 
 * @author yangtao
 * created 2011-3-21 ����09:32:07
 * modified [who date description]
 * check [who date description]
 */
public interface Session {
    /**
     * �ر�session
     */
    void close();
    /**
     * sesssion�Ƿ��
     * @return
     */
    boolean isOpen();
    /**
     * ��ȡJDBC����
     * @return
     */
    Connection getConnection(); 
    /**
     * ��������
     * @return Transaction
     */
    Transaction beginTransaction();
    /**
     * ��ȡ����
     * @return Transaction
     */
    Transaction getTransaction();
    /**
     * ɾ���־û�����
     * @param <T>
     * @param objs
     *        ��ɾ���ĳ־û�����
     */
    <T> void delete(List<T> objs);
    
    /**
     * ɾ���־û�����
     * @param <T>
     * @param objs
     *        ��ɾ���ĳ־û�����
     */
    <T> void delete(T...objs);
    /**
     * ���³־û�����
     * @param <T>
     * @param objs
     *         �����µĳ־ö���
     */
    <T> void update (List<T> objs);
    /**
     * ���³־û�����
     * @param <T>
     * @param objs
     *         �����µĳ־ö���
     */
    <T> void update(T...objs);
    /**
     * �����־ö���
     * @param <T>
     * @param objs
     *        
     */
    <T> void save (List<T> objs);
    /**
     * �����־ö���
     * @param <T>
     * @param objs
     *        
     */
    <T> void save (T...objs);
    
    /**
     * ��һ���������������
     * @param <T>
     * @param objs
     */
    <T> void evict(T...objs);
    
    /**
     * ��һ���������������
     * @param <T>
     * @param objs
     */
    <T> void evict(List<T> objs);
    
     /**
      * һ���������Ƿ����ָ������
      * @param obj
      *        ָ������
      * @return
      *        true ����
      *        false ������
      */
     boolean contains(Object obj);
    /**
     * ����hql����ѯ����
     * @param <T>
     * @param hql
     * @return ���������Ķ���
     *  
     */
    <T> List<T> query(String hql);
    /**
     * ִ��hql���
     * @param hql
     * @return
     *       ִ�н��
     */
    Object execute(String hql);
    
    /**
     * ִ��Hibernate�ص�
     * @param hibernateCallback
     * @return
     *       ִ�н��
     *     
     */
    public Object execute(HibernateCallback hibernateCallback);


    public <T> void merge(T... entities);
}
