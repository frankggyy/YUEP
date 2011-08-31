/*
 * $Id: DataSourceConnProvider.java, 2011-3-21 下午05:52:02 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.datasource;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.HibernateException;
import org.hibernate.connection.ConnectionProvider;

/**
 * <p>
 * Title: DataSourceConnProvider
 * </p>
 * <p>
 * Description:数据库连接提供者
 * 
 * </p>
 * 
 * @author yangtao
 * created 2011-3-21 下午05:52:02
 * modified [who date description]
 * check [who date description]
 */
public class DataSourceConnProvider implements ConnectionProvider {
    private final static String BASE_KEY = "dscp.";
    private final static String DATASOURCE_KEY = "dscp.dataSource";
    protected DataSource dataSource;

    /**
     * 
     * (non-Javadoc)
     * @see org.hibernate.connection.ConnectionProvider#configure(java.util.Properties)
     */
    @Override
    public void configure(Properties props) throws HibernateException {
        String dataSourceClass = null;
        Properties new_props = new Properties();
        for(Entry<Object,Object> entry:props.entrySet()){
            String key=entry.getKey().toString();
            if (DATASOURCE_KEY.equalsIgnoreCase(key)) {
                dataSourceClass = props.getProperty(key);
            } else if (key.startsWith(BASE_KEY)) {
                String value = entry.getValue().toString();
                new_props.setProperty(key.substring(BASE_KEY.length()), value);
            }
        
        }
        if (dataSourceClass == null)
            throw new HibernateException("Property 'dscp.datasource' no defined.");
        try {
            dataSource = (DataSource) Class.forName(dataSourceClass).newInstance();
            BeanUtils.populate(dataSource, new_props);
        } catch (Exception e) {
            throw new HibernateException(e);
        }
    }

    /**
     * 
     * (non-Javadoc)
     * @see org.hibernate.connection.ConnectionProvider#getConnection()
     */
    @Override
    public Connection getConnection() throws SQLException {
        final Connection conn = dataSource.getConnection();
        return conn;
    }

    /**
     * 
     * (non-Javadoc)
     * @see org.hibernate.connection.ConnectionProvider#closeConnection(java.sql.Connection)
     */
    @Override
    public void closeConnection(Connection conn) throws SQLException {
        if (conn != null && !conn.isClosed())
            conn.close();
    }

    /**
     * 
     * (non-Javadoc)
     * @see org.hibernate.connection.ConnectionProvider#close()
     */
    @Override
    public void close() throws HibernateException {
        if (dataSource != null)
            try {
                Method mClose = dataSource.getClass().getMethod("close", null);
                mClose.invoke(dataSource, null);
            } catch (Exception e) {
                throw new HibernateException(e);
            }
        dataSource = null;
    }

    /**
     * 
     * (non-Javadoc)
     * @see org.hibernate.connection.ConnectionProvider#supportsAggressiveRelease()
     */
    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }
}
