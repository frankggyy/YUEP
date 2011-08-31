/*
 * $Id: JDBCTemplateImpl.java, 2011-3-31 上午10:51:45 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.access.jdbc.template;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.util.ObjectUtils;

import com.yuep.base.exception.YuepException;
import com.yuep.core.db.session.def.Session;

/**
 * <p>
 * Title: JDBCTemplateImpl
 * </p>
 * <p>
 * Description:
 * JDBCTemplate接口实现类
 * </p>
 * 
 * @author yangtao
 * created 2011-3-31 上午10:51:45
 * modified [who date description]
 * check [who date description]
 */
public class JDBCTemplateImpl implements JDBCTemplate {
    
    private Session session;
    
    public  JDBCTemplateImpl(Session session){
        this.session=session;
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.db.access.jdbc.template.JDBCTemplate#create(java.util.List, com.yuep.core.db.access.jdbc.template.SQLConvert)
     */
    @Override
    public <T> void create(List<T> createdObjects, SQLConvert<T> sqlConvert) {
        final Connection connection=session.getConnection();
        Statement statement=null;
        try {
            statement=connection.createStatement();
            for(String sql:sqlConvert.convertToSQL(createdObjects)){
                statement.executeUpdate(sql);
            }
        } catch (SQLException ex) {
            throw new YuepException(ex);
        }finally{
            try {
                if(statement!=null)
                   statement.close();
                }catch (SQLException ex) {
                     throw new YuepException(ex);
                  }
                }
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.db.access.jdbc.template.JDBCTemplate#delete(java.lang.String[], java.lang.Object[], java.lang.String)
     */
    @Override
    public void delete(String[] columnNames,Object[] comlumnValues, final String tableName) {
        if(ObjectUtils.isEmpty(comlumnValues)||ObjectUtils.isEmpty(columnNames)||comlumnValues.length!=columnNames.length)
            throw new IllegalArgumentException();
        String sql="delete  from "+tableName+" where ";
        StringBuilder sb=new StringBuilder();
        sb.append(sql);
        for(int i=0;i<columnNames.length;i++){
            sb.append(columnNames[i]);
            sb.append("=?");
            if(i!=columnNames.length-1)
                sb.append(" and ");
        }
        PreparedStatement statement=null;
        final Connection connection=session.getConnection();
        try {
            statement=connection.prepareStatement(sb.toString());
            for(int i=0;i<columnNames.length;i++){
                statement.setObject(i+1, comlumnValues[i]);
            }
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new YuepException(ex);
        }finally{
            try {
            if(statement!=null)
               statement.close();
            }catch (SQLException ex) {
                 throw new YuepException(ex);
              }
            }
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.db.access.jdbc.template.JDBCTemplate#execute(java.lang.String)
     */
    @Override
    public void execute(String sql) {
        final Connection connection=session.getConnection();
        Statement statement=null;
        try {
            statement=connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException ex) {
            throw new YuepException(ex);
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException ex) {
                throw new YuepException(ex);
            }
        }
    }
    
    ///**
    // * (ResultSet里面的Statement需要由调用者来关闭)
    // * (non-Javadoc)
    // * @see com.yuep.core.db.access.jdbc.template.JDBCTemplate#executeQuery(java.lang.String)
    // */
    //@Override
    //public ResultSet executeQuery(String sql) {
    //    final Connection connection=session.getConnection();
    //    Statement statement=null;
    //    try {
    //        statement=connection.createStatement();
    //        return statement.executeQuery(sql);
    //    } catch (SQLException ex) {
    //        throw new YuepException(ex);
    //    }
    //}
    
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.db.access.jdbc.template.JDBCTemplate#query(java.lang.String, com.yuep.core.db.access.jdbc.template.ObjectConvert)
     */
    @Override
    public <T> List<T> query(String querySQL, ObjectConvert<T> objectConvert) {
        final Connection connection=session.getConnection();
        Statement statement=null;
        ResultSet resultSet=null;
        try {
            statement=connection.createStatement();
            resultSet=statement.executeQuery(querySQL);
            return objectConvert.converToObject(resultSet);
         } catch (SQLException ex) {
             throw new YuepException(ex);
         }finally{
             try {
                 if(resultSet!=null)
                     resultSet.close();
                 if (statement != null)
                     statement.close();
                
             } catch (SQLException ex) {
                 throw new YuepException(ex);
             }
         }
    }
    
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.db.access.jdbc.template.JDBCTemplate#query(java.lang.String[], java.lang.Object[], java.lang.String, com.yuep.core.db.access.jdbc.template.ObjectConvert)
     */
    @Override
    public <T> List<T> query(String[] columnNames, Object[] comlumnValues, String tableName, ObjectConvert<T> objectConvert) {
        String sql="select *  from "+tableName+" where ";
        StringBuilder sb=new StringBuilder();
        sb.append(sql);
        for(int i=0;i<columnNames.length;i++){
            sb.append(columnNames[i]);
            sb.append("='");
            sb.append(comlumnValues[i]);
            sb.append("'");
            if(i!=columnNames.length-1)
                sb.append(" and ");
        }
        return query(sb.toString(),objectConvert);
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.db.access.jdbc.template.JDBCTemplate#update(java.util.List, com.yuep.core.db.access.jdbc.template.SQLConvert)
     */
    @Override
    public <T> void update(List<T> updatedObjects, SQLConvert<T> sqlConvert) {
        final Connection connection=session.getConnection();
        Statement statement=null;
        try {
            statement=connection.createStatement();
            for(final String sql:sqlConvert.convertToSQL(updatedObjects)){
                statement.executeUpdate(sql);
            }
         } catch (SQLException ex) {
             throw new YuepException(ex);
         }finally{
             try {
                 if (statement != null)
                     statement.close();
             } catch (SQLException ex) {
                 throw new YuepException(ex);
             }
         }
    }
   
}
