/*
 * $Id: JDBCTemplate.java, 2011-3-31 ����10:51:08 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.access.jdbc.template;

import java.util.List;

/**
 * <p>
 * Title: JDBCTemplate
 * </p>
 * <p>
 * Description:
 * JDBC���ݿ���ʵ�ģ��ӿ�
 * </p>
 * 
 * @author yangtao
 * created 2011-3-31 ����10:51:08
 * modified [who date description]
 * check [who date description]
 */
public interface JDBCTemplate {
    
   /**
    * ���ݱ���б���ֵɾ��ָ�����ļ�¼
    * @param columnNames
    *        ���������
    * @param comlumnValues
    *        ����е�ֵ
    * @param tableName
    *        �������
    */
   public void  delete (String[] columnNames,Object[] comlumnValues,String tableName);
   /**
    * ���¶���
    * @param <T>
    * @param updatedObjects
    *        �����µĶ���
    * @param sqlConvert
    *         SQLConvert
    */
   public <T> void update(List<T> updatedObjects,SQLConvert<T> sqlConvert);
   /**
    * ��������
    * @param <T>
    * @param createdObjects
    *        �������Ķ���
    * @param sqlConvert
    *        SQLConvert
    */
   public <T> void  create(List<T> createdObjects,  SQLConvert <T> sqlConvert);
   /**
    * ��ѯ����
    * @param <T>
    * @param querySQL
    *        SQL��ѯ���
    * @param objectConvert
    *        ObjectConvert
    * @return
    *       ���󼯺�
    */
   public <T> List<T> query(String querySQL,ObjectConvert<T> objectConvert);
  
   
   /**
    * ��ѯ����
    * @param <T>
    * @param columnNames
    * @param comlumnValues
    * @param tableName
    * @param objectConvert
    * @return
    */
   public <T> List<T> query(String[] columnNames,Object[] comlumnValues,String tableName,ObjectConvert<T> objectConvert);
   
   ///**
   //* ����SQL��ѯ����(ResultSet�����Statement��Ҫ�ɵ��������ر�)
   //* @param sql
   //* @return
   //*        ResultSet
   // */
   //ResultSet executeQuery (String sql);
   
   /**
    * ִ��SQL���
    * @param sql
    *        SQL
    */
   void execute(String sql);

    

}
