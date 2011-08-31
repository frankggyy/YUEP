/*
 * $Id: JDBCTemplate.java, 2011-3-31 上午10:51:08 yangtao Exp $
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
 * JDBC数据库访问的模板接口
 * </p>
 * 
 * @author yangtao
 * created 2011-3-31 上午10:51:08
 * modified [who date description]
 * check [who date description]
 */
public interface JDBCTemplate {
    
   /**
    * 根据表格列表及其值删除指定表格的记录
    * @param columnNames
    *        表格列名称
    * @param comlumnValues
    *        表格列的值
    * @param tableName
    *        表格名称
    */
   public void  delete (String[] columnNames,Object[] comlumnValues,String tableName);
   /**
    * 更新对象
    * @param <T>
    * @param updatedObjects
    *        被更新的对象
    * @param sqlConvert
    *         SQLConvert
    */
   public <T> void update(List<T> updatedObjects,SQLConvert<T> sqlConvert);
   /**
    * 创建对象
    * @param <T>
    * @param createdObjects
    *        被创建的对象
    * @param sqlConvert
    *        SQLConvert
    */
   public <T> void  create(List<T> createdObjects,  SQLConvert <T> sqlConvert);
   /**
    * 查询对象
    * @param <T>
    * @param querySQL
    *        SQL查询语句
    * @param objectConvert
    *        ObjectConvert
    * @return
    *       对象集合
    */
   public <T> List<T> query(String querySQL,ObjectConvert<T> objectConvert);
  
   
   /**
    * 查询对象
    * @param <T>
    * @param columnNames
    * @param comlumnValues
    * @param tableName
    * @param objectConvert
    * @return
    */
   public <T> List<T> query(String[] columnNames,Object[] comlumnValues,String tableName,ObjectConvert<T> objectConvert);
   
   ///**
   //* 根据SQL查询对象(ResultSet里面的Statement需要由调用者来关闭)
   //* @param sql
   //* @return
   //*        ResultSet
   // */
   //ResultSet executeQuery (String sql);
   
   /**
    * 执行SQL语句
    * @param sql
    *        SQL
    */
   void execute(String sql);

    

}
